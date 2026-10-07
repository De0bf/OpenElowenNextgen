package com.elowen.config;

import com.elowen.values.ValueType;

// $VF: synthetic class
class ClientConfig$1 {
   static final int[] p = new int[ValueType.values().length];

   static {
      try {
         p[ValueType.BOOLEAN.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         p[ValueType.FLOAT.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         p[ValueType.STRING.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         p[ValueType.MODE.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
