package com.elowen.ui.notification;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.renderer.SkijaRenderer;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;

public class NotificationManager {
   private final List f = new CopyOnWriteArrayList();
   private static int[] b;

   public void m(Notification var1) {
      int[] var2 = r();
      if (!this.f.contains(var1)) {
         this.f.add(var1);
      }
   }

   public void Y(SkijaRenderer var1, Canvas var2) {
      int[] var3 = r();
      if (var1 != null && var2 != null) {
         float var4 = 5.0F;
         Iterator var5 = this.f.iterator();
         while (var5.hasNext()) {
            Notification var6;
            SmoothAnimationTimer var8;
            SmoothAnimationTimer var9;
            label30: {
               var6 = (Notification)var5.next();
               float var7 = var6.G$F();
               var4 += var6.u$F();
               var8 = var6.n$M();
               var9 = var6.B$M();
               float var10 = (float)(System.currentTimeMillis() - var6.w());
               if (var10 > (float)var6.P()) {
                  var8.J = 0.0F;
                  var9.J = 0.0F;
                  if (!var8.P(true)) {
                     break label30;
                  }

                  this.f.remove(var6);
               }

               var8.J = var7;
               var9.J = var4;
            }

            var8.F(true);
            var9.F(true);
            float var11 = Minecraft.getInstance().getWindow().getGuiScaledWidth() - var8.l + 2.0F;
            float var12 = Minecraft.getInstance().getWindow().getGuiScaledHeight() - var9.l;
            var6.J(var1, var2, var11, var12);
         }
      }
   }

   public void J(SkijaRenderer var1, Canvas var2) {
      int[] var3 = r();
      if (var1 != null && var2 != null) {
         Iterator var4 = this.f.iterator();
         while (var4.hasNext()) {
            Notification var5 = (Notification)var4.next();
            SmoothAnimationTimer var6 = var5.n$M();
            SmoothAnimationTimer var7 = var5.B$M();
            float var8 = Minecraft.getInstance().getWindow().getGuiScaledWidth() - var6.l + 2.0F;
            float var9 = Minecraft.getInstance().getWindow().getGuiScaledHeight() - var7.l;
            float var10 = var8 + 2.0F;
            float var11 = var9 + 4.0F;
            float var12 = var5.G$F();
            float var13 = 20.0F;
            float var14 = 8.0F;
            Rect var15 = Rect.makeLTRB(var10 - var14, var11 - var14, var10 + var12 + var14, var11 + var13 + var14);
            var2.saveLayer(var15, null);
            var5.N(var1, var2, var8, var9);
            var2.restore();
         }
      }
   }

   public static void s(int[] var0) {
      b = var0;
   }

   public static int[] r() {
      return b;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (r() != null) {
         s(new int[5]);
      }
   }
}
