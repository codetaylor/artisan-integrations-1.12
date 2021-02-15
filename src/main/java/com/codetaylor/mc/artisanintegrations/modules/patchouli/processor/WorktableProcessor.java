package com.codetaylor.mc.artisanintegrations.modules.patchouli.processor;

import com.codetaylor.mc.artisanworktables.api.ArtisanAPI;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.IArtisanIngredient;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.IArtisanItemStack;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.OutputWeightPair;
import com.codetaylor.mc.artisanworktables.api.internal.reference.EnumTier;
import com.codetaylor.mc.artisanworktables.api.internal.reference.EnumType;
import com.codetaylor.mc.artisanworktables.api.recipe.IArtisanRecipe;
import com.codetaylor.mc.artisanworktables.modules.worktables.ModuleWorktables;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.Arrays;
import java.util.List;

public class WorktableProcessor
    implements IComponentProcessor {

  protected String tableName;
  protected IArtisanRecipe recipe;

  @Override
  public void setup(IVariableProvider<String> variables) {

    String recipe = variables.get("recipe");
    this.tableName = recipe.split(":")[0];
    this.recipe = ArtisanAPI.getRecipe(recipe);

    if (this.recipe == null) {
      throw new IllegalArgumentException("Can't find Artisan Worktables recipe with name " + recipe);
    }
  }

  @Override
  public String process(String key) {

    if (this.recipe != null) {

      if ("table_image".equals(key)) {
        return this.getTableBackgroundImage(this.tableName);

      } else if (key.startsWith("item")) {

        int tableIndex = Integer.parseInt(key.substring(4)) - 1;
        List<IArtisanIngredient> ingredientList = this.recipe.getIngredientList();
        int recipeIndex;

        if (this.recipe.isShaped()) {
          int tableWidth = (this.getTableTier() == EnumTier.WORKSHOP) ? 5 : 3;
          int tableHeight = (this.getTableTier() == EnumTier.WORKSHOP) ? 5 : 3;

          int col = tableIndex % tableWidth;
          int recipeWidth = this.recipe.getWidth();
          int row = tableIndex / tableHeight;
          int recipeHeight = this.recipe.getHeight();

          if (col >= recipeWidth || row >= recipeHeight) {
            return "";
          }

          recipeIndex = tableIndex - row * (tableWidth - recipeWidth);

        } else {

          if (tableIndex >= ingredientList.size()) {
            return "";
          }

          recipeIndex = tableIndex;
        }

        Ingredient ingredient = ingredientList.get(recipeIndex).toIngredient();
        ItemStack[] stacks = ingredient.getMatchingStacks();
        ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];

        return ItemStackUtil.serializeStack(stack);

      } else if (key.startsWith("extra_item_chance")) {
        int index = Integer.parseInt(key.substring(17)) - 1;

        switch (index) {
          case 0:
            return String.valueOf((int) (this.recipe.getSecondaryOutputChance() * 100));
          case 1:
            return String.valueOf((int) (this.recipe.getTertiaryOutputChance() * 100));
          case 2:
            return String.valueOf((int) (this.recipe.getQuaternaryOutputChance() * 100));
          default:
            return ItemStackUtil.serializeStack(ItemStack.EMPTY);
        }

      } else if (key.startsWith("extra_item")) {
        int index = Integer.parseInt(key.substring(10)) - 1;

        switch (index) {
          case 0:
            IArtisanItemStack secondaryOutput = this.recipe.getSecondaryOutput();
            return secondaryOutput.isEmpty() ? "" : ItemStackUtil.serializeStack(secondaryOutput.toItemStack());
          case 1:
            IArtisanItemStack tertiaryOutput = this.recipe.getTertiaryOutput();
            return tertiaryOutput.isEmpty() ? "" : ItemStackUtil.serializeStack(tertiaryOutput.toItemStack());
          case 2:
            IArtisanItemStack quaternaryOutput = this.recipe.getQuaternaryOutput();
            return quaternaryOutput.isEmpty() ? "" : ItemStackUtil.serializeStack(quaternaryOutput.toItemStack());
          default:
            return "";
        }

      } else if ("output".equals(key)) {
        ItemStack[] itemStacks = this.recipe.getOutputWeightPairList().stream()
            .map(OutputWeightPair::getOutput)
            .map(IArtisanItemStack::toItemStack)
            .toArray(ItemStack[]::new);
        Ingredient ingredient = Ingredient.fromStacks(itemStacks);
        return ItemStackUtil.serializeIngredient(ingredient);

      } else if (key.startsWith("tool_damage")) {
        int index = Integer.parseInt(key.substring(11)) - 1;
        return String.valueOf(this.recipe.getToolDamage(index));

      } else if (key.startsWith("tool")) {
        int index = Integer.parseInt(key.substring(4)) - 1;
        ItemStack[] tools = this.recipe.getTools(index);

        if (tools.length > 0) {
          ItemStack[] itemStacks = Arrays.stream(tools)
              .toArray(ItemStack[]::new);
          Ingredient ingredient = Ingredient.fromStacks(itemStacks);
          return ItemStackUtil.serializeIngredient(ingredient);
        }

        return "";

      } else if ("input_fluid".equals(key)) {
        FluidStack fluidIngredient = this.recipe.getFluidIngredient();
        return (fluidIngredient == null) ? "" : fluidIngredient.getFluid().getName();

      } else if ("input_fluid_name".equals(key)) {
        FluidStack fluidIngredient = this.recipe.getFluidIngredient();
        return (fluidIngredient == null) ? "" : fluidIngredient.getLocalizedName();

      } else if ("input_fluid_amount".equals(key)) {
        FluidStack fluidIngredient = this.recipe.getFluidIngredient();
        return (fluidIngredient == null) ? "" : String.valueOf(fluidIngredient.amount);

      } else if ("table_item".equals(key)) {

        EnumType type = EnumType.fromName(this.tableName);
        Item item = this.getTableItem();
        return (item == null) ? "" : ItemStackUtil.serializeStack(new ItemStack(item, 1, type.getMeta()));
      }
    }

    return null;
  }

  protected Item getTableItem() {

    return Item.getItemFromBlock(ModuleWorktables.Blocks.WORKTABLE);
  }

  protected String getTableBackgroundImage(String tableName) {

    return "artisanworktables:textures/gui/worktable_" + tableName + ".png";
  }

  protected EnumTier getTableTier() {

    return EnumTier.WORKTABLE;
  }
}
