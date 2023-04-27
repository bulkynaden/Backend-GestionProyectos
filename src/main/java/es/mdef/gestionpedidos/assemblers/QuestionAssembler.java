package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.FamiliesController;
import es.mdef.gestionpedidos.controllers.QuestionsController;
import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.models.question.QuestionPostModel;
import es.mdef.gestionpedidos.repositories.FamilyRepository;
import es.mdef.gestionpedidos.repositories.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionAssembler implements RepresentationModelAssembler<Question, EntityModel<Question>> {
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public QuestionAssembler(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public @NonNull EntityModel<Question> toModel(@NonNull Question entity) {
        EntityModel<Question> model = EntityModel.of(entity);

        model.add(
                linkTo(methodOn(QuestionsController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FamiliesController.class).one(entity.getFamily().getId())).withRel("family"),
                linkTo(methodOn(UsersController.class).one(entity.getUser().getId())).withRel("user")
        );
        return model;
    }

    public Question toEntity(QuestionPostModel model) {
        Question question = new Question();
        question.setStatement(model.getStatement());
        question.setFamily(model.getFamily());
        question.setUser(model.getUser());

        return question;
    }
}
