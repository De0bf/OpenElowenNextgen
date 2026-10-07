package com.elowen.utils.renderer.shader;

import com.elowen.values.HasValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

public class SkijaShadow {
   private static HasValue[] z;

   public static void m(Canvas var0, RRect var1, float var2, int var3, float var4, float var5) {
      if (!(var2 <= 0.0F) && var0 != null) {
         Rect var6 = Rect.makeLTRB(var1.getLeft(), var1.getTop(), var1.getRight(), var1.getBottom());
         float var7 = 3.0F * var2 + Math.abs(var4) + Math.abs(var5);
         Rect var8 = Rect.makeLTRB(
            var6.getLeft() + Math.min(0.0F, var4) - var7,
            var6.getTop() + Math.min(0.0F, var5) - var7,
            var6.getRight() + Math.max(0.0F, var4) + var7,
            var6.getBottom() + Math.max(0.0F, var5) + var7
         );
         var0.saveLayer(var8, null);
         var0.translate(var4, var5);
         Paint var9 = new Paint();

         try {
            var9.setColor(var3);
            var9.setImageFilter(ImageFilter.makeBlur(var2, var2, FilterTileMode.DECAL));
            var0.drawRRect(var1, var9);
         } catch (Throwable var13) {
            try {
               var9.close();
            } catch (Throwable var12) {
               var13.addSuppressed(var12);
            }

            throw var13;
         }

         var9.close();
         var0.restore();
      }
   }

   public static void F(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      HasValue[] var10000 = x();
      m(var0, RRect.makeLTRB(var1, var2, var1 + var3, var2 + var4, 0.0F), var5, var6, var7, var8);
      HasValue[] var9 = var10000;
      if (var9 != null) {
         HasValue.d(HasValue.x());
      }
   }

   public static void w(Canvas var0, Path var1, float var2, int var3, float var4, float var5) {
      HasValue[] var6 = x();
      if (!(var2 <= 0.0F) && var0 != null && var1 != null) {
         Rect var7 = var1.getBounds();
         float var8 = 3.0F * var2 + Math.abs(var4) + Math.abs(var5);
         Rect var9 = Rect.makeLTRB(
            var7.getLeft() + Math.min(0.0F, var4) - var8,
            var7.getTop() + Math.min(0.0F, var5) - var8,
            var7.getRight() + Math.max(0.0F, var4) + var8,
            var7.getBottom() + Math.max(0.0F, var5) + var8
         );
         var0.saveLayer(var9, null);
         var0.translate(var4, var5);
         Paint var10 = new Paint();

         try {
            var10.setColor(var3);
            var10.setImageFilter(ImageFilter.makeBlur(var2, var2, FilterTileMode.DECAL));
            var0.drawPath(var1, var10);
         } catch (Throwable var14) {
            try {
               var10.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         var10.close();
         var0.restore();
      }
   }

   public static void Y(HasValue[] var0) {
      z = var0;
   }

   public static HasValue[] x() {
      return z;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
      if (x() != null) {
         Y(new HasValue[3]);
      }
   }
}
