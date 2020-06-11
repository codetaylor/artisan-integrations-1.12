package com.codetaylor.mc.artisanintegrations.modules.ftgu.plugin;

import com.codetaylor.mc.artisanworktables.api.event.ArtisanUpdateJEIRecipeVisibilityEvent;
import ftgumod.api.event.FTGUClientSyncEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Handles updating JEI on the client when FTGU syncs a player's technologies.
 */
public class PluginFTGU {

  public PluginFTGU() {

    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void on(FTGUClientSyncEvent.Post event) {

    MinecraftForge.EVENT_BUS.post(new ArtisanUpdateJEIRecipeVisibilityEvent());
  }
}
