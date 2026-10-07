package com.elowen.modules.impl.render.projectiles.datas;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.HasValue;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import net.minecraft.world.entity.projectile.arrow.Arrow;

public class EntityArrowData extends BasicProjectileData {
   public EntityArrowData() {
      BasicProjectileData.j$String();
      super(new HashSet<>(Collections.singletonList(Arrow.class)), new Color(255, 0, 0));
      if (!HasValue.x()) {
         BasicProjectileData.b("Nbw3Z");
      }
   }

   @Override
   public float h$F() {
      return 0.25F;
   }

   @Override
   public float P() {
      return 0.5F;
   }

   @Override
   public float y$F() {
      return 0.05F;
   }

   private static NoSuchModuleException b(NoSuchModuleException var0) {
      return var0;
   }
}
