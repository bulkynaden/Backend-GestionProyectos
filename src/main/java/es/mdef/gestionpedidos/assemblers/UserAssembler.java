package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.models.UserModel;
import es.mdef.gestionpedidos.models.UserPostModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User entity) {
        EntityModel<User> model = EntityModel.of(entity);
        model.add(
                linkTo(methodOn(UsersController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).questions(entity.getId())).withRel("questions")
        );
        return model;
    }

    public User toEntity(UserModel model) {
        User user = new User();
        user.setName(model.getName());
        user.setUsername(model.getUsername());
        user.setPassword(model.getPassword());
        return user;
    }

    public User toEntity(UserPostModel model) {
        User user = new User();
        user.setName(model.getName());
        user.setUsername(model.getUsername());
        user.setPassword(model.getPassword());
        return user;
    }
}
