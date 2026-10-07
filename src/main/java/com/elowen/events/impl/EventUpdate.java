package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;

public class EventUpdate implements com.elowen.events.api.events.Event {
   private final EventType G;

   public EventType a$f() {
      return this.G;
   }

   public EventUpdate(EventType var1) {
      this.G = var1;
   }
}
