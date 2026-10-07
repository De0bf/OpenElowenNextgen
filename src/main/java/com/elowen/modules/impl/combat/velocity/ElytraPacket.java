package com.elowen.modules.impl.combat.velocity;

import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;

public class ElytraPacket implements VelocityMode {
   private Velocity x;
   private static final String a;

   @Override
   public void C(Velocity var1) {
      this.x = var1;
   }

   @Override
   public void E(EventMoveInput var1) {
   }

   @Override
   public void J(EventPacket var1) {
   }

   @Override
   public void b(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.x != null && this.x.i$MC().player != null) {
         if (this.x.i$MC().player.hurtTime <= 1) {
            this.x.i$MC().getConnection().send(new ServerboundPlayerCommandPacket(this.x.i$MC().player, Action.START_FALL_FLYING));
         }
      }
   }

   @Override
   public void Z(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.x != null) {
         this.x.X(a);
      }
   }

   @Override
   public void c$V() {
   }

   @Override
   public void z$V() {
   }

   @Override
   public void B(com.elowen.events.impl.EventMotion var1) {
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
      a = "ElytraPacket";
   }
}
