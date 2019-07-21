package com.codetaylor.mc.artisanintegrations.modules.gamestages;

import com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement.GameStagesRequirementBuilder;
import com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement.GameStagesRequirementContext;
import com.codetaylor.mc.artisanworktables.ModArtisanWorktables;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementBuilderSupplier;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementContextSupplier;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleGameStages
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanWorktables.MOD_ID;

  public ModuleGameStages() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(this);

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.codetaylor.mc.artisanintegrations.modules.gamestages.crafttweaker.ZenGameStagesRequirement"
    );

    this.registerIntegrationPlugin(
        "gamestages",
        "com.codetaylor.mc.artisanintegrations.modules.gamestages.plugin.PluginGameStages"
    );
  }

  @SubscribeEvent
  public void onRegisterRequirementContextSupplierEvent(RegistryEvent.Register<RequirementContextSupplier> event) {

    event.getRegistry().register(
        new RequirementContextSupplier(MOD_ID, "gamestages", GameStagesRequirementContext::new)
    );
  }

  @SubscribeEvent
  public void onRegisterRequirementBuilderSupplierEvent(RegistryEvent.Register<RequirementBuilderSupplier> event) {

    event.getRegistry().register(
        new RequirementBuilderSupplier(MOD_ID, "gamestages", GameStagesRequirementBuilder::new)
    );
  }

}
