package com.tuckersoft.branchengine.user;

import com.tuckersoft.branchengine.playthrough.Playthrough;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * El analista de QA. Se registra, entra con su cuenta y es dueno de sus partidas.
 * "user" es palabra reservada en PostgreSQL, por eso la tabla se llama "users".
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    /** Siempre codificada con BCrypt. Nunca sale en un response. */
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 60)
    private String displayName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playthrough> playthroughs = new ArrayList<>();

    public boolean esAdmin() {
        return ROLE_ADMIN.equals(role);
    }
}
