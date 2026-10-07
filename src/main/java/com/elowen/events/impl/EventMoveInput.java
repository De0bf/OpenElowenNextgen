package com.elowen.events.impl;

public class EventMoveInput implements com.elowen.events.api.events.Event {
   public static Object N;
   public static Object w;
   private float L;
   private float b;
   private boolean u;
   private boolean D;
   private double e;
   private static final String[] a = new String[]{", sneak=", ", jump=", "EventMoveInput(forward=", ", sneakSlowDownMultiplier=", ", strafe="};
   public float I() {
      return this.L;
   }

   public float g$F() {
      return this.b;
   }

   public boolean D() {
      return this.u;
   }

   public boolean q$Z() {
      return this.D;
   }

   public double s$D() {
      return this.e;
   }

   public void b(float var1) {
      this.L = var1;
   }

   public void m(float var1) {
      this.b = var1;
   }

   public void A(boolean var1) {
      this.u = var1;
   }

   public void v(boolean var1) {
      this.D = var1;
   }

   public void Z(double var1) {
      this.e = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventMoveInput var3) {
         ;
      }

      return false;
   }

   protected boolean x(Object var1) {
      return var1 instanceof EventMoveInput;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      byte var2 = 59;
      com.elowen.values.HasValue[] var1 = var10000;
      int var3 = 1;
      var3 = var3 * 59 + Float.floatToIntBits(this.I());
      var3 = var3 * 59 + Float.floatToIntBits(this.g$F());
      int var10 = var3 * 59;
      int var10001 = ((this.D()) ? 1 : 0);
      if (var1 == null) {
         var10001 = (byte)(var10001 != 0 ? 79 : 97);
      }

      var3 = var10 + var10001;
      int var11 = var3 * 59;
      var10001 = ((this.q$Z()) ? 1 : 0);
      if (var1 == null) {
         var10001 = (byte)(var10001 != 0 ? 79 : 97);
      }

      var3 = var11 + var10001;
      long var4 = Double.doubleToLongBits(this.s$D());
      int var12 = var3 * 59 + (int)(var4 >>> 32 ^ var4);
      if (var1 != null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }

      return var12;
   }

   @Override
   public String toString() {
      float var10000 = this.I();
      float var10001 = this.g$F();
      boolean var10002 = this.D();
      double var1 = this.s$D();
      boolean var3 = this.q$Z();
      boolean var4 = var10002;
      float var5 = var10001;
      float var6 = var10000;
      String[] var7 = a;
      return "EventMoveInput(forward=" + var6 + ", strafe=" + var5 + ", jump=" + var4 + ", sneak=" + var3 + ", sneakSlowDownMultiplier=" + var1 + ")";
   }

   public EventMoveInput(float var1, float var2, boolean var3, boolean var4, boolean var5, double var6) {
      this.L = var1;
      this.b = var2;
      this.u = var3;
      this.D = var4;
      this.e = var6;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
