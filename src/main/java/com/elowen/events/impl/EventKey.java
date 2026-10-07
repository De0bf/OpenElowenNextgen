package com.elowen.events.impl;

public class EventKey extends com.elowen.events.api.events.callables.EventCancellable {
   private final int y;
   private final boolean B;

   public int n$I() {
      return this.y;
   }

   public boolean U$Z() {
      return this.B;
   }

   public EventKey(int var1, boolean var2) {
      this.y = var1;
      this.B = var2;
   }
}
