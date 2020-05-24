package org.gszone.jfenix13.utils;

import org.gszone.jfenix13.Main;

/**
 * Clase que representa a un rectángulo
 *
 * (x1, y1) ---------------------- (x1 + width, y1)
 *     |                                    |
 *     |                                    |
 *     |                                    |
 *     |                                    |
 * (x1, y1 + height) ------------ (x1 + width, y1 + height)
 */
public class Rect {
    protected float x1, y1;
    protected float width, height;

    public Rect() {

    }

    public Rect(float x1, float y1, float width, float height) {
        setX1(x1);
        setY1(y1);
        setWidth(width);
        setHeight(height);
    }


    public boolean isPointIn(Position pos) {
        return isPointIn(pos.getX(), pos.getY());
    }

    /**
     * Verifica si un punto está dentro del rectángulo principal
     * @param x: coordenada en X
     * @param y: coordenada en Y
     * @return
     */
    public boolean isPointIn(float x, float y) {
        return x >= x1 && x <= getX2() && y >= y1 && y <= getY2();
    }




    /**
     * Verifica si al menos una parte de un rectángulo está en el rectángulo principal.
     * @param rect: rectángulo a comparar
     * @return
     */
    public boolean isRectIn(Rect rect) {
        return isRectIn(rect.getX1(), rect.getY1(), rect.getWidth(), rect.getHeight());
    }

    /**
     * Verifica si al menos una parte de un rectángulo está en el rectángulo principal.
     * @param x1: coordenada en X
     * @param y1: coordenada en Y
     * @param width: ancho
     * @param height: alto
     * @return
     */
    public boolean isRectIn(float x1, float y1, float width, float height) {
        float x2 = x1 + width;
        float y2 = y1 + height;
        return isPointIn(x1, y1) || isPointIn(x2, y2) ||
                ((x1 < this.x1 || y1 < this.y1) && (x2 > getX2() || y2 > getY2()));
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public void addY1(float addY) {
        this.y1 += addY;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public void addX1(float addX) {
        this.x1 += addX;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void addWidth(float addW) {
        this.width += addW;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void addHeight(float addH) {
        this.height += addH;
    }

    public float getY2() {
        return getY1() + getHeight();
    }

    public void setY2(float bottom) {
        setHeight(bottom - getY1());
    }

    public void addY2(float addY) {
        addHeight(addY);
    }

    public float getX2() {
        return getX1() + getWidth();
    }

    public void setX2(float right) {
        setWidth(right - getX1());
    }

    public void addX2(float addX) {
        addWidth(addX);
    }

    public float getTileWidth() {
        return getWidth() / Main.getInstance().getConfig().getTilePixelWidth();
    }

    public float getTileHeight() {
        return getHeight() / Main.getInstance().getConfig().getTilePixelHeight();
    }
}