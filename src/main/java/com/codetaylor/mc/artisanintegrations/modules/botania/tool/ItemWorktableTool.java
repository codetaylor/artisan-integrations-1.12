package com.codetaylor.mc.artisanintegrations.modules.botania.tool;

import com.codetaylor.mc.artisanintegrations.modules.botania.ModuleBotaniaConfig;
import com.codetaylor.mc.artisanworktables.api.internal.tool.CustomMaterial;
import com.codetaylor.mc.artisanworktables.api.tool.ItemWorktableToolBase;
import com.codetaylor.mc.artisanworktables.api.tool.reference.EnumWorktableToolType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nonnull;
import java.util.Collections;

public class ItemWorktableTool
    extends ItemWorktableToolBase
    implements IManaUsingItem {

  public ItemWorktableTool(
      EnumWorktableToolType type,
      CustomMaterial material
  ) {

    super(material.getToolMaterial(), Collections.emptySet(), type, material);
    this.setMaxStackSize(1);
  }

  @Override
  public boolean usesMana(ItemStack itemStack) {

    return true;
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity player, int itemSlot, boolean isSelected) {

    if (!world.isRemote
        && player instanceof EntityPlayer
        && stack.getItemDamage() > 0
        && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, ModuleBotaniaConfig.MANA_PER_DAMAGE * 2, true)) {
      stack.setItemDamage(stack.getItemDamage() - 1);
    }
  }

  @Override
  public boolean getIsRepairable(ItemStack toRepair, @Nonnull ItemStack repair) {

    return repair.getItem() == ModItems.manaResource
        && repair.getItemDamage() == 0 || super.getIsRepairable(toRepair, repair);
  }
}
