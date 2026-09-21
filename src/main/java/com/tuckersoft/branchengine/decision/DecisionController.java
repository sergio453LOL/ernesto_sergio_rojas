package com.tuckersoft.branchengine.decision;

import com.tuckersoft.branchengine.decision.dto.DecisionRequest;
import com.tuckersoft.branchengine.decision.dto.DecisionResponse;
import com.tuckersoft.branchengine.decision.dto.PageResponse;
import com.tuckersoft.branchengine.realitylog.dto.RealityLogResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/decisions")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    /**
     * Devuelve 201 de inmediato. La cabecera X-Bandersnatch-Simulate es opcional
     * y un valor desconocido nunca provoca un 400.
     */
    @PostMapping
    public ResponseEntity<DecisionResponse> crear(
            @Valid @RequestBody DecisionRequest peticion,
            @RequestHeader(value = "X-Bandersnatch-Simulate", required = false) String simulate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(decisionService.crear(peticion, simulate));
    }

    @GetMapping
    public ResponseEntity<PageResponse<DecisionResponse>> listar(
            @RequestParam(required = false) String branchType,
            @RequestParam(required = false) String impactLevel,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long playthroughId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                decisionService.listar(branchType, impactLevel, status, playthroughId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DecisionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(decisionService.obtener(id));
    }

    @GetMapping("/{id}/reality-logs")
    public ResponseEntity<List<RealityLogResponse>> informes(@PathVariable Long id) {
        return ResponseEntity.ok(decisionService.informes(id));
    }
}
