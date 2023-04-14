package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.assemblers.FamilyAssembler;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.FamilyModel;
import es.mdef.gestionpedidos.models.FamilyPostModel;
import es.mdef.gestionpedidos.repositories.FamilyRepository;
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
    private final FamilyAssembler familyAssembler;
    private final Logger log;

    public FamiliesController(FamilyRepository familyRepository, FamilyAssembler familyAssembler) {
        this.familyRepository = familyRepository;
        this.familyAssembler = familyAssembler;
        this.log = LoggerFactory.getLogger(FamiliesController.class);
    }

    @GetMapping
    public CollectionModel<EntityModel<FamilyImpl>> all() {
        List<EntityModel<FamilyImpl>> families = familyRepository.findAll().stream()
                .map(familyAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(families,
                linkTo(methodOn(FamiliesController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<FamilyImpl>> create(@RequestBody FamilyPostModel familyPostModel) {
        FamilyImpl family = familyAssembler.toEntity(familyPostModel);
        FamilyImpl savedFamily = familyRepository.save(family);
        EntityModel<FamilyImpl> familyEntityModel = familyAssembler.toModel(savedFamily);

        return ResponseEntity.created(familyEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(familyEntityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<FamilyImpl> one(@PathVariable Long id) {
        FamilyImpl family = familyRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
        return familyAssembler.toModel(family);
    }

    @PutMapping("/{id}")
    public EntityModel<FamilyImpl> update(@RequestBody FamilyModel model, @PathVariable Long id) {
        FamilyImpl family = familyRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "familia"));

        family.setStatement(model.getStatement());

        FamilyImpl updatedFamily = familyRepository.save(family);
        return familyAssembler.toModel(updatedFamily);
    }
}

