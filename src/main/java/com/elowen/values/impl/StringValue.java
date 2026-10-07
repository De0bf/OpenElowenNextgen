package com.elowen.values.impl;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringValue extends Value {
   private final String b;
   private final Consumer d;
   private String X;

   public StringValue(HasValue var1, String var2, String var3, Consumer var4, Supplier var5) {
      super(var1, var2, var5);
      this.d = var4;
      int var10000 = BooleanValue.G$I();
      this.b = var3;
      int var6 = var10000;
      this.X = var3;
      if (!HasValue.x()) {
         BooleanValue.Q(++var6);
      }
   }

   @Override
   public ValueType J$H() {
      return ValueType.STRING;
   }

   @Override
   public StringValue N() {
      return this;
   }

   public void j(String var1) {
      int var10000 = BooleanValue.G$I();
      this.X = var1;
      int var2 = var10000;
      Consumer var3 = this.d;
      if (var2 == 0) {
         if (this.d == null) {
            return;
         }

         var3 = this.d;
      }

      var3.accept(this);
   }

   public String y$String() {
      return this.b;
   }

   public Consumer b$Consumer() {
      return this.d;
   }

   public String R$String() {
      return this.X;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
