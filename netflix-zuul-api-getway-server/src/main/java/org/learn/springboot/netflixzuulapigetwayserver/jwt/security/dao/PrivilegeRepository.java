package org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao;


import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    void delete(Privilege privilege);

}
