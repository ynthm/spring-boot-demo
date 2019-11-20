package com.ynthm.springbootdemo;

import com.ynthm.springbootdemo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Author : Ynthm
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

//        roleRepository.save(new Role(RoleName.ROLE_USER));
//        roleRepository.save(new Role(RoleName.ROLE_ADMIN));

    }
}
