package com.elowen.utils;

public class SmoothAnimationTimer {
   public float J;
   public float G = 0.4F;
   public float l;

   public SmoothAnimationTimer(float var1) {
      this.J = var1;
      this.l = var1;
   }

   public SmoothAnimationTimer(float var1, float var2) {
      this.J = var1;
      this.l = var2;
   }

   public SmoothAnimationTimer(float var1, float var2, float var3) {
      this.J = var1;
      this.G = var3;
      this.l = var2;
   }

   public void F(boolean var1) {
      this.l = AnimationUtils.R(this.l, var1 ? this.J : 0.0F, Math.max(10.0F, Math.abs(this.l - (var1 ? this.J : 0.0F)) * 40.0F) * this.G);
   }

   public boolean P(boolean var1) {
      String var2 = Vector2f.e();
      return var1 ? this.l == this.J : this.l == 0.0F;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
