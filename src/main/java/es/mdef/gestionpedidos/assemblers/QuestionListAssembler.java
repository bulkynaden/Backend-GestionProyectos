package es.mdef.gestionpedidos.assemblers;

import es.mdef.gestionpedidos.controllers.QuestionsController;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.models.question.QuestionListModel;
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
public class QuestionListAssembler implements RepresentationModelAssembler<Question, QuestionListModel> {
    @Override
    public @NonNull QuestionListModel toModel(@NonNull Question entity) {
        QuestionListModel model = new QuestionListModel();
        model.setStatement(entity.getStatement());
        model.add(
                linkTo(methodOn(QuestionsController.class).one(entity.getId())).withSelfRel()
        );
        return model;
    }

    public CollectionModel<QuestionListModel> toCollection(Iterable<? extends Question> entities) {
        List<QuestionListModel> questionListModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(questionListModels,
                linkTo(methodOn(QuestionsController.class).all()).withSelfRel());
    }
}