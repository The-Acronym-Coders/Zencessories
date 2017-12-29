package com.teamacronymcoders.zencessories.itemstackinfo;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class BurnTimeCommand extends ItemStackInfoCommand {
    public BurnTimeCommand() {
        super("burnTime");
    }

    @Override
    protected String getStringForItemStack(ItemStack itemStack) {
        int burnTime = TileEntityFurnace.getItemBurnTime(itemStack);
        return burnTime > 0 ? String.format("%s:%d - %d", itemStack.getItem().getRegistryName(), itemStack.getItemDamage(),
                burnTime) : null;

    }
}
