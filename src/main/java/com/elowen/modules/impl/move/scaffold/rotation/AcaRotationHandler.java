package com.elowen.modules.impl.move.scaffold.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;

public class AcaRotationHandler implements RotationHandler {
   private static final float C = 12.0F;
   private static final float v = 0.35F;

   @Override
   public Vector2f Z(Vector2f var1, Vector2f var2, float var3, Scaffold var4) {
      HasValue[] var5;
      float var8;
      float var9;
      label52: {
         float var6 = RotationUtils.e(var1.H, var2.H);
         HasValue[] var10000 = NoneRotationHandler.i();
         float var7 = var2.E - var1.E;
         var5 = var10000;
         var8 = var6 * 0.35F;
         var9 = var7 * 0.35F;
         double var10 = Math.sqrt(var8 * var8 + var9 * var9);
         double var15 = var10;
         double var10001 = 12.0;
         if (var5 == null) {
            if (!(var10 > 12.0)) {
               break label52;
            }

            var15 = 12.0;
            var10001 = var10;
         }

         double var12 = var15 / var10001;
         var8 = (float)(var8 * var12);
         var9 = (float)(var9 * var12);
      }

      float var13;
      float var14;
      label46: {
         float var17;
         label55: {
            var14 = var1.H + var8;
            var13 = var1.E + var9;
            float var19;
            int var16 = (var19 = Math.abs(RotationUtils.e(var14, var2.H)) - 0.5F) == 0.0F ? 0 : (var19 < 0.0F ? -1 : 1);
            if (var5 == null) {
               if (var16 < 0) {
                  var14 = var2.H;
               }

               var17 = Math.abs(var13 - var2.E);
               if (var5 != null) {
                  break label55;
               }

               float var20;
               var16 = (var20 = var17 - 0.5F) == 0.0F ? 0 : (var20 < 0.0F ? -1 : 1);
            }

            if (var16 >= 0) {
               break label46;
            }

            var17 = var2.E;
         }

         var13 = var17;
      }

      Vector2f var18 = new Vector2f(var14, var13);
      if (var5 != null) {
         HasValue.d(HasValue.x());
      }

      return var18;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
