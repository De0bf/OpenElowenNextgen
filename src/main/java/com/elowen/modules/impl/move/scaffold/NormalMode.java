package com.elowen.modules.impl.move.scaffold;

import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventClick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;
import net.minecraft.core.BlockPos;

public class NormalMode implements ScaffoldMode {
   private final Scaffold w;

   public NormalMode(Scaffold var1) {
      TellyBridgeMode.m();
      this.w = var1;
      if (!HasValue.x()) {
         TellyBridgeMode.J(new String[5]);
      }
   }

   @Override
   public Scaffold l$Scaffold() {
      return this.w;
   }

   @Override
   public void T$V() {
   }

   @Override
   public void F() {
   }

   private boolean T$Z() {
      String[] var1 = TellyBridgeMode.m();
      return this.w.D$M().player != null && this.w.D$M().player.getDeltaMovement().horizontalDistance() > 0.01;
   }

   @Override
   public void Y(com.elowen.events.impl.EventTick var1, BlockPos var2) {
      String[] var3 = TellyBridgeMode.m();
      if (this.w.D$M().player != null) {
         if (this.w.f.w()) {
            this.w.D$M().options.keyShift.setDown(this.w.D$M().player.onGround() && Scaffold.c(0.3F));
         }

         if (this.w.D$M().player.onGround() && this.T$Z() && this.w.s$Z() && this.w.i.r && !this.w.D$M().options.keyShift.isDown()) {
            this.w.j.Z(RotationUtils.i(this.w.v$F(), this.w.j.H, this.w.c$F()));
            this.w.J.D(this.w.j.H, this.w.j.E);
         } else {
            Vector2f var4 = this.w.x$H();
            this.w.j = this.w.r().Z(this.w.j, var4, this.w.X$F(), this.w);
            this.w.J.D(this.w.j.H, this.w.j.E);
            this.w.w(var2);
         }
      }
   }

   @Override
   public void v(EventClick var1, BlockPos var2) {
      this.w.R(var2);
   }

   @Override
   public void a(EventPacket var1) {
      this.w.E(var1);
   }

   @Override
   public void q(EventUpdate var1) {
      this.w.p(var1);
   }

   @Override
   public void z(EventMoveInput var1) {
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
