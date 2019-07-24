package com.codetaylor.mc.artisanintegrations.modules.ftgu.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.artisanintegrations.requirement.FTGU")
public class ZenFTGURequirement {

  @ZenMethod
  public static ZenFTGURequirementBuilder allOf(String[] requireAll) {

    ZenFTGURequirementBuilder builder = new ZenFTGURequirementBuilder();
    return builder.allOf(requireAll);
  }

  @ZenMethod
  public static ZenFTGURequirementBuilder anyOf(String[] requireAny) {

    ZenFTGURequirementBuilder builder = new ZenFTGURequirementBuilder();
    return builder.anyOf(requireAny);
  }

  @ZenMethod
  public static ZenFTGURequirementBuilder exclude(String[] exclude) {

    ZenFTGURequirementBuilder builder = new ZenFTGURequirementBuilder();
    return builder.exclude(exclude);
  }
}
