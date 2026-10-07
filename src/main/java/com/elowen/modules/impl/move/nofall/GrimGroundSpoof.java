package com.elowen.modules.impl.move.nofall;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.move.NoFall;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.PearlPhysicsUtil;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.phys.Vec3;

public class GrimGroundSpoof implements NoFallMode {
   private NoFall S;
   private float W;
   private boolean k;
   private long G;
   private static final long U = 500L;
   private boolean F;

   public GrimGroundSpoof() {
      ElytraPacket.b$ArrI();
      super();
      this.G = 0L;
      this.F = true;
      if (!com.elowen.values.HasValue.x()) {
         ElytraPacket.B(new int[3]);
      }
   }

   @Override
   public void Q(NoFall var1) {
      this.S = var1;
   }

   @Override
   public void J$V() {
      this.W = 0.0F;
      this.k = false;
      this.G = 0L;
      int[] var10000 = ElytraPacket.b$ArrI();
      this.F = true;
      int[] var1 = var10000;
      if (var1 == null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }
   }

   @Override
   public void l$V() {
      this.G = 0L;
      this.S.Q = false;
   }

   private boolean X(double var1) {
      boolean var13 = false;
      int[] var10000 = ElytraPacket.b$ArrI();
      Minecraft var4 = this.S.v$Minecraft();
      int[] var3 = var10000;
      if (var4.level == null) {
         return false;
      }

      int var5 = (int)(var1 * 20.0);
      Iterator var6 = var4.level.entitiesForRendering().iterator();

      while (true) {
         if (var6.hasNext()) {
            Entity var7 = (Entity)var6.next();
            Entity var12 = var7;
            if (var3 != null) {
               var13 = var7 instanceof ThrownEnderpearl;
               if (var3 == null) {
                  break;
               }

               if (!var13) {
                  continue;
               }

               var12 = var7;
            }

            ThrownEnderpearl var8 = (ThrownEnderpearl)var12;
            Object[] var9 = PearlPhysicsUtil.p(var8, var4.level);
            Vec3 var10 = (Vec3)var9[0];
            int var11 = (Integer)var9[1];
            label55:
            if (var10 != null) {
               int var14 = var11;
               if (var3 != null) {
                  if (var11 <= 0) {
                     break label55;
                  }

                  var14 = var11;
               }

               if (var3 == null) {
                  return (boolean)((var14) != 0);
               }

               if (var14 <= var5) {
                  return true;
               }
            }

            if (var3 != null) {
               continue;
            }
         }

         var13 = false;
         break;
      }

      return var13;
   }

   @Override
   public void g(com.elowen.events.impl.EventMotion var1) {
      int[] var2 = ElytraPacket.b$ArrI();
      if (var1.Q() == EventType.PRE) {
         Minecraft var3 = this.S.v$Minecraft();
         if (Velocity.r()) {
            this.S.Q = false;
         } else {
            if (this.X(0.5)) {
               this.G = System.currentTimeMillis() + 500L;
            }

            boolean var4 = System.currentTimeMillis() >= this.G;
            if (var4 && !this.F) {
               this.W = (float)var3.player.fallDistance;
               this.k = false;
            }

            this.F = var4;
            if (!var4) {
               this.S.Q = false;
            } else {
               this.S.Q = false;
               float var5 = (float)var3.player.fallDistance;
               if (this.W >= this.S.J$F() && var1.r()) {
                  this.S.Q = true;
                  var1.q(false);
                  com.elowen.utils.PacketUtils.c(new StatusOnly(true, var3.player.horizontalCollision));
                  Elowen.U = (int)this.S.A$F();
                  this.k = true;
               }

               this.W = var5;
            }
         }
      }
   }

   @Override
   public void T(EventMoveInput var1) {
      GrimGroundSpoof var7 = null;
      int[] var10000 = ElytraPacket.b$ArrI();
      Minecraft var3 = this.S.v$Minecraft();
      int[] var2 = var10000;
      int var5 = ((Velocity.r()) ? 1 : 0);
      if (var2 != null) {
         if (var5 != 0) {
            return;
         }

         long var8;
         var5 = (var8 = System.currentTimeMillis() - this.G) == 0L ? 0 : (var8 < 0L ? -1 : 1);
      }

      if (var2 != null) {
         var5 = var5 >= 0 ? 1 : 0;
      }

      int var4 = var5;
      int var6 = var4;
      if (var2 != null) {
         if (var4 == 0) {
            return;
         }

         float var9;
         var6 = (var9 = this.W - this.S.J$F()) == 0.0F ? 0 : (var9 < 0.0F ? -1 : 1);
      }

      label73: {
         label59:
         if (var2 != null) {
            if (var6 >= 0) {
               var6 = ((var3.player.onGround()) ? 1 : 0);
               if (var2 == null) {
                  break label59;
               }

               if (var6 != 0) {
                  var1.v(false);
               }
            }

            var7 = this;
            if (var2 == null) {
               break label73;
            }

            var6 = ((this.k) ? 1 : 0);
         }

         if (var6 == 0) {
            return;
         }

         var1.A(true);
         var7 = this;
      }

      var7.k = false;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
