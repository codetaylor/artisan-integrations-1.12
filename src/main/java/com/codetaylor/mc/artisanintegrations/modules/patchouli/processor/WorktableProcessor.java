package com.codetaylor.mc.artisanintegrations.modules.patchouli.processor;

import com.codetaylor.mc.artisanworktables.ModArtisanWorktables;
import com.codetaylor.mc.artisanworktables.api.ArtisanAPI;
import com.codetaylor.mc.artisanworktables.api.ArtisanToolHandlers;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.IArtisanItemStack;
import com.codetaylor.mc.artisanworktables.api.internal.recipe.OutputWeightPair;
import com.codetaylor.mc.artisanworktables.api.internal.reference.EnumType;
import com.codetaylor.mc.artisanworktables.api.recipe.IArtisanRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.common.util.ItemStackUtil;

import java.util.Arrays;

public class WorktableProcessor
    implements IComponentProcessor {

  private String tableName;
  private IArtisanRecipe recipe;

  @Override
  public void setup(IVariableProvider<String> variables) {

    String recipe = variables.get("recipe");
    this.tableName = recipe.split(":")[0];
    this.recipe = ArtisanAPI.getRecipe(recipe);
  }

  @Override
  public String process(String key) {

    if (this.recipe != null) {

      if ("table_image".equals(key)) {
        return "artisanworktables:textures/gui/worktable_" + this.tableName + ".png";

      } else if (key.startsWith("item")) {
        int index = Integer.parseInt(key.substring(4)) - 1;
        Ingredient ingredient = this.recipe.getIngredientList().get(index).toIngredient();
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
            return ItemStackUtil.serializeStack(this.recipe.getSecondaryOutput().toItemStack());
          case 1:
            return ItemStackUtil.serializeStack(this.recipe.getTertiaryOutput().toItemStack());
          case 2:
            return ItemStackUtil.serializeStack(this.recipe.getQuaternaryOutput().toItemStack());
          default:
            return ItemStackUtil.serializeStack(ItemStack.EMPTY);
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
        IArtisanItemStack[] tools = this.recipe.getTools(index);
        ItemStack[] itemStacks = Arrays.stream(tools)
            .map(IArtisanItemStack::toItemStack)
            .toArray(ItemStack[]::new);

        if (itemStacks.length > 0) {
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
        ResourceLocation resourceLocation;
        resourceLocation = new ResourceLocation(ModArtisanWorktables.MOD_ID, "worktable");
        Item item = ForgeRegistries.ITEMS.getValue(resourceLocation);
        return (item == null) ? "" : ItemStackUtil.serializeStack(new ItemStack(item, 1, type.getMeta()));
      }
    }

    return null;
  }
}
