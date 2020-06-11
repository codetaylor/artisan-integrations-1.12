package com.codetaylor.mc.artisanintegrations.modules.reskillable.requirement;

import codersafterdark.reskillable.api.data.RequirementHolder;
import com.codetaylor.mc.artisanintegrations.modules.reskillable.ModuleReskillable;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirement;
import net.minecraft.util.ResourceLocation;

public class ReskillableRequirement
    implements IRequirement<ReskillableRequirementContext> {

  public static final String REQUIREMENT_ID = "reskillable";
  public static final ResourceLocation LOCATION = new ResourceLocation(
      ModuleReskillable.MOD_ID,
      REQUIREMENT_ID
  );

  private final RequirementHolder requirementHolder;

  public ReskillableRequirement(RequirementHolder requirementHolder) {

    this.requirementHolder = requirementHolder;
  }

  @Override
  public ResourceLocation getResourceLocation() {

    return LOCATION;
  }

  @Override
  public boolean match(ReskillableRequirementContext context) {

    return context.getPlayerData() != null && context.getPlayerData().matchStats(this.requirementHolder);
  }
}
