package com.codetaylor.mc.artisanintegrations.modules.botania;

import com.codetaylor.mc.artisanintegrations.ModArtisanIntegrations;
import com.codetaylor.mc.artisanintegrations.lib.JsonInitializer;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.CustomToolMaterial;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.CustomToolMaterialList;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.ItemWorktableTool;
import com.codetaylor.mc.artisanintegrations.modules.botania.tool.ToolHandler;
import com.codetaylor.mc.artisanworktables.api.ArtisanToolHandlers;
import com.codetaylor.mc.artisanworktables.api.event.ArtisanCustomToolMaterialRegistrationEvent;
import com.codetaylor.mc.artisanworktables.api.tool.CustomToolMaterialRegistrationEntry;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModuleBotania
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanIntegrations.MOD_ID;

  private static final Logger LOGGER = LogManager.getLogger(ModuleBotania.class);

  public ModuleBotania() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(this);
  }

  @Override
  public void onInitializationEvent(FMLInitializationEvent event) {

    super.onInitializationEvent(event);

    ArtisanToolHandlers.register(new ToolHandler());
  }

  @SubscribeEvent
  public void on(ArtisanCustomToolMaterialRegistrationEvent event) {

    Path configurationPath = Paths.get(this.getConfigurationDirectory().toString(), ModuleBotania.MOD_ID);

    CustomToolMaterialList customToolMaterialList = JsonInitializer.generateAndReadCustom(
        configurationPath,
        "module.botania.Tools-Generated.json",
        "module.botania.Tools-Custom.json",
        CustomToolMaterialList.class,
        () -> {
          ArrayList<CustomToolMaterial> list = new ArrayList<>();

          list.add(new CustomToolMaterial(
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
          ));

          list.add(new CustomToolMaterial(
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
          ));

          list.add(new CustomToolMaterial(
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
          ));

          return new CustomToolMaterialList(list);
        },
        ModuleBotania.LOGGER
    );

    if (customToolMaterialList != null) {
      List<CustomToolMaterialRegistrationEntry> materialList = event.getMaterialList();

      for (CustomToolMaterial customToolMaterial : customToolMaterialList.getList()) {
        materialList.add(new CustomToolMaterialRegistrationEntry(customToolMaterial, ItemWorktableTool::new));
      }

    } else {
      ModuleBotania.LOGGER.error("Unable to load custom tool list");
    }
  }
}
