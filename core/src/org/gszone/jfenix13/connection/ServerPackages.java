package org.gszone.jfenix13.connection;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import org.gszone.jfenix13.containers.Assets;
import org.gszone.jfenix13.containers.GameData;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.general.Main;
import org.gszone.jfenix13.graphics.Drawer;
import org.gszone.jfenix13.graphics.Grh;
import org.gszone.jfenix13.objects.Char;
import org.gszone.jfenix13.objects.MapTile;
import org.gszone.jfenix13.objects.Objeto;
import org.gszone.jfenix13.screens.Screen;
import org.gszone.jfenix13.screens.desktop.DtPrincipal;
import org.gszone.jfenix13.screens.mobile.MbPrincipal;

import static org.gszone.jfenix13.utils.Bytes.*;
import org.gszone.jfenix13.general.General.Direccion;
import org.gszone.jfenix13.utils.Position;
import org.gszone.jfenix13.utils.Rect;

/**
 * Clase con los paquetes que vienen del servidor y el cliente tiene que procesar
 */
public class ServerPackages {
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

    public ServerPackages() {
    }

    public GameData getGD() { return Main.getInstance().getGameData(); }
    public Assets getAssets() { return Main.getInstance().getAssets(); }
    private Stage getActStage() { return ((Screen)Main.getInstance().getScreen()).getStage(); }

    /**
     * Ejecuta el método correspondiente al ID del paquete recibido.
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

            // TODO: borrar sout
            System.out.println(id.ordinal());

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
                case ConsoleMsg:
                    handleConsoleMsg(bytes);
                    break;
                default:
                    System.out.println("se rompió...");
                    broken = true;
                    break;
            }
        }
    }

    /**
     * Asigna un FX a un char
     */
    public void handleCreateFX(Array<Byte> bytes) {
        short index = readShort(bytes);
        short fx = readShort(bytes);
        short loops = readShort(bytes);

        Grh grh = new Grh(getAssets().getFxs().getFx(fx).getGrhIndex());
        grh.setLoops(loops);

        getGD().getChars().getChar(index).setFx(grh);
        getGD().getChars().getChar(index).setFxIndex(fx);
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
        // TODO: manejar la lluvia.. (si no hay, parar el sonido)
    }

    private void handlePlayMusic(Array<Byte> bytes) {
        int num = readByte(bytes);
        if (num > 0)
            getAssets().getAudio().playMusic(num);

        // Corresponde a los loops, pero considero que todas las músicas se repiten infinitamente
        readShort(bytes);
    }


    private void handleAreaChanged(Array<Byte> bytes) {
        // Los valores están hardcodeados, no dependen del tamaño del mapa
        Rect area = Main.getInstance().getGameData().getArea();
        int x = readByte(bytes);
        int y = readByte(bytes);
        Position pos = new Position(x, y);

        area.setX1((x / 11 - 1) * 11);
        area.setWidth(32);

        area.setY1((y / 11 - 1) * 11);
        area.setHeight(32);

        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                if (!area.isPointIn(pos)) {
                    // Borro usuarios y npcs.
                    MapTile tile = Main.getInstance().getAssets().getMapa().getTile(i, j);
                    if (tile.getCharIndex() > 0) {
                        if (tile.getCharIndex() != Main.getInstance().getGameData().getCurrentUser().getIndexInServer()) {
                            Main.getInstance().getGameData().getChars().deleteChar(tile.getCharIndex());
                        }
                    }

                    // TODO: borrar objetos
                    tile.setObjeto(null);
                }
            }
        }



    }

    private void handleCharacterCreate(Array<Byte> bytes) {
        Grh[] grhs;
        int index = readShort(bytes);
        int body = readShort(bytes);
        int head = readShort(bytes);
        int heading =  readByte(bytes);
        int x = readByte(bytes);
        int y = readByte(bytes);
        int weapon = readShort(bytes);
        int shield = readShort(bytes);
        int helmet = readShort(bytes);
        byte privs;

        Char user = getGD().getChars().getChar(index);

        // Lecturas innecesarias (fx y loop, pero no se usan)
        readShort(bytes);
        readShort(bytes);

        user.setNombre(readString(bytes));
        user.setNombreOffset(-(int)(Drawer.getTextWidth(3, user.getNombre()) / 2) + Main.getInstance().getGeneral().getTilePixelWidth() / 2);

        user.setGuildName(readString(bytes));
        user.setGuildNameOffset(-(int)(Drawer.getTextWidth(3, user.getGuildName()) / 2) + Main.getInstance().getGeneral().getTilePixelWidth() / 2);

        user.setCriminal(readByte(bytes));

        privs = readByte(bytes);
        user.setPriv((int)(Math.log(privs) / Math.log(2)));
        // TODO: manejar privilegios (es un if largo)

        // Actualizamos el atributo lastChar. (para saber cual es el index del char con nro mas alto)
        if (index > getGD().getChars().getLastChar()) getGD().getChars().setLastChar(index);

        // Si el char es nuevo, aumento la cantidad de chars.
        if (!user.isActive()) getGD().getChars().setNumChars(getGD().getChars().getNumChars() + 1);

        if (weapon == 0) weapon = 2;
        if (shield == 0) shield = 2;
        if (helmet == 0) helmet = 2;

        if (body > 0) {
            grhs = new Grh[Direccion.values().length];
            for (int i = 0; i < grhs.length; i++) {
                Grh grh = new Grh(getAssets().getBodies().getBody(body).getGrhIndex(Direccion.values()[i]));
                grhs[i] = grh;
            }
            user.setBody(grhs);
            user.setBodyIndex(body);
        }

        if (head > 0) {
            grhs = new Grh[Direccion.values().length];
            for (int i = 0; i < grhs.length; i++) {
                Grh grh = new Grh(getAssets().getHeads().getGrhDir(head).getGrhIndex(Direccion.values()[i]));
                grhs[i] = grh;
            }
            user.setHead(grhs);
            user.setHeadIndex(head);
        }

        grhs = new Grh[Direccion.values().length];
        for (int i = 0; i < grhs.length; i++) {
            Grh grh = new Grh(getAssets().getHelmets().getGrhDir(helmet).getGrhIndex(Direccion.values()[i]));
            grhs[i] = grh;
        }
        user.setHelmet(grhs);


        grhs = new Grh[Direccion.values().length];
        for (int i = 0; i < grhs.length; i++) {
            Grh grh = new Grh(getAssets().getShields().getGrhDir(shield).getGrhIndex(Direccion.values()[i]));
            grhs[i] = grh;
        }
        user.setShield(grhs);


        grhs = new Grh[Direccion.values().length];
        for (int i = 0; i < grhs.length; i++) {
            Grh grh = new Grh(getAssets().getWeapons().getGrhDir(weapon).getGrhIndex(Direccion.values()[i]));
            grhs[i] = grh;
        }
        user.setWeapon(grhs);


        user.setHeading(General.Direccion.values()[heading - 1]);
        user.getPos().setX(x);
        user.getPos().setY(y);
        user.setActive(true);

        getAssets().getMapa().getTile(x, y).setCharIndex(index);
    }

    private void handleUserCharIndexInServer(Array<Byte> bytes) {
        int index = readShort(bytes);
        getGD().getCurrentUser().setIndexInServer(index);
        getGD().getWorld().getPos().setX(getGD().getChars().getChar(index).getPos().getX());
        getGD().getWorld().getPos().setY(getGD().getChars().getChar(index).getPos().getY());

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

    private void handleLogged() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            Main.getInstance().setScreen(new DtPrincipal());
        else
            Main.getInstance().setScreen(new MbPrincipal());
    }

    private void handleErrorMsg(Array<Byte> bytes) {
        Dialogs.showOKDialog(getActStage(), "Error", readString(bytes));
    }

    private void handleShowMessageBox(Array<Byte> bytes) {
        Dialogs.showOKDialog(getActStage(), "Mensaje del Servidor", readString(bytes));
    }

    private void handleObjectCreate(Array<Byte> bytes) {
        int x = readByte(bytes);
        int y = readByte(bytes);
        MapTile tile = Main.getInstance().getAssets().getMapa().getTile(x, y);

        tile.setObjeto(new Grh(readShort(bytes)));
    }

    private void handleObjectDelete(Array<Byte> bytes) {
        int x = readByte(bytes);
        int y = readByte(bytes);
        MapTile tile = Main.getInstance().getAssets().getMapa().getTile(x, y);

        tile.setObjeto(null);
    }

    private void handleBlockPosition(Array<Byte> bytes) {
        int x = readByte(bytes);
        int y = readByte(bytes);
        MapTile tile = Main.getInstance().getAssets().getMapa().getTile(x, y);

        tile.setBlocked(readBoolean(bytes));
    }

    private void handleCharacterMove(Array<Byte> bytes) {
        int index = readShort(bytes);
        int x = readByte(bytes);
        int y = readByte(bytes);

        Main.getInstance().getGameData().getChars().moveChar(index, x, y);
    }

    private void handlePosUpdate(Array<Byte> bytes) {
        readByte(bytes);
        readByte(bytes);
    }

    private void handleConsoleMsg(Array<Byte> bytes) {
        readString(bytes);
        readByte(bytes);
    }

}
