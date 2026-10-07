package com.elowen.events.impl;

public class EventStayingOnGroundSurface implements com.elowen.events.api.events.Event {
   private boolean B;
   private static final String a;

   public boolean o$Z() {
      return this.B;
   }

   public void g(boolean var1) {
      this.B = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventStayingOnGroundSurface var3) {
         ;
      }

      return false;
   }

   protected boolean J(Object var1) {
      return var1 instanceof EventStayingOnGroundSurface;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      byte var2 = 59;
      com.elowen.values.HasValue[] var1 = var10000;
      byte var3 = 1;
      int var4 = var3 * 59;
      int var10001 = ((this.o$Z()) ? 1 : 0);
      if (var1 == null) {
         var10001 = (byte)(var10001 != 0 ? 79 : 97);
      }

      return var4 + var10001;
   }

   @Override
   public String toString() {
      return a + this.o$Z() + ")";
   }

   public EventStayingOnGroundSurface(boolean var1) {
      this.B = var1;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "EventStayingOnGroundSurface(stay=";
   }
}
