package com.tuckersoft.branchengine.config;

import com.tuckersoft.branchengine.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Crea al administrador al arrancar.
 *
 * TODO [PARTE 1 - SEGURIDAD]: implementar run().
 *
 * Que tiene que hacer:
 *  - Si ya existe un usuario con ese email, no hacer nada.
 *  - Si no existe, crearlo con displayName = nombre, role = ROLE_ADMIN,
 *    createdAt = ahora y la contrasena codificada con BCrypt (passwordEncoder).
 *    Nunca en texto plano: los autotests entran con colin@tuckersoft.co.uk y
 *    colin1984, y si la contrasena no esta codificada el login falla.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String nombre;
    private final String email;
    private final String password;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${app.admin.display-name}") String nombre,
                           @Value("${app.admin.email}") String email,
                           @Value("${app.admin.password}") String password) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(String... args) {
        log.warn("TODO [PARTE 1]: el administrador {} todavia no se crea al arrancar", email);
    }
}
