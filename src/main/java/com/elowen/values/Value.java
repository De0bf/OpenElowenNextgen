package com.elowen.values;

import com.elowen.Elowen;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.StringValue;
import com.elowen.values.impl.ModeValue;
import java.util.function.Supplier;

public abstract class Value {
   private final HasValue a;
   private final String p;
   private final Supplier L;
   private static int I;

   protected Value(HasValue var1, String var2, Supplier var3) {
      this.a = var1;
      this.p = var2;
      this.L = var3;
      Elowen.S$Elowen().E$C().p(this);
   }

   public abstract ValueType J$H();

   public BooleanValue f$O() {
      throw new com.elowen.exceptions.BadValueTypeException();
   }

   public FloatValue L() {
      throw new com.elowen.exceptions.BadValueTypeException();
   }

   public StringValue N() {
      throw new com.elowen.exceptions.BadValueTypeException();
   }

   public ModeValue T$t() {
      throw new com.elowen.exceptions.BadValueTypeException();
   }

   public boolean c$Z() {
      int var1 = x();
      Object var10000 = this.L;
      if (var1 == 0) {
         if (this.L == null) {
            return true;
         }

         var10000 = this.L.get();
      }

      boolean var2 = (Boolean)var10000;
      return var1 != 0 ? var2 : var2;
   }

   public HasValue A$Q() {
      return this.a;
   }

   public String r() {
      return this.p;
   }

   public Supplier l$Supplier() {
      return this.L;
   }

   public static void d(int var0) {
      I = var0;
   }

   public static int x() {
      return I;
   }

   public static int a$I() {
      int var0 = x();
      return var0 == 0 ? 87 : 0;
   }

   private static com.elowen.exceptions.BadValueTypeException a(com.elowen.exceptions.BadValueTypeException var0) {
      return var0;
   }

   static {
      if (x() != 0) {
         d(12);
      }
   }
}
