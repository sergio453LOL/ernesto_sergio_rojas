package com.tuckersoft.branchengine.node;

import com.tuckersoft.branchengine.node.dto.NodeRequest;
import com.tuckersoft.branchengine.node.dto.NodeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Las escenas de la historia.
 *
 * TODO [PARTE 2 - NODOS]: implementar los metodos.
 *
 * Reglas del enunciado:
 *  - crear: nodeCode repetido -> ConflictException (409). currentBranches
 *    arranca SIEMPRE en 0 y createdAt lo pone el service, no el request.
 *    Los 400 por validacion (branchCapacity = 0, campos faltantes, sceneText
 *    corto) los cubren las anotaciones de NodeRequest.
 *  - listar: un array simple de DTOs, no una estructura paginada.
 *  - obtener: id inexistente -> NotFoundException (404).
 *  - Nunca devuelvas la entidad JPA: siempre NodeResponse.
 */
@Service
public class StoryNodeService {

    private final StoryNodeRepository storyNodeRepository;

    public StoryNodeService(StoryNodeRepository storyNodeRepository) {
        this.storyNodeRepository = storyNodeRepository;
    }

    @Transactional
    public NodeResponse crear(NodeRequest peticion) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: crear el nodo de historia");
    }

    @Transactional(readOnly = true)
    public List<NodeResponse> listar() {
        throw new UnsupportedOperationException("TODO [PARTE 2]: listar los nodos");
    }

    @Transactional(readOnly = true)
    public NodeResponse obtener(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: devolver el nodo por id");
    }

    @Transactional(readOnly = true)
    public StoryNode buscar(Long id) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: buscar el nodo o lanzar 404");
    }

    public NodeResponse aDto(StoryNode nodo) {
        throw new UnsupportedOperationException("TODO [PARTE 2]: mapear StoryNode a NodeResponse");
    }
}
