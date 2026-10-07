package com.elowen.values;

import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.StringValue;
import com.elowen.values.impl.ModeValue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ValueBuilder {
   private final HasValue h;
   private final String c;
   private ValueType R;
   private Consumer l;
   private Supplier V;
   private boolean v;
   private float M;
   private float z;
   private float G;
   private float u;
   private String[] W;
   private int D;
   private String y;
   private static String T;
   private static final String[] a = new String[]{"Value type is not set", "Value type is not string", "Unknown value type", "Value type is not mode", "Value type is not mode", "Modes are not set", "Default string value is not set", "Value type is not float", "Value type is not float", "Value type is not boolean"};
   private ValueBuilder(HasValue var1, String var2) {
      this.h = var1;
      this.c = var2;
   }

   public static ValueBuilder m(HasValue var0, String var1) {
      return new ValueBuilder(var0, var1);
   }

   public ValueBuilder W(ValueType var1) {
      this.R = var1;
      return this;
   }

   public ValueBuilder h(boolean var1) {
      int var2 = Value.a$I();
      ValueType var10000 = this.R;
      if (var2 != 0) {
         if (this.R == null) {
            this.W(ValueType.BOOLEAN);
         }

         if (var2 == 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.BOOLEAN) {
         throw new IllegalStateException("Value type is not boolean");
      }

      this.v = var1;
      return this;
   }

   public ValueBuilder d(float var1) {
      int var2 = Value.x();
      ValueType var10000 = this.R;
      if (var2 == 0) {
         if (this.R == null) {
            this.W(ValueType.FLOAT);
         }

         if (var2 != 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.FLOAT) {
         throw new IllegalStateException("Value type is not float");
      }

      this.M = var1;
      return this;
   }

   public ValueBuilder w(float var1) {
      int var2 = Value.x();
      ValueType var10000 = this.R;
      if (var2 == 0) {
         if (this.R == null) {
            this.W(ValueType.FLOAT);
         }

         if (var2 != 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.FLOAT) {
         throw new IllegalStateException("Value type is not float");
      }

      this.z = var1;
      return this;
   }

   public ValueBuilder M(float var1) {
      int var2 = Value.a$I();
      ValueType var10000 = this.R;
      if (var2 != 0) {
         if (this.R == null) {
            this.W(ValueType.FLOAT);
         }

         if (var2 == 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.FLOAT) {
         throw new IllegalStateException("Value type is not float");
      }

      this.G = var1;
      return this;
   }

   public ValueBuilder V(float var1) {
      int var2 = Value.x();
      ValueType var10000 = this.R;
      if (var2 == 0) {
         if (this.R == null) {
            this.W(ValueType.FLOAT);
         }

         if (var2 != 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.FLOAT) {
         throw new IllegalStateException("Value type is not float");
      }

      this.u = var1;
      return this;
   }

   public ValueBuilder W(String[] var1) {
      int var2 = Value.a$I();
      ValueType var10000 = this.R;
      if (var2 != 0) {
         if (this.R == null) {
            this.W(ValueType.MODE);
         }

         if (var2 == 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.MODE) {
         throw new IllegalStateException("Value type is not mode");
      }

      this.W = var1;
      return this;
   }

   public ValueBuilder m(int var1) {
      int var2 = Value.a$I();
      ValueType var10000 = this.R;
      if (var2 != 0) {
         if (this.R == null) {
            this.W(ValueType.MODE);
         }

         if (var2 == 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.MODE) {
         throw new IllegalStateException("Value type is not mode");
      }

      this.D = var1;
      return this;
   }

   public ValueBuilder J(String var1) {
      int var2 = Value.x();
      ValueType var10000 = this.R;
      if (var2 == 0) {
         if (this.R == null) {
            this.W(ValueType.STRING);
         }

         if (var2 != 0) {
            return this;
         }

         var10000 = this.R;
      }

      if (var10000 != ValueType.STRING) {
         throw new IllegalStateException("Value type is not string");
      }

      this.y = var1;
      return this;
   }

   public ValueBuilder z(Consumer var1) {
      this.l = var1;
      return this;
   }

   public ValueBuilder l(Supplier var1) {
      this.V = var1;
      return this;
   }

   public Value f$K() {
      int var1 = Value.a$I();
      if (this.R == null) {
         throw new IllegalStateException("Value type is not set");
      }

      switch (com.elowen.values.ValueBuilder$1.c[this.R.ordinal()]) {
         case 1:
            return new BooleanValue(this.h, this.c, this.v, this.l, this.V);
         case 2:
            return new FloatValue(this.h, this.c, this.M, this.z, this.G, this.u, this.l, this.V);
         case 3:
            if (this.W == null) {
               throw new IllegalStateException("Modes are not set");
            }

            return new ModeValue(this.h, this.c, this.W, this.D, this.l, this.V);
         case 4:
            if (this.y == null) {
               throw new IllegalStateException("Default string value is not set");
            }

            StringValue var10000 = new StringValue(this.h, this.c, this.y, this.l, this.V);
            if (!HasValue.x()) {
               Value.d(++var1);
            }

            return var10000;
         default:
            throw new IllegalStateException("Unknown value type");
      }
   }

   public static void p(String var0) {
      T = var0;
   }

   public static String q$String() {
      return T;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }

   static {
      p("YOtAN");
   }
}
