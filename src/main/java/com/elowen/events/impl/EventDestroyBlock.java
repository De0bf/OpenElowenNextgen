package com.elowen.events.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class EventDestroyBlock implements com.elowen.events.api.events.Event {
   private final BlockPos s;
   private final Direction J;

   public EventDestroyBlock(BlockPos var1, Direction var2) {
      this.s = var1;
      this.J = var2;
   }

   public BlockPos O() {
      return this.s;
   }

   public Direction n$Direction() {
      return this.J;
   }
}
