package org.gszone.jfenix13.controllers;

import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.utils.Position;

/**
 * Contiene información que maneja parte del World
 *
 * downLeft y timeDownLeft: se usan para controlar la pulsación larga del click izquierdo.
 * longPressDone: indica si el último intento de longpress se realizó correctamente.
 * lastMousePos: indica la última posición (X, Y) del mouse en píxeles
 * changeMousePos: indica si se cambió la posición del mouse
 */
public class WorldController {
    private boolean downLeft;
    private long timeDownLeft;
    private boolean longPressDone;
    private Position lastMousePos;
    private boolean changeMousePos;

    public WorldController() {
        lastMousePos = new Position();
    }

    /**
     * Inicia un intento de pulsación larga
     */
    public void startLongPress() {
        this.downLeft = true;
        timeDownLeft = TimeUtils.millis();
    }

    /**
     * Termina un intento de pulsación larga
     *
     * @return true si se realizó un longpress con éxito
     */
    public boolean endLongPress() {
        this.downLeft = false;
        if (longPressDone) {
            longPressDone = false;
            return true;
        }
        return false;
    }

    /**
     * Si pasó un tiempo determinado y el intento de pulsación larga no terminó, se realiza.
     */
    public boolean longPressDone() {
        boolean can = downLeft && TimeUtils.millis() - timeDownLeft > 350;
        if (can) {
            downLeft = false;
            longPressDone = true;
        }
        return can;
    }

    public void setLastMousePos(float x, float y) {
        setLastMousePos(new Position(x, y));
    }

    /**
     * Actualiza la última posición del mouse
     */
    public void setLastMousePos(Position pos) {
        this.lastMousePos = pos;
        changeMousePos = true;
    }

    /**
     * Devuelve la última posición del mouse
     */
    public Position getLastMousePos() {
        return lastMousePos;
    }

    /**
     * Indica si la posición del mouse cambió
     */
    public boolean mousePosChanged() {
        if (changeMousePos) {
            changeMousePos = false;
            return true;
        }
        return false;
    }
}
