package com.teamacronymcoders.zencessories;

import com.teamacronymcoders.zencessories.burntime.BurnTimeCommand;
import crafttweaker.mc1120.commands.CTChatCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.teamacronymcoders.zencessories.Zencessories.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class Zencessories {
    public static final String MOD_ID = "zencessories";
    public static final String MOD_NAME = "Zencessories";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDS = "required-after:crafttweaker;after:tconstruct;";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CTChatCommand.registerCommand(new BurnTimeCommand());
    }
}
