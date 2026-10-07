package com.elowen.events.impl;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class EventUpdateHeldItem implements com.elowen.events.api.events.Event {
   private final InteractionHand S;
   private ItemStack o;
   private static final String[] a = new String[]{"EventUpdateHeldItem(hand=", ", item="};
   public EventUpdateHeldItem(InteractionHand var1, ItemStack var2) {
      this.S = var1;
      this.o = var2;
   }

   public InteractionHand j$InteractionHand() {
      return this.S;
   }

   public ItemStack x() {
      return this.o;
   }

   public void w(ItemStack var1) {
      this.o = var1;
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventUpdateHeldItem var3) {
         ;
      }

      return false;
   }

   protected boolean n(Object var1) {
      return var1 instanceof EventUpdateHeldItem;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var1;
      int var7;
      int var9;
      label31: {
         com.elowen.values.HasValue[] var10000 = EventPacket.C();
         byte var2 = 59;
         byte var3 = 1;
         InteractionHand var4 = this.j$InteractionHand();
         var1 = var10000;
         var7 = var3 * 59;
         InteractionHand var10001 = var4;
         if (var1 == null) {
            if (var4 == null) {
               var9 = 43;
               break label31;
            }

            var10001 = var4;
         }

         var9 = var10001.hashCode();
      }

      int var6 = var7 + var9;
      ItemStack var5 = this.x();
      var7 = var6 * 59;
      ItemStack var10 = var5;
      if (var1 == null) {
         if (var5 == null) {
            return var7 + 43;
         }

         var10 = var5;
      }

      return var7 + var10.hashCode();
   }

   @Override
   public String toString() {
      String var10000 = String.valueOf(this.j$InteractionHand());
      String var10001 = String.valueOf(this.x());
      String[] var1 = a;
      return "EventUpdateHeldItem(hand=" + var10000 + ", item=" + var10001 + ")";
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
