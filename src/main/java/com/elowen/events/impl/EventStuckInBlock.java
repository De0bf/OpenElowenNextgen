package com.elowen.events.impl;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EventStuckInBlock extends com.elowen.events.api.events.callables.EventCancellable {
   private BlockState J;
   private Vec3 Y;
   private static final String[] a = new String[]{"EventStuckInBlock(state=", ", stuckSpeedMultiplier="};
   public BlockState k() {
      return this.J;
   }

   public Vec3 O() {
      return this.Y;
   }

   public void O(BlockState var1) {
      this.J = var1;
   }

   public void f(Vec3 var1) {
      this.Y = var1;
   }

   @Override
   public String toString() {
      String var10000 = String.valueOf(this.k());
      String var10001 = String.valueOf(this.O());
      String[] var1 = a;
      return "EventStuckInBlock(state=" + var10000 + ", stuckSpeedMultiplier=" + var10001 + ")";
   }

   @Override
   public boolean equals(Object var1) {
      com.elowen.values.HasValue[] var2 = EventPacket.C();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof EventStuckInBlock var3) {
         ;
      }

      return false;
   }

   protected boolean z(Object var1) {
      return var1 instanceof EventStuckInBlock;
   }

   @Override
   public int hashCode() {
      com.elowen.values.HasValue[] var1;
      int var7;
      int var9;
      label31: {
         com.elowen.values.HasValue[] var10000 = EventPacket.C();
         byte var2 = 59;
         var1 = var10000;
         byte var3 = 1;
         BlockState var4 = this.k();
         var7 = var3 * 59;
         BlockState var10001 = var4;
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
      Vec3 var5 = this.O();
      var7 = var6 * 59;
      Vec3 var10 = var5;
      if (var1 == null) {
         if (var5 == null) {
            return var7 + 43;
         }

         var10 = var5;
      }

      return var7 + var10.hashCode();
   }

   public EventStuckInBlock(BlockState var1, Vec3 var2) {
      this.J = var1;
      this.Y = var2;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
