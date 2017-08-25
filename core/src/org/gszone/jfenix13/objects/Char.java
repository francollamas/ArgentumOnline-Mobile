package org.gszone.jfenix13.objects;


import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;

import static org.gszone.jfenix13.general.General.*;

public class Char {
    private boolean active;
    private String nombre;
    private String guildName;
    private byte criminal;
    private boolean muerto;
    private boolean invisible;

    private Direccion heading;

    private int fxIndex;
    private Grh fx;
    private int bodyIndex;
    private Grh[] body;
    private int headIndex;
    private Grh[] head;
    private Grh[] helmet;
    private Grh[] weapon;
    private Grh[] shield;

    private Position pos;
    private Position moveDir;
    private Position moveOffset;
    private Boolean moving;



    public Char() {
        pos = new Position();
        moveDir = new Position();
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

    public boolean isMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public Direccion getHeading() {
        return heading;
    }

    public void setHeading(Direccion heading) {
        this.heading = heading;
    }

    public int getFxIndex() {
        return fxIndex;
    }

    public void setFxIndex(int fxIndex) {
        this.fxIndex = fxIndex;
    }

    public Grh getFx() {
        return fx;
    }

    public void setFx(Grh fx) {
        this.fx = fx;
    }

    public int getBodyIndex() {
        return bodyIndex;
    }

    public void setBodyIndex(int bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public Grh[] getBody() {
        return body;
    }

    public void setBody(Grh[] body) {
        this.body = body;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public Grh[] getHead() {
        return head;
    }

    public void setHead(Grh[] head) {
        this.head = head;
    }

    public Grh[] getHelmet() {
        return helmet;
    }

    public void setHelmet(Grh[] helmet) {
        this.helmet = helmet;
    }

    public Grh[] getWeapon() {
        return weapon;
    }

    public void setWeapon(Grh[] weapon) {
        this.weapon = weapon;
    }

    public Grh[] getShield() {
        return shield;
    }

    public void setShield(Grh[] shield) {
        this.shield = shield;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getMoveDir() {
        return moveDir;
    }

    public void setMoveDir(Position moveDir) {
        this.moveDir = moveDir;
    }

    public Position getMoveOffset() {
        return moveOffset;
    }

    public void setMoveOffset(Position moveOffset) {
        this.moveOffset = moveOffset;
    }

    public Boolean getMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }
}
