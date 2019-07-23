package com.codetaylor.mc.artisanintegrations.modules.gregtech;

import com.codetaylor.mc.artisanintegrations.ModArtisanIntegrations;
import com.codetaylor.mc.artisanintegrations.modules.gregtech.tool.ToolHandler;
import com.codetaylor.mc.artisanworktables.api.ArtisanToolHandlers;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ModuleGregTech
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanIntegrations.MOD_ID;

  public ModuleGregTech() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(this);
  }

  @Override
  public void onInitializationEvent(FMLInitializationEvent event) {

    super.onInitializationEvent(event);

    ArtisanToolHandlers.register(new ToolHandler());
  }
}
