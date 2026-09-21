package com.tuckersoft.branchengine.playthrough;

import com.tuckersoft.branchengine.decision.DecisionRepository;
import com.tuckersoft.branchengine.node.StoryNodeRepository;
import com.tuckersoft.branchengine.playthrough.dto.PathResponse;
import com.tuckersoft.branchengine.playthrough.dto.PlaythroughRequest;
import com.tuckersoft.branchengine.playthrough.dto.PlaythroughResponse;
import com.tuckersoft.branchengine.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Las partidas de prueba.
 *
 * TODO [PARTE 2 - PARTIDAS]: implementar los metodos.
 *
 * crear(), los 7 pasos del enunciado EN ESTE ORDEN:
 *  1. El dueno sale de currentUser.get(), nunca del request body.
 *  2. Buscar el StoryNode por startNodeCode -> 404 si no existe.
 *  3. playerTag repetido -> 409.
 *  4. Nodo lleno (currentBranches >= branchCapacity) -> 400.
 *  5. lucidity = 100, controlLevel = 0, status = ACTIVA, endingCode = null,
 *     startNodeCode = el codigo del nodo actual.
 *  6. currentBranches + 1 y guardar el nodo.
 *  7. Guardar la partida.
 *
 * listar(): un ROLE_USER ve solo las suyas (filtrando en el repositorio, no en
 * memoria); un ROLE_ADMIN las ve todas. Array simple, por createdAt descendente.
 *
 * obtener() y recorrido(): primero 404 si la partida no existe, y despues 403
 * si es de otro analista. El administrador SI puede leer partidas ajenas.
 *
 * recorrido(): solo las decisiones con resolvedNodeCode no nulo, ordenadas por
 * createdAt ascendente, con order empezando en 1. fromNodeCode es el nodo de
 * origen de la decision y toNodeCode su resolvedNodeCode.
 */
@Service
public class PlaythroughService {

    private final PlaythroughRepository playthroughRepository;
    private final StoryNodeRepository storyNodeRepository;
    private final DecisionRepository decisionRepository;
    private final CurrentUser currentUser;

    public PlaythroughService(PlaythroughRepository playthroughRepository,
                              StoryNodeRepository storyNodeRepository,
                              DecisionRepository decisionRepository,
                              CurrentUser currentUser) {
        this.playthroughRepository = playthroughRepository;
        this.storyNodeRepository = storyNodeRepository;
        this.decisionRepository = decisionRepository;
        this.currentUser = currentUser;
    }

    @Transactional
    public PlaythroughResponse crear(PlaythroughRequest peticion) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: abrir la partida con los 7 pasos");
    }

    @Transactional(readOnly = true)
    public List<PlaythroughResponse> listar() {
        throw new UnsupportedOperationException("TODO [PARTE 2]: listar segun el rol del token");
    }

    @Transactional(readOnly = true)
    public PlaythroughResponse obtener(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: devolver la partida (404 / 403)");
    }

    @Transactional(readOnly = true)
    public PathResponse recorrido(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: armar el recorrido de la partida");
    }

    public PlaythroughResponse aDto(Playthrough partida) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: mapear Playthrough a su DTO");
    }
}
