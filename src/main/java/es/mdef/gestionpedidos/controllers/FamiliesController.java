package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.assemblers.FamilyAssembler;
import es.mdef.gestionpedidos.assemblers.FamilyListAssembler;
import es.mdef.gestionpedidos.assemblers.QuestionListAssembler;
import es.mdef.gestionpedidos.assemblers.UserListAssembler;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.family.FamilyListModel;
import es.mdef.gestionpedidos.models.family.FamilyModel;
import es.mdef.gestionpedidos.models.family.FamilyPostModel;
import es.mdef.gestionpedidos.models.question.QuestionListModel;
import es.mdef.gestionpedidos.models.user.UserListModel;
import es.mdef.gestionpedidos.repositories.FamilyRepository;
import es.mdef.gestionpedidos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/families")
public class FamiliesController {
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final FamilyAssembler familyAssembler;
    private final QuestionListAssembler questionListAssembler;
    private final UserListAssembler userListAssembler;
    private final FamilyListAssembler familyListAssembler;
    private final Logger log;

    public FamiliesController(FamilyRepository familyRepository,
                              FamilyAssembler familyAssembler,
                              QuestionListAssembler questionListAssembler,
                              UserRepository userRepository,
                              UserListAssembler userListAssembler, FamilyListAssembler familyListAssembler) {
        this.familyRepository = familyRepository;
        this.familyAssembler = familyAssembler;
        this.questionListAssembler = questionListAssembler;
        this.userRepository = userRepository;
        this.userListAssembler = userListAssembler;
        this.familyListAssembler = familyListAssembler;
        this.log = LoggerFactory.getLogger(FamiliesController.class);
    }

    @GetMapping("/{id}")
    public EntityModel<FamilyModel> one(@PathVariable Long id) {
        FamilyImpl family = familyRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "familia"));

        log.info("Recuperada familia " + family);
        return familyAssembler.toModel(family);
    }

    @GetMapping
    public CollectionModel<FamilyListModel> all() {
        log.info("Recuperadas familias");
        return familyListAssembler.toCollection(familyRepository.findAll());
    }

    @GetMapping("{id}/questions")
    public CollectionModel<QuestionListModel> questions(@PathVariable Long id) {
        List<Question> questions = familyRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "family"))
                .getQuestions();
        log.info("Recuperadas preguntas de la familia " + id);
        return CollectionModel.of(
                questions.stream().map(questionListAssembler::toModel).collect(Collectors.toList()),
                linkTo(methodOn(FamiliesController.class).one(id)).slash("questions").withSelfRel()
        );
    }

    @GetMapping("{id}/users")
    public CollectionModel<UserListModel> users(@PathVariable Long id) {
        List<User> users = userRepository.findDistinctByQuestionsFamilyId(id);

        log.info("Recuperados usuarios de la familia " + id);
        return CollectionModel.of(
                users.stream().map(userListAssembler::toModel).collect(Collectors.toList()),
                linkTo(methodOn(FamiliesController.class).one(id)).slash("users").withSelfRel()
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel<FamilyModel>> add(@Valid @RequestBody FamilyPostModel familyPostModel) {
        FamilyImpl family = familyAssembler.toEntity(familyPostModel);
        FamilyImpl savedFamily = familyRepository.save(family);
        EntityModel<FamilyModel> familyEntityModel = familyAssembler.toModel(savedFamily);

        log.info("Añadida familia" + family);
        return ResponseEntity.created(familyEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(familyEntityModel);
    }

    @PutMapping("/{id}")
    public EntityModel<FamilyModel> edit(@Valid @RequestBody FamilyModel model, @PathVariable Long id) {
        FamilyImpl family = familyRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "familia"));

        family.setStatement(model.getStatement());
        FamilyImpl updatedFamily = familyRepository.save(family);

        log.info("Actualizada " + family);
        return familyAssembler.toModel(updatedFamily);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Borrada familia " + id);
        familyRepository.deleteById(id);
    }
}

