package org.gszone.jfenix13.general;

import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.utils.StrUtils;


import static org.gszone.jfenix13.containers.FontTypes.FontTypeName.*;
import static org.gszone.jfenix13.utils.StrUtils.*;
import static org.gszone.jfenix13.utils.StrUtils.TipoDato.*;

/**
 * Analiza los comandos para enviar al servidor
 */
public class Commands {

    // Usado para el /MOD
    public enum EditOptions {Oro, Exp, Cuerpo, Cabeza, CiuMatados, CriMatados, Nivel, Clase, Skills, SkillsLibres,
                                Nobleza, Asesino, Sexo, Raza, AgregarOro}

    public Commands() {

    }

    /**
     * Analiza y ejecuta un comando
     */
    public void parse(String texto) {
        ClientPackages c = getClPack();

        // Si es texto, lo envía y termina ahí.
        if (texto.length() > 0 && !texto.substring(0, 1).equals("/")) {
            c.writeTalk(texto);
            return;
        }

        // SI ES COMANDO...
        if (texto.trim().length() == 0) return;
        int separador = texto.indexOf(" ");

        // Comando en mayúscula
        String cmd = separador != -1 ? texto.substring(0, separador).toUpperCase() : texto.toUpperCase();

        // Todos los parámetros juntos, con todos sus espacios y sin pasarlo a mayúsculas
        String resto = separador != -1 ? texto.substring(separador, texto.length()).trim() : "";

        // Parámetros por separado, en mayúscula
        String[] params = StrUtils.getPalabras(resto.toUpperCase()).toArray(String.class);

        switch (cmd) {
            case "/ONLINE":
                c.writeOnline();
                break;
            case "/SALIR":
                cmdQuit();
                break;
            case "/MEDITAR":
                cmdMeditate();
                break;
            case "/PING":
                c.writePing();
                break;
            case "/RESUCITAR":
                c.writeResucitate();
                break;
            case "/CURAR":
                c.writeHeal();
                break;
            case "/AYUDA":
                c.writeHelp();
                break;
            case "/EST":
                c.writeRequestStats();
                break;
            case "/COMERCIAR":
                cmdCommerce();
                break;
            case "/BOVEDA":
                cmdBankStart();
                break;
            case "/ENLISTAR":
                c.writeEnlist();
                break;
            case "/INFORMACION":
                c.writeInformation();
                break;
            case "/RECOMPENSA":
                c.writeReward();
                break;
            case "/UPTIME":
                c.writeUpTime();
                break;
            case "/ENCUESTA":
                cmdInquiry(params);
                break;
            case "/GO":
                cmdGo(params);
                break;
            case "/CT":
                cmdCrearTelep(params);
                break;
            case "/DT":
                c.writeTeleportDestroy();
                break;
            case "/MOD":
                cmdMod(params);
                break;
            case "/SUM":
                cmdSumonear(resto);
                break;
            case "/IRA":
                cmdIrA(resto);
                break;
            case "/RMSG":
                cmdRmsg(resto);
                break;
            case "/ITEM":
                cmdCreateItem(params);
                break;
            case "/DEST":
                c.writeDestroyItems();
                break;
            case "/BUSCAR":
                cmdBuscar(resto);
                break;
            case "/SEGUIR":
                c.writeNpcFollow();
                break;
            default:
                getC().addMessage("Comando inexistente.", Info);
                break;
        }
    }

    private void cmdQuit() {
        if (getU().isParalizado()) {
            getC().addMessage(bu("msg.paralizado"), Warning);
            return;
        }
        getClPack().writeQuit();
    }

    private void cmdMeditate() {
        if (isMuerto()) return;
        if (getU().getStats().getMana() == getU().getStats().getMaxMana()) return;
        getClPack().writeMeditate();
    }

    private void cmdCommerce() {
        if (isMuerto()) return;
        if (getU().isComerciando()) {
            getC().addMessage("Ya estás comerciando", Info);
            return;
        }
        getClPack().writeCommerceStart();
    }

    private void cmdBankStart() {
        if (isMuerto()) return;
        getClPack().writeBankStart();
    }

    private void cmdInquiry(String[] params) {
        Object[] p = validar(params, 0, TByte);

        if (p == null)
            incorrectMsg("/ENCUESTA [VOTO]");

        else if (p.length == 0)
            // Abrir encuesta
            getClPack().writeInquiry();
        else
            // Votar
            getClPack().writeInquiryVote((byte)p[0]);
    }

    private void cmdGo(String[] params) {
        Object[] p = validar(params, TShort);

        if (p == null)
            incorrectMsg("/GO MAPA");
        else
            getClPack().writeWarpToMap((short)p[0]);
    }

    private void cmdCrearTelep(String[] params) {
        Object[] p = validar(params, 3, TShort, TByte, TByte, TByte);

        if (p == null)
            incorrectMsg("/CT MAPA X Y [RADIO]");
        else
            getClPack().writeTeleportCreate((short)p[0], (byte)p[1], (byte)p[2], p.length == 4 ? (byte)p[3] : 0);
    }

    private void cmdMod(String[] params) {
        Object[] p = validar(params, 3, TString, TString, TString, TString);

        if (p == null)
            incorrectMsg("/MOD NOMBRE CARACT1 [CARACT2] VALOR");

        else {
            EditOptions caract;
            switch ((String)p[1]) {
                case "BODY":
                    caract = EditOptions.Cuerpo;
                    break;
                case "HEAD":
                    caract = EditOptions.Cabeza;
                    break;
                case "ORO":
                    caract = EditOptions.Oro;
                    break;
                case "LEVEL":
                    caract = EditOptions.Nivel;
                    break;
                case "SKILLS":
                    caract = EditOptions.Skills;
                    break;
                case "SKILLSLIBRES":
                    caract = EditOptions.SkillsLibres;
                    break;
                case "CLASE":
                    caract = EditOptions.Clase;
                    break;
                case "EXP":
                    caract = EditOptions.Exp;
                    break;
                case "CRI":
                    caract = EditOptions.CriMatados;
                    break;
                case "CIU":
                    caract = EditOptions.CiuMatados;
                    break;
                case "NOB":
                    caract = EditOptions.Nobleza;
                    break;
                case "ASE":
                    caract = EditOptions.Asesino;
                    break;
                case "SEX":
                    caract = EditOptions.Sexo;
                    break;
                case "RAZA":
                    caract = EditOptions.Raza;
                    break;
                case "AGREGAR":
                    caract = EditOptions.AgregarOro;
                    break;
                default:
                    caract = null;
            }

            if (caract != null)
                getClPack().writeEditChar((String)p[0], caract, (String)p[2], p.length == 4 ? (String)p[3] : "");
        }
    }

    private void cmdSumonear(String nombre) {
        if (nombre.length() == 0)
            incorrectMsg("/SUM NOMBRE");
        else
            getClPack().writeSummonChar(nombre);
    }

    private void cmdIrA(String nombre) {
        if (nombre.length() == 0)
            incorrectMsg("/IRA NOMBRE");
        else
            getClPack().writeGoToChar(nombre);
    }

    private void cmdRmsg(String texto) {
        if (texto.length() == 0)
            incorrectMsg("/RMSG TEXTO");
        else
            getClPack().writeServerMessage(texto);
    }

    private void cmdCreateItem(String[] params) {
        Object[] p = validar(params, TShort, TShort);

        if (p == null)
            incorrectMsg("/ITEM NÚMERO [CANTIDAD]");
        else
            getClPack().writeCreateItem((short)p[0], p.length == 2 ? (short)p[1] : 1);
    }

    private void cmdBuscar(String texto) {
        if (texto.length() == 0)
            incorrectMsg("/BUSCAR NOMBRE_ITEM");
        else if (texto.length() == 1)
            getC().addMessage("Sé más específico.");
        else
            getClPack().writeSearchObjs(texto);
    }

    private void incorrectMsg(String comando) {
        getC().addMessage("Comando incorrecto. Utilice " + comando + ".", Info);
    }

    /**
     * Muestra un mensaje en consola si el usuario está muerto.
     * (se puso en un método separado para no escribir el código de mostrar mensaje tantas veces)
     *
     * @return indica si está muerto
     */
    private boolean isMuerto() {
        if (getU().isMuerto()) {
            getC().addMessage(bu("msg.muerto"), Info);
            return true;
        }
        return false;
    }

    public String bu(String key) { return Main.getInstance().getBundle().get(key); }
    public ClientPackages getClPack() { return Main.getInstance().getConnection().getClPack(); }
    public GameData getGD() { return Main.getInstance().getGameData(); }
    public User getU() { return getGD().getCurrentUser(); }
    public Consola getC() { return getGD().getConsola(); }
}



