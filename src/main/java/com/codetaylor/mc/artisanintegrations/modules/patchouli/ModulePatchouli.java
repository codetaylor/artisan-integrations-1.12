package com.codetaylor.mc.artisanintegrations.modules.patchouli;

import com.codetaylor.mc.artisanintegrations.ModArtisanIntegrations;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.patchouli.api.PatchouliAPI;

public class ModulePatchouli
    extends ModuleBase {

  public static final String MOD_ID = ModArtisanIntegrations.MOD_ID;

  private static final String TEMPLATE_FOLDER = "patchouli/templates/";
  private static final Logger LOGGER = LogManager.getLogger(ModulePatchouli.class);

  public ModulePatchouli() {

    super(0, MOD_ID);
  }

  @Override
  public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onClientPreInitializationEvent(event);

    this.registerIncludes(
        "worktable",
        "workstation"
    );
  }

  private void registerIncludes(String... names) {

    for (String name : names) {
      this.registerInclude(name);
    }
  }

  private void registerInclude(String name) {

    final ResourceLocation internalResourceLocation = new ResourceLocation(MOD_ID, TEMPLATE_FOLDER + name + ".json");
    final ResourceLocation externalResourceLocation = new ResourceLocation(MOD_ID, name);

    PatchouliAPI.instance.registerTemplateAsBuiltin(
        externalResourceLocation,
        () -> {
          try {
            Minecraft minecraft = Minecraft.getMinecraft();
            IResourceManager resourceManager = minecraft.getResourceManager();
            IResource resource = resourceManager.getResource(internalResourceLocation);
            return resource.getInputStream();

          } catch (Exception e) {
            LOGGER.error("Error loading template: " + internalResourceLocation, e);
          }
          return null;
        }
    );
  }
}
