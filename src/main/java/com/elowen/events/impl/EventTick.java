package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;

public class EventTick implements com.elowen.events.api.events.Event {
   private final EventType f;

   public EventType s$f() {
      return this.f;
   }

   public EventTick(EventType var1) {
      this.f = var1;
   }
}
