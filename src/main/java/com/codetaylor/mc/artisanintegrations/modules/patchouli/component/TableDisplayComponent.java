package com.codetaylor.mc.artisanintegrations.modules.patchouli.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.VariableHolder;
import vazkii.patchouli.common.util.ItemStackUtil;

public class TableDisplayComponent
    implements ICustomComponent {

  @VariableHolder
  public String item;
  public float scale;
  public int x, y;

  transient ItemStack stack;

  @Override
  public void build(int componentX, int componentY, int pageNum) {

    this.x = (int) (componentX / this.scale);
    this.y = (int) (componentY / this.scale);

    this.stack = ItemStackUtil.loadStackFromString(this.item);
  }

  @Override
  public void render(IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {

    GlStateManager.pushMatrix();
    GlStateManager.scale(this.scale, this.scale, this.scale);

    if (this.stack == null || this.stack.isEmpty()) {
      return;
    }
    RenderHelper.enableGUIStandardItemLighting();
    Minecraft minecraft = Minecraft.getMinecraft();
    RenderItem renderItem = minecraft.getRenderItem();
    renderItem.renderItemAndEffectIntoGUI(this.stack, this.x, this.y);
    renderItem.renderItemOverlays(minecraft.fontRenderer, this.stack, this.x, this.y);
    GlStateManager.popMatrix();
  }
}
