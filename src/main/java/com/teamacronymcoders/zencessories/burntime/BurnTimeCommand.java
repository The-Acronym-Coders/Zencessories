package com.teamacronymcoders.zencessories.burntime;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class BurnTimeCommand extends CraftTweakerCommand {
    public BurnTimeCommand() {
        super("burnTime");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct burnTime", "/ct burnTime", true),
                getNormalMessage(" \u00A73Outputs a list of burn times in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Starting Calculation of Burn Times, This may take some time"));
        ForgeRegistries.ITEMS.getValues().stream()
                .map(BurnTimeCommand::getSubItems)
                .flatMap(List::stream)
                .map(BurnTimeCommand::getBurnTimeString)
                .filter(Objects::nonNull)
                .forEach(CraftTweakerAPI::logInfo);
        sender.sendMessage(new TextComponentString("Burn Time Calculation Complete, See CraftTweaker.log for info"));

    }

    private static String getBurnTimeString(ItemStack itemStack) {
        int burnTime = TileEntityFurnace.getItemBurnTime(itemStack);
        return burnTime > 0 ? String.format("%s:%d - %d", itemStack.getItem().getRegistryName(), itemStack.getItemDamage(),
                burnTime) : null;
    }

    private static List<ItemStack> getSubItems(Item item) {
        CreativeTabs[] creativeTabs = item.getCreativeTabs();
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        for (CreativeTabs creativeTab : creativeTabs) {
            item.getSubItems(Optional.ofNullable(creativeTab).orElse(CreativeTabs.SEARCH), itemStacks);
        }
        return itemStacks;
    }
}
