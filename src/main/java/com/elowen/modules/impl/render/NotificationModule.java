package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.ui.notification.NotificationManager;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.HasValue;
import io.github.humbleui.skija.Canvas;

@ModuleInfo(R = "Notification", a = "Displays notifications", M = Category.RENDER)
public class NotificationModule extends Module {
   private final SkijaRenderer v = SkiaRenderManager.X$m();
   private PostProcess J;
   private static final String b;

   private PostProcess D$h() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (this.J == null) {
         try {
            this.J = (PostProcess)Elowen.S$Elowen().q$ModuleManager().A(PostProcess.class);
         } catch (Exception var3) {
         }
      }

      return this.J;
   }

   @com.elowen.events.api.EventTarget
   public void l(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (this.w()) {
         NotificationManager var3 = Elowen.S$Elowen().Q();

         try {
            Canvas var4 = this.v.G$Canvas();
            if (var4 == null) {
               return;
            }

            PostProcess var5 = this.D$h();
            boolean var6 = var5 != null && var5.w() && var5.U.w();
            if (var6) {
               var3.J(this.v, var4);
            }

            var3.Y(this.v, var4);
            this.v.p();
            this.v.G$V();
         } catch (RuntimeException var7) {
            System.err.println(b + var7);
         }
      }
   }

   @Override
   public void q$V() {
      super.q$V();
      this.J = null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b = "[Notification] Render failed: ";
   }
}
