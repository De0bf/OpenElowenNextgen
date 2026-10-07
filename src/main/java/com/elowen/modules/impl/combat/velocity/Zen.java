package com.elowen.modules.impl.combat.velocity;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Module;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.PlayerUtils;
import java.util.concurrent.LinkedBlockingDeque;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Zen implements VelocityMode {
   private Velocity n;
   public static boolean j;
   public static int O;
   private int Q = 0;
   private Entity C = null;
   private int A = 0;
   private int X = 0;
   private boolean d = false;
   private int t = 0;
   private int M = 0;
   private boolean F = false;
   private int B = 0;
   private ClientboundSetEntityMotionPacket v = null;
   private final LinkedBlockingDeque h = new LinkedBlockingDeque();
   private final LinkedBlockingDeque R = new LinkedBlockingDeque();
   private volatile boolean Y = false;
   private float b = 0.0F;
   private boolean s = false;
   private boolean x = false;
   private static final String[] a = new String[]{"Alink Timeout", "Alink Wait", "Zen", "Attack (", "not sprinting", "Attack (", "done", "ground", "Flag Detected"};
   @Override
   public void C(Velocity var1) {
      this.n = var1;
   }

   private void o$V() {
      this.f$V();
      this.X = 0;
      this.d = false;
      this.t = 0;
      this.M = 0;
      this.C();
   }

   private void f$V() {
      this.C = null;
      this.A = 0;
   }

   private void C() {
      this.F = false;
      this.B = 0;
      this.v = null;
      this.h.clear();
      this.R.clear();
      this.Y = false;
      this.b = 0.0F;
      this.s = false;
      Elowen.i = 1.0F;
   }

   private boolean Y() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().player != null && this.n.i$MC().level != null) {
         if (this.n.i$MC().player.isDeadOrDying() || !this.n.i$MC().player.isAlive() || this.n.i$MC().player.getHealth() <= 0.0F) {
            return true;
         }

         if (this.n.i$MC().player.isSpectator() || this.n.i$MC().player.getAbilities().flying) {
            return true;
         }

         if (this.n.i$MC().player.isInLava()
            || this.n.i$MC().player.isOnFire()
            || this.n.i$MC().player.isInWater()
            || this.n.i$MC().player.onClimbable()
            || this.n.i$MC().player.isSleeping()) {
            return true;
         }

         if (this.n.i$MC().level.getBlockState(this.n.i$MC().player.blockPosition()).is(Blocks.COBWEB)) {
            return true;
         }

         Module var2 = Elowen.S$Elowen().q$ModuleManager().A(Stuck.class);
         return var2 != null && var2.w();
      } else {
         return true;
      }
   }

   private double P(Entity var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().player == null) {
         return Double.MAX_VALUE;
      }

      Vec3 var3 = this.n.i$MC().player.getEyePosition(1.0F);
      AABB var4 = var1.getBoundingBox();
      double var5 = Math.max(var4.minX, Math.min(var3.x, var4.maxX));
      double var7 = Math.max(var4.minY, Math.min(var3.y, var4.maxY));
      double var9 = Math.max(var4.minZ, Math.min(var3.z, var4.maxZ));
      return var3.distanceTo(new Vec3(var5, var7, var9));
   }

   private Entity J$Entity() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().hitResult != null && this.n.i$MC().hitResult.getType() == Type.ENTITY) {
         Entity var2 = ((EntityHitResult)this.n.i$MC().hitResult).getEntity();
         if (var2 instanceof LivingEntity && var2 != this.n.i$MC().player && var2.isAlive() && !var2.isSpectator()) {
            return var2;
         }
      }

      return null;
   }

   private Entity j$Entity() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      return Aura.cj != null ? Aura.cj : this.J$Entity();
   }

   private boolean K(Entity var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1 != null && var1.isAlive()) {
         if (!(var1 instanceof LivingEntity var3 && (var3.isDeadOrDying() || var3.getHealth() <= 0.0F))) {
            double var5 = 3.7F;
            return this.P(var1) <= var5;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void F() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.C != null && this.C.isAlive()) {
         if (this.P(this.C) > 3.7F) {
            this.f$V();
         } else {
            j = true;
            O = this.A--;
            this.Q = 2;
            this.w(this.C);
            if (this.A <= 0) {
               this.f$V();
               if (this.n.s$Z()) {
                  this.n.F("Attack (" + (int)this.n.I() + ")");
               }
            }
         }
      } else {
         this.f$V();
      }
   }

   private boolean w(Entity var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().player == null || this.n.i$MC().gameMode == null) {
         return false;
      }

      if (this.n.t() && !this.n.i$MC().player.isSprinting()) {
         this.n.F("not sprinting");
         return false;
      }

      boolean var3 = this.n.i$MC().player.isSprinting();
      if (var3) {
         this.n.i$MC().player.setSprinting(false);
      }

      this.n.i$MC().gameMode.attack(this.n.i$MC().player, var1);
      PlayerUtils.s(InteractionHand.MAIN_HAND);
      if (var3) {
         Vec3 var4 = this.n.i$MC().player.getDeltaMovement();
         double var10001 = var4.x * 0.6;
         this.n.i$MC().player.setDeltaMovement(var10001, var4.y, var4.z * 0.6);
      }

      if (!this.n.s$Z()) {
         this.n.F("Attack (" + this.A + ")");
      }

      return true;
   }

   private void q$V() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().getConnection() != null) {
         while (!this.R.isEmpty()) {
            Packet var2 = (Packet)this.R.poll();
            if (var2 != null) {
               try {
                  this.n.i$MC().getConnection().send(var2);
               } catch (Exception var4) {
                  var4.printStackTrace();
               }
               break;
            }
         }
      }
   }

   private void T$V() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.v != null && this.n.i$MC().getConnection() != null) {
         try {
            this.v.handle(this.n.i$MC().getConnection());
         } catch (Exception var3) {
            var3.printStackTrace();
         }

         this.v = null;
      }
   }

   private void W$V() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().getConnection() != null) {
         this.x = true;
      }
   }

   private boolean V(Packet var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      return var1 instanceof ClientboundSetEntityMotionPacket
         || var1 instanceof ClientboundSetHealthPacket
         || var1 instanceof ClientboundPlayerPositionPacket
         || var1 instanceof ClientboundSoundPacket
         || var1 instanceof ClientboundPlayerChatPacket
         || var1 instanceof ClientboundPlayerCombatKillPacket
         || var1 instanceof ClientboundContainerClosePacket
         || var1 instanceof ClientboundHurtAnimationPacket
         || var1 instanceof ClientboundSetTitleTextPacket
         || var1 instanceof ClientboundSetPlayerTeamPacket
         || var1 instanceof ClientboundSystemChatPacket
         || var1 instanceof ClientboundDisconnectPacket
         || var1 instanceof ClientboundAnimatePacket && ((ClientboundAnimatePacket)var1).getId() != PlayerUtils.S$I();
   }

   private void p() {
      this.Y = true;
      this.q$V();
      this.T$V();
      this.W$V();
      this.Y = false;
      this.F = false;
      this.B = 0;
      this.b = 0.0F;
      this.s = false;
      Elowen.i = 1.0F;
   }

   @Override
   public void J(EventPacket var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().player != null) {
         if (!this.Y) {
            if (!this.Y()) {
               Packet var3 = var1.R$Packet();
               if (var3 instanceof ServerboundMovePlayerPacket && this.F) {
                  this.R.add(var3);
                  var1.c(true);
               } else {
                  if (var1.M() == com.elowen.events.api.types.EventType.RECEIVE) {
                     if (var1.c$Z()) {
                        return;
                     }

                     if (var3 instanceof ClientboundPlayerPositionPacket) {
                        if (this.F) {
                           this.p();
                        }

                        this.C();
                        String[] var11 = a;
                        this.n.F("Flag Detected");
                        this.X = 2;
                     }

                     if (this.X > 0) {
                        return;
                     }

                     if (this.F) {
                        if (!this.V(var3)) {
                           this.h.add(var3);
                           var1.c(true);
                        }

                        return;
                     }

                     if (var3 instanceof ClientboundSetEntityMotionPacket var4 && var4.id() == PlayerUtils.S$I()) {
                        double var5 = -var4.movement().x;
                        double var7 = -var4.movement().z;
                        if (Math.abs(var5) > 0.01 || Math.abs(var7) > 0.01) {
                           this.M = 1;
                        }

                        if (var4.movement().y > 0.0) {
                           this.t = this.t % 100 + 100;
                           if (this.t >= 100) {
                              this.d = true;
                           }

                           Entity var9 = this.j$Entity();
                           boolean var10 = this.K(var9) && this.n.i$MC().player.isSprinting();
                           if (!this.n.i$MC().player.onGround()) {
                              this.F = true;
                              this.B = 0;
                              this.v = var4;
                              var1.c(true);
                           }

                           if (var10) {
                              this.C = var9;
                              this.A = (int)this.n.I();
                           }

                           this.F = true;
                           this.B = 0;
                           this.v = var4;
                           var1.c(true);
                           this.n.F("Alink Wait");
                        }
                     }
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
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1.s$f() == com.elowen.events.api.types.EventType.PRE) {
         if (this.n.i$MC().player != null) {
            if (this.Q > 0) {
               this.Q--;
               if (this.Q <= 0) {
                  j = false;
                  O = 0;
               }
            }

            if (this.M > 0) {
               this.M++;
               if (this.M > 2) {
                  this.M = 0;
               }
            }

            if (!this.n.i$MC().player.isDeadOrDying() && this.n.i$MC().player.isAlive() && !this.Y()) {
               if (this.X > 0) {
                  this.X--;
                  this.f$V();
               }

               if (this.F) {
                  this.B++;
                  boolean var3 = this.n.s$Z();
                  if (var3 && this.b < 3.0F) {
                     float var4 = 0.5F;
                     Elowen.i = var4;
                     this.b += 1.0F - var4;
                     this.b = Math.min(this.b, 3.0F);
                  }

                  boolean var10 = this.n.i$MC().player.onGround();
                  boolean var5 = this.B >= 12;
                  if (var10 || var5) {
                     String var10001;
                     if (var5) {
                        var10001 = "Alink Timeout";
                     } else {
                        String[] var9 = a;
                        var10001 = "ground";
                     }

                     this.n.F(var10001);
                     if (var3) {
                        Elowen.i = 1.0F;
                     }

                     Entity var6 = this.j$Entity();
                     boolean var7 = this.K(var6);
                     boolean var8 = this.n.i$MC().player.isSprinting();
                     if (var10 && var7 && var8) {
                        this.Y = true;
                        this.C = var6;
                        this.A = (int)this.n.I();
                        this.q$V();
                        this.T$V();
                        if (var3 && this.b > 0.0F) {
                           this.A = (int)this.b;
                           this.W$V();
                           this.F = false;
                           this.B = 0;
                           this.Y = false;
                           this.s = true;
                           Elowen.i = 4.0F;
                        }

                        this.F();
                        this.W$V();
                        this.F = false;
                        this.B = 0;
                        this.Y = false;
                     }

                     this.p();
                     if (var3) {
                        this.b = 0.0F;
                     }

                     if (var10 && this.n.i$MC().player.isSprinting()) {
                        this.n.i$MC().player.setSprinting(false);
                     }
                  }
               } else {
                  if (this.s) {
                     this.b--;
                     if (this.b <= 0.0F) {
                        this.b = 0.0F;
                        this.s = false;
                        Elowen.i = 1.0F;
                        this.n.F("done");
                     }
                  }

                  if (this.A > 0 && this.C != null) {
                     this.F();
                  }
               }
            } else {
               this.f$V();
               if (this.F) {
                  this.p();
               }

               if (this.s) {
                  this.s = false;
                  this.b = 0.0F;
                  Elowen.i = 1.0F;
               }
            }
         }
      }
   }

   @Override
   public void E(EventMoveInput var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.n.i$MC().player != null) {
         if (this.M > 0) {
            var1.b(1.0F);
         }

         if (this.d) {
            this.d = false;
            if (this.n.i$MC().player.onGround() && this.n.i$MC().player.isSprinting() && !this.n.i$MC().player.hasEffect(MobEffects.JUMP_BOOST) && !this.Y()) {
               this.n.i$MC().player.setSprinting(true);
            }
         }
      }
   }

   @Override
   public void B(com.elowen.events.impl.EventMotion var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE && this.x) {
         while (!this.h.isEmpty()) {
            Packet var3 = (Packet)this.h.poll();
            if (var3 != null) {
               try {
                  var3.handle(this.n.i$MC().getConnection());
               } catch (Exception var5) {
                  var5.printStackTrace();
               }
               break;
            }
         }

         this.x = false;
      }
   }

   @Override
   public void c$V() {
      this.o$V();
      this.n.X("Zen");
   }

   @Override
   public void z$V() {
      this.o$V();
   }

   @Override
   public boolean z$Z() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      return this.M > 0 || this.F;
   }

   @Override
   public boolean X$Z() {
      return this.F;
   }

   static {
      j = false;
      O = 0;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
