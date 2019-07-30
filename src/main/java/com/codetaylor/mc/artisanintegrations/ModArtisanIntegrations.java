package com.codetaylor.mc.artisanintegrations;

import com.codetaylor.mc.artisanintegrations.modules.botania.ModuleBotania;
import com.codetaylor.mc.artisanintegrations.modules.ftgu.ModuleFTGU;
import com.codetaylor.mc.artisanintegrations.modules.gamestages.ModuleGameStages;
import com.codetaylor.mc.artisanintegrations.modules.gregtech.ModuleGregTech;
import com.codetaylor.mc.artisanintegrations.modules.reskillable.ModuleReskillable;
import com.codetaylor.mc.athenaeum.gui.GuiHandler;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.module.ModuleManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(
    modid = ModArtisanIntegrations.MOD_ID,
    version = ModArtisanIntegrations.VERSION,
    name = ModArtisanIntegrations.NAME
    //@@DEPENDENCIES@@
)
public class ModArtisanIntegrations {

  public static final String MOD_ID = "artisanintegrations";
  public static final String VERSION = "@@VERSION@@";
  public static final String NAME = "Artisan Integrations";

  @Mod.Instance
  public static ModArtisanIntegrations INSTANCE;

  private final ModuleManager moduleManager;

  public ModArtisanIntegrations() {

    this.moduleManager = new ModuleManager(MOD_ID);
  }

  @Mod.EventHandler
  public void onConstructionEvent(FMLConstructionEvent event) {

    if (Loader.isModLoaded("ftgumod")) {
      ModArtisanIntegrations.INSTANCE.registerIntegrationHandler(
          "ftgumod",
          "com.codetaylor.mc.athenaeum.integration.SimplePluginHandler"
      );
    }

    Map<String, Class<? extends ModuleBase>> register = new LinkedHashMap<>();

    register.put("gamestages", ModuleGameStages.class);
    register.put("reskillable", ModuleReskillable.class);
    register.put("gregtech", ModuleGregTech.class);
    register.put("botania", ModuleBotania.class);
    register.put("ftgumod", ModuleFTGU.class);

    register.forEach((key, value) -> {

      if (Loader.isModLoaded(key)) {
        this.moduleManager.registerModules(value);
      }
    });

    this.moduleManager.onConstructionEvent();
    this.moduleManager.routeFMLStateEvent(event);
  }

  public void registerIntegrationHandler(String modId, String handler) {

    this.moduleManager.registerIntegrationHandler(modId, handler);
  }

  @Mod.EventHandler
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onInitializationEvent(FMLInitializationEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStartedEvent(FMLServerStartedEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppingEvent(FMLServerStoppingEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

  @Mod.EventHandler
  public void onServerStoppedEvent(FMLServerStoppedEvent event) {

    this.moduleManager.routeFMLStateEvent(event);
  }

}
