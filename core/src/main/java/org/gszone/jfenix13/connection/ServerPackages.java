package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Queue;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.*;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.screens.DtPrincipal;
import org.gszone.jfenix13.screens.MbPrincipal;
import org.gszone.jfenix13.general.General.Direccion;
import org.gszone.jfenix13.utils.BytesReader;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

import static com.badlogic.gdx.Application.ApplicationType.*;
import static org.gszone.jfenix13.containers.GameData.*;


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

    private BytesReader r;
    private Queue<byte[]> cola;

    public Queue<byte[]> getCola() {
        return cola;
    }

    public ServerPackages() {
        r = new BytesReader();
        r.setLittleEndian(true);
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
    public void handleReceived(byte[] bytes) {
        /*
        * Esta variable se activa si se recibe un paquete que no existe
        * (para que deje de procesar paquetes, sino se rompe el juego)
        * TODO: borrarla, ya que una vez listo todo debería funcionar perfectamente.
        */
        boolean broken = false;

        r.setBytes(bytes);

        while (r.getAvailable() > 0 && !broken) {
            ID id = ID.values()[r.readByte()];

            //Gdx.app.log(""+id.ordinal(), id.name());

            switch (id) {
                case CreateFX:
                    handleCreateFX();
                    break;
                case ChangeInventorySlot:
                    handleChangeInventorySlot();
                    break;
                case ChangeSpellSlot:
                    handleChangeSpellSlot();
                    break;
                case DumbNoMore:
                    handleDumbNoMore();
                    break;
                case UserIndexInServer:
                    handleUserIndexInServer();
                    break;
                case ChangeMap:
                    handleChangeMap();
                    break;
                case PlayMusic:
                    handlePlayMusic();
                    break;
                case AreaChanged:
                    handleAreaChanged();
                    break;
                case CharacterCreate:
                    handleCharacterCreate();
                    break;
                case CharacterChange:
                    handleCharacterChange();
                    break;
                case UserCharIndexInServer:
                    handleUserCharIndexInServer();
                    break;
                case UpdateUserStats:
                    handleUpdateUserStats();
                    break;
                case UpdateHungerAndThirst:
                    handleUpdateHungerAndThirst();
                    break;
                case UpdateStrenghtAndDexterity:
                    handleUpdateStrenghtAndDexterity();
                    break;
                case SendSkills:
                    handleSendSkills();
                    break;
                case LevelUp:
                    handleLevelUp();
                    break;
                case Logged:
                    handleLogged();
                    break;
                case ErrorMsg:
                    handleErrorMsg();
                    break;
                case ShowMessageBox:
                    handleShowMessageBox();
                    break;
                case ObjectCreate:
                    handleObjectCreate();
                    break;
                case ObjectDelete:
                    handleObjectDelete();
                    break;
                case BlockPosition:
                    handleBlockPosition();
                    break;
                case CharacterMove:
                    handleCharacterMove();
                    break;
                case PosUpdate:
                    handlePosUpdate();
                    break;
                case ChatOverHead:
                    handleChatOverHead();
                    break;
                case ConsoleMsg:
                    handleConsoleMsg();
                    break;
                case CharacterRemove:
                    handleCharacterRemove();
                    break;
                case ForceCharMove:
                    handleForceCharMove();
                    break;
                case RemoveDialogs:
                    handleRemoveDialogs();
                    break;
                case PlaySound:
                    handlePlaySound();
                    break;
                case RemoveCharDialog:
                    handleRemoveCharDialog();
                    break;
                case UpdateSta:
                    handleUpdateSta();
                    break;
                case UpdateMana:
                    handleUpdateMana();
                    break;
                case UpdateHP:
                    handleUpdateHP();
                    break;
                case UpdateGold:
                    handleUpdateGold();
                    break;
                case UpdateExp:
                    handleUpdateExp();
                    break;
                case Disconnect:
                    handleDisconnect();
                    break;
                default:
                    Dialogs.showOKDialog(getActStage(), "Error", "No se reconoce el paquete " + id.ordinal() + "'" + id.toString() + "'. Posiblemente se perdieron más paquetes");
                    broken = true;
                    break;
            }
        }
    }

    /**
     * Asigna un FX a un char
     */
    public void handleCreateFX() {
        short charIndex = r.readShort();
        short fxIndex = r.readShort();
        short loops = r.readShort();

        Char c = getGD().getChars().getChar(charIndex);
        if (c == null) return;
        c.setFx(fxIndex, loops);
    }


    public void handleChangeInventorySlot() {
        // TODO: completar
        r.readByte();
        r.readShort();
        r.readString();
        r.readShort();
        r.readBoolean();
        r.readShort();
        r.readByte();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readFloat();
    }

    private void handleChangeSpellSlot() {
        // TODO: completar
        r.readByte();
        r.readShort();
        r.readString();
    }

    private void handleDumbNoMore() {
        // TODO: sacar estupidez al usuario
    }

    /**
     * Guarda el index con el que el servidor conoce usuario que estámos manejando
     */
    private void handleUserIndexInServer() {
        getGD().getCurrentUser().setIndex(r.readShort());
    }

    private void handleChangeMap() {
        getGD().getCurrentUser().setMap(r.readShort());

        // Version del mapa (por ahora no se usa)
        r.readShort();

        getAssets().changeMap(getGD().getCurrentUser().getMap());
        getGD().getChars().clear();
        // TODO: manejar la lluvia.. (si no hay, parar el sonido)
    }

    private void handlePlayMusic() {
        int num = r.readByte();
        if (num > 0)
            getAssets().getAudio().playMusic(num);

        // Corresponde a los loops, pero considero que todas las músicas se repiten infinitamente
        r.readShort();
    }


    /**
     * Define la nueva área, y borra todos los personajes y objetos que no pertenecen a esa área
     */
    private void handleAreaChanged() {
        // Los valores están hardcodeados
        Rect area = getGD().getCurrentUser().getArea();
        int x = r.readByte();
        int y = r.readByte();

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
    private void handleCharacterCreate() {
        int index = r.readShort();
        Char c = getGD().getChars().getChar(index, true);

        c.setBody(r.readShort());
        c.setHead(r.readShort());
        c.setHeading(General.Direccion.values()[r.readByte() - 1]);
        c.getPos().set(r.readByte(), r.readByte());
        c.setWeapon(r.readShort());
        c.setShield(r.readShort());
        c.setHelmet(r.readShort());

        // Lecturas innecesarias (fx y loop, pero no se usan)
        r.readShort();
        r.readShort();

        c.setNombre(r.readString());
        c.setGuildName(r.readString());
        c.setBando(r.readByte());

        // Privilegios
        byte privs = r.readByte();
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
        getAssets().getMapa().getTile((int) c.getPos().getX(), (int) c.getPos().getY()).setCharIndex(index);
        getGD().getChars().refresh();
    }

    /**
     * Actualiza las características del PJ o NPC ya existente.
     */
    private void handleCharacterChange() {
        int index = r.readShort();
        Char c = getGD().getChars().getChar(index);

        c.setBody(r.readShort());
        c.setHead(r.readShort());
        c.setMuerto(c.getHeadIndex() == MUERTO_HEAD);
        c.setHeading(Direccion.values()[r.readByte() - 1]);
        c.setWeapon(r.readShort());
        c.setShield(r.readShort());
        c.setHelmet(r.readShort());
        c.setFx(r.readShort(), r.readShort());

        User u = getGD().getCurrentUser();
        if (index == u.getIndexInServer() && u.isCambiandoDir())
            u.setCambiandoDir(false);

        getGD().getChars().refresh();
    }

    /**
     * Borra un PJ o NPC
     */
    private void handleCharacterRemove() {
        int index = r.readShort();
        getGD().getChars().deleteChar(index);
        getGD().getChars().refresh();
    }

    /**
     * Mueve a cualquier PJ o NPC
     */
    private void handleCharacterMove() {
        getGD().getChars().moveChar(r.readShort(), r.readByte(), r.readByte());
        getGD().getChars().refresh();
    }

    /**
     * Mueve al PJ actual a una dirección especificada (el servidor fuerza el movimiento de nuestro PJ)
     */
    private void handleForceCharMove() {
        Direccion dir = Direccion.values()[r.readByte() - 1];
        User u = getGD().getCurrentUser();
        getGD().getChars().moveChar(u.getIndexInServer(), dir);
        getGD().getWorld().setMove(dir);
        getGD().getChars().refresh();
    }

    /**
     * Actualiza la posición del usuario (en caso que esté incorrecta)
     */
    private void handlePosUpdate() {
        // Obtengo la posición real del personaje
        int x = r.readByte();
        int y = r.readByte();

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
    private void handleUserCharIndexInServer() {
        int index = r.readShort();
        getGD().getCurrentUser().setIndexInServer(index);

        Position p = getGD().getChars().getChar(index).getPos();
        getGD().getWorld().getPos().set(p.getX(), p.getY());
        getGD().getWorld().setTecho();
    }

    private void handleUpdateUserStats() {
        // TODO: completar
        r.readShort();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readInt();
        r.readByte();
        r.readInt();
        r.readInt();
    }

    private void handleUpdateHungerAndThirst() {
        // TODO: completar
        r.readByte();
        r.readByte();
        r.readByte();
        r.readByte();
    }

    private void handleUpdateStrenghtAndDexterity() {
        // TODO: completar
        r.readByte();
        r.readByte();
    }

    private void handleSendSkills() {
    // TODO: hacerlo.. LEE CLASE, Y LEE CADA SKILL
    for (int i = 0; i < 23; i++) {
        r.readByte();
    }
}

    private void handleLevelUp() {
        r.readShort();
    }

    /**
     * Carga la pantalla principal
     */
    private void handleLogged() {
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL)
            Main.getInstance().setScreen(new DtPrincipal());
        else
            Main.getInstance().setScreen(new MbPrincipal());
    }

    /**
     * Muestra un mensaje de error
     */
    private void handleErrorMsg() {
        Dialogs.showOKDialog(getActStage(), "Error", r.readString());
    }

    /**
     * Muestra un mensaje del servidor
     */
    private void handleShowMessageBox() {
        Dialogs.showOKDialog(getActStage(), "Mensaje del Servidor", r.readString());
    }

    /**
     * Crea un objeto en el mapa
     */
    private void handleObjectCreate() {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setObjeto(new Grh(r.readShort()));
    }

    /**
     * Borra un objeto del mapa
     */
    private void handleObjectDelete() {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setObjeto(null);
    }

    /**
     * Bloquea o desbloquea una posición del mapa
     */
    private void handleBlockPosition() {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setBlocked(r.readBoolean());
    }

    /**
     * Agrega el diálogo de un PJ
     */
    private void handleChatOverHead() {
        String texto = r.readString().trim();
        int index = r.readShort();
        byte r = this.r.readByte();
        byte g = this.r.readByte();
        byte b = this.r.readByte();

        // TODO: crear diálogo arriba del pj
    }

    /**
     * Agrega un mensaje en consola
     */
    private void handleConsoleMsg() {
        r.readString(); //TODO: << agregar este mensaje en la consola
        r.readByte(); // TODO: << con esta ID de fuente...
    }

    private void handleRemoveDialogs() {
        // TODO: Borrar diálogos
    }

    /**
     * Reproduce un sonido
     */
    private void handlePlaySound() {
        getAssets().getAudio().playSound(r.readByte());

        // Estos valores corresponden a la posición (X, Y) de donde proviene el sonido (para sonido 3D, que no se usa)
        r.readByte();
        r.readByte();
    }

    /**
     * Borra el dialogo de un PJ
     */
    private void handleRemoveCharDialog() {
        int index = r.readShort();

        //TODO: llamar a RemoveDialog(index)
    }

    private void handleDisconnect() {
        Main.getInstance().getConnection().dispose();
        // TODO: parar lluvia
        Main.getInstance().setScreen(Screen.Scr.MENU);
        getGD().resetGameData();
        getAssets().getAudio().playMusic(6);
    }

    public void handleUpdateSta() {
        User u = getGD().getCurrentUser();
        u.setMinSta(r.readShort());
    }

    public void handleUpdateMana() {
        User u = getGD().getCurrentUser();
        u.setMinMana(r.readShort());
    }

    public void handleUpdateHP() {
        User u = getGD().getCurrentUser();
        u.setMinHP(r.readShort());
    }

    public void handleUpdateGold() {
        User u = getGD().getCurrentUser();
        u.setOro(r.readInt());
    }

    public void handleUpdateExp() {
        User u = getGD().getCurrentUser();
        u.setMinExp(r.readInt());
    }



}
