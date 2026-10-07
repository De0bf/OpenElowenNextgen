package com.elowen.utils;

public final class TimeHelper {
   private long u = 0L;
   private long e = -1L;

   public boolean H(long var1) {
      String var3 = Vector2f.e();
      if (this.D() >= var1) {
         this.p();
         return true;
      } else {
         return false;
      }
   }

   public boolean O(float var1) {
      String var2 = Vector2f.e();
      return (float)(System.currentTimeMillis() - this.e) >= var1;
   }

   public boolean e(double var1) {
      return this.S(var1, false);
   }

   public boolean S(double var1, boolean var3) {
      String var4 = Vector2f.e();
      boolean var5 = MathHelper.K((float)(this.j$J() - this.u), 0.0F, (float)var1) >= var1;
      if (var5 && var3) {
         this.p();
      }

      return var5;
   }

   public void p() {
      this.e = System.currentTimeMillis();
      this.u = this.j$J();
   }

   public void q(long var1) {
      this.e = System.currentTimeMillis();
      this.u = this.j$J() + var1;
   }

   public long D() {
      return System.nanoTime() / 1000000L - this.u;
   }

   public long j$J() {
      return System.nanoTime() / 1000000L;
   }

   public double d$D() {
      return this.j$J() - this.U$J();
   }

   public long U$J() {
      return this.u;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
