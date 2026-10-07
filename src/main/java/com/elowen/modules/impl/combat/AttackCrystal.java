package com.elowen.modules.impl.combat;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.NetworkUtils;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.utils.rotation.Rotation;
import com.elowen.utils.rotation.RotationManager;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "CrystalAura", a = "Automatically attacks end crystals", M = Category.COMBAT)
public class AttackCrystal extends Module {
   public static Vector2f D;
   private static final double m = 3.0;
   private static final double c = 1.0;
   BooleanValue r;
   private static final String b;

   public AttackCrystal() {
      this.r = ValueBuilder.m(this, b).h(false).f$K().f$O();
   }

   @Override
   public void h$V() {
      D = null;
   }

   @Override
   public void q$V() {
      D = null;
   }

   @EventTarget
   public void W(EventTick var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.s$f() == EventType.PRE && G.player != null && G.level != null) {
         Vec3 var3 = G.player.getEyePosition();
         AABB var4 = G.player.getBoundingBox().inflate(3.0);
         List<EndCrystal> var5 = G.level.getEntitiesOfClass(EndCrystal.class, var4, AttackCrystal::deobfLambda$onTick$0);
         if (var5.isEmpty()) {
            D = null;
         } else {
            EndCrystal var6 = null;
            Vec3 var7 = null;
            boolean var8 = false;
            double var9 = Double.MAX_VALUE;

            for (EndCrystal var12 : var5) {
               AABB var13 = this.T$AABB(var12);
               if (var13.contains(var3)) {
                  var6 = var12;
                  var8 = true;
               }

               if (!(this.i(var3, var13) > 3.0)) {
                  Optional var14 = this.M(var3, var13);
                  if (var14.isPresent()) {
                     Vec3 var15 = (Vec3)var14.get();
                     double var16 = var3.distanceTo(var15);
                     if (!(var16 > 3.0)) {
                        if (var16 < var9) {
                           var7 = var15;
                           var6 = var12;
                        }
                        break;
                     }
                  }
               }
            }

            if (var6 == null) {
               D = null;
            } else if (var8) {
               D = null;
               this.T$V(var6);
            } else if (var7 != null && this.X(var3, var7)) {
               Rotation var18 = com.elowen.utils.rotation.RotationUtils.X(var3, var7);
               if (var18 == null) {
                  D = null;
               } else {
                  D = new Vector2f(var18.o$F(), var18.y$F());
                  RotationManager.y(var18.o$F(), var18.y$F());
                  if (this.g(var6, 3.0)) {
                     this.T$V(var6);
                  }
               }
            } else {
               D = null;
            }
         }
      }
   }

   private void T$V(EndCrystal var1) {
      G.gameMode.attack(G.player, var1);
      com.elowen.utils.PlayerUtils.s(InteractionHand.MAIN_HAND);
   }

   private boolean g(EndCrystal var1, double var2) {
      boolean var4 = Velocity.p();
      if (G.player == null) {
         return false;
      }

      Vector2f var5 = RotationManager.a$h();
      HitResult var6 = com.elowen.utils.RayTraceUtils.G(new Rotation(var5.H, var5.E), var2, 0.0F, G.player, var1, false, 1.0F);
      HitResult var10000 = var6;
      if (!var4) {
         if (!(var6 instanceof EntityHitResult)) {
            return false;
         }

         var10000 = var6;
      }

      return ((EntityHitResult)var10000).getEntity() == var1;
   }

   @EventTarget
   public void S(EventPacket var1) {
      AttackCrystal var15 = null;
      boolean var2 = Velocity.p();
      EventPacket var10000 = var1;
      if (!var2) {
         if (var1.M() != EventType.RECEIVE) {
            return;
         }

         var10000 = var1;
      }

      boolean var11 = var10000.R$Packet() instanceof ClientboundAddEntityPacket;
      if (!var2) {
         if (!var11) {
            return;
         }

         var11 = this.r.w();
      }

      if (var11) {
         ClientboundAddEntityPacket var3 = (ClientboundAddEntityPacket)var1.R$Packet();
         if (var3.getType() == EntityTypes.END_CRYSTAL) {
            EndCrystal var4 = new EndCrystal(G.level, var3.getX(), var3.getY(), var3.getZ());
            var4.setId(var3.getId());
            LocalPlayer var12 = G.player;
            if (!var2) {
               if (G.player.distanceTo(var4) > 4.0F) {
                  return;
               }

               var12 = G.player;
            }

            EndCrystal var10001;
            label90: {
               Vector2f var7;
               label110: {
                  Vec3 var5;
                  AABB var6;
                  label88: {
                     var5 = var12.getEyePosition();
                     var6 = this.T$AABB(var4);
                     if (!var2) {
                        if (!var6.contains(var5)) {
                           break label88;
                        }

                        D = null;
                     }

                     var7 = new Vector2f(G.player.getYRot(), G.player.getXRot());
                     if (!var2) {
                        break label110;
                     }
                  }

                  Optional var8 = this.M(var5, var6);
                  Object var13 = var8;
                  if (!var2) {
                     if (!var8.isPresent()) {
                        return;
                     }

                     var13 = var8.get();
                  }

                  Vec3 var9 = (Vec3)var13;
                  double var16;
                  int var14 = (var16 = var5.distanceTo(var9) - 3.0) == 0.0 ? 0 : (var16 < 0.0 ? -1 : 1);
                  if (!var2) {
                     if (var14 > 0) {
                        return;
                     }

                     var14 = ((this.X(var5, var9)) ? 1 : 0);
                  }

                  if (var14 == 0) {
                     return;
                  }

                  Rotation var10 = com.elowen.utils.rotation.RotationUtils.X(var5, var9);
                  if (var10 == null) {
                     return;
                  }

                  var7 = new Vector2f(var10.o$F(), var10.y$F());
                  D = var7;
                  var15 = this;
                  var10001 = var4;
                  if (var2) {
                     break label90;
                  }

                  if (!this.g(var4, 3.0)) {
                     return;
                  }
               }

               G.getConnection()
                  .send(new PosRot(G.player.getX(), G.player.getY(), G.player.getZ(), var7.S$F(), var7.p(), G.player.onGround(), G.player.horizontalCollision));
               Vector2f var10003 = var7;
               NetworkUtils.h(var10004 -> deobfLambda$onPacket$0(var10003, var10004));
               RotationManager.y(var7.S$F(), var7.p());
               G.getConnection().send(new ServerboundAttackPacket(var4.getId()));
               var15 = this;
               var10001 = var4;
            }

            var15.T$V(var10001);
         }
      }
   }

   private AABB T$AABB(EndCrystal var1) {
      double var2 = var1.getX();
      double var4 = var1.getY();
      double var6 = var1.getZ();
      return new AABB(var2 - 1.0, var4, var6 - 1.0, var2 + 1.0, var4 + 2.0, var6 + 1.0);
   }

   private Optional M(Vec3 var1, AABB var2) {
      Vec3 var3 = var2.getCenter();
      return var2.clip(var1, var3);
   }

   private double i(Vec3 var1, AABB var2) {
      double var3 = Math.max(var2.minX - var1.x, 0.0);
      var3 = Math.max(var3, var1.x - var2.maxX);
      double var5 = Math.max(var2.minY - var1.y, 0.0);
      var5 = Math.max(var5, var1.y - var2.maxY);
      double var7 = Math.max(var2.minZ - var1.z, 0.0);
      var7 = Math.max(var7, var1.z - var2.maxZ);
      return Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
   }

   private boolean X(Vec3 var1, Vec3 var2) {
      boolean var3 = Velocity.o$Z();
      if (G.level != null && G.player != null) {
         HitResult var4 = com.elowen.utils.RayTraceUtils.c(var1, var2, Block.COLLIDER, Fluid.NONE, G.player);
         return var4.getType() == Type.MISS;
      } else {
         return false;
      }
   }

   private static Packet deobfLambda$onPacket$0(Vector2f var0, int var1) {
      return new ServerboundUseItemPacket(InteractionHand.MAIN_HAND, var1, var0.S$F(), var0.p());
   }

   private static boolean deobfLambda$onTick$0(EndCrystal var0) {
      return true;
   }

   static {
      b = "Attack on Packet (Danger)";
      D = null;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
