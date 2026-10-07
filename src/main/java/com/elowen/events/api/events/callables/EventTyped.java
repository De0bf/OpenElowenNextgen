package com.elowen.events.api.events.callables;

import com.elowen.events.api.events.Typed;
import com.elowen.events.api.events.Event;
import com.elowen.values.HasValue;

public abstract class EventTyped implements Event, Typed {
   private final byte O;

   protected EventTyped(byte var1) {
      int[] var10000 = EventCancellable.u$ArrI();
      super();
      int[] var2 = var10000;
      this.O = var1;
      if (var2 == null) {
         HasValue.d(HasValue.X$Z());
      }
   }

   @Override
   public byte Z() {
      return this.O;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
