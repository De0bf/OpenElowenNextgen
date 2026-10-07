package com.elowen.modules.impl.combat.velocity;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Module;
import com.elowen.modules.impl.combat.AntiBots;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.modules.impl.move.LongJump;
import com.elowen.modules.impl.move.NoFall;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.FriendManager;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.PearlPhysicsUtil;
import com.elowen.utils.Vector2f;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class NoXZ implements VelocityMode {
   public static boolean f;
   private Velocity P;
   public final Queue w = new ConcurrentLinkedQueue();
   private Vec3 h;
   private long T;
   public Player I;
   private Aura v;
   public NoXZ$Stage m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE;
   private long K;
   private boolean p = false;
   private int k = 0;
   public int N = 0;
   public int o = 0;
   public boolean L;
   private long O;
   private boolean Z;
   private boolean s;
   public UUID x;
   private boolean W;
   private Player z;
   private long e;
   private long i;
   private boolean Q;
   public boolean d;
   public long n;
   public int R;
   public boolean H;
   private boolean l;
   private final NoXZ$ResumeDelayHandler a;
   private static com.elowen.values.HasValue[] u;
   private static final String[] b = new String[]{"[Velocity] delay until ground, no target, packet kept", "[Velocity] timeout reached, force release knockback", "[Velocity] delay until ground, attack enabled, packet kept", "[Velocity] released knockback on ground", "[Velocity] cancel alink", "Alink ", " (ground wait)", "NoXZ", "[Velocity] attack timeout, force release", "tick", " ⏳", "Color"};
   public NoXZ() {
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      this.L = false;
      this.O = 0L;
      this.Z = false;
      this.s = false;
      this.x = null;
      this.W = false;
      this.z = null;
      com.elowen.values.HasValue[] var1 = var10000;
      this.e = -1L;
      this.i = -1L;
      this.Q = false;
      this.d = false;
      this.n = 0L;
      this.R = 0;
      this.l = false;
      this.a = new NoXZ$ResumeDelayHandler(this);
      if (var1 != null) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }
   }

   private void t() {
      this.I = null;
      this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE;
      this.N = 0;
      this.o = 0;
      this.d = false;
      this.n = 0L;
      this.Q = false;
      this.R = 0;
      f = false;
   }

   private void U(Entity var1) {
      Minecraft var7 = null;
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      this.l = false;
      Entity var10000 = this.P.i$MC().player;
      if (var2 == null) {
         if (var10000 == null) {
            return;
         }

         var10000 = var1;
      }

      if (var10000 != null) {
         boolean var5 = this.W(var1);
         if (var2 == null) {
            if (!var5) {
               return;
            }

            var5 = this.r();
         }

         if (var2 == null) {
            if (var5) {
               return;
            }

            var5 = this.Q();
         }

         if (var2 == null) {
            if (var5) {
               this.e(true);
               return;
            }

            var5 = this.s$Z();
         }

         if (var2 == null) {
            if (var5) {
               return;
            }

            this.l = true;
            var5 = this.P.i$MC().player.isSprinting();
         }

         label86: {
            boolean var3 = var5;
            boolean var6 = var3;
            if (var2 == null) {
               if (var3) {
                  this.P.i$MC().player.setSprinting(false);
               }

               var7 = this.P.i$MC();
               if (var2 != null) {
                  break label86;
               }

               var7.gameMode.attack(this.P.i$MC().player, var1);
               com.elowen.utils.PlayerUtils.s(InteractionHand.MAIN_HAND);
               var6 = var3;
            }

            if (!var6) {
               return;
            }

            var7 = this.P.i$MC();
         }

         var7.player.setDeltaMovement(this.P.i$MC().player.getDeltaMovement().multiply(0.6, 1.0, 0.6));
      }
   }

   private void Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.P.i$MC().player != null && this.e != -1L && this.i == -1L) {
         this.i = this.P.i$MC().player.tickCount;
         long var2 = this.i - this.e;
         this.P.F(var2 + "tick");
         this.e = -1L;
         this.i = -1L;
      }
   }

   private void q(EventMoveInput var1) {
      var1.b(1.0F);
      var1.m(0.0F);
      var1.v(false);
   }

   private boolean E$Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.K != 0L && System.currentTimeMillis() - this.K <= 2500L) {
         this.e(true);
         return true;
      } else {
         return false;
      }
   }

   private boolean v$Z() {
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      Module var2 = Elowen.S$Elowen().q$ModuleManager().A(NoFall.class);
      com.elowen.values.HasValue[] var1 = var10000;
      Module var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      if (var1 != null) {
         return var4;
      }

      if (var4) {
         if (var1 != null) {
            return ((NoFall)var2).Q;
         }

         if (((NoFall)var2).Q) {
            return true;
         }
      }

      return false;
   }

   public boolean R(Player var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (var1 == null) {
         return true;
      } else {
         return this.e(var1) ? true : this.P.i$MC().player != null && this.P.i$MC().player.distanceTo(var1) >= 3.0;
      }
   }

   private boolean H() {
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      Scaffold var2 = (Scaffold)Elowen.S$Elowen().q$ModuleManager().A(Scaffold.class);
      com.elowen.values.HasValue[] var1 = var10000;
      Scaffold var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      return var1 != null ? var4 : var4;
   }

   private boolean M() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      return this.P.i$MC().player == null ? false : this.P.i$MC().player.isInWater() || this.P.i$MC().player.isInLava();
   }

   private boolean D() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (!this.H() && !this.M()) {
         if (this.P.i$MC().player != null && this.P.i$MC().level != null) {
            BlockPos var2 = this.P.i$MC().player.blockPosition();
            if (this.P.i$MC().level.getBlockState(var2).getBlock() instanceof WebBlock) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private boolean c$Z() {
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      Module var2 = Elowen.S$Elowen().q$ModuleManager().A(Stuck.class);
      com.elowen.values.HasValue[] var1 = var10000;
      Module var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      return var1 != null ? var4 : var4;
   }

   private boolean b$Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (!this.P.m()) {
         return false;
      }

      if (this.v == null) {
         this.v = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
      }

      boolean var2 = this.v != null && this.v.w();
      if (var2) {
         return false;
      }

      if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE || !this.w.isEmpty()) {
         this.e(true);
      }

      return true;
   }

   private boolean r() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      return this.P.i$MC().player == null ? false : this.P.i$MC().player.isUsingItem();
   }

   private boolean Q() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.P.i$MC().player == null || this.P.i$MC().level == null) {
         return false;
      }

      if (this.P.i$MC().player.isInWater()) {
         return true;
      }

      if (this.P.i$MC().player.onClimbable()) {
         return true;
      }

      BlockPos var2 = this.P.i$MC().player.blockPosition();
      return this.P.i$MC().level.getBlockState(var2).getBlock() instanceof WebBlock;
   }

   private boolean X(Player var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (this.P.i$MC().player != null && var1 != null) {
         Teams var3 = (Teams)Elowen.S$Elowen().q$ModuleManager().A(Teams.class);
         if (var1.getTeam() != null && this.P.i$MC().player.getTeam() != null) {
            String var4 = var1.getTeam().getName();
            String var5 = this.P.i$MC().player.getTeam().getName();
            if (Objects.equals(var4, var5)) {
               return true;
            }
         }

         if (var3 == null) {
            return false;
         }

         if (var3.v != null && var3.v.t("Color")) {
            Integer var7 = var1.getTeamColor();
            Integer var9 = this.P.i$MC().player.getTeamColor();
            return var7 != null && var9 != null && var7.equals(var9);
         }

         if (this.P.i$MC().getConnection() == null) {
            return false;
         }

         String var6 = Teams.b(var1);
         String var8 = Teams.b(this.P.i$MC().player);
         return Objects.equals(var6, var8);
      } else {
         return false;
      }
   }

   private boolean e(Player var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (var1 == null) {
         return true;
      } else if (var1.isRemoved() || var1.isDeadOrDying()) {
         return true;
      } else if (AntiBots.g(var1) || AntiBots.O(var1)) {
         return true;
      } else if (FriendManager.A(var1)) {
         return true;
      } else {
         String var3 = var1.getScoreboardName();
         if (var3 != null && FriendManager.u$Z(var3)) {
            return true;
         } else {
            return var1.getGameProfile() != null && FriendManager.u$Z(var1.getGameProfile().name()) ? true : this.X(var1);
         }
      }
   }

   private boolean s$Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (!this.P.a$Z()) {
         return false;
      }

      if (this.I == null) {
         return true;
      }

      int var2 = this.I.hurtTime;
      float var3 = this.P.V();
      return var2 <= var3;
   }

   private Vector2f E$h() {
      return this.P.i$MC().player != null ? new Vector2f(this.P.i$MC().player.getYRot(), this.P.i$MC().player.getXRot()) : null;
   }

   private boolean V(Entity var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (var1 != null && this.P.i$MC().player != null) {
         Vector2f var3 = this.E$h();
         if (var3 == null) {
            return false;
         }

         Vec3 var4 = this.P.i$MC().player.getEyePosition();
         double var5 = this.P.i$MC().player.isCreative() ? 5.0 : 3.0;
         Vec3 var7 = Vec3.directionFromRotation(var3.E, var3.H);
         Vec3 var8 = var4.add(var7.x * var5, var7.y * var5, var7.z * var5);
         AABB var9 = var1.getBoundingBox();
         if (var9.contains(var4)) {
            return true;
         }

         Optional var10 = var9.clip(var4, var8);
         return var10.isPresent();
      } else {
         return false;
      }
   }

   private boolean X(Entity var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (this.P.i$MC().player != null && var1 != null) {
         Vec3 var3 = this.P.i$MC().player.getEyePosition(1.0F);
         Vec3 var4 = this.P.i$MC().player.getViewVector(1.0F);
         double var5 = this.P.i$MC().player.isCreative() ? 5.0 : 3.0;
         Vec3 var7 = var3.add(var4.x * var5, var4.y * var5, var4.z * var5);
         AABB var8 = var1.getBoundingBox().inflate(0.1);
         Optional var9 = var8.clip(var3, var7);
         return var9.isPresent();
      } else {
         return false;
      }
   }

   private boolean W(Entity var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (this.P.i$MC().hitResult != null && var1 != null) {
         if (this.P.i$MC().hitResult.getType() == Type.ENTITY) {
            EntityHitResult var3 = (EntityHitResult)this.P.i$MC().hitResult;
            return var3.getEntity() == var1;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void E$V() {
      boolean var6 = false;
      NoXZ var7 = null;
      NoXZ var8 = null;
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      float var2 = Elowen.i;
      Elowen.i = 4.0F;
      int var3 = this.N;
      int var4 = 0;
      com.elowen.values.HasValue[] var1 = var10000;
      int var5 = 0;

      while (true) {
         label110:
         if (var5 < var3) {
            var6 = this.R(this.I);
            if (var1 != null) {
               break;
            }

            if (var1 == null) {
               if (((var6) ? 1 : 0) != 0) {
                  this.N = 0;
                  if (var1 == null) {
                     break label110;
                  }
               }

               this.U(this.I);
               var6 = this.l;
            }

            if (var1 == null) {
               if (((var6) ? 1 : 0) == 0 && var1 == null) {
                  break label110;
               }

               var6 = ((var5) != 0);
            }

            if (((var6) ? 1 : 0) == 0) {
               this.Z();
            }

            var4++;
            var5++;
            if (var1 == null) {
               continue;
            }
         }

         this.N -= var4;
         var6 = ((this.N) != 0);
         break;
      }

      if (var1 == null) {
         if (((var6) ? 1 : 0) < 0) {
            this.N = 0;
         }

         var6 = ((this.N) != 0);
      }

      if (var1 == null) {
         label88: {
            if (((var6) ? 1 : 0) == 0) {
               var7 = this;
               if (var1 != null) {
                  break label88;
               }

               if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK) {
                  this.t();
               }
            }

            Elowen.i = var2;
            var7 = this;
         }

         var6 = var7.P.j$Z();
      }

      label117: {
         if (var1 == null) {
            if (((var6) ? 1 : 0) == 0) {
               return;
            }

            var8 = this;
            if (var1 != null) {
               break label117;
            }

            var6 = this.Q;
         }

         if (((var6) ? 1 : 0) == 0) {
            return;
         }

         this.w(true, false);
         var8 = this;
      }

      var8.Q = false;
   }

   public void N() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.P.i$MC().player != null) {
         if (!this.L && this.O == 0L) {
            this.x();
            this.s = true;
            this.a.C();
         }
      }
   }

   private void j$V() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.s && this.P.i$MC().player != null && this.P.i$MC().level != null) {
         for (Entity var3 : this.P.i$MC().level.entitiesForRendering()) {
            if (var3 instanceof ThrownEnderpearl var4 && var4.getOwner() == this.P.i$MC().player && (this.x == null || !this.x.equals(var4.getUUID()))) {
               Object[] var5 = com.elowen.utils.PearlPhysicsUtil.p(var4, this.P.i$MC().level);
               Vec3 var6 = (Vec3)var5[0];
               int var7 = (Integer)var5[1];
               if (var6 != null && var7 > 0) {
                  this.O = System.currentTimeMillis() + var7 * 50L;
                  this.x = var4.getUUID();
                  this.s = false;
                  return;
               }
               break;
            }
         }
      }
   }

   public void L() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.O != 0L) {
         if (!this.Z) {
            long var2 = this.O - System.currentTimeMillis();
            long var4 = (long)(this.P.x$F() * 1000.0F);
            if (var2 <= var4) {
               this.e(true);
               this.L = true;
               this.Z = true;
               this.a.C();
            }
         }
      }
   }

   public void x() {
      this.L = false;
      this.O = 0L;
      this.Z = false;
      this.s = false;
      this.x = null;
      this.a.C();
   }

   private void v$V() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      if (this.P.i$MC().player == null || this.P.i$MC().level == null) {
         this.W = false;
         this.z = null;
      } else if (this.E$h() == null) {
         this.W = false;
         this.z = null;
      } else {
         double var2 = this.P.S$F();
         Player var4 = null;
         boolean var5 = false;

         for (Entity var7 : this.P.i$MC().level.players()) {
            if (var7 != this.P.i$MC().player && var7 instanceof Player var8 && !this.e(var8) && !(this.P.i$MC().player.distanceTo(var8) > var2)) {
               boolean var9;
               try {
                  var9 = this.V(var8);
               } catch (Throwable var11) {
                  var9 = this.X(var8);
               }

               if (var9) {
                  var4 = var8;
                  var5 = true;
                  break;
               }
            }
         }

         if (var5 && var4 != null && !this.r() && !this.Q()) {
            this.W = true;
            this.z = var4;
         }

         this.W = false;
         this.z = null;
      }
   }

   private void Y() {
      this.a.V(this.P.X$F());
   }

   public void e(boolean var1) {
      this.w(var1, true);
   }

   public void w(boolean var1, boolean var2) {
      com.elowen.values.HasValue[] var3 = c$ArrQ();
      if (var2) {
         this.H = false;
         this.t();
      }

      if (!var1) {
         this.w.clear();
      } else {
         if (!this.w.isEmpty()) {
            Packet var4 = (Packet)this.w.poll();
            if (var4 != null && this.P.i$MC().getConnection() != null) {
               var4.handle(this.P.i$MC().getConnection());
            }
         }
      }
   }

   public void d$V() {
      this.H = false;
      this.t();
      this.K = 0L;
      this.p = this.c$Z();
      this.k = 0;
      this.x();
      this.W = false;
      this.z = null;
      this.a.e();
      this.e = -1L;
      this.i = -1L;
      this.w.clear();
      this.h = null;
      this.T = 0L;
      this.d = false;
      this.n = 0L;
      this.Q = false;
   }

   @Override
   public void C(Velocity var1) {
      this.P = var1;
   }

   @Override
   public void B(com.elowen.events.impl.EventMotion var1) {
   }

   @Override
   public void J(EventPacket var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (this.P.i$MC().player != null) {
         if (this.L) {
            if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE && var1.R$Packet() instanceof ClientboundPlayerPositionPacket) {
               this.a.u(this.P.X$F());
            }
         } else if (!this.c$Z() && this.k <= 0) {
            if (var1.M() == com.elowen.events.api.types.EventType.SEND) {
               if (var1.R$Packet() instanceof ServerboundUseItemPacket var3) {
                  InteractionHand var19 = var3.hand();
                  if (this.P.i$MC().player != null && this.P.i$MC().player.getItemInHand(var19).getItem() == Items.ENDER_PEARL) {
                     this.N();
                  }
               } else if (var1.R$Packet() instanceof ServerboundUseItemOnPacket var4) {
                  InteractionHand var21 = var4.hand();
                  if (this.P.i$MC().player != null && this.P.i$MC().player.getItemInHand(var21).getItem() == Items.ENDER_PEARL) {
                     this.N();
                  }
               }
            }

            if (!this.b$Z()) {
               if (var1.M() == com.elowen.events.api.types.EventType.SEND) {
                  if (var1.R$Packet() instanceof ServerboundUseItemPacket var11) {
                     InteractionHand var23 = var11.hand();
                     if (this.P.i$MC().player != null && this.P.i$MC().player.getItemInHand(var23).getItem() == Items.FIRE_CHARGE) {
                        this.K = System.currentTimeMillis();
                     }
                  } else if (var1.R$Packet() instanceof ServerboundUseItemOnPacket var14) {
                     InteractionHand var25 = var14.hand();
                     if (this.P.i$MC().player != null && this.P.i$MC().player.getItemInHand(var25).getItem() == Items.FIRE_CHARGE) {
                        this.K = System.currentTimeMillis();
                     }
                  }
               }

               if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE) {
                  if (var1.R$Packet() instanceof ClientboundExplodePacket) {
                     this.K = System.currentTimeMillis();
                     this.e(true);
                     return;
                  }

                  if (var1.R$Packet() instanceof ClientboundSetEntityMotionPacket var12 && var12.id() == com.elowen.utils.PlayerUtils.S$I()) {
                     if (this.a.c$Z()) {
                        return;
                     }

                     if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK && this.N > 0) {
                        return;
                     }

                     if (this.D()) {
                        return;
                     }

                     if (this.E$Z()) {
                        return;
                     }

                     if (this.v$Z()) {
                        return;
                     }

                     if (this.P.i$MC().player != null && this.e == -1L) {
                        this.e = this.P.i$MC().player.tickCount;
                     }

                     if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE && !Elowen.S$Elowen().q$ModuleManager().A(LongJump.class).w()) {
                        if (!this.H) {
                           double var18 = var12.movement().x;
                           double var6 = var12.movement().z;
                           double var8 = this.P.f$F();
                           if (Math.abs(var18) < var8 && Math.abs(var6) < var8) {
                              return;
                           }

                           if (this.W && this.z != null && !this.D()) {
                              if (this.P.J$Z() && !this.P.i$MC().player.isSprinting()) {
                                 return;
                              }

                              this.I = this.z;
                              this.h = var12.movement();
                              if (this.P.Y()) {
                                 this.d = true;
                                 this.n = System.currentTimeMillis();
                                 this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK;
                                 this.P.F("[Velocity] delay until ground, attack enabled, packet kept");
                              }

                              if (this.P.s$Z() && this.P.j$Z()) {
                                 this.Q = true;
                              }

                              this.w(true, false);
                              this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK;
                              String[] var10 = b;
                              this.P.F("[Velocity] cancel alink");
                              return;
                           }

                           this.h = var12.movement();
                           if (this.P.Y() && !this.W) {
                              this.d = true;
                              this.n = System.currentTimeMillis();
                              this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                              this.P.F("[Velocity] delay until ground, no target, packet kept");
                           }

                           this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                           this.T = System.currentTimeMillis();
                           this.H = false;
                           this.w.add(var12);
                           var1.c(true);
                        } else {
                           this.H = false;
                        }

                        return;
                     }
                  }

                  if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY) {
                     Packet var13 = var1.R$Packet();
                     if (var13 instanceof ClientboundSetEntityMotionPacket var16 && var16.id() == com.elowen.utils.PlayerUtils.S$I()) {
                        if (this.E$Z()) {
                           return;
                        }

                        this.h = var16.movement();
                     }

                     if (var13 instanceof ClientboundPlayerPositionPacket || var13 instanceof ClientboundPlayerLookAtPacket) {
                        this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.LAG;
                        this.w.add(var13);
                        var1.c(true);
                        this.a.s(this.P.X$F());
                        return;
                     }

                     if (var13 instanceof ClientboundDisconnectPacket || var13 instanceof ClientboundRespawnPacket) {
                        this.e(false);
                        return;
                     }

                     if (!(var13 instanceof ClientboundPingPacket)
                        && (!(var1.R$Packet() instanceof ClientboundSetEntityMotionPacket var17) || var17.id() != com.elowen.utils.PlayerUtils.S$I())) {
                        return;
                     }

                     this.w.add(var13);
                     var1.c(true);
                  }
               }
            }
         }
      }
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void Z(EventTick var1) {
      Velocity var31 = null;
      NoXZ var15 = null;
      NoXZ var14 = null;
      NoXZ var19 = null;
      NoXZ var20 = null;
      NoXZ var24 = null;
      NoXZ var25 = null;
      NoXZ var26 = null;
      NoXZ var27 = null;
      String var30 = null;
      String var33 = null;
      com.elowen.values.HasValue[] var2;
      int var11;
      this.a.O();
      com.elowen.values.HasValue[] var10000 = c$ArrQ();
      this.v$V();
      var2 = var10000;
      this.j$V();
      long var34;
      var11 = (var34 = this.O - 0L) == 0L ? 0 : (var34 < 0L ? -1 : 1);
      label745:
      if (var2 == null) {
         if (var11 != 0) {
            var11 = ((this.L) ? 1 : 0);
            if (var2 != null) {
               break label745;
            }

            if (!this.L) {
               this.L();
            }
         }

         this.a.f$Z();
         var11 = ((this.c$Z()) ? 1 : 0);
      }

      int var3 = var11;
      var11 = ((this.p) ? 1 : 0);
      label738:
      if (var2 == null) {
         if (this.p) {
            var11 = var3;
            if (var2 != null) {
               break label738;
            }

            if (var3 == 0) {
               this.k = (int)this.P.D$F();
               var11 = this.k;
               if (var2 != null) {
                  break label738;
               }

               if (this.k > 0) {
                  this.e(true);
               }
            }
         }

         this.p = (boolean)((var3) != 0);
         var11 = var3;
      }

      label784: {
         label749: {
            label750: {
               if (var2 == null) {
                  if (var11 == 0) {
                     var11 = this.k;
                     if (var2 != null) {
                        break label750;
                     }

                     if (this.k <= 0) {
                        var15 = this;
                        if (var2 != null) {
                           break label784;
                        }

                        var11 = ((this.L) ? 1 : 0);
                        break label750;
                     }
                  }

                  var14 = this;
                  if (var2 != null) {
                     break label749;
                  }

                  var11 = this.k;
               }

               if (var11 <= 0) {
                  return;
               }

               var14 = this;
               break label749;
            }

            if (var11 != 0) {
               return;
            }

            var15 = this;
            break label784;
         }

         var14.k--;
         return;
      }

      label706:
      if (var2 == null) {
         if (var15.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY) {
            var15 = this;
            if (var2 != null) {
               break label706;
            }

            if (this.D()) {
               this.e(true);
               this.t();
               return;
            }
         }

         var15 = this;
      }

      if (var2 == null) {
         if (var15.P.i$MC().player == null) {
            return;
         }

         var15 = this;
      }

      if (var2 == null) {
         if (var15.v == null) {
            this.v = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
         }

         var15 = this;
      }

      if (var2 == null) {
         if (var15.b$Z()) {
            return;
         }

         var15 = this;
      }

      label752: {
         if (var2 == null) {
            if (var15.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY) {
               break label752;
            }

            var15 = this;
         }

         if (var2 == null) {
            if (!var15.w.isEmpty()) {
               break label752;
            }

            this.H = false;
            this.t();
            var15 = this;
         }

         var15.h = null;
      }

      com.elowen.events.api.types.EventType var16 = var1.s$f();
      com.elowen.events.api.types.EventType var10001 = com.elowen.events.api.types.EventType.POST;
      if (var2 == null) {
         label654:
         if (var16 == com.elowen.events.api.types.EventType.POST) {
            NoXZ var17 = this;
            if (var2 == null) {
               if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.PRE_ATTACK) {
                  break label654;
               }

               var17 = this;
            }

            var11 = ((var17.r()) ? 1 : 0);
            if (var2 == null) {
               if (var11 != 0) {
                  this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                  return;
               }

               var11 = ((this.Q()) ? 1 : 0);
            }

            if (var2 == null) {
               if (var11 != 0) {
                  this.e(true);
                  return;
               }

               var11 = ((this.e(this.I)) ? 1 : 0);
            }

            if (var2 == null) {
               if (var11 != 0) {
                  this.I = null;
                  this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                  return;
               }

               float var35;
               var11 = (var35 = this.P.i$MC().player.distanceTo(this.I) - this.P.S$F()) == 0.0F ? 0 : (var35 < 0.0F ? -1 : 1);
            }

            if (var2 == null) {
               if (var11 > 0) {
                  this.I = null;
                  this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                  return;
               }

               var11 = ((this.P.J$Z()) ? 1 : 0);
            }

            label674: {
               label754: {
                  label672:
                  if (var2 == null) {
                     if (var11 != 0) {
                        var11 = ((this.P.i$MC().player.isSprinting()) ? 1 : 0);
                        if (var2 != null) {
                           break label672;
                        }

                        if (var11 == 0) {
                           this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                           return;
                        }
                     }

                     var19 = this;
                     if (var2 != null) {
                        break label754;
                     }

                     var11 = ((this.P.s$Z()) ? 1 : 0);
                  }

                  if (var11 != 0) {
                     var19 = this;
                     if (var2 != null) {
                        break label754;
                     }

                     if (this.P.j$Z()) {
                        this.Q = true;
                        if (var2 == null) {
                           break label674;
                        }
                     }
                  }

                  var19 = this;
               }

               var19.w(true, false);
            }

            this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK;
         }

         var16 = var1.s$f();
         var10001 = com.elowen.events.api.types.EventType.POST;
      }

      if (var2 == null) {
         label638:
         if (var16 == var10001) {
            var20 = this;
            label644:
            if (var2 == null) {
               if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.LAG) {
                  var20 = this;
                  if (var2 != null) {
                     break label644;
                  }

                  if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.TIMEOUT) {
                     break label638;
                  }
               }

               this.e(true);
               var20 = this;
            }

            var20.t();
         }

         var16 = var1.s$f();
         var10001 = com.elowen.events.api.types.EventType.PRE;
      }

      if (var16 == var10001) {
         label633: {
            label632: {
               label757: {
                  label777: {
                     NoXZ var21 = this;
                     if (var2 == null) {
                        if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK) {
                           break label777;
                        }

                        var21 = this;
                     }

                     var11 = ((var21.P.Y()) ? 1 : 0);
                     label623:
                     if (var2 == null) {
                        if (var11 != 0) {
                           var11 = ((this.d) ? 1 : 0);
                           if (var2 != null) {
                              break label623;
                           }

                           if (this.d) {
                              float var36;
                              var11 = (var36 = (float)(System.currentTimeMillis() - this.n) - this.P.s$F()) == 0.0F ? 0 : (var36 < 0.0F ? -1 : 1);
                              if (var2 != null) {
                                 break label623;
                              }

                              if (var11 >= 0) {
                                 this.e(true);
                                 this.t();
                                 this.P.F("[Velocity] attack timeout, force release");
                                 return;
                              }
                           }
                        }

                        var11 = ((this.R(this.I)) ? 1 : 0);
                     }

                     label613: {
                        label760: {
                           if (var2 == null) {
                              if (var11 != 0) {
                                 this.t();
                                 if (var2 == null) {
                                    break label760;
                                 }
                              }

                              var11 = this.N;
                           }

                           if (var2 != null) {
                              break label613;
                           }

                           label605:
                           if (var11 == 0) {
                              label603: {
                                 label602: {
                                    boolean var4 = this.P.J$Z();
                                    boolean var23 = var4;
                                    if (var2 == null) {
                                       if (!var4) {
                                          break label602;
                                       }

                                       var24 = this;
                                       if (var2 != null) {
                                          break label603;
                                       }

                                       var23 = this.P.i$MC().player.isSprinting();
                                    }

                                    if (!var23) {
                                       this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
                                       if (var2 == null) {
                                          break label605;
                                       }
                                    }
                                 }

                                 var24 = this;
                              }

                              var24.N = (int)this.P.I();
                           }
                        }

                        var11 = this.N;
                     }

                     label588:
                     if (var2 == null) {
                        label586:
                        if (var11 > 0) {
                           var11 = ((this.P.J$Z()) ? 1 : 0);
                           label584:
                           if (var2 == null) {
                              if (var11 != 0) {
                                 var11 = ((this.P.i$MC().player.isSprinting()) ? 1 : 0);
                                 if (var2 != null) {
                                    break label584;
                                 }

                                 if (var11 == 0) {
                                    break label586;
                                 }
                              }

                              var11 = ((this.P.s$Z()) ? 1 : 0);
                           }

                           if (var2 == null) {
                              if (var11 != 0) {
                                 this.E$V();
                                 if (var2 == null) {
                                    break label586;
                                 }
                              }

                              var11 = this.o;
                           }

                           if (var2 == null) {
                              if (var11 > 0) {
                                 this.o--;
                                 if (var2 == null) {
                                    break label586;
                                 }
                              }

                              this.U(this.I);
                              var11 = ((this.l) ? 1 : 0);
                           }

                           if (var2 != null) {
                              break label588;
                           }

                           if (var11 != 0) {
                              this.Z();
                              this.N--;
                              var11 = this.N;
                              if (var2 != null) {
                                 break label588;
                              }

                              if (this.N > 0) {
                                 this.o = (int)this.P.h$F();
                              }
                           }
                        }

                        var11 = this.N;
                     }

                     if (var2 != null) {
                        break label633;
                     }

                     if (var11 != 0) {
                        break label757;
                     }

                     var25 = this;
                     if (var2 != null) {
                        break label632;
                     }

                     if (this.m != com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK) {
                        break label757;
                     }

                     this.t();
                     if (var2 == null) {
                        break label757;
                     }
                  }

                  float var37;
                  var11 = (var37 = (float)(System.currentTimeMillis() - this.T) - this.P.s$F()) == 0.0F ? 0 : (var37 < 0.0F ? -1 : 1);
                  if (var2 != null) {
                     break label633;
                  }

                  if (var11 >= 0) {
                     var25 = this;
                     if (var2 != null) {
                        break label632;
                     }

                     if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY) {
                        var26 = this;
                        label544:
                        if (var2 == null) {
                           if (this.P.Y()) {
                              var26 = this;
                              if (var2 != null) {
                                 break label544;
                              }

                              if (this.d) {
                                 this.e(true);
                                 this.P.F("[Velocity] timeout reached, force release knockback");
                              }
                           }

                           var26 = this;
                        }

                        var26.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.TIMEOUT;
                     }
                  }
               }

               var25 = this;
            }

            var11 = ((var25.P.Y()) ? 1 : 0);
         }

         label764: {
            label530:
            if (var2 == null) {
               label528: {
                  if (var11 != 0) {
                     var11 = ((this.d) ? 1 : 0);
                     if (var2 != null) {
                        break label530;
                     }

                     if (this.d) {
                        var27 = this;
                        if (var2 != null) {
                           break label528;
                        }

                        label523:
                        if (this.P.i$MC().player != null) {
                           NoXZ var28 = this;
                           if (var2 == null) {
                              if (this.P.i$MC().player.onGround()) {
                                 this.R++;
                                 float var38;
                                 var11 = (var38 = this.R - this.P.v$F()) == 0.0F ? 0 : (var38 < 0.0F ? -1 : 1);
                                 if (var2 != null) {
                                    break label530;
                                 }

                                 if (var11 <= 0) {
                                    break label523;
                                 }

                                 if ((float)(System.currentTimeMillis() - this.n) < this.P.s$F()) {
                                    this.e(true);
                                    this.P.F("[Velocity] released knockback on ground");
                                 }

                                 this.d = false;
                                 this.R = 0;
                                 if (var2 == null) {
                                    break label523;
                                 }
                              }

                              var28 = this;
                           }

                           var28.R = 0;
                        }
                     }
                  }

                  var27 = this;
               }

               if (var2 != null) {
                  break label764;
               }

               var11 = ((var27.H) ? 1 : 0);
            }

            if (var11 != 0) {
               var27 = this;
               if (var2 != null) {
                  break label764;
               }

               if (this.P.i$MC().player.hurtTime == 0) {
                  this.H = false;
               }
            }

            var27 = this;
         }

         label767: {
            if (var2 == null) {
               if (var27.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY) {
                  long var10 = (System.currentTimeMillis() - this.T) / 50L;
                  long var39;
                  var11 = (var39 = var10 - 0L) == 0L ? 0 : (var39 < 0L ? -1 : 1);
                  if (var2 == null) {
                     if (var11 < 0) {
                        var10 = 0L;
                     }

                     var11 = ((this.P.Y()) ? 1 : 0);
                  }

                  label493: {
                     label492: {
                        if (var2 == null) {
                           if (var11 == 0) {
                              break label492;
                           }

                           var11 = ((this.d) ? 1 : 0);
                        }

                        if (var11 != 0) {
                           var30 = " (ground wait)";
                           break label493;
                        }
                     }

                     var30 = "";
                  }

                  String var6 = var30;
                  String var7 = var6;
                  long var8 = var10;
                  this.P.X("Alink " + var8 + var7);
                  if (var2 == null) {
                     break label767;
                  }
               }

               var27 = this;
            }

            label479: {
               label478: {
                  var31 = var27.P;
                  boolean var32 = this.P.Y();
                  if (var2 == null) {
                     if (!var32) {
                        break label478;
                     }

                     var32 = this.d;
                  }

                  if (var32) {
                     var33 = " ⏳";
                     break label479;
                  }
               }

               var33 = "";
            }

            var31.X("NoXZ" + var33);
         }

         f = this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
      }
   }

   @Override
   public void E(EventMoveInput var1) {
      com.elowen.values.HasValue[] var2 = c$ArrQ();
      if (!this.c$Z() && this.k <= 0) {
         if (!this.L) {
            if (!this.D()) {
               if (!this.r() && this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.PRE_ATTACK) {
                  this.q(var1);
               } else if (!this.r() && this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK) {
                  this.q(var1);
               } else if (this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY && this.h != null) {
                  Player var3 = this.z;
                  if (var3 != null && !this.e(var3)) {
                     this.I = var3;
                     this.q(var1);
                     if (!this.r() && this.P.i$MC().player.distanceTo(this.I) <= this.P.S$F()) {
                        this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.PRE_ATTACK;
                     }
                  } else {
                     Player var4 = null;
                     if (this.P.i$MC().player != null && this.P.i$MC().level != null) {
                        HitResult var5 = this.P.i$MC().hitResult;
                        if (var5 != null
                           && var5.getType() == Type.ENTITY
                           && ((EntityHitResult)var5).getEntity() instanceof Player var7
                           && var7 != this.P.i$MC().player
                           && !this.e(var7)
                           && this.P.i$MC().player.distanceTo(var7) <= this.P.S$F()) {
                           var4 = var7;
                        }
                     }

                     if (var4 != null) {
                        this.I = var4;
                        this.q(var1);
                        if (!this.r() && this.P.i$MC().player.distanceTo(this.I) <= this.P.S$F()) {
                           this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.PRE_ATTACK;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void c$V() {
      this.H = false;
      c$ArrQ();
      this.I = null;
      this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE;
      this.K = 0L;
      this.p = this.c$Z();
      this.k = 0;
      this.v = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
      this.N = 0;
      this.o = 0;
      this.x();
      this.W = false;
      this.z = null;
      this.a.e();
      this.e = -1L;
      this.i = -1L;
      this.Q = false;
      this.d = false;
      this.n = 0L;
      this.R = 0;
      f = false;
      this.l = false;
      if (com.elowen.values.HasValue.X$Z()) {
         l(new com.elowen.values.HasValue[2]);
      }
   }

   @Override
   public void z$V() {
      this.H = false;
      this.I = null;
      this.m = com.elowen.modules.impl.combat.velocity.NoXZ$Stage.NONE;
      this.e(true);
      this.p = false;
      this.k = 0;
      this.N = 0;
      this.o = 0;
      this.x();
      this.W = false;
      this.z = null;
      this.a.e();
      this.e = -1L;
      this.i = -1L;
      this.Q = false;
      this.d = false;
      this.n = 0L;
      this.R = 0;
      f = false;
      this.l = false;
   }

   @Override
   public boolean z$Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      return this.D()
         ? false
         : this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY
            || this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.PRE_ATTACK
            || this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.ATTACK;
   }

   @Override
   public boolean X$Z() {
      com.elowen.values.HasValue[] var1 = c$ArrQ();
      return this.D() ? false : this.m == com.elowen.modules.impl.combat.velocity.NoXZ$Stage.DELAY;
   }

   static {
      l(null);
      f = false;
   }

   public static void l(com.elowen.values.HasValue[] var0) {
      u = var0;
   }

   public static com.elowen.values.HasValue[] c$ArrQ() {
      return u;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
