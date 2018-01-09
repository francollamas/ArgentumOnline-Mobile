package org.gszone.jfenix13.views.windows;

import org.gszone.jfenix13.actors.Window;

public class ComerciarWindow extends Window {
    public ComerciarWindow() {
        super("Comerciar");
    }

    @Override
    protected void close() {
        super.close();

        getClPack().writeCommerceEnd();
    }
}
