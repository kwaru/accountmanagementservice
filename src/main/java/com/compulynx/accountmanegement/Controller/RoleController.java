package com.compulynx.accountmanegement.Controller;

import com.compulynx.accountmanegement.Entity.Role;
import com.compulynx.accountmanegement.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody Role role){

      return new ResponseEntity<>(roleRepository.save(role), HttpStatus.CREATED);
    }
}
