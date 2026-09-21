package com.tuckersoft.branchengine.node;

import com.tuckersoft.branchengine.node.dto.NodeRequest;
import com.tuckersoft.branchengine.node.dto.NodeResponse;
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
@RequestMapping("/api/v1/nodes")
public class StoryNodeController {

    private final StoryNodeService storyNodeService;

    public StoryNodeController(StoryNodeService storyNodeService) {
        this.storyNodeService = storyNodeService;
    }

    /** Solo ROLE_ADMIN: la regla vive en el SecurityFilterChain. */
    @PostMapping
    public ResponseEntity<NodeResponse> crear(@Valid @RequestBody NodeRequest peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storyNodeService.crear(peticion));
    }

    @GetMapping
    public ResponseEntity<List<NodeResponse>> listar() {
        return ResponseEntity.ok(storyNodeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(storyNodeService.obtener(id));
    }
}
