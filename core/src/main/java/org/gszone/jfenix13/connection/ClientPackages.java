package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Queue;
import org.gszone.jfenix13.general.General;
import org.gszone.jfenix13.utils.BytesWritter;

/**
 * Clase con los paquetes que se envían al servidor
 *
 * bytes: corresponde a la secuencia de paquetes que se está armando antes de ser enviada al servidor
 * cola: colección con las secuencais obtenidas en 'bytes' (el socket va leyendo esta cola constantemente)
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

    public Queue<byte[]> getCola() {
        return cola;
    }

    public ClientPackages() {
        w = new BytesWritter();
        cola = new Queue();
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

    /**
     * Caminar hacia una dirección
     */
    public void writeWalk(General.Direccion dir) {
        w.writeByte((byte)ID.Walk.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
    }

    /**
     * Cambio de dirección sin cambiar de tile
     */
    public void writeChangeHeading(General.Direccion dir) {
        w.writeByte((byte)ID.ChangeHeading.ordinal());
        w.writeByte((byte)(dir.ordinal() + 1));
    }

    /**
     * Petición de posición (para corregirla)
     */
    public void writeRequestPositionUpdate() {
        w.writeByte((byte)ID.RequestPositionUpdate.ordinal());
    }
}
