package com.elowen.events.impl;

public class EventClientChat extends com.elowen.events.api.events.callables.EventCancellable {
   private String m;
   private final String V;

   public EventClientChat(String var1) {
      this.m = var1;
      this.V = var1;
   }

   public String n$String() {
      return this.m;
   }

   public void m(String var1) {
      this.m = var1;
   }

   public String W$String() {
      return this.V;
   }
}
