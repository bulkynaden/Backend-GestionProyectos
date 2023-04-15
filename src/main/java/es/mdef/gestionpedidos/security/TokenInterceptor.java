package es.mdef.gestionpedidos.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return true;
        }

        String newToken = JwtTokenService.updateExpirationDateToken(token);
        response.setHeader("Authorization", newToken);
        response.setHeader("Access-control-expose-headers", "Authorization");

        return true;
    }
}
