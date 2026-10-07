package com.elowen.utils;

public class Vector2f {
   public float H;
   public float E;
   private static String b;
   private static final String[] a = new String[]{", ", "Vector2f["};
   public Vector2f() {
   }

   public Vector2f(float var1, float var2) {
      this.D(var1, var2);
   }

   public static float G(Vector2f var0, Vector2f var1) {
      return var0.H * var1.H + var0.E * var1.E;
   }

   public static float t(Vector2f var0, Vector2f var1) {
      String var10000 = e();
      float var3 = G(var0, var1) / (var0.H() * var1.H());
      String var2 = var10000;
      float var6;
      int var4 = (var6 = var3 - -1.0F) == 0.0F ? 0 : (var6 < 0.0F ? -1 : 1);
      if (var2 == null) {
         if (var4 < 0) {
            var3 = -1.0F;
            if (var2 == null) {
               return (float)Math.acos(var3);
            }
         }

         if (var2 != null) {
            return var3;
         }

         float var7;
         var4 = (var7 = var3 - 1.0F) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
      }

      if (var4 > 0) {
         var3 = 1.0F;
      }

      return (float)Math.acos(var3);
   }

   public static Vector2f m(Vector2f var0, Vector2f var1, Vector2f var2) {
      String var3 = e();
      if (var2 == null) {
         return new Vector2f(var0.H + var1.H, var0.E + var1.E);
      }

      var2.D(var0.H + var1.H, var0.E + var1.E);
      return var2;
   }

   public static Vector2f b(Vector2f var0, Vector2f var1, Vector2f var2) {
      String var3 = e();
      if (var2 == null) {
         return new Vector2f(var0.H - var1.H, var0.E - var1.E);
      }

      var2.D(var0.H - var1.H, var0.E - var1.E);
      return var2;
   }

   public void D(float var1, float var2) {
      this.H = var1;
      this.E = var2;
   }

   public final float H() {
      return (float)Math.sqrt(this.O());
   }

   public float O() {
      return this.H * this.H + this.E * this.E;
   }

   public Vector2f a(float var1, float var2) {
      this.H += var1;
      this.E += var2;
      return this;
   }

   public Vector2f E(Vector2f var1) {
      String var2 = e();
      if (var1 == null) {
         var1 = new Vector2f();
      }

      var1.H = -this.H;
      var1.E = -this.E;
      return var1;
   }

   public Vector2f V(Vector2f var1) {
      String var10000 = e();
      float var3 = this.H();
      String var2 = var10000;
      Vector2f var4 = var1;
      if (var2 == null) {
         if (var1 == null) {
            var1 = new Vector2f(this.H / var3, this.E / var3);
            if (var2 == null) {
               return var1;
            }
         }

         var4 = var1;
      }

      var4.D(this.H / var3, this.E / var3);
      return var1;
   }

   @Override
   public String toString() {
      String[] var1 = a;
      return "Vector2f[" + this.H + ", " + this.E + "]";
   }

   public final float S$F() {
      return this.H;
   }

   public final void Z(float var1) {
      this.H = var1;
   }

   public final float p() {
      return this.E;
   }

   public final void t(float var1) {
      this.E = var1;
   }

   @Override
   public boolean equals(Object var1) {
      String var2 = e();
      if (this == var1) {
         return true;
      }

      if (var1 == null) {
         return false;
      }

      if (this.getClass() != var1.getClass()) {
         return false;
      }

      Vector2f var3 = (Vector2f)var1;
      return this.H == var3.H && this.E == var3.E;
   }

   public static void M(String var0) {
      b = var0;
   }

   public static String e() {
      return b;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
      M(null);
   }
}
