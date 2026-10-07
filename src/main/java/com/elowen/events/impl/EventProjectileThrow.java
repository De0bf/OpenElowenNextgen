package com.elowen.events.impl;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EventProjectileThrow implements com.elowen.events.api.events.Event {
   private final Level c;
   private final Player O;
   private final ItemStack E;
   private final InteractionHand f;
   private boolean a;

   public EventProjectileThrow(Level var1, Player var2, ItemStack var3, InteractionHand var4) {
      this.c = var1;
      this.O = var2;
      this.E = var3;
      this.f = var4;
   }

   public Level n$Level() {
      return this.c;
   }

   public Player A$Player() {
      return this.O;
   }

   public ItemStack l$ItemStack() {
      return this.E;
   }

   public InteractionHand C() {
      return this.f;
   }

   public boolean V() {
      return this.a;
   }

   public void t(boolean var1) {
      this.a = var1;
   }
}
