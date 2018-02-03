package org.gszone.jfenix13.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.graphics.DrawParameter;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.graphics.FontParameter;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.utils.Position;

import static org.gszone.jfenix13.general.Config.*;

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
    private int bando;
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
    private Dialog dialog;

    private Position pos;
    private Position moveDir;
    private Position moveOffset;
    private boolean moving;

    public Char() {
        pos = new Position();
        moveDir = new Position();
        moveOffset = new Position();
    }

    /**
     * Dibuja al PJ o NPC
     */
    public void draw(Batch batch, float x, float y, DrawParameter dp) {
        int heading = getHeading().ordinal();
        boolean moved = false;

        // Movimiento del char
        if (isMoving()) {

            if (getMoveDir().getX() != 0 || getMoveDir().getY() != 0) {
                // Arranco las animaciones
                if (getBody() != null && getBody()[heading].getSpeed() > 0) getBody()[heading].setStarted((byte)1);
                if (getWeapon() != null) getWeapon()[heading].setStarted((byte)1);
                if (getShield() != null) getShield()[heading].setStarted((byte)1);
                moved = true;


                // Muevo en X
                if (getMoveDir().getX() != 0) {
                    getMoveOffset().setX(getMoveOffset().getX()
                            + Main.getInstance().getConfig().getScrollPixelsPerFrame() * getMoveDir().getX() * Drawer.getDelta());
                    if ((getMoveDir().getX() == 1 && getMoveOffset().getX() >= 0)
                            || getMoveDir().getX() == -1 && getMoveOffset().getX() <= 0) {
                        getMoveOffset().setX(0);
                        getMoveDir().setX(0);
                    }
                }

                // Muevo en Y
                if (getMoveDir().getY() != 0) {
                    getMoveOffset().setY(getMoveOffset().getY()
                            + Main.getInstance().getConfig().getScrollPixelsPerFrame() * getMoveDir().getY() * Drawer.getDelta());
                    if ((getMoveDir().getY() == 1 && getMoveOffset().getY() >= 0)
                            || getMoveDir().getY() == -1 && getMoveOffset().getY() <= 0) {
                        getMoveOffset().setY(0);
                        getMoveDir().setY(0);
                    }
                }

            }
        }

        // Si no se movió (o sea, si no pasó por el trozo de código de arriba)
        if (!moved) {
            if (getBody() != null) {
                getBody()[heading].setStarted((byte) 0);
                getBody()[heading].setFrame(1);
            }

            if (getWeapon() != null) {
                getWeapon()[heading].setStarted((byte) 0);
                getWeapon()[heading].setFrame(1);
            }

            if (getShield() != null) {
                getShield()[heading].setStarted((byte) 0);
                getShield()[heading].setFrame(1);
            }

            setMoving(false);
        }

        x += Math.round(getMoveOffset().getX());
        y += Math.round(getMoveOffset().getY());


        // Defino el offset de la cabeza
        Position headOffset;
        if (getBody() != null)
            headOffset = Main.getInstance().getAssets().getBodies().getBody(getBodyIndex()).getHeadOffset();
        else
            headOffset = new Position();

        if (!isInvisible()) {
            if (getBody() != null)
                Drawer.drawGrh(batch, getBody()[heading], x, y, dp);

            if (getHead() != null)
                Drawer.drawGrh(batch, getHead()[heading], x + headOffset.getX(), y + headOffset.getY(), dp);

            if (getHelmet() != null)
                Drawer.drawGrh(batch, getHelmet()[heading], x + headOffset.getX(), y + headOffset.getY(), dp);

            if (getWeapon() != null)
                Drawer.drawGrh(batch, getWeapon()[heading], x, y, dp);

            if (getShield() != null)
                Drawer.drawGrh(batch, getShield()[heading], x, y, dp);

            if (getNombre().length() > 0) {
                FontParameter fp = new FontParameter("tahoma13boldhborder");
                fp.setColor(Main.getInstance().getGameData().getColors().getColor(getPriv(), getBando()));

                Drawer.drawText(batch, getNombre(), x + 16, y + 35, fp);
            }

        }

        if (getFxIndex() != 0) {
            Fx fxData = Main.getInstance().getAssets().getFxs().getFx(getFxIndex());
            Drawer.drawGrh(batch, getFx(), x + fxData.getOffset().getX(), y + fxData.getOffset().getY(), dp);
            if (getFx().getStarted() == 0) {
                setFx(0, 0);
            }
        }

    }

    /**
     * Dibuja el diálogo de un PJ o NPC
     * (se usa otro método para poder dibujarlo en otra capa)
     */
    public void drawDialog(Batch batch, float x, float y, DrawParameter dp) {

        // Agrego el offset del pj
        x += Math.round(getMoveOffset().getX());
        y += Math.round(getMoveOffset().getY());

        // Defino el offset de la cabeza
        Position headOffset;
        if (getBody() != null)
            headOffset = Main.getInstance().getAssets().getBodies().getBody(getBodyIndex()).getHeadOffset();
        else
            headOffset = new Position();

        if (dialog != null) {
            if (nombre.length() == 0 && this != Main.getInstance().getGameData().getChars().getNpcDialog())
                dialog = null;
            else {
                if (dialog.isAlive()) {
                    dialog.draw(batch, x + headOffset.getX() + 16, y + headOffset.getY());
                }
                else
                    dialog = null;
            }
        }
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

    public int getBando() {
        return bando;
    }

    public void setBando(int bando) {
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

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(String text) {
        this.dialog = new Dialog(text);
    }

    public void setDialog(String text, Color c) {
        this.dialog = new Dialog(text, c);
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
