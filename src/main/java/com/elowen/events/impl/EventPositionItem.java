package com.elowen.events.impl;

import net.minecraft.network.protocol.Packet;

public class EventPositionItem extends com.elowen.events.api.events.callables.EventCancellable {
   private Packet A;

   public Packet e() {
      return this.A;
   }

   public void g(Packet var1) {
      this.A = var1;
   }

   public EventPositionItem(Packet var1) {
      this.A = var1;
   }
}
