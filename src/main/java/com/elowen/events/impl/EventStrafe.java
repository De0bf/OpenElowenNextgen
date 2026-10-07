package com.elowen.events.impl;

public class EventStrafe implements com.elowen.events.api.events.Event {
   private float Q;

   public void y(float var1) {
      this.Q = var1;
   }

   public float q$F() {
      return this.Q;
   }

   public EventStrafe(float var1) {
      this.Q = var1;
   }
}
