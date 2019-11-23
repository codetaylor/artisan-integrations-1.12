package com.codetaylor.mc.artisanintegrations.modules.patchouli.processor;

import com.codetaylor.mc.artisanworktables.modules.worktables.ModuleWorktables;
import net.minecraft.item.Item;

public class WorkshopProcessor
    extends WorkstationProcessor {

  @Override
  protected Item getTableItem() {

    return Item.getItemFromBlock(ModuleWorktables.Blocks.WORKSHOP);
  }

  @Override
  protected String getTableBackgroundImage(String tableName) {

    return "artisanworktables:textures/gui/workshop_" + tableName + ".png";
  }

}
