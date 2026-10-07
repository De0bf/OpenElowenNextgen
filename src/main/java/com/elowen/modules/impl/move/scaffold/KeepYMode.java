package com.elowen.modules.impl.move.scaffold;

import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventClick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import net.minecraft.core.BlockPos;

public class KeepYMode implements ScaffoldMode {
   private final Scaffold L;

   public KeepYMode(Scaffold var1) {
      this.L = var1;
   }

   @Override
   public Scaffold l$Scaffold() {
      return this.L;
   }

   @Override
   public void T$V() {
   }

   @Override
   public void F() {
   }

   private boolean n$Z() {
      String[] var1 = TellyBridgeMode.m();
      return this.L.D$M().player != null && this.L.D$M().player.getDeltaMovement().horizontalDistance() > 0.01;
   }

   @Override
   public void Y(com.elowen.events.impl.EventTick var1, BlockPos var2) {
      String[] var3 = TellyBridgeMode.m();
      if (this.L.D$M().player != null) {
         this.L.D$M().options.keyJump.setDown(this.n$Z() || this.L.s$Z());
         Vector2f var4 = this.L.x$H();
         this.L.j = this.L.r().Z(this.L.j, var4, this.L.X$F(), this.L);
         this.L.J.D(this.L.j.H, this.L.j.E);
         this.L.w(var2);
      }
   }

   @Override
   public void v(EventClick var1, BlockPos var2) {
      this.L.R(var2);
   }

   @Override
   public void a(EventPacket var1) {
      this.L.E(var1);
   }

   @Override
   public void q(EventUpdate var1) {
      this.L.p(var1);
   }

   @Override
   public void z(EventMoveInput var1) {
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
