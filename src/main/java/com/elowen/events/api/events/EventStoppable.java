package com.elowen.events.api.events;

public abstract class EventStoppable implements Event {
   private boolean X;
   private static String b;

   protected EventStoppable() {
   }

   public void c$V() {
      this.X = true;
   }

   public boolean d$Z() {
      return this.X;
   }

   public static void R(String var0) {
      b = var0;
   }

   public static String t() {
      return b;
   }

   static {
      if (t() != null) {
         R("nBkojb");
      }
   }
}
