package com.codetaylor.mc.artisanintegrations.modules.gregtech.tool;

import com.codetaylor.mc.artisanworktables.api.recipe.IToolHandler;
import gregtech.api.items.IToolItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ToolHandler
    implements IToolHandler {

  @Override
  public boolean matches(ItemStack itemStack) {

    return itemStack.getItem() instanceof IToolItem;
  }

  @Override
  public boolean matches(ItemStack tool, ItemStack toolToMatch) {

    return (tool.getItem() == toolToMatch.getItem()
        && tool.getMetadata() == toolToMatch.getMetadata());
  }

  @Override
  public boolean canAcceptAllDamage(ItemStack itemStack, int damage) {

    return this.damageItem(itemStack, damage, true);
  }

  @Override
  public boolean applyDamage(World world, ItemStack itemStack, int damage, EntityPlayer player, boolean simulate) {

    // The server tag object for the stack is ending up on the client in
    // a single player setup. The same instance - so any changes made on the
    // client's tag were also made on the server's tag because it's the same
    // object.
    //
    // TODO: I don't know what is causing this to happen.
    //  - Is this normal behavior?
    //  - Is something that AW does causing this?
    //
    // Duplicating the tag on the client before it's modified seems to do
    // the trick.
    if (!simulate
        && world.isRemote
        && itemStack.getTagCompound() != null) {
      itemStack.setTagCompound(itemStack.getTagCompound().copy());
    }

    return !this.damageItem(itemStack, damage, simulate);
  }

  private boolean damageItem(ItemStack itemStack, int damage, boolean simulate) {

    Item item = itemStack.getItem();

    if (item instanceof IToolItem) {
      return ((IToolItem) item).damageItem(itemStack, damage, simulate);
    }

    return false;
  }

}
