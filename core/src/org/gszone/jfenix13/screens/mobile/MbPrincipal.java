package org.gszone.jfenix13.screens.mobile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import org.gszone.jfenix13.actors.Controller;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.actors.World;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.graphics.Drawer.Alignment;

public class MbPrincipal extends Screen {

    private Controller controller;

    public MbPrincipal() { super(Scr.PRINCIPAL); }

    @Override
    public void show() {
        super.show();

        getWorld().setPosition(108, 0);
        stage.addActor(getWorld());

        controller = new Controller();
        controller.setWorld(getWorld());
        controller.setPosition(0, 0);
        stage.addActor(controller);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        getBatch().begin();

        Drawer.pushScissors(stage, getWorld().getRect());
        getWorld().render(stage);
        Drawer.setDefColor(Color.WHITE);
        Drawer.popScissors(stage);

        Drawer.drawText(getBatch(), 1, "" + Gdx.graphics.getFramesPerSecond(), 5, 5, Alignment.LEFT);
        Drawer.drawText(getBatch(), 2, "X: " + (int) getWorld().getPos().getX() +
                "  -  Y: " + (int) getWorld().getPos().getY(), 5, 20, Alignment.LEFT);
        getBatch().end();

        stage.draw();
    }

    private World getWorld() { return Main.getInstance().getGameData().getWorld(); }
}
