package com.codetaylor.mc.artisanintegrations.modules.gamestages.requirement;

import com.codetaylor.mc.artisanworktables.api.internal.recipe.ICraftingContext;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementContext;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class GameStagesRequirementContext
    implements IRequirementContext {

  private Set<String> unlockedStages;

  @Override
  public void initialize(ICraftingContext craftingContext) {

    Optional<EntityPlayer> optionalPlayer = craftingContext.getPlayer();

    if (optionalPlayer.isPresent()) {
      EntityPlayer player = optionalPlayer.get();
      IStageData playerData = GameStageHelper.getPlayerData(player);
      Collection<String> unlockedStages = playerData.getStages();
      this.setUnlockedStages(unlockedStages);

    } else {
      this.setUnlockedStages(Collections.emptyList());
    }
  }

  /* package */ Set<String> getUnlockedStages() {

    return this.unlockedStages;
  }

  public void setUnlockedStages(Collection<String> unlockedStages) {

    this.unlockedStages = Collections.unmodifiableSet(new HashSet<>(unlockedStages));
  }
}
