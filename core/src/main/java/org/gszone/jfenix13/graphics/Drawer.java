package org.gszone.jfenix13.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.objects.GrhData;
import org.gszone.jfenix13.utils.Rect;
import java.util.Stack;

import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

/**
 * Contiene lo referido a la manipulacion de texturas
 *
 * dp: es un objeto que contiene los parametros por defecto para dibujar un gráfico.
 * fp: es un objeto que contiene los parametros por defecto para mostrar un texto
 * containerRect: pila con datos del rectángulo del contenedor actual, para dibujar pasando una posición relativa.
 * defColor: color del mundo por defecto
 */
public final class Drawer {
    public enum Alignment {LEFT, CENTER, RIGHT}


    public static Stack<Rectangle> containerRect;
    private static DrawParameter dp;
    private static FontParameter fp;
    private static Color defColor;

    static {
        dp = new DrawParameter();
        fp = new FontParameter();
        setDefColor(Color.WHITE);
        containerRect = new Stack();
        containerRect.push(new Rectangle(0, 0, getConfig().getVirtualWidth(),
                getConfig().getVirtualHeight()));
    }

    public static void pushScissors(Stage stage, float x, float y, float width, float height) {
        pushScissors(stage, new Rectangle(x, y, width, height));
    }

    /**
     * Hace que lo que se dibuje luego, se limite a un rectángulo determinado por rect.
     */
    public static void pushScissors(Stage stage, Rectangle rect) {
        float rectX = rect.getX(), rectY = rect.getY();
        rectX += containerRect.peek().getX();
        rectY += containerRect.peek().getY();
        rect.set(rectX, rectY, rect.getWidth(), rect.getHeight());

        Rectangle scissors = new Rectangle();
        Viewport vp = stage.getViewport();
        ScissorStack.calculateScissors(stage.getCamera(), vp.getScreenX(), vp.getScreenY(),
                vp.getScreenWidth(), vp.getScreenHeight(), stage.getBatch().getTransformMatrix(), rect, scissors);
        stage.getBatch().flush(); // para que lo que se dibuja antes se renderize antes de verse afectado por esto
        ScissorStack.pushScissors(scissors);
        containerRect.push(rect);
    }

    /**
     * Libera la limitación del rectángulo.
     */
    public static void popScissors(Stage stage) {
        stage.getBatch().flush(); // para mandar a dibujar lo que está entre scissors
        ScissorStack.popScissors();
        containerRect.pop();
        stage.getBatch().flush();
    }

    /**
     * Dibuja un Grh sin especificarle más nada
     */
    public static void drawGrh(Batch batch, Grh grh, float x, float y) {
        drawGrh(batch, grh, x, y, dp);
    }

    /**
     * Dibuja un Grh especificandole una serie de parámetros
     */
    public static void drawGrh(Batch batch, Grh grh, float x, float y, DrawParameter dp) {
        GrhData grhData = Main.getInstance().getAssets().getGrhs().getGrhData(grh.getIndex());
        if (grhData == null) return;

        if (dp.isAnimated()) {
            if (grh.getStarted() == 1) {
                grh.setFrame(grh.getFrame() + (getDelta() * grhData.getCantFrames() / grh.getSpeed()));
                if (grh.getFrame() >= grhData.getCantFrames() + 1) {
                    grh.setFrame(grh.getFrame() % grhData.getCantFrames());

                    if (grh.getLoops() != Grh.INF_LOOPS) {
                        if (grh.getLoops() > 0)
                            grh.setLoops((short)(grh.getLoops() - 1));
                        else
                            grh.setStarted((byte)0);
                    }
                }
            }
        }
        drawGrh(batch, grhData.getFrame((short)(grh.getFrame() - 1)), x, y, dp);
    }

    /**
     * Dibuja un Grh especificandole su Index, sin ningún otro parámetro.
     */
    public static void drawGrh(Batch batch, int index, float x, float y) {
        drawGrh(batch, index, x, y, dp);
    }

    /**
     * Dibuja un Grh especificándole su Index y una serie de parámetros.
     */
    public static void drawGrh(Batch batch, int index, float x, float y, DrawParameter dp) {
        GrhData grhDataCurrent = Main.getInstance().getAssets().getGrhs().getGrhData(index);
        if (grhDataCurrent == null) return;

        draw(batch, grhDataCurrent.getTR(), x, y, dp);
    }

    public static void draw(Batch batch, TextureRegion reg, float x, float y) {
        draw(batch, reg, x, y, dp);
    }

    /**
     * Dibuja una región de textura
     */
    public static void draw(Batch batch, TextureRegion reg, float x, float y, DrawParameter dp) {
        if (reg == null) return;

        if (dp.isCenter()) {
            float tileWidth = reg.getRegionWidth() / (float) getConfig().getTilePixelWidth();
            float tileHeight = reg.getRegionHeight() / (float) getConfig().getTilePixelHeight();

            if (tileWidth != 1f)
                x = x - reg.getRegionWidth() / 2 + getConfig().getTilePixelWidth() / 2;

            if (tileHeight != 1f)
               y = y - reg.getRegionHeight() + getConfig().getTilePixelHeight();
        }

        // Transforma el Y tomando el origen arriba, por un Y tomando el origen abajo
        y = (int)containerRect.peek().getHeight() - y - reg.getRegionHeight();

        // Lleva los puntos al sis. de coordenadas de la Pantalla
        x += containerRect.peek().getX();
        y += containerRect.peek().getY();

        Sprite sp = new Sprite(reg);

        // El color final depende del color por default
        Color[] c = dp.getColors();

        // TODO: agregar funciones de color al libGDX 1.9.6 y descomentar el bloque de abajo
        /*
        if (defColor == Color.WHITE || dp.isLight())
            // Para evitar multiplicar cada elemento del color por el color por defecto
            sp.setColors(c);
        else
            for (int i = 0; i < c.length; i++)
                sp.setVertColor(i, new Color(c[i].r * defColor.r, c[i].g * defColor.g, c[i].b * defColor.b,
                        c[i].a * defColor.a));

        sp.setAlphas(dp.getAlphas());
        */

        // TODO: si arregle lo de arriba, borrar este trozo:
        if (defColor == Color.WHITE || dp.isLight())
            sp.setColor(c[0]);
        else
            sp.setColor(new Color(c[0].r * defColor.r, c[0].g * defColor.g, c[0].b * defColor.b,
                    c[0].a * defColor.a));
        sp.setAlpha(dp.getAlphas()[0] * defColor.a);


        sp.setScale(dp.getScaleX(), dp.getScaleY());
        sp.setPosition(x, y);
        sp.rotate(dp.getRotation());
        sp.flip(dp.isFlipX(), dp.isFlipY());

        if (dp.isBlend())
            batch.setBlendFunction(dp.getBlendSrcFunc(), dp.getBlendDstFunc());

        sp.draw(batch);

        if (dp.isBlend())
            batch.setBlendFunction(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Devuelve una TextureRegion según el número de gráfico
     */
    public static TextureRegion getTextureRegion(int numGrafico, Rect r) {
        TextureRegion reg = new TextureRegion(Main.getInstance().getAssets().getTextures().getTexture(numGrafico));

        // Hardcode: arranco a partir del pixel (16,16), ya que los gráficos tienen bordes blancos (para evitar problemas gráficos)
        int padding = 16;

        // Verificamos si hay que buscar una región específica de la textura.
        if (r != null)
            if (reg != null)
                reg = new TextureRegion(reg, (int) r.getX1() + padding, (int) r.getY1() + padding, (int) r.getWidth(), (int) r.getHeight());

        return reg;
    }

    public static void drawText(Batch batch, String text, float x, float y) {
        drawText(batch, text, x, y, fp);
    }

    /**
     * Dibuja texto de una sola línea
     */
    public static void drawText(Batch batch, String text, float x, float y, FontParameter fp) {
        Array<String> arr = new Array<>();
        arr.add(text);
        drawText(batch, arr, x, y, fp);
    }

    public static void drawText(Batch batch, Array<String> text, float x, float y) {
        drawText(batch, text, x, y, fp);
    }

    /**
     * Dibuja texto de una o varias líneas
     */
    public static void drawText(Batch batch, Array<String> text, float x, float y, FontParameter fp) {

        // Transforma el Y tomando el origen arriba, por un Y tomando el origen abajo
        y = (int)containerRect.peek().getHeight() - y;

        // Lleva los puntos al sis. de coordenadas de la Pantalla
        x += containerRect.peek().getX();
        y += containerRect.peek().getY();

        BitmapFont font = fp.getFont();
        font.setColor(fp.getColor());


        int offset = 0;
        if (text.size == 1)
            font.draw(batch, text.get(0), x, y, 0, fp.getAlign(), false);
        else {
            // Calculo el ancho más grande
            BitmapFontCache fontCache = new BitmapFontCache(font);
            float maxWidth = 0;
            for (String t : text) {
                float tempWidth = fontCache.addText(t, x, y).width;
                fontCache.clear();
                if (tempWidth > maxWidth) maxWidth = tempWidth;
            }

            for (String t : text) {
                GlyphLayout layout = font.draw(batch, t, x - maxWidth / 2, y + offset, 0, Align.left, false);
                offset -= layout.height + 3;
            }
        }
    }

    /**
     * Obtiene el tiempo transcurrido entre dos frames y se multiplica por una constante
     * Se usa para calcular velocidades sin depender de los FPS.
     */
    public static float getDelta() {
        return Gdx.graphics.getDeltaTime() * getConfig().getBaseSpeed();
    }

    public static Color getDefColor() {
        return defColor;
    }

    // Setea el color por default, con valores de 0 a 255
    public static void setDefColor(int r, int g, int b, int a) {
        setDefColor((float)r/255, (float)g/255, (float)b/255, (float)a/255);
    }

    // Setea el color por default, con valores de 0 a 1
    public static void setDefColor(float r, float g, float b, float a) {
        setDefColor(new Color(r, g, b, a));
    }

    public static void setDefColor(Color color) {
        defColor = color;
    }

    public static Config getConfig() { return Main.getInstance().getConfig(); }
}
