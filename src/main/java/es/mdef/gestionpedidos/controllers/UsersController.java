package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.GestionPedidosApplication;
import es.mdef.gestionpedidos.assemblers.QuestionAssembler;
import es.mdef.gestionpedidos.assemblers.QuestionListAssembler;
import es.mdef.gestionpedidos.assemblers.UserAssembler;
import es.mdef.gestionpedidos.assemblers.UserListAssembler;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.*;
import es.mdef.gestionpedidos.repositories.QuestionRepository;
import es.mdef.gestionpedidos.repositories.UserRepository;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserRepository repository;
    private final QuestionRepository questionRepository;
    private final UserAssembler assembler;
    private final QuestionAssembler questionAssembler;
    private final UserListAssembler listAssembler;
    private final QuestionListAssembler questionListAssembler;
    private final Logger log;

    public UsersController(UserRepository repositorio,
                           QuestionRepository questionRepository,
                           UserAssembler assembler,
                           QuestionAssembler questionAssembler,
                           UserListAssembler listAssembler,
                           QuestionListAssembler questionListAssembler) {
        this.repository = repositorio;
        this.questionRepository = questionRepository;
        this.assembler = assembler;
        this.questionAssembler = questionAssembler;
        this.listAssembler = listAssembler;
        this.questionListAssembler = questionListAssembler;
        this.log = GestionPedidosApplication.log;
    }

    @PostMapping
    public EntityModel<User> add(@RequestBody UserPostModel model) {
        User user = repository.save(assembler.toEntity(model));
        log.info("Añadido " + user);
        return assembler.toModel(user);
    }

    @PostMapping("{id}/add-question")
    public EntityModel<Question> addQuestion(@RequestBody QuestionModel model, @PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        model.setUser(user);
        Question question = questionRepository.save(questionAssembler.toEntity(model));
        log.info("Añadida " + question);
        return questionAssembler.toModel(question);
    }

    @GetMapping("{id}")
    public EntityModel<User> one(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Recuperado " + user);
        return assembler.toModel(user);
    }

    @GetMapping
    public CollectionModel<UserListModel> all() {
        return listAssembler.toCollection(repository.findAll());
    }

    @GetMapping("{id}/questions")
    public CollectionModel<QuestionListModel> allQuestions(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        return questionListAssembler.toCollection(questionRepository.findQuestionByUser(user));
    }

    @PutMapping("{id}")
    public EntityModel<User> edit(@PathVariable Long id, @RequestBody UserPutModel model) {
        User user = repository.findById(id).map(u -> {
                    u.setName(model.getName());
                    u.setUsername(model.getUsername());
                    return repository.save(u);
                })
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Actualizado " + user);
        return assembler.toModel(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Borrado usuario " + id);
        repository.deleteById(id);
    }
}

