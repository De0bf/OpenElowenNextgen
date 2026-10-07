package com.elowen.events.impl;

public class EventPlayerTick implements com.elowen.events.api.events.Cancellable, com.elowen.events.api.events.Event {
   private boolean g = false;

   @Override
   public boolean c$Z() {
      return this.g;
   }

   @Override
   public void c(boolean var1) {
      this.g = var1;
   }
}
