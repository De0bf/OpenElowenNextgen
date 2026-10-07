package com.elowen.modules.impl.move.stuck;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Stuck;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;

public class SkipTicks implements StuckMode {
   private Stuck c;
   private static int[] t;

   @Override
   public void k(Stuck var1) {
      this.c = var1;
   }

   @Override
   public void B(EventTick var1) {
      int[] var2 = v$ArrI();
      if (var1.s$f() == EventType.PRE) {
         Elowen.U++;
      }
   }

   @Override
   public void V(EventPacket var1) {
      label20: {
         int[] var10000 = v$ArrI();
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

      this.c.M(false);
   }

   public static void S(int[] var0) {
      t = var0;
   }

   public static int[] v$ArrI() {
      return t;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (v$ArrI() != null) {
         S(new int[3]);
      }
   }
}
