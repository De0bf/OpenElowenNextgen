package com.elowen.ui.notification;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.renderer.Fonts;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.SkijaColoredText;
import com.elowen.utils.renderer.shader.SkijaShadow;
import com.elowen.values.HasValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;

public class Notification {
   private NotificationLevel A;
   private String K;
   private long E;
   private long h = System.currentTimeMillis();
   private SmoothAnimationTimer e = new SmoothAnimationTimer(0.0F);
   private SmoothAnimationTimer M = new SmoothAnimationTimer(0.0F);
   private static final float L = 9.5564F;
   private static final String[] a = new String[]{", widthTimer=", ", maxAge=", ", createTime=", ", heightTimer=", ", message=", "Notification(level="};
   public Notification(NotificationLevel var1, String var2, long var3) {
      this.A = var1;
      this.K = var2;
      this.E = var3;
   }

   public void N(SkijaRenderer var1, Canvas var2, float var3, float var4) {
      float var5 = var3 + 2.0F;
      float var6 = var4 + 4.0F;
      float var7 = this.G$F();
      float var8 = 20.0F;
      float var9 = 5.0F;
      RRect var10 = RRect.makeLTRB(var5, var6, var5 + var7, var6 + var8, var9);
      SkijaShadow.m(var2, var10, 4.0F, Integer.MIN_VALUE, 0.0F, 0.0F);
   }

   public void J(SkijaRenderer var1, Canvas var2, float var3, float var4) {
      int[] var10000 = NotificationManager.r();
      int var6 = this.A.y$I();
      float var7 = var3 + 2.0F;
      int[] var5 = var10000;
      float var8 = var4 + 4.0F;
      float var9 = this.G$F();
      float var10 = 20.0F;
      float var11 = 5.0F;
      var2.save();
      var2.clipRRect(RRect.makeLTRB(var7, var8, var7 + var9, var8 + var10, var11, var11), true);
      var1.d(var7, var8, var9, var10, var11, var6);
      Typeface var12 = Fonts.H(9.5564F).getTypeface();
      if (var5 == null) {
         if (var12 != null) {
            var1.q(this.K, var3 + 6.0F, var4 + 9.0F, var12, 9.5564F, -1);
         }

         var2.restore();
      }

      if (!HasValue.x()) {
         NotificationManager.s(new int[5]);
      }
   }

   public float G$F() {
      Typeface var1 = Fonts.H(9.5564F).getTypeface();
      if (var1 != null) {
         float var2 = SkijaColoredText.V(this.K, var1, 9.5564F);
         return var2 + 12.0F;
      } else {
         return 100.0F;
      }
   }

   public float u$F() {
      return 24.0F;
   }

   public NotificationLevel R$C() {
      return this.A;
   }

   public String L() {
      return this.K;
   }

   public long P() {
      return this.E;
   }

   public long w() {
      return this.h;
   }

   public SmoothAnimationTimer n$M() {
      return this.e;
   }

   public SmoothAnimationTimer B$M() {
      return this.M;
   }

   public void j(NotificationLevel var1) {
      this.A = var1;
   }

   public void X(String var1) {
      this.K = var1;
   }

   public void J(long var1) {
      this.E = var1;
   }

   public void I(long var1) {
      this.h = var1;
   }

   public void x(SmoothAnimationTimer var1) {
      this.e = var1;
   }

   public void s(SmoothAnimationTimer var1) {
      this.M = var1;
   }

   @Override
   public boolean equals(Object var1) {
      int[] var2 = NotificationManager.r();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof Notification var3) {
         ;
      }

      return false;
   }

   protected boolean K(Object var1) {
      return var1 instanceof Notification;
   }

   @Override
   public int hashCode() {
      int[] var1;
      int var17;
      int var22;
      label71: {
         byte var2 = 59;
         int[] var10000 = NotificationManager.r();
         int var3 = 1;
         long var4 = this.P();
         var3 = var3 * 59 + (int)(var4 >>> 32 ^ var4);
         long var6 = this.w();
         var1 = var10000;
         var3 = var3 * 59 + (int)(var6 >>> 32 ^ var6);
         NotificationLevel var8 = this.R$C();
         var17 = var3 * 59;
         NotificationLevel var10001 = var8;
         if (var1 == null) {
            if (var8 == null) {
               var22 = 43;
               break label71;
            }

            var10001 = var8;
         }

         var22 = var10001.hashCode();
      }

      label65: {
         int var14 = var17 + var22;
         String var9 = this.L();
         var17 = var14 * 59;
         String var23 = var9;
         if (var1 == null) {
            if (var9 == null) {
               var22 = 43;
               break label65;
            }

            var23 = var9;
         }

         var22 = var23.hashCode();
      }

      label59: {
         int var15 = var17 + var22;
         SmoothAnimationTimer var10 = this.n$M();
         var17 = var15 * 59;
         SmoothAnimationTimer var25 = var10;
         if (var1 == null) {
            if (var10 == null) {
               var22 = 43;
               break label59;
            }

            var25 = var10;
         }

         var22 = var25.hashCode();
      }

      label53: {
         int var16 = var17 + var22;
         SmoothAnimationTimer var11 = this.B$M();
         var17 = var16 * 59;
         SmoothAnimationTimer var27 = var11;
         if (var1 == null) {
            if (var11 == null) {
               var22 = 43;
               break label53;
            }

            var27 = var11;
         }

         var22 = var27.hashCode();
      }

      var17 += var22;
      if (var1 != null) {
         HasValue.d(HasValue.x());
      }

      return var17;
   }

   @Override
   public String toString() {
      String var10000 = String.valueOf(this.R$C());
      String var10001 = this.L();
      long var10002 = this.P();
      long var10003 = this.w();
      String var1 = String.valueOf(this.B$M());
      String var2 = String.valueOf(this.n$M());
      long var3 = var10003;
      long var5 = var10002;
      String var7 = var10001;
      String var8 = var10000;
      String[] var9 = a;
      return "Notification(level="
         + var8
         + ", message="
         + var7
         + ", maxAge="
         + var5
         + ", createTime="
         + var3
         + ", widthTimer="
         + var2
         + ", heightTimer="
         + var1
         + ")";
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
