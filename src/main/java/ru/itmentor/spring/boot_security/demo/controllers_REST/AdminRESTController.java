package ru.itmentor.spring.boot_security.demo.controllers_REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/admin/REST")
public class AdminRESTController {

    private final PersonService personService;

    private final RoleService roleService;

    @Autowired
    public AdminRESTController(PersonService personService, RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPeopleById(@PathVariable("id") int id) {
        return personService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        personService.save(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        personService.update(person, id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
