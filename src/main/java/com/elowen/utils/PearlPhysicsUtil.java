package com.elowen.utils;

import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class PearlPhysicsUtil {
   public static final double r = 1.5;
   public static final double U = 0.03;
   public static final double k = 0.99;

   private PearlPhysicsUtil() {
   }

   public static Object[] p(Entity var0, ClientLevel var1) {
      double var3 = var0.getX();
      double var5 = var0.getY();
      double var7 = var0.getZ();
      String var10000 = Vector2f.e();
      double var9 = var0.getDeltaMovement().x;
      double var11 = var0.getDeltaMovement().y;
      String var2 = var10000;
      double var13 = var0.getDeltaMovement().z;
      List var15 = var1.getEntities(var0, var0.getBoundingBox().inflate(128.0), var30 -> deobfLambda$predictPearlLandingWithTicks$0(var0, var30));
      int var16 = 0;
      int var17 = 0;

      label33:
      while (var17 < 1000) {
         Vec3 var18 = new Vec3(var3, var5, var7);
         Vec3 var19 = new Vec3(var3 + var9, var5 + var11, var7 + var13);
         var16++;
         HitResult var20 = RayTraceUtils.g(var18, var19, false, false, false, var0);
         HitResult var25 = var20;

         label31:
         while (var25.getType() == Type.MISS) {
            for (Entity var22 : ((List<Entity>)(var15))) {
               EntityHitResult var23 = RayTraceUtils.i(var22.getBoundingBox(), var18, var19);
               var25 = var23;
               if (var2 != null) {
                  continue label31;
               }

               if (var23 != null) {
                  return new Object[]{var23.getLocation(), var16};
               }
            }

            var3 += var9;
            var5 += var11;
            var7 += var13;
            var9 *= 0.99;
            var11 *= 0.99;
            var13 *= 0.99;
            var11 -= 0.03;
            var17++;
            if (var2 != null) {
               return new Object[]{null, var16};
            }
            continue label33;
         }

         return new Object[]{var20.getLocation(), var16};
      }

      return new Object[]{null, var16};
   }

   public static Vector2f y(Vec3 var0, Vec3 var1) {
      double var3 = var1.x - var0.x;
      double var5 = var1.y - var0.y;
      double var7 = var1.z - var0.z;
      String var10000 = Vector2f.e();
      float var9 = (float)(Math.toDegrees(Math.atan2(var7, var3)) - 90.0);
      String var2 = var10000;
      double var10 = Math.sqrt(var3 * var3 + var7 * var7);
      double var19 = var10;
      if (var2 == null) {
         if (var10 == 0.0) {
            return new Vector2f(var9, var5 > 0.0 ? -90.0F : 90.0F);
         }

         var19 = 1.5;
      }

      double var12 = var19;
      double var14 = 0.03;
      double var16 = Math.pow(var12, 4.0) - var14 * (var14 * var10 * var10 + 2.0 * var5 * var12 * var12);
      double var20 = var16;
      double var10001 = 0.0;
      if (var2 == null) {
         if (var16 < 0.0) {
            return null;
         }

         var20 = var12 * var12 - Math.sqrt(var16);
         var10001 = var14 * var10;
      }

      float var18 = (float)(-Math.toDegrees(Math.atan(var20 / var10001)));
      return new Vector2f(var9, var18);
   }

   public static boolean L(ClientLevel var0, Player var1, Vec3 var2) {
      int var28 = 0;
      String var10000 = Vector2f.e();
      Vector2f var4 = y(var1.getEyePosition(), var2);
      String var3 = var10000;
      if (var4 == null) {
         return false;
      }

      Vec3 var5 = var1.getEyePosition();
      double var6 = var5.x;
      double var8 = var5.y;
      double var10 = var5.z;
      float var12 = (float)Math.toRadians(var4.H + 90.0F);
      float var13 = (float)Math.toRadians(-var4.E);
      double var14 = Math.cos(var12) * Math.cos(var13) * 1.5;
      double var16 = Math.sin(var13) * 1.5;
      double var18 = Math.sin(var12) * Math.cos(var13) * 1.5;
      int var20 = 0;

      label99:
      while (true) {
         int var26 = var20;

         label95:
         while (true) {
            label93:
            if (var26 < 300) {
               Vec3 var21 = new Vec3(var6, var8, var10);
               Vec3 var22 = new Vec3(var6 + var14, var8 + var16, var10 + var18);
               Vec3 var27 = var21;
               Vec3 var10001 = var5;
               if (var3 == null) {
                  double var29;
                  var28 = (var29 = var21.distanceToSqr(var5) - var2.distanceToSqr(var5)) == 0.0 ? 0 : (var29 < 0.0 ? -1 : 1);
                  if (var3 != null) {
                     break;
                  }

                  if (var28 > 0) {
                     break label93;
                  }

                  var27 = var21;
                  var10001 = var22;
               }

               if (RayTraceUtils.g(var27, var10001, false, false, false, var1).getType() != Type.MISS) {
                  return false;
               }

               for (Entity var24 : var0.entitiesForRendering()) {
                  var26 = ((var24.equals(var1)) ? 1 : 0);
                  if (var3 != null) {
                     continue label95;
                  }

                  if (var3 == null) {
                     if (var26 != 0) {
                        continue;
                     }

                     var26 = ((var24.isPickable()) ? 1 : 0);
                  }

                  if (var3 == null) {
                     if (var26 == 0) {
                        continue;
                     }

                     var26 = ((var24 instanceof LivingEntity) ? 1 : 0);
                  }

                  if (var3 == null) {
                     if (var26 == 0) {
                        continue;
                     }

                     var26 = ((var24.getBoundingBox().inflate(0.3).clip(var21, var22).isPresent()) ? 1 : 0);
                  }

                  if (var3 != null) {
                     return (boolean)((var26) != 0);
                  }

                  if (var26 != 0) {
                     return false;
                  }

                  if (var3 != null) {
                     break;
                  }
               }

               var6 += var14;
               var8 += var16;
               var10 += var18;
               var14 *= 0.99;
               var16 *= 0.99;
               var18 *= 0.99;
               var16 -= 0.03;
               var20++;
               if (var3 == null) {
                  continue label99;
               }
            }

            var28 = 1;
            break;
         }

         return (boolean)((var28) != 0);
      }
   }

   private static boolean deobfLambda$predictPearlLandingWithTicks$0(Entity var0, Entity var1) {
      String var2 = Vector2f.e();
      return var1 instanceof LivingEntity && !(var1 instanceof Enderman) && var1.isAlive() && var1 != ((ThrownEnderpearl)var0).getOwner();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
