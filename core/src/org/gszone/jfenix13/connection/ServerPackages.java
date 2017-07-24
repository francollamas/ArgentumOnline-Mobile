package org.gszone.jfenix13.connection;

import com.badlogic.gdx.utils.Array;

import static org.gszone.jfenix13.utils.Bytes.*;

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
        PlayMIDI,                // TM
        PlayWave,                // TW
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

    /**
     * Ejecuta el método correspondiente al ID del paquete recibido.
     */
    public void handleReceived(Array<Byte> bytes) {
        while (bytes.size > 0) {
            ID id = ID.values()[readByte(bytes)];

            switch (id) {
                case Logged:
                    handleLogged();
                    break;
            }
        }
    }

    private void handleLogged() {
    }
}
