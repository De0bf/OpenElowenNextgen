package com.elowen.events.impl;

public class EventSprint implements com.elowen.events.api.events.Event {
   private final boolean Q;
   private int f;
   private static final String[] a = new String[]{"EventSprint(sprinting=", "Sprint state must be -1, 0, or 1", ", sprint="};
   public EventSprint(boolean var1) {
      this.Q = var1;
      this.f = 0;
   }

   public boolean Q() {
      return this.Q;
   }

   public int j$I() {
      return this.f;
   }

   public void W(int var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 >= -1 && var1 <= 1) {
         this.f = var1;
      } else {
         throw new IllegalArgumentException("Sprint state must be -1, 0, or 1");
      }
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventSprint var3) {
         ;
      }

      return false;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var1 = EventPacket.C();
      return 59 + this.f * 2 + (this.Q ? 1 : 0);
   }

   @Override
   public String toString() {
      String[] var1 = a;
      return "EventSprint(sprinting=" + this.Q + ", sprint=" + this.f + ")";
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }

   static {
   }
}
