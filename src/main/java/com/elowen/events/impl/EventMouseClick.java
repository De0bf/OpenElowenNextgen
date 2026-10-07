package com.elowen.events.impl;

public class EventMouseClick implements com.elowen.events.api.events.Event {
   private final int W;
   private final boolean C;
   private static final String[] a = new String[]{"EventMouseClick(key=", ", state="};
   public int v$I() {
      return this.W;
   }

   public boolean Z() {
      return this.C;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventMouseClick var3) {
         ;
      }

      return false;
   }

   protected boolean F(Object var1) {
      return var1 instanceof EventMouseClick;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      byte var2 = 59;
      com.elowen.values.HasValue[] var1 = var10000;
      int var3 = 1;
      var3 = var3 * 59 + this.v$I();
      int var5 = var3 * 59;
      byte var10001 = (((byte)((this.Z()) ? 1 : 0)));
      if (var1 == null) {
         var10001 = (byte)(var10001 != 0 ? 79 : 97);
      }

      return var5 + var10001;
   }

   @Override
   public String toString() {
      int var10000 = this.v$I();
      boolean var10001 = this.Z();
      String[] var1 = a;
      return "EventMouseClick(key=" + var10000 + ", state=" + var10001 + ")";
   }

   public EventMouseClick(int var1, boolean var2) {
      this.W = var1;
      this.C = var2;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
