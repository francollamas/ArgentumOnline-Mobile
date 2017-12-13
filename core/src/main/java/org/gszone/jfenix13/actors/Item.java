package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.sun.java.swing.plaf.motif.MotifTreeUI;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.graphics.FontParameter;

import static org.gszone.jfenix13.containers.GameData.ObjTypes.*;

/**
 * Representa un Item del Inventario
 * Es un slot pero con más características (defensa, golpe, equipado, precio, tipo...)
 */
public class Item extends Slot {
    private static final Color COL_EQUIPADO = new Color(1, 1, 0, 0.1f);

    private int obj;
    private boolean equipado;
    private GameData.ObjTypes tipo;
    private int maxHit;
    private int minHit;
    private int maxDef;
    private int minDef;
    private float precio;

    public Item() {
        Config c = Main.getInstance().getConfig();
        setGrhSize(c.getTilePixelWidth(), c.getTilePixelHeight());
    }

    public void set(int obj, String nombre, int cant, boolean equip, int grh, int tipo,
                    int maxHit, int minHit, int maxDef, int minDef, float precio) {
        this.obj = obj;
        setNombre(nombre);
        setCantidad(cant);
        setEquipado(equip);
        setGrh(grh);
        if (tipo == 0) tipo = 1000; // si el tipo es 0 o 1000, lo cambio a tipo Cualquiera..
        this.tipo = tipo == 1000 ? GameData.ObjTypes.Cualquiera : GameData.ObjTypes.values()[tipo - 1];
        this.maxHit = maxHit;
        this.minHit = minHit;
        this.maxDef = maxDef;
        this.minDef = minDef;
        this.precio = precio;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = Main.getInstance().getConfig().getVirtualHeight() - getY() - getHeight();

        if (getCantidad() == 0 || !isVisible()) return;

        FontParameter fp = new FontParameter("tahoma11bold");
        fp.setAlign(Align.left);
        Drawer.drawText(batch, "" + getCantidad(), x + 1, y + 1, fp);
    }

    @Override
    public void exchangeWith(Slot s) {
        // Obtengo los índices de ambos slots.
        Integer[] arr = getGrid().getIndexs(this, s);

        // Solicito intercambiarlos...
        if (arr != null) {
            Main.getInstance().getConnection().getClPack().writeMoveItem(arr[0], arr[1]);
        }
    }

    @Override
    protected void onDblClick() {
        // TODO: verificar que REALMENTE estén TODOS los objetos equipables, y que no haya ninguno de otro tipo
        if (tipo == Armadura || tipo == Casco || tipo == Escudo || tipo == Weapon
                || tipo == Anillo || tipo == Instrumentos || tipo == Mochilas)
            equipItem();
        else
            usarItem();
    }

    private void usarItem() {
        // TODO: muchas comprobaciones (intervalos, etc, etc).
        Main.getInstance().getConnection().getClPack().writeUseItem(getGrid().indexOf(this));
    }

    private void equipItem() {
        // TODO: muchas comprobaciones (intervalos, etc, etc).
        Main.getInstance().getConnection().getClPack().writeEquipItem(getGrid().indexOf(this));
    }

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }

    public boolean isEquipado() {
        return equipado;
    }

    public void setEquipado(boolean equipado) {
        this.equipado = equipado;

        if (equipado)
            setColBackground(COL_EQUIPADO);
        else
            removeColBackground();
    }

    public GameData.ObjTypes getTipo() {
        return tipo;
    }

    public void setTipo(GameData.ObjTypes tipo) {
        this.tipo = tipo;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    public int getMinHit() {
        return minHit;
    }

    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    public int getMaxDef() {
        return maxDef;
    }

    public void setMaxDef(int maxDef) {
        this.maxDef = maxDef;
    }

    public int getMinDef() {
        return minDef;
    }

    public void setMinDef(int minDef) {
        this.minDef = minDef;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
