package com.elowen.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class RayTraceUtils {
   private static final Minecraft f = Minecraft.getInstance();

   public static HitResult B(float var0, Vector2f var1) {
      String var10000 = Vector2f.e();
      HitResult var3 = null;
      Entity var4 = f.getCameraEntity();
      String var2 = var10000;
      if (var4 != null) {
         Minecraft var7 = f;
         if (var2 == null) {
            if (f.level == null) {
               return var3;
            }

            var7 = f;
         }

         double var5 = var7.player.blockInteractionRange();
         var3 = r(var5, var0, true, var1.S$F(), var1.p());
      }

      return var3;
   }

   public static HitResult v(double var0, float var2, boolean var3, Vector2f var4) {
      HitResult var5 = null;
      Entity var6 = f.getCameraEntity();
      if (var6 != null && f.level != null) {
         var5 = r(var0, var2, var3, var4.S$F(), var4.p());
      }

      return var5;
   }

   public static HitResult I(float var0, com.elowen.utils.rotation.Rotation var1) {
      String var10000 = Vector2f.e();
      HitResult var3 = null;
      String var2 = var10000;
      Entity var4 = f.getCameraEntity();
      if (var4 != null) {
         Minecraft var7 = f;
         if (var2 == null) {
            if (f.level == null) {
               return var3;
            }

            var7 = f;
         }

         double var5 = var7.player.blockInteractionRange();
         var3 = r(var5, var0, true, var1.o$F(), var1.y$F());
      }

      return var3;
   }

   public static HitResult a(double var0, float var2, boolean var3, com.elowen.utils.rotation.Rotation var4) {
      HitResult var5 = null;
      Entity var6 = f.getCameraEntity();
      if (var6 != null && f.level != null) {
         var5 = r(var0, var2, var3, var4.o$F(), var4.y$F());
      }

      return var5;
   }

   public static HitResult h(com.elowen.utils.rotation.Rotation var0, double var1, float var3, Entity var4, Entity var5, boolean var6) {
      return G(var0, var1, var3, var4, var5, var6, f.getDeltaTracker().getGameTimeDeltaPartialTick(false));
   }

   public static HitResult G(com.elowen.utils.rotation.Rotation var0, double var1, float var3, Entity var4, Entity var5, boolean var6, float var7) {
      String var8 = Vector2f.e();
      if (var4 != null && f.level != null) {
         Vec3 var9 = var4.getEyePosition(var7);
         Vec3 var10 = com.elowen.utils.rotation.RotationUtils.n(var0);
         Vec3 var11 = var9.add(var10.x * var1, var10.y * var1, var10.z * var1);
         BlockHitResult var12 = null;
         double var13 = var1;
         if (!var6) {
            var12 = f.level.clip(new ClipContext(var9, var11, Block.COLLIDER, Fluid.NONE, var4));
            var13 = var12.getType() == Type.BLOCK ? var9.distanceTo(var12.getLocation()) : var1;
         }

         double var15 = Math.min(var1, var13) + var3;
         AABB var17 = new AABB(var9.x - var15, var9.y - var15, var9.z - var15, var9.x + var15, var9.y + var15, var9.z + var15);
         List var18 = f.level.getEntitiesOfClass(Entity.class, var17, var10000 -> deobfLambda$rayCast$0(var4, var5, var10000));
         Entity var19 = null;
         Vec3 var20 = null;
         double var21 = Math.min(var1, var13);
         var21 *= var21;
         Iterator var23 = var18.iterator();
         while (var23.hasNext()) {
            Entity var24 = (Entity)var23.next();
            AABB var25 = var24.getBoundingBox().inflate(var3);
            Optional var26 = var25.clip(var9, var11);
            if (var26.isPresent()) {
               Vec3 var27 = (Vec3)var26.get();
               double var28 = var9.distanceToSqr(var27);
               if (var28 < var21) {
                  boolean var30 = true;
                  if (!var6) {
                     BlockHitResult var31 = f.level.clip(new ClipContext(var9, var27, Block.COLLIDER, Fluid.NONE, var4));
                     if (var31.getType() == Type.BLOCK && var9.distanceToSqr(var31.getLocation()) <= var28) {
                        var30 = false;
                     }
                  }

                  if (var30) {
                     var19 = var24;
                     var20 = var27;
                  }
               }
            }
         }

         if (var19 != null) {
            return new EntityHitResult(var19, var20);
         } else {
            return !var6 && var12 != null ? var12 : f.level.clip(new ClipContext(var9, var11, Block.COLLIDER, Fluid.NONE, var4));
         }
      } else {
         return null;
      }
   }

   public static Vec3 c(float var0, float var1) {
      float var2 = var1 * (float) (Math.PI / 180.0);
      float var3 = -var0 * (float) (Math.PI / 180.0);
      float var4 = Mth.cos(var3);
      float var5 = Mth.sin(var3);
      float var6 = Mth.cos(var2);
      float var7 = Mth.sin(var2);
      return new Vec3(var5 * var6, -var7, var4 * var6);
   }

   public static HitResult r(double var0, float var2, boolean var3, float var4, float var5) {
      Vec3 var6 = f.player.getEyePosition(var2);
      Vec3 var7 = c(var4, var5);
      Vec3 var8 = var6.add(var7.x * var0, var7.y * var0, var7.z * var0);
      return f.player.level().clip(new ClipContext(var6, var8, Block.OUTLINE, var3 ? Fluid.ANY : Fluid.NONE, f.player));
   }

   public static HitResult g(Vec3 var0, Vec3 var1, boolean var2, boolean var3, boolean var4, Entity var5) {
      String var6 = Vector2f.e();
      if (var3) {
         Block var7 = Block.COLLIDER;
      }

      Block var10 = var4 ? Block.VISUAL : Block.OUTLINE;
      Fluid var8 = var2 ? Fluid.ANY : Fluid.NONE;
      ClipContext var9 = new ClipContext(var0, var1, var10, var8, var5);
      return f.level.clip(var9);
   }

   public static EntityHitResult i(AABB var0, Vec3 var1, Vec3 var2) {
      Optional<Vec3> var3 = var0.clip(var1, var2);
      return var3.map(RayTraceUtils::deobfLambda$calculateIntercept$0).orElse(null);
   }

   public static HitResult c(Vec3 var0, Vec3 var1, Block var2, Fluid var3, Entity var4) {
      ClipContext var5 = new ClipContext(var0, var1, var2, var3, var4);
      return f.level.clip(var5);
   }

   public static HitResult e(float var0, float var1, double var2, Block var4, Fluid var5, Entity var6) {
      Vec3 var7 = f.player.getEyePosition(f.getDeltaTracker().getGameTimeDeltaPartialTick(false));
      Vec3 var8 = c(var0, var1);
      Vec3 var9 = var7.add(var8.x * var2, var8.y * var2, var8.z * var2);
      return c(var7, var9, var4, var5, var6);
   }

   public static HitResult n(com.elowen.utils.rotation.Rotation var0, double var1, Block var3, Fluid var4) {
      return e(var0.o$F(), var0.y$F(), var1, var3, var4, f.player);
   }

   private static EntityHitResult deobfLambda$calculateIntercept$0(Vec3 var0) {
      return new EntityHitResult(null, var0);
   }

   private static boolean deobfLambda$rayCast$0(Entity var0, Entity var1, Entity var2) {
      String var3 = Vector2f.e();
      return var2 != var0 && (var1 == null || var2 == var1) && EntitySelector.NO_SPECTATORS.test(var2) && var2.isPickable();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
