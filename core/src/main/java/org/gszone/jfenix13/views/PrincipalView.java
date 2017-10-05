package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.github.czyzby.lml.annotation.LmlActor;
import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class PrincipalView extends View {
    public static final String ID = "principal";

    @LmlActor("world") private Container worldContainer;
    @LmlActor("touchpad") private Container tpContainer;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {
        //setBackground();
        // Agrego el mundo y touchpad
        worldContainer.setActor(getGD().getWorld());
    }
}
