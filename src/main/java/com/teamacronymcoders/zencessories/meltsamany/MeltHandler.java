package com.teamacronymcoders.zencessories.meltsamany;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MeltHandler {
    private static MeltHandler instance;

    private Map<String, List<FluidStack>> meltEntries = Maps.newHashMap();
    private List<IMeltFunction> meltFunctions = Lists.newArrayList();

    private MeltHandler() {

    }

    static MeltHandler getInstance() {
        if (instance == null) {
            instance = new MeltHandler();
            MinecraftForge.EVENT_BUS.register(instance);
        }
        return instance;
    }

    public Map<String, List<FluidStack>> getMeltEntries() {
        return meltEntries;
    }

    public List<IMeltFunction> getMeltFunctions() {
        return meltFunctions;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMelting(TinkerSmelteryEvent.OnMelting onMeltingEvent) {
        String itemStackString = onMeltingEvent.itemStack.toString();
        SmelteryTank tank = onMeltingEvent.smeltery.getTank();
        int currentAvailable = tank.getCapacity() - tank.getFluidAmount();
        currentAvailable -= onMeltingEvent.result.amount;
        if (meltEntries.containsKey(itemStackString)) {
            Iterator<FluidStack> additional = meltEntries.get(itemStackString).iterator();
            while (additional.hasNext() && currentAvailable > 0) {
                currentAvailable = fillTank(additional.next(), tank, currentAvailable);
            }
        }

        if (currentAvailable > 0) {
            IItemStack itemStack = new MCItemStack(onMeltingEvent.itemStack.copy());
            ILiquidStack output = new MCLiquidStack(onMeltingEvent.result.copy());
            ILiquidStack fuel = new MCLiquidStack(onMeltingEvent.smeltery.currentFuel.copy());
            List<ILiquidStack> returned = Lists.newArrayList();
            for (IMeltFunction meltFunction : meltFunctions) {
                returned.addAll(meltFunction.onMelt(itemStack, output, fuel));
            }
            Iterator<ILiquidStack> returnedIterator = returned.iterator();
            while (returnedIterator.hasNext() && currentAvailable > 0) {
                currentAvailable = fillTank(returnedIterator.next(), tank, currentAvailable);
            }
        }
    }

    private int fillTank(ILiquidStack liquidStack, SmelteryTank smelteryTank, int currentAvailable) {
        if (liquidStack.getInternal() instanceof FluidStack) {
            currentAvailable = fillTank((ILiquidStack) liquidStack.getInternal(), smelteryTank, currentAvailable);
        }
        return currentAvailable;
    }

    private int fillTank(FluidStack currentStack, SmelteryTank smelteryTank, int currentAvailable) {
        if (currentStack.amount > currentAvailable) {
            currentStack.amount = currentAvailable;
        }
        if (currentStack.amount >= 0) {
            currentAvailable -= currentStack.amount;
            smelteryTank.fill(currentStack, true);
        }
        return currentAvailable;
    }
}
