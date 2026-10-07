package com.elowen.events.impl;

import net.minecraft.world.entity.Entity;

public class EventEntityJoinWorld implements com.elowen.events.api.events.Event {
   private final Entity k;

   public EventEntityJoinWorld(Entity var1) {
      this.k = var1;
   }

   public Entity I() {
      return this.k;
   }
}
