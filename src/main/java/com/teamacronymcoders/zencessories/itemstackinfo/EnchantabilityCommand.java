package com.teamacronymcoders.zencessories.itemstackinfo;

import net.minecraft.item.ItemStack;

public class EnchantabilityCommand extends ItemStackInfoCommand {
    public EnchantabilityCommand() {
        super("enchantability");
    }

    @Override
    protected String getStringForItemStack(ItemStack itemStack) {
        int enchantability = itemStack.getItem().getItemEnchantability(itemStack);
        return enchantability > 0 ? String.format("%s:%d - %d", itemStack.getItem().getRegistryName(), itemStack.getItemDamage(),
                enchantability) : null;
    }
}
