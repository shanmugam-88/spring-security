package org.learn.dao;

import org.learn.dto.login.User;

public interface IUserService {

    User findUserByEmail(String email);

}
