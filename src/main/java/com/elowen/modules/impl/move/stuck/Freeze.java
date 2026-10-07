package com.elowen.modules.impl.move.stuck;

import com.elowen.events.impl.EventPlayerTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Stuck;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class Freeze implements StuckMode {
   private Stuck z;
   private double i;
   private double b;
   private double Q;

   @Override
   public void k(Stuck var1) {
      this.z = var1;
   }

   @Override
   public void G$V() {
      int[] var1 = SkipTicks.v$ArrI();
      if (this.z.U$Minecraft().player != null) {
         this.i = this.z.U$Minecraft().player.getX();
         this.b = this.z.U$Minecraft().player.getY();
         this.Q = this.z.U$Minecraft().player.getZ();
      }
   }

   @Override
   public void a(EventPlayerTick var1) {
      int[] var10000 = SkipTicks.v$ArrI();
      var1.c(true);
      int[] var2 = var10000;
      LocalPlayer var3 = this.z.U$Minecraft().player;
      if (var2 == null) {
         if (var3 == null) {
            return;
         }

         var3 = this.z.U$Minecraft().player;
      }

      var3.setPos(this.i, this.b, this.Q);
   }

   @Override
   public void z(com.elowen.events.impl.EventMoveInput var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.z.U$Minecraft().player != null) {
         this.z.U$Minecraft().player.setPos(this.i, this.b, this.Q);
      }
   }

   @Override
   public void V(EventPacket var1) {
      int[] var10000 = SkipTicks.v$ArrI();
      Packet var3 = var1.R$Packet();
      int[] var2 = var10000;
      boolean var4 = var3 instanceof ServerboundMovePlayerPacket;
      if (var2 == null) {
         if (var4) {
            var1.c(true);
            return;
         }

         var4 = var3 instanceof ClientboundRespawnPacket;
      }

      label30: {
         if (var2 == null) {
            if (var4) {
               break label30;
            }

            var4 = var3 instanceof ClientboundExplodePacket;
         }

         if (!var4) {
            return;
         }
      }

      this.z.M(false);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
