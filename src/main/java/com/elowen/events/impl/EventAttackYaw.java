package com.elowen.events.impl;

public class EventAttackYaw implements com.elowen.events.api.events.Event {
   private float y;

   public float t() {
      return this.y;
   }

   public void r(float var1) {
      this.y = var1;
   }

   public EventAttackYaw(float var1) {
      this.y = var1;
   }
}
