package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.network.protocol.Packet;

public class EventGlobalPacket extends com.elowen.events.api.events.callables.EventCancellable {
   private final EventType p;
   private Packet t;

   public EventType f$f() {
      return this.p;
   }

   public Packet g$Packet() {
      return this.t;
   }

   public void g(Packet var1) {
      this.t = var1;
   }

   public EventGlobalPacket(EventType var1, Packet var2) {
      this.p = var1;
      this.t = var2;
   }
}
