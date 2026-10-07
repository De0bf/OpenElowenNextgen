package com.elowen.events.impl;

public class EventJump implements com.elowen.events.api.events.Event {
   private float R;

   public void A(float var1) {
      this.R = var1;
   }

   public float h$F() {
      return this.R;
   }

   public EventJump(float var1) {
      this.R = var1;
   }
}
