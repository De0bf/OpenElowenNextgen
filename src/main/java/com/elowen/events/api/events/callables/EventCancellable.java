package com.elowen.events.api.events.callables;

import com.elowen.events.api.events.Event;
import com.elowen.events.api.events.Cancellable;

public abstract class EventCancellable implements Event, Cancellable {
   public boolean D;
   private static int[] N;

   protected EventCancellable() {
   }

   @Override
   public boolean c$Z() {
      return this.D;
   }

   @Override
   public void c(boolean var1) {
      this.D = var1;
   }

   public static void p(int[] var0) {
      N = var0;
   }

   public static int[] u$ArrI() {
      return N;
   }

   static {
      if (u$ArrI() == null) {
         p(new int[3]);
      }
   }
}
