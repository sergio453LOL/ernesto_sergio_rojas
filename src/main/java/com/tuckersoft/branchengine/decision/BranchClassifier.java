package com.tuckersoft.branchengine.decision;

import org.springframework.stereotype.Component;

/**
 * Clasifica el texto del jugador en una de las cinco ramas del guion.
 *
 * TODO [PARTE 3 - DECISIONES]: implementar clasificar() y normalizar().
 *
 * Paso 1, normalizar: Normalizer.Form.NFD, quitar \p{M} y pasar a minusculas,
 * para que "CAMARA", "cámara" y "camara" se comparen igual.
 *
 * Paso 2, las reglas EN ESTE ORDEN (gana la primera que se cumple):
 *  1. No contiene ninguna letra de la 'a' a la 'z'          -> ENTRADA_CORRUPTA
 *  2. Contiene netflix, camara, espectador o videojuego     -> RUPTURA_CUARTA_PARED
 *  3. Contiene vigilan, simbolo o conspiracion              -> SOSPECHA
 *  4. Contiene rechaza, destruye, desobedece o renuncia     -> REBELDIA
 *  5. Cualquier otro caso                                   -> OBEDIENCIA
 * "Stefan destruye la camara" es RUPTURA_CUARTA_PARED, no REBELDIA.
 *
 * Paso 3, derivar de la rama (no vienen en el request):
 *  OBEDIENCIA           -> "Mesa de Guion"           / ADVANCE_MAIN_PATH
 *  REBELDIA             -> "Control de Continuidad"  / FORK_TIMELINE
 *  SOSPECHA             -> "Oficina de Seguridad"    / INJECT_WHITE_BEAR_SYMBOL
 *  RUPTURA_CUARTA_PARED -> "Departamento Netflix"    / BREAK_FOURTH_WALL
 *  ENTRADA_CORRUPTA     -> "Archivo de Errores"      / DISCARD_INPUT
 * Ojo: "Mesa de Guion" va SIN tilde, los autotests comparan el String exacto.
 */
@Component
public class BranchClassifier {

    public static final String OBEDIENCIA = "OBEDIENCIA";
    public static final String REBELDIA = "REBELDIA";
    public static final String SOSPECHA = "SOSPECHA";
    public static final String RUPTURA_CUARTA_PARED = "RUPTURA_CUARTA_PARED";
    public static final String ENTRADA_CORRUPTA = "ENTRADA_CORRUPTA";

    /** Lo que el motor deriva de un rawInput. */
    public record Clasificacion(String branchType, String handlerUnit, String outcomeCode) {
    }

    public Clasificacion clasificar(String rawInput) {
        throw new UnsupportedOperationException(
                "TODO [PARTE 3]: normalizar el texto y aplicar las 5 reglas en orden");
    }

    /** Minusculas y sin tildes. */
    public static String normalizar(String rawInput) {
        throw new UnsupportedOperationException(
                "TODO [PARTE 3]: normalizar con NFD, quitar las marcas y pasar a minusculas");
    }
}
