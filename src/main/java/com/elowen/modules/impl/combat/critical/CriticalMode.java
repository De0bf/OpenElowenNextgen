package com.elowen.modules.impl.combat.critical;

import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventAttack;
import com.elowen.events.impl.EventSprint;
import com.elowen.modules.impl.combat.Critical;

public interface CriticalMode {
   default void F() {
   }

   default void w() {
   }

   default void x() {
   }

   default void m(EventMoveInput var1) {
   }

   default void G(EventMoveInput var1) {
   }

   default void K(EventMotion var1) {
   }

   default void f(EventTick var1) {
   }

   default void T(EventAttack var1) {
   }

   default void P(EventSprint var1) {
   }

   void O(Critical var1);
}
