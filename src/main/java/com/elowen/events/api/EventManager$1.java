package com.elowen.events.api;

import com.elowen.values.HasValue;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

class EventManager$1 extends CopyOnWriteArrayList {
   private static final long serialVersionUID = 666L;
   final EventManager$MethodData B;
   final EventManager L;

   EventManager$1(EventManager var1, EventManager$MethodData var2) {
      this.B = var2;
      EventManager.j$ArrString();
      Objects.requireNonNull(var1);
      this.L = var1;
      super();
      this.add(this.B);
      if (HasValue.X$Z()) {
         EventManager.E(new String[3]);
      }
   }
}
