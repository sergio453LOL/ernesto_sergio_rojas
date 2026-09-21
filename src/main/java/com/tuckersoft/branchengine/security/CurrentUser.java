package com.tuckersoft.branchengine.security;

import com.tuckersoft.branchengine.common.UnauthorizedException;
import com.tuckersoft.branchengine.user.User;
import com.tuckersoft.branchengine.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * El usuario del token. Todos los services lo usan para saber quien esta
 * pidiendo: el dueno de una partida nunca sale del request body.
 */
@Component
public class CurrentUser {

    private final UserRepository userRepository;

    public CurrentUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion == null || !autenticacion.isAuthenticated()
                || "anonymousUser".equals(autenticacion.getPrincipal())) {
            throw new UnauthorizedException("No hay un usuario autenticado en la peticion");
        }

        return userRepository.findByEmail(autenticacion.getName())
                .orElseThrow(() -> new UnauthorizedException("El usuario del token ya no existe"));
    }
}
