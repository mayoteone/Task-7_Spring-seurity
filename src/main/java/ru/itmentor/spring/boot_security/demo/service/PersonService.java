package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.PeopleRepository;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.security.PersonDetails;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    private final RoleService roleService;


    @Autowired
    public PersonService(PeopleRepository peopleRepository, RoleService roleService) {
        this.peopleRepository = peopleRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }


    public List<Person> findAll() {
        return peopleRepository.findAll();
    }


    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public boolean save(Person person) {
        Role personRole = roleService.findByRoleName("ROLE_USER");
        person.setRoles(new HashSet<>(Set.of(personRole)));

        peopleRepository.save(person);
        return true;

    }

    @Transactional
    public void update(Person updatePerson, int id) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
