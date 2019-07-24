package com.codetaylor.mc.artisanintegrations.modules.ftgu.crafttweaker;

import com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement.FTGURequirement;
import com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement.FTGURequirementBuilder;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirement;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementBuilder;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ZenFTGURequirementBuilder
    implements IRequirementBuilder {

  private FTGURequirementBuilder builder;

  /* package */ ZenFTGURequirementBuilder() {

    this.builder = new FTGURequirementBuilder();
  }

  @ZenMethod
  public ZenFTGURequirementBuilder allOf(String[] requireAll) {

    this.builder.allOf(requireAll);
    return this;
  }

  @ZenMethod
  public ZenFTGURequirementBuilder anyOf(String[] requireAny) {

    this.builder.anyOf(requireAny);
    return this;
  }

  @ZenMethod
  public ZenFTGURequirementBuilder exclude(String[] exclude) {

    this.builder.exclude(exclude);
    return this;
  }

  @Nonnull
  @Override
  public String getRequirementId() {

    return FTGURequirement.REQUIREMENT_ID;
  }

  @Nonnull
  @Override
  public ResourceLocation getResourceLocation() {

    return FTGURequirement.LOCATION;
  }

  @Nullable
  @Override
  public IRequirement create() {

    return this.builder.create();
  }

}
