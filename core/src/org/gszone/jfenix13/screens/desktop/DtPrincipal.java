package org.gszone.jfenix13.screens.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.kotcrab.vis.ui.widget.VisTextButton;
import org.gszone.jfenix13.actors.Controller;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.actors.World;
import org.gszone.jfenix13.screens.Screen;


/**
 * Pantalla principal del juego
 *
 * world: el rectángulo principal donde se muestra el mundo con los personajes, npcs, etc.
 */
public class DtPrincipal extends Screen {

    private VisTextButton tbCerrar;

    public DtPrincipal() { super(Scr.PRINCIPAL, "dt_principal"); }

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

        getWorld().setPosition(15, 74);
        stage.addActor(getWorld());

    }


    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();
        
        Drawer.pushScissors(stage, getWorld().getRect());
        getWorld().checkKeys();
        getWorld().move();
        getWorld().render(stage);
        Drawer.setDefColor(Color.WHITE);
        Drawer.popScissors(stage);


        background.draw(getBatch(), 1);
        Drawer.drawText(getBatch(), 1, "" + Gdx.graphics.getFramesPerSecond(), 662, 114);
        Drawer.drawText(getBatch(), 2, "X: " + (int) getWorld().getPos().getX() +
                "  -  Y: " + (int) getWorld().getPos().getY(), 560, 725);
        getBatch().end();

        stage.draw();
    }

    private World getWorld() { return Main.getInstance().getGameData().getWorld(); }

}
