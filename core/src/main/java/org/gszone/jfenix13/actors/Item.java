package org.gszone.jfenix13.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.containers.GameData.ObjTypes;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.graphics.FontParameter;

/**
 * Corresponde a un Item del inventario del usuario, o del banco, comerciante, etc...
 */
public class Item extends Slot {

    private int obj;
    private int cantidad;
    private boolean equipado;
    private int grh;
    private ObjTypes tipo;
    private int maxHit;
    private int minHit;
    private int maxDef;
    private int minDef;
    private float precio;

    public Item() {
        super();
        setDisabled(true);
    }

    public void set(int obj, String nombre, int cant, boolean equip, int grh, int tipo,
                    int maxHit, int minHit, int maxDef, int minDef, float precio) {
        setDisabled(false);
        this.obj = obj;
        super.name = nombre;
        this.cantidad = cant;
        this.equipado = equip;
        this.grh = grh;
        if (tipo == 0) tipo = 1000; // si el tipo es 0 o 1000, lo cambio a tipo Cualquiera..
        this.tipo = tipo == 1000 ? ObjTypes.Cualquiera : ObjTypes.values()[tipo - 1];
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

        if (cantidad == 0) return;
        Drawer.drawGrh(batch, grh, x, y);

        FontParameter fp = new FontParameter("tahoma11bold");
        fp.setAlign(Align.left);
        Drawer.drawText(batch, "" + cantidad, x + 1, y + 1, fp);
    }

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEquipado() {
        return equipado;
    }

    public void setEquipado(boolean equipado) {
        this.equipado = equipado;
    }

    public int getGrh() {
        return grh;
    }

    public void setGrh(int grh) {
        this.grh = grh;
    }

    public ObjTypes getTipo() {
        return tipo;
    }

    public void setTipo(ObjTypes tipo) {
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
