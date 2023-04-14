package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.QuestionsController;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.models.QuestionListModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionListAssembler implements RepresentationModelAssembler<Question, QuestionListModel> {
    @Override
    public QuestionListModel toModel(Question entity) {
        QuestionListModel model = new QuestionListModel();
        model.setStatement(entity.getStatement());
        model.add(
                linkTo(methodOn(QuestionsController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public CollectionModel<QuestionListModel> toCollection(Iterable<? extends Question> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}