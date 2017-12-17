package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;

public class SlotTarget extends Target {

    public SlotTarget(Actor actor) {
        super(actor);
    }

    @Override
    public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
        if (source.getActor() == getActor()) {
            getActor().setColor(1, 0.5f, 0.5f, 1);
            return false;
        }
        else
            getActor().setColor(0.7f, 1, 0.7f, 1);
        return true;
    }

    @Override
    public void drop(Source source, Payload payload, float x, float y, int pointer) {

    }

    @Override
    public void reset(Source source, Payload payload) {
        getActor().setColor(Color.WHITE);
    }
}
