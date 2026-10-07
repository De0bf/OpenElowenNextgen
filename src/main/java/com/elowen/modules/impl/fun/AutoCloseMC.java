package com.elowen.modules.impl.fun;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.HasValue;

@ModuleInfo(R = "GetOP", a = "Get OP permission on the server.", M = Category.FUN)
public class AutoCloseMC extends Module {
   public static boolean Z;
   private static int[] r;
   private static final String b;

   @Override
   public void h$V() {
      int[] var10000 = P();
      super.h$V();
      int[] var1 = var10000;
      G.execute(AutoCloseMC::deobfLambda$onEnable$0);
      if (var1 == null) {
         HasValue.d(HasValue.X$Z());
      }
   }

   private static void deobfLambda$onEnable$0() {
      Z = true;
      throw new StackOverflowError(b);
   }

   static {
      b = "傻逼操你妈获取你妈逼OP权限";
      Z = false;
   }

   public static void g(int[] var0) {
      r = var0;
   }

   public static int[] P() {
      return r;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
