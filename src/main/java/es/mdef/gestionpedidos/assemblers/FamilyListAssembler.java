package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.FamiliesController;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.models.family.FamilyListModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FamilyListAssembler implements RepresentationModelAssembler<FamilyImpl, FamilyListModel> {
    @Override
    public FamilyListModel toModel(FamilyImpl entity) {
        FamilyListModel model = new FamilyListModel();
        model.setStatement(entity.getStatement());
        model.setLength(entity.getLength());
        model.add(
                linkTo(methodOn(FamiliesController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public CollectionModel<FamilyListModel> toCollection(Iterable<? extends FamilyImpl> entities) {
        List<FamilyListModel> familyListModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(familyListModels,
                linkTo(methodOn(FamiliesController.class).all()).withSelfRel());
    }
}