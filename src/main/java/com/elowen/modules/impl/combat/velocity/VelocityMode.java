package com.elowen.modules.impl.combat.velocity;

import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventStuckInBlock;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.impl.combat.Velocity;

public interface VelocityMode {
   void J(EventPacket var1);

   void b(EventTick var1);

   void Z(EventTick var1);

   void E(EventMoveInput var1);

   void B(com.elowen.events.impl.EventMotion var1);

   void c$V();

   void z$V();

   boolean z$Z();

   boolean X$Z();

   void C(Velocity var1);

   default void f(EventStuckInBlock var1) {
   }
}
