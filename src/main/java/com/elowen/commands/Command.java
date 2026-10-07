package com.elowen.commands;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.HasValue;

public abstract class Command {
   private String R;
   private String Z;
   private String[] J;
   private static String M;

   protected void c$V() {
      String var1 = V();
      if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
         CommandInfo var2 = this.getClass().getAnnotation(CommandInfo.class);
         this.R = var2.V();
         this.Z = var2.Z();
         this.J = var2.C();
      }
   }

   public abstract void l(String[] var1);

   public void m(String[] var1, String var2) {
      this.l(var1);
   }

   public abstract String[] X(String[] var1);

   public Command(String var1, String var2, String[] var3) {
      V();
      super();
      this.R = var1;
      this.Z = var2;
      this.J = var3;
      if (HasValue.X$Z()) {
         y("iOh6zb");
      }
   }

   public Command() {
   }

   public String I() {
      return this.R;
   }

   public String u$String() {
      return this.Z;
   }

   public String[] u$ArrString() {
      return this.J;
   }

   public static void y(String var0) {
      M = var0;
   }

   public static String V() {
      return M;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (V() == null) {
         y("lQX9B");
      }
   }
}
