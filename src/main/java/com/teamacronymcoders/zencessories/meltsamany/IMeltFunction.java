package com.teamacronymcoders.zencessories.meltsamany;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;

import java.util.List;

public interface IMeltFunction {
    List<ILiquidStack> onMelt(IItemStack input, ILiquidStack output, ILiquidStack fuel);
}
