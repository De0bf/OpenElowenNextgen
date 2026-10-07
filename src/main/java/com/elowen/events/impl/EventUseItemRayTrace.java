package com.elowen.events.impl;

public class EventUseItemRayTrace implements com.elowen.events.api.events.Event {
   private float f;
   private float D;
   private static final String[] a = new String[]{", pitch=", "EventUseItemRayTrace(yaw="};
   public EventUseItemRayTrace(float var1, float var2) {
      this.f = var1;
      this.D = var2;
   }

   public float h$F() {
      return this.f;
   }

   public float a$F() {
      return this.D;
   }

   public void V(float var1) {
      this.f = var1;
   }

   public void m(float var1) {
      this.D = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventUseItemRayTrace var3) {
         ;
      }

      return false;
   }

   protected boolean U(Object var1) {
      return var1 instanceof EventUseItemRayTrace;
   }

   @Override
   public int hashCode() {
      byte var1 = 59;
      int var2 = 1;
      var2 = var2 * 59 + Float.floatToIntBits(this.h$F());
      return var2 * 59 + Float.floatToIntBits(this.a$F());
   }

   @Override
   public String toString() {
      float var10000 = this.h$F();
      float var10001 = this.a$F();
      String[] var1 = a;
      return "EventUseItemRayTrace(yaw=" + var10000 + ", pitch=" + var10001 + ")";
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
