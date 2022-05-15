package com.med.patientsmvc.security;

import com.med.patientsmvc.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    //@Autowired //on a besoin de passwordEncoder c'est ca la methode
    //private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* in memoire authenticatin
         PasswordEncoder passwordEncoder = passwordEncoder();
        String encodedPWD = passwordEncoder.encode("1234");
        System.out.println(encodedPWD);
        auth.inMemoryAuthentication().withUser("user1").password(encodedPWD).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder.encode("1111")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("2345")).roles("USER","ADMIN");
         */

        /* jdbc authentication
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principale, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
        */

        /* user details service */
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        //http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); dans la 1er et 2eme methode ca marche
        //http.authorizeRequests().antMatchers("/user/**").hasRole("USER"); dans la 1er et 2eme methode ca marche
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }

}
