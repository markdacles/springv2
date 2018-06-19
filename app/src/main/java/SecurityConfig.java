package com.exist.ecc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

    @Autowired
   public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username,password, enabled from users where username=?")
        .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
        // .passwordEncoder(new BCryptPasswordEncoder());
  }

  	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/").access("hasRole('ADMIN') or hasRole('PERS') or hasRole('ROLE') or hasRole('PROJ')")
            .antMatchers("/personnel/**").access("hasRole('PERS') or hasRole('ADMIN')")
            .antMatchers("/role/**").access("hasRole('ROLE') or hasRole('ADMIN')")
            .antMatchers("/project/**").access("hasRole('PROJ') or hasRole('ADMIN')")
            .and().formLogin().loginPage("/login")
            .usernameParameter("username").passwordParameter("password")
            .defaultSuccessUrl("/")
            .and().csrf().disable().exceptionHandling().accessDeniedPage("/forbidden");
	}
}
