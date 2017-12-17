package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
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

        payload.setDragActor(newTempSlot(a, new Color(1, 1, 1, 0.5f)));
        payload.setValidDragActor(newTempSlot(a, new Color(0.7f, 1, 0.7f, 0.5f)));
        payload.setInvalidDragActor(newTempSlot(a, new Color(1, 0.5f, 0.5f, 0.5f)));
        return payload;
    }

    private Slot newTempSlot(Slot a, Color c) {
        return newTempSlot(a, c, null);
    }

    /**
     * Crea un slot provisorio para el Drag-Drop
     *
     * @param a slot de referencia (para sacar tamaño y Grh)
     */
    private Slot newTempSlot(Slot a, Color c, Color backgroundColor) {
        Slot s = new Slot();
        s.setGrh(a.getGrh());
        s.setGrhWidth(a.getGrhWidth());
        s.setGrhHeight(a.getGrhHeight());
        s.setCantidad(1);
        s.setColor(c);
        return s;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        Slot pSlot = (Slot)payload.getObject();
        pSlot.setVisible(true);

        if (target != null) {
            pSlot.getGrid().onDDAction(pSlot, (Slot)target.getActor());
        }
    }
}
