package com.elowen.utils;

import java.util.Arrays;

public class SharedESPData {
   public String n;
   public double u;
   public double E;
   public double G;
   public double F;
   public double S;
   public double W;
   public double[] i;
   public String[] l;
   public long K;
   private static final String[] a = new String[]{", posZ=", ", posY=", ", health=", "SharedESPData(displayName=", ", absorption=", ", maxHealth=", ", renderPosition=", ", posX=", ", updateTime=", ", tags="};
   public String R$String() {
      return this.n;
   }

   public double n$D() {
      return this.u;
   }

   public double X$D() {
      return this.E;
   }

   public double I() {
      return this.G;
   }

   public double g$D() {
      return this.F;
   }

   public double M() {
      return this.S;
   }

   public double P() {
      return this.W;
   }

   public double[] y$ArrD() {
      return this.i;
   }

   public String[] R$ArrString() {
      return this.l;
   }

   public long X$J() {
      return this.K;
   }

   public void w(String var1) {
      this.n = var1;
   }

   public void i(double var1) {
      this.u = var1;
   }

   public void t(double var1) {
      this.E = var1;
   }

   public void P(double var1) {
      this.G = var1;
   }

   public void c(double var1) {
      this.F = var1;
   }

   public void W(double var1) {
      this.S = var1;
   }

   public void H(double var1) {
      this.W = var1;
   }

   public void n(double[] var1) {
      this.i = var1;
   }

   public void L(String[] var1) {
      this.l = var1;
   }

   public void K(long var1) {
      this.K = var1;
   }

   @Override
   public boolean equals(Object var1) {
      String var2 = Vector2f.e();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof SharedESPData var3) {
         ;
      }

      return false;
   }

   protected boolean b(Object var1) {
      return var1 instanceof SharedESPData;
   }

   @Override
   public int hashCode() {
      String var1;
      int var28;
      int var30;
      label29: {
         byte var2 = 59;
         String var10000 = Vector2f.e();
         int var3 = 1;
         long var4 = Double.doubleToLongBits(this.n$D());
         var3 = var3 * 59 + (int)(var4 >>> 32 ^ var4);
         long var6 = Double.doubleToLongBits(this.X$D());
         var3 = var3 * 59 + (int)(var6 >>> 32 ^ var6);
         long var8 = Double.doubleToLongBits(this.I());
         var3 = var3 * 59 + (int)(var8 >>> 32 ^ var8);
         long var10 = Double.doubleToLongBits(this.g$D());
         var3 = var3 * 59 + (int)(var10 >>> 32 ^ var10);
         long var12 = Double.doubleToLongBits(this.M());
         var3 = var3 * 59 + (int)(var12 >>> 32 ^ var12);
         long var14 = Double.doubleToLongBits(this.P());
         var3 = var3 * 59 + (int)(var14 >>> 32 ^ var14);
         var1 = var10000;
         long var16 = this.X$J();
         var3 = var3 * 59 + (int)(var16 >>> 32 ^ var16);
         String var18 = this.R$String();
         var28 = var3 * 59;
         String var10001 = var18;
         if (var1 == null) {
            if (var18 == null) {
               var30 = 43;
               break label29;
            }

            var10001 = var18;
         }

         var30 = var10001.hashCode();
      }

      int var26 = var28 + var30;
      var26 = var26 * 59 + Arrays.hashCode(this.y$ArrD());
      var28 = var26 * 59 + Arrays.deepHashCode(this.R$ArrString());
      if (var1 != null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.x());
      }

      return var28;
   }

   @Override
   public String toString() {
      String var10000 = this.R$String();
      double var10001 = this.n$D();
      double var10002 = this.X$D();
      double var10003 = this.I();
      double var10004 = this.g$D();
      double var10005 = this.M();
      double var10006 = this.P();
      String var10007 = Arrays.toString(this.y$ArrD());
      String var10008 = Arrays.deepToString(this.R$ArrString());
      long var1 = this.X$J();
      String var3 = var10008;
      String var4 = var10007;
      double var5 = var10006;
      double var7 = var10005;
      double var9 = var10004;
      double var11 = var10003;
      double var13 = var10002;
      double var15 = var10001;
      String var17 = var10000;
      String[] var18 = a;
      return "SharedESPData(displayName="
         + var17
         + ", posX="
         + var15
         + ", posY="
         + var13
         + ", posZ="
         + var11
         + ", health="
         + var9
         + ", maxHealth="
         + var7
         + ", absorption="
         + var5
         + ", renderPosition="
         + var4
         + ", tags="
         + var3
         + ", updateTime="
         + var1
         + ")";
   }

   public SharedESPData(String var1, double var2, double var4, double var6, double var8, double var10, double var12, double[] var14, String[] var15, long var16) {
      this.n = var1;
      this.u = var2;
      this.E = var4;
      this.G = var6;
      this.F = var8;
      this.S = var10;
      this.W = var12;
      this.i = var14;
      this.l = var15;
      this.K = var16;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
