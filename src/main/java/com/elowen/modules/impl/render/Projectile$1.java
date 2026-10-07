package com.elowen.modules.impl.render;

import net.minecraft.core.Direction;

// $VF: synthetic class
class Projectile$1 {
   static final int[] F = new int[Direction.values().length];

   static {
      try {
         F[Direction.UP.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         F[Direction.DOWN.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         F[Direction.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         F[Direction.NORTH.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         F[Direction.EAST.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }
   }
}
