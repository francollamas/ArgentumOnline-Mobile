package org.gszone.jfenix13.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.objects.UserAtributos;

import static org.gszone.jfenix13.general.FileNames.getViewDir;

public class CrearPjView extends View {
    public static final String ID = "crear_pj";

    @LmlActor("fondo") private VisWindow fondo;
    @LmlActor("nombre") private VisTextField tfNombre;
    @LmlActor("contraseña") private VisTextField tfContraseña;
    @LmlActor("mail") private VisTextField tfMail;

    @LmlActor("ciudad") private VisSelectBox<String> sbCiudad;
    @LmlActor("genero") private VisSelectBox<String> sbGenero;
    @LmlActor("raza") private VisSelectBox<String> sbRaza;

    @LmlActor("fuerza") private VisLabel lbFuerza;
    @LmlActor("agilidad") private VisLabel lbAgilidad;
    @LmlActor("inteligencia") private VisLabel lbInteligencia;
    @LmlActor("constitucion") private VisLabel lbConstitucion;
    @LmlActor("carisma") private VisLabel lbCarisma;


    @Override
    public String getViewId() { return ID; }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal(getViewDir(ID));
    }

    @Override
    public void show() {
        setTfFocus(tfNombre);
        Main.getInstance().getAssets().getAudio().playMusic(7);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        UserAtributos a = getGD().getCurrentUser().getAtributos();
        lbFuerza.setText("" + a.getFuerza());
        lbAgilidad.setText("" + a.getAgilidad());
        lbInteligencia.setText("" + a.getInteligencia());
        lbConstitucion.setText("" + a.getConstitucion());
        lbCarisma.setText("" + a.getCarisma());
    }

    @LmlAction("tirar-dados")
    public void tirarDados() {
        getClPack().writeThrowDices();
    }

    @LmlAction("crear-pj")
    public void crearPj() {
        // TODO: hacer validaciones del formulario.

        getClPack().writeLoginNewChar(tfNombre.getText(), tfContraseña.getText(), tfMail.getText(),
                sbRaza.getSelectedIndex() + 1, sbGenero.getSelectedIndex() + 1, sbCiudad.getSelectedIndex() + 1);
    }
}
