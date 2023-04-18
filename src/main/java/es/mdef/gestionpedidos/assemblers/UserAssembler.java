package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.constants.UserEnums;
import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.Administrator;
import es.mdef.gestionpedidos.entities.NotAdministrator;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.models.user.AdminModel;
import es.mdef.gestionpedidos.models.user.NotAdminModel;
import es.mdef.gestionpedidos.models.user.UserModel;
import es.mdef.gestionpedidos.models.user.UserPostModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<UserModel>> {
    @Override
    public @NonNull EntityModel<UserModel> toModel(@NonNull User entity) {
        UserModel userModel;

        if (entity instanceof Administrator) {
            AdminModel adminModel = new AdminModel();
            adminModel.setPhone(((Administrator) entity).getPhone());
            userModel = adminModel;
        } else if (entity instanceof NotAdministrator) {
            NotAdminModel notAdminModel = new NotAdminModel();
            notAdminModel.setDepartment(((NotAdministrator) entity).getDepartment());
            notAdminModel.setType(((NotAdministrator) entity).getType());
            userModel = notAdminModel;
        } else {
            userModel = new UserModel();
        }

        userModel.setName(entity.getName());
        userModel.setUsername(entity.getUsername());
        userModel.setRole(entity.getRole());
        userModel.setAccountNonExpired(entity.isAccountNonExpired());
        userModel.setEnabled(entity.isEnabled());
        userModel.setAccountNonLocked(entity.isAccountNonLocked());
        userModel.setCredentialsNonExpired(entity.isCredentialsNonExpired());

        EntityModel<UserModel> model = EntityModel.of(userModel);
        model.add(
                linkTo(methodOn(UsersController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).questions(entity.getId())).withRel("questions"),
                linkTo(methodOn(UsersController.class).families(entity.getId())).withRel("families")
        );
        return model;
    }

    public User toEntity(UserPostModel model) {
        User user = getUserFields(model.getRole(),
                model.getPhone(),
                model.getDepartment(),
                model.getType(),
                model.getName(),
                model.getUsername());
        user.setPassword(model.getPassword());
        return user;
    }

    private User getUserFields(UserEnums.Role role,
                               String phone,
                               UserEnums.Department department,
                               UserEnums.Type type,
                               String name,
                               String username) {
        User user;
        if (role == UserEnums.Role.Admin) {
            Administrator userAdmin = new Administrator();
            userAdmin.setPhone(phone);
            user = userAdmin;
        } else {
            NotAdministrator userNotAdmin = new NotAdministrator();
            userNotAdmin.setDepartment(department);
            userNotAdmin.setType(type);
            user = userNotAdmin;
        }
        user.setName(name);
        user.setUsername(username);
        return user;
    }
}
