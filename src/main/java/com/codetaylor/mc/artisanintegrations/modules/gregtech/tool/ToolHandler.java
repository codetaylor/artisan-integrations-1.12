package com.codetaylor.mc.artisanintegrations.modules.gregtech.tool;

import com.codetaylor.mc.artisanintegrations.modules.gregtech.ModuleGregTech;
import com.codetaylor.mc.artisanworktables.api.recipe.IToolHandler;
import gregtech.api.items.IToolItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ToolHandler
    implements IToolHandler {

  private GTCEMethodHandleWrapper methodHandleWrapper;

  private abstract static class GTCEMethodHandleWrapper {

    protected final MethodHandle methodHandle;

    protected GTCEMethodHandleWrapper(MethodHandle methodHandle) {

      this.methodHandle = methodHandle;
    }

    abstract boolean damageItem(IToolItem item, ItemStack itemStack, int damage, boolean simulate);
  }

  public ToolHandler() {

    Throwable errorGTCE = null;
    Throwable errorGTCEu = null;

    // Support GTCE

    try {
      MethodHandle methodHandle = MethodHandles.publicLookup().findVirtual(
          IToolItem.class,
          "damageItem",
          MethodType.methodType(boolean.class, ItemStack.class, int.class, boolean.class)
      );

      this.methodHandleWrapper = new GTCEMethodHandleWrapper(methodHandle) {

        @Override
        boolean damageItem(IToolItem item, ItemStack itemStack, int damage, boolean simulate) {

          try {
            return (boolean) this.methodHandle.invoke(item, itemStack, damage, simulate);

          } catch (Throwable e) {
            ModuleGregTech.LOGGER.error("Unable to invoke GTCE API method handle: " + this.methodHandle.toString(), e);
          }

          return false;
        }
      };

      ModuleGregTech.LOGGER.info("Bound GTCE method handle for gregtech.api.items.IToolItem$damageItem: " + methodHandle.toString());

    } catch (NoSuchMethodException | IllegalAccessException e) {
      this.methodHandleWrapper = null;
      errorGTCE = e;
    }

    if (this.methodHandleWrapper == null) {

      // Support GTCEu

      try {
        //noinspection JavaLangInvokeHandleSignature
        MethodHandle methodHandle = MethodHandles.publicLookup().findVirtual(
            IToolItem.class,
            "damageItem",
            MethodType.methodType(boolean.class, ItemStack.class, EntityLivingBase.class, int.class, boolean.class)
        );

        this.methodHandleWrapper = new GTCEMethodHandleWrapper(methodHandle) {

          @Override
          boolean damageItem(IToolItem item, ItemStack itemStack, int damage, boolean simulate) {

            try {
              return (boolean) this.methodHandle.invoke(item, itemStack, null, damage, simulate);

            } catch (Throwable e) {
              ModuleGregTech.LOGGER.error("Unable to invoke GTCEu API method handle: " + this.methodHandle.toString(), e);
            }

            return false;
          }
        };

        ModuleGregTech.LOGGER.info("Bound GTCEu method handle for gregtech.api.items.IToolItem$damageItem: " + methodHandle.toString());

      } catch (NoSuchMethodException | IllegalAccessException e) {
        this.methodHandleWrapper = null;
        errorGTCEu = e;
      }
    }

    if (this.methodHandleWrapper == null) {
      ModuleGregTech.LOGGER.error("Unable to bind API method IToolItem$damageItem for GTCE", errorGTCE);
      ModuleGregTech.LOGGER.error("Unable to bind API method IToolItem$damageItem for GTCEu", errorGTCEu);
    }
  }

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
  public boolean applyDamage(World world, ItemStack itemStack, int damage, @Nullable EntityPlayer player, boolean simulate) {

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
      return this.methodHandleWrapper.damageItem((IToolItem) item, itemStack, damage, simulate);
    }

    return false;
  }

}
