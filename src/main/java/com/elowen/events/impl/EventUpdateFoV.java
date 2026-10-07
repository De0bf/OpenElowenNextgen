package com.elowen.events.impl;

public class EventUpdateFoV implements com.elowen.events.api.events.Event {
   private float e;
   private static final String a;

   public EventUpdateFoV(float var1) {
      this.e = var1;
   }

   public float F() {
      return this.e;
   }

   public void F(float var1) {
      this.e = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventUpdateFoV var3) {
         ;
      }

      return false;
   }

   protected boolean T(Object var1) {
      return var1 instanceof EventUpdateFoV;
   }

   @Override
   public int hashCode() {
      byte var1 = 59;
      byte var2 = 1;
      return var2 * 59 + Float.floatToIntBits(this.F());
   }

   @Override
   public String toString() {
      return a + this.F() + ")";
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "EventUpdateFoV(fov=";
   }
}
