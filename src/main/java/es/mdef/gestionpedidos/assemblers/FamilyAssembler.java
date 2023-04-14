package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.FamiliesController;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.models.FamilyModel;
import es.mdef.gestionpedidos.models.FamilyPostModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FamilyAssembler implements RepresentationModelAssembler<FamilyImpl, EntityModel<FamilyImpl>> {
    @Override
    public EntityModel<FamilyImpl> toModel(FamilyImpl entity) {
        EntityModel<FamilyImpl> model = EntityModel.of(entity);
        model.add(
                linkTo(methodOn(FamiliesController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public FamilyImpl toEntity(FamilyModel model) {
        FamilyImpl family = new FamilyImpl();
        family.setStatement(model.getStatement());
        return family;
    }

    public FamilyImpl toEntity(FamilyPostModel model) {
        FamilyImpl family = new FamilyImpl();
        family.setStatement(model.getStatement());
        return family;
    }
}
