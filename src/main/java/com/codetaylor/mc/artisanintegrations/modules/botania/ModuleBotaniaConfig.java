package com.codetaylor.mc.artisanintegrations.modules.botania;

import net.minecraftforge.common.config.Config;

@Config(modid = ModuleBotania.MOD_ID, name = ModuleBotania.MOD_ID + "/" + "module.Botania")
public class ModuleBotaniaConfig {

  @Config.Comment({
      "The amount of mana to charge per damage.",
      "Default: " + 60
  })
  public static int MANA_PER_DAMAGE = 60;
}
