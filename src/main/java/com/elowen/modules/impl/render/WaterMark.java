package com.elowen.modules.impl.render;

import com.elowen.Version;
import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.renderer.SkijaEffects;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.SkijaFonts;
import com.elowen.utils.renderer.SkijaColoredText;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

@ModuleInfo(R = "WaterMark", a = "Displays the watermark", M = Category.RENDER)
public class WaterMark extends Module {
   private static final SimpleDateFormat c;
   private static final Minecraft M;
   public FloatValue Z;
   public com.elowen.values.impl.StringValue U;
   public com.elowen.values.impl.StringValue S;
   private float R;
   private float q;
   private String x;
   private float X;
   private float D;
   private float y;
   private final SkijaRenderer T;
   private static final String[] b = new String[]{"USERNAME", "User Name", "Client Name", "[WaterMark] Render failed: ", " | ", "Elowen", "UnknownUser", "Elowen", " | ", "Watermark Size", "USER", "user.name", "HH:mm:ss", " FPS | "};
   public WaterMark() {
      String[] var1 = b;
      this.Z = ValueBuilder.m(this, "Watermark Size").d(0.4F).V(0.01F).w(0.1F).M(1.0F).f$K().L();
      this.U = ValueBuilder.m(this, "Client Name").J("Elowen").f$K().N();
      this.S = ValueBuilder.m(this, "User Name").J(d$String()).f$K().N();
      this.X = -1.0F;
      this.T = com.elowen.utils.renderer.SkiaRenderManager.X$m();
   }

   private static String a(String var0, String var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var0 != null && !var0.trim().isEmpty() ? var0 : var1;
   }

   private static String d$String() {
      String var7 = null;
      HasValue[] var10000 = Theme.s$ArrQ();
      String var1 = null;
      HasValue[] var0 = var10000;

      try {
         label82: {
            label76: {
               String[] var3 = b;
               var1 = System.getProperty("user.name");
               String var6 = var1;
               if (var0 != null) {
                  if (var1 == null) {
                     break label76;
                  }

                  var6 = var1.trim();
               }

               if (!var6.isEmpty()) {
                  break label82;
               }
            }

            Map var2 = System.getenv();
            String[] var5 = b;
            var1 = (String)var2.get("USERNAME");
            var7 = var1;
            label68:
            if (var0 != null) {
               if (var1 != null) {
                  var7 = var1.trim();
                  if (var0 == null) {
                     break label68;
                  }

                  if (!var7.isEmpty()) {
                     break label82;
                  }
               }

               var7 = (String)var2.get("USER");
            }

            var1 = var7;
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      String var8 = var1;
      if (var0 != null) {
         if (var1 == null) {
            return "UnknownUser";
         }

         var8 = var1.trim();
      }

      if (var0 == null) {
         return var8;
      } else {
         return !var8.isEmpty() ? var1 : "UnknownUser";
      }
   }

   @com.elowen.events.api.EventTarget
   public void n(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (M.level != null && M.player != null) {
         if (this.w()) {
            try {
               Canvas var3 = this.T.G$Canvas();
               if (var3 == null) {
                  return;
               }

               this.v(var3);
               this.T.p();
               this.T.G$V();
            } catch (RuntimeException var4) {
               System.err.println("[WaterMark] Render failed: " + var4);
            }
         }
      }
   }

   private void v(Canvas var1) {
      HasValue[] var2 = Theme.s$ArrQ();

      try {
         String var10000 = this.U.R$String();
         String[] var23 = b;
         String var3 = a(var10000, "Elowen");
         String var4 = a(this.S.R$String(), d$String());
         String var5 = String.valueOf(M.getFps());
         String var10001 = Version.Y();
         String var19 = c.format(new Date());
         String var20 = var5;
         String var21 = var4;
         String var22 = var10001;
         String var6 = var3 + " | " + var22 + " | " + var21 + " | " + var20 + " FPS | " + var19;
         double var7 = this.Z.o$F();
         float var9 = (float)(var7 * 23.498F);
         Typeface var10 = com.elowen.utils.renderer.Fonts.i(var9).getTypeface();
         if (var9 == this.X && var6.equals(this.x)) {
            float var11 = this.D;
            float var12 = this.y;
         }

         float var25 = this.v(var6, var9);
         float var26 = this.x(var10, var9);
         this.x = var6;
         this.X = var9;
         this.D = var25;
         this.y = var26;
         this.R = var25 + 14.0F;
         this.q = var26;
         float var13 = 5.0F;
         float var14 = 5.0F;
         float var15 = this.R;
         float var16 = this.q + 8.0F;
         float var17 = 5.0F;
         com.elowen.utils.renderer.SkijaEffects.T(this.T, var1, var13, var14, var15, var16, var17, List.of(new float[]{var13, var14, var15, var16}));
         var1.save();
         var1.clipRRect(RRect.makeLTRB(var13, var14, var13 + var15, var14 + var16, var17), true);
         Theme var18 = Theme.K();
         if (var18 != null) {
            var18.A(var13, var14, var15, 3.0F);
         }

         this.T.d(var13, var14, var15, 3.0F, 0.0F, -16338703);
         this.T.d(var13, var14 + 3.0F, var15, var16 - 3.0F, 0.0F, Theme.f);
         this.T.Y(var6, 12.0F, 10.0F, var10, var9, -1);
         var1.restore();
      } catch (Exception var24) {
      }
   }

   private float v(String var1, float var2) {
      return SkijaColoredText.V(var1, com.elowen.utils.renderer.Fonts.i(var2).getTypeface(), var2);
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private float x(Typeface var1, float var2) {
      HasValue[] var10000 = Theme.s$ArrQ();
      Font var4 = SkijaFonts.V(var1, var2);
      HasValue[] var3 = var10000;

      float var6;
      try {
         FontMetrics var5 = var4.getMetrics();
         var6 = var5.getDescent() - var5.getAscent();
      } catch (Throwable var9) {
         Font var10 = var4;
         if (var3 != null) {
            if (var4 == null) {
               throw var9;
            }

            try {
               var10 = var4;
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
               throw var9;
            }
         }

         try {
            var10.close();
         } catch (Throwable var7) {
            var9.addSuppressed(var7);
         }

         throw var9;
      }

      Font var11 = var4;
      if (var3 != null) {
         if (var4 == null) {
            return var6;
         }

         var11 = var4;
      }

      var11.close();
      return var6;
   }

   static {
      c = new SimpleDateFormat("HH:mm:ss");
      M = Minecraft.getInstance();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
