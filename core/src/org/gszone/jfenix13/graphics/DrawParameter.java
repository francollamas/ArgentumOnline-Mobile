package org.gszone.jfenix13.graphics;

import com.badlogic.gdx.graphics.Color;

import static com.badlogic.gdx.graphics.GL20.GL_ONE;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

/**
 * Contiene los parametros para usar al renderizar
 *
 * color: color de cada vértice
 * alpha: cantidad de transparencia que luego se combina con el color.
 * light: indica si brilla o no (si cambia el clima, esto no se ve afectado
 * center: indica si el sprite va centrado
 * animated: si es una animación, indica si se reproduce o no.
 * rotation: indica la inclinación. Se expresa en grados (va de 0f a 360f)
 * scaleX, scaleY: indica un aumento de tamaño en X o Y, valor > 1 para mas grande, < 1 para mas chico.
 * flipX, flipY: indica si el sprite se voltea de forma horizontal o vertical.
 * blend: indica si se le aplica el efecto de blend al sprite
 * blendSrcFunc, blendDstFunc: variar para cambiar el efecto de blend por defecto, por otro.
 */
public class DrawParameter {
    private Color[] color;
    private float[] alpha;
    private boolean light;
    private boolean center;
    private boolean animated;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private boolean flipX;
    private boolean flipY;
    private boolean blend;
    private int blendSrcFunc;
    private int blendDstFunc;

    /**
     * Crea el conjunto de Parámetros asignando a cada uno los valores más comunes
     */
    public DrawParameter() {
        setColor(1f, 1f, 1f);
        setAlpha(1f);

        this.scaleX = 1;
        this.scaleY = 1;
        this.blendSrcFunc = GL_SRC_ALPHA;
        this.blendDstFunc = GL_ONE;
    }

    /**
     * Devuelve el array de colores
     */
    public Color[] getColors() {
        return color;
    }

    /**
     * Setea un array de colores
     */
    public void setColors(Color[] color) {
        this.color = color;
    }

    /**
     * Devuelve el color de uno de los vértices (útil cuando todos los vertices tienen el mismo color)
     */
    public Color getColor() {
        return color[0];
    }

    /**
     * Setea un mismo color para todos los vértices, especificando Color
     */
    public void setColor(Color color) {
        setColor(color.r, color.g, color.b);
    }

    public void setColor(int r, int g, int b) {
        setColor((float)r/255, (float)g/255, (float)b/255);
    }

    /**
     * Setea un mismo color para todos los vértices, especificando cada componente
     */
    public void setColor(float r, float g, float b) {
        this.color = new Color[4];
        for (int i = 0; i < this.color.length; i++) {
            this.color[i] = new Color(r, g, b, 1);
        }
    }

    /**
     * Devuelve el color de un vértice específico
     */
    public Color getVertColor(int vert) {
        return color[vert];
    }

    /**
     * Setea el color de un vértice específico, especificando Color
     */
    public void setVertColor(int vert, Color color) {
        this.color[vert] = color;
    }

    public void setVertColor(int vert, int r, int g, int b) {
        setVertColor(vert, (float)r/255, (float)g/255, (float)b/255);
    }

    /**
     * Setea el color de un vértice específico, especificando cada componente
     */
    public void setVertColor(int vert, float r, float g, float b) {
        this.color[vert] = new Color(r, g, b, 1);
    }

    public float[] getAlphas() {
        return alpha;
    }

    /**
     * Devuelve el conjunto de alphas de todos los vértices
     */
    public void setAlpha(int alpha) {
        setAlpha((float)alpha/255);
    }

    /**
     * Setea un mísmo alpha para todos los vértices
     */
    public void setAlpha(float alpha) {
        this.alpha = new float[4];
        for (int i = 0; i < this.alpha.length; i++) {
            this.alpha[i] = alpha;
        }
    }

    public void setAlphas(float[] alphas) {
        for (int i = 0; i < alpha.length; i++) {
            setVertAlpha(i, alphas[i]);
        }
    }

    public void setVertAlpha(int vert, int alpha) {
        setVertAlpha(vert, (float)alpha/255);
    }

    /**
     * Setea un alpha a un vértice específico
     */
    public void setVertAlpha(int vert, float alpha) {
        this.alpha[vert] = alpha;
    }

    /**
     * Devuelve el alpha de un vértice
     */
    public float getVertAlpha(int vert) {
        return alpha[vert];
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }


    public float getRotation() {
        return rotation;
    }

    /**
     * Setea los grados del ángulo de inclinación
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    /**
     * Setea la misma escala para ambos ejes
     */
    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    /**
     * Voltea el sprite de forma horizontal y vertical simultaneamente.
     */
    public void setFlip(boolean flipXY) {
        this.flipX = flipXY;
        this.flipY = flipXY;
    }

    public boolean isFlip() {
        return this.flipX && this.flipY;
    }

    public boolean isBlend() {
        return blend;
    }

    public void setBlend(boolean blend) {
        this.blend = blend;
    }

    public int getBlendSrcFunc() {
        return blendSrcFunc;
    }

    public void setBlendSrcFunc(int blendSrcFunc) {
        this.blendSrcFunc = blendSrcFunc;
    }

    public int getBlendDstFunc() {
        return blendDstFunc;
    }

    public void setBlendDstFunc(int blendDstFunc) {
        this.blendDstFunc = blendDstFunc;
    }
}
