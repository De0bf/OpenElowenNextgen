package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import java.awt.Color;

@ModuleInfo(R = "Theme", a = "Shared colors for the interface", M = Category.RENDER)
public class Theme extends Module {
   private static final long q = 1000L;
   public static final int f;
   public static final int D;
   private long T = 0L;
   private int x;
   private boolean C;
   public BooleanValue B;
   public BooleanValue K;
   public FloatValue Q;
   public FloatValue i;
   public FloatValue d;
   public FloatValue b;
   public FloatValue h;
   public FloatValue j;
   private final SkijaRenderer E;
   private static HasValue[] v;
   private static final String[] c = new String[]{"Header Red", "Header Alpha", "Gui Rainbow", "Custom Color", "Rainbow Speed", "Header Blue", "Rainbow Offset", "Header Green"};
   public Theme() {
      HasValue[] var10000 = s$ArrQ();
      this.x = new Color(6, 176, 241, 255).getRGB();
      this.C = true;
      String[] var2 = c;
      this.B = ValueBuilder.m(this, "Custom Color").h(true).f$K().f$O();
      HasValue[] var1 = var10000;
      this.K = ValueBuilder.m(this, "Gui Rainbow").l(this.B::w).h(false).f$K().f$O();
      this.Q = ValueBuilder.m(this, "Header Red").l(this::deobfLambda$new$0).d(6.0F).w(0.0F).M(255.0F).V(1.0F).z(v -> this.deobfLambda$new$1((Value)v)).f$K().L();
      this.i = ValueBuilder.m(this, "Header Green").l(this::deobfLambda$new$2).d(176.0F).w(0.0F).M(255.0F).V(1.0F).z(v -> this.deobfLambda$new$3((Value)v)).f$K().L();
      this.d = ValueBuilder.m(this, "Header Blue").l(this::deobfLambda$new$4).d(241.0F).w(0.0F).M(255.0F).V(1.0F).z(v -> this.deobfLambda$new$5((Value)v)).f$K().L();
      this.b = ValueBuilder.m(this, "Header Alpha").l(this::deobfLambda$new$6).d(255.0F).w(0.0F).M(255.0F).V(1.0F).z(v -> this.deobfLambda$new$7((Value)v)).f$K().L();
      this.h = ValueBuilder.m(this, "Rainbow Speed").l(this::q$Z).w(1.0F).M(20.0F).d(10.0F).V(0.1F).f$K().L();
      this.j = ValueBuilder.m(this, "Rainbow Offset").l(this::q$Z).w(1.0F).M(20.0F).d(10.0F).V(0.1F).f$K().L();
      this.E = SkiaRenderManager.X$m();
      if (var1 == null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.x());
      }
   }

   public static Theme K() {
      try {
         return (Theme)Elowen.S$Elowen().q$ModuleManager().A(Theme.class);
      } catch (RuntimeException var1) {
         return null;
      }
   }

   private boolean q$Z() {
      HasValue[] var1;
      label40: {
         HasValue[] var10000 = s$ArrQ();
         ArrayListModule var2 = ArrayListModule.r();
         var1 = var10000;
         ArrayListModule var3 = var2;
         if (var1 != null) {
            if (var2 == null) {
               break label40;
            }

            var3 = var2;
         }

         boolean var4 = var3.w();
         if (var1 == null) {
            return var4;
         }

         if (var4) {
            return true;
         }
      }

      boolean var5 = this.K.w();
      return var1 == null ? var5 : var5;
   }

   @Override
   public void M(boolean var1) {
   }

   @Override
   public void R$V() {
   }

   private int j$I() {
      HasValue[] var1 = s$ArrQ();
      if (!this.B.w()) {
         return new Color(6, 176, 241, 255).getRGB();
      }

      if (this.K.w()) {
         return com.elowen.utils.renderer.RenderUtils.A((int)(System.currentTimeMillis() / 10L), 1.0F, 1.0F, (21.0F - this.h.o$F()) * 1000.0F);
      }

      long var2 = System.currentTimeMillis();
      if (this.C || var2 - this.T >= 1000L) {
         int var4 = Math.max(0, Math.min(255, Math.round(this.Q.o$F())));
         int var5 = Math.max(0, Math.min(255, Math.round(this.i.o$F())));
         int var6 = Math.max(0, Math.min(255, Math.round(this.d.o$F())));
         int var7 = Math.max(0, Math.min(255, Math.round(this.b.o$F())));
         this.x = new Color(var4, var5, var6, var7).getRGB();
         this.C = false;
         this.T = var2;
      }

      return this.x;
   }

   public int g$I() {
      return this.j$I();
   }

   public void A(float var1, float var2, float var3, float var4) {
      HasValue[] var5 = s$ArrQ();
      if (this.B.w() && this.K.w()) {
         long var6 = System.currentTimeMillis();
         float var8 = this.j.o$F();
         float var9 = (21.0F - this.h.o$F()) * 1000.0F;
         int var10 = 0;
         while (var10 < var3) {
            float var11 = var10 / var3 * var8 * 5.0F;
            int var12 = com.elowen.utils.renderer.RenderUtils.u(var6, (int)(-var11 * var8), 1.0F, 1.0F, var9);
            this.E.d(var1 + var10, var2, 1.0F, var4, 0.0F, var12);
            var10++;
         }
      } else {
         this.E.d(var1, var2, var3, var4, 0.0F, this.j$I());
      }
   }

   private void deobfLambda$new$7(Value var1) {
      this.C = true;
   }

   private Boolean deobfLambda$new$6() {
      HasValue[] var1 = s$ArrQ();
      return this.B.w() && !this.K.w();
   }

   private void deobfLambda$new$5(Value var1) {
      this.C = true;
   }

   private Boolean deobfLambda$new$4() {
      HasValue[] var1 = s$ArrQ();
      return this.B.w() && !this.K.w();
   }

   private void deobfLambda$new$3(Value var1) {
      this.C = true;
   }

   private Boolean deobfLambda$new$2() {
      HasValue[] var1 = s$ArrQ();
      return this.B.w() && !this.K.w();
   }

   private void deobfLambda$new$1(Value var1) {
      this.C = true;
   }

   private Boolean deobfLambda$new$0() {
      HasValue[] var1 = s$ArrQ();
      return this.B.w() && !this.K.w();
   }

   static {
      HasValue[] var10000 = new HasValue[3];
      U(var10000);
      f = new Color(0, 0, 0, 120).getRGB();
      D = new Color(0, 0, 0, 40).getRGB();
   }

   public static void U(HasValue[] var0) {
      v = var0;
   }

   public static HasValue[] s$ArrQ() {
      return v;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
