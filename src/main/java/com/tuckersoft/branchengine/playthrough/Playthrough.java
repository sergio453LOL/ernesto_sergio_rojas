package com.tuckersoft.branchengine.playthrough;

import com.tuckersoft.branchengine.decision.Decision;
import com.tuckersoft.branchengine.node.StoryNode;
import com.tuckersoft.branchengine.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** Una partida de prueba. Pertenece al usuario que la creo. */
@Entity
@Table(name = "playthrough")
@Getter
@Setter
public class Playthrough {

    public static final String ACTIVA = "ACTIVA";
    public static final String FINALIZADA = "FINALIZADA";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String playerTag;

    /** El dueno. Sale del token, nunca del request. */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String startNodeCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "current_node_id", nullable = false)
    private StoryNode currentNode;

    @Column(nullable = false)
    private Integer lucidity = 100;

    @Column(nullable = false)
    private Integer controlLevel = 0;

    @Column(nullable = false)
    private String status = ACTIVA;

    private String endingCode;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "playthrough")
    private List<Decision> decisions = new ArrayList<>();

    public boolean estaActiva() {
        return ACTIVA.equals(status);
    }
}
