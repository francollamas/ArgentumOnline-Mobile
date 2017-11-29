package org.gszone.jfenix13.managers;

import org.gszone.jfenix13.utils.Dialogs;
import org.gszone.jfenix13.views.CrearPjView;

public class MenuManager extends ViewManager {

    public MenuManager() {

    }

    public void conectar(String nombre, String contraseña) {
        if (nombre.equals(""))
            Dialogs.showOKDialog("Error", "El nombre no puede estar vacío.");
        else if (contraseña.equals(""))
            Dialogs.showOKDialog("Error", "La contraseña no puede estar vacía");
        else {
            if (getConnection().connect())
                getClPack().writeLoginExistingChar(nombre, contraseña);
        }
    }

    public void crearPj() {
        if (getConnection().connect()) {
            setScreen(new CrearPjView());
            getClPack().writeThrowDices();
        }
    }
}
