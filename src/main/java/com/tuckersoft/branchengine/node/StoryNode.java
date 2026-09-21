package com.tuckersoft.branchengine.node;

import com.tuckersoft.branchengine.decision.Decision;
import com.tuckersoft.branchengine.playthrough.Playthrough;
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
 * Una escena de Bandersnatch. primaryBranchCode y glitchBranchCode son Strings,
 * no llaves foraneas: los nodos se crean en cualquier orden y pueden apuntar a
 * nodos que todavia no existen. La resolucion ocurre al decidir, no al crear.
 */
@Entity
@Table(name = "story_node")
@Getter
@Setter
public class StoryNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String nodeCode;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sceneText;

    @Column(nullable = false)
    private Integer branchCapacity;

    @Column(nullable = false)
    private Integer currentBranches = 0;

    private String primaryBranchCode;

    private String glitchBranchCode;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "currentNode")
    private List<Playthrough> playthroughs = new ArrayList<>();

    @OneToMany(mappedBy = "node")
    private List<Decision> decisions = new ArrayList<>();

    public boolean estaLleno() {
        return currentBranches >= branchCapacity;
    }
}
