package com.codetaylor.mc.artisanintegrations.modules.gregtech;

import com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement.GameStagesRequirementBuilder;
import com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement.GameStagesRequirementContext;
import com.codetaylor.mc.artisanworktables.ModArtisanWorktables;
import com.codetaylor.mc.artisanworktables.api.ArtisanAPI;
import com.codetaylor.mc.artisanworktables.api.ArtisanToolHandlers;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementBuilderSupplier;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementContextSupplier;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleGregTech
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanWorktables.MOD_ID;

  public ModuleGregTech() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(this);
  }

  @Override
  public void onInitializationEvent(FMLInitializationEvent event) {

    super.onInitializationEvent(event);

    ArtisanToolHandlers.register(new GTCEToolHandler());
  }
}
