package com.elowen.exceptions;

public class NoSuchModuleException extends RuntimeException {
   public static Object n;
   public static Object I;
   private static int U;

   public static void u(int var0) {
      U = var0;
   }

   public static int O() {
      return U;
   }

   public static int Y() {
      int var0 = O();
      return var0 == 0 ? 13 : 0;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
      if (O() == 0) {
         u(63);
      }
   }
}
