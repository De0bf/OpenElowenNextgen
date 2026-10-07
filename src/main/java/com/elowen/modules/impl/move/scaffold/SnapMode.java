package com.elowen.modules.impl.move.scaffold;

import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventClick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.RayTraceUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.apache.commons.lang3.RandomUtils;

public class SnapMode implements ScaffoldMode {
   private final Scaffold r;

   public SnapMode(Scaffold var1) {
      this.r = var1;
   }

   @Override
   public Scaffold l$Scaffold() {
      return this.r;
   }

   @Override
   public void T$V() {
   }

   @Override
   public void F() {
   }

   @Override
   public void Y(com.elowen.events.impl.EventTick var1, BlockPos var2) {
      String[] var3 = TellyBridgeMode.m();
      if (this.r.D$M().player != null) {
         if (this.r.f.w()) {
            this.r.D$M().options.keyShift.setDown(this.r.D$M().player.onGround() && Scaffold.c(0.3F));
         }

         if (this.r.D$M().options.keyUse.isDown() && var2 != null) {
            boolean var4 = false;
            HitResult var5 = RayTraceUtils.B(1.0F, this.r.j);
            if (var5.getType() == Type.BLOCK) {
               BlockHitResult var6 = (BlockHitResult)var5;
               if (var6.getBlockPos().equals(var2) && var6.getDirection() != Direction.UP) {
                  var4 = true;
               }
            }

            if (!var4) {
               this.r.j.Z(this.r.c$F() + RandomUtils.nextFloat(0.0F, 0.5F) - 0.25F);
            }

            this.r.j.Z(this.r.c$F() - 180.0F);
            this.r.J.D(this.r.j.H, this.r.j.E);
         }

         this.r.j.D(this.r.c$F(), this.r.a$F());
         this.r.J.D(this.r.j.H, this.r.j.E);
         this.r.w(var2);
      }
   }

   @Override
   public void v(EventClick var1, BlockPos var2) {
      this.r.R(var2);
   }

   @Override
   public void a(EventPacket var1) {
      this.r.E(var1);
   }

   @Override
   public void q(EventUpdate var1) {
      this.r.p(var1);
   }

   @Override
   public void z(EventMoveInput var1) {
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
