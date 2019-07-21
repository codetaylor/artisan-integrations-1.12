package com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement;

import com.codetaylor.mc.artisanworktables.api.internal.recipe.ICraftingContext;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementContext;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameStagesRequirementContext
    implements IRequirementContext {

  private Set<String> unlockedStages;

  @Override
  public void initialize(ICraftingContext craftingContext) {

    EntityPlayer player = craftingContext.getPlayer();
    IStageData playerData = GameStageHelper.getPlayerData(player);
    Collection<String> unlockedStages = playerData.getStages();
    this.setUnlockedStages(unlockedStages);
  }

  /* package */ Set<String> getUnlockedStages() {

    return this.unlockedStages;
  }

  public void setUnlockedStages(Collection<String> unlockedStages) {

    this.unlockedStages = Collections.unmodifiableSet(new HashSet<>(unlockedStages));
  }
}
