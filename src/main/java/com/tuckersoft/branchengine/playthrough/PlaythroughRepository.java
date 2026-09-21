package com.tuckersoft.branchengine.playthrough;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaythroughRepository extends JpaRepository<Playthrough, Long> {

    boolean existsByPlayerTag(String playerTag);

    List<Playthrough> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Playthrough> findAllByOrderByCreatedAtDesc();
}
