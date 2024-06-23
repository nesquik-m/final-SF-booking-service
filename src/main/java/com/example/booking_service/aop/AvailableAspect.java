package com.example.booking_service.aop;

import com.example.booking_service.exception.AccessDeniedException;
import com.example.booking_service.security.AppUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AvailableAspect {

    @Before("@annotation(AvailableAction)")
    public void checkBefore(JoinPoint joinPoint) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userId  = pathVariables.get("id");
        var user = (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getAuthorities().size() == 1 &&
                user.getAuthorities().toString().contains("ROLE_USER") &&
                !user.getId().equals(userId)) {
            throw new AccessDeniedException("Отказано в доступе! Вы не являетесь владельцем данного профиля!");
        }
    }

}
