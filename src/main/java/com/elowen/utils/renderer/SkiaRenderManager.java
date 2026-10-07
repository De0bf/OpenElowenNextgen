package com.elowen.utils.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.SurfaceOrigin;
import java.io.PrintStream;
import net.minecraft.client.Minecraft;

public final class SkiaRenderManager {
   private static final Minecraft l;
   private static boolean P;
   private static SkiaGpuRenderer M;
   private static SkiaGpuRenderer Z;
   private static SkiaGpuRenderer L;
   private static float q;
   private static float g;
   private static boolean m;
   private static boolean G;
   private static boolean a;
   private static boolean v;
   private static int y;
   private static boolean h;
   private static boolean k;
   private static int U;
   private static boolean X;
   private static SkiaGpuRenderer e;
   private static SkijaRenderer x;
   private static SkijaRenderer A;
   private static boolean o;
   private static boolean r;
   private static final String[] b = new String[]{"overlay pass", "[SkiaRenderManager] Skia 渲染已停用(", "ui pass", "world pass", ")，本局不再重试: "};
   private SkiaRenderManager() {
   }

   private static void y(String var0, Throwable var1) {
      boolean var10000 = com.elowen.utils.renderer.SkijaRenderer.x();
      o = true;
      boolean var2 = var10000;
      var10000 = r;
      if (!var2) {
         if (r) {
            return;
         }

         var10000 = true;
      }

      r = var10000;
      PrintStream var5 = System.err;
      String var10002 = String.valueOf(var1);
      String[] var3 = b;
      var5.println("[SkiaRenderManager] Skia 渲染已停用(" + var0 + ")，本局不再重试: " + var10002);
      var1.printStackTrace();
   }

   public static SkijaRenderer X$m() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (x == null) {
         x = new SkijaRenderer(com.elowen.utils.renderer.SkiaRenderManager::v$n);
      }

      return x;
   }

   public static SkijaRenderer c$m() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (A == null) {
         A = new SkijaRenderer(com.elowen.utils.renderer.SkiaRenderManager::B$n);
      }

      return A;
   }

   public static int t() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      return Z != null ? Z.u$I() : l.gameRenderer.mainRenderTarget().width;
   }

   public static int S$I() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.x();
      SkiaGpuRenderer var10000 = Z;
      if (!var0) {
         if (Z == null) {
            return l.gameRenderer.mainRenderTarget().height;
         }

         var10000 = Z;
      }

      return var10000.H();
   }

   public static int L() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      return M != null ? M.u$I() : l.gameRenderer.mainRenderTarget().width;
   }

   public static int F() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      return M != null ? M.H() : l.gameRenderer.mainRenderTarget().height;
   }

   public static void X$V() {
      if (SkiaGpuRenderer.c$Z()) {
         SkiaGpuRenderer.Y();
      }
   }

   public static void W$V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      Canvas var1 = Z != null ? Z.y$Canvas() : null;
      if (a && var1 != null) {
         var1.restoreToCount(y);
      }

      if (G && M != null) {
         M.t();
         if (!P) {
            M.m();
         }
      }

      if (v && Z != null) {
         Z.t();
         if (!P) {
            Z.m();
         }
      }

      if (X && e != null) {
         e.F();
         X = false;
         e = null;
      }

      m = false;
      a = false;
      G = false;
      v = false;
   }

   public static void N() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.x();
      if (G) {
         SkiaGpuRenderer var10000 = M;
         if (!var0) {
            if (M == null) {
               return;
            }

            var10000 = M;
         }

         var10000.t();
         boolean var1 = P;
         if (!var0) {
            if (!P) {
               M.m();
            }

            var1 = false;
         }

         G = var1;
      }
   }

   public static Canvas g$Canvas() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.x();
      if (o) {
         return null;
      }

      try {
         x();
      } catch (Throwable var2) {
         y("world pass", var2);
         return null;
      }

      SkiaGpuRenderer var1 = M;
      SkiaGpuRenderer var10000 = var1;
      if (!var0) {
         if (var1 == null) {
            return null;
         }

         var10000 = var1;
      }

      return var10000.y$Canvas();
   }

   public static void y$V() {
   }

   public static Canvas K() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (o) {
         return null;
      }

      try {
         v$V();
      } catch (Throwable var2) {
         y("ui pass", var2);
         return null;
      }

      if (Z == null) {
         return null;
      }

      v = true;
      Canvas var1 = Z.y$Canvas();
      if (var1 == null) {
         return null;
      }

      var1.save();
      var1.scale(q, g);
      return var1;
   }

   public static void s$V() {
      boolean var0;
      Canvas var2;
      label43: {
         var0 = com.elowen.utils.renderer.SkijaRenderer.x();
         SkiaGpuRenderer var10000 = Z;
         if (!var0) {
            if (Z == null) {
               var2 = null;
               break label43;
            }

            var10000 = Z;
         }

         var2 = var10000.y$Canvas();
      }

      Canvas var1 = var2;
      if (a) {
         var2 = var1;
         if (!var0) {
            if (var1 == null) {
               return;
            }

            var2 = var1;
         }

         if (!var0 && var2.getSaveCount() > y) {
            var1.restore();
         }
      }
   }

   public static void A$V() {
      G = true;
   }

   public static Canvas R$Canvas() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (o) {
         return null;
      }

      if (!h) {
         h = true;

         try {
            if (L == null) {
               P = SkiaGpuRenderer.S$Z();
               if (P) {
                  L = SkiaGpuRenderer.M();
               }
            }

            if (L != null) {
               n(L);
            }

            H();
         } catch (Throwable var4) {
            y("overlay pass", var4);
            return null;
         }

         if (L == null) {
            return null;
         }

         int var1 = l.getWindow().getGuiScaledWidth();
         int var2 = l.getWindow().getGuiScaledHeight();
         q = (float)L.u$I() / var1;
         g = (float)L.H() / var2;
         Canvas var3 = L.y$Canvas();
         if (var3 == null) {
            return null;
         }

         U = var3.getSaveCount();
      }

      if (L == null) {
         return null;
      }

      k = true;
      Canvas var5 = L.y$Canvas();
      if (var5 == null) {
         return null;
      }

      var5.save();
      var5.scale(q, g);
      return var5;
   }

   public static void S$V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (h && L != null) {
         Canvas var1 = L.y$Canvas();
         if (var1 != null) {
            var1.restoreToCount(U);
         }

         if (X && e != null) {
            e.F();
            X = false;
            e = null;
         }
      }
   }

   public static void u$V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (k && L != null) {
         n(L);
         L.t();
         if (!P) {
            L.m();
         }

         if (X && e != null) {
            e.F();
            X = false;
            e = null;
         }
      }

      h = false;
      k = false;
   }

   private static void H() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (P) {
         RenderTarget var1 = l.gameRenderer.mainRenderTarget();
         L.d(var1.width, var1.height, var1.getColorTexture());
      }

      int var3 = Math.max(1, l.getWindow().getGuiScaledWidth() * l.getWindow().getGuiScale());
      int var2 = Math.max(1, l.getWindow().getGuiScaledHeight() * l.getWindow().getGuiScale());
      if (L == null || L.u$I() != var3 || L.H() != var2) {
         if (L != null) {
            L.close();
         }

         L = SkiaGpuRenderer.Y(var3, var2, SurfaceOrigin.BOTTOM_LEFT);
      }

      L.y$Canvas().clear(0);
   }

   private static void n(SkiaGpuRenderer var0) {
      boolean var1 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (!X && var0 != null) {
         var0.p();
         X = true;
         e = var0;
      }
   }

   private static void x() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (!m) {
         m = true;
         if (M == null) {
            P = SkiaGpuRenderer.S$Z();
            if (P) {
               M = SkiaGpuRenderer.M();
            }
         }

         if (M != null) {
            n(M);
         }

         V();
      }
   }

   private static void V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (P) {
         RenderTarget var1 = l.gameRenderer.mainRenderTarget();
         M.d(var1.width, var1.height, var1.getColorTexture());
      }

      int var3 = Math.max(1, l.getWindow().getGuiScaledWidth() * l.getWindow().getGuiScale());
      int var2 = Math.max(1, l.getWindow().getGuiScaledHeight() * l.getWindow().getGuiScale());
      if (M == null || M.u$I() != var3 || M.H() != var2) {
         if (M != null) {
            M.close();
         }

         M = SkiaGpuRenderer.Y(var3, var2, SurfaceOrigin.BOTTOM_LEFT);
      }

      M.y$Canvas().clear(0);
   }

   private static void v$V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (!a) {
         a = true;
         if (Z == null) {
            P = SkiaGpuRenderer.S$Z();
            if (P) {
               Z = SkiaGpuRenderer.M();
            }
         }

         if (Z != null) {
            n(Z);
         }

         k();
         int var1 = l.getWindow().getGuiScaledWidth();
         int var2 = l.getWindow().getGuiScaledHeight();
         q = (float)Z.u$I() / var1;
         g = (float)Z.H() / var2;
         y = Z.y$Canvas().getSaveCount();
      }
   }

   private static void k() {
      SkiaGpuRenderer var5 = null;
      SkiaGpuRenderer var4 = null;
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.x();
      int var10000 = ((P) ? 1 : 0);
      if (!var0) {
         if (P) {
            RenderTarget var1 = l.gameRenderer.mainRenderTarget();
            Z.d(var1.width, var1.height, var1.getColorTexture());
            if (!var0) {
               return;
            }
         }

         var10000 = Math.max(1, l.getWindow().getGuiScaledWidth() * l.getWindow().getGuiScale());
      }

      label60: {
         label70: {
            int var2;
            int var3;
            var3 = var10000;
            var2 = Math.max(1, l.getWindow().getGuiScaledHeight() * l.getWindow().getGuiScale());
            var4 = Z;
            label58:
            if (!var0) {
               if (Z != null) {
                  var4 = Z;
                  if (var0) {
                     break label58;
                  }

                  if (Z.u$I() == var3) {
                     var5 = Z;
                     if (var0) {
                        break label60;
                     }

                     if (Z.H() == var2) {
                        break label70;
                     }
                  }
               }

               var4 = Z;
            }

            if (!var0) {
               if (var4 != null) {
                  Z.close();
               }

               var4 = SkiaGpuRenderer.Y(var3, var2, SurfaceOrigin.BOTTOM_LEFT);
            }

            Z = var4;
            if (!var0) {
               return;
            }
         }

         var5 = Z;
      }

      var5.y$Canvas().clear(0);
   }

   static SkiaGpuRenderer v$n() {
      return Z;
   }

   static SkiaGpuRenderer p() {
      return M;
   }

   static SkiaGpuRenderer B$n() {
      return L;
   }

   public static void l$V() {
      boolean var0 = com.elowen.utils.renderer.SkijaRenderer.x();
      SkiaGpuRenderer var10000 = Z;
      if (!var0) {
         if (Z != null) {
            Z.close();
            Z = null;
         }

         var10000 = M;
      }

      if (!var0) {
         if (var10000 != null) {
            M.close();
            M = null;
         }

         var10000 = L;
      }

      label34: {
         if (!var0) {
            if (var10000 == null) {
               break label34;
            }

            var10000 = L;
         }

         var10000.close();
         L = null;
      }

      m = false;
      a = false;
      h = false;
      X = false;
      e = null;
      G = false;
      v = false;
      k = false;
   }

   static {
      l = Minecraft.getInstance();
      q = 1.0F;
      g = 1.0F;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
