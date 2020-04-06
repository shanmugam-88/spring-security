package org.learn.springboot.netflixzuulapigetwayserver.jwt.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dto.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtTokenResponse implements Serializable {

  private static final long serialVersionUID = 8317676219297719109L;

  private final String token;
  private final String firstName;
  private final String lastName;
  private final List<String> roles ;
  private final String userName;
  
  //private final UserDetails user;

    public JwtTokenResponse(String token,UserDetails user) {
        this.token = token;
        if(user instanceof User) {
        	User user2 = (User) user;
        	this.firstName = user2.getFirstName();
        	this.lastName = user2.getLastName();
        	this.roles = new ArrayList<String>();
        	user2.getRoles().forEach(role->{roles.add(role.getName());});
        	this.userName = ((User) user).getEmail();
        } else {
        	this.firstName = null;
        	this.lastName = null;
        	roles = new ArrayList<String>();
        	this.userName = null;
        }
        //this.user=user;
    }

    public String getToken() {
        return this.token;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getUserName() {
		return userName;
	}

	
    
    
}