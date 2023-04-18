package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.FamiliesController;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.models.family.FamilyModel;
import es.mdef.gestionpedidos.models.family.FamilyPostModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FamilyAssembler implements RepresentationModelAssembler<FamilyImpl, EntityModel<FamilyModel>> {
    @Override
    public @NonNull EntityModel<FamilyModel> toModel(@NonNull FamilyImpl entity) {
        FamilyModel familyModel = new FamilyModel();
        familyModel.setStatement(entity.getStatement());
        familyModel.setLength(entity.getLength());

        EntityModel<FamilyModel> model = EntityModel.of(familyModel);
        model.add(
                linkTo(methodOn(FamiliesController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FamiliesController.class).questions(entity.getId())).withRel("questions"),
                linkTo(methodOn(FamiliesController.class).users(entity.getId())).withRel("users")
        );
        return model;
    }

    public FamilyImpl toEntity(FamilyPostModel model) {
        FamilyImpl family = new FamilyImpl();
        family.setStatement(model.getStatement());
        return family;
    }
}
