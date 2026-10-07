package com.elowen.modules.impl.move.stuck;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Stuck;
import java.util.Optional;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;
import net.minecraft.world.phys.Vec3;

public class ElytraPacket implements StuckMode {
   private Stuck i;

   @Override
   public void k(Stuck var1) {
      this.i = var1;
   }

   @Override
   public void b(com.elowen.events.impl.EventMotion var1) {
      if (this.i.U$Minecraft().player != null) {
         if (var1.Q() == EventType.PRE) {
            this.i.U$Minecraft().getConnection().send(new ServerboundPlayerCommandPacket(this.i.U$Minecraft().player, Action.START_FALL_FLYING));
            Elowen.U++;
         }
      }
   }

   @Override
   public void V(EventPacket var1) {
      int[] var2;
      Packet var7;
      label72: {
         int[] var10000 = SkipTicks.v$ArrI();
         Packet var3 = var1.R$Packet();
         var2 = var10000;
         boolean var6 = var3 instanceof ClientboundRespawnPacket;
         if (var2 == null) {
            if (var6) {
               this.i.M(false);
               if (var2 == null) {
                  return;
               }
            }

            var7 = var3;
            if (var2 != null) {
               break label72;
            }

            var6 = var3 instanceof ClientboundExplodePacket;
         }

         if (!var6) {
            return;
         }

         var7 = var3;
      }

      ClientboundExplodePacket var4 = (ClientboundExplodePacket)var7;
      Optional var5 = var4.playerKnockback();
      int var8 = ((var5.isPresent()) ? 1 : 0);
      if (var2 == null) {
         if ((var8 == 0)) {
            return;
         }

         double var9;
         var8 = (var9 = ((Vec3)var5.get()).x - 0.0) == 0.0 ? 0 : (var9 < 0.0 ? -1 : 1);
      }

      label74: {
         if (var2 == null) {
            if (((var8) != 0)) {
               break label74;
            }

            double var10;
            var8 = (var10 = ((Vec3)var5.get()).y - 0.0) == 0.0 ? 0 : (var10 < 0.0 ? -1 : 1);
         }

         if (var2 == null) {
            if (((var8) != 0)) {
               break label74;
            }

            double var11;
            var8 = (var11 = ((Vec3)var5.get()).z - 0.0) == 0.0 ? 0 : (var11 < 0.0 ? -1 : 1);
         }

         if ((var8 == 0)) {
            return;
         }
      }

      this.i.M(false);
   }

   @Override
   public void Y() {
      int[] var1 = SkipTicks.v$ArrI();
      if (this.i.U$Minecraft().player != null) {
         this.i.U$Minecraft().options.keyShift.setDown(true);
         new Thread(this::deobfLambda$onDisable$0).start();
      }
   }

   private void deobfLambda$onDisable$0() {
      try {
         Thread.sleep(100L);
      } catch (InterruptedException var2) {
      }

      this.i.U$Minecraft().execute(this::deobfLambda$onDisable$1);
   }

   private void deobfLambda$onDisable$1() {
      int[] var1 = SkipTicks.v$ArrI();
      if (this.i.U$Minecraft().player != null) {
         this.i.U$Minecraft().options.keyShift.setDown(false);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
