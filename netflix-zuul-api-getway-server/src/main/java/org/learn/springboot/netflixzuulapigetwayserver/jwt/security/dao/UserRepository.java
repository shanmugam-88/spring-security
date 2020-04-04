package org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao;


import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void delete(User user);

}
