package com.elowen.events.impl;

import net.minecraft.network.chat.Component;

public class EventRenderScoreboard implements com.elowen.events.api.events.Event {
   private Component O;

   public EventRenderScoreboard(Component var1) {
      this.O = var1;
   }

   public Component u$Component() {
      return this.O;
   }

   public void G(Component var1) {
      this.O = var1;
   }
}
