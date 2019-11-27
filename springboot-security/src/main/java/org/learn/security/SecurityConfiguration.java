package org.learn.security;

import org.learn.authentication.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.learn.dao.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public SecurityConfiguration() {
		super();
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LogoutSuccessHandler myLogoutSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(userRepository,
				userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/login*", "/logout*", "/protectedbynothing*", "/home*").permitAll()
            .antMatchers("/protectedbyrole").hasAnyRole("USER","ADMIN")
            .antMatchers("/protectedbyadminrole").hasAnyRole("ADMIN")
            .antMatchers("/protectedbyuserrole").hasAnyRole("USER")
            //.antMatchers("/policyhandler/policy").hasAuthority("READ_PRIVILEGE")
            .antMatchers(HttpMethod.GET, "/policyhandler/policy/**").hasAuthority("READ_PRIVILEGE")
            .antMatchers(HttpMethod.POST, "/policyhandler/policy").hasAuthority("WRITE_PRIVILEGE")
            .antMatchers(HttpMethod.PUT, "/policyhandler/policy").hasAuthority("WRITE_PRIVILEGE")
            .antMatchers(HttpMethod.DELETE, "/policyhandler/policy/**").hasAuthority("DELETE_PRIVILEGE")
            .and()
            .exceptionHandling().accessDeniedPage("/403")
            .and()
        .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .permitAll()
            .and()
        .logout()
            .logoutSuccessHandler(myLogoutSuccessHandler)
            .invalidateHttpSession(false)
            .logoutSuccessUrl("/logout.html?logSucc=true")
            .deleteCookies("JSESSIONID")
            .permitAll();
	}

}
