package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.FamiliesController;
import es.mdef.gestionpedidos.controllers.QuestionsController;
import es.mdef.gestionpedidos.controllers.UsersController;
import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.models.QuestionModel;
import es.mdef.gestionpedidos.models.QuestionPostModel;
import es.mdef.gestionpedidos.repositories.FamilyRepository;
import es.mdef.gestionpedidos.repositories.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public EntityModel<Question> toModel(Question entity) {
        EntityModel<Question> model = EntityModel.of(entity);
        model.add(
                linkTo(methodOn(QuestionsController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FamiliesController.class).one(entity.getFamily().getId())).withRel("family"),
                linkTo(methodOn(UsersController.class).one(entity.getUser().getId())).withRel("user")
        );
        return model;
    }

    public Question toEntity(QuestionModel model) {
        Question question = new Question();
        question.setStatement(model.getStatement());

        Optional<User> userOptional = userRepository.findById(model.getUser().getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            question.setUser(user);
        }

        Optional<FamilyImpl> familyOptional = familyRepository.findById(model.getFamily().getId());
        if (familyOptional.isPresent()) {
            FamilyImpl family = familyOptional.get();
            question.setFamily(family);
        }
        return question;
    }

    public Question toEntity(QuestionPostModel model) {
        Question question = new Question();
        question.setStatement(model.getStatement());

        Optional<User> userOptional = userRepository.findById(model.getUser().getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            question.setUser(user);
        }

        Optional<FamilyImpl> familyOptional = familyRepository.findById(model.getFamily().getId());
        if (familyOptional.isPresent()) {
            FamilyImpl family = familyOptional.get();
            question.setFamily(family);
        }
        return question;
    }
}
