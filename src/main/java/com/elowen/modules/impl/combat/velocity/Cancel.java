package com.elowen.modules.impl.combat.velocity;

import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.utils.PlayerUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;

public class Cancel implements VelocityMode {
   private Velocity g;
   private static final String a;

   @Override
   public void C(Velocity var1) {
      this.g = var1;
   }

   @Override
   public void J(EventPacket var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.g.i$MC().player != null) {
         if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE && var1.R$Packet() instanceof ClientboundSetEntityMotionPacket var3 && var3.id() == PlayerUtils.S$I()) {
            var1.c(true);
         }
      }
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void Z(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.g != null) {
         this.g.X(a);
      }
   }

   @Override
   public void E(EventMoveInput var1) {
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
      a = "Cancel";
   }
}
