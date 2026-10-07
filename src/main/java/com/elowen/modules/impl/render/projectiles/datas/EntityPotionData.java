package com.elowen.modules.impl.render.projectiles.datas;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.HasValue;
import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;

public class EntityPotionData extends BasicProjectileData {
   public EntityPotionData() {
      String var10000 = BasicProjectileData.j$String();
      super(new HashSet<>(Collections.singleton(AbstractThrownPotion.class)), new Color(255, 66, 249));
      String var1 = var10000;
      if (var1 != null) {
         HasValue.d(HasValue.x());
      }
   }

   @Override
   public float y$F() {
      return 0.05F;
   }

   private static NoSuchModuleException b(NoSuchModuleException var0) {
      return var0;
   }
}
