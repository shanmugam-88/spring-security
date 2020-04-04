package org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao;


import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

}
