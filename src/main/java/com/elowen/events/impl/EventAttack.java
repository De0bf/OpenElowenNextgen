package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.world.entity.Entity;

public class EventAttack extends com.elowen.events.api.events.callables.EventCancellable {
   private final Entity M;

   public EventAttack(Entity var1, EventType var2) {
      this.M = var1;
   }

   public Entity d$Entity() {
      return this.M;
   }
}
