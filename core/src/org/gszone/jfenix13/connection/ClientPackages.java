package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Array;
import org.gszone.jfenix13.general.Main;

import static org.gszone.jfenix13.utils.Bytes.*;

/**
 * Clase con los paquetes que se envían al servidor
 */
public class ClientPackages {

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

    private Array<Byte> bytes;

    public ClientPackages() {
        bytes = new Array();
    }

    public void write() {
        Main.getInstance().getConnection().write(bytes);
        bytes.clear();
    }

    /**
     * Petición para conectarse
     */
    public void writeLoginExistingChar() {
        writeByte(bytes, (byte)ID.LoginExistingChar.ordinal());
        writeString(bytes, "Thusing");
        writeString(bytes, "asdasd");
        writeByte(bytes, (byte) 0);
        writeByte(bytes, (byte) 13);
        writeByte(bytes, (byte) 0);
    }
}
