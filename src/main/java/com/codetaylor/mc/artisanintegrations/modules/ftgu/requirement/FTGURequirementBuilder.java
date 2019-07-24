package com.codetaylor.mc.artisanintegrations.modules.ftgu.requirement;

import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementBuilder;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FTGURequirementBuilder
    implements IRequirementBuilder<FTGURequirementContext, FTGURequirement> {

  private Set<String> exclude;
  private Set<String> requireOne;
  private Set<String> requireAll;

  public FTGURequirementBuilder() {

    this.exclude = Collections.emptySet();
    this.requireOne = Collections.emptySet();
    this.requireAll = Collections.emptySet();
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

  public FTGURequirementBuilder allOf(@Nonnull String... requireAll) {

    this.requireAll = new HashSet<>();

    for (String technology : requireAll) {
      this.requireAll.add(technology.toLowerCase());
    }
    return this;
  }

  public FTGURequirementBuilder anyOf(@Nonnull String... requireOne) {

    this.requireOne = new HashSet<>();

    for (String technology : requireOne) {
      this.requireOne.add(technology.toLowerCase());
    }
    return this;
  }

  public FTGURequirementBuilder exclude(@Nonnull String... exclude) {

    this.exclude = new HashSet<>();

    for (String technology : exclude) {
      this.exclude.add(technology.toLowerCase());
    }
    return this;
  }

  @Override
  public FTGURequirement create() {

    return new FTGURequirement(this.requireAll, this.requireOne, this.exclude);
  }
}
