package com.elowen.events.impl;

import net.minecraft.network.protocol.Packet;

public class EventServerSetPosition implements com.elowen.events.api.events.Event {
   private Packet z;

   public Packet e() {
      return this.z;
   }

   public void G(Packet var1) {
      this.z = var1;
   }

   public EventServerSetPosition(Packet var1) {
      this.z = var1;
   }
}
