package com.teamacronymcoders.zencessories.itemstackinfo;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public abstract class ItemStackInfoCommand extends CraftTweakerCommand {
    public ItemStackInfoCommand(String subCommandName) {
        super(subCommandName);
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(" \u00A73Outputs a list of " + subCommandName + " in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("Starting Calculation of " + subCommandName + ", This may take some time"));
        ForgeRegistries.ITEMS.getValues().stream()
                .map(this::getSubItems)
                .flatMap(List::stream)
                .map(this::getStringForItemStack)
                .filter(Objects::nonNull)
                .forEach(CraftTweakerAPI::logInfo);
        sender.sendMessage(new TextComponentString(subCommandName + " Calculation Complete, See CraftTweaker.log for info"));

    }

    protected abstract String getStringForItemStack(ItemStack itemStack);

    protected List<ItemStack> getSubItems(Item item) {
        CreativeTabs[] creativeTabs = item.getCreativeTabs();
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        for (CreativeTabs creativeTab : creativeTabs) {
            item.getSubItems(Optional.ofNullable(creativeTab).orElse(CreativeTabs.SEARCH), itemStacks);
        }
        return itemStacks;
    }
}
