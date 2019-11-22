package com.codetaylor.mc.artisanintegrations.modules.patchouli.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.VariableHolder;

import java.awt.*;

public class TextComponent
    implements ICustomComponent {

  @VariableHolder
  public String text;
  public int x, y;

  @Override
  public void build(int componentX, int componentY, int pageNum) {

    this.x = componentX;
    this.y = componentY;
  }

  @Override
  public void render(IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {

    if (this.text == null || this.text.isEmpty()) {
      return;
    }

    Minecraft minecraft = Minecraft.getMinecraft();
    GlStateManager.pushMatrix();
    GlStateManager.translate(0, 0, 1000);
    GlStateManager.scale(0.5f, 0.5f, 0.5f);
    minecraft.fontRenderer.drawStringWithShadow(this.text, this.x * 2, this.y * 2, Color.WHITE.getRGB());
    GlStateManager.popMatrix();
  }
}
