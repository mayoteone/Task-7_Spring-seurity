package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public List<Role> getRolesByIds(List<Long> roleIds) {
        return roleRepository.findAllByIdIn(roleIds);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findByRoleName(String name){
         return roleRepository.findByName(name);
    }


}
