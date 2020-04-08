package org.learn.springboot.netflixzuulapigetwayserver.jwt.security;

//import javax.servlet.http.HttpServletResponse;

//import org.h2.util.json.JSONObject;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.dao.UserRepository;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.util.CustomAuthenticationProvider;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.util.JwtTokenAuthorizationOncePerRequestFilter;
import org.learn.springboot.netflixzuulapigetwayserver.jwt.security.util.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;
    
    @Autowired
    private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;
    
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    
    @Autowired
	private UserRepository userRepository;
    
    @Value("${jwt.get.token.uri}")
    private String authenticationPath;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth .userDetailsService(jwtUserDetailsService)
		 * .passwordEncoder(passwordEncoderBean());
		 */
    	auth.authenticationProvider(authProvider());
    }

    @Bean
	public DaoAuthenticationProvider authProvider() {
		final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(userRepository,
				jwtUserDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
    
    @Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}
	/*
	 * @Bean public PasswordEncoder passwordEncoderBean() { return new
	 * BCryptPasswordEncoder(); }
	 */

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		//httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
	
		httpSecurity
		.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		/*.antMatchers(HttpMethod.DELETE, "/currency-conversion-service/currency-converter-exchange-value/**").hasAnyRole("ADMIN")
		.and().exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            //response.setContentType("application/json;charset=UTF-8");
            //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Need a root permission to accress this URL");
            
        }).and()*/
		//.and().authorizeRequests()
		.anyRequest().authenticated();
        
		
		httpSecurity
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
			
		
		httpSecurity
        .headers()
        .frameOptions().sameOrigin()  //H2 Console Needs this setting
        .cacheControl(); //disable caching
		        
	}
	
	@Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
            .ignoring()
            .antMatchers(
                HttpMethod.POST,
                authenticationPath
            )
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .and()
            .ignoring()
            .antMatchers(
                HttpMethod.GET,
                "/" //Other Stuff You want to Ignore
            )
            .and()
            .ignoring()
            .antMatchers("/currency-exchange-service/**/**")
            .and()
            .ignoring()
            .antMatchers("/h2-console/**/**");//Should not be in Production!
    }

}
