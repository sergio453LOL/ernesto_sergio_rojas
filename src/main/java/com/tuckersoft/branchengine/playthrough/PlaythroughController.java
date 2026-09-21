package com.tuckersoft.branchengine.playthrough;

import com.tuckersoft.branchengine.playthrough.dto.PathResponse;
import com.tuckersoft.branchengine.playthrough.dto.PlaythroughRequest;
import com.tuckersoft.branchengine.playthrough.dto.PlaythroughResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playthroughs")
public class PlaythroughController {

    private final PlaythroughService playthroughService;

    public PlaythroughController(PlaythroughService playthroughService) {
        this.playthroughService = playthroughService;
    }

    @PostMapping
    public ResponseEntity<PlaythroughResponse> crear(@Valid @RequestBody PlaythroughRequest peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playthroughService.crear(peticion));
    }

    @GetMapping
    public ResponseEntity<List<PlaythroughResponse>> listar() {
        return ResponseEntity.ok(playthroughService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaythroughResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(playthroughService.obtener(id));
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<PathResponse> recorrido(@PathVariable Long id) {
        return ResponseEntity.ok(playthroughService.recorrido(id));
    }
}
