package com.tuckersoft.branchengine.user;

import com.tuckersoft.branchengine.common.BadRequestException;
import com.tuckersoft.branchengine.common.ConflictException;
import com.tuckersoft.branchengine.common.NotFoundException;
import com.tuckersoft.branchengine.common.UnauthorizedException;
import com.tuckersoft.branchengine.security.CurrentUser;
import com.tuckersoft.branchengine.security.JwtService;
import com.tuckersoft.branchengine.user.dto.AuthResponse;
import com.tuckersoft.branchengine.user.dto.LoginRequest;
import com.tuckersoft.branchengine.user.dto.RegisterRequest;
import com.tuckersoft.branchengine.user.dto.RoleUpdateRequest;
import com.tuckersoft.branchengine.user.dto.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static final Set<String> ROLES_VALIDOS = Set.of(User.ROLE_USER, User.ROLE_ADMIN);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CurrentUser currentUser;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.currentUser = currentUser;
    }

    @Transactional
    public AuthResponse registrar(RegisterRequest peticion) {
        if (userRepository.existsByEmail(peticion.email())) {
            throw new ConflictException("Ya existe una cuenta con el email " + peticion.email());
        }

        User usuario = new User();
        usuario.setEmail(peticion.email());
        usuario.setPassword(passwordEncoder.encode(peticion.password()));
        usuario.setDisplayName(peticion.displayName());
        // El rol nunca se toma del request: quien se registra siempre es ROLE_USER.
        usuario.setRole(User.ROLE_USER);
        usuario.setCreatedAt(Instant.now());

        return autenticar(userRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest peticion) {
        // Mismo 401 para email inexistente y contrasena incorrecta: no se revela cual fallo.
        User usuario = userRepository.findByEmail(peticion.email())
                .orElseThrow(() -> new UnauthorizedException("Credenciales invalidas"));

        if (!passwordEncoder.matches(peticion.password(), usuario.getPassword())) {
            throw new UnauthorizedException("Credenciales invalidas");
        }

        return autenticar(usuario);
    }

    @Transactional(readOnly = true)
    public UserResponse actual() {
        return aDto(currentUser.get());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> listar() {
        return userRepository.findAllByOrderByIdAsc().stream().map(this::aDto).toList();
    }

    @Transactional
    public UserResponse cambiarRol(Long id, RoleUpdateRequest peticion) {
        String nuevoRol = peticion.role() == null ? null : peticion.role().trim().toUpperCase();
        if (!ROLES_VALIDOS.contains(nuevoRol)) {
            throw new BadRequestException("El role debe ser ROLE_USER o ROLE_ADMIN");
        }

        User objetivo = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el usuario con id " + id));

        if (objetivo.getId().equals(currentUser.get().getId())) {
            throw new BadRequestException("Un administrador no puede cambiar su propio rol");
        }

        objetivo.setRole(nuevoRol);
        return aDto(userRepository.save(objetivo));
    }

    private AuthResponse autenticar(User usuario) {
        return new AuthResponse(
                jwtService.generarToken(usuario.getEmail()),
                "Bearer",
                usuario.getEmail(),
                usuario.getDisplayName(),
                usuario.getRole());
    }

    private UserResponse aDto(User usuario) {
        return new UserResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getDisplayName(),
                usuario.getRole(),
                usuario.getCreatedAt());
    }
}
