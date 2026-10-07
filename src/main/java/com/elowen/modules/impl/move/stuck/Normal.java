package com.elowen.modules.impl.move.stuck;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Module;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.rotation.RotationManager;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class Normal implements StuckMode {
   private Stuck t;
   private int L = 0;
   private Packet b;
   private float z;
   private float A;
   private boolean O = false;
   private final Queue G = new ConcurrentLinkedQueue();
   private static final String a;

   @Override
   public void k(Stuck var1) {
      this.t = var1;
   }

   @Override
   public void G$V() {
      this.L = 0;
      this.b = null;
      this.z = com.elowen.utils.rotation.RotationManager.r.H;
      SkipTicks.v$ArrI();
      this.A = com.elowen.utils.rotation.RotationManager.r.E;
      this.O = false;
      if (com.elowen.values.HasValue.X$Z()) {
         SkipTicks.S(new int[4]);
      }
   }

   @Override
   public void Y() {
      int[] var1 = SkipTicks.v$ArrI();
      if (this.t.U$Minecraft().player != null && this.L != 3) {
         PacketUtils.c(
            new Pos(
               this.t.U$Minecraft().player.getX() + 1337.0,
               this.t.U$Minecraft().player.getY(),
               this.t.U$Minecraft().player.getZ() + 1337.0,
               this.t.U$Minecraft().player.onGround(),
               false
            )
         );
         if (!this.G.isEmpty()) {
            PacketUtils.c((Packet)this.G.poll());
            com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
         }
      }

      this.L = 0;
      this.b = null;
      this.z = 0.0F;
      this.A = 0.0F;
      this.O = false;
      this.G.clear();
   }

   @Override
   public void B(EventTick var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.t.U$Minecraft().player != null) {
         this.t.U$Minecraft().player.setDeltaMovement(0.0, 0.0, 0.0);
      }
   }

   @Override
   public void b(com.elowen.events.impl.EventMotion var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (this.O) {
         this.L = 3;
         if (!this.G.isEmpty()) {
            PacketUtils.c((Packet)this.G.poll());
         }

         this.O = false;
         this.t.M(false);
      } else {
         Module var3 = Elowen.S$Elowen().q$ModuleManager().k(a);
         if (var3 != null && var3.w()) {
            var3.R$V();
         }

         if (var1.Q() == EventType.PRE && this.L == 1) {
            this.L = 2;
            float var4 = this.t.U$Minecraft().player.getYRot();
            float var5 = this.t.U$Minecraft().player.getXRot();
            if (this.s$Z() && (this.z != var4 || this.A != var5)) {
               PacketUtils.c(new Rot(var4, var5, this.t.U$Minecraft().player.onGround(), false));
               if (!this.G.isEmpty()) {
                  PacketUtils.c((Packet)this.G.poll());
               }

               this.z = var4;
               this.A = var5;
            }

            PacketUtils.c(this.b);
         }
      }
   }

   private boolean s$Z() {
      int[] var10000 = SkipTicks.v$ArrI();
      Packet var3 = this.b;
      int[] var1 = var10000;
      Packet var7 = var3;
      if (var1 == null) {
         if (var3 instanceof ServerboundUseItemPacket var2) {
            ItemStack var6 = this.t.U$Minecraft().player.getItemInHand(var2.hand());
            boolean var10 = var6.getItem() instanceof BowItem;
            if (var1 == null) {
               var10 = !var10;
            }

            return var10;
         }

         var7 = this.b;
      }

      Packet var4 = var7;
      Packet var8 = var4;
      if (var1 == null) {
         if (!(var4 instanceof ServerboundPlayerActionPacket)) {
            return false;
         }

         var8 = var4;
      }

      ServerboundPlayerActionPacket var5 = (ServerboundPlayerActionPacket)var8;
      if (var5.getAction() == Action.RELEASE_USE_ITEM) {
         boolean var9 = this.t.U$Minecraft().player.getUseItem().getItem() instanceof BowItem;
         if (var1 != null) {
            return var9;
         }

         if (var9) {
            return true;
         }
      }

      return false;
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
      this.L = 3;
      this.t.M(false);
   }

   @Override
   public void V(EventPacket var1) {
      int[] var2 = SkipTicks.v$ArrI();
      if (var1.R$Packet() instanceof ServerboundMovePlayerPacket) {
         var1.c(true);
      }

      if (var1.R$Packet() instanceof ServerboundPongPacket) {
         this.G.offer((ServerboundPongPacket)var1.R$Packet());
         var1.c(true);
      }

      if (var1.R$Packet() instanceof ServerboundUseItemPacket || var1.R$Packet() instanceof ServerboundPlayerActionPacket) {
         this.b = var1.R$Packet();
         this.L = 1;
         var1.c(true);
      }

      if (var1.R$Packet() instanceof ClientboundPlayerPositionPacket) {
         if (!this.G.isEmpty()) {
            PacketUtils.c((Packet)this.G.poll());
         }

         this.L = 3;
         this.t.M(false);
      }
   }

   public boolean t() {
      int[] var1 = SkipTicks.v$ArrI();
      return this.L == 3;
   }

   public void r(boolean var1) {
      this.O = var1;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "Scaffold";
   }
   }
