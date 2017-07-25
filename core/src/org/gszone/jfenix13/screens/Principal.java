package org.gszone.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.objects.World;

import static com.badlogic.gdx.Input.Keys.*;
import static org.gszone.jfenix13.general.General.*;


/**
 * Pantalla principal del juego
 *
 * world: el rectángulo principal donde se muestra el mundo con los personajes, npcs, etc.
 */
public class Principal extends Screen {

    private TextButton tbCerrar;
    private World world;

    public Principal() { super(Scr.PRINCIPAL, "principal"); }

    @Override
    public void show() {
        super.show();

        // Boton para cerrar el juego
        tbCerrar = new TextButton("X", getSkin());
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

        world = new World();
        world.setPosition(15, 74);
        stage.addActor(world);

    }


    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();


        Drawer.pushScissors(stage, world.getRect());
        Drawer.setDefColor(255, 128, 50, 255);
        checkKeys();
        world.move();
        world.render(stage);
        Drawer.setDefColor(255, 255, 255, 255);
        Drawer.popScissors(stage);


        background.draw(getBatch(), 1);
        Drawer.drawText(getBatch(), 1, "" + Gdx.graphics.getFramesPerSecond(), 662, 114);
        Drawer.drawText(getBatch(), 2, "X: " + (int) world.getPos().getX() +
                "  -  Y: " + (int) world.getPos().getY(), 560, 725);
        getBatch().end();

        stage.draw();
    }

    /**
     * Mueve la pantalla según la entrada del teclado
     */
    public void checkKeys() {
        if (!world.isMoving()) {
            if (Gdx.input.isKeyPressed(UP)) {
                world.setMove(Direccion.NORTE);
            } else if (Gdx.input.isKeyPressed(RIGHT)) {
                world.setMove(Direccion.ESTE);
            } else if (Gdx.input.isKeyPressed(DOWN)) {
                world.setMove(Direccion.SUR);
            } else if (Gdx.input.isKeyPressed(LEFT)) {
                world.setMove(Direccion.OESTE);
            }
        }
    }

}
