package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.QuestionsController;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.models.QuestionModel;
import es.mdef.gestionpedidos.models.QuestionPostModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionAssembler implements RepresentationModelAssembler<Question, EntityModel<Question>> {
    @Override
    public EntityModel<Question> toModel(Question entity) {
        EntityModel<Question> model = EntityModel.of(entity);
        model.add(
                linkTo(methodOn(QuestionsController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public Question toEntity(QuestionModel model) {
        Question question = new Question();
        question.setStatement(model.getStatement());
        question.setUser(model.getUser());
        question.setFamily(model.getFamily());
        return question;
    }

    public Question toEntity(QuestionPostModel model) {
        Question question = new Question();
        question.setStatement(model.getStatement());
        question.setUser(model.getUser());
        question.setFamily(model.getFamily());
        return question;
    }
}
