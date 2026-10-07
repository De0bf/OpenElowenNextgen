package com.elowen.modules.impl.test;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.NetworkUtils;
import com.elowen.values.HasValue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(R = "ECFullDisabler", M = Category.TEST, a = "Bypass EC")
public class ECFullDisabler extends Module {
   private static final int E = 20;
   private final Queue I;
   private int S;

   public ECFullDisabler() {
      String var10000 = NoInterpolation.U$String();
      this.I = new ConcurrentLinkedQueue();
      String var1 = var10000;
      this.S = 0;
      if (var1 != null) {
         HasValue.d(HasValue.x());
      }
   }

   @EventTarget
   public void J(EventPacket var1) {
      String var2 = NoInterpolation.U$String();
      if (var1.M() == EventType.RECEIVE) {
         if (var1.R$Packet() instanceof ClientboundPlayerPositionPacket
            || var1.R$Packet() instanceof ClientboundPlayerLookAtPacket
            || var1.R$Packet() instanceof ClientboundSetEntityMotionPacket) {
            var1.c(true);
         }
      }
   }

   @EventTarget
   public void W(EventPacket var1) {
      String var2 = NoInterpolation.U$String();
      if (var1.M() == EventType.RECEIVE) {
         if (!var1.c$Z()) {
            if (G.player != null) {
               if (var1.R$Packet() instanceof ClientboundPingPacket || var1.R$Packet() instanceof ClientboundKeepAlivePacket) {
                  this.I.add(var1.R$Packet());
                  var1.c(true);
               }
            }
         }
      }
   }

   @EventTarget
   public void D(EventTick var1) {
      String var2 = NoInterpolation.U$String();
      if (G.player != null) {
         if (var1.s$f() == EventType.PRE) {
            if (G.level != null) {
               this.C$h();
            }

            if (++this.S >= 20) {
               this.S = 0;
               this.z$V();
            }
         }
      }
   }

   private void C$h() {
      BlockHitResult var8;
      label17: {
         float var2 = G.player.getYRot();
         String var10000 = NoInterpolation.U$String();
         float var3 = G.player.getXRot();
         String var1 = var10000;
         NetworkUtils.O(seq -> deobfLambda$sendUseItemSpam$0(var2, var3, seq));
         HitResult var6 = G.hitResult;
         HitResult var7 = var6;
         if (var1 == null) {
            if (!(var6 instanceof BlockHitResult)) {
               var8 = this.m();
               break label17;
            }

            var7 = var6;
         }

         BlockHitResult var5 = (BlockHitResult)var7;
         var8 = var5;
      }

      BlockHitResult var4 = var8;
      NetworkUtils.O(seq -> deobfLambda$sendUseItemSpam$1(var4, seq));
   }

   private BlockHitResult m() {
      Vec3 var1 = G.player.getEyePosition(1.0F);
      return BlockHitResult.miss(var1, Direction.UP, BlockPos.containing(var1));
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void z$V() {
      String var10000 = NoInterpolation.U$String();
      ClientPacketListener var2 = G.getConnection();
      String var1 = var10000;
      if (var1 == null) {
         if (var2 != null) {
            while (!this.I.isEmpty()) {
               label35: {
                  Packet var3 = (Packet)this.I.poll();
                  Packet var7 = var3;
                  if (var1 == null) {
                     if (var3 == null) {
                        continue;
                     }

                     try {
                        var7 = var3;
                     } catch (Exception var6) {
                        var6.printStackTrace();
                        break label35;
                     }
                  }

                  try {
                     var7.handle(var2);
                  } catch (Exception var5) {
                     var5.printStackTrace();
                  }
               }

               if (var1 != null) {
                  break;
               }
            }

            return;
         }

         this.I.clear();
      }
   }

   @Override
   public void h$V() {
      super.h$V();
      this.I.clear();
      this.S = 0;
   }

   @Override
   public void q$V() {
      super.q$V();
      this.I.clear();
      NoInterpolation.U$String();
      this.S = 0;
      if (!HasValue.x()) {
         NoInterpolation.d("lMll3b");
      }
   }

   private static Packet deobfLambda$sendUseItemSpam$1(BlockHitResult var0, int var1) {
      return new ServerboundUseItemOnPacket(InteractionHand.MAIN_HAND, var0, var1);
   }

   private static Packet deobfLambda$sendUseItemSpam$0(float var0, float var1, int var2) {
      return new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, var2, var0, var1);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
