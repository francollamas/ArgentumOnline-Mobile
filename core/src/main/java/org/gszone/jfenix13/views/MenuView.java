package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class MenuView extends View {
    public static final String ID = "menu";

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }
}
