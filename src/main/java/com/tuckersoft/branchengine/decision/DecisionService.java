package com.tuckersoft.branchengine.decision;

import com.tuckersoft.branchengine.decision.dto.DecisionRequest;
import com.tuckersoft.branchengine.decision.dto.DecisionResponse;
import com.tuckersoft.branchengine.decision.dto.PageResponse;
import com.tuckersoft.branchengine.node.StoryNodeRepository;
import com.tuckersoft.branchengine.playthrough.PlaythroughRepository;
import com.tuckersoft.branchengine.realitylog.RealityLogRepository;
import com.tuckersoft.branchengine.realitylog.dto.RealityLogResponse;
import com.tuckersoft.branchengine.security.CurrentUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * El motor de ramificacion.
 *
 * TODO [PARTE 3 - DECISIONES]: implementar los metodos.
 *
 * crear(), los 9 pasos del enunciado EN ESTE ORDEN:
 *  1. Usuario del token y partida por id -> 404 si no existe. Si la partida es
 *     de otro analista -> 403, SIN excepcion para el administrador: supervisar
 *     no es jugar.
 *  2. Partida FINALIZADA -> 409. impactLevel fuera de LEVE/MODERADO/GRAVE/
 *     CRITICO -> 400.
 *  3. Clasificar el rawInput y derivar handlerUnit y outcomeCode. El nodo de
 *     origen de la decision es el currentNode de la partida.
 *  4. Si es ENTRADA_CORRUPTA: guardar con status ERROR y resolvedNodeCode null,
 *     NO tocar la partida (ni stats ni nodo ni estado), NO publicar evento, y
 *     devolver igualmente 201.
 *  5. Stats segun el impacto, acotados con Math.max/Math.min entre 0 y 100:
 *       LEVE -5/+5 · MODERADO -15/+10 · GRAVE -30/+20 · CRITICO -40/+45
 *     Nodo destino: glitchBranchCode si la rama es RUPTURA_CUARTA_PARED O el
 *     impacto es CRITICO; en cualquier otro caso primaryBranchCode. Ese codigo
 *     se guarda en resolvedNodeCode aunque ese nodo no exista.
 *     Estado de la partida, EN ESTE ORDEN:
 *       1) controlLevel >= 100            -> FINALIZADA, ENDING_PAC_SYMBOL
 *       2) lucidity <= 0                  -> FINALIZADA, ENDING_WHITE_BEAR
 *       3) el destino es null o no existe -> FINALIZADA, ENDING_NETFLIX_CUT
 *       4) ninguna de las anteriores      -> ACTIVA y currentNode = destino
 *     En 1, 2 y 3 currentNode NO cambia. updatedAt se actualiza siempre.
 *  6 y 7. Guardar la partida y la decision con status REGISTRADA.
 *  8. publisher.publishEvent(new DecisionCommittedEvent(...)) con TODO lo que el
 *     listener necesita, incluido el valor de la cabecera simulate.
 *  9. Devolver 201.
 *
 * listar(): paginado { content, totalElements, totalPages, currentPage, size }
 * con los filtros branchType, impactLevel, status y playthroughId aplicados EN
 * EL REPOSITORIO. Un ROLE_USER nunca ve decisiones de partidas ajenas.
 *
 * obtener() e informes(): 404 si no existe y 403 si la partida es de otro; el
 * administrador si puede leerlas.
 */
@Service
public class DecisionService {

    public static final String ENDING_PAC_SYMBOL = "ENDING_PAC_SYMBOL";
    public static final String ENDING_WHITE_BEAR = "ENDING_WHITE_BEAR";
    public static final String ENDING_NETFLIX_CUT = "ENDING_NETFLIX_CUT";

    /** impactLevel -> { delta de lucidity, delta de controlLevel }. */
    private static final Map<String, int[]> IMPACTOS = Map.of(
            "LEVE", new int[]{-5, 5},
            "MODERADO", new int[]{-15, 10},
            "GRAVE", new int[]{-30, 20},
            "CRITICO", new int[]{-40, 45});

    private final DecisionRepository decisionRepository;
    private final PlaythroughRepository playthroughRepository;
    private final StoryNodeRepository storyNodeRepository;
    private final RealityLogRepository realityLogRepository;
    private final BranchClassifier branchClassifier;
    private final CurrentUser currentUser;
    private final ApplicationEventPublisher publisher;

    public DecisionService(DecisionRepository decisionRepository,
                           PlaythroughRepository playthroughRepository,
                           StoryNodeRepository storyNodeRepository,
                           RealityLogRepository realityLogRepository,
                           BranchClassifier branchClassifier,
                           CurrentUser currentUser,
                           ApplicationEventPublisher publisher) {
        this.decisionRepository = decisionRepository;
        this.playthroughRepository = playthroughRepository;
        this.storyNodeRepository = storyNodeRepository;
        this.realityLogRepository = realityLogRepository;
        this.branchClassifier = branchClassifier;
        this.currentUser = currentUser;
        this.publisher = publisher;
    }

    @Transactional
    public DecisionResponse crear(DecisionRequest peticion, String simulate) {
        throw new UnsupportedOperationException("TODO [PARTE 3]: los 9 pasos del motor de ramas");
    }

    @Transactional(readOnly = true)
    public PageResponse<DecisionResponse> listar(String branchType, String impactLevel, String status,
                                                 Long playthroughId, int page, int size) {
        throw new UnsupportedOperationException("TODO [PARTE 3]: listado paginado con filtros");
    }

    @Transactional(readOnly = true)
    public DecisionResponse obtener(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 3]: devolver la decision (404 / 403)");
    }

    @Transactional(readOnly = true)
    public List<RealityLogResponse> informes(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 3]: devolver los RealityLog de la decision");
    }
}
