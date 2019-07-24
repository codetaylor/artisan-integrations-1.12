package com.codetaylor.mc.artisanintegrations.modules.botania.tool;

import com.codetaylor.mc.artisanintegrations.modules.botania.ModuleBotaniaConfig;
import com.codetaylor.mc.artisanworktables.api.recipe.IToolHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

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

    ToolCommons.damageItem(itemStack, damage, player, ModuleBotaniaConfig.MANA_PER_TOOL_DAMAGE);
    boolean broken = itemStack.getMaxDamage() == itemStack.getItemDamage();

    if (broken) {
      itemStack.shrink(1);
    }

    return broken;
  }
}
