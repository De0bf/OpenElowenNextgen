package com.elowen.files.impl;

import com.elowen.values.ValueType;

// $VF: synthetic class
class InvSlotsFile$1 {
   static final int[] j = new int[ValueType.values().length];

   static {
      try {
         j[ValueType.BOOLEAN.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         j[ValueType.FLOAT.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         j[ValueType.STRING.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         j[ValueType.MODE.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
