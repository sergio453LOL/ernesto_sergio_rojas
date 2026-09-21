package com.tuckersoft.branchengine.realitylog;

import com.tuckersoft.branchengine.decision.DecisionCommittedEvent;
import com.tuckersoft.branchengine.decision.DecisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Manda el Informe de Realidad fuera de la peticion.
 *
 * TODO [PARTE 3 - ASINCRONIA]: implementar alCommit() y sus anotaciones.
 *
 * Las tres anotaciones del metodo, que ahora faltan:
 *   @Async("branchExecutor")
 *   @Transactional(propagation = Propagation.REQUIRES_NEW)
 *   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
 * AFTER_COMMIT garantiza que la decision ya existe en PostgreSQL cuando este
 * hilo la busca, y sin REQUIRES_NEW la aplicacion ni siquiera arranca. Este
 * componente va aparte del DecisionService a proposito: Spring no aplica @Async
 * a una llamada interna.
 *
 * Que tiene que hacer, en orden:
 *  1. Buscar la decision del evento y pasarla a PROCESANDO.
 *  2. Armar el asunto y el cuerpo (RealityMailService ya los construye) y
 *     enviarlo de verdad con JavaMailSender al email del dueno de la partida.
 *  3. Si sale bien: decision ESTABILIZADA y RealityLog SENT con sentAt.
 *     Si falla: decision ERROR, RealityLog FAILED con errorMessage y sin
 *     sentAt, mas un log.error(). El fallo simulado por la cabecera
 *     X-Bandersnatch-Simulate: MAIL_FAILURE tiene que caer por el MISMO catch.
 *  4. Imprimir el [BRANCH-LOG] con el nombre del hilo y el status final:
 *     [BRANCH-LOG] Decision ID: <id> | Player: <playerTag> | Branch: <branchType>
 *     | Impact: <impactLevel> | Unit: <handlerUnit> | Node: <origen> -> <destino>
 *     | Thread: <hilo> | Status: <status>
 *     Ese hilo tiene que ser branch-worker-N, no http-nio-8080-exec-N.
 */
@Component
public class BranchNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(BranchNotificationListener.class);

    private final DecisionRepository decisionRepository;
    private final RealityLogRepository realityLogRepository;
    private final RealityMailService realityMailService;

    public BranchNotificationListener(DecisionRepository decisionRepository,
                                      RealityLogRepository realityLogRepository,
                                      RealityMailService realityMailService) {
        this.decisionRepository = decisionRepository;
        this.realityLogRepository = realityLogRepository;
        this.realityMailService = realityMailService;
    }

    public void alCommit(DecisionCommittedEvent evento) {
        log.warn("TODO [PARTE 3]: la decision #{} no se notifica todavia", evento.decisionId());
    }
}
