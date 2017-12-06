package org.gszone.jfenix13.general;

import com.badlogic.gdx.utils.Array;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.connection.ClientPackages;
import org.gszone.jfenix13.containers.FontTypes;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.objects.User;
import org.gszone.jfenix13.utils.StrUtils;

import static org.gszone.jfenix13.containers.FontTypes.FontTypeName.*;

/**
 * Analiza los comandos para enviar al servidor
 */
public class Commands {
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

        // Si es comando...
        Array<String> arr = StrUtils.getPalabras(texto.toUpperCase());
        String cmd = arr.removeIndex(0);
        String[] params = arr.toArray(String.class);

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
        if (params.length == 0)
            // Abrir encuesta
            getClPack().writeInquiry();
        else
            // Votar
            getClPack().writeInquiryVote(Byte.parseByte(params[0]));
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



