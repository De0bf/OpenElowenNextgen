package com.elowen.modules.impl.combat.velocity;

import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;

public class JumpReset implements VelocityMode {
   private Velocity Y;
   private static final String[] a = new String[]{"Jump Reset", "Jump Reset"};
   @Override
   public void C(Velocity var1) {
      this.Y = var1;
   }

   @Override
   public void J(EventPacket var1) {
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void B(com.elowen.events.impl.EventMotion var1) {
   }

   @Override
   public void Z(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.Y != null) {
         this.Y.X("Jump Reset");
      }
   }

   @Override
   public void E(EventMoveInput var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.Y != null && this.Y.i$MC().player != null) {
         if (this.Y.i$MC().player.hurtTime == 9 && this.Y.i$MC().player.onGround()) {
            var1.A(true);
         }
      }
   }

   @Override
   public void c$V() {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      if (this.Y != null) {
         this.Y.X("Jump Reset");
      }
   }

   @Override
   public void z$V() {
   }

   @Override
   public boolean z$Z() {
      return false;
   }

   @Override
   public boolean X$Z() {
      return false;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
