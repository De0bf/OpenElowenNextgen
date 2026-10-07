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

public class TellyBridgeMode implements ScaffoldMode {
   private final Scaffold h;
   private static String[] t;

   public TellyBridgeMode(Scaffold var1) {
      this.h = var1;
   }

   @Override
   public Scaffold l$Scaffold() {
      return this.h;
   }

   @Override
   public void T$V() {
   }

   @Override
   public void F() {
   }

   private boolean g$Z() {
      String[] var1 = m();
      return this.h.D$M().player != null && this.h.D$M().player.getDeltaMovement().horizontalDistance() > 0.01;
   }

   @Override
   public void Y(com.elowen.events.impl.EventTick var1, BlockPos var2) {
      String[] var3 = m();
      if (this.h.D$M().player != null) {
         if (this.h.D$M().player.onGround() && this.g$Z()) {
            float var8 = this.h.c$F();
            Vector2f var5 = new Vector2f(var8, this.h.j.E);
            this.h.j = this.h.r().Z(this.h.j, var5, this.h.v$F(), this.h);
            this.h.J.D(this.h.j.H, this.h.j.E);
            boolean var6 = Math.abs(RotationUtils.e(this.h.j.H, var8)) < 1.0F;
            boolean var7 = Scaffold.c(0.1F);
            this.h.D$M().options.keyJump.setDown((var6 || var7) && (this.g$Z() || this.h.s$Z()));
         } else {
            this.h.D$M().options.keyJump.setDown(this.g$Z() || this.h.s$Z());
            Vector2f var4 = this.h.x$H();
            this.h.j = this.h.r().Z(this.h.j, var4, this.h.X$F(), this.h);
            this.h.J.D(this.h.j.H, this.h.j.E);
            this.h.w(var2);
         }
      }
   }

   @Override
   public void v(EventClick var1, BlockPos var2) {
      String[] var3 = m();
      if (this.h.al || this.h.V >= 1) {
         this.h.R(var2);
         HasValue.d(HasValue.x());
      }
   }

   @Override
   public void a(EventPacket var1) {
      this.h.E(var1);
   }

   @Override
   public void q(EventUpdate var1) {
      this.h.p(var1);
   }

   @Override
   public void z(EventMoveInput var1) {
   }

   public static void J(String[] var0) {
      t = var0;
   }

   public static String[] m() {
      return t;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (m() != null) {
         J(new String[1]);
      }
   }
}
