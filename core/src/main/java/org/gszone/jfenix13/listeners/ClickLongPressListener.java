package org.gszone.jfenix13.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.utils.Position;

import static com.badlogic.gdx.Application.ApplicationType.*;

/**
 * Mismo que el ClickListener pero con la funcionalidad de pulsación larga.
 * (Existe el gesto de LongPress, pero me resultó más cómodo trabajarlo directamente desde acá).
 *
 * downLeft y timeDownLeft: se usan para controlar la pulsación larga del click izquierdo.
 * longPressDone: indica si el último intento de longpress se realizó correctamente.
 * lastMousePos: indica la última posición (X, Y) del mouse en píxeles
 * changeMousePos: indica si se cambió la posición del mouse
 */
public class ClickLongPressListener extends ClickListener {
    public static final int MS_LONG_PRESS = 350;

    private boolean downLeft;
    private long timeDownLeft;
    private boolean longPressDone;
    private Position lastMousePos;
    private boolean changeMousePos;
    private boolean longPressMobileOnly;

    public ClickLongPressListener() {
        lastMousePos = new Position();
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        boolean endLongPress = endLongPress();
        setLastMousePos(x, y);

        if ((button == 0 && !endLongPress) || button == 1)
            tUp(event, x, y, pointer, button);

        // Hago esto para que el tercer click consecutivo sea normal, y no siga aumentando
        if (getTapCount() >= 2) setTapCount(0);
    }

    /**
     * Usar para TouchUp
     */
    public void tUp(InputEvent event, float x, float y, int pointer, int button) {

    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        setLastMousePos(x, y);
        if (button == 0 && (!longPressMobileOnly || (longPressMobileOnly && (Gdx.app.getType() == Android || Gdx.app.getType() == iOS))))
            startLongPress();
        tDown(event, x, y, pointer, button);
        super.touchDown(event, x, y, pointer, button);
        return true;
    }

    /**
     * Usar para TouchDown
     */
    public void tDown(InputEvent event, float x, float y, int pointer, int button) {

    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        setLastMousePos(x, y);
        return super.mouseMoved(event, x, y);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        setLastMousePos(x, y);
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
        boolean can = downLeft && TimeUtils.millis() - timeDownLeft > MS_LONG_PRESS;
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

    public boolean isLongPressMobileOnly() {
        return longPressMobileOnly;
    }

    public void setLongPressMobileOnly(boolean longPressMobileOnly) {
        this.longPressMobileOnly = longPressMobileOnly;
    }
}
