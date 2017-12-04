package org.gszone.jfenix13.actors;

import org.gszone.jfenix13.containers.GameData.ObjTypes;

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
        super(32, 32);
    }

    public void set(int obj, String nombre, int cant, boolean equip, int grh, ObjTypes tipo,
                    int maxHit, int minHit, int maxDef, int minDef, float precio) {
        this.obj = obj;
        super.name = nombre;
        this.cantidad = cant;
        this.equipado = equip;
        this.grh = grh;
        this.tipo = tipo;
        this.maxHit = maxHit;
        this.minHit = minHit;
        this.maxDef = maxDef;
        this.minDef = minDef;
        this.precio = precio;
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
