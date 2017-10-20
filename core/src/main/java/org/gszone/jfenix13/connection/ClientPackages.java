package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.utils.BytesWritter;

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
        cola = new Queue<byte[]>();
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
        write();
    }

    public void writeThrowDices() {
        w.writeByte((byte)ID.ThrowDices.ordinal());
        write();
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
        write();
    }

    /**
     * Caminar hacia una dirección
     */
    public void writeWalk(General.Direccion dir) {
        w.writeByte((byte)ID.Walk.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
        w.writeByte((byte)ID.Ping.ordinal());
        writePing();
    }

    public void writePing() {
        if (getPingTime() != 0) return;
        setPingTime(TimeUtils.millis());
        write();
    }

    /**
     * Cambio de dirección sin cambiar de tile
     */
    public void writeChangeHeading(General.Direccion dir) {
        w.writeByte((byte)ID.ChangeHeading.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
        write();
    }

    /**
     * Petición de posición (para corregirla)
     */
    public void writeRequestPositionUpdate() {
        w.writeByte((byte)ID.RequestPositionUpdate.ordinal());
        write();
    }

}
