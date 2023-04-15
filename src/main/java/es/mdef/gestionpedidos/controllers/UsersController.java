package es.mdef.gestionpedidos.controllers;

import es.mdef.gestionpedidos.GestionPedidosApplication;
import es.mdef.gestionpedidos.assemblers.*;
import es.mdef.gestionpedidos.entities.Administrator;
import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.entities.User;
import es.mdef.gestionpedidos.errors.RegisterNotFoundException;
import es.mdef.gestionpedidos.models.family.FamilyListModel;
import es.mdef.gestionpedidos.models.question.QuestionListModel;
import es.mdef.gestionpedidos.models.question.QuestionModel;
import es.mdef.gestionpedidos.models.question.QuestionPostModel;
import es.mdef.gestionpedidos.models.user.*;
import es.mdef.gestionpedidos.repositories.FamilyRepository;
import es.mdef.gestionpedidos.repositories.QuestionRepository;
import es.mdef.gestionpedidos.repositories.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final FamilyRepository familyRepository;
    private final UserAssembler userAssembler;
    private final QuestionAssembler questionAssembler;
    private final UserListAssembler userListAssembler;
    private final QuestionListAssembler questionListAssembler;
    private final FamilyListAssembler familyListAssembler;
    private final Logger log;
    private final JdbcTemplate jdbcTemplate;


    public UsersController(UserRepository userRepository,
                           QuestionRepository questionRepository,
                           FamilyRepository familyRepository,
                           UserAssembler userAssembler,
                           QuestionAssembler questionAssembler,
                           UserListAssembler userListAssembler,
                           QuestionListAssembler questionListAssembler,
                           FamilyListAssembler familyListAssembler, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.familyRepository = familyRepository;
        this.userAssembler = userAssembler;
        this.questionAssembler = questionAssembler;
        this.userListAssembler = userListAssembler;
        this.questionListAssembler = questionListAssembler;
        this.familyListAssembler = familyListAssembler;
        this.jdbcTemplate = jdbcTemplate;
        this.log = GestionPedidosApplication.log;
    }

    @GetMapping("{id}")
    public EntityModel<UserModel> one(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Recuperado " + user);
        return userAssembler.toModel(user);
    }

    @GetMapping
    public CollectionModel<UserListModel> all() {
        log.info("Recuperados usuarios");
        return userListAssembler.toCollection(userRepository.findAll());
    }

    @GetMapping("{id}/questions")
    public CollectionModel<QuestionListModel> questions(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Recuperadas preguntas del usuario " + user);
        return questionListAssembler.toCollection(questionRepository.findQuestionByUser(user));
    }

    @GetMapping("{id}/families")
    public CollectionModel<FamilyListModel> families(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Recuperados familias del usuario " + user);
        return familyListAssembler.toCollection(familyRepository.findDistinctByQuestionsUser(user));
    }

    @PostMapping
    public EntityModel<UserModel> add(@RequestBody UserPostModel model) {
        model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
        User user = userRepository.save(userAssembler.toEntity(model));
        log.info("Añadido " + user);
        return userAssembler.toModel(user);
    }

    @PostMapping("{id}/add-question")
    public EntityModel<QuestionModel> addQuestion(@Valid @RequestBody QuestionPostModel model, @PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        model.setUser(user);
        Question question = questionRepository.save(questionAssembler.toEntity(model));
        log.info("Añadida " + question + " del usuario " + user);
        return questionAssembler.toModel(question);
    }

    @PutMapping("{id}")
    public EntityModel<UserModel> edit(@Valid @RequestBody UserPutModel model, @PathVariable Long id) {
        User user = userRepository.findById(id).map(u -> {
                    u.setName(model.getName());
                    u.setUsername(model.getUsername());
                    u.setAccountNonExpired(model.isAccountNonExpired());
                    u.setEnabled(model.isEnabled());
                    u.setCredentialsNonExpired(model.isCredentialsNonExpired());
                    u.setAccountNonLocked(model.isAccountNonLocked());
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Actualizado " + user);
        return userAssembler.toModel(user);
    }

    @PutMapping("{id}/edit-password")
    public EntityModel<UserModel> editPassword(@Valid @RequestBody UserChangePasswordModel model, @PathVariable Long id) {
        User user = userRepository.findById(id).map(u -> {
                    u.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
        log.info("Cambiada contraseña del usuario " + user);
        return userAssembler.toModel(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Borrado usuario " + id);
        userRepository.deleteById(id);
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable();
    }

    private void seedUsersTable() {
        String sql = "SELECT USERNAME FROM users";
        List<User> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (u.size() == 0) {
            Administrator user = new Administrator();
            user.setName("Javier Tomás Acín");
            user.setUsername("jtomaci");
            user.setPassword(new BCryptPasswordEncoder().encode("password"));
            userRepository.save(user);
            log.info("Users Seeded");
        } else {
            log.info("Users Seeding Not Required");
        }
    }
}

