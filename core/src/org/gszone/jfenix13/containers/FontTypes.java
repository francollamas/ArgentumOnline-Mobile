package org.gszone.jfenix13.containers;

import com.badlogic.gdx.graphics.Color;
import org.gszone.jfenix13.objects.FontType;
import static org.gszone.jfenix13.containers.FontTypes.FontTypeName.*;
import static org.gszone.jfenix13.containers.Colors.*;

/**
 * Contiene los distintos tipos de fuente para los mensajes
 *
 * fontTypes: array de tipos de fuente.
 */
public class FontTypes {
    public enum FontTypeName {
        Talk,
        Fight,
        Warning,
        Info,
        InfoBold,
        Ejecucion,
        Party,
        Veneno,
        Guild,
        Server,
        GuildMsg,
        Consejo,
        ConsejoCaos,
        ConsejoVesA,
        ConsejoCaosVesA,
        Centinela,
        GmMsg,
        Gm,
        Citizen,
        Conse,
        Dios,
        Newbie,
        Neutral,
        GuildWelcome,
        GuildLogin
    }

    private FontType[] fontTypes;

    public FontTypes() {
        fontTypes = new FontType[FontTypeName.values().length];

        // Defino un estilo alternativo
        String s = "bold";

        // Cargo las fuentes. Si no paso parámetro de estilo, asume el estilo por defecto.
        load(Talk, newColor(255, 255, 255));
        load(Fight, newColor(255, 0, 0), s);
        load(Warning, newColor(32, 51, 223), s);
        load(Info, newColor(65, 190, 156));
        load(InfoBold, newColor(65, 190, 156), s);
        load(Ejecucion, newColor(130, 130, 130), s);
        load(Party, newColor(255, 180, 250));
        load(Veneno, newColor(0, 255, 0));
        load(Guild, newColor(255, 255, 255), s);
        load(Server, newColor(0, 185, 0));
        load(GuildMsg, newColor(228, 199, 27));
        load(Consejo, newColor(130, 130, 255), s);
        load(ConsejoCaos, newColor(255, 60, 0), s);
        load(ConsejoVesA, newColor(0, 200, 255), s);
        load(ConsejoCaosVesA, newColor(255, 50, 0), s);
        load(Centinela, newColor(0, 0, 255), s);
        load(GmMsg, newColor(255, 255, 255));
        load(Gm, newColor(255, 128, 32), s);
        load(Citizen, newColor(0, 0, 200), s);
        load(Conse, newColor(30, 150, 30), s);
        load(Dios, newColor(250, 250, 150), s);
        load(Newbie, newColor(100, 200, 100), s);
        load(Neutral, newColor(180, 180, 0), s);
        load(GuildWelcome, newColor(255, 201, 14), s);
        load(GuildLogin, newColor(255, 255, 128));
    }

    private void load(FontTypeName ftName, Color c) {
        fontTypes[ftName.ordinal()] = new FontType(c);
    }

    private void load(FontTypeName ftName, Color c, String style) {
        fontTypes[ftName.ordinal()] = new FontType(c, style);
    }

    public FontType getFontType(int num) {
        return fontTypes[num];
    }
}
