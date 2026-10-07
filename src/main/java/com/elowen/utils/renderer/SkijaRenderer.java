package com.elowen.utils.renderer;

import com.elowen.exceptions.NoSuchModuleException;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;

public class SkijaRenderer implements AutoCloseable {
   private final Typeface P;
   private final Supplier o;
   private final Paint i;
   private final Paint E;
   private final Paint d;
   private static boolean Q;

   SkijaRenderer(Supplier var1) {
      boolean var10000 = L();
      super();
      this.i = new Paint();
      this.E = new Paint();
      this.d = new Paint();
      boolean var2 = var10000;
      this.o = var1;
      Typeface var3 = com.elowen.utils.renderer.SkijaFonts.R(SkijaFonts$FontType.OPENSANS);
      Typeface var4 = var3;
      if (var2) {
         if (var3 == null) {
            var3 = com.elowen.utils.renderer.SkijaFonts.R(SkijaFonts$FontType.HARMONY);
         }

         var4 = var3;
      }

      label25: {
         if (var2) {
            if (var4 != null) {
               break label25;
            }

            var4 = FontMgr.getDefault().matchFamilyStyle(null, FontStyle.NORMAL);
         }

         var3 = var4;
      }

      this.P = var3;
      this.E.setMode(PaintMode.STROKE);
   }

   public SkiaGpuRenderer a$n() {
      return (SkiaGpuRenderer)this.o.get();
   }

   public Surface C() {
      boolean var10000 = x();
      SkiaGpuRenderer var2 = (SkiaGpuRenderer)this.o.get();
      boolean var1 = var10000;
      SkiaGpuRenderer var3 = var2;
      if (!var1) {
         if (var2 == null) {
            return null;
         }

         var3 = var2;
      }

      return var3.U$Surface();
   }

   public float M() {
      return 1.0F;
   }

   public float J$F() {
      return 1.0F;
   }

   public int G$I() {
      return Minecraft.getInstance().getWindow().getGuiScaledWidth();
   }

   public int q$I() {
      return Minecraft.getInstance().getWindow().getGuiScaledHeight();
   }

   public int D() {
      return this.G$I();
   }

   public int W$I() {
      return this.q$I();
   }

   public Canvas E$Canvas() {
      boolean var10000 = L();
      SkiaGpuRenderer var2 = (SkiaGpuRenderer)this.o.get();
      boolean var1 = var10000;
      SkiaGpuRenderer var3 = var2;
      if (var1) {
         if (var2 == null) {
            return null;
         }

         var3 = var2;
      }

      return var3.y$Canvas();
   }

   public Canvas G$Canvas() {
      return SkiaRenderManager.K();
   }

   public void p() {
      SkiaRenderManager.s$V();
   }

   public void G$V() {
   }

   public void O() {
   }

   public void F() {
   }

   public boolean r() {
      return SkiaGpuRenderer.S$Z();
   }

   public void d(float var1, float var2, float var3, float var4, float var5, int var6) {
      RRect var8 = RRect.makeLTRB(var1, var2, var1 + var3, var2 + var4, var5, var5);
      boolean var10000 = x();
      this.i.setColor(var6);
      this.i.setAntiAlias(true);
      Canvas var9 = this.E$Canvas();
      boolean var7 = var10000;
      if (!var7 && var9 != null) {
         var9.drawRRect(var8, this.i);
      }
   }

   public void b(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      boolean var10000 = L();
      RRect var9 = RRect.makeLTRB(var1, var2, var1 + var3, var2 + var4, var5, var5);
      this.E.setColor(var7);
      this.E.setAntiAlias(true);
      boolean var8 = var10000;
      this.E.setStrokeWidth(var6);
      Canvas var10 = this.E$Canvas();
      if (var8 && var10 != null) {
         var10.drawRRect(var9, this.E);
      }
   }

   public void w(float var1, float var2, float var3, int var4) {
      this.i.setColor(var4);
      boolean var10000 = x();
      this.i.setAntiAlias(true);
      boolean var5 = var10000;
      Canvas var6 = this.E$Canvas();
      if (!var5 && var6 != null) {
         var6.drawCircle(var1, var2, var3, this.i);
      }
   }

   public void D(String var1, float var2, float var3, float var4, int var5) {
      this.Y(var1, var2, var3, this.P, var4, var5);
   }

   public void Y(String var1, float var2, float var3, Typeface var4, float var5, int var6) {
      boolean var7 = x();
      if (!var7) {
         if (var4 == null) {
            return;
         }

         var1 = com.elowen.utils.renderer.SkijaFonts.b(var1);
      }

      if (!var1.isEmpty()) {
         Font var8 = com.elowen.utils.renderer.SkijaFonts.j(var4, var5);
         this.d.setColor(var6);
         this.d.setAntiAlias(true);
         float var9 = var3 - var8.getMetrics().getAscent();
         Canvas var10 = this.E$Canvas();
         if (!var7 && var10 != null) {
            var10.drawString(var1, var2, var9, var8, this.d);
         }
      }
   }

   public void E(String var1, float var2, float var3, float var4, int var5) {
      this.q(var1, var2, var3, this.P, var4, var5);
   }

   public void q(String var1, float var2, float var3, Typeface var4, float var5, int var6) {
      char var21 = (char)0;
      int var22 = 0;
      boolean var7 = x();
      if (var4 != null) {
         String var10000 = var1;
         if (!var7) {
            if (var1 == null) {
               return;
            }

            var10000 = var1;
         }

         if (!var10000.isEmpty()) {
            Canvas var8 = this.E$Canvas();
            if (var8 == null) {
               return;
            }

            Font var9 = com.elowen.utils.renderer.SkijaFonts.j(var4, var5);
            float var10 = var3 - var9.getMetrics().getAscent();
            int var11 = var6 >>> 24 & 0xFF;
            int var12 = var6;
            this.d.setAntiAlias(true);
            StringBuilder var13 = new StringBuilder();
            float var14 = var2;
            int var15 = 0;
            int var16 = var1.length();

            while (true) {
               if (var15 < var16) {
                  char var17 = var1.charAt(var15);
                  if (var7) {
                     break;
                  }

                  label129: {
                     label130: {
                        int var10001;
                        var21 = var17;
                        var10001 = 167;
                        label109:
                        if (!var7) {
                           if (var17 != 167) {
                              var21 = var17;
                              var10001 = 38;
                              if (var7) {
                                 break label109;
                              }

                              if (var17 != '&') {
                                 break label129;
                              }
                           }

                           var21 = ((char)(var15 + 1));
                           if (var7) {
                              break label130;
                           }

                           var10001 = var16;
                        }

                        if (var21 >= var10001) {
                           break label129;
                        }

                        var14 = this.J(var8, var9, var13, var14, var10, var12);
                        var21 = var1.charAt(var15 + 1);
                     }

                     label99: {
                        label132: {
                           label133: {
                              int var18 = var21;
                              var22 = var18;
                              byte var23 = 120;
                              if (!var7) {
                                 if (var18 == 120) {
                                    int var19 = SkijaColoredText.S(var1, var15);
                                    if (!var7) {
                                       if (var19 != -1) {
                                          var12 = var11 << 24 | var19;
                                       }

                                       var15 += 14;
                                    }

                                    if (!var7) {
                                       continue;
                                    }
                                 }

                                 var22 = var18;
                                 if (var7) {
                                    break label133;
                                 }

                                 var23 = 114;
                              }

                              if (var22 == var23) {
                                 var12 = var6;
                                 if (!var7) {
                                    break label132;
                                 }
                              }

                              var22 = SkijaColoredText.b((char)var18);
                           }

                           int var20 = var22;
                           if (var7) {
                              break label99;
                           }

                           if (var20 != -1) {
                              var12 = var11 << 24 | var20;
                           }
                        }

                        var15 += 2;
                     }

                     if (!var7) {
                        continue;
                     }
                  }

                  var13.append(var17);
                  var15++;
                  if (!var7) {
                     continue;
                  }
               }

               this.J(var8, var9, var13, var14, var10, var12);
               break;
            }

            return;
         }
      }
   }

   private float J(Canvas var1, Font var2, StringBuilder var3, float var4, float var5, int var6) {
      boolean var7 = L();
      if (var3.length() == 0) {
         return var4;
      }

      String var8 = com.elowen.utils.renderer.SkijaFonts.b(var3.toString());
      var3.setLength(0);
      if (var8.isEmpty()) {
         return var4;
      }

      this.d.setColor(var6);
      var1.drawString(var8, var4, var5, var2, this.d);
      return var4 + var2.measureTextWidth(var8);
   }

   public void H(String var1, float var2, float var3, float var4, int var5) {
      this.l(var1, var2, var3, this.P, var4, var5);
   }

   public void l(String var1, float var2, float var3, Typeface var4, float var5, int var6) {
      boolean var7 = L();
      if (var4 != null) {
         var1 = com.elowen.utils.renderer.SkijaFonts.b(var1);
         if (!var1.isEmpty()) {
            Font var8 = com.elowen.utils.renderer.SkijaFonts.j(var4, var5);
            this.d.setColor(var6);
            this.d.setAntiAlias(true);
            FontMetrics var9 = var8.getMetrics();
            float var10 = var3 - (var9.getAscent() + var9.getDescent()) * 0.5F;
            Canvas var11 = this.E$Canvas();
            if (var11 != null) {
               var11.drawString(var1, var2, var10, var8, this.d);
            }
         }
      }
   }

   public void V(String var1, float var2, float var3, float var4, int var5) {
      this.a(var1, var2, var3, this.P, var4, var5);
   }

   public void a(String var1, float var2, float var3, Typeface var4, float var5, int var6) {
      boolean var7 = L();
      if (var4 != null) {
         var1 = com.elowen.utils.renderer.SkijaFonts.b(var1);
         if (!var1.isEmpty()) {
            Font var8 = com.elowen.utils.renderer.SkijaFonts.j(var4, var5);
            this.d.setColor(var6);
            this.d.setAntiAlias(true);
            float var9 = var8.measureTextWidth(var1);
            float var10 = var3 - var8.getMetrics().getAscent();
            Canvas var11 = this.E$Canvas();
            if (var11 != null) {
               var11.drawString(var1, var2 - var9 / 2.0F, var10, var8, this.d);
            }
         }
      }
   }

   public void F(float var1, float var2, float var3, float var4, float var5, int var6) {
      this.d(var1, var2, var3, var4, var5, var6);
   }

   public void F(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      this.b(var1, var2, var3, var4, var5, var6, var7);
   }

   public float W(String var1, Typeface var2, float var3) {
      return com.elowen.utils.renderer.SkijaFonts.K(var1, var2, var3);
   }

   public Typeface c$Typeface() {
      return this.P;
   }

   @Override
   public void close() {
   }

   public static void J(boolean var0) {
      Q = var0;
   }

   public static boolean L() {
      return Q;
   }

   public static boolean x() {
      return !L();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (!L()) {
         J(true);
      }
   }
}
