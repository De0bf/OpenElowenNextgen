package com.elowen.events.api;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.HasValue;
import java.lang.reflect.Method;
import java.util.Objects;

final class EventManager$MethodData {
   final Object S;
   final Method A;
   private final byte n;

   public EventManager$MethodData(Object var1, Method var2, byte var3) {
      this.S = var1;
      this.A = var2;
      this.n = var3;
   }

   @Override
   public boolean equals(Object var1) {
      String[] var2 = EventManager.j$ArrString();
      if (this == var1) {
         return true;
      }

      if (var1 instanceof EventManager$MethodData var3) {
         ;
      }

      return false;
   }

   @Override
   public int hashCode() {
      String[] var1 = EventManager.j$ArrString();
      int var10000 = Objects.hash(this.S, this.A, this.n);
      HasValue.d(HasValue.x());
      return var10000;
   }

   public Object S$Object() {
      return this.S;
   }

   public Method O() {
      return this.A;
   }

   public byte L() {
      return this.n;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
