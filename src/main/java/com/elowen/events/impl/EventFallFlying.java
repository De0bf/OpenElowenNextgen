package com.elowen.events.impl;

public class EventFallFlying implements com.elowen.events.api.events.Event {
   private float S;

   public void E(float var1) {
      this.S = var1;
   }

   public float x() {
      return this.S;
   }

   public EventFallFlying(float var1) {
      this.S = var1;
   }
}
