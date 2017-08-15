package org.gszone.jfenix13.objects;


import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;

import static org.gszone.jfenix13.general.General.*;

public class Char {
    private boolean active;
    private String nombre;
    private String guildName;
    private byte criminal;
    private Grh fx;
    private Position pos;

    public Char() {
        pos = new Position();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public byte getCriminal() {
        return criminal;
    }

    public void setCriminal(byte criminal) {
        this.criminal = criminal;
    }

    public Grh getFx() {
        return fx;
    }

    public void setFx(Grh fx) {
        this.fx = fx;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
}
