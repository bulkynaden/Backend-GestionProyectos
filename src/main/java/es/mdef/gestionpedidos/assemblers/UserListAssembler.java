package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.models.UserListModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserListAssembler implements RepresentationModelAssembler<User, UserListModel> {
    @Override
    public UserListModel toModel(User entity) {
        UserListModel model = new UserListModel();
        model.setName(entity.getName());
        model.add(
                linkTo(methodOn(UsersController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public CollectionModel<UserListModel> toCollection(Iterable<? extends User> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
