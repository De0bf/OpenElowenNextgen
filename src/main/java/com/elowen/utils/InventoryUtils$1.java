package com.elowen.utils;

import net.minecraft.world.entity.EquipmentSlot;

// $VF: synthetic class
class InventoryUtils$1 {
   static final int[] B = new int[EquipmentSlot.values().length];

   static {
      try {
         B[EquipmentSlot.HEAD.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         B[EquipmentSlot.CHEST.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         B[EquipmentSlot.LEGS.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         B[EquipmentSlot.FEET.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
