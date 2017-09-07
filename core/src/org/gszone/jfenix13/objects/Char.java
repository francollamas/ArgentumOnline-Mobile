package org.gszone.jfenix13.objects;

import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;

import static org.gszone.jfenix13.general.General.*;

/**
 * Representa a un PJ o NPC
 *
 * active: determina si el personaje se acaba de crear o ya estaba creado (útil para actualizar el pj)
 * nombre: nombre del PJ
 * guildName: nombre del clan
 * bando: número que indica bando (ciudadano, criminal, neutral, newbie). Es distinto al número manejado en el servidor.
 * muerto: indica si está muerto
 * invisible: indica si está invisible
 * priv: número que indica los privilegios del usuario.
 */
public class Char {
    private boolean active;
    private String nombre;
    private String guildName;
    private byte bando;
    private boolean muerto;
    private boolean invisible;
    private int priv;

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
    private boolean moving;

    public Char() {
        pos = new Position();
        moveDir = new Position();
        moveOffset = new Position();
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

    public byte getBando() {
        return bando;
    }

    public void setBando(byte bando) {
        this.bando = bando;
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

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
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

    public Grh getFx() {
        return fx;
    }

    public void setFx(int index, int loops) {
        Fx fx = Main.getInstance().getAssets().getFxs().getFx(index);
        if (fx != null) {
            this.fx = fx.getGrh();
            this.fx.setLoops((short)loops);
            this.fxIndex = index;
        }
        else {
            this.fx = null;
            this.fxIndex = 0;
        }
    }

    public int getBodyIndex() {
        return bodyIndex;
    }

    public Grh[] getBody() {
        return body;
    }

    public void setBody(int index) {
        Body body = Main.getInstance().getAssets().getBodies().getBody(index);
        if (body != null) {
            this.body = body.getGrhs();
            this.bodyIndex = index;
        }
        else {
            this.body = null;
            this.bodyIndex = 0;
        }
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public Grh[] getHead() {
        return head;
    }

    public void setHead(int index) {
        GrhDir head = Main.getInstance().getAssets().getHeads().getGrhDir(index);
        if (head != null) {
            this.head = head.getGrhs();
            this.headIndex = index;
        }
        else {
            this.head = null;
            this.headIndex = 0;
        }
    }

    public Grh[] getHelmet() {
        return helmet;
    }

    public void setHelmet(int index) {
        GrhDir helmet = Main.getInstance().getAssets().getHelmets().getGrhDir(index);
        if (helmet != null)
            this.helmet = helmet.getGrhs();
        else
            this.helmet = null;
    }

    public Grh[] getWeapon() {
        return weapon;
    }

    public void setWeapon(int index) {
        GrhDir weapon = Main.getInstance().getAssets().getWeapons().getGrhDir(index);
        if (weapon != null)
            this.weapon = weapon.getGrhs();
        else
            this.weapon = null;
    }

    public Grh[] getShield() {
        return shield;
    }

    public void setShield(int index) {
        GrhDir shield = Main.getInstance().getAssets().getShields().getGrhDir(index);
        if (shield != null)
            this.shield = shield.getGrhs();
        else
            this.weapon = null;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
