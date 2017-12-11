package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
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
        w.writeByte((byte)ID.LoginExistingChar.ordinal());
        w.writeString(name);
        w.writeString(password);
        w.writeByte((byte) 0);
        w.writeByte((byte) 13);
        w.writeByte((byte) 0);
    }

    public void writeThrowDices() {
        w.writeByte((byte)ID.ThrowDices.ordinal());
    }

    public void writeLoginNewChar(String name, String password, String mail, int raza, int sexo, int ciudad) {
        w.writeByte((byte)ID.LoginNewChar.ordinal());
        w.writeString(name);
        w.writeString(password);
        w.writeByte((byte) 0);
        w.writeByte((byte) 13);
        w.writeByte((byte) 0);
        w.writeByte((byte) raza);
        w.writeByte((byte) sexo);
        w.writeShort((byte) 5); //TODO: cabeza hardcodeada, no se deberia mandar esto, tiene que hacerse random desde el sv.
        w.writeString(mail);
        w.writeByte((byte) ciudad);

        for (int i = 0; i < 22; i++) {
            if (i == 0)
                w.writeByte((byte) 10);
            else
                w.writeByte((byte) 0);
        }
    }

    /**
     * Caminar hacia una dirección
     */
    public void writeWalk(Config.Direccion dir) {
        w.writeByte((byte)ID.Walk.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
    }

    public void writePing() {
        if (getPingTime() != 0) return;
        setPingTime(TimeUtils.millis());
        w.writeByte((byte)ID.Ping.ordinal());
    }

    /**
     * Cambio de dirección sin cambiar de tile
     */
    public void writeChangeHeading(Config.Direccion dir) {
        w.writeByte((byte)ID.ChangeHeading.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
    }

    /**
     * Petición de posición (para corregirla)
     */
    public void writeRequestPositionUpdate() {
        w.writeByte((byte)ID.RequestPositionUpdate.ordinal());
    }

    public void writeWarpChar(String name, int map, Position pos) {
        w.writeByte((byte)ID.GMCommands.ordinal());
        w.writeByte((byte)GmID.WarpChar.ordinal() + 1);
        w.writeString(name);
        w.writeShort((short) map);
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeLeftClick(Position pos) {
        w.writeByte((byte)ID.LeftClick.ordinal());
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeDoubleClick(Position pos) {
        w.writeByte((byte)ID.DoubleClick.ordinal());
        w.writeByte((byte) pos.getX());
        w.writeByte((byte) pos.getY());
    }

    public void writeOnline() {
        w.writeByte((byte)ID.Online.ordinal());
    }

    public void writeQuit() {
        w.writeByte((byte)ID.Quit.ordinal());
    }

    public void writeTalk(String texto) {
        w.writeByte((byte)ID.Talk.ordinal());
        w.writeString(texto);
    }

    public void writeMeditate() {
        w.writeByte((byte)ID.Meditate.ordinal());
    }

    public void writeResucitate() {
        w.writeByte((byte)ID.Resucitate.ordinal());
    }

    public void writeHeal() {
        w.writeByte((byte)ID.Heal.ordinal());
    }

    public void writeHelp() {
        w.writeByte((byte)ID.Help.ordinal());
    }

    public void writeRequestStats() {
        w.writeByte((byte)ID.RequestStats.ordinal());
    }

    public void writeCommerceStart() {
        w.writeByte((byte)ID.CommerceStart.ordinal());
    }

    public void writeBankStart() {
        w.writeByte((byte)ID.BankStart.ordinal());
    }

    public void writeEnlist() {
        w.writeByte((byte)ID.Enlist.ordinal());
    }

    public void writeInformation() {
        w.writeByte((byte)ID.Information.ordinal());
    }

    public void writeReward() {
        w.writeByte((byte)ID.Reward.ordinal());
    }

    public void writeUpTime() {
        w.writeByte((byte)ID.UpTime.ordinal());
    }

    public void writeInquiry() {
        w.writeByte((byte)ID.Inquiry.ordinal());
    }

    public void writeInquiryVote(byte opt) {
        w.writeByte((byte)ID.InquiryVote.ordinal());
        w.writeByte(opt);
    }

    public void writeWarpToMap(int map) {
        w.writeByte(ID.GMCommands.ordinal());
        w.writeByte(GmID.WarpToMap.ordinal() + 1);
        w.writeShort((short)map);
    }

}