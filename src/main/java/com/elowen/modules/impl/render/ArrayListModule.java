package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.ModuleManager;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.SkijaEffects;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.SkijaFonts;
import com.elowen.utils.renderer.SkijaColoredText;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.Typeface;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ModuleInfo(R = "ArrayList", a = "Displays the enabled modules", M = Category.RENDER)
public class ArrayListModule extends Module {
   public BooleanValue M;
   public BooleanValue j;
   public BooleanValue D;
   public ModeValue e;
   public FloatValue i;
   public FloatValue o;
   public FloatValue h;
   private List S;
   private float I;
   private final Map P;
   private final Map p;
   private float t;
   private float d;
   private boolean E;
   private final SkijaRenderer Q;
   private static final String[] b = new String[]{"Y Offset", "[ArrayList] Render failed: ", " §7", "Right", "Right", "X Offset", "Pretty Module Name", "Rainbow", "ArrayList Size", "Left", "ArrayList Direction", "Hide Render Modules"};
   public ArrayListModule() {
      String[] var1 = b;
      this.M = ValueBuilder.m(this, "Pretty Module Name").z(v -> deobfLambda$new$0((Value)v)).h(false).f$K().f$O();
      this.j = ValueBuilder.m(this, "Hide Render Modules").z(v -> deobfLambda$new$1((Value)v)).h(false).f$K().f$O();
      this.D = ValueBuilder.m(this, "Rainbow").h(true).f$K().f$O();
      this.e = ValueBuilder.m(this, "ArrayList Direction").m(0).W(new String[]{"Right", "Left"}).f$K().T$t();
      this.i = ValueBuilder.m(this, "X Offset").w(-100.0F).M(100.0F).d(1.0F).V(1.0F).f$K().L();
      this.o = ValueBuilder.m(this, "Y Offset").w(1.0F).M(100.0F).d(1.0F).V(1.0F).f$K().L();
      this.h = ValueBuilder.m(this, "ArrayList Size").d(0.4F).V(0.01F).w(0.1F).M(1.0F).f$K().L();
      this.P = new HashMap();
      this.p = new HashMap();
      this.t = -1.0F;
      this.E = true;
      this.Q = SkiaRenderManager.X$m();
   }

   public static ArrayListModule r() {
      try {
         return (ArrayListModule)Elowen.S$Elowen().q$ModuleManager().A(ArrayListModule.class);
      } catch (RuntimeException var1) {
         return null;
      }
   }

   public String r(Module var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      String var3 = this.M.w() ? var1.o$String() : var1.i();
      return var3 + (var1.W$String() == null ? "" : " §7" + var1.W$String());
   }

   @com.elowen.events.api.EventTarget
   public void l(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (G.level != null && G.player != null) {
         if (this.w()) {
            try {
               Canvas var3 = this.Q.G$Canvas();
               if (var3 == null) {
                  return;
               }

               this.E(var3);
               this.Q.p();
               this.Q.G$V();
            } catch (RuntimeException var4) {
               System.err.println("[ArrayList] Render failed: " + var4);
            }
         }
      }
   }

   private void W$V() {
      int var13 = 0;
      HasValue[] var10000 = Theme.s$ArrQ();
      float var2 = this.h.o$F() * 23.498F;
      HasValue[] var1 = var10000;
      float var16;
      int var12 = (var16 = var2 - this.t) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
      if (var1 != null) {
         ;
      }

      int var3 = var12;
      var13 = var3;
      label120:
      if (var1 != null) {
         if ((var3 == 0)) {
            var13 = this.E ? 1 : 0;
            if (var1 == null) {
               break label120;
            }

            if (!this.E) {
               var13 = Module.k ? 1 : 0;
               if (var1 == null) {
                  break label120;
               }

               if (!Module.k) {
                  return;
               }
            }
         }

         var13 = var3;
      }

      if (var1 != null) {
         if (var13 != 0) {
            this.t = var2;
            this.P.clear();
            this.p.clear();
         }

         this.E = false;
         var13 = 0;
      }

      Module.k = (var13 != 0);
      ModuleManager var4 = Elowen.S$Elowen().q$ModuleManager();
      java.util.ArrayList var5 = new java.util.ArrayList(var4.i());
      if (var1 != null && this.j.w()) {
         var5.removeIf(v -> deobfLambda$ensureArrayLayout$0((Module)v));
      }

      HashMap var6 = new HashMap(var5.size() * 2);
      HashMap var7 = new HashMap(var5.size() * 2);
      Iterator var8 = var5.iterator();

      while (true) {
         if (var8.hasNext()) {
            Module var9 = (Module)var8.next();
            String var10 = this.r(var9);
            Float var11 = (Float)this.p.get(var9);
            if (var1 == null) {
               break;
            }

            label97: {
               label96: {
                  Float var14 = var11;
                  if (var1 != null) {
                     if (var11 != null) {
                        if (var1 == null) {
                           break label97;
                        }

                        if (var10.equals(this.P.get(var9))) {
                           break label96;
                        }
                     }

                     var14 = this.y(var10, var2);
                  }

                  var11 = var14;
               }

               var6.put(var9, var10);
               var7.put(var9, var11);
            }

            if (var1 != null) {
               continue;
            }
         }

         var5.sort((x, y) -> deobfLambda$ensureArrayLayout$1(var7, (Module)x, (Module)y));
         this.P.clear();
         this.P.putAll(var6);
         this.p.clear();
         this.p.putAll(var7);
         this.S = var5;
         break;
      }

      float var15;
      label83: {
         Object var10001 = var5;
         if (var1 != null) {
            if (var5.isEmpty()) {
               var15 = 0.0F;
               break label83;
            }

            var10001 = var7.get(var5.get(0));
         }

         var15 = (Float)var10001;
      }

      this.I = var15;
      this.d = this.t(com.elowen.utils.renderer.Fonts.i(var2).getTypeface(), var2);
   }

   private void E(Canvas var1) {
      Theme var33 = null;
      java.util.ArrayList var46 = null;
      HasValue[] var2;
      float var3;
      Typeface var4;
      float var5;
      float var6;
      int var7;
      float var44;
      label129: {
         HasValue[] var10000 = Theme.s$ArrQ();
         this.W$V();
         var3 = this.t;
         var2 = var10000;
         var4 = com.elowen.utils.renderer.Fonts.i(var3).getTypeface();
         var5 = this.d;
         var6 = this.I;
         var7 = ((this.e.t("Right")) ? 1 : 0);
         int var43 = var7;
         if (var2 != null) {
            if (var7 == 0) {
               var44 = 3.0F + this.i.o$F();
               break label129;
            }

            var43 = G.getWindow().getGuiScaledWidth();
         }

         var44 = var43 - var6 - 6.0F + this.i.o$F();
      }

      float var8 = var44;
      float var9 = this.o.o$F();
      java.util.ArrayList<ArrayListModule$RowInfo> var10 = new java.util.ArrayList<>();
      float var11 = Float.MAX_VALUE;
      float var12 = Float.MAX_VALUE;
      float var13 = Float.MIN_VALUE;
      float var14 = Float.MIN_VALUE;
      float var15 = 0.0F;
      Iterator var16 = this.S.iterator();

      label122: {
         while (true) {
            if (var16.hasNext()) {
               Module var17 = (Module)var16.next();
               SmoothAnimationTimer var18 = var17.l$M();
               if (var2 == null) {
                  break;
               }

               var18.J = var17.w() ? 100.0F : 0.0F;
               var18.F(true);
               var44 = var18.l;
               float var10001 = 0.0F;
               if (var2 != null) {
                  if (var18.l <= 0.0F) {
                     continue;
                  }

                  var44 = var18.l;
                  var10001 = 100.0F;
               }

               float var19 = var44 / var10001;
               String var20 = (String)this.P.get(var17);
               float var21 = (Float)this.p.get(var17);
               float var22 = var19 * var5;
               float var23 = -var21 * (1.0F - var19);
               float var24 = var6 - var21 * var19;
               float var25 = var7 != 0 ? var24 : var23;
               float var26 = var21 + 3.0F;
               float var27 = var22;
               float var28 = var8 + var25;
               float var29 = var9 + var15 + 2.0F;
               var10.add(new ArrayListModule$RowInfo(var20, var28, var29, var26, var27, var19));
               var11 = Math.min(var11, var28);
               var12 = Math.min(var12, var29);
               var13 = Math.max(var13, var28 + var26);
               var14 = Math.max(var14, var29 + var27);
               var15 += var27;
               if (var2 != null) {
                  continue;
               }
            }

            var46 = var10;
            if (var2 == null) {
               break label122;
            }

            if (var10.isEmpty()) {
               return;
            }
            break;
         }

         var46 = new java.util.ArrayList(var10.size());
      }

      java.util.ArrayList var31 = var46;
      Iterator var32 = var10.iterator();

      while (true) {
         if (var32.hasNext()) {
            ArrayListModule$RowInfo var34 = (ArrayListModule$RowInfo)var32.next();
            var31.add(new float[]{var34.Z(), var34.R$F(), var34.Q(), var34.u$F(), var34.m$F()});
            if (var2 == null) {
               break;
            }

            if (var2 != null) {
               continue;
            }
         }

         SkijaEffects.T(this.Q, var1, var11, var12, var13 - var11, var14 - var12, 0.0F, var31);
         break;
      }

      label95: {
         var33 = Theme.K();
         Theme var47 = var33;
         if (var2 != null) {
            if (var33 == null) {
               var44 = 10.0F;
               break label95;
            }

            var47 = var33;
         }

         var44 = var47.j.o$F();
      }

      float var35;
      label89: {
         var35 = var44;
         Theme var49 = var33;
         if (var2 != null) {
            if (var33 == null) {
               var44 = 10.0F;
               break label89;
            }

            var49 = var33;
         }

         var44 = var49.h.o$F();
      }

      float var36 = var44;
      var15 = 0.0F;

      for (ArrayListModule$RowInfo var38 : var10) {
         this.Q.d(var38.Z(), var38.R$F(), var38.Q(), var38.u$F(), 0.0F, Theme.D);
         float var39 = var38.Z() + 1.5F;
         float var40 = var38.R$F() - 1.0F;
         int var41 = -1;
         int var51 = ((this.D.w()) ? 1 : 0);
         if (var2 != null) {
            if (var51 != 0) {
               var41 = com.elowen.utils.renderer.RenderUtils.A((int)(-var15 * var35), 1.0F, 1.0F, (21.0F - var36) * 1000.0F);
            }

            var51 = (int)(var38.m$F() * 255.0F) << 24 | var41 & 16777215;
         }

         int var42 = var51;
         this.Q.q(var38.n$String(), var39, var40, var4, var3, var42);
         var15 += var38.u$F();
         if (var2 == null) {
            break;
         }
      }
   }

   private float y(String var1, float var2) {
      return com.elowen.utils.renderer.SkijaColoredText.V(var1, com.elowen.utils.renderer.Fonts.i(var2).getTypeface(), var2);
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private float t(Typeface var1, float var2) {
      HasValue[] var10000 = Theme.s$ArrQ();
      Font var4 = com.elowen.utils.renderer.SkijaFonts.V(var1, var2);
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

   private static int deobfLambda$ensureArrayLayout$1(Map var0, Module var1, Module var2) {
      return Float.compare((Float)var0.get(var2), (Float)var0.get(var1));
   }

   private static boolean deobfLambda$ensureArrayLayout$0(Module var0) {
      return var0.C() == Category.RENDER;
   }

   private static void deobfLambda$new$1(Value var0) {
      Module.k = true;
   }

   private static void deobfLambda$new$0(Value var0) {
      Module.k = true;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
   }
}
