package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.network.protocol.Packet;

public class EventPacket extends com.elowen.events.api.events.callables.EventCancellable {
   private final EventType J;
   private Packet k;
   private static com.elowen.values.HasValue[] T;

   public EventType M() {
      return this.J;
   }

   public Packet R$Packet() {
      return this.k;
   }

   public void S(Packet var1) {
      this.k = var1;
   }

   public EventPacket(EventType var1, Packet var2) {
      this.J = var1;
      this.k = var2;
   }

   public static void J(com.elowen.values.HasValue[] var0) {
      T = var0;
   }

   public static com.elowen.values.HasValue[] C() {
      return T;
   }

   static {
      if (C() != null) {
         J(new com.elowen.values.HasValue[4]);
      }
   }
}
