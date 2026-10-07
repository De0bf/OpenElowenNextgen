package com.elowen.utils.renderer;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.render.PostProcess;
import com.elowen.utils.renderer.shader.SkijaBlur;
import com.elowen.utils.renderer.shader.SkijaShadow;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.Iterator;
import java.util.List;

public class SkijaEffects {
   public static void T(SkijaRenderer var0, Canvas var1, float var2, float var3, float var4, float var5, float var6, List var7) {
      boolean var10000 = SkijaRenderer.x();
      ModuleManager var9 = Elowen.S$Elowen().q$ModuleManager();
      boolean var8 = var10000;
      ModuleManager var12 = var9;
      if (!var8) {
         if (var9 == null) {
            return;
         }

         var12 = var9;
      }

      PostProcess var10 = (PostProcess)var12.A(PostProcess.class);
      PostProcess var13 = var10;
      if (!var8) {
         if (var10 == null) {
            return;
         }

         var13 = var10;
      }

      var10000 = var13.w();
      if (!var8) {
         if (!var10000) {
            return;
         }

         var10000 = var10.r.w();
      }

      if (!var8) {
         if (var10000) {
            SkijaBlur.s(var0, var1, var2, var3, var4, var5, var10.S.o$F(), var6, var7);
         }

         var10000 = var10.U.w();
      }

      if (var10000) {
         Path var11 = m(var7, var6, var2, var3, var4, var5);
         SkijaShadow.w(var1, var11, 4.0F, Integer.MIN_VALUE, 0.0F, 0.0F);
      }
   }

   private static Path m(List var0, float var1, float var2, float var3, float var4, float var5) {
      boolean var6 = SkijaRenderer.L();
      if (var0 != null && !var0.isEmpty()) {
         if (var0.size() == 1) {
            float[] var10 = (float[])var0.get(0);
            return Path.makeRRect(RRect.makeLTRB(var10[0], var10[1], var10[0] + var10[2], var10[1] + var10[3], var1, var1));
         }

         PathBuilder var7 = new PathBuilder();
         Iterator var8 = var0.iterator();
         while (var8.hasNext()) {
            float[] var9 = (float[])var8.next();
            var7.addRect(Rect.makeXYWH(var9[0], var9[1], var9[2], var9[3]));
         }

         return var7.detach();
      } else {
         return Path.makeRRect(RRect.makeLTRB(var2, var3, var2 + var4, var3 + var5, var1, var1));
      }
   }

   public static void G(SkijaRenderer var0) {
      boolean var10000 = SkijaRenderer.L();
      Canvas var2 = var0.E$Canvas();
      boolean var1 = var10000;
      if (var1) {
         if (var2 == null) {
            return;
         }

         T(var0, var2, 0.0F, 0.0F, var0.G$I(), var0.q$I(), 0.0F, null);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
