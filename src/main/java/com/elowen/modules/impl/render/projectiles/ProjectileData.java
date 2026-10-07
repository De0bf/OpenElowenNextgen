package com.elowen.modules.impl.render.projectiles;

import java.awt.Color;
import net.minecraft.world.entity.Entity;

public interface ProjectileData {
   Color P(Object var1);

   default float h$F() {
      return 0.125F;
   }

   boolean R(Entity var1);

   default float P() {
      return 0.25F;
   }

   default float y$F() {
      return 0.03F;
   }
}
