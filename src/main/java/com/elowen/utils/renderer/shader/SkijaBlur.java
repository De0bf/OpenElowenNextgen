package com.elowen.utils.renderer.shader;

import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.GpuScreenCapture;
import com.elowen.values.HasValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorFilter;
import io.github.humbleui.skija.ColorMatrix;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.Iterator;
import java.util.List;

public class SkijaBlur {
   private static final ColorFilter Q = ColorFilter.makeMatrix(
      new ColorMatrix(new float[]{1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F})
   );

   public static void s(SkijaRenderer var0, Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, List var8) {
      HasValue[] var9 = SkijaShadow.x();
      if (!(var6 <= 0.0F) && !(var4 <= 0.0F) && !(var5 <= 0.0F)) {
         DirectContext var10 = var0.a$n().L();
         Image var11 = GpuScreenCapture.N(var10);
         if (var11 != null) {
            int var12 = var11.getWidth();
            int var13 = var11.getHeight();
            if (var12 > 0 && var13 > 0) {
               Surface var14 = var0.a$n().U$Surface();
               if (var14 != null) {
                  int var15 = var14.getWidth();
                  int var16 = var14.getHeight();
                  if (var15 > 0 && var16 > 0) {
                     float var17 = (float)var15 / var0.G$I();
                     float var18 = (float)var16 / var0.q$I();
                     float var19 = (float)var15 / var12;
                     float var20 = var2 * var17;
                     float var21 = var3 * var18;
                     float var22 = var4 * var17;
                     float var23 = var5 * var18;
                     if (!(var22 <= 0.0F) && !(var23 <= 0.0F)) {
                        float var24 = var6 * var17;
                        if (!(var24 <= 0.0F)) {
                           float var25 = var24 * 3.0F;
                           float var26 = Math.max(0.0F, var20 - var25);
                           float var27 = Math.max(0.0F, var21 - var25);
                           float var28 = Math.min(var15, var20 + var22 + var25);
                           float var29 = Math.min(var16, var21 + var23 + var25);
                           if (!(var28 <= var26) && !(var29 <= var27)) {
                              Rect var30 = Rect.makeXYWH(var26 / var19, var27 / var19, (var28 - var26) / var19, (var29 - var27) / var19);
                              Rect var31 = Rect.makeXYWH(var26, var27, var28 - var26, var29 - var27);
                              var1.save();
                              var1.resetMatrix();
                              Path var32 = g(var8, var7, var17, var18, var20, var21, var22, var23);
                              var1.clipPath(var32, true);
                              Paint var33 = new Paint();

                              try {
                                 var33.setAntiAlias(true);
                                 var33.setImageFilter(ImageFilter.makeBlur(var24, var24, FilterTileMode.CLAMP));
                                 var33.setColorFilter(Q);
                                 var1.drawImageRect(var11, var30, var31, SamplingMode.LINEAR, var33, false);
                              } catch (Throwable var37) {
                                 try {
                                    var33.close();
                                 } catch (Throwable var36) {
                                    var37.addSuppressed(var36);
                                 }

                                 throw var37;
                              }

                              var33.close();
                              var1.restore();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static Path g(List var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float[] var24 = null;
      PathBuilder var25 = null;
      HasValue[] var10000 = SkijaShadow.x();
      float var9 = var1 * var2;
      HasValue[] var8 = var10000;
      List var22 = var0;
      if (var8 == null) {
         if (var0 == null) {
            return Path.makeRRect(RRect.makeLTRB(var4, var5, var4 + var6, var5 + var7, var9, var9));
         }

         var22 = var0;
      }

      label56: {
         int var23 = ((var22.isEmpty()) ? 1 : 0);
         if (var8 == null) {
            if (var23 != 0) {
               return Path.makeRRect(RRect.makeLTRB(var4, var5, var4 + var6, var5 + var7, var9, var9));
            }

            var22 = var0;
            if (var8 != null) {
               break label56;
            }

            var23 = var0.size();
         }

         if (var23 != 1) {
            PathBuilder var10 = new PathBuilder();
            Iterator var11 = var0.iterator();

            while (true) {
               if (var11.hasNext()) {
                  float[] var12 = (float[])var11.next();
                  float var13 = var12[0] * var2;
                  float var14 = var12[1] * var3;
                  float var15 = var12[2] * var2;
                  float var16 = var12[3] * var3;
                  var25 = var10.addRect(Rect.makeXYWH(var13, var14, var15, var16));
                  if (var8 != null) {
                     break;
                  }

                  if (var8 == null) {
                     continue;
                  }
               }

               var25 = var10;
               break;
            }

            return var25.detach();
         }

         var24 = (float[])var0.get(0);
      }

      float[] var17 = (float[])var24;
      float var18 = var17[0] * var2;
      float var19 = var17[1] * var3;
      float var20 = var17[2] * var2;
      float var21 = var17[3] * var3;
      return Path.makeRRect(RRect.makeLTRB(var18, var19, var18 + var20, var19 + var21, var9, var9));
   }

   public static void E(SkijaRenderer var0, float var1) {
      HasValue[] var10000 = SkijaShadow.x();
      Canvas var3 = var0.E$Canvas();
      HasValue[] var2 = var10000;
      if (var2 == null) {
         if (var3 == null) {
            return;
         }

         s(var0, var3, 0.0F, 0.0F, var0.G$I(), var0.q$I(), var1, 0.0F, null);
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
