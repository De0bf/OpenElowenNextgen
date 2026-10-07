package com.elowen.values.impl;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModeValue extends Value {
   private final String[] T;
   private final Consumer X;
   private int b;

   public ModeValue(HasValue var1, String var2, String[] var3, int var4, Consumer var5, Supplier var6) {
      super(var1, var2, var6);
      this.X = var5;
      this.T = var3;
      this.b = var4;
   }

   public boolean t(String var1) {
      return this.C().equalsIgnoreCase(var1);
   }

   @Override
   public ValueType J$H() {
      return ValueType.MODE;
   }

   @Override
   public ModeValue T$t() {
      return this;
   }

   public String C() {
      return this.T[this.b];
   }

   public void y(int var1) {
      int var10000 = BooleanValue.G$I();
      this.b = var1;
      int var2 = var10000;
      Consumer var3 = this.X;
      if (var2 == 0) {
         if (this.X == null) {
            return;
         }

         var3 = this.X;
      }

      var3.accept(this);
   }

   public String[] T$ArrString() {
      return this.T;
   }

   public Consumer Z() {
      return this.X;
   }

   public int N$t() {
      return this.b;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
