package com.teamacronymcoders.zencessories;

import net.minecraftforge.fml.common.Mod;

import static com.teamacronymcoders.zencessories.Zencessories.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class Zencessories {
    public static final String MOD_ID = "zencessories";
    public static final String MOD_NAME = "Zencessories";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDS = "required-after:crafttweaker;after:tconstruct;";
}
