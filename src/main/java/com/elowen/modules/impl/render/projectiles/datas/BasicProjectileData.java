package com.elowen.modules.impl.render.projectiles.datas;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.render.projectiles.ProjectileData;
import java.awt.Color;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.world.entity.Entity;

public class BasicProjectileData implements ProjectileData {
   private final Color V;
   private final Set N;
   private static String A;

   public BasicProjectileData(Set var1) {
      this(var1, new Color(255, 255, 255));
   }

   public BasicProjectileData(Set var1, Color var2) {
      this.N = var1;
      this.V = var2;
   }

   @Override
   public Color P(Object var1) {
      return this.V;
   }

   @Override
   public boolean R(Entity var1) {
      boolean var5 = false;
      String var10000 = j$String();
      Iterator var3 = this.N.iterator();
      String var2 = var10000;

      while (true) {
         if (var3.hasNext()) {
            Class var4 = (Class)var3.next();
            var5 = var4.isInstance(var1);
            if (var2 != null) {
               break;
            }

            if (var2 != null) {
               return var5;
            }

            if (var5) {
               return true;
            }

            if (var2 == null) {
               continue;
            }
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public static void b(String var0) {
      A = var0;
   }

   public static String j$String() {
      return A;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (j$String() != null) {
         b("FfeVL");
      }
   }
}
