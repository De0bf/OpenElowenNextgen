package com.elowen.events.impl;

import net.minecraft.world.InteractionHand;

public class EventSwing extends com.elowen.events.api.events.callables.EventCancellable {
   private final InteractionHand E;

   public EventSwing(InteractionHand var1) {
      this.E = var1;
   }

   public InteractionHand G$InteractionHand() {
      return this.E;
   }
}
