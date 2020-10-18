package com.cognixia.jump.configuration;

import com.cognixia.jump.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for Security for the Users of Restaurant Reviews API.
 * @author David Morales and Lori White
 * @version v3 (08/29/2020)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    MyUserDetailService userDetailsService;
    
    /**
     * Configures the Authentication with the custom user detail service.
     * @author David Morales
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    /**
     * Retrieves the password encoder.
     * @author David Morales
     * @return PasswordEncoder - the instance of the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    /**
     * Configures the Http Security for all API requests.
     * specify which users can access which api's
     * @author David Morales
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/api/update/user").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/add/user").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/delete/user/{firstName}/firstName/{lastName}/lastName").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/user/role").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/user/userName").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/patch/user/password").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/enrollees").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/dependents/{firstName}/firstName/{lastName}/lastName").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/add/enrollee").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/api/patch/modify/enrollee/dependents").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/api/patch/enrollee/status").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/api/patch/enrollee/phoneNumber").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/delete/enrollee/{firstName}/firstName/{lastName}/lastName").hasRole("ADMIN")
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
				.and().formLogin().permitAll()
				.and().httpBasic()
				.and().logout().permitAll();
    }
}
