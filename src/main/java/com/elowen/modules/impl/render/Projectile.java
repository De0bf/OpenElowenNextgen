package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.render.projectiles.datas.EntityArrowData;
import com.elowen.modules.impl.render.projectiles.datas.EntityPotionData;
import com.elowen.modules.impl.render.projectiles.datas.BasicProjectileData;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import io.github.humbleui.skija.Canvas;
import java.awt.Color;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "Projectiles", a = "Renders the trajectory of held throwables and in-flight projectiles", M = Category.RENDER)
public class Projectile extends Module {
   private final EntityArrowData d = new EntityArrowData();
   private final EntityPotionData S = new EntityPotionData();
   private final BasicProjectileData R = new BasicProjectileData(Collections.singleton(ThrownEnderpearl.class), new Color(173, 12, 255));
   private final BasicProjectileData I = new BasicProjectileData(Collections.singleton(ThrownEgg.class), new Color(255, 238, 154));
   private final BasicProjectileData h = new BasicProjectileData(Collections.singleton(Snowball.class), new Color(255, 255, 255));
   public BooleanValue j;
   public BooleanValue v;
   public BooleanValue o;
   public BooleanValue J;
   public BooleanValue M;
   private static final int V = 200;
   private static final double z = 0.99;
   private static final double p = 128.0;
   private static final int Z = 16;
   private static final int K = -1;
   private static final float T = 1.5F;
   private final com.elowen.utils.renderer.threeD.Skija3DRenderer y;
   private Projectile$PathResult q;
   private double[][] E;
   private Item b;
   private float t;
   private float D;
   private int Y;
   private double x;
   private double c;
   private double Q;
   private static final String[] e = new String[]{"Show Snowballs", "Show Potions", "Show Pearls", "Show Arrows", "[Projectile] Render failed: ", "Show Eggs"};
   public Projectile() {
      String[] var1 = e;
      this.j = com.elowen.values.ValueBuilder.m(this, "Show Arrows").h(true).f$K().f$O();
      this.v = com.elowen.values.ValueBuilder.m(this, "Show Pearls").h(true).f$K().f$O();
      this.o = com.elowen.values.ValueBuilder.m(this, "Show Potions").h(false).f$K().f$O();
      this.J = com.elowen.values.ValueBuilder.m(this, "Show Eggs").h(false).f$K().f$O();
      this.M = com.elowen.values.ValueBuilder.m(this, "Show Snowballs").h(false).f$K().f$O();
      this.y = new com.elowen.utils.renderer.threeD.Skija3DRenderer();
      this.t = Float.NaN;
      this.D = Float.NaN;
      this.Y = -1;
   }

   @com.elowen.events.api.EventTarget
   public void q(EventRender var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null && this.w()) {
         try {
            Camera var3 = NameTags$NameTagData.gameRenderer.mainCamera();
            if (!var3.isInitialized()) {
               return;
            }

            Canvas var4 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
            if (var4 == null) {
               return;
            }

            this.y.I(var4, new WorldProjector(var3, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), com.elowen.utils.renderer.ViewBob.g$Matrix4f()));
            float var5 = NameTags$NameTagData.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            this.W(var5);
            this.g(var5);
            this.y.G$V();
            com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
            if (!this.y.s$Z()) {
               com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
            }
         } catch (RuntimeException var6) {
            System.err.println("[Projectile] Render failed: " + var6);
         }
      }
   }

   private void W(float var1) {
      com.elowen.modules.impl.render.Projectile var10000 = this;
      LocalPlayer var3 = NameTags$NameTagData.player;
      ItemStack var4 = var3.getMainHandItem();
      HasValue[] var2 = Theme.s$ArrQ();
      ItemStack var18 = var4;
      if (var2 != null) {
         if (var4.isEmpty()) {
            return;
         }

         var18 = var4;
      }

      Item var5;
      label79: {
         var5 = var18.getItem();
         boolean var19 = this.x(var5);
         if (var2 != null) {
            if (!var19) {
               return;
            }

            var10000 = this;
            if (var2 == null) {
               break label79;
            }

            var19 = this.e(var5);
         }

         if (!var19) {
            return;
         }

         var10000 = this;
      }

      Projectile$PathResult var6 = var10000.L(var3, var5, var1);
      Projectile$PathResult var21 = var6;
      if (var2 != null) {
         if (var6 == null) {
            return;
         }

         var21 = var6;
      }

      if (var21.w.size() >= 2) {
         double[][] var7 = this.E;
         double[][] var22 = var7;
         if (var2 != null) {
            if (var7 == null) {
               return;
            }

            var22 = var7;
         }

         if (var22.length >= 2) {
            Camera var8 = NameTags$NameTagData.gameRenderer.mainCamera();
            Vec3 var9 = var8.position();
            double[] var10 = var7[0];
            double var11 = var10[0] - var9.x;
            double var13 = var10[1] - var9.y;
            double var15 = var10[2] - var9.z;
            if (var2 != null) {
               if (var11 * var11 + var13 * var13 + var15 * var15 < 0.25) {
                  var10[0] = var9.x;
                  var10[1] = var9.y;
                  var10[2] = var9.z;
               }

               this.y.m(var7, false, -1, 1.5F);
            }

            Vec3 var17 = (Vec3)var6.w.get(var6.w.size() - 1);
            this.y(var17, var6.R, var1);
         }
      }
   }

   private void g(float var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      Iterator var3 = NameTags$NameTagData.level.entitiesForRendering().iterator();
      HasValue[] var2 = var10000;

      while (var3.hasNext()) {
         Entity var4 = (Entity)var3.next();
         if (var4 instanceof net.minecraft.world.entity.projectile.Projectile) {
            com.elowen.modules.impl.render.projectiles.ProjectileData var5 = this.K(var4);
            com.elowen.modules.impl.render.projectiles.ProjectileData var9 = var5;
            if (var2 != null) {
               if (var5 == null) {
                  continue;
               }

               var9 = var5;
            }

            Color var6;
            label52: {
               var6 = var9.P(var4);
               Color var10 = var6;
               if (var2 != null) {
                  if (var6 != null) {
                     break label52;
                  }

                  var10 = Color.WHITE;
               }

               var6 = var10;
            }

            List var7 = this.N(var4, var5);
            if (var7.size() >= 2) {
               double[][] var8 = this.w(var7);
               if (var2 != null) {
                  if (var8.length < 2) {
                     continue;
                  }

                  this.y.m(var8, false, com.elowen.utils.renderer.RenderUtils.h(var6), 1.5F);
               }

               if (var2 == null) {
                  break;
               }
            }
         }
      }
   }

   private double[][] w(List var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.size() < 2) {
         return D(var1);
      }

      double[][] var3 = new double[(var1.size() - 1) * 16 + 1][3];
      int var4 = 0;
      int var5 = 0;
      while (var5 < var1.size() - 1) {
         Vec3 var6 = (Vec3)var1.get(var5);
         Vec3 var7 = (Vec3)var1.get(var5 + 1);
         int var8 = 0;
         while (var8 < 16) {
            double var9 = var8 / 16.0;
            var3[var4][0] = var6.x + (var7.x - var6.x) * var9;
            var3[var4][1] = var6.y + (var7.y - var6.y) * var9;
            var3[var4][2] = var6.z + (var7.z - var6.z) * var9;
            var4++;
            var8++;
         }

         var5++;
      }

      Vec3 var12 = (Vec3)var1.get(var1.size() - 1);
      var3[var4][0] = var12.x;
      var3[var4][1] = var12.y;
      var3[var4][2] = var12.z;
      return var3;
   }

   private Projectile$PathResult L(LocalPlayer var1, Item var2, float var3) {
      float var5 = var1.getYRot();
      float var6 = var1.getXRot();
      HasValue[] var10000 = Theme.s$ArrQ();
      int var7 = var1.getTicksUsingItem();
      double var8 = Mth.lerp(var3, var1.xOld, var1.getX());
      HasValue[] var4 = var10000;
      double var10 = Mth.lerp(var3, var1.yOld, var1.getY());
      double var12 = Mth.lerp(var3, var1.zOld, var1.getZ());
      com.elowen.modules.impl.render.Projectile var14 = this;
      if (var4 != null) {
         label72:
         if (this.q != null && var2 == this.b) {
            float var16;
            int var15 = (var16 = Math.abs(var5 - this.t) - 0.05F) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
            if (var4 != null) {
               if (var15 > 0) {
                  break label72;
               }

               float var17;
               var15 = (var17 = Math.abs(var6 - this.D) - 0.05F) == 0.0F ? 0 : (var17 < 0.0F ? -1 : 1);
            }

            if (var4 != null) {
               if (var15 > 0) {
                  break label72;
               }

               var15 = var7;
            }

            if (var4 != null) {
               if (var15 != this.Y) {
                  break label72;
               }

               double var18;
               var15 = (var18 = Math.abs(var8 - this.x) - 0.01) == 0.0 ? 0 : (var18 < 0.0 ? -1 : 1);
            }

            if (var4 != null) {
               if (var15 > 0) {
                  break label72;
               }

               double var19;
               var15 = (var19 = Math.abs(var10 - this.c) - 0.01) == 0.0 ? 0 : (var19 < 0.0 ? -1 : 1);
            }

            if (var4 != null) {
               if (var15 > 0) {
                  break label72;
               }

               double var20;
               var15 = (var20 = Math.abs(var12 - this.Q) - 0.01) == 0.0 ? 0 : (var20 < 0.0 ? -1 : 1);
            }

            if (var15 <= 0) {
               return this.q;
            }
         }

         this.q = this.y(var1, var2, var5, var6, var3);
         this.E = this.w(this.q.w);
         this.b = var2;
         this.t = var5;
         this.D = var6;
         this.Y = var7;
         this.x = var8;
         this.c = var10;
         var14 = this;
      }

      var14.Q = var12;
      return this.q;
   }

   private Projectile$PathResult y(LocalPlayer var1, Item var2, float var3, float var4, float var5) {
      double var79 = 0.0;
      double var80 = 0.0;
      int var82 = 0;
      byte var85 = (byte)0;
      HasValue[] var6;
      double var33;
      double var35;
      double var37;
      double var39;
      double var41;
      double var43;
      double var45;
      double var47;
      byte var49;
      byte var50;
      Fluid var51;
      label243: {
         double var7 = Mth.lerp(var5, var1.xOld, var1.getX());
         double var9 = Mth.lerp(var5, var1.yOld, var1.getY());
         double var11 = Mth.lerp(var5, var1.zOld, var1.getZ());
         HasValue[] var10000 = Theme.s$ArrQ();
         double var13 = var9 + var1.getEyeHeight();
         double var15 = Math.toRadians(var3);
         double var17 = Math.toRadians(var4);
         boolean var19 = var2 instanceof FishingRodItem;
         boolean var20 = var2 instanceof PotionItem;
         int var21 = ((var2 instanceof ProjectileWeaponItem) ? 1 : 0);
         var6 = var10000;
         int var22 = ((var2 instanceof TridentItem) ? 1 : 0);
         double var23 = var20 ? Math.toRadians(-20.0) : 0.0;
         double var25 = -Math.sin(var15) * Math.cos(var17);
         double var27 = -Math.sin(var17 + var23);
         double var29 = Math.cos(var15) * Math.cos(var17);
         double var31 = Math.sqrt(var25 * var25 + var27 * var27 + var29 * var29);
         var25 /= var31;
         var27 /= var31;
         var29 /= var31;
         var51 = var19 ? Fluid.ANY : Fluid.NONE;
         if (var19) {
            double var52 = Math.cos(-var15 - Math.PI);
            double var54 = Math.sin(-var15 - Math.PI);
            var33 = var7;
            var35 = var13;
            var37 = var11;
            double var56 = Mth.clamp(-Math.tan(var17), -5.0, 5.0);
            double var58 = Math.sqrt(var54 * var54 + var56 * var56 + var52 * var52);
            double var60 = 0.6 / var58 + 0.5;
            var39 = -var54 * var60;
            var41 = var56 * var60;
            var43 = -var52 * var60;
            var45 = 0.03;
            var47 = 0.92;
            var49 = 0;
            var50 = 1;
            if (var6 != null) {
               break label243;
            }
         }

         double var71;
         label233: {
            var33 = var7;
            var35 = var13;
            var37 = var11;
            boolean var78 = var2 instanceof CrossbowItem;
            if (var6 != null) {
               if (var78) {
                  var71 = 3.15;
                  if (var6 != null) {
                     break label233;
                  }
               }

               var78 = var2 instanceof ProjectileWeaponItem;
            }

            if (var6 != null) {
               if (var78) {
                  var71 = this.y(var1) * 3.0;
                  if (var6 != null) {
                     break label233;
                  }
               }

               var78 = var2 instanceof TridentItem;
            }

            if (var6 != null) {
               if (var78) {
                  var71 = 2.5;
                  if (var6 != null) {
                     break label233;
                  }
               }

               var78 = var2 instanceof PotionItem;
            }

            if (var78) {
               var71 = 0.5;
               if (var6 != null) {
                  break label233;
               }
            }

            var71 = 1.5;
         }

         double var86;
         label203: {
            var39 = var25 * var71;
            var41 = var27 * var71;
            var43 = var29 * var71;
            Vec3 var73 = var1.getDeltaMovement();
            var39 += var73.x;
            var79 = var43;
            Vec3 var10001 = var73;
            if (var6 != null) {
               var43 += var73.z;
               var79 = var41;
               if (var1.onGround()) {
                  var86 = 0.0;
                  break label203;
               }

               var10001 = var73;
            }

            var86 = var10001.y;
         }

         var80 = var79 + var86;
         label189:
         if (var6 != null) {
            var41 = var80;
            label195:
            if (!var20) {
               byte var81 = (byte)var21;
               if (var6 != null) {
                  if (var21 != 0) {
                     break label195;
                  }

                  var81 = (byte)var22;
               }

               if (var81 == 0) {
                  var80 = 0.03;
                  break label189;
               }
            }

            var80 = 0.05;
         }

         var45 = var80;
         var47 = 0.99;
         var82 = var21;
         label184:
         if (var6 != null) {
            if (var21 == 0) {
               var82 = var22;
               if (var6 == null) {
                  break label184;
               }

               if (var22 == 0) {
                  var82 = 0;
                  break label184;
               }
            }

            var82 = 1;
         }

         var49 = (byte)var82;
         var50 = 0;
      }

      double var72 = var33;
      double var74 = var37;
      java.util.ArrayList var75 = new java.util.ArrayList();
      var75.add(new Vec3(var33, var35, var37));
      int var57 = 0;

      while (var57 < 200) {
         double var76 = (var33 - var72) * (var33 - var72) + (var37 - var74) * (var37 - var74);
         double var87;
         int var83 = (var87 = var76 - 16384.0) == 0.0 ? 0 : (var87 < 0.0 ? -1 : 1);
         if (var6 != null) {
            if (var83 > 0) {
               break;
            }

            var83 = var50;
         }

         label172: {
            if (var6 != null) {
               if (var83 != 0) {
                  var41 -= var45;
                  if (var6 != null) {
                     break label172;
                  }
               }

               var83 = var49;
            }

            if (var83 == 0) {
               var41 -= var45;
               var39 *= var47;
               var41 *= var47;
               var43 *= var47;
            }
         }

         Vec3 var61;
         Vec3 var77;
         label165: {
            var77 = new Vec3(var33, var35, var37);
            var61 = new Vec3(var33 + var39, var35 + var41, var37 + var43);
            HitResult var62 = com.elowen.utils.RayTraceUtils.c(var77, var61, Block.COLLIDER, var51, var1);
            HitResult var84 = var62;
            if (var6 != null) {
               if (var62 == null) {
                  break label165;
               }

               var84 = var62;
            }

            if (var84.getType() != Type.MISS) {
               var75.add(var62.getLocation());
               return new Projectile$PathResult(var75, var62);
            }
         }

         AABB var63 = new AABB(var33, var35, var37, var33, var35, var37).inflate(0.25).expandTowards(var39, var41, var43).inflate(1.0);
         EntityHitResult var64 = ProjectileUtil.getEntityHitResult(var1, var77, var61, var63, e -> deobfLambda$computeTrajectory$0(var1, e), Double.MAX_VALUE);
         if (var6 != null) {
            if (var64 != null && var64.getType() == Type.ENTITY) {
               var75.add(var64.getLocation());
               return new Projectile$PathResult(var75, var64);
            }

            var33 += var39;
            var35 += var41;
            var37 += var43;
         }

         label237: {
            label238: {
               label239: {
                  var85 = var49;
                  if (var6 != null) {
                     if (var49 != 0) {
                        var39 *= var47;
                        var41 *= var47;
                        var43 *= var47;
                        var41 -= var45;
                        if (var6 != null) {
                           break label239;
                        }
                     }

                     var85 = var50;
                  }

                  if (var6 == null) {
                     break label238;
                  }

                  if (var85 != 0) {
                     var39 *= var47;
                     var41 *= var47;
                     var43 *= var47;
                  }
               }

               var75.add(new Vec3(var33, var35, var37));
               if (var6 == null) {
                  break label237;
               }

               double var88;
               var85 = (byte)((var88 = var35 - -64.0) == 0.0 ? 0 : (var88 < 0.0 ? -1 : 1));
            }

            if (var85 < 0) {
               break;
            }

            var57++;
         }

         if (var6 == null) {
            break;
         }
      }

      return new Projectile$PathResult(var75, null);
   }

   private void y(Vec3 var1, HitResult var2, float var3) {
      short var5;
      short var6;
      short var7;
      label76: {
         HasValue[] var4;
         HitResult var38;
         HasValue[] var10000 = Theme.s$ArrQ();
         var5 = 255;
         var4 = var10000;
         var6 = 255;
         var7 = 255;
         var38 = var2;
         label73:
         if (var4 != null) {
            if (var2 != null) {
               var38 = var2;
               if (var4 == null) {
                  break label73;
               }

               if (var2.getType() == Type.ENTITY) {
                  var5 = 255;
                  var6 = 0;
                  var7 = 0;
                  Entity var8 = ((EntityHitResult)var2).getEntity();
                  AABB var9 = var8.getBoundingBox();
                  double var10 = Mth.lerp(var3, var8.xOld, var8.getX()) - var8.getX();
                  double var12 = Mth.lerp(var3, var8.yOld, var8.getY()) - var8.getY();
                  double var14 = Mth.lerp(var3, var8.zOld, var8.getZ()) - var8.getZ();
                  AABB var16 = var9.move(var10, var12, var14).inflate(0.1);
                  this.y
                     .E(
                        var16.minX,
                        var16.minY,
                        var16.minZ,
                        var16.maxX,
                        var16.maxY,
                        var16.maxZ,
                        com.elowen.utils.renderer.RenderUtils.O(255, 0, 0, 140),
                        com.elowen.utils.renderer.RenderUtils.O(255, 0, 0, 255),
                        1.5F
                     );
                  if (var4 != null) {
                     break label76;
                  }
               }
            }

            var38 = var2;
         }

         if (var4 != null) {
            if (var38 == null) {
               break label76;
            }

            var38 = var2;
         }

         if (var4 != null) {
            if (var38.getType() != Type.BLOCK) {
               break label76;
            }

            var38 = var2;
         }

         Direction var33;
         label55: {
            BlockHitResult var32 = (BlockHitResult)var38;
            var33 = var32.getDirection();
            if (var4 != null) {
               if (var33 != Direction.UP) {
                  break label55;
               }

               var5 = 0;
               var6 = 255;
            }

            var7 = 0;
         }

         double var34 = var1.x;
         double var35 = var1.y;
         double var36 = var1.z;
         double var37 = 0.25;
         double var18 = 0.1;
         double var20;
         double var22;
         double var24;
         double var26;
         double var28;
         double var30;
         switch (Projectile$1.F[var33.ordinal()]) {
            case 1:
               var20 = var34 - var37;
               var22 = var34 + var37;
               var24 = var35 - var37;
               var26 = var35 - (var37 - var18);
               var28 = var36 - var37;
               var30 = var36 + var37;
               if (var4 != null) {
                  break;
               }
            case 2:
               var20 = var34 - var37;
               var22 = var34 + var37;
               var24 = var35 + (var37 - var18);
               var26 = var35 + var37;
               var28 = var36 - var37;
               var30 = var36 + var37;
               if (var4 != null) {
                  break;
               }
            case 3:
               var20 = var34 - var37;
               var22 = var34 + var37;
               var24 = var35 - var37;
               var26 = var35 + var37;
               var28 = var36 - var37;
               var30 = var36 - (var37 - var18);
               if (var4 != null) {
                  break;
               }
            case 4:
               var20 = var34 - var37;
               var22 = var34 + var37;
               var24 = var35 - var37;
               var26 = var35 + var37;
               var28 = var36 + (var37 - var18);
               var30 = var36 + var37;
               if (var4 != null) {
                  break;
               }
            case 5:
               var20 = var34 - var37;
               var22 = var34 - (var37 - var18);
               var24 = var35 - var37;
               var26 = var35 + var37;
               var28 = var36 - var37;
               var30 = var36 + var37;
               if (var4 != null) {
                  break;
               }
            default:
               var20 = var34 + (var37 - var18);
               var22 = var34 + var37;
               var24 = var35 - var37;
               var26 = var35 + var37;
               var28 = var36 - var37;
               var30 = var36 + var37;
         }

         this.y
            .E(
               var20,
               var24,
               var28,
               var22,
               var26,
               var30,
               com.elowen.utils.renderer.RenderUtils.O(var5, var6, var7, 110),
               com.elowen.utils.renderer.RenderUtils.O(var5, var6, var7, 220),
               1.5F
            );
         return;
      }

      this.y
         .E(
            var1.x - 0.1,
            var1.y - 0.1,
            var1.z - 0.1,
            var1.x + 0.1,
            var1.y + 0.1,
            var1.z + 0.1,
            com.elowen.utils.renderer.RenderUtils.O(var5, var6, var7, 110),
            com.elowen.utils.renderer.RenderUtils.O(var5, var6, var7, 220),
            1.5F
         );
   }

   private List N(Entity var1, com.elowen.modules.impl.render.projectiles.ProjectileData var2) {
      java.util.ArrayList var4 = new java.util.ArrayList();
      double var5 = var1.getX();
      double var7 = var1.getY();
      double var9 = var1.getZ();
      Vec3 var11 = var1.getDeltaMovement();
      double var12 = var11.x;
      HasValue[] var10000 = Theme.s$ArrQ();
      double var14 = var11.y;
      double var16 = var11.z;
      double var18 = var2.y$F();
      int var20 = ((var1 instanceof AbstractArrow) ? 1 : 0);
      double var21 = var5;
      double var23 = var9;
      HasValue[] var3 = var10000;
      var4.add(new Vec3(var5, var7, var9));
      int var25 = 0;

      while (var25 < 200) {
         double var26 = (var5 - var21) * (var5 - var21) + (var9 - var23) * (var9 - var23);
         double var38;
         int var35 = (var38 = var26 - 16384.0) == 0.0 ? 0 : (var38 < 0.0 ? -1 : 1);
         if (var3 != null) {
            if (var35 > 0) {
               break;
            }

            var35 = var20;
         }

         if (var35 == 0) {
            var14 -= var18;
            var12 *= 0.99;
            var14 *= 0.99;
            var16 *= 0.99;
         }

         Vec3 var28;
         Vec3 var29;
         label75: {
            var28 = new Vec3(var5, var7, var9);
            var29 = new Vec3(var5 + var12, var7 + var14, var9 + var16);
            HitResult var30 = com.elowen.utils.RayTraceUtils.c(var28, var29, Block.COLLIDER, Fluid.NONE, var1);
            HitResult var36 = var30;
            if (var3 != null) {
               if (var30 == null) {
                  break label75;
               }

               var36 = var30;
            }

            if (var36.getType() != Type.MISS) {
               var4.add(var30.getLocation());
               if (var3 != null) {
                  break;
               }
            }
         }

         AABB var31 = new AABB(var5, var7, var9, var5, var7, var9).inflate(0.25).expandTowards(var12, var14, var16).inflate(1.0);
         EntityHitResult var32 = ProjectileUtil.getEntityHitResult(var1, var28, var29, var31, e -> deobfLambda$simulateEntityPath$0(var1, e), Double.MAX_VALUE);
         if (var3 != null) {
            if (var32 != null && var32.getType() == Type.ENTITY) {
               var4.add(var32.getLocation());
               if (var3 != null) {
                  break;
               }
            }

            var5 += var12;
            var7 += var14;
            var9 += var16;
         }

         label86: {
            byte var37 = (byte)var20;
            if (var3 != null) {
               if (var20 != 0) {
                  var12 *= 0.99;
                  var14 *= 0.99;
                  var16 *= 0.99;
                  var14 -= var18;
               }

               var4.add(new Vec3(var5, var7, var9));
               if (var3 == null) {
                  break label86;
               }

               double var39;
               var37 = (byte)((var39 = var7 - -64.0) == 0.0 ? 0 : (var39 < 0.0 ? -1 : 1));
            }

            if (var37 < 0) {
               break;
            }

            var25++;
         }

         if (var3 == null) {
            break;
         }
      }

      return var4;
   }

   private double y(LocalPlayer var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      float var3 = var1.getTicksUsingItem() / 20.0F;
      var3 = (var3 * var3 + var3 * 2.0F) / 3.0F;
      HasValue[] var2 = var10000;
      float var5 = var3;
      if (var2 != null) {
         if (var3 > 1.0F) {
            var3 = 1.0F;
         }

         var5 = var3;
      }

      return var5;
   }

   private boolean x(Item var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 instanceof BowItem
         || var1 instanceof CrossbowItem
         || var1 instanceof SnowballItem
         || var1 instanceof EggItem
         || var1 instanceof EnderpearlItem
         || var1 instanceof SplashPotionItem
         || var1 instanceof LingeringPotionItem
         || var1 instanceof FishingRodItem
         || var1 instanceof TridentItem;
   }

   private boolean e(Item var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1 instanceof PotionItem) {
         return this.o.w();
      } else if (var1 instanceof EnderpearlItem) {
         return this.v.w();
      } else if (var1 instanceof EggItem) {
         return this.J.w();
      } else if (var1 instanceof SnowballItem) {
         return this.M.w();
      } else {
         return !(var1 instanceof ProjectileWeaponItem) && !(var1 instanceof TridentItem) ? true : this.j.w();
      }
   }

   private com.elowen.modules.impl.render.projectiles.ProjectileData K(Entity var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.onGround()) {
         return null;
      }

      if (var1.getX() == var1.xOld && var1.getZ() == var1.zOld) {
         return null;
      }

      Iterator var3 = this.Y().iterator();
      while (var3.hasNext()) {
         com.elowen.modules.impl.render.projectiles.ProjectileData var4 = (com.elowen.modules.impl.render.projectiles.ProjectileData)var3.next();
         if (var4.R(var1)) {
            return var4;
         }
      }

      return null;
   }

   private List Y() {
      HasValue[] var10000 = Theme.s$ArrQ();
      java.util.ArrayList var2 = new java.util.ArrayList();
      HasValue[] var1 = var10000;
      boolean var3 = this.j.w();
      if (var1 != null) {
         if (var3) {
            var2.add(this.d);
         }

         var3 = this.o.w();
      }

      if (var1 != null) {
         if (var3) {
            var2.add(this.S);
         }

         var3 = this.v.w();
      }

      if (var1 != null) {
         if (var3) {
            var2.add(this.R);
         }

         var3 = this.J.w();
      }

      if (var1 != null) {
         if (var3) {
            var2.add(this.I);
         }

         var3 = this.M.w();
      }

      if (var1 != null && var3) {
         var2.add(this.h);
      }

      return var2;
   }

   private static double[][] D(List var0) {
      double[][] var5 = null;
      HasValue[] var10000 = Theme.s$ArrQ();
      double[][] var2 = new double[var0.size()][3];
      HasValue[] var1 = var10000;
      int var3 = 0;

      while (true) {
         if (var3 < var0.size()) {
            Vec3 var4 = (Vec3)var0.get(var3);
            var2[var3][0] = var4.x;
            var2[var3][1] = var4.y;
            var5 = var2;
            if (var1 == null) {
               break;
            }

            var2[var3][2] = var4.z;
            var3++;
            if (var1 != null) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private static boolean deobfLambda$simulateEntityPath$0(Entity var0, Entity var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 != var0 && var1 instanceof LivingEntity;
   }

   private static boolean deobfLambda$computeTrajectory$0(LocalPlayer var0, Entity var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 != var0 && var1 instanceof LivingEntity;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
