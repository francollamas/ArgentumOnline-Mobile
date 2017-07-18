package org.gszone.jfenix13.screens;


import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Pantalla provisoria para testear cosas
 */
public class Test extends Screen {

    public Test() { super(Scr.TEST); }

    @Override
    public void show() {
        super.show();

        TextButton boton = new TextButton("Boton", getSkin());
        stage.addActor(boton);

        boton.setPosition(50, 50);
        boton.setSize(100, 50);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.draw();

        stage.getBatch().begin();
        stage.getBatch().end();

    }
}
