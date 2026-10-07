package com.elowen.events.impl;

import net.minecraft.world.entity.Entity;

public class EventRayTrace implements com.elowen.events.api.events.Event {
   public Entity D;
   public float b;
   public float i;

   public Entity F() {
      return this.D;
   }

   public float M() {
      return this.b;
   }

   public float h$F() {
      return this.i;
   }

   public void o(Entity var1) {
      this.D = var1;
   }

   public void K(float var1) {
      this.b = var1;
   }

   public void O(float var1) {
      this.i = var1;
   }

   public EventRayTrace(Entity var1, float var2, float var3) {
      this.D = var1;
      this.b = var2;
      this.i = var3;
   }
}
