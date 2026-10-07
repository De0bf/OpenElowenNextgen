package com.elowen.values.impl;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.MathUtils;
import com.elowen.values.ValueType;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatValue extends Value {
   private final float K;
   private final float h;
   private final float V;
   private final float r;
   private final Consumer P;
   private float m;
   private static int[] Z;

   public FloatValue(HasValue var1, String var2, float var3, float var4, float var5, float var6, Consumer var7, Supplier var8) {
      int var10000 = BooleanValue.y$I();
      super(var1, var2, var8);
      int var9 = var10000;
      this.P = var7;
      this.m = this.K = var3;
      this.h = var4;
      this.V = var5;
      this.r = var6;
      if (var9 == 0) {
         HasValue.d(HasValue.x());
      }
   }

   @Override
   public ValueType J$H() {
      return ValueType.FLOAT;
   }

   @Override
   public FloatValue L() {
      return this;
   }

   public void I(float var1) {
      int var10000 = BooleanValue.y$I();
      this.m = MathUtils.s(var1, this.h, this.V);
      int var2 = var10000;
      Consumer var3 = this.P;
      if (var2 != 0) {
         if (this.P == null) {
            return;
         }

         var3 = this.P;
      }

      var3.accept(this);
   }

   public float E$F() {
      return this.K;
   }

   public float y$F() {
      return this.h;
   }

   public float h$F() {
      return this.V;
   }

   public float e() {
      return this.r;
   }

   public Consumer b$Consumer() {
      return this.P;
   }

   public float o$F() {
      return this.m;
   }

   public static void G(int[] var0) {
      Z = var0;
   }

   public static int[] R$ArrI() {
      return Z;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (R$ArrI() != null) {
         G(new int[2]);
      }
   }
}
