package com.elowen.utils.renderer;

import io.github.humbleui.skija.Font;

public final class Fonts {
   public static final float t = 27.304F;
   public static final float X = 23.498F;
   public static final float Y = 32.0F;

   private Fonts() {
   }

   public static void J$V() {
      SkijaFonts.M();
   }

   public static Font i(float var0) {
      return SkijaFonts.E(var0);
   }

   public static Font U(float var0) {
      return SkijaFonts.b(var0);
   }

   public static Font N(float var0) {
      return SkijaFonts.F(var0);
   }

   public static Font H(float var0) {
      return SkijaFonts.j(var0);
   }

   public static Font e(float var0) {
      return SkijaFonts.s(var0);
   }

   public static Font n(float var0) {
      return SkijaFonts.x(var0);
   }

   public static float n(Font var0) {
      return var0.getMetrics().getHeight();
   }
}
