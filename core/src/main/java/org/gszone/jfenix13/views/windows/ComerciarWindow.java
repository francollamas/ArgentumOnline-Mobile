package org.gszone.jfenix13.views.windows;

import com.kotcrab.vis.ui.widget.VisWindow;

import static org.gszone.jfenix13.utils.Actors.*;

public class ComerciarWindow extends VisWindow {
    public ComerciarWindow() {
        super("Comerciar");
        centerWindow();

        newTextButton(this, "Volver");
    }
}
