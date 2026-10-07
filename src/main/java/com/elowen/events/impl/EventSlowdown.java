package com.elowen.events.impl;

public class EventSlowdown implements com.elowen.events.api.events.Event {
   private boolean C;
   private static final String a;

   public boolean B$Z() {
      return this.C;
   }

   public void N(boolean var1) {
      this.C = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventSlowdown var3) {
         ;
      }

      return false;
   }

   protected boolean V(Object var1) {
      return var1 instanceof EventSlowdown;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      byte var2 = 59;
      byte var3 = 1;
      com.elowen.values.HasValue[] var1 = var10000;
      int var4 = var3 * 59;
      int var10001 = ((this.B$Z()) ? 1 : 0);
      if (var1 == null) {
         var10001 = (byte)(var10001 != 0 ? 79 : 97);
      }

      return var4 + var10001;
   }

   @Override
   public String toString() {
      return a + this.B$Z() + ")";
   }

   public EventSlowdown(boolean var1) {
      this.C = var1;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "EventSlowdown(slowdown=";
   }
}
