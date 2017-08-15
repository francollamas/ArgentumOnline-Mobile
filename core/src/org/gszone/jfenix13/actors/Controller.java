package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import org.gszone.jfenix13.general.FileNames;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.general.General.Direccion;
import static org.gszone.jfenix13.general.General.Direccion.*;


public class Controller extends Touchpad {
    private static Skin tpSkin;
    private static TouchpadStyle tpStyle;
    private World world;

    public Controller() {
        super(20, getTouchPadStyle());
        setSize(140, 140);
    }

    private static TouchpadStyle getTouchPadStyle(){

        tpSkin = new Skin();
        tpSkin.add("tp_background", new Texture(FileNames.DIR_GUI + "/tp_background.png"));
        tpSkin.add("tp_knob", new Texture(FileNames.DIR_GUI + "/tp_knob.png"));

        tpStyle = new TouchpadStyle();
        tpStyle.background = tpSkin.getDrawable("tp_background");
        tpStyle.knob = tpSkin.getDrawable("tp_knob");
        return tpStyle;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isTouched()) {
            if (!world.isMoving()) {
                float despX = getKnobPercentX();
                float despY = getKnobPercentY();
                Direccion dir = null;
                if (despX != 0 || despY != 0) {
                    if (Math.abs(despX) > Math.abs(despY)) {
                        if (despX > 0)
                            dir = ESTE;
                        else
                            dir = OESTE;
                    }
                    else {
                        if (despY > 0)
                            dir = NORTE;
                        else
                            dir = SUR;
                    }
                }


                if (dir != null)
                    world.setMove(dir);
            }
        }
    }

    public static void dispose() {
        tpSkin.dispose();
        tpStyle = null;
    }
}
