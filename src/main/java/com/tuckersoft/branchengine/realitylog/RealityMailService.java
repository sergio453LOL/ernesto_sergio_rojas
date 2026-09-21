package com.tuckersoft.branchengine.realitylog;

import com.tuckersoft.branchengine.decision.DecisionCommittedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/** Arma y envia el Informe de Realidad. El envio es real, con JavaMailSender. */
@Service
public class RealityMailService {

    private static final String SEPARADOR = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    private final JavaMailSender mailSender;
    private final String remitente;

    public RealityMailService(JavaMailSender mailSender,
                              @Value("${spring.mail.username:}") String usuarioSmtp) {
        this.mailSender = mailSender;
        this.remitente = (usuarioSmtp == null || usuarioSmtp.isBlank())
                ? "branch-engine@tuckersoft.co.uk"
                : usuarioSmtp;
    }

    public String asunto(DecisionCommittedEvent evento) {
        return "[TUCKERSOFT] " + evento.branchType()
                + " en " + evento.playerTag()
                + " | Impacto " + evento.impactLevel();
    }

    public String cuerpo(DecisionCommittedEvent evento) {
        return """
                Hola %s,

                Una partida de prueba acaba de ramificarse.

                %s
                Decision ID      : #%d
                Jugador          : %s
                Rama             : %s
                Impacto          : %s
                Departamento     : %s
                Consecuencia     : %s
                Nodo origen      : %s
                Nodo destino     : %s
                Estado partida   : %s
                Lucidez          : %d/100
                Nivel de control : %d/100
                Final            : %s
                Registrada       : %s
                %s

                Decision original del jugador:
                "%s"

                - Tuckersoft Branch Engine, 1984
                """.formatted(
                evento.displayName(),
                SEPARADOR,
                evento.decisionId(),
                evento.playerTag(),
                evento.branchType(),
                evento.impactLevel(),
                evento.handlerUnit(),
                evento.outcomeCode(),
                texto(evento.sourceNodeCode()),
                texto(evento.resolvedNodeCode()),
                evento.playthroughStatus(),
                evento.lucidity(),
                evento.controlLevel(),
                texto(evento.endingCode()),
                evento.createdAt(),
                SEPARADOR,
                evento.rawInput());
    }

    /**
     * Envia de verdad. En Modo QA lanza una excepcion real, para que la atrape el
     * mismo catch del listener que atraparia un fallo de SMTP autentico.
     */
    public void enviar(DecisionCommittedEvent evento, String asunto, String cuerpo) {
        if (evento.simulaFalloDeCorreo()) {
            throw new MailSendException(
                    "Fallo de SMTP simulado por la cabecera X-Bandersnatch-Simulate: MAIL_FAILURE");
        }

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setTo(evento.recipientEmail());
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }

    private String texto(String valor) {
        return valor == null ? "-" : valor;
    }
}
