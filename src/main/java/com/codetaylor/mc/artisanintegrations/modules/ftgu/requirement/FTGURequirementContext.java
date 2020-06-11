package com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement;

import com.codetaylor.mc.artisanintegrations.modules.ftgu.ModuleFTGU;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.ICraftingContext;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementContext;
import ftgumod.api.FTGUAPI;
import ftgumod.api.technology.ITechnology;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class FTGURequirementContext
    implements IRequirementContext {

  private List<String> researchedTechnologies;

  @Override
  public void initialize(ICraftingContext craftingContext) {

    Optional<EntityPlayer> optionalPlayer = craftingContext.getPlayer();

    if (optionalPlayer.isPresent()) {
      initialize(optionalPlayer.get());

    } else {
      this.researchedTechnologies = Collections.emptyList();
    }
  }

  /* package */ void initialize(EntityPlayer player) {

    Collection<ITechnology> technologies = FTGUAPI.technologyManager.getTechnologies();
    List<String> researched = new ArrayList<>(technologies.size());

    for (ITechnology technology : technologies) {

      if (technology.isResearched(player)) {
        ResourceLocation registryName = technology.getRegistryName();

        if (registryName != null) {
          researched.add(registryName.toString());

        } else {
          ModuleFTGU.LOGGER.error("Technology missing registry name: " + technology);
        }
      }
    }

    this.researchedTechnologies = Collections.unmodifiableList(researched);
  }

  public List<String> getResearchedTechnologies() {

    return this.researchedTechnologies;
  }
}
