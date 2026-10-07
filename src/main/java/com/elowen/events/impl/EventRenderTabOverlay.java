package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.network.chat.Component;

public class EventRenderTabOverlay implements com.elowen.events.api.events.Event {
   private EventType b;
   private Component i;

   public void B(EventType var1) {
      this.b = var1;
   }

   public void O(Component var1) {
      this.i = var1;
   }

   public EventType N() {
      return this.b;
   }

   public Component t() {
      return this.i;
   }

   public EventRenderTabOverlay(EventType var1, Component var2) {
      this.b = var1;
      this.i = var2;
   }
}
