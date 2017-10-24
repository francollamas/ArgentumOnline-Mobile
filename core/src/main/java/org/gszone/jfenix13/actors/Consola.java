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
import org.gszone.jfenix13.utils.StrUtils;

/**
 * Consola de mensajes
 */
public class Consola extends VisScrollPane {

    private int maxRows;
    private int maxChars;
    private VisTable tabla;

    public Consola() {
        this(200, 75);
    }

    public Consola(int maxRows, int maxChars) {
        super(new VisTable());
        tabla = (VisTable)getWidget();
        tabla.align(Align.topLeft);
        tabla.defaults().fill();
        tabla.padLeft(2f).padRight(2f);
        //tabla.setBackground("textfield-disabled");
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
        Array<String> arr = StrUtils.getLineas(sb.toString());

        // Vacío el constructor de strings para llenarlo con las nuevas líneas
        sb = new StringBuilder(sb.length() + 10);

        // Crea más líneas si es necesario para que prácticamente el scroll tenga que ser solo vertical.
        for (int i = 0; i < arr.size; i++) {
            String s = arr.get(i);
            sb.append(StrUtils.getFormattedText(s, maxChars));
            if (i != arr.size - 1) sb.append('\n');
        }

        // Busco la fontType
        FontType ft = Main.getInstance().getGameData().getFontTypes().getFontType(ftName.ordinal());

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

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
