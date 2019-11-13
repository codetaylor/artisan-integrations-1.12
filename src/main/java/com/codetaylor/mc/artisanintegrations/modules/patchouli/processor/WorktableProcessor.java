package com.codetaylor.mc.artisanintegrations.modules.patchouli.processor;

import com.codetaylor.mc.artisanworktables.api.ArtisanAPI;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.IArtisanItemStack;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.OutputWeightPair;
import com.codetaylor.mc.artisanworktables.api.recipe.IArtisanRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.Arrays;

public class WorktableProcessor
    implements IComponentProcessor {

  private IArtisanRecipe recipe;

  @Override
  public void setup(IVariableProvider<String> variables) {

    String recipe = variables.get("recipe");
    this.recipe = ArtisanAPI.getRecipe(recipe);
  }

  @Override
  public String process(String key) {

    if (this.recipe != null) {

      if (key.startsWith("item")) {
        int index = Integer.parseInt(key.substring(4)) - 1;
        Ingredient ingredient = this.recipe.getIngredientList().get(index).toIngredient();
        ItemStack[] stacks = ingredient.getMatchingStacks();
        ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];

        return ItemStackUtil.serializeStack(stack);

      } else if (key.equals("output")) {
        ItemStack[] itemStacks = this.recipe.getOutputWeightPairList().stream()
            .map(OutputWeightPair::getOutput)
            .map(IArtisanItemStack::toItemStack)
            .toArray(ItemStack[]::new);
        Ingredient ingredient = Ingredient.fromStacks(itemStacks);
        return ItemStackUtil.serializeIngredient(ingredient);

      } else if (key.equals("tool")) {
        int index = Integer.parseInt(key.substring(4)) - 1;
        IArtisanItemStack[] tools = this.recipe.getTools(index);
        ItemStack[] itemStacks = Arrays.stream(tools)
            .map(IArtisanItemStack::toItemStack)
            .toArray(ItemStack[]::new);

        if (itemStacks.length > 0) {
          Ingredient ingredient = Ingredient.fromStacks(itemStacks);
          return ItemStackUtil.serializeIngredient(ingredient);
        }

        return "";

      } else if (key.equals("tool_damage")) {
        int index = Integer.parseInt(key.substring(11)) - 1;
        return String.valueOf(this.recipe.getToolDamage(index));
      }
    }

    return null;
  }
}
