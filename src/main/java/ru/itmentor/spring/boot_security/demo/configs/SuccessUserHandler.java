package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //System.out.println(roles.toString()); лог на проверку ролей у пользователя

        if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
            System.out.println("у пользователя роль ADMIN");
        }
        else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/person");
            System.out.println("у пользователя роль USER");
        } else {
            httpServletResponse.sendRedirect("/");
            System.out.println("у пользователя другая роль");
        }
    }
}