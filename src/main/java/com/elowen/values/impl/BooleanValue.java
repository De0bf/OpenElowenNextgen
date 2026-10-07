package com.elowen.values.impl;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanValue extends Value {
   private final boolean w;
   private final Consumer M;
   public boolean r;
   private static int N;

   public BooleanValue(HasValue var1, String var2, boolean var3, Consumer var4, Supplier var5) {
      super(var1, var2, var5);
      this.M = var4;
      this.r = this.w = var3;
   }

   @Override
   public ValueType J$H() {
      return ValueType.BOOLEAN;
   }

   @Override
   public BooleanValue f$O() {
      return this;
   }

   public boolean E$Z() {
      return this.w;
   }

   public boolean w() {
      return this.r;
   }

   public void P(boolean var1) {
      int var10000 = G$I();
      this.r = var1;
      int var2 = var10000;
      Consumer var3 = this.M;
      if (var2 == 0) {
         if (this.M == null) {
            return;
         }

         var3 = this.M;
      }

      var3.accept(this);
   }

   public static void Q(int var0) {
      N = var0;
   }

   public static int y$I() {
      return N;
   }

   public static int G$I() {
      int var0 = y$I();
      return var0 == 0 ? 64 : 0;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (y$I() == 0) {
         Q(36);
      }
   }
}
