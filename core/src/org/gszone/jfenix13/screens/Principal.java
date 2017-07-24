package org.gszone.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import org.gszone.jfenix13.graphics.Drawer;


public class Principal extends Screen {

    public Principal() { super(Scr.PRINCIPAL); }

    @Override
    public void show() {
        super.show();

        TextButton tb = new TextButton("Mandar", getSkin());
        tb.setPosition(100, 100);
        tb.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getClPack().writeLoginExistingChar();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(tb);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();

        getBatch().begin();
        Drawer.drawText(getBatch(), 2, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 10);
        getBatch().end();

        getClPack().write();
    }
}
