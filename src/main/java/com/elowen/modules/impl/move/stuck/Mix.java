package com.elowen.modules.impl.move.stuck;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Stuck;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;

public class Mix implements StuckMode {
   private Stuck Q;

   @Override
   public void k(Stuck var1) {
      this.Q = var1;
   }

   @Override
   public void b(com.elowen.events.impl.EventMotion var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.Q.U$Minecraft().player != null) {
         if (var1.Q() == EventType.PRE) {
            var1.q(true);
            this.Q.U$Minecraft().getConnection().send(new ServerboundPlayerCommandPacket(this.Q.U$Minecraft().player, Action.START_FALL_FLYING));
            this.Q
               .U$Minecraft()
               .getConnection()
               .send(
                  new Pos(
                     this.Q.U$Minecraft().player.getX() + 214748.0,
                     this.Q.U$Minecraft().player.getY() - 214748.0,
                     this.Q.U$Minecraft().player.getZ() + 214748.0,
                     this.Q.U$Minecraft().player.onGround(),
                     false
                  )
               );
            Elowen.U = 3;
         }
      }
   }

   @Override
   public void V(EventPacket var1) {
      label20: {
         int[] var10000 = SkipTicks.v$ArrI();
         Packet var3 = var1.R$Packet();
         int[] var2 = var10000;
         boolean var4 = var3 instanceof ClientboundRespawnPacket;
         if (var2 == null) {
            if (var4) {
               break label20;
            }

            var4 = var3 instanceof ClientboundExplodePacket;
         }

         if (!var4) {
            return;
         }
      }

      this.Q.M(false);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
