package org.learn.springboot.netflixzuulapigetwayserver.jwt.security.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao.PrivilegeRepository;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao.RoleRepository;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao.UserRepository;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.Privilege;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.Role;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    //private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (privilegeRepository.count()>1) {
            return;
        }

        // == create initial privileges
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        // == create initial roles
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        List<Privilege> rolePrivileges = Arrays.asList(readPrivilege);
        createRoleIfNotFound("ROLE_USER", rolePrivileges);
        List<Privilege> rootPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);
        createRoleIfNotFound("ROLE_ROOT", rootPrivileges);
        
        Role rootRole = roleRepository.findByName("ROLE_ROOT");
        User root = new User();
        root.setFirstName("Root");
        root.setLastName("Root");
        root.setEmail("root@test.com");
        root.setPassword(passwordEncoder.encode("pass"));
        root.setRoles(Arrays.asList(rootRole));
        root.setEnabled(true);
        userRepository. save(root);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setEmail("admin@test.com");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository. save(user);

        Role basicRole = roleRepository.findByName("ROLE_USER");
        User basicUser = new User();
        basicUser.setFirstName("User");
        basicUser.setLastName("User");
        basicUser.setEmail("user@test.com");
        basicUser.setPassword(passwordEncoder.encode("pass"));
        basicUser.setRoles(Arrays.asList(basicRole));
        basicUser.setEnabled(true);
        userRepository.save(basicUser);

        //alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}