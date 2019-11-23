package com.codetaylor.mc.artisanintegrations.modules.patchouli.processor;

import com.codetaylor.mc.artisanworktables.api.internal.recipe.IArtisanIngredient;
import com.codetaylor.mc.artisanworktables.modules.worktables.ModuleWorktables;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.List;

public class WorkstationProcessor
    extends WorktableProcessor {

  @Override
  public String process(String key) {

    String result = super.process(key);

    if (result == null
        && this.recipe != null) {

      if (key.startsWith("secondary_item")) {
        int index = Integer.parseInt(key.substring(14)) - 1;
        List<IArtisanIngredient> secondaryIngredients = this.recipe.getSecondaryIngredients();

        if (index >= secondaryIngredients.size()) {
          return "";
        }

        Ingredient ingredient = secondaryIngredients.get(index).toIngredient();
        ItemStack[] stacks = ingredient.getMatchingStacks();
        ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];
        return ItemStackUtil.serializeStack(stack);
      }
    }

    return result;
  }

  @Override
  protected Item getTableItem() {

    return Item.getItemFromBlock(ModuleWorktables.Blocks.WORKSTATION);
  }

  @Override
  protected String getTableBackgroundImage(String tableName) {

    return "artisanworktables:textures/gui/workstation_" + tableName + ".png";
  }

}
