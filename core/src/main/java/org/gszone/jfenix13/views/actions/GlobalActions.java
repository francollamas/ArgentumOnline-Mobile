package org.gszone.jfenix13.views.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.util.LmlUtilities;
import org.gszone.jfenix13.Main;

import java.util.Locale;

public class GlobalActions implements ActionContainer {

    /**
     * Cambia el idioma
     */
    @LmlAction("setLocale")
    public void setLocale(final Actor actor) {
        final String localeId = LmlUtilities.getActorId(actor);
        final I18NBundle currentBundle = Main.getInstance().getParser().getData().getDefaultI18nBundle();
        if (currentBundle.getLocale().getLanguage().equalsIgnoreCase(localeId)) {
            // mismo lenguaje
            return;
        }
        Main.getInstance().getParser().getData()
                .setDefaultI18nBundle(I18NBundle.createBundle(Gdx.files.internal("i18n/bundle"), new Locale(localeId)));
        Main.getInstance().reloadViews();
    }
}
