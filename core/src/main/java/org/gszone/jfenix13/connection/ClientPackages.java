package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.general.Commands.EditOptions;
import org.gszone.jfenix13.general.Config;
import org.gszone.jfenix13.utils.BytesWritter;
import org.gszone.jfenix13.utils.Position;

/**
 * Clase con los paquetes que se envían al servidor
 *
 * bytes: corresponde a la secuencia de paquetes que se está armando antes de ser enviada al servidor
 * cola: colección con las secuencais obtenidas en 'bytes' (el socket va leyendo esta cola constantemente)
 * pingTime: usado para medir el tiempo de respuesta del servidor
 */
public class ClientPackages {

    // Enumeración de los paquetes que se envían al servidor
    enum ID {
        LoginExistingChar,       // OLOGIN
        ThrowDices,              // TIRDAD
        LoginNewChar,            // LOGIN
        Talk,                    // ;
        Yell,                    // -
        Whisper,                 // \
        Walk,                    // M
        RequestPositionUpdate,   // RPU
        Attack,                  // AT
        PickUp,                  // AG
        RequestAtributes,        // ATR
        RequestFame,             // FAMA
        RequestSkills,           // ESKI
        RequestMiniStats,        // FEST
        CommerceEnd,             // FINCOM
        UserCommerceEnd,         // FINCOMUSU
        UserCommerceConfirm,
        CommerceChat,
        BankEnd,                 // FINBAN
        UserCommerceOk,          // COMUSUOK
        UserCommerceReject,      // COMUSUNO
        Drop,                    // TI
        CastSpell,               // LH
        LeftClick,               // LC
        DoubleClick,             // RC
        Work,                    // UK
        UseSpellMacro,           // UMH
        UseItem,                 // USA
        CraftBlacksmith,         // CNS
        CraftCarpenter,          // CNC
        WorkLeftClick,           // WLC
        SpellInfo,               // INFS
        EquipItem,               // EQUI
        ChangeHeading,           // CHEA
        ModifySkills,            // SKSE
        Train,                   // ENTR
        CommerceBuy,             // COMP
        BankExtractItem,         // RETI
        CommerceSell,            // VEND
        BankDeposit,             // DEPO
        ForumPost,               // DEMSG
        MoveSpell,               // DESPHE
        MoveBank,
        UserCommerceOffer,       // OFRECER
        Online,                  // /ONLINE
        Quit,                    // /SALIR
        RequestAccountState,     // /BALANCE
        PetStand,                // /QUIETO
        PetFollow,               // /ACOMPAÑAR
        ReleasePet,              // /LIBERAR
        TrainList,               // /ENTRENAR
        Rest,                    // /DESCANSAR
        Meditate,                // /MEDITAR
        Resucitate,              // /RESUCITAR
        Heal,                    // /CURAR
        Help,                    // /AYUDA
        RequestStats,            // /EST
        CommerceStart,           // /COMERCIAR
        BankStart,               // /BOVEDA
        Enlist,                  // /ENLISTAR
        Information,             // /INFORMACION
        Reward,                  // /RECOMPENSA
        UpTime,                  // /UPTIME
        Inquiry,                 // /ENCUESTA ( with no params )
        CentinelReport,          // /CENTINELA
        CouncilMessage,          // /BMSG
        RoleMasterRequest,       // /ROL
        GMRequest,               // /GM
        bugReport,               // /_BUG
        ChangeDescription,       // /DESC
        Punishments,             // /PENAS
        ChangePassword,          // /CONTRASEÑA
        Gamble,                  // /APOSTAR
        InquiryVote,             // /ENCUESTA ( with parameters )
        LeaveFaction,            // /RETIRAR ( with no arguments )
        BankExtractGold,         // /RETIRAR ( with arguments )
        BankDepositGold,         // /DEPOSITAR
        Denounce,                // /DENUNCIAR
        Ping,                    // /PING
        GMCommands,
        InitCrafting,
        Home,
        Consulta,
        RequestClaseForm,
        EligioClase,
        EligioFaccion,
        RequestFaccionForm,
        RequestRecompensaForm,
        EligioRecompensa,
        RequestGuildWindow,
        GuildFoundate,
        GuildConfirmFoundation,
        GuildRequest,
        MoveItem
    }

    /* Las ID de los comandos de GM empiezan en 0, pero deberían empezar en 1!!
       por eso cada vez que se mande la ID por el socket, hay que sumarle 1. */
    enum GmID {
        GMMessage,              // /GMSG
        showName,               // /SHOWNAME
        OnlineRoyalArmy,        // /ONLINEREAL
        OnlineChaosLegion,      // /ONLINECAOS
        GoNearby,               // /IRCERCA
        comment,                // /REM
        serverTime,             // /HORA
        Where,                  // /DONDE
        CreaturesInMap,         // /NENE
        WarpMeToTarget,         // /TELEPLOC
        WarpChar,               // /TELEP
        Silence,                // /SILENCIAR
        SOSShowList,            // /SHOW SOS
        SOSRemove,              // SOSDONE
        GoToChar,               // /IRA
        invisible,              // /INVISIBLE
        GMPanel,                // /PANELGM
        RequestUserList,        // LISTUSU
        Working,                // /TRABAJANDO
        Hiding,                 // /OCULTANDO
        Jail,                   // /CARCEL
        KillNPC,                // /RMATA
        WarnUser,               // /ADVERTENCIA
        EditChar,               // /MOD
        RequestCharInfo,        // /INFO
        RequestCharStats,       // /STAT
        RequestCharGold,        // /BAL
        RequestCharInventory,   // /INV
        RequestCharBank,        // /BOV
        RequestCharSkills,      // /SKILLS
        ReviveChar,             // /REVIVIR
        OnlineGM,               // /ONLINEGM
        OnlineMap,              // /ONLINEMAP
        Kick,                   // /ECHAR
        Execute,                // /EJECUTAR
        BanChar,                // /BAN
        UnbanChar,              // /UNBAN
        NPCFollow,              // /SEGUIR
        SummonChar,             // /SUM
        SpawnListRequest,       // /CC
        SpawnCreature,          // SPA
        ResetNPCInventory,      // /RESETINV
        CleanWorld,             // /LIMPIAR
        ServerMessage,          // /RMSG
        NickToIP,               // /NICK2IP
        IPToNick,               // /IP2NICK
        TeleportCreate,         // /CT
        TeleportDestroy,        // /DT
        RainToggle,             // /LLUVIA
        SetCharDescription,     // /SETDESC
        ForceMIDIToMap,         // /FORCEMIDIMAP
        ForceWAVEToMap,         // /FORCEWAVMAP
        RoyalArmyMessage,       // /REALMSG
        ChaosLegionMessage,     // /CAOSMSG
        CitizenMessage,         // /CIUMSG
        CriminalMessage,        // /CRIMSG
        TalkAsNPC,              // /TALKAS
        DestroyAllItemsInArea,  // /MASSDEST
        AcceptRoyalCouncilMember,// /ACEPTCONSE
        AcceptChaosCouncilMember,// /ACEPTCONSECAOS
        ItemsInTheFloor,        // /PISO
        MakeDumb,               // /ESTUPIDO
        MakeDumbNoMore,         // /NOESTUPIDO
        DumpIPTables,           // /DUMPSECURITY
        CouncilKick,            // /KICKCONSE
        SetTrigger,             // /TRIGGER
        AskTrigger,             // /TRIGGER with no args
        BannedIPList,           // /BANIPLIST
        BannedIPReload,         // /BANIPRELOAD
        BanIP,                  // /BANIP
        UnbanIP,                // /UNBANIP
        CreateItem,             // /ITEM
        DestroyItems,           // /DEST
        ChaosLegionKick,        // /NOCAOS
        RoyalArmyKick,          // /NOREAL
        ForceMIDIAll,           // /FORCEMIDI
        ForceWAVEAll,           // /FORCEWAV
        RemovePunishment,       // /BORRARPENA
        TileBlockedToggle,      // /BLOQ
        KillNPCNoRespawn,       // /MATA
        KillAllNearbyNPCs,      // /MASSKILL
        LastIP,                 // /LASTIP
        ChangeMOTD,             // /MOTDCAMBIA
        SetMOTD,                // ZMOTD
        SystemMessage,          // /SMSG
        CreateNPC,              // /ACC
        CreateNPCWithRespawn,   // /RACC
        NavigateToggle,         // /NAVE
        ServerOpenToUsersToggle,// /RESTRINGIR
        TurnOffServer,          // /APAGAR
        ResetFactions,          // /RAJAR
        RequestCharMail,        // /LASTEMAIL
        AlterPassword,          // /APASS
        AlterMail,              // /AEMAIL
        AlterName,              // /ANAME
        ToggleCentinelActivated,// /CENTINELAACTIVADO
        DoBackUp,               // /DOBACKUP
        SaveMap,                // /GUARDAMAPA
        ChangeMapInfoPK,        // /MODMAPINFO PK
        ChangeMapInfoBackup,    // /MODMAPINFO BACKUP
        ChangeMapInfoRestricted,// /MODMAPINFO RESTRINGIR
        ChangeMapInfoNoMagic,   // /MODMAPINFO MAGIASINEFECTO
        ChangeMapInfoLand,      // /MODMAPINFO TERRENO
        ChangeMapInfoZone,      // /MODMAPINFO ZONA
        SaveChars,              // /GRABAR
        CleanSOS,               // /BORRAR SOS
        ShowServerForm,         // /SHOW INT
        night,                  // /NOCHE
        KickAllChars,           // /ECHARTODOSPJS
        ReloadNPCs,             // /RELOADNPCS
        ReloadServerIni,        // /RELOADSINI
        ReloadSpells,           // /RELOADHECHIZOS
        ReloadObjects,          // /RELOADOBJ
        ChatColor,              // /CHATCOLOR
        Ignored,                // /IGNORADO
        CheckSlot,              // /SLOT
        SetIniVar,              // /SETINIVAR LLAVE CLAVE VALOR
        WarpToMap,              // /GO
        StaffMessage,           // /STAFF
        SearchObjs,             // /BUSCAR
        Countdown,              // /CUENTA
        WinTournament,          // /GANOTORNEO
        LoseTournament,         // /PERDIOTORNEO
        WinQuest,               // /GANOQUEST
        LoseQuest,              // /PERDIOQUEST
    }

    private BytesWritter w;
    private Queue<byte[]> cola;

    private long pingTime;

    public Queue<byte[]> getCola() {
        return cola;
    }

    /**
     * Arma un gran array de bytes, con cada array de la cola y lo devuelve
     */
    public byte[] removeAll() {
        /* Guardo el tamaño para asegurarme que voy a extraer solo lo que hay hasta éste punto, y evitar procesar algo
        que el thread principal me agregue */

        int size = cola.size;

        // Cantidad total de bytes
        int cant = 0;
        for (int i = 0; i < size; i++) {
            cant += cola.get(i).length;
        }

        byte[] totales = new byte[cant];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            byte[] bytes = cola.removeFirst();
            System.arraycopy(bytes, 0, totales, pos, bytes.length);
            pos += bytes.length;
        }

        return totales;
    }

    public long getPingTime() {
        return pingTime;
    }

    public void setPingTime(long pingTime) {
        this.pingTime = pingTime;
    }

    public ClientPackages() {
        w = new BytesWritter();
        cola = new Queue<>();
    }

    /**
     * Agrega el array pendiente a la cola, y lo vacía para mas escritura de paquetes
     */
    public void write() {
        if (w.getSize() > 0) {
            cola.addLast(w.getBytes());
            w.clear();
        }
    }

    /**
     * Petición para conectarse
     */
    public void writeLoginExistingChar(String name, String password) {
        w.writeByte(ID.LoginExistingChar.ordinal());
        w.writeString(name);
        w.writeString(password);
        w.writeByte(0);
        w.writeByte(13);
        w.writeByte(0);
    }

    public void writeThrowDices() {
        w.writeByte(ID.ThrowDices.ordinal());
    }

    public void writeLoginNewChar(String name, String password, String mail, int raza, int sexo, int ciudad) {
        w.writeByte(ID.LoginNewChar.ordinal());
        w.writeString(name);
        w.writeString(password);
        w.writeByte(0);
        w.writeByte(13);
        w.writeByte(0);
        w.writeByte(raza);
        w.writeByte(sexo);
        w.writeShort(5); //TODO: cabeza hardcodeada, no se deberia mandar esto, tiene que hacerse random desde el sv.
        w.writeString(mail);
        w.writeByte(ciudad);

        for (int i = 0; i < 22; i++) {
            if (i == 0)
                w.writeByte(10);
            else
                w.writeByte(0);
        }
    }

    /**
     * Caminar hacia una dirección
     */
    public void writeWalk(Config.Direccion dir) {
        w.writeByte(ID.Walk.ordinal());
        w.writeByte((dir.ordinal() + 1));
    }

    public void writePing() {
        if (getPingTime() != 0) return;
        setPingTime(TimeUtils.millis());
        w.writeByte(ID.Ping.ordinal());
    }

    /**
     * Cambio de dirección sin cambiar de tile
     */
    public void writeChangeHeading(Config.Direccion dir) {
        w.writeByte(ID.ChangeHeading.ordinal());
        w.writeByte(dir.ordinal() + 1);
    }

    /**
     * Petición de posición (para corregirla)
     */
    public void writeRequestPositionUpdate() {
        w.writeByte(ID.RequestPositionUpdate.ordinal());
    }

    public void writeWarpChar(String name, int map, Position pos) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.WarpChar.ordinal() + 1);
        w.writeString(name);
        w.writeShort(map);
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeLeftClick(Position pos) {
        w.writeByte(ID.LeftClick.ordinal());
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeDoubleClick(Position pos) {
        w.writeByte(ID.DoubleClick.ordinal());
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeOnline() {
        w.writeByte(ID.Online.ordinal());
    }

    public void writeQuit() {
        w.writeByte(ID.Quit.ordinal());
    }

    public void writeTalk(String texto) {
        w.writeByte(ID.Talk.ordinal());
        w.writeString(texto);
    }

    public void writeMeditate() {
        w.writeByte(ID.Meditate.ordinal());
    }

    public void writeResucitate() {
        w.writeByte(ID.Resucitate.ordinal());
    }

    public void writeHeal() {
        w.writeByte(ID.Heal.ordinal());
    }

    public void writeHelp() {
        w.writeByte(ID.Help.ordinal());
    }

    public void writeRequestStats() {
        w.writeByte(ID.RequestStats.ordinal());
    }

    public void writeCommerceStart() {
        w.writeByte(ID.CommerceStart.ordinal());
    }

    public void writeCommerceEnd() {
        w.writeByte(ID.CommerceEnd.ordinal());
    }

    public void writeBankStart() {
        w.writeByte(ID.BankStart.ordinal());
    }

    public void writeEnlist() {
        w.writeByte(ID.Enlist.ordinal());
    }

    public void writeInformation() {
        w.writeByte(ID.Information.ordinal());
    }

    public void writeReward() {
        w.writeByte(ID.Reward.ordinal());
    }

    public void writeUpTime() {
        w.writeByte(ID.UpTime.ordinal());
    }

    public void writeInquiry() {
        w.writeByte(ID.Inquiry.ordinal());
    }

    public void writeInquiryVote(byte opt) {
        w.writeByte(ID.InquiryVote.ordinal());
        w.writeByte(opt);
    }

    public void writeWarpToMap(short map) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.WarpToMap.ordinal() + 1);
        w.writeShort(map);
    }

    public void writeMoveItem(int slot1, int slot2) {
        w.writeByte(ID.MoveItem.ordinal());
        w.writeByte(slot1);
        w.writeByte(slot2);
        w.writeByte(1); // TODO: tipo (inventario, banco, etc...) no se maneja todavía en el servidor...
    }

    public void writeUseItem(int index) {
        w.writeByte(ID.UseItem.ordinal());
        w.writeByte(index);
    }

    public void writeEquipItem(int index) {
        w.writeByte(ID.EquipItem.ordinal());
        w.writeByte(index);
    }

    public void writeTeleportCreate(short map, byte x, byte y, byte radio) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.TeleportCreate.ordinal() + 1);
        w.writeShort(map);
        w.writeByte(x);
        w.writeByte(y);
        w.writeByte(radio);
    }

    public void writeTeleportDestroy() {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.TeleportDestroy.ordinal() + 1);
    }

    public void writeEditChar(String nombre, EditOptions caract, String arg1, String arg2) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.EditChar.ordinal() + 1);
        w.writeString(nombre);
        w.writeByte(caract.ordinal() + 1);
        w.writeString(arg1);
        w.writeString(arg2);
    }

    public void writeSummonChar(String nombre) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.SummonChar.ordinal() + 1);
        w.writeString(nombre);
    }

    public void writeGoToChar(String nombre) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.GoToChar.ordinal() + 1);
        w.writeString(nombre);
    }

    public void writeServerMessage(String nombre) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.ServerMessage.ordinal() + 1);
        w.writeString(nombre);
    }

    public void writeCreateItem(short index, short cantidad) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.CreateItem.ordinal() + 1);
        w.writeShort(index);
        w.writeShort(cantidad);
    }

    public void writeDestroyItems() {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.DestroyItems.ordinal() + 1);
    }

    public void writeSearchObjs(String texto) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.SearchObjs.ordinal() + 1);
        w.writeString(texto);
    }

}