package com.elowen.modules.impl.test;

import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;

@ModuleInfo(R = "NoInterpolation", M = Category.TEST, a = "Removes player movement interpolation (transition) for testing")
public class NoInterpolation extends Module {
   private static boolean p = false;
   private static String P;

   @Override
   public void h$V() {
      p = true;
   }

   @Override
   public void q$V() {
      p = false;
   }

   public static boolean D$h() {
      return p;
   }

   static {
      d(null);
   }

   public static void d(String var0) {
      P = var0;
   }

   public static String U$String() {
      return P;
   }
}
