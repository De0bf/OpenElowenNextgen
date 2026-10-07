package com.elowen.modules.impl.move.scaffold.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.MathUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.MathHelper;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;

public class HeypixelRotationHandler implements RotationHandler {
   private static final int M = 3;
   private static final float L = 80.0F;
   private static final float u = 50.0F;
   private static final int A = 2;
   private int N = 0;
   private int j = 0;
   private int R = 0;
   private boolean x = false;

   @Override
   public Vector2f Z(Vector2f var1, Vector2f var2, float var3, Scaffold var4) {
      HasValue[] var5 = NoneRotationHandler.i();
      if (var4.D$M().player == null) {
         return var2;
      }

      boolean var6 = var4.D$M().player.onGround();
      if (var6) {
         this.N++;
         this.j = 0;
      }

      this.j++;
      this.N = 0;
      if (this.R > 0) {
         this.R--;
         return var1;
      }

      if (!var6) {
         this.x = false;
         if (this.j < 3) {
            float var10 = this.j == 1 ? 80.0F : 50.0F;
            var10 -= (float)MathUtils.K(0.001, 0.005);
            float var12 = RotationUtils.q(var1.H, var2.H, var10);
            return new Vector2f(MathHelper.N(var12), var2.E);
         } else {
            return var2;
         }
      } else {
         float var7 = RotationUtils.i(var3, var1.H, var2.H);
         float var8 = RotationUtils.q(var1.E, 90.0F, var3);
         boolean var9 = Math.abs(RotationUtils.e(var7, var2.H)) < 1.0F && Math.abs(var8 - 90.0F) < 1.0F;
         if (var9 && !this.x) {
            this.R = 2;
         }

         this.x = var9;
         return new Vector2f(MathHelper.N(var7), var8);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
