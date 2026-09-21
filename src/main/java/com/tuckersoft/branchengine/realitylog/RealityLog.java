package com.tuckersoft.branchengine.realitylog;

import com.tuckersoft.branchengine.decision.Decision;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/** Audita cada intento de envio del Informe de Realidad. */
@Entity
@Table(name = "reality_log")
@Getter
@Setter
public class RealityLog {

    public static final String SENT = "SENT";
    public static final String FAILED = "FAILED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "decision_id", nullable = false)
    private Decision decision;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false, length = 500)
    private String subject;

    @Column(nullable = false)
    private String logStatus;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    /** Solo se asigna cuando el envio es exitoso. */
    private Instant sentAt;

    @Column(nullable = false)
    private Instant createdAt;
}
