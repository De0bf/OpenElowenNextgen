package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;

public class EventMotion extends com.elowen.events.api.events.callables.EventCancellable {
   private final EventType E;
   private double p;
   private double A;
   private double l;
   private float f;
   private float R;
   private boolean d;
   private static String c;

   public EventMotion(EventType var1, float var2, float var3) {
      this.E = var1;
      this.f = var2;
      this.R = var3;
   }

   public EventType Q() {
      return this.E;
   }

   public double y$D() {
      return this.p;
   }

   public double D() {
      return this.A;
   }

   public double G$D() {
      return this.l;
   }

   public float k() {
      return this.f;
   }

   public float g$F() {
      return this.R;
   }

   public boolean r() {
      return this.d;
   }

   public void X(double var1) {
      this.p = var1;
   }

   public void B(double var1) {
      this.A = var1;
   }

   public void i(double var1) {
      this.l = var1;
   }

   public void v(float var1) {
      this.f = var1;
   }

   public void o(float var1) {
      this.R = var1;
   }

   public void q(boolean var1) {
      this.d = var1;
   }

   public EventMotion(EventType var1, double var2, double var4, double var6, float var8, float var9, boolean var10) {
      this.E = var1;
      this.p = var2;
      this.A = var4;
      this.l = var6;
      this.f = var8;
      this.R = var9;
      this.d = var10;
   }

   public static void I(String var0) {
      c = var0;
   }

   public static String d$String() {
      return c;
   }

   static {
      if (d$String() != null) {
         I("B6TMEc");
      }
   }
}
