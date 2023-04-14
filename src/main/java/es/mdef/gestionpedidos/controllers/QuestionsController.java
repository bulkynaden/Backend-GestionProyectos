package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.GestionPedidosApplication;
import es.mdef.gestionpedidos.assemblers.QuestionAssembler;
import es.mdef.gestionpedidos.assemblers.QuestionListAssembler;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.QuestionListModel;
import es.mdef.gestionpedidos.models.QuestionModel;
import es.mdef.gestionpedidos.models.QuestionPostModel;
import es.mdef.gestionpedidos.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionsController {
    private final QuestionRepository repositorio;
    private final QuestionAssembler assembler;
    private final QuestionListAssembler listAssembler;
    private final Logger log;

    public QuestionsController(QuestionRepository repositorio, QuestionAssembler assembler, QuestionListAssembler listAssembler) {
        this.repositorio = repositorio;
        this.assembler = assembler;
        this.listAssembler = listAssembler;
        this.log = GestionPedidosApplication.log;
    }

    @PostMapping
    public EntityModel<Question> add(@RequestBody QuestionPostModel model) {
        Question question = repositorio.save(assembler.toEntity(model));
        log.info("Añadida " + question);
        return assembler.toModel(question);
    }

    @GetMapping("{id}")
    public EntityModel<Question> one(@PathVariable Long id) {
        Question question = repositorio.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
        log.info("Recuperado " + question);
        return assembler.toModel(question);
    }

    @GetMapping
    public CollectionModel<QuestionListModel> all() {
        return listAssembler.toCollection(repositorio.findAll());
    }

    @PutMapping("{id}")
    public EntityModel<Question> edit(@PathVariable Long id, @RequestBody QuestionModel model) {
        Question question = repositorio.findById(id).map(q -> {
                    q.setStatement(model.getStatement());
                    q.setUser(model.getUser());
                    return repositorio.save(q);
                })
                .orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
        log.info("Actualizado " + question);
        return assembler.toModel(question);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Borrada pregunta " + id);
        repositorio.deleteById(id);
    }
}
