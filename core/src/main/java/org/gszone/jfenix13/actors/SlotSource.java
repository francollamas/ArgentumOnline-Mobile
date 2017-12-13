package org.gszone.jfenix13.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;

public class SlotSource extends Source {

    public SlotSource(Actor actor) {
        super(actor);
    }

    @Override
    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
        Slot a = (Slot)getActor();
        if (a.getClass() == Slot.class) return null;
        if (a.getCantidad() == 0) return null;
        if (a.getGrid() == null) return null;

        Payload payload = new Payload();

        payload.setObject(a);

        a.setVisible(false);
        Slot dragSlot = new Slot();
        dragSlot.setGrh(a.getGrh());
        dragSlot.setGrhWidth(a.getGrhWidth());
        dragSlot.setGrhHeight(a.getGrhHeight());
        dragSlot.setCantidad(1);
        dragSlot.setColor(1, 1, 1, 0.5f);
        payload.setDragActor(dragSlot);

        //payload.setValidDragActor(new VisTextButton("valid"));
        //payload.setInvalidDragActor(null);
        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        Slot pSlot = (Slot)payload.getObject();
        pSlot.setVisible(true);

        if (target != null) {
            pSlot.exchangeWith((Slot)target.getActor());
        }
    }
}
