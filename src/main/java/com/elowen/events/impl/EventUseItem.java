package com.elowen.events.impl;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class EventUseItem extends com.elowen.events.api.events.callables.EventCancellable {
   private final InteractionHand s;
   private final ItemStack e;
   private final boolean c;

   public InteractionHand D() {
      return this.s;
   }

   public ItemStack h$ItemStack() {
      return this.e;
   }

   public boolean C() {
      return this.c;
   }

   public EventUseItem(InteractionHand var1, ItemStack var2) {
      this(var1, var2, false);
   }

   public EventUseItem(InteractionHand var1, ItemStack var2, boolean var3) {
      this.s = var1;
      this.e = var2;
      this.c = var3;
   }
}
