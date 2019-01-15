package com.teamacronymcoders.zencessories.zenextensions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Optional;

@ZenRegister
@ZenExpansion("crafttweaker.item.IItemStack")
public class IItemStackFoodExtension {
    @ZenMethod
    public static int getHealAmount(IItemStack itemStack) {
        return getItemFood(itemStack)
            .map(pair -> pair.getLeft().getHealAmount(pair.getRight()))
            .orElse(-1);
    }

    @ZenMethod
    public static float getSaturationModifier(IItemStack itemStack) {
        return getItemFood(itemStack)
                .map(pair -> pair.getLeft().getSaturationModifier(pair.getRight()))
                .orElse(-1F);
    }

    @ZenMethod
    public static boolean isFood(IItemStack itemStack) {
        return getItemFood(itemStack).isPresent();
    }

    private static Optional<Pair<ItemFood, ItemStack>> getItemFood(IItemStack itemStack) {
        Object actualStack = itemStack.getInternal();
        if (actualStack instanceof ItemStack) {
            Item item = ((ItemStack)actualStack).getItem();
            if (item instanceof ItemFood) {
                return Optional.of(Pair.of((ItemFood)item, (ItemStack)actualStack));
            }
        }
        return Optional.empty();
    }
}
