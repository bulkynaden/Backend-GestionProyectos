package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.models.user.UserListModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserListAssembler implements RepresentationModelAssembler<User, UserListModel> {
    @Override
    public @NonNull UserListModel toModel(@NonNull User entity) {
        UserListModel model = new UserListModel();

        model.setName(entity.getName());
        model.setRole(entity.getRole());
        model.add(
                linkTo(methodOn(UsersController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public CollectionModel<UserListModel> toCollection(Iterable<? extends User> entities) {
        List<UserListModel> userListModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(userListModels,
                linkTo(methodOn(UsersController.class).all()).withSelfRel());
    }
}
