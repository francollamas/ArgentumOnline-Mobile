package org.gszone.jfenix13.general;

import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.objects.User;


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
        if (texto.trim().length() == 0) return;
        ClientPackages c = getClPack();

        // Si es texto, lo envía y termina ahí.
        if (!texto.substring(0, 1).equals("/")) {
            c.writeTalk(texto.trim());
            return;
        }

        // SI ES COMANDO...

        int separador = texto.indexOf(" ");

        // Comando en mayúscula
        String cmd = separador != -1 ? texto.substring(0, separador).toUpperCase() : texto.toUpperCase();

        // Todos los parámetros juntos, con todos sus espacios y sin pasarlo a mayúsculas
        String resto = separador != -1 ? texto.substring(separador, texto.length()).trim() : "";

        // Parámetros por separado, en mayúscula
        String[] params = getPalabras(resto.toUpperCase()).toArray(String.class);

        // TODO: IMPORTANTÍSIMO!! validar parámetros.. (ej, si se carga un número muy grande y se pasa a int, crashea)
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
        Object[] p = validar(params, TByte);

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

        else if (p.length == 1)
            getClPack().writeWarpToMap((short)p[0]);
    }

    private void cmdCrearTelep(String[] params) {
        Object[] p = validar(params, TShort, TByte, TByte, TByte);

        if (p == null)
            incorrectMsg("/CT MAPA X Y [RADIO]");

        else if (p.length == 3 || p.length == 4)
            getClPack().writeTeleportCreate((short)p[0], (byte)p[1], (byte)p[2], p.length == 4 ? (byte)p[3] : 0);
    }

    private void cmdMod(String[] params) {
        if (params.length < 3 || params.length > 4)
            incorrectMsg("/MOD NOMBRE CARACTERÍSTICA/S VALOR");

        else {
            EditOptions caract;
            switch (params[1]) {
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
                getClPack().writeEditChar(params[0], caract, params[2], params.length == 4 ? params[3] : "");
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



