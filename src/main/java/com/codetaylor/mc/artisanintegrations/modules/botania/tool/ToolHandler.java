package com.codetaylor.mc.artisanintegrations.modules.botania.tool;

import com.codetaylor.mc.artisanintegrations.modules.botania.ModuleBotaniaConfig;
import com.codetaylor.mc.artisanworktables.api.recipe.DefaultToolHandler;
import com.codetaylor.mc.artisanworktables.api.recipe.IToolHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ToolHandler
    implements IToolHandler {

  @Override
  public boolean matches(ItemStack itemStack) {

    return itemStack.getItem() instanceof IManaUsingItem;
  }

  @Override
  public boolean canAcceptAllDamage(ItemStack itemStack, int damage) {

    return itemStack.getMaxDamage() - itemStack.getItemDamage() >= damage;
  }

  @Override
  public boolean applyDamage(World world, ItemStack itemStack, int damage, EntityPlayer player, boolean simulate) {

    if (ModuleBotaniaConfig.ENABLE_CRAFT_WITH_MANA) {
      int manaToRequest = damage * ModuleBotaniaConfig.MANA_PER_TOOL_DAMAGE;
      boolean playerHasEnoughMana = ManaItemHandler.requestManaExactForTool(itemStack, player, manaToRequest, simulate);

      if (!playerHasEnoughMana) {
        // Player does not have enough mana to cover the cost, damage the tool.
        return DefaultToolHandler.INSTANCE.applyDamage(world, itemStack, damage, player, simulate);

      } else {
        // Player has enough mana to cover the cost, return false to indicate the tool was not broken.
        return false;
      }

    } else {
      // If crafting with mana is disabled, damage the tool.
      return DefaultToolHandler.INSTANCE.applyDamage(world, itemStack, damage, player, simulate);
    }
  }
}
