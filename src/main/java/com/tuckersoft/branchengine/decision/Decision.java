package com.tuckersoft.branchengine.decision;

import com.tuckersoft.branchengine.node.StoryNode;
import com.tuckersoft.branchengine.playthrough.Playthrough;
import com.tuckersoft.branchengine.realitylog.RealityLog;
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

/** Lo que el jugador decidio hacer y hacia donde lo mando el motor. */
@Entity
@Table(name = "decision")
@Getter
@Setter
public class Decision {

    public static final String REGISTRADA = "REGISTRADA";
    public static final String PROCESANDO = "PROCESANDO";
    public static final String ESTABILIZADA = "ESTABILIZADA";
    public static final String ERROR = "ERROR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "playthrough_id", nullable = false)
    private Playthrough playthrough;

    /** Nodo de origen: el currentNode de la partida al momento de decidir. */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "node_id", nullable = false)
    private StoryNode node;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String rawInput;

    @Column(nullable = false)
    private String branchType;

    @Column(nullable = false)
    private String impactLevel;

    @Column(nullable = false)
    private String handlerUnit;

    @Column(nullable = false)
    private String outcomeCode;

    private String resolvedNodeCode;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "decision")
    private List<RealityLog> realityLogs = new ArrayList<>();
}
