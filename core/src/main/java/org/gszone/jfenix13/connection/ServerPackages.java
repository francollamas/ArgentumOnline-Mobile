package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.actors.Item;
import org.gszone.jfenix13.utils.*;
import org.gszone.jfenix13.views.windows.ComerciarWindow;
import org.gszone.jfenix13.actors.Consola;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.Colors;
import org.gszone.jfenix13.containers.FontTypes;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.Main;
import org.gszone.jfenix13.general.Messages;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.*;
import org.gszone.jfenix13.general.Config.Direccion;
import org.gszone.jfenix13.general.Messages.Message;
import org.gszone.jfenix13.containers.FontTypes.FontTypeName;
import org.gszone.jfenix13.views.screens.PrincipalView;
import org.gszone.jfenix13.views.screens.PrincipalViewM;
import org.gszone.jfenix13.views.screens.View;

import static com.badlogic.gdx.Application.ApplicationType.Desktop;
import static com.badlogic.gdx.Application.ApplicationType.WebGL;
import static org.gszone.jfenix13.containers.GameData.*;


/**
 * Clase con los paquetes que vienen del servidor y el cliente tiene que procesar
 * <p>
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
        GuildFoundation,
    }


    private boolean lostConnection;
    private BytesReader r;
    private Queue<byte[]> cola;

    public boolean isLostConnection() {
        return lostConnection;
    }

    public void setLostConnection(boolean lostConnection) {
        this.lostConnection = lostConnection;
    }

    public Queue<byte[]> getCola() {
        return cola;
    }

    public ServerPackages() {
        r = new BytesReader();
        r.setLittleEndian(true);
        cola = new Queue<byte[]>();
    }

    public String bu(String key) {
        return Main.getInstance().getBundle().get(key);
    }

    public GameData getGD() {
        return Main.getInstance().getGameData();
    }

    public Assets getAssets() {
        return Main.getInstance().getAssets();
    }

    private Stage getActStage() {
        return ((View)Main.getInstance().getScreen()).getStage();
    }

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
        */
        boolean broken = false;

        r.appendBytes(bytes);

        try {
            while (r.getAvailable() > 0 && !broken) {

                // Marco la posición del comienzo del paquete
                r.mark();

                ID id = ID.values()[r.readByte()];
                //Gdx.app.log(""+id.ordinal(), id.name());

                switch (id) {
                    case CreateFX:
                        handleCreateFX();
                        break;
                    case ChangeInventorySlot:
                        handleChangeInventorySlot();
                        break;
                    case ChangeNPCInventorySlot:
                        handleChangeNPCInventorySlot();
                        break;
                    case ChangeSpellSlot:
                        handleChangeSpellSlot();
                        break;
                    case Dumb:
                        handleDumb();
                        break;
                    case DumbNoMore:
                        handleDumbNoMore();
                        break;
                    case Blind:
                        handleBlind();
                        break;
                    case BlindNoMore:
                        handleBlindNoMore();
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
                    case UpdateStrenght:
                        handleUpdateStrenght();
                        break;
                    case UpdateDexterity:
                        handleUpdateDexterity();
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
                    case DiceRoll:
                        handleDiceRoll();
                        break;
                    case Pong:
                        handlePong();
                        break;
                    case Disconnect:
                        handleDisconnect();
                        break;
                    case NavigateToggle:
                        handleNavigateToggle();
                        break;
                    case PauseToggle:
                        handlePauseToggle();
                        break;
                    case MultiMessage:
                        handleMultiMessage();
                        break;
                    case MeditateToggle:
                        handleMeditateToggle();
                        break;
                    case CommerceInit:
                        handleCommerceInit();
                        break;
                    case CommerceEnd:
                        handleCommerceEnd();
                        break;
                    default:
                        // Si llega un paquete que no está implementado...
                        Dialogs.showOKDialog(bu("error"), "Paquete no implementado: " + id.ordinal() + " '" + id.toString() + "'.");
                        broken = true;
                        break;
                }
            }

            r.clear();
        }
        catch (NotEnoughDataException ex) {
            /* Es común que un paquete llegue cortado, por lo que no hay suficientes datos para leer...
            entonces se vuelve hasta la posición marcada (comienzo del último paquete)
            */
            r.reset();
        }
    }

    /**
     * Asigna un FX a un char
     */
    public void handleCreateFX() throws NotEnoughDataException {
        short charIndex = r.readShort();
        short fxIndex = r.readShort();
        short loops = r.readShort();

        Char c = getGD().getChars().getChar(charIndex);
        if (c == null) return;
        c.setFx(fxIndex, loops);
    }


    public void handleChangeInventorySlot() throws NotEnoughDataException {
        Item item = new Item();
        int index = r.readByte();

        item.set(r.readShort(), r.readString(), r.readShort(), r.readBoolean(),
                    r.readShort(), r.readByte(),
                    r.readShort(), r.readShort(), r.readShort(), r.readShort(), r.readFloat());
        //if (index == 2) item.setChecked(true);

        getGD().getInventario().setSlot(item, index);
    }

    public void handleChangeNPCInventorySlot() throws NotEnoughDataException {
        //OLDItem item = new OLDItem();
        r.readByte();
        r.readString();
        r.readShort();
        r.readFloat();
        r.readShort();
        r.readShort();
        r.readByte();
        r.readShort();
        r.readShort();
        r.readShort();
        r.readShort();
    }

    private void handleChangeSpellSlot() throws NotEnoughDataException {
        // TODO: completar
        r.readByte();
        r.readShort();
        r.readString();
    }

    private void handleDumb() {
        getGD().getCurrentUser().setEstupido(true);
    }

    private void handleDumbNoMore() {
        getGD().getCurrentUser().setEstupido(false);
    }

    private void handleBlind() {
        getGD().getCurrentUser().setCiego(true);
    }

    private void handleBlindNoMore() {
        getGD().getCurrentUser().setCiego(false);
    }

    /**
     * Guarda el index con el que el servidor conoce usuario que estámos manejando
     */
    private void handleUserIndexInServer() throws NotEnoughDataException {
        getGD().getCurrentUser().setIndex(r.readShort());
    }

    private void handleChangeMap() throws NotEnoughDataException {
        getGD().getCurrentUser().setMap(r.readShort());

        // Version del mapa (por ahora no se usa)
        r.readShort();

        getAssets().changeMap(getGD().getCurrentUser().getMap());
        getGD().getChars().clear();
        // TODO: manejar la lluvia.. (si no hay, parar el sonido)
    }

    private void handlePlayMusic() throws NotEnoughDataException {
        int num = r.readByte();
        if (num > 0)
            getAssets().getAudio().playMusic(num);

        // Corresponde a los loops, pero considero que todas las músicas se repiten infinitamente
        r.readShort();
    }


    /**
     * Define la nueva área, y borra todos los personajes y objetos que no pertenecen a esa área
     */
    private void handleAreaChanged() throws NotEnoughDataException {
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
    }

    /**
     * Crea un PJ o NPC, asignándole todas sus características y lo ubica en una posición del mapa
     */
    private void handleCharacterCreate() throws NotEnoughDataException {
        int index = r.readShort();
        Char c = getGD().getChars().getChar(index, true);

        c.setBody(r.readShort());
        c.setHead(r.readShort());
        c.setHeading(Config.Direccion.values()[r.readByte() - 1]);
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
        int privs = r.readByte();
        if (privs != 0) {
            // Si es del concejo del caos y tiene privilegios
            if ((privs & 64) != 0 && (privs & 1) == 0)
                privs = (byte) (privs ^ 64);

            // Si es del concejo de banderbill y tiene privilegios
            if ((privs & 128) != 0 && (privs & 1) == 0)
                privs = (byte) (privs ^ 128);

            // Si es rolmaster
            if ((privs & 32) != 0)
                privs = 32;

            // Con ésta operación se obtiene el número correspondiente al privilegio del usuario y se le asigna.
            c.setPriv((int) (Math.log(privs) / Math.log(2)));
        } else
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
    private void handleCharacterChange() throws NotEnoughDataException {
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
    private void handleCharacterRemove() throws NotEnoughDataException {
        int index = r.readShort();
        getGD().getChars().deleteChar(index);
        getGD().getChars().refresh();
    }

    /**
     * Mueve a cualquier PJ o NPC
     */
    private void handleCharacterMove() throws NotEnoughDataException {
        getGD().getChars().moveChar(r.readShort(), r.readByte(), r.readByte());
        getGD().getChars().refresh();
    }

    /**
     * Mueve al PJ actual a una dirección especificada (el servidor fuerza el movimiento de nuestro PJ)
     */
    private void handleForceCharMove() throws NotEnoughDataException {
        Direccion dir = Direccion.values()[r.readByte() - 1];
        User u = getGD().getCurrentUser();
        getGD().getChars().moveChar(u.getIndexInServer(), dir);
        getGD().getWorld().setMove(dir);
        getGD().getChars().refresh();
    }

    /**
     * Actualiza la posición del usuario (en caso que esté incorrecta)
     */
    private void handlePosUpdate() throws NotEnoughDataException {
        // Obtengo la posición real del personaje
        int x = r.readByte();
        int y = r.readByte();

        // Obtengo la posición posiblemente incorrecta
        Position wPos = getGD().getWorld().getPos();

        // Si estaba bien, salgo
        if (wPos.equals(new Position(x, y))) return;

        // Si estaba mal:
        // Borro el char de esa pos del mapa
        getAssets().getMapa().getTile((int) wPos.getX(), (int) wPos.getY()).setCharIndex(0);

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
    private void handleUserCharIndexInServer() throws NotEnoughDataException {
        int index = r.readShort();
        getGD().getCurrentUser().setIndexInServer(index);

        Char c = getGD().getChars().getChar(index);
        if (c != null) {
            Position p = c.getPos();
            getGD().getWorld().getPos().set(p.getX(), p.getY());
            getGD().getWorld().setTecho();
        }
    }

    private void handleUpdateUserStats() throws NotEnoughDataException {
        UserStats s = getGD().getCurrentUser().getStats();
        s.setMaxVida(r.readShort());
        s.setVida(r.readShort());
        s.setMaxMana(r.readShort());
        s.setMana(r.readShort());
        s.setMaxEnergia(r.readShort());
        s.setEnergia(r.readShort());
        s.setOro(r.readInt());
        s.setNivel(r.readByte());
        s.setMaxExp(r.readInt());
        s.setExp(r.readInt());
    }

    private void handleUpdateHungerAndThirst() throws NotEnoughDataException {
        UserStats s = getGD().getCurrentUser().getStats();
        s.setMaxSed(r.readByte());
        s.setSed(r.readByte());
        s.setMaxHambre(r.readByte());
        s.setHambre(r.readByte());
    }

    private void handleUpdateStrenghtAndDexterity() throws NotEnoughDataException {
        UserStats s = getGD().getCurrentUser().getStats();
        s.setFuerza(r.readByte());
        s.setAgilidad(r.readByte());
    }

    private void handleUpdateStrenght() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setFuerza(r.readByte());
    }

    private void handleUpdateDexterity() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setAgilidad(r.readByte());
    }

    private void handleSendSkills() throws NotEnoughDataException {
        // TODO: hacerlo.. LEE CLASE, Y LEE CADA SKILL
        r.readByte();
        for (int i = 0; i < 22; i++) {
            r.readByte();
        }
    }

    private void handleLevelUp() throws NotEnoughDataException {
        // TODO: revisar
        r.readShort();
    }

    /**
     * Carga la pantalla principal
     */
    private void handleLogged() {
        if (Gdx.app.getType() == Desktop || Gdx.app.getType() == WebGL)
            Main.getInstance().setScreen(new PrincipalView());
        else
            Main.getInstance().setScreen(new PrincipalViewM());
    }

    /**
     * Muestra un mensaje de error
     */
    private void handleErrorMsg() throws NotEnoughDataException {
        Dialogs.showOKDialog(bu("error"), r.readString());
    }

    /**
     * Muestra un mensaje del servidor
     */
    private void handleShowMessageBox() throws NotEnoughDataException {
        Dialogs.showOKDialog(bu("msg.sv"), r.readString());
    }

    /**
     * Crea un objeto en el mapa
     */
    private void handleObjectCreate() throws NotEnoughDataException {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setObjeto(new Grh(r.readShort()));
    }

    /**
     * Borra un objeto del mapa
     */
    private void handleObjectDelete() throws NotEnoughDataException {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setObjeto(null);
    }

    /**
     * Bloquea o desbloquea una posición del mapa
     */
    private void handleBlockPosition() throws NotEnoughDataException {
        MapTile tile = getAssets().getMapa().getTile(r.readByte(), r.readByte());
        tile.setBlocked(r.readBoolean());
    }

    /**
     * Agrega el diálogo de un PJ
     */
    private void handleChatOverHead() throws NotEnoughDataException {
        String texto = r.readString().trim();
        int index = r.readShort();
        Color c = Colors.newColor(r.readByte(), r.readByte(), r.readByte());

        getGD().getChars().setDialog(index, texto, c);
    }

    /**
     * Agrega un mensaje en consola
     */
    private void handleConsoleMsg() throws NotEnoughDataException {
        getGD().getConsola().addMessage(r.readString(), FontTypes.FontTypeName.values()[r.readByte()]);
        // TODO: si surge algún error, manejar también las fonts con formato viejo (ej ~255~255~255~1~0~)
    }

    /**
     * Reproduce un sonido
     */
    private void handlePlaySound() throws NotEnoughDataException {
        getAssets().getAudio().playSound(r.readByte());

        // Estos valores corresponden a la posición (X, Y) de donde proviene el sonido (para sonido 3D, que no se usa)
        r.readByte();
        r.readByte();
    }

    private void handleRemoveDialogs() {
        // TODO: Borrar diálogos
        // y esto borraría todos los diálogos??? (fijarme que hacer)
    }

    /**
     * Borra el dialogo de un PJ
     */
    private void handleRemoveCharDialog() throws NotEnoughDataException {
        int index = r.readShort();

        // TODO: llamar a RemoveDialog(index)
        // esto es realmente necesario? (fijarme).. ya que como el diálogo es un atributo del char, si borro el char se pierde el diálogo
    }

    private void handleDisconnect() {
        getGD().disconnect();
    }

    public void handleUpdateSta() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setEnergia(r.readShort());
    }

    public void handleUpdateMana() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setMana(r.readShort());
    }

    public void handleUpdateHP() throws NotEnoughDataException {
        User u = getGD().getCurrentUser();
        u.getStats().setVida(r.readShort());
    }

    public void handleUpdateGold() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setOro(r.readInt());
    }

    public void handleUpdateExp() throws NotEnoughDataException {
        getGD().getCurrentUser().getStats().setExp(r.readInt());
    }

    public void handleDiceRoll() throws NotEnoughDataException {
        UserAtributos a = getGD().getCurrentUser().getAtributos();
        a.setFuerza(r.readByte());
        a.setAgilidad(r.readByte());
        a.setInteligencia(r.readByte());
        a.setCarisma(r.readByte());
        a.setConstitucion(r.readByte());
    }

    public void handlePong() {
        ClientPackages c = Main.getInstance().getConnection().getClPack();
        int ping = (int) (TimeUtils.millis() - c.getPingTime() - (Gdx.graphics.getRawDeltaTime() * 1000));
        c.setPingTime(0);

        getGD().getConsola().addMessage("El ping es de " + ping + " ms.", FontTypes.FontTypeName.Warning);
    }

    public void handleNavigateToggle() {
        getGD().getCurrentUser().setNavegando(!getGD().getCurrentUser().isNavegando());
    }

    public void handlePauseToggle() {
        getGD().setPausa(!getGD().isPausa());
    }

    public void handleMultiMessage() throws NotEnoughDataException {

        Message msg = Messages.Message.values()[r.readByte()];
        Consola c = getGD().getConsola();
        I18NBundle b = Main.getInstance().getBundle();

        switch (msg) {
            case DontSeeAnything:
                c.addMessage(b.get("msg.dont-see-anything"), FontTypeName.Info);
                break;

            // TODO: completar con todos los mensajes!!!
        }
    }

    private void handleMeditateToggle() {
        getGD().getCurrentUser().setMeditando(!getGD().getCurrentUser().isMeditando());
    }

    private void handleCommerceInit() {
        // TODO: completar
        // llenar el NPCInventory, mostrar la pantalla
        ((View)Main.getInstance().getScreen()).getStage().addActor(new ComerciarWindow());
        getGD().getCurrentUser().setComerciando(true);
    }

    private void handleCommerceEnd() {
        // cerrar la pantalla y cambiar Flag comerciando
        getGD().getCurrentUser().setComerciando(false);
    }

}
