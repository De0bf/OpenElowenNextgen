package com.elowen.utils.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.MathUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.MathHelper;
import com.elowen.values.HasValue;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class RotationUtils {
   private static final Minecraft I = Minecraft.getInstance();
   private static boolean e;

   public static float e(float var0, float var1) {
      return ((var0 - var1) % 360.0F + 540.0F) % 360.0F - 180.0F;
   }

   public static Vec3 V() {
      return b(I.player.getYRot(), I.player.getXRot());
   }

   public static Vector2f w(float var0, float var1, float var2, float var3) {
      float var4 = (float)((Double)I.options.sensitivity().get() * 0.6F + 0.2F);
      float var5 = var4 * var4 * var4 * 1.2F;
      float var6 = var0 - var2;
      float var7 = var1 - var3;
      float var8 = var6 - var6 % var5;
      float var9 = var7 - var7 % var5;
      float var10 = var2 + var8;
      float var11 = var3 + var9;
      return new Vector2f(var10, var11);
   }

   public static Vec3 b(float var0, float var1) {
      float var2 = Mth.cos(-var0 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var3 = Mth.sin(-var0 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var4 = -Mth.cos(-var1 * (float) (Math.PI / 180.0));
      float var5 = Mth.sin(-var1 * (float) (Math.PI / 180.0));
      return new Vec3(var3 * var4, var5, var2 * var4);
   }

   public static boolean T(AABB var0, Vec3 var1) {
      boolean var2 = T$Z();
      return var1.x > var0.minX && var1.x < var0.maxX && var1.y > var0.minY && var1.y < var0.maxY && var1.z > var0.minZ && var1.z < var0.maxZ;
   }

   public static boolean F(AABB var0) {
      return var0 != null && I.player != null ? var0.inflate(1.0E-4).contains(I.player.getEyePosition(1.0F)) : false;
   }

   public static Rotation X(Vec3 var0, Vec3 var1) {
      double var2 = var1.x - var0.x;
      double var4 = var1.y - var0.y;
      double var6 = var1.z - var0.z;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)Math.toDegrees(Math.atan2(var6, var2)) - 90.0F;
      float var11 = (float)(-Math.toDegrees(Math.atan2(var4, var8)));
      return new Rotation(Mth.wrapDegrees(var10), Mth.wrapDegrees(var11));
   }

   public static Rotation F(BlockPos var0, float var1) {
      Vec3 var2 = new Vec3(
         I.player.getX() + I.player.getDeltaMovement().x * var1,
         I.player.getY() + I.player.getEyeHeight() + I.player.getDeltaMovement().y() * var1,
         I.player.getZ() + I.player.getDeltaMovement().z() * var1
      );
      double var3 = var0.getX() - var2.x + 0.5;
      double var5 = var0.getY() - var2.y + 0.5;
      double var7 = var0.getZ() - var2.z + 0.5;
      return U(X(var3), X(var5), X(var7));
   }

   public static Rotation U(double var0, double var2, double var4) {
      double var6 = Math.sqrt(var0 * var0 + var4 * var4);
      float var8 = (float)Math.toDegrees(Math.atan2(var4, var0)) - 90.0F;
      float var9 = (float)(-Math.toDegrees(Math.atan2(var2, var6)));
      return new Rotation(Mth.wrapDegrees(var8), Mth.wrapDegrees(var9));
   }

   private static double X(double var0) {
      return var0 + MathUtils.K(0.05, 0.08) * (MathUtils.K(0.0, 1.0) * 2.0 - 1.0);
   }

   public static double T(Entity var0, Vector2f var1) {
      float var10 = 0.0F;
      boolean var10000 = T$Z();
      double var3 = Double.MAX_VALUE;
      Iterator var5 = B$List().iterator();
      boolean var2 = var10000;

      while (true) {
         if (var5.hasNext()) {
            double var6 = ((Float)var5.next()).floatValue();
            Vec3 var8 = new Vec3(I.player.getX(), I.player.getY(), I.player.getZ());
            Vec3 var9 = var8.add(0.0, var6, 0.0);
            var10 = ((float)(Math.min(var3, o(var0, var9, var1))));
            if (var2) {
               break;
            }

            var3 = var10;
            if (!var2) {
               continue;
            }
         }

         var10 = ((float)(var3));
         break;
      }

      return var10;
   }

   public static double o(Entity var0, Vec3 var1, Vector2f var2) {
      boolean var10000 = t();
      AABB var4 = n(var0);
      boolean var3 = var10000;
      HitResult var5 = m(var4, var2, var1, 6.0);
      HitResult var7 = var5;
      if (var3) {
         if (var5 == null) {
            return 1000.0;
         }

         var7 = var5;
      }

      Vec3 var6 = var7.getLocation();
      return var6.distanceTo(var1);
   }

   public static HitResult m(AABB var0, Vector2f var1, Vec3 var2, double var3) {
      Vec3 var5 = b(var1.H, var1.E);
      Vec3 var6 = var2.add(var5.x * var3, var5.y * var3, var5.z * var3);
      return ProjectileUtil.getEntityHitResult(I.player, var2, var6, var0, RotationUtils::deobfLambda$getIntercept$0, var3 * var3);
   }

   public static HitResult Y(AABB var0, Vector2f var1, Vec3 var2) {
      return m(var0, var1, var2, 6.0);
   }

   public static Vector2f O(double var0, double var2, double var4) {
      double var6 = Math.sqrt(var0 * var0 + var4 * var4);
      float var8 = (float)Math.toDegrees(Math.atan2(var4, var0)) - 90.0F;
      float var9 = (float)(-Math.toDegrees(Math.atan2(var2, var6)));
      return new Vector2f(MathHelper.N(var8), MathHelper.N(var9));
   }

   public static Vector2f x(Vec3 var0) {
      Vec3 var1 = new Vec3(I.player.getX(), I.player.getY() + I.player.getEyeHeight(), I.player.getZ());
      double var2 = var0.x - var1.x;
      double var4 = var0.y - var1.y;
      double var6 = var0.z - var1.z;
      return O(var2, var4, var6);
   }

   private static boolean S(Vec3 var0, HitResult var1, Entity var2) {
      boolean var3 = T$Z();
      if (var1.getType() == Type.ENTITY && ((EntityHitResult)var1).getEntity() == var2) {
         Vec3 var4 = var1.getLocation();
         return T(n(var2), var0) || var4.distanceTo(var0) <= 3.0;
      } else {
         return false;
      }
   }

   private static HitResult v(Rotation var0) {
      float var21 = 0.0F;
      boolean var1;
      double var2;
      HitResult var4;
      Vec3 var5;
      byte var6;
      double var7;
      label89: {
         double var18;
         label92: {
            var2 = I.player.blockInteractionRange();
            boolean var10000 = t();
            var4 = RayTraceUtils.a(var2, 1.0F, false, var0);
            var1 = var10000;
            var5 = I.player.getEyePosition(1.0F);
            var6 = 0;
            int var17 = ((I.player.getAbilities().instabuild) ? 1 : 0);
            if (var1) {
               if (var17 != 0) {
                  var2 = 6.0;
                  var7 = 6.0;
                  if (var1) {
                     break label89;
                  }
               }

               var18 = var2;
               if (!var1) {
                  break label92;
               }

               double var22;
               var17 = (byte)((var22 = var2 - 3.0) == 0.0 ? 0 : (var22 < 0.0 ? -1 : 1));
            }

            if (var17 > 0) {
               var6 = 1;
            }

            var18 = var2;
         }

         var7 = var18;
      }

      label78: {
         var7 *= var7;
         HitResult var19 = var4;
         if (var1) {
            if (var4 == null) {
               break label78;
            }

            var19 = var4;
         }

         var7 = var19.getLocation().distanceToSqr(var5);
      }

      Vec3 var9 = b(var0.o$F(), var0.y$F());
      Vec3 var10 = var5.add(var9.x * var2, var9.y * var2, var9.z * var2);
      AABB var11 = I.player.getBoundingBox().expandTowards(var9.scale(var2)).inflate(1.0, 1.0, 1.0);
      EntityHitResult var12 = ProjectileUtil.getEntityHitResult(I.player, var5, var10, var11, RotationUtils::deobfLambda$rayTrace$0, var7);
      Object var20 = var12;
      if (var1) {
         label70:
         if (var12 != null) {
            Vec3 var13 = var12.getLocation();
            double var14 = var5.distanceToSqr(var13);
            var21 = var6;
            label68:
            if (var1) {
               if (var6 != 0) {
                  double var23;
                  var21 = (byte)((var23 = var14 - 9.0) == 0.0 ? 0 : (var23 < 0.0 ? -1 : 1));
                  if (!var1) {
                     break label68;
                  }

                  if (var21 > 0) {
                     var4 = BlockHitResult.miss(var13, Direction.getApproximateNearest(var9.x, var9.y, var9.z), BlockPos.containing(var13));
                     if (var1) {
                        break label70;
                     }
                  }
               }

               double var24;
               var21 = (byte)((var24 = var14 - var7) == 0.0 ? 0 : (var24 < 0.0 ? -1 : 1));
            }

            if (var21 >= 0) {
               if (!var1) {
                  return (HitResult)var4;
               }

               if (var4 != null) {
                  break label70;
               }
            }

            var4 = var12;
         }

         var20 = var4;
      }

      return (HitResult)var20;
   }

   public static Vec3 n(Rotation var0) {
      float var1 = (float)Math.cos(-var0.o$F() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var2 = (float)Math.sin(-var0.o$F() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var3 = (float)(-Math.cos(-var0.y$F() * (float) (Math.PI / 180.0)));
      float var4 = (float)Math.sin(-var0.y$F() * (float) (Math.PI / 180.0));
      return new Vec3(var2 * var3, var4, var1 * var3);
   }

   public static RotationUtils$Data s(Entity var0) {
      float var33 = 0.0F;
      double var34 = 0.0;
      Vec3 var35 = null;
      Vec3 var6 = new Vec3(I.player.getX(), I.player.getY(), I.player.getZ());
      Vec3 var7 = var6.add(0.0, I.player.getEyeHeight(), 0.0);
      AABB var8 = n(var0);
      double var9 = var8.minX;
      double var11 = var8.minY;
      double var13 = var8.minZ;
      double var15 = var8.maxX;
      boolean var10000 = t();
      double var17 = var8.maxY;
      double var19 = var8.maxZ;
      boolean var1 = var10000;
      double var21 = 0.1;
      LinkedHashSet var23 = new LinkedHashSet();
      var23.add(new Vec3(var9 + var15 / 2.0, var11 + var17 / 2.0, var13 + var19 / 2.0));
      var23.add(x(var7, var8));
      double var4 = var9;

      while (true) {
         if (var4 <= var15) {
            var33 = ((float)(var11));
            if (!var1) {
               break;
            }

            double var24 = var11;

            label118: {
               while (var24 <= var17) {
                  var23.add(new Vec3(var4, var24, var13));
                  var23.add(new Vec3(var4, var24, var19));
                  var24 += var21;
                  if (!var1) {
                     break label118;
                  }

                  if (!var1) {
                     HasValue.d(HasValue.x());
                     break;
                  }
               }

               var4 += var21;
            }

            if (var1) {
               continue;
            }
         }

         var33 = ((float)(var9));
         break;
      }

      var4 = var33;

      while (true) {
         if (var4 <= var15) {
            var34 = var13;
            if (!var1) {
               break;
            }

            double var2 = var13;

            label97: {
               while (var2 <= var19) {
                  var23.add(new Vec3(var4, var11, var2));
                  var23.add(new Vec3(var4, var17, var2));
                  var2 += var21;
                  if (!var1) {
                     break label97;
                  }

                  if (!var1) {
                     break;
                  }
               }

               var4 += var21;
            }

            if (var1) {
               continue;
            }
         }

         var34 = var11;
         break;
      }

      double var31 = var34;

      while (var31 <= var17) {
         double var29 = var13;

         while (true) {
            if (var29 <= var19) {
               var23.add(new Vec3(var9, var31, var29));
               var23.add(new Vec3(var15, var31, var29));
               var29 += var21;
               if (!var1) {
                  break;
               }

               if (var1) {
                  continue;
               }
            }

            var31 += var21;
            break;
         }

         if (!var1) {
            break;
         }
      }

      Iterator var32 = var23.iterator();

      Rotation var26;
      while (true) {
         if (var32.hasNext()) {
            Vec3 var25 = (Vec3)var32.next();
            var26 = X(var7, var25);
            HitResult var27 = v(var26);
            var35 = var7;
            if (!var1) {
               break;
            }

            if (S(var7, var27, var0)) {
               var35 = var27.getLocation();
               break;
            }

            if (var1) {
               continue;
            }
         }

         return new RotationUtils$Data(var7, var7, 1000.0, null);
      }

      Vec3 var28 = var35;
      return new RotationUtils$Data(var7, var28, var28.distanceTo(var7), w(var26.o$F(), var26.y$F(), RotationManager.G.H, RotationManager.G.E));
   }

   public static RotationUtils$Data F(Entity var0, AABB var1) {
      float var35 = 0.0F;
      float var36 = 0.0F;
      HitResult var37 = null;
      boolean var10000 = t();
      Vec3 var3 = new Vec3(I.player.getX(), I.player.getY(), I.player.getZ());
      Vec3 var4 = var3.add(0.0, I.player.getEyeHeight(), 0.0);
      double var5 = 6.0;
      double var7 = var1.minX;
      double var9 = var1.minY;
      double var11 = var1.minZ;
      double var13 = var1.maxX;
      boolean var2 = var10000;
      double var15 = var1.maxY;
      double var17 = var1.maxZ;
      double var19 = 0.1;
      LinkedHashSet var21 = new LinkedHashSet();
      var21.add(new Vec3((var7 + var13) / 2.0, (var9 + var15) / 2.0, (var11 + var17) / 2.0));
      var21.add(x(var4, var1));
      double var22 = var7;

      while (true) {
         if (var22 <= var13) {
            var35 = ((float)(var9));
            if (!var2) {
               break;
            }

            double var24 = var9;

            label122: {
               while (var24 <= var15) {
                  var21.add(new Vec3(var22, var24, var11));
                  var21.add(new Vec3(var22, var24, var17));
                  var24 += var19;
                  if (!var2) {
                     break label122;
                  }

                  if (!var2) {
                     break;
                  }
               }

               var22 += var19;
            }

            if (var2) {
               continue;
            }
         }

         var35 = ((float)(var7));
         break;
      }

      var22 = var35;

      while (true) {
         if (var22 <= var13) {
            var36 = ((float)(var11));
            if (!var2) {
               break;
            }

            double var32 = var11;

            label102: {
               while (var32 <= var17) {
                  var21.add(new Vec3(var22, var9, var32));
                  var21.add(new Vec3(var22, var15, var32));
                  var32 += var19;
                  if (!var2) {
                     break label102;
                  }

                  if (!var2) {
                     break;
                  }
               }

               var22 += var19;
            }

            if (var2) {
               continue;
            }
         }

         var36 = ((float)(var9));
         break;
      }

      var22 = var36;

      while (var22 <= var15) {
         double var33 = var11;

         while (true) {
            if (var33 <= var17) {
               var21.add(new Vec3(var7, var22, var33));
               var21.add(new Vec3(var13, var22, var33));
               var33 += var19;
               if (!var2) {
                  break;
               }

               if (var2) {
                  continue;
               }
            }

            var22 += var19;
            break;
         }

         if (!var2) {
            break;
         }
      }

      Iterator var31 = var21.iterator();

      Rotation var34;
      while (true) {
         if (var31.hasNext()) {
            Vec3 var23 = (Vec3)var31.next();
            var34 = X(var4, var23);
            Vec3 var25 = b(var34.o$F(), var34.y$F());
            Vec3 var26 = var4.add(var25.x * var5, var25.y * var5, var25.z * var5);
            Optional var27 = var1.clip(var4, var26);
            var37 = (HitResult)var27.orElse(null);
            if (!var2) {
               break;
            }

            if (var27.isPresent()) {
               var37 = ((net.minecraft.world.phys.HitResult)(var27.get()));
               break;
            }

            if (var2) {
               continue;
            }
         }

         RotationUtils$Data var38 = new RotationUtils$Data(var4, var4, 1000.0, null);
         if (HasValue.X$Z()) {
            v(!var2);
         }

         return var38;
      }

      Vec3 var28 = var37.getLocation();
      return new RotationUtils$Data(var4, var28, var28.distanceTo(var4), w(var34.o$F(), var34.y$F(), RotationManager.G.H, RotationManager.G.E));
   }

   private static AABB n(Entity var0) {
      return var0.getBoundingBox();
   }

   public static List B$List() {
      return List.of(I.player.getEyeHeight());
   }

   public static Vec3 x(Vec3 var0, AABB var1) {
      double var2 = Math.max(var1.minX, Math.min(var0.x, var1.maxX));
      double var4 = Math.max(var1.minY, Math.min(var0.y, var1.maxY));
      double var6 = Math.max(var1.minZ, Math.min(var0.z, var1.maxZ));
      return new Vec3(var2, var4, var6);
   }

   public static Vector2f B$h(Entity var0) {
      boolean var1 = T$Z();
      if (var0 == null) {
         return null;
      }

      double var2 = var0.getX() - I.player.getX();
      double var4 = var0.getZ() - I.player.getZ();
      double var6 = var0.getY() + var0.getEyeHeight() - (I.player.getY() + I.player.getEyeHeight());
      return O(var2, var6, var4);
   }

   public static float i(float var0, float var1, float var2) {
      return q(var1, var2, var0);
   }

   public static float q(float var0, float var1, float var2) {
      int var10000 = ((t()) ? 1 : 0);
      float var4 = MathHelper.N(var1 - var0);
      boolean var3 = (boolean)((var10000) != 0);
      float var7;
      var10000 = (var7 = var4 - var2) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
      if (var3) {
         if (var10000 > 0) {
            var4 = var2;
         }

         float var10001 = -var2;
         if (!var3) {
            return var4 + var10001;
         }

         float var8;
         var10000 = (var8 = var4 - var10001) == 0.0F ? 0 : (var8 < 0.0F ? -1 : 1);
      }

      if (var10000 < 0) {
         var4 = -var2;
      }

      return var0 + var4;
   }

   public static boolean b(Entity var0, float var1) {
      Vector2f var3 = B$h(var0);
      int var10000 = ((T$Z()) ? 1 : 0);
      float var4 = Math.abs(I.player.getYRot() % 360.0F - var3.H);
      boolean var2 = (boolean)((var10000) != 0);
      float var5 = Math.abs(Math.min(var4, 360.0F - var4));
      float var7;
      var10000 = (var7 = var5 - var1) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
      if (!var2) {
         var10000 = var10000 <= 0 ? 1 : 0;
      }

      return (boolean)((var10000) != 0);
   }

   public static float I(float var0, float var1) {
      boolean var10000 = t();
      float var3 = Math.abs(var0 - var1) % 360.0F;
      boolean var2 = var10000;
      float var4 = var3;
      if (var2) {
         if (var3 > 180.0F) {
            var3 = 0.0F;
         }

         var4 = var3;
      }

      return var4;
   }

   public static Vec3 O() {
      return new Vec3(I.player.getX(), I.player.getY() + I.player.getEyeHeight(I.player.getPose()), I.player.getZ());
   }

   private static boolean deobfLambda$rayTrace$0(Entity var0) {
      boolean var1 = T$Z();
      return !var0.isSpectator() && var0.isPickable();
   }

   private static boolean deobfLambda$getIntercept$0(Entity var0) {
      boolean var1 = T$Z();
      return !var0.isSpectator() && var0.isPickable();
   }

   static {
      v(false);
   }

   public static void v(boolean var0) {
      e = var0;
   }

   public static boolean T$Z() {
      return e;
   }

   public static boolean t() {
      return !T$Z();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
