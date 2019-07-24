package com.codetaylor.mc.artisanintegrations.modules.botania;

import net.minecraftforge.common.config.Config;

@Config(modid = ModuleBotania.MOD_ID, name = ModuleBotania.MOD_ID + "/" + "module.Botania")
public class ModuleBotaniaConfig {

  @Config.Comment({
      "The amount of mana to charge per damage when repairing mana tools and crafting.",
      "Default: " + 60
  })
  public static int MANA_PER_TOOL_DAMAGE = 60;
}
