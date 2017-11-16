package org.gszone.jfenix13.views;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.vis.ui.VisTabTable;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisRadioButton;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.general.DtConfig;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class ConfigView extends View {
    public static final String ID = "config";

    @LmlActor("videotabb") private VisTabTable tabVideo;
    @LmlActor("vsync") private VisCheckBox cbVSync;
    @LmlActor("modo-ventana") private VisRadioButton rbModoVentana;
    @LmlActor("pant-completa") private VisRadioButton rbPantCompleta;
    @LmlActor("t-modo-ventana") private VisTable tbModoVentana;
    @LmlActor("resolucion") private VisSelectBox<String> sbRes;
    @LmlActor("title-bar") private VisCheckBox cbTitleBar;
    @LmlActor("redimensionable") private VisCheckBox cbRedimensionable;

    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {

        // Desactivamos las configuraciones de video para Web, ya que son innecesarias.
        if (Gdx.app.getType() == Application.ApplicationType.WebGL)
            tabVideo.setDisabled(true);

        // Configuraciones EXCLUSIVAS para Escritorio
        // GWTIncompatible (comentar el if entero para que compile en GWT)
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (DtConfig.vSync) cbVSync.setChecked(true);

            if (DtConfig.fullscreeen) rbPantCompleta.setChecked(true);
            else rbModoVentana.setChecked(true);

            setModoVentanaVisible();

            if (DtConfig.width == 800) sbRes.setSelectedIndex(0);
            if (DtConfig.width == 1024) sbRes.setSelectedIndex(1);
            if (DtConfig.width == 1200) sbRes.setSelectedIndex(2);

            if (DtConfig.decorated) cbTitleBar.setChecked(true);
            if (DtConfig.resizable) cbRedimensionable.setChecked(true);
        }
    }

    @LmlAction("setModoVentanaVisible")
    public void setModoVentanaVisible() {
        tbModoVentana.setVisible(!rbPantCompleta.isChecked());
    }

    @LmlAction("guardar")
    public void guardar() {

        // Configuraciones EXCLUSIVAS para Escritorio
        // GWTIncompatible (comentar el if entero para que compile en GWT)
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            // Guarda los valores actuales para luego verificar si se hizo un cambio en al menos una característica
            boolean oldVSync = DtConfig.vSync;
            boolean oldFullScreen = DtConfig.fullscreeen;
            int oldWidth = DtConfig.width;
            int oldHeight = DtConfig.height;
            boolean oldDecorated = DtConfig.decorated;
            boolean oldResizable = DtConfig.resizable;

            DtConfig.vSync = cbVSync.isChecked();

            DtConfig.fullscreeen = rbPantCompleta.isChecked();
            if (DtConfig.fullscreeen) {
                DtConfig.width = 1024; DtConfig.height = 768;
            }
            else {
                switch (sbRes.getSelectedIndex()) {
                    case 0:
                        DtConfig.width = 800; DtConfig.height = 600;
                        break;
                    case 1:
                        DtConfig.width = 1024; DtConfig.height = 768;
                        break;
                    case 2:
                        DtConfig.width = 1200; DtConfig.height = 900;
                        break;
                }
            }

            DtConfig.decorated = cbTitleBar.isChecked();
            DtConfig.resizable = cbRedimensionable.isChecked();

            DtConfig.saveConfig();

            // Si se hicieron cambios de video, se reinicia el cliente.
            if (oldVSync != DtConfig.vSync || oldFullScreen != DtConfig.fullscreeen
                    || oldWidth != DtConfig.width || oldHeight != DtConfig.height
                    || oldDecorated != DtConfig.decorated || oldResizable != DtConfig.resizable) {

                Main.getInstance().reiniciar();
            }

        }

        Config c = Main.getInstance().getConfig();
        c.saveConfigFile();

        setView(MenuView.class);
    }
}
