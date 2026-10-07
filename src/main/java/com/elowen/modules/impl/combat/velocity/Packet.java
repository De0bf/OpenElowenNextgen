package com.elowen.modules.impl.combat.velocity;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.PlayerUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;

public class Packet implements VelocityMode {
   private Velocity a;
   private static final String b;

   @Override
   public void C(Velocity var1) {
      this.a = var1;
   }

   @Override
   public void J(EventPacket var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.a.i$MC().player != null) {
         if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE && var1.R$Packet() instanceof ClientboundSetEntityMotionPacket var3 && var3.id() == PlayerUtils.S$I()) {
            Elowen.U++;
            PacketUtils.c(
               new Pos(this.a.i$MC().player.getX() + 214748.0, this.a.i$MC().player.getY(), this.a.i$MC().player.getZ() + 214748.0, this.a.i$MC().player.onGround(), false)
            );
            Elowen.U++;
         }
      }
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void Z(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.a != null) {
         this.a.X(b);
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
      b = "Packet";
   }
}
