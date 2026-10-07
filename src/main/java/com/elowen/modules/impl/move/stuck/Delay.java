package com.elowen.modules.impl.move.stuck;

import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.PacketUtils;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;

public class Delay implements StuckMode {
   private Stuck V;
   private int k = 0;
   private Packet x;
   private boolean U = false;
   private final Queue R = new ConcurrentLinkedQueue();
   private float P;
   private float e;

   @Override
   public void k(Stuck var1) {
      this.V = var1;
   }

   @Override
   public void G$V() {
      this.k = 0;
      int[] var10000 = SkipTicks.v$ArrI();
      this.x = null;
      this.U = false;
      int[] var1 = var10000;
      Delay var2 = this;
      if (var1 == null) {
         if (this.V.U$Minecraft().player == null) {
            return;
         }

         this.P = this.V.U$Minecraft().player.getYRot();
         var2 = this;
      }

      var2.e = this.V.U$Minecraft().player.getXRot();
   }

   @Override
   public void Y() {
      this.k = 0;
      this.x = null;
      this.U = false;
      this.R.clear();
   }

   @Override
   public void b(com.elowen.events.impl.EventMotion var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.V.U$Minecraft().player != null) {
         if (var1.Q() == EventType.POST && this.U) {
            PacketUtils.c(
               new Pos(
                  this.V.U$Minecraft().player.getX() + 1337.0,
                  this.V.U$Minecraft().player.getY(),
                  this.V.U$Minecraft().player.getZ() + 1337.0,
                  this.V.U$Minecraft().player.onGround(),
                  false
               )
            );
            if (!this.R.isEmpty()) {
               PacketUtils.c((Packet)this.R.poll());
            }

            this.k = 3;
            this.U = false;
         }
      }
   }

   @Override
   public void z(com.elowen.events.impl.EventMoveInput var1) {
      var1.b(0.0F);
      var1.m(0.0F);
      var1.A(false);
      var1.v(false);
   }

   @Override
   public void E(com.elowen.events.impl.EventRespawn var1) {
      this.k = 3;
      this.V.M(false);
   }

   @Override
   public void V(EventPacket var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.V.U$Minecraft().player != null) {
         Packet var3 = var1.R$Packet();
         if (var3 instanceof ServerboundMovePlayerPacket) {
            var1.c(true);
         }

         if (var3 instanceof ServerboundPongPacket) {
            this.R.offer((ServerboundPongPacket)var3);
            var1.c(true);
         }

         if (var3 instanceof ServerboundUseItemPacket || var3 instanceof ServerboundPlayerActionPacket) {
            this.x = var3;
            this.k = 1;
            var1.c(true);
         }

         if (var3 instanceof ClientboundPlayerPositionPacket) {
            if (!this.R.isEmpty()) {
               PacketUtils.c((Packet)this.R.poll());
            }

            this.k = 3;
            this.V.M(false);
         }
      }
   }

   public boolean H() {
      int[] var1 = SkipTicks.v$ArrI();
      return this.k == 3;
   }

   public void g(boolean var1) {
      this.U = var1;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
