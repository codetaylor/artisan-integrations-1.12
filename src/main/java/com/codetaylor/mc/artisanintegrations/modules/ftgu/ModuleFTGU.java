package com.codetaylor.mc.artisanintegrations.modules.ftgu;

import com.codetaylor.mc.artisanintegrations.ModArtisanIntegrations;
import com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement.FTGURequirementBuilder;
import com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement.FTGURequirementContext;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementBuilderSupplier;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.RequirementContextSupplier;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleFTGU
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanIntegrations.MOD_ID;

  public static final Logger LOGGER = LogManager.getLogger(ModuleFTGU.class);

  public ModuleFTGU() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(this);

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.codetaylor.mc.artisanintegrations.modules.ftgu.crafttweaker.ZenFTGURequirement"
    );
  }

  @SubscribeEvent
  public void onRegisterRequirementContextSupplierEvent(RegistryEvent.Register<RequirementContextSupplier> event) {

    event.getRegistry().register(
        new RequirementContextSupplier(MOD_ID, "ftgumod", FTGURequirementContext::new)
    );
  }

  @SubscribeEvent
  public void onRegisterRequirementBuilderSupplierEvent(RegistryEvent.Register<RequirementBuilderSupplier> event) {

    event.getRegistry().register(
        new RequirementBuilderSupplier(MOD_ID, "ftgumod", FTGURequirementBuilder::new)
    );
  }
}
