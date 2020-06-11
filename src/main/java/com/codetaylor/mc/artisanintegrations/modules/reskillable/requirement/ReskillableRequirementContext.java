package com.codetaylor.mc.artisanintegrations.modules.reskillable.requirement;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.ICraftingContext;
import com.codetaylor.mc.artisanworktables.api.recipe.requirement.IRequirementContext;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;
import java.util.Optional;

public class ReskillableRequirementContext
    implements IRequirementContext {

  private PlayerData playerData;

  @Override
  public void initialize(ICraftingContext craftingContext) {

    Optional<EntityPlayer> optionalPlayer = craftingContext.getPlayer();
    optionalPlayer.ifPresent(player -> this.playerData = PlayerDataHandler.get(player));
  }

  @Nullable
  /* package */ PlayerData getPlayerData() {

    return this.playerData;
  }
}
