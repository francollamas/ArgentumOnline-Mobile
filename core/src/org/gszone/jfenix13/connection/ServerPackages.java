package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.*;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.screens.desktop.DtPrincipal;
import org.gszone.jfenix13.screens.mobile.MbPrincipal;
import org.gszone.jfenix13.general.General.Direccion;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

import static org.gszone.jfenix13.containers.GameData.*;
import static org.gszone.jfenix13.utils.Bytes.*;


/**
 * Clase con los paquetes que vienen del servidor y el cliente tiene que procesar
 *
 * cola: conjunto de secuencias de paquetes (el socket va ingresando las secuencias constantemente).
 */
public class ServerPackages {

    // Enumeración de los paquetes que se reciben del servidor
    enum ID {
        Logged,                  // LOGGED
        RemoveDialogs,           // QTDL
        RemoveCharDialog,        // QDL
        NavigateToggle,          // NAVEG
        Disconnect,              // FINOK
        CommerceEnd,             // FINCOMOK
        BankEnd,                 // FINBANOK
        CommerceInit,            // INITCOM
        BankInit,                // INITBANCO
        UserCommerceInit,        // INITCOMUSU
        UserCommerceEnd,         // FINCOMUSUOK
        UserOfferConfirm,
        CommerceChat,
        ShowBlacksmithForm,      // SFH
        ShowCarpenterForm,       // SFC
        UpdateSta,               // ASS
        UpdateMana,              // ASM
        UpdateHP,                // ASH
        UpdateGold,              // ASG
        UpdateBankGold,
        UpdateExp,               // ASE
        ChangeMap,               // CM
        PosUpdate,               // PU
        ChatOverHead,            // ||
        ConsoleMsg,              // || - Beware!! its the same as above, but it was properly splitted
        ShowMessageBox,          // !!
        UserIndexInServer,       // IU
        UserCharIndexInServer,   // IP
        CharacterCreate,         // CC
        CharacterRemove,         // BP
        CharacterChangeNick,
        CharacterMove,           // MP, +, * and _ '
        ForceCharMove,
        CharacterChange,         // CP
        ObjectCreate,            // HO
        ObjectDelete,            // BO
        BlockPosition,           // BQ
        PlayMusic,                // TM
        PlaySound,                // TW
        AreaChanged,             // CA
        PauseToggle,             // BKW
        RainToggle,              // LLU
        CreateFX,                // CFX
        UpdateUserStats,         // EST
        WorkRequestTarget,       // T01
        ChangeInventorySlot,     // CSI
        ChangeBankSlot,          // SBO
        ChangeSpellSlot,         // SHS
        Atributes,               // ATR
        BlacksmithWeapons,       // LAH
        BlacksmithArmors,        // LAR
        CarpenterObjects,        // OBR
        RestOK,                  // DOK
        ErrorMsg,                // ERR
        Blind,                   // CEGU
        Dumb,                    // DUMB
        ShowSignal,              // MCAR
        ChangeNPCInventorySlot,  // NPCI
        UpdateHungerAndThirst,   // EHYS
        Fame,                    // FAMA
        MiniStats,               // MEST
        LevelUp,                 // SUNI
        AddForumMsg,             // FMSG
        ShowForumForm,           // MFOR
        SetInvisible,            // NOVER
        DiceRoll,                // DADOS
        MeditateToggle,          // MEDOK
        BlindNoMore,             // NSEGUE
        DumbNoMore,              // NESTUP
        SendSkills,              // SKILLS
        TrainerCreatureList,     // LSTCRI
        ParalizeOK,              // PARADOK
        ShowUserRequest,         // PETICIO
        TradeOK,                 // TRANSOK
        BankOK,                  // BANCOOK
        ChangeUserTradeSlot,     // COMUSUINV
        SendNight,               // NOC
        Pong,
        UpdateTagAndStatus,
        SpawnList,               // SPL
        ShowSOSForm,             // MSOS
        ShowMOTDEditionForm,     // ZMOTD
        ShowGMPanelForm,         // ABPANEL
        UserNameList,            // LISTUSU
        UpdateStrenghtAndDexterity,
        UpdateStrenght,
        UpdateDexterity,
        AddSlots,
        MultiMessage,
        StopWorking,
        CancelOfferItem,
        SubeClase,
        ShowFormClase,
        EligeFaccion,
        ShowFaccionForm,
        EligeRecompensa,
        ShowRecompensaForm,
        SendGuildForm,
        GuildFoundation
    }

    private Queue<Array<Byte>> cola;

    public Queue<Array<Byte>> getCola() {
        return cola;
    }

    public ServerPackages() {
        cola = new Queue();
    }

    public GameData getGD() { return Main.getInstance().getGameData(); }
    public Assets getAssets() { return Main.getInstance().getAssets(); }
    private Stage getActStage() { return ((Screen)Main.getInstance().getScreen()).getStage(); }

    /**
     * Lee los paquetes almacenados en la cola.
     */
    public void doActions() {
        int size = cola.size;
        for (int i = 0; i < size; i++)
            handleReceived(cola.removeFirst());
    }

    /**
     * Ejecuta los métodos correspondientes al ID de los paquetes recibidos.
     */
    public void handleReceived(Array<Byte> bytes) {
        /*
        * Esta variable se activa si se recibe un paquete que no existe
        * (para que deje de procesar paquetes, sino se rompe el juego)
        * TODO: borrarla, ya que una vez listo todo debería funcionar perfectamente.
        */
        boolean broken = false;

        while (bytes.size > 0 && !broken) {
            ID id = ID.values()[readByte(bytes)];

            switch (id) {
                case CreateFX:
                    handleCreateFX(bytes);
                    break;
                case ChangeInventorySlot:
                    handleChangeInventorySlot(bytes);
                    break;
                case ChangeSpellSlot:
                    handleChangeSpellSlot(bytes);
                    break;
                case DumbNoMore:
                    handleDumbNoMore();
                    break;
                case UserIndexInServer:
                    handleUserIndexInServer(bytes);
                    break;
                case ChangeMap:
                    handleChangeMap(bytes);
                    break;
                case PlayMusic:
                    handlePlayMusic(bytes);
                    break;
                case AreaChanged:
                    handleAreaChanged(bytes);
                    break;
                case CharacterCreate:
                    handleCharacterCreate(bytes);
                    break;
                case CharacterChange:
                    handleCharacterChange(bytes);
                    break;
                case UserCharIndexInServer:
                    handleUserCharIndexInServer(bytes);
                    break;
                case UpdateUserStats:
                    handleUpdateUserStats(bytes);
                    break;
                case UpdateHungerAndThirst:
                    handleUpdateHungerAndThirst(bytes);
                    break;
                case UpdateStrenghtAndDexterity:
                    handleUpdateStrenghtAndDexterity(bytes);
                    break;
                case SendSkills:
                    handleSendSkills(bytes);
                    break;
                case LevelUp:
                    handleLevelUp(bytes);
                    break;
                case Logged:
                    handleLogged();
                    break;
                case ErrorMsg:
                    handleErrorMsg(bytes);
                    break;
                case ShowMessageBox:
                    handleShowMessageBox(bytes);
                    break;
                case ObjectCreate:
                    handleObjectCreate(bytes);
                    break;
                case ObjectDelete:
                    handleObjectDelete(bytes);
                    break;
                case BlockPosition:
                    handleBlockPosition(bytes);
                    break;
                case CharacterMove:
                    handleCharacterMove(bytes);
                    break;
                case PosUpdate:
                    handlePosUpdate(bytes);
                    break;
                case ChatOverHead:
                    handleChatOverHead(bytes);
                    break;
                case ConsoleMsg:
                    handleConsoleMsg(bytes);
                    break;
                case CharacterRemove:
                    handleCharacterRemove(bytes);
                    break;
                case ForceCharMove:
                    handleForceCharMove(bytes);
                    break;
                case RemoveDialogs:
                    handleRemoveDialogs();
                    break;
                case PlaySound:
                    handlePlaySound(bytes);
                    break;
                case RemoveCharDialog:
                    handleRemoveCharDialog(bytes);
                    break;
                case UpdateSta:
                    handleUpdateSta(bytes);
                    break;
                case UpdateMana:
                    handleUpdateMana(bytes);
                    break;
                case UpdateHP:
                    handleUpdateHP(bytes);
                    break;
                case UpdateGold:
                    handleUpdateGold(bytes);
                    break;
                case UpdateExp:
                    handleUpdateExp(bytes);
                    break;
                case Disconnect:
                    handleDisconnect();
                    break;
                default:
                    Dialogs.showOKDialog(getActStage(), "Error", "No se reconoce el paquete '" + id.toString() + "'. Posiblemente se perdieron más paquetes");
                    broken = true;
                    break;
            }
        }
    }

    /**
     * Asigna un FX a un char
     */
    public void handleCreateFX(Array<Byte> bytes) {
        short charIndex = readShort(bytes);
        short fxIndex = readShort(bytes);
        short loops = readShort(bytes);

        Char c = getGD().getChars().getChar(charIndex);
        if (c == null) return;
        c.setFx(fxIndex, loops);
    }


    public void handleChangeInventorySlot(Array<Byte> bytes) {
        // TODO: completar
        readByte(bytes);
        readShort(bytes);
        readString(bytes);
        readShort(bytes);
        readBoolean(bytes);
        readShort(bytes);
        readByte(bytes);
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readFloat(bytes);
    }

    private void handleChangeSpellSlot(Array<Byte> bytes) {
        // TODO: completar
        readByte(bytes);
        readShort(bytes);
        readString(bytes);
    }

    private void handleDumbNoMore() {
        // TODO: sacar estupidez al usuario
    }

    /**
     * Guarda el index con el que el servidor conoce usuario que estámos manejando
     */
    private void handleUserIndexInServer(Array<Byte> bytes) {
        getGD().getCurrentUser().setIndex(readShort(bytes));
    }

    private void handleChangeMap(Array<Byte> bytes) {
        getGD().getCurrentUser().setMap(readShort(bytes));

        // Version del mapa (por ahora no se usa)
        readShort(bytes);

        getAssets().changeMap(getGD().getCurrentUser().getMap());
        getGD().getChars().clear();
        // TODO: manejar la lluvia.. (si no hay, parar el sonido)
    }

    private void handlePlayMusic(Array<Byte> bytes) {
        int num = readByte(bytes);
        if (num > 0)
            getAssets().getAudio().playMusic(num);

        // Corresponde a los loops, pero considero que todas las músicas se repiten infinitamente
        readShort(bytes);
    }


    /**
     * Define la nueva área, y borra todos los personajes y objetos que no pertenecen a esa área
     */
    private void handleAreaChanged(Array<Byte> bytes) {
        // Los valores están hardcodeados
        Rect area = getGD().getCurrentUser().getArea();
        int x = readByte(bytes);
        int y = readByte(bytes);

        area.setX1((x / 9 - 1) * 9);
        area.setWidth(26);

        area.setY1((y / 9 - 1) * 9);
        area.setHeight(26);

        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (!area.isPointIn(new Position(i, j))) {
                    // Borro usuarios y npcs.
                    MapTile tile = getAssets().getMapa().getTile(i, j);
                    if (tile.getCharIndex() > 0) {
                        if (tile.getCharIndex() != getGD().getCurrentUser().getIndexInServer()) {
                            getGD().getChars().deleteChar(tile.getCharIndex());
                        }
                    }

                    // Borro objetos
                    tile.setObjeto(null);
                }
            }
        }

        getGD().getChars().refresh();
        // TODO: remover dialogos de los que no están en la PCArea
    }

    /**
     * Crea un PJ o NPC, asignándole todas sus características y lo ubica en una posición del mapa
     */
    private void handleCharacterCreate(Array<Byte> bytes) {
        int index = readShort(bytes);
        Char c = getGD().getChars().getChar(index, true);

        c.setBody(readShort(bytes));
        c.setHead(readShort(bytes));
        c.setHeading(General.Direccion.values()[readByte(bytes) - 1]);
        c.getPos().set(readByte(bytes), readByte(bytes));
        c.setWeapon(readShort(bytes));
        c.setShield(readShort(bytes));
        c.setHelmet(readShort(bytes));

        // Lecturas innecesarias (fx y loop, pero no se usan)
        readShort(bytes);
        readShort(bytes);

        c.setNombre(readString(bytes));
        c.setGuildName(readString(bytes));
        c.setBando(readByte(bytes));

        // Privilegios
        byte privs = readByte(bytes);
        if (privs != 0) {
            // Si es del concejo del caos y tiene privilegios
            if ((privs & 64) != 0 && (privs & 1) == 0)
                privs = (byte)(privs ^ 64);

            // Si es del concejo de banderbill y tiene privilegios
            if ((privs & 128) != 0 && (privs & 1) == 0)
                privs = (byte)(privs ^ 128);

            // Si es rolmaster
            if ((privs & 32) != 0)
                privs = 32;

            // Con ésta operación se obtiene el número correspondiente al privilegio del usuario y se le asigna.
            c.setPriv((int)(Math.log(privs) / Math.log(2)));
        }
        else
            c.setPriv(0);

        // Actualizamos el atributo lastChar. (para saber cual es el index del char con nro mas alto)
        if (index > getGD().getChars().getLastChar()) getGD().getChars().setLastChar(index);

        // Si el char es nuevo, aumento la cantidad de chars.
        if (!c.isActive()) getGD().getChars().setNumChars(getGD().getChars().getNumChars() + 1);

        // Lo activamos e insertamos en el mapa
        c.setActive(true);
        getAssets().getMapa().getTile((int)c.getPos().getX(), (int)c.getPos().getY()).setCharIndex(index);
        getGD().getChars().refresh();
    }

    /**
     * Actualiza las características del PJ o NPC ya existente.
     */
    private void handleCharacterChange(Array<Byte> bytes) {
        int index = readShort(bytes);
        Char c = getGD().getChars().getChar(index);

        c.setBody(readShort(bytes));
        c.setHead(readShort(bytes));
        c.setMuerto(c.getHeadIndex() == MUERTO_HEAD);
        c.setHeading(Direccion.values()[readByte(bytes) - 1]);
        c.setWeapon(readShort(bytes));
        c.setShield(readShort(bytes));
        c.setHelmet(readShort(bytes));
        c.setFx(readShort(bytes), readShort(bytes));

        User u = getGD().getCurrentUser();
        if (index == u.getIndexInServer() && u.isCambiandoDir())
            u.setCambiandoDir(false);

        getGD().getChars().refresh();
    }

    /**
     * Borra un PJ o NPC
     */
    private void handleCharacterRemove(Array<Byte> bytes) {
        int index = readShort(bytes);
        getGD().getChars().deleteChar(index);
        getGD().getChars().refresh();
    }

    /**
     * Mueve a cualquier PJ o NPC
     */
    private void handleCharacterMove(Array<Byte> bytes) {
        getGD().getChars().moveChar(readShort(bytes), readByte(bytes), readByte(bytes));
        getGD().getChars().refresh();
    }

    /**
     * Mueve al PJ actual a una dirección especificada (el servidor fuerza el movimiento de nuestro PJ)
     */
    private void handleForceCharMove(Array<Byte> bytes) {
        Direccion dir = Direccion.values()[readByte(bytes) - 1];
        User u = getGD().getCurrentUser();
        getGD().getChars().moveChar(u.getIndexInServer(), dir);
        getGD().getWorld().setMove(dir);
        getGD().getChars().refresh();
    }

    /**
     * Actualiza la posición del usuario (en caso que esté incorrecta)
     */
    private void handlePosUpdate(Array<Byte> bytes) {
        // Obtengo la posición real del personaje
        int x = readByte(bytes);
        int y = readByte(bytes);

        // Obtengo la posición posiblemente incorrecta
        Position wPos = getGD().getWorld().getPos();

        // Si estaba bien, salgo
        if (wPos.equals(new Position(x, y))) return;

        // Si estaba mal:
        // Borro el char de esa pos del mapa
        getAssets().getMapa().getTile((int)wPos.getX(), (int)wPos.getY()).setCharIndex(0);

        // Cambio las coordenadas del World
        wPos.set(x, y);

        // Pongo al char en donde debe ir realmente
        User u = getGD().getCurrentUser();
        getAssets().getMapa().getTile(x, y).setCharIndex(u.getIndexInServer());
        Position cPos = getGD().getChars().getChar(u.getIndexInServer()).getPos();
        cPos.set(x, y);

        getGD().getWorld().setTecho();
    }

    /**
     * Asigna el índice del usuario principal y cambia la posición del mundo
     */
    private void handleUserCharIndexInServer(Array<Byte> bytes) {
        int index = readShort(bytes);
        getGD().getCurrentUser().setIndexInServer(index);

        Position p = getGD().getChars().getChar(index).getPos();
        getGD().getWorld().getPos().set(p.getX(), p.getY());
        getGD().getWorld().setTecho();
    }

    private void handleUpdateUserStats(Array<Byte> bytes) {
        // TODO: completar
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readShort(bytes);
        readInt(bytes);
        readByte(bytes);
        readInt(bytes);
        readInt(bytes);
    }

    private void handleUpdateHungerAndThirst(Array<Byte> bytes) {
        // TODO: completar
        readByte(bytes);
        readByte(bytes);
        readByte(bytes);
        readByte(bytes);
    }

    private void handleUpdateStrenghtAndDexterity(Array<Byte> bytes) {
        // TODO: completar
        readByte(bytes);
        readByte(bytes);
    }

    private void handleSendSkills(Array<Byte> bytes) {
    // TODO: hacerlo.. LEE CLASE, Y LEE CADA SKILL
    for (int i = 0; i < 23; i++) {
        readByte(bytes);
    }
}

    private void handleLevelUp(Array<Byte> bytes) {
        readShort(bytes);
    }

    /**
     * Carga la pantalla principal
     */
    private void handleLogged() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            Main.getInstance().setScreen(new DtPrincipal());
        else
            Main.getInstance().setScreen(new MbPrincipal());
    }

    /**
     * Muestra un mensaje de error
     */
    private void handleErrorMsg(Array<Byte> bytes) {
        Dialogs.showOKDialog(getActStage(), "Error", readString(bytes));
    }

    /**
     * Muestra un mensaje del servidor
     */
    private void handleShowMessageBox(Array<Byte> bytes) {
        Dialogs.showOKDialog(getActStage(), "Mensaje del Servidor", readString(bytes));
    }

    /**
     * Crea un objeto en el mapa
     */
    private void handleObjectCreate(Array<Byte> bytes) {
        MapTile tile = getAssets().getMapa().getTile(readByte(bytes), readByte(bytes));
        tile.setObjeto(new Grh(readShort(bytes)));
    }

    /**
     * Borra un objeto del mapa
     */
    private void handleObjectDelete(Array<Byte> bytes) {
        MapTile tile = getAssets().getMapa().getTile(readByte(bytes), readByte(bytes));
        tile.setObjeto(null);
    }

    /**
     * Bloquea o desbloquea una posición del mapa
     */
    private void handleBlockPosition(Array<Byte> bytes) {
        MapTile tile = getAssets().getMapa().getTile(readByte(bytes), readByte(bytes));
        tile.setBlocked(readBoolean(bytes));
    }

    /**
     * Agrega el diálogo de un PJ
     */
    private void handleChatOverHead(Array<Byte> bytes) {
        String texto = readString(bytes).trim();
        int index = readShort(bytes);
        byte r = readByte(bytes);
        byte g = readByte(bytes);
        byte b = readByte(bytes);

        // TODO: crear diálogo arriba del pj
    }

    /**
     * Agrega un mensaje en consola
     */
    private void handleConsoleMsg(Array<Byte> bytes) {
        readString(bytes); //TODO: << agregar este mensaje en la consola
        readByte(bytes); // TODO: << con esta ID de fuente...
    }

    private void handleRemoveDialogs() {
        // TODO: Borrar diálogos
    }

    /**
     * Reproduce un sonido
     */
    private void handlePlaySound(Array<Byte> bytes) {
        getAssets().getAudio().playSound(readByte(bytes));

        // Estos valores corresponden a la posición (X, Y) de donde proviene el sonido (para sonido 3D, que no se usa)
        readByte(bytes);
        readByte(bytes);
    }

    /**
     * Borra el dialogo de un PJ
     */
    private void handleRemoveCharDialog(Array<Byte> bytes) {
        int index = readShort(bytes);

        //TODO: llamar a RemoveDialog(index)
    }

    private void handleDisconnect() {
        Main.getInstance().getConnection().dispose();
        // TODO: parar lluvia
        Main.getInstance().setScreen(Screen.Scr.MENU);
        getGD().resetGameData();
        getAssets().getAudio().playMusic(6);
    }

    public void handleUpdateSta(Array<Byte> bytes) {
        User u = getGD().getCurrentUser();
        u.setMinSta(readShort(bytes));
    }

    public void handleUpdateMana(Array<Byte> bytes) {
        User u = getGD().getCurrentUser();
        u.setMinMana(readShort(bytes));
    }

    public void handleUpdateHP(Array<Byte> bytes) {
        User u = getGD().getCurrentUser();
        u.setMinHP(readShort(bytes));
    }

    public void handleUpdateGold(Array<Byte> bytes) {
        User u = getGD().getCurrentUser();
        u.setOro(readInt(bytes));
    }

    public void handleUpdateExp(Array<Byte> bytes) {
        User u = getGD().getCurrentUser();
        u.setMinExp(readInt(bytes));
    }



}
