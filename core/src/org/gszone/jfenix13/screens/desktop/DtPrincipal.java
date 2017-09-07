package org.gszone.jfenix13.screens.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.actors.World;
import org.gszone.jfenix13.screens.Screen;

import com.badlogic.gdx.Input.Keys;
import org.gszone.jfenix13.graphics.Drawer.Alignment;


/**
 * Pantalla principal del juego
 *
 * world: el rectángulo principal donde se muestra el mundo con los personajes, npcs, etc.
 */
public class DtPrincipal extends Screen {

    private VisTextButton tbCerrar;
    private VisTextField tfSend;

    public DtPrincipal() { super(Scr.PRINCIPAL, "principal"); }

    @Override
    public void show() {
        super.show();

        // Boton para cerrar el juego
        tbCerrar = new VisTextButton("X");
        tbCerrar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        tbCerrar.setPosition(983, 730);
        tbCerrar.setSize(25, 22);
        stage.addActor(tbCerrar);

        // Texto para enviar mandar comandos
        tfSend = new VisTextField("");
        tfSend.setSize(480, 20);
        tfSend.setPosition(77, 452);
        tfSend.setFocusBorderEnabled(false);
        tfSend.setVisible(false);


        getWorld().setPosition(9, 34);
        stage.addActor(getWorld());
        stage.addActor(tfSend);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER) {
                    // TODO: Agregar la condición de que si no esta comerciando, etc...
                    tfSend.setVisible(true);
                    stage.setKeyboardFocus(tfSend);
                    tfSend.getOnscreenKeyboard().show(true);
                    tfSend.focusGained();
                }

                return super.keyUp(event, keycode);
            }
        });
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();
        
        Drawer.pushScissors(stage, getWorld().getRect());
        getWorld().render(stage);
        Drawer.setDefColor(Color.WHITE);
        Drawer.popScissors(stage);


        background.draw(getBatch(), 1);
        DrawParameter dp = new DrawParameter();
        dp.setColor(0, 192, 0);
        Drawer.drawText(getBatch(), 2, "" + Gdx.graphics.getFramesPerSecond(), 52, 7, Alignment.LEFT, dp);

        int numMap = Main.getInstance().getGameData().getCurrentUser().getMap();
        String nomMap = Main.getInstance().getAssets().getMapa().getNombre();
        Drawer.drawText(getBatch(), 1, nomMap + " [" + numMap + ", " + (int) getWorld().getPos().getX() +
                ", " + (int) getWorld().getPos().getY() + "]", 678, 575, Alignment.CENTER);
        getBatch().end();

        stage.draw();
    }

    private World getWorld() { return Main.getInstance().getGameData().getWorld(); }

}
