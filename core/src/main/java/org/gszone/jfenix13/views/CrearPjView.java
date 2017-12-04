package org.gszone.jfenix13.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.*;
import org.gszone.jfenix13.managers.CrearPjManager;
import org.gszone.jfenix13.objects.UserAtributos;

import static org.gszone.jfenix13.utils.Actors.*;

public class CrearPjView extends View {

    public CrearPjView() {
        super(new CrearPjManager());
    }
    public CrearPjManager getGestor() { return (CrearPjManager)gestor; }

    private VisTextField tfNombre;
    private VisTextField tfContraseña;
    private VisTextField tfRContraseña;
    private VisTextField tfMail;
    private VisTextField tfRMail;

    private VisSelectBox<String> sbCiudad;
    private VisSelectBox<String> sbGenero;
    private VisSelectBox<String> sbRaza;

    private VisImageButton ibTirarDados;

    private VisLabel lbFuerza;
    private VisLabel lbAgilidad;
    private VisLabel lbInteligencia;
    private VisLabel lbConstitucion;
    private VisLabel lbCarisma;

    private VisTextButton tbAtras;
    private VisTextButton tbCrearPj;

    @Override
    public void show() {
        super.show();

        // Definición de los elementos de la pantalla
        VisWindow w = newWindow(bu("cp.title"), null, false, false);
            Table t = newTable(w).top().padRight(40).getActor();
                newLabel(t, bu("cp.name"), "col-title", "smallgradient").left().spaceBottom(0).row();
                tfNombre = newTextField(t, "", "", "bold").width(180).getActor(); t.row();
                tfNombre.setMaxLength(30);

                newLabel(t, bu("cp.pass"), "col-title", "smallgradient").left().spaceBottom(0).row();
                tfContraseña = newTextField(t, "", "", "bold", true).fill().getActor(); t.row();
                tfContraseña.setMaxLength(100);

                newLabel(t, bu("cp.rpass"), "col-title", "smallgradient").left().spaceBottom(0).row();
                tfRContraseña = newTextField(t, "", "", "bold", true).fill().getActor(); t.row();
                tfRContraseña.setMaxLength(100);

                newLabel(t, bu("cp.mail"), "col-title", "smallgradient").left().spaceBottom(0).row();
                tfMail = newTextField(t, "", "", "bold").fill().getActor(); t.row();
                tfMail.setMaxLength(100);

                newLabel(t, bu("cp.rmail"), "col-title", "smallgradient").fill().left().spaceBottom(0).row();
                tfRMail = newTextField(t, "", "", "bold").fill().getActor(); t.row();
                tfRMail.setMaxLength(100);

            Table t2 = newTable(w).top().padRight(40).getActor();
                newLabel(t2, bu("cp.born"), "col-title", "smallgradient").left().spaceBottom(0).row();
                sbCiudad = newSelectBox(t2, "Ullathorpe", "Nix", "Banderbill", "Lindos", "Arghâl").width(130).getActor(); t2.row();

                newLabel(t2, bu("cp.gender"), "col-title", "smallgradient").left().spaceBottom(0).row();
                sbGenero = newSelectBox(t2, bu("male"), bu("female")).fill().getActor(); t2.row();

                newLabel(t2, bu("cp.race"), "col-title", "smallgradient").left().spaceBottom(0).row();
                sbRaza = newSelectBox(t2, bu("human"), bu("elf"), bu("dark-elf"), bu("gnome"), bu("dwarf")).fill().getActor();

            Table t3 = newTable(w).top().getActor(); w.row();
                newLabel(t3, bu("throw-dices"), "col-title", "smallgradient").left().spaceBottom(0).row();
                ibTirarDados = newImageButton(t3, "dices").left().getActor(); t3.row();

                newLabel(t3, bu("attrib"), "col-title", "smallgradient").spaceBottom(10).left().row();

                newLabel(t3, bu("strength")).padRight(8).left();
                lbFuerza = newLabel(t3, "0").width(25).getActor(); t3.row();

                newLabel(t3, bu("agility")).padRight(8).left();
                lbAgilidad = newLabel(t3, "0").width(25).getActor(); t3.row();

                newLabel(t3, bu("intelligence")).padRight(8).left();
                lbInteligencia = newLabel(t3, "0").width(25).getActor(); t3.row();

                newLabel(t3, bu("constitution")).padRight(8).left();
                lbConstitucion = newLabel(t3, "0").width(25).getActor(); t3.row();

                newLabel(t3, bu("charisma")).padRight(8).left();
                lbCarisma = newLabel(t3, "0").width(25).getActor(); t3.row();

            Table t4 = newTable(w).padTop(40).fillX().colspan(3).getActor();
                tbAtras = newTextButton(t4, bu("back")).expandX().left().getActor();
                tbCrearPj = newTextButton(t4, bu("cp.create")).expandX().right().getActor();


        fitWindow(w);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK)
                    getGestor().back();
                return super.keyUp(event, keycode);
            }
        });

        ibTirarDados.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                getGestor().solicTirarDados();
            }
        });

        tbAtras.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                getGestor().back();
            }
        });

        tbCrearPj.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                getGestor().crearPj(tfNombre.getText().trim(), tfContraseña.getText(), tfRContraseña.getText(),
                        tfMail.getText().trim(), tfRMail.getText().trim(),
                        sbRaza.getSelectedIndex() + 1, sbGenero.getSelectedIndex() + 1, sbCiudad.getSelectedIndex() + 1);
            }
        });

        setFocus(tfNombre);
        getGestor().playMusic(7);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        UserAtributos a = getGestor().getAttribActuales();
        lbFuerza.setText("" + a.getFuerza());
        lbAgilidad.setText("" + a.getAgilidad());
        lbInteligencia.setText("" + a.getInteligencia());
        lbConstitucion.setText("" + a.getConstitucion());
        lbCarisma.setText("" + a.getCarisma());
    }


}
