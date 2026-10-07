package com.elowen.modules.impl.move.nofall;

import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventTick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.move.NoFall;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ElytraPacket implements NoFallMode {
   private NoFall R;
   private static final int i = 40;
   private static final double f = 1.0E-8;
   private ElytraPacket$State H = ElytraPacket$State.IDLE;
   private int m;
   private static int[] E;

   @Override
   public void Q(NoFall var1) {
      this.R = var1;
   }

   @Override
   public void J$V() {
      this.W$V();
   }

   @Override
   public void l$V() {
      this.W$V();
      this.R.Q = false;
   }

   @Override
   public void e(EventRespawn var1) {
      this.W$V();
   }

   @Override
   public void R(EventTick var1) {
      int[] var2 = b$ArrI();
      if (var1.s$f() == com.elowen.events.api.types.EventType.PRE) {
         Minecraft var3 = this.R.v$Minecraft();
         if (var3.player != null && var3.level != null) {
            this.p();
            this.R.Q = this.H != ElytraPacket$State.IDLE;
         } else {
            this.W$V();
            this.R.Q = false;
         }
      }
   }

   private void p() {
      int[] var1 = b$ArrI();
      if (this.H != ElytraPacket$State.WAIT_CORRECTION && this.H != ElytraPacket$State.REPLACE_CONFIRM) {
         if (!Velocity.r() && this.z$Z() && !(this.M() < this.R.J$F())) {
            if (this.H == ElytraPacket$State.IDLE) {
               this.H = ElytraPacket$State.WAIT_CONTACT;
            }
         } else {
            if (this.H == ElytraPacket$State.WAIT_CONTACT) {
               this.W$V();
            }
         }
      } else {
         if (++this.m > 40) {
            this.W$V();
         }
      }
   }

   @Override
   public void h(com.elowen.events.impl.EventPacket var1) {
      int[] var2 = b$ArrI();
      if (!var1.c$Z()) {
         Minecraft var3 = this.R.v$Minecraft();
         if (var3.player != null && var3.level != null) {
            if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE) {
               if (this.H == ElytraPacket$State.WAIT_CORRECTION && var1.R$Packet() instanceof ClientboundPlayerPositionPacket) {
                  this.H = ElytraPacket$State.REPLACE_CONFIRM;
                  this.m = 0;
               }
            } else if (var1.M() == com.elowen.events.api.types.EventType.SEND) {
               if (this.H == ElytraPacket$State.WAIT_CONTACT && var1.R$Packet() instanceof ServerboundMovePlayerPacket var4 && var4.isOnGround()) {
                  this.N(var1);
               }

               if (this.H == ElytraPacket$State.REPLACE_CONFIRM && var1.R$Packet() instanceof ServerboundAcceptTeleportationPacket var5) {
                  this.R(var1, var5);
               }
            }
         } else {
            this.W$V();
         }
      }
   }

   private void N(com.elowen.events.impl.EventPacket var1) {
      int[] var10000 = b$ArrI();
      Minecraft var3 = this.R.v$Minecraft();
      ItemStack var4 = var3.player.getItemBySlot(EquipmentSlot.CHEST);
      int[] var2 = var10000;
      if (var2 != null) {
         if (!LivingEntity.canGlideUsing(var4, EquipmentSlot.CHEST)) {
            if (var3.getConnection() == null) {
               this.W$V();
               return;
            }

            this.H = ElytraPacket$State.WAIT_CORRECTION;
            this.m = 0;
            com.elowen.utils.PacketUtils.c(new ServerboundPlayerCommandPacket(var3.player, Action.START_FALL_FLYING));
            var1.c(true);
            return;
         }

         this.W$V();
      }
   }

   private void R(com.elowen.events.impl.EventPacket var1, ServerboundAcceptTeleportationPacket var2) {
      int[] var10000 = b$ArrI();
      Minecraft var4 = this.R.v$Minecraft();
      int[] var3 = var10000;
      if (var3 != null) {
         if (var4.getConnection() != null) {
            ServerboundAcceptTeleportationPacket var5 = new ServerboundAcceptTeleportationPacket(
               var2.id(), var2.x(), var2.y() + 1.0E-8, var2.z(), var2.yRot(), var2.xRot()
            );
            var1.c(true);
            com.elowen.utils.PacketUtils.c(var5);
            var4.player.resetFallDistance();
            this.U$V();
            this.W$V();
            return;
         }

         this.W$V();
      }
   }

   private boolean z$Z() {
      int[] var10000 = b$ArrI();
      Minecraft var2 = this.R.v$Minecraft();
      int[] var1 = var10000;
      int var3 = ((var2.player.onGround()) ? 1 : 0);
      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.isPassenger()) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.getAbilities().mayfly) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.getAbilities().flying) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.isFallFlying()) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.isInWater()) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.isInLava()) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         var3 = ((var2.player.onClimbable()) ? 1 : 0);
      }

      if (var1 != null) {
         if (var3 != 0) {
            return false;
         }

         double var4;
         var3 = (byte)((var4 = var2.player.getDeltaMovement().y - 0.0) == 0.0 ? 0 : (var4 < 0.0 ? -1 : 1));
      }

      return (boolean)(var1 == null ? var3 : var3 < 0);
   }

   private float M() {
      Minecraft var1 = this.R.v$Minecraft();
      return (float)(var1.player.fallDistance + Math.max(0.0, -var1.player.getDeltaMovement().y));
   }

   private void U$V() {
      int[] var10000 = b$ArrI();
      Minecraft var2 = this.R.v$Minecraft();
      int[] var1 = var10000;
      Minecraft var3 = var2;
      if (var1 != null) {
         if (var2.player == null) {
            return;
         }

         var3 = var2;
      }

      var3.options.keyShift.setDown(true);
      Minecraft var4 = var3;
      new Thread(() -> deobfLambda$crouchOnce$0(var4)).start();
   }

   private void W$V() {
      this.H = ElytraPacket$State.IDLE;
      this.m = 0;
   }

   private static void deobfLambda$crouchOnce$0(Minecraft var0) {
      try {
         Thread.sleep(100L);
      } catch (InterruptedException var2) {
      }

      var0.execute(() -> deobfLambda$crouchOnce$1(var0));
   }

   private static void deobfLambda$crouchOnce$1(Minecraft var0) {
      int[] var1 = b$ArrI();
      if (var0.player != null) {
         var0.options.keyShift.setDown(false);
      }
   }

   public static void B(int[] var0) {
      E = var0;
   }

   public static int[] b$ArrI() {
      return E;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (b$ArrI() == null) {
         B(new int[2]);
      }
   }
}
