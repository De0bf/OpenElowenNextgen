package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.network.chat.Component;

public class EventSetTitle extends com.elowen.events.api.events.callables.EventCancellable {
   private EventType C;
   private Component g;

   public EventSetTitle(EventType var1, Component var2) {
      this.C = var1;
      this.g = var2;
   }

   public EventType j$f() {
      return this.C;
   }

   public Component J$Component() {
      return this.g;
   }

   public void P(EventType var1) {
      this.C = var1;
   }

   public void N(Component var1) {
      this.g = var1;
   }
}
