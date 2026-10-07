package com.elowen.modules.impl.combat.velocity;

import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.utils.PlayerUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.phys.Vec3;

public class Set implements VelocityMode {
   private Velocity V;

   @Override
   public void C(Velocity var1) {
      this.V = var1;
   }

   @Override
   public void J(EventPacket var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.V.i$MC().player != null) {
         if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE && var1.R$Packet() instanceof ClientboundSetEntityMotionPacket var3 && var3.id() == PlayerUtils.S$I()) {
            Vec3 var8 = var3.movement();
            float var5 = this.V.k() / 100.0F;
            float var6 = this.V.g$F() / 100.0F;
            Vec3 var7 = new Vec3(var8.x * var5, var8.y * var6, var8.z * var5);
            var1.S(new ClientboundSetEntityMotionPacket(var3.id(), var7));
         }
      }
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void Z(EventTick var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.V != null) {
         this.V.X((int)this.V.k() + " " + (int)this.V.g$F());
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
}
