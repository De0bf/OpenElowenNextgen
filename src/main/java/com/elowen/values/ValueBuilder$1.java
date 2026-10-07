package com.elowen.values;

// $VF: synthetic class
class ValueBuilder$1 {
   static final int[] c = new int[ValueType.values().length];

   static {
      try {
         c[ValueType.BOOLEAN.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         c[ValueType.FLOAT.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         c[ValueType.MODE.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         c[ValueType.STRING.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
