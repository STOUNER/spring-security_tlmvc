package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.LoginSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/user").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/user").access("hasAnyRole('ROLE_USER')")
                .antMatchers("/admin").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/admin/edit/{id}").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/admin/update/{id}").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/admin/delete/{id}").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/admin/addUser").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/admin/saveUser").access("hasAnyRole('ROLE_ADMIN')")
                .and().formLogin().loginPage("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/appLogout")
                .permitAll();

    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}