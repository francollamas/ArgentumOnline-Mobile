package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTextField;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class CrearPjView extends View {
    public static final String ID = "crear_pj";

    @LmlActor("nombre") private VisTextField tfNombre;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {
        setTfFocus(tfNombre);
    }
}
