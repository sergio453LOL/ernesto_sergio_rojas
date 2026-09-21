package com.tuckersoft.branchengine.user;

import com.tuckersoft.branchengine.user.dto.RoleUpdateRequest;
import com.tuckersoft.branchengine.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> actual() {
        return ResponseEntity.ok(userService.actual());
    }

    /** Solo ROLE_ADMIN: la regla vive en el SecurityFilterChain. */
    @GetMapping
    public ResponseEntity<List<UserResponse>> listar() {
        return ResponseEntity.ok(userService.listar());
    }

    /** Solo ROLE_ADMIN: la regla vive en el SecurityFilterChain. */
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponse> cambiarRol(@PathVariable Long id,
                                                   @Valid @RequestBody RoleUpdateRequest peticion) {
        return ResponseEntity.ok(userService.cambiarRol(id, peticion));
    }
}
