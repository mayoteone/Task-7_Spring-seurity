package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Person;
import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();

    List<Role> findAllByIdIn(List<Long> roleIds);

    Role findByName(String name);

}