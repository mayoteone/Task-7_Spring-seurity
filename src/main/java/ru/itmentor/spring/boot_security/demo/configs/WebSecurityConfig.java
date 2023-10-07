package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.service.PersonService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final PersonService personDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, PersonService personDetailsService) {
        this.successUserHandler = successUserHandler;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()//Включаем Для Postman

                .authorizeRequests()// все запросы на сервер будут проходить через авторизацию, которая настроена ниже
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/index", "/hello").permitAll() // не аутентифицированный пользователь может прейти на эту страницу
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .successHandler(successUserHandler) // выключаем для Postman
                .permitAll()

                //.and().httpBasic() //для базовой авторизации в Postman

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

//                .logout()
//                .permitAll()
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login");
    }

    // Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}