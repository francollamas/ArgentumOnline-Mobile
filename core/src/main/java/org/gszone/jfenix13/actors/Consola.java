package org.gszone.jfenix13.actors;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.containers.FontTypes.FontTypeName;
import org.gszone.jfenix13.objects.FontType;


/**
 * Consola de mensajes
 */
public class Consola extends VisScrollPane {

    private int maxRows;
    private int maxChars;
    private VisTable tabla;

    public Consola() {
        this(500, 75);
    }

    public Consola(int maxRows, int maxChars) {
        super(new VisTable());
        tabla = (VisTable)getWidget();
        tabla.align(Align.topLeft);
        tabla.defaults().fill();
        tabla.setBackground("textfield-disabled");
        this.maxRows = maxRows;
        this.maxChars = maxChars;
    }

    public void addMessage(String msg) {
        addMessage(msg, FontTypeName.Talk, true);
    }

    public void addMessage(String msg, FontTypeName ftName) {
        addMessage(msg, ftName, true);
    }

    public void addMessage(String msg, boolean newLine) {
        addMessage(msg, FontTypeName.Talk, newLine);
    }

    public void addMessage(String msg, FontTypeName ftName, boolean newLine) {
        if (msg.length() == 0) return;

        if (tabla.getRows() >= maxRows)
            tabla.clear(); // TODO: no limpiar todo de una, sino, ir borrando lo sobrante de arriba

        // Borro todos los retornos de carro, ya que no sirven y muestran un caracter feo...
        StringBuilder sb = new StringBuilder(msg.length() + 10);
        sb.append(msg);
        int index = 0;
        while (index != -1) {
            index = sb.indexOf("\r", index);
            if (index != -1)
                sb.deleteCharAt(index);
        }

        // Creo un array con todas las líneas
        Array<StringBuilder> arr = getLineas(sb.toString());

        // Vacío el constructor de strings para llenarlo con las nuevas líneas
        sb = new StringBuilder(sb.length() + 10);

        // Crea más líneas si es necesario para que prácticamente el scroll tenga que ser solo vertical.
        for (int i = 0; i < arr.size; i++) {
            StringBuilder s = arr.get(i);
            int pos = 0;
            while (s.length() - pos > maxChars) {
                String prov = s.substring(pos, pos + maxChars);
                int lastSpace = prov.lastIndexOf(" ");
                if (lastSpace != -1) {
                    prov = prov.substring(0, lastSpace);
                    pos++;
                }
                sb.append(prov.trim() + '\n');
                pos += prov.length();
            }
            sb.append(s.substring(pos).trim());
            if (i != arr.size - 1) sb.append('\n');
        }

        // Busco la fontType
        FontType ft = Main.getInstance().getGameData().getFontTypes().getFontType(ftName.ordinal());
        System.out.println(ft);

        // Creo el label
        VisLabel l = new VisLabel(sb.toString(), ft.getColor());
        l.setStyle(VisUI.getSkin().get(ft.getStyle(), Label.LabelStyle.class));
        l.setAlignment(Align.topLeft);
        l.layout();

        // Agrego a la tabla y actualizo el scroll
        tabla.add(l);
        if (newLine) tabla.row();
        layout();
        setScrollPercentY(1f);
    }

    /**
     * Obtiene un array de líneas
     */
    private Array<StringBuilder> getLineas(String sb) {

        Array<StringBuilder> arr = new Array();
        int firstIndex = 0;
        int secondIndex = 0;
        StringBuilder s;
        while (secondIndex != -1) {
            secondIndex = sb.indexOf("\n", firstIndex);
            if (secondIndex != -1) {
                s = new StringBuilder();
                s.append(sb.substring(firstIndex, secondIndex));
                arr.add(s);
                firstIndex = secondIndex + 1;
            }
        }
        s = new StringBuilder();
        s.append(sb.substring(firstIndex));
        arr.add(s);

        return arr;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
