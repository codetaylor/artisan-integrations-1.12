package com.codetaylor.mc.artisanintegrations.modules.botania;

import com.codetaylor.mc.artisanintegrations.ModArtisanIntegrations;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.CustomToolMaterial;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.ItemWorktableTool;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.ToolHandler;
import com.codetaylor.mc.artisanworktables.api.ArtisanToolHandlers;
import com.codetaylor.mc.artisanworktables.api.event.ArtisanCustomToolMaterialRegistrationEvent;
import com.codetaylor.mc.artisanworktables.api.tool.CustomToolMaterialRegistrationEntry;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ModuleBotania
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanIntegrations.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModArtisanIntegrations.CREATIVE_TAB;

  private static final Logger LOGGER = LogManager.getLogger(ModuleBotania.class);

  public ModuleBotania() {

    super(0, MOD_ID);
    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();

    MinecraftForge.EVENT_BUS.register(this);
  }

  @Override
  public void onInitializationEvent(FMLInitializationEvent event) {

    super.onInitializationEvent(event);

    ArtisanToolHandlers.register(new ToolHandler());
  }

  @SubscribeEvent
  public void on(ArtisanCustomToolMaterialRegistrationEvent event) {

    List<CustomToolMaterialRegistrationEntry> materialList = event.getMaterialList();

    materialList.add(new CustomToolMaterialRegistrationEntry(new CustomToolMaterial(
        "manasteel",
        3,
        300,
        6.2f,
        2.0f,
        20,
        "3389ff",
        true,
        "ore:ingotManasteel",
        "material.athenaeum.manasteel",
        "artisansToolManasteel"
    ), ItemWorktableTool::new));

    materialList.add(new CustomToolMaterialRegistrationEntry(new CustomToolMaterial(
        "elementium",
        3,
        720,
        6.2f,
        2.0f,
        20,
        "f15cae",
        true,
        "ore:ingotElvenElementium",
        "material.athenaeum.elementium",
        "artisansToolElementium"
    ), ItemWorktableTool::new));

    materialList.add(new CustomToolMaterialRegistrationEntry(new CustomToolMaterial(
        "terrasteel",
        4,
        2300,
        9.0f,
        3.0f,
        26,
        "53f900",
        true,
        "ore:ingotTerrasteel",
        "material.athenaeum.terrasteel",
        "artisansToolTerrasteel"
    ), ItemWorktableTool::new));
  }

}
