package com.elowen.utils.renderer;

import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontHinting;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.Typeface;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class SkijaFonts {
   private static final Map<String, Typeface> K;
   private static final Map<Typeface, Map<Float, Font>> d;
   private static final String[] a = new String[]{"Unable to load font resource: ", "assets/elowen/fonts/", "Failed to preload font: ", "Failed to load font: ", "Font not found: "};
   public static Font V(Typeface var0, float var1) {
      Font var2 = new Font(var0, var1);
      var2.setSubpixel(true);
      var2.setMetricsLinear(true);
      var2.setHinting(FontHinting.NONE);
      return var2;
   }

   public static Font j(Typeface var0, float var1) {
      return d.computeIfAbsent(var0, SkijaFonts::deobfLambda$cachedFont$0).computeIfAbsent(var1, var10000 -> deobfLambda$cachedFont$1(var0, var10000));
   }

   private SkijaFonts() {
   }

   public static void M() {
      boolean var10000 = SkijaRenderer.x();
      SkijaFonts$FontType[] var1 = SkijaFonts$FontType.values();
      boolean var0 = var10000;

      for (SkijaFonts$FontType var4 : var1) {
         try {
            R(var4);
         } catch (Exception var6) {
            System.err.println("Failed to preload font: " + var4.W$String());
            var6.printStackTrace();
         }

         if (var0) {
            break;
         }
      }
   }

   public static Typeface R(SkijaFonts$FontType var0) {
      return K.computeIfAbsent(var0.W$String(), SkijaFonts::deobfLambda$getTypeface$0);
   }

   public static Font E(float var0) {
      return V(R(SkijaFonts$FontType.OPENSANS), var0);
   }

   public static Font F(float var0) {
      return V(R(SkijaFonts$FontType.OPENSANS_ITALIC), var0);
   }

   public static Font b(float var0) {
      return V(R(SkijaFonts$FontType.OPENSANS_BOLD), var0);
   }

   public static Font q(float var0) {
      return V(R(SkijaFonts$FontType.OPENSANS_BOLD_ITALIC), var0);
   }

   public static Font j(float var0) {
      return V(R(SkijaFonts$FontType.HARMONY), var0);
   }

   public static Font N(float var0) {
      return V(R(SkijaFonts$FontType.HARMONY_ITALIC), var0);
   }

   public static Font s(float var0) {
      return V(R(SkijaFonts$FontType.HARMONY_BOLD), var0);
   }

   public static Font I(float var0) {
      return V(R(SkijaFonts$FontType.HARMONY_BOLD_ITALIC), var0);
   }

   public static Font x(float var0) {
      return V(R(SkijaFonts$FontType.ICONS), var0);
   }

   public static Font h(SkijaFonts$FontType var0, float var1) {
      return V(R(var0), var1);
   }

   public static String b(String var0) {
      boolean var1 = SkijaRenderer.L();
      if (var0 != null && !var0.isEmpty()) {
         StringBuilder var2 = null;
         int var3 = 0;
         while (var3 < var0.length()) {
            char var4 = var0.charAt(var3);
            if (Character.isHighSurrogate(var4)) {
               if (var3 + 1 < var0.length() && Character.isLowSurrogate(var0.charAt(var3 + 1))) {
                  if (var2 != null) {
                     var2.append(var4).append(var0.charAt(var3 + 1));
                  }

                  var3++;
               }

               if (var2 == null) {
                  var2 = new StringBuilder(var0.length());
                  var2.append(var0, 0, var3);
               }

               var2.append('�');
            }

            if (Character.isLowSurrogate(var4)) {
               if (var2 == null) {
                  var2 = new StringBuilder(var0.length());
                  var2.append(var0, 0, var3);
               }

               var2.append('�');
            }

            if (var2 != null) {
               var2.append(var4);
            }

            var3++;
         }

         return var2 == null ? var0 : var2.toString();
      } else {
         return "";
      }
   }

   public static float K(String var0, Typeface var1, float var2) {
      if (var1 == null) {
         return 0.0F;
      }

      String var3 = b(var0);
      return var3.isEmpty() ? 0.0F : j(var1, var2).measureTextWidth(var3);
   }

   public static float t(String var0, SkijaFonts$FontType var1, float var2) {
      return K(var0, R(var1), var2);
   }

   public static float g(Font var0) {
      return var0.getMetrics().getHeight();
   }

   private static Typeface deobfLambda$getTypeface$0(String var0) {
      boolean var1 = SkijaRenderer.x();

      try {
         String[] var7 = a;
         String var2 = "assets/elowen/fonts/" + var0;
         InputStream var3 = SkijaFonts.class.getClassLoader().getResourceAsStream(var2);
         InputStream var10000 = var3;
         if (!var1) {
            if (var3 == null) {
               throw new IllegalArgumentException("Font not found: " + var2);
            }

            var10000 = var3;
         }

         byte[] var4 = var10000.readAllBytes();
         Data var5 = Data.makeFromBytes(var4);
         Typeface var6 = FontMgr.getDefault().makeFromData(var5);
         Typeface var9 = var6;
         if (!var1) {
            if (var6 == null) {
               throw new IllegalStateException("Unable to load font resource: " + var2);
            }

            var9 = var6;
         }

         return var9;
      } catch (IOException var8) {
         throw new RuntimeException("Failed to load font: " + var0, var8);
      }
   }

   private static Font deobfLambda$cachedFont$1(Typeface var0, Float var1) {
      return V(var0, var1);
   }

   private static Map<Float, Font> deobfLambda$cachedFont$0(Typeface var0) {
      return new HashMap<>();
   }

   static {
      K = new HashMap();
      d = new HashMap();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
