package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.GestionPedidosApplication;
import es.mdef.gestionpedidos.assemblers.QuestionAssembler;
import es.mdef.gestionpedidos.assemblers.QuestionListAssembler;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.question.QuestionListModel;
import es.mdef.gestionpedidos.models.question.QuestionPostModel;
import es.mdef.gestionpedidos.repositories.QuestionRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionsController {
    private final QuestionRepository questionRepository;
    private final QuestionAssembler assembler;
    private final QuestionListAssembler listAssembler;
    private final Logger log;
    
    public QuestionsController(QuestionRepository questionRepository, QuestionAssembler assembler, QuestionListAssembler listAssembler) {
        this.questionRepository = questionRepository;
        this.assembler = assembler;
        this.listAssembler = listAssembler;
        this.log = GestionPedidosApplication.log;
    }

    @GetMapping("{id}")
    public EntityModel<Question> one(@PathVariable Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
        log.info("Recuperado " + question);
        return assembler.toModel(question);
    }

    @GetMapping
    public CollectionModel<QuestionListModel> all() {
        log.info("Recuperadas preguntas");
        return listAssembler.toCollection(questionRepository.findAll());
    }

    @PostMapping
    public EntityModel<Question> add(@Valid @RequestBody QuestionPostModel model) {
        Question question = questionRepository.save(assembler.toEntity(model));
        log.info("Añadida " + question);
        return assembler.toModel(question);
    }

    @PutMapping("{id}")
    public EntityModel<Question> edit(@Valid @RequestBody QuestionPostModel model, @PathVariable Long id) {
        Question question = questionRepository.findById(id).map(q -> {
                    q.setStatement(model.getStatement());
                    q.setUser(model.getUser());
                    q.setFamily(model.getFamily());

                    return questionRepository.save(q);
                })
                .orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
        log.info("Actualizado " + question);
        return assembler.toModel(question);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Borrada pregunta " + id);
        questionRepository.deleteById(id);
    }
}
