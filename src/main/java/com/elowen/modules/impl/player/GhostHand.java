package com.elowen.modules.impl.player;

import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventClick;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.BlockUtils;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.renderer.Fonts;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import io.github.humbleui.skija.Typeface;
import java.awt.Color;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector2f;

@ModuleInfo(R = "GhostHand", a = "Allows interacting with blocks through walls", M = Category.PLAYER)
public class GhostHand extends Module {
   private final BooleanValue S;
   private final BooleanValue K;
   private final BooleanValue p;
   private final BooleanValue y;
   private final BooleanValue B;
   private final BooleanValue X;
   private final BooleanValue C;
   private final BooleanValue U;
   private static final double T = 0.001;
   private static final double D = 6.0;
   private static final double b = 4.5;
   private static final Set m;
   private static final Set Y;
   private static final Set Z;
   private static final Map J;
   private static final Map f;
   private final SkijaRenderer I;
   private static final String[] c = new String[]{" Shulker", "Furnace", "Trapped Chests", "Shulker Boxes", "[GhostHand] Render failed: ", " dist=", "Ender Chest", "Ender Chests", "Debug Mode", "Brewing Stands", "Furnaces", "[GhostHand] Interacting with ", "Trapped Chest", "Blast Furnace", "Smoker", " face=", "Render Tags", "Chest", "Brewing Stand", "[%s] [%.1fm]", "Chest", "Chests"};
   public GhostHand() {
      String[] var1 = c;
      this.S = ValueBuilder.m(this, "Chests").h(true).f$K().f$O();
      this.K = ValueBuilder.m(this, "Ender Chests").h(true).f$K().f$O();
      this.p = ValueBuilder.m(this, "Trapped Chests").h(true).f$K().f$O();
      this.y = ValueBuilder.m(this, "Shulker Boxes").h(true).f$K().f$O();
      this.B = ValueBuilder.m(this, "Furnaces").h(false).f$K().f$O();
      this.X = ValueBuilder.m(this, "Brewing Stands").h(false).f$K().f$O();
      this.C = ValueBuilder.m(this, "Render Tags").h(true).f$K().f$O();
      this.U = ValueBuilder.m(this, "Debug Mode").h(false).f$K().f$O();
      this.I = com.elowen.utils.renderer.SkiaRenderManager.X$m();
   }

   private static Color A(DyeColor var0) {
      return switch (com.elowen.modules.impl.player.GhostHand$1.G[var0.ordinal()]) {
         case 1 -> Color.WHITE;
         case 2 -> Color.ORANGE;
         case 3 -> Color.MAGENTA;
         case 4 -> Color.CYAN;
         case 5 -> Color.YELLOW;
         case 6 -> Color.GREEN;
         case 7 -> Color.PINK;
         case 8 -> Color.GRAY;
         case 9 -> Color.LIGHT_GRAY;
         case 10 -> Color.CYAN;
         case 11 -> new Color(128, 0, 128);
         case 12 -> Color.BLUE;
         case 13 -> new Color(139, 69, 19);
         case 14 -> Color.GREEN;
         case 15 -> Color.RED;
         case 16 -> Color.BLACK;
         default -> throw new MatchException(null, null);
      };
   }

   private boolean A(Block var1) {
      boolean var5 = false;
      int var2 = ChestStealer.d$I();
      Block var10000 = var1;
      Block var10001 = Blocks.CHEST;
      if (var2 != 0) {
         if (var1 == Blocks.CHEST) {
            boolean var3 = this.S.w();
            if (var2 == 0) {
               return var3;
            }

            if (var3) {
               return true;
            }
         }

         var10000 = var1;
         var10001 = Blocks.ENDER_CHEST;
      }

      if (var2 != 0) {
         if (var10000 == var10001) {
            boolean var4 = this.K.w();
            if (var2 == 0) {
               return var4;
            }

            if (var4) {
               return true;
            }
         }

         var10000 = var1;
         var10001 = Blocks.TRAPPED_CHEST;
      }

      label100: {
         if (var10000 == var10001) {
            var5 = this.p.w();
            if (var2 == 0) {
               break label100;
            }

            if (var5) {
               return true;
            }
         }

         var5 = this.y.w();
      }

      label94:
      if (var2 != 0) {
         if (var5) {
            var5 = Y.contains(var1);
            if (var2 == 0) {
               break label94;
            }

            if (var5) {
               return true;
            }
         }

         var5 = this.B.w();
      }

      if (var2 != 0) {
         if (var5) {
            boolean var6 = Z.contains(var1);
            if (var2 == 0) {
               return var6 && var1 == Blocks.BREWING_STAND;
            }

            if (var6) {
               return true;
            }
         }

         var5 = this.X.w();
      }

      return var5 && var1 == Blocks.BREWING_STAND;
   }

   private double u$D() {
      int var1 = ChestStealer.m();
      LocalPlayer var10000 = G.player;
      if (var1 == 0) {
         if (G.player == null) {
            return 4.5;
         }

         var10000 = G.player;
      }

      if (var1 == 0) {
         if (var10000.isCreative()) {
            return 6.0;
         }

         var10000 = G.player;
      }

      return var10000.blockInteractionRange();
   }

   @EventTarget
   public void G(EventClick var1) {
      int var2 = ChestStealer.d$I();
      if (this.w()) {
         LocalPlayer var10000 = G.player;
         if (var2 != 0) {
            if (G.player == null) {
               return;
            }

            var10000 = G.player;
         }

         boolean var3 = var10000.isUsingItem();
         if (var2 != 0) {
            if (var3) {
               return;
            }

            var3 = G.options.keyUse.isDown();
         }

         if (var2 != 0) {
            if (!var3) {
               return;
            }

            var3 = this.k();
         }

         if (var2 != 0) {
            if (var3) {
               return;
            }

            var3 = this.V();
         }

         if (var3) {
            var1.c(true);
         }
      }
   }

   private boolean k() {
      int var1 = ChestStealer.m();
      Minecraft var10000 = G;
      if (var1 == 0) {
         if (G.player == null) {
            return false;
         }

         var10000 = G;
      }

      if (var1 == 0) {
         if (var10000.level == null) {
            return false;
         }

         var10000 = G;
      }

      HitResult var2 = var10000.player.pick(this.u$D(), 1.0F, false);
      HitResult var5 = var2;
      if (var1 == 0) {
         if (var2 == null) {
            return false;
         }

         var5 = var2;
      }

      if (var1 == 0) {
         if (var5.getType() != Type.BLOCK) {
            return false;
         }

         var5 = var2;
      }

      BlockPos var3 = ((BlockHitResult)var5).getBlockPos();
      BlockState var4 = G.level.getBlockState(var3);
      return this.A(var4.getBlock());
   }

   private boolean V() {
      BlockEntity var43 = null;
      int var47 = 0;
      int var1 = ChestStealer.d$I();
      Minecraft var10000 = G;
      if (var1 != 0) {
         if (G.player == null) {
            return false;
         }

         var10000 = G;
      }

      if (var1 != 0) {
         if (var10000.level == null) {
            return false;
         }

         var10000 = G;
      }

      Vec3 var2 = var10000.player.getEyePosition(1.0F);
      Vec3 var3 = G.player.getViewVector(1.0F);
      double var4 = this.u$D();
      Vec3 var6 = var2.add(var3.scale(var4));
      double var7 = var4 * var4;
      BlockEntity var9 = null;
      double var10 = Double.MAX_VALUE;
      double var12 = Double.MAX_VALUE;
      Vec3 var14 = null;
      Direction var15 = null;
      BlockPos var16 = null;
      Iterator var17 = com.elowen.utils.ChunkUtils.R$Stream().iterator();

      while (true) {
         if (var17.hasNext()) {
            BlockEntity var18 = (BlockEntity)var17.next();
            var43 = var18;
            if (var1 == 0) {
               break;
            }

            if (var1 != 0) {
               if (var18 == null) {
                  continue;
               }

               var43 = var18;
            }

            BlockPos var19 = var43.getBlockPos();
            double var20 = var19.getX() + 0.5 - var2.x;
            double var22 = var19.getY() + 0.5 - var2.y;
            double var24 = var19.getZ() + 0.5 - var2.z;
            if (var20 * var20 + var22 * var22 + var24 * var24 > var7) {
               continue;
            }

            BlockState var26 = var18.getBlockState();
            Block var27 = var26.getBlock();
            GhostHand var44 = this;
            if (var1 != 0) {
               if (!this.A(var27)) {
                  continue;
               }

               var44 = this;
            }

            AABB var28 = var44.I(var18);
            AABB var45 = var28;
            if (var1 != 0) {
               if (var28 == null) {
                  continue;
               }

               var45 = var28;
            }

            Optional var29 = var45.clip(var2, var6);
            Object var46 = var29;
            if (var1 != 0) {
               if (var29.isEmpty()) {
                  continue;
               }

               var46 = var29.get();
            }

            Vec3 var30;
            double var31;
            double var34;
            label124: {
               byte var36;
               label150: {
                  var30 = (Vec3)var46;
                  var31 = var30.distanceTo(var2);
                  Vec3 var33 = var30.subtract(var2).normalize();
                  var34 = var3.dot(var33);
                  var36 = 0;
                  double var49;
                  var47 = (var49 = Math.abs(var31 - var10) - 1.0E-7) == 0.0 ? 0 : (var49 < 0.0 ? -1 : 1);
                  if (var1 != 0) {
                     if (var47 < 0) {
                        double var50;
                        var47 = (var50 = var34 - var12) == 0.0 ? 0 : (var50 < 0.0 ? -1 : 1);
                        if (var1 == 0) {
                           break label124;
                        }

                        if (var47 <= 0) {
                           break label150;
                        }

                        var36 = 1;
                        if (var1 != 0) {
                           break label150;
                        }
                     }

                     double var51;
                     var47 = (var51 = var31 - (var10 - 1.0E-7)) == 0.0 ? 0 : (var51 < 0.0 ? -1 : 1);
                  }

                  if (var1 == 0) {
                     break label124;
                  }

                  if (var47 < 0) {
                     var36 = 1;
                  }
               }

               var47 = var36;
            }

            if (var47 != 0) {
               var10 = var31;
               var12 = var34;
               var9 = var18;
               var16 = var19;
               var14 = var30;
               var15 = this.F(var28, var14, var2);
            }

            if (var1 != 0) {
               continue;
            }
         }

         var43 = var9;
         break;
      }

      if (var43 != null && var14 != null && var15 != null) {
         if (this.U.w()) {
            PrintStream var48 = System.out;
            String var10001 = String.valueOf(var16);
            String var10002 = String.valueOf(var15);
            double var37 = var10;
            String var39 = var10002;
            String var40 = var10001;
            String[] var41 = c;
            var48.println("[GhostHand] Interacting with " + var40 + " face=" + var39 + " dist=" + var37);
         }

         BlockHitResult var42 = new BlockHitResult(var14, var15, var16, false);
         G.gameMode.useItemOn(G.player, InteractionHand.MAIN_HAND, var42);
         com.elowen.utils.PlayerUtils.s(InteractionHand.MAIN_HAND);
         return true;
      } else {
         return false;
      }
   }

   private Direction F(AABB var1, Vec3 var2, Vec3 var3) {
      int var4;
      int var10000;
      var4 = ChestStealer.m();
      double var7;
      var10000 = (var7 = Math.abs(var2.x - var1.minX) - 0.001) == 0.0 ? 0 : (var7 < 0.0 ? -1 : 1);
      label135:
      if (var4 == 0) {
         if (var10000 < 0) {
            double var8;
            var10000 = (var8 = var3.x - var1.minX) == 0.0 ? 0 : (var8 < 0.0 ? -1 : 1);
            if (var4 != 0) {
               break label135;
            }

            if (var10000 > 0) {
               return Direction.WEST;
            }
         }

         double var9;
         var10000 = (var9 = Math.abs(var2.x - var1.maxX) - 0.001) == 0.0 ? 0 : (var9 < 0.0 ? -1 : 1);
      }

      label128:
      if (var4 == 0) {
         if (var10000 < 0) {
            double var10;
            var10000 = (var10 = var3.x - var1.maxX) == 0.0 ? 0 : (var10 < 0.0 ? -1 : 1);
            if (var4 != 0) {
               break label128;
            }

            if (var10000 < 0) {
               return Direction.EAST;
            }
         }

         double var11;
         var10000 = (var11 = Math.abs(var2.y - var1.minY) - 0.001) == 0.0 ? 0 : (var11 < 0.0 ? -1 : 1);
      }

      label121:
      if (var4 == 0) {
         if (var10000 < 0) {
            double var12;
            var10000 = (var12 = var3.y - var1.minY) == 0.0 ? 0 : (var12 < 0.0 ? -1 : 1);
            if (var4 != 0) {
               break label121;
            }

            if (var10000 > 0) {
               return Direction.DOWN;
            }
         }

         double var13;
         var10000 = (var13 = Math.abs(var2.y - var1.maxY) - 0.001) == 0.0 ? 0 : (var13 < 0.0 ? -1 : 1);
      }

      label114:
      if (var4 == 0) {
         if (var10000 < 0) {
            double var14;
            var10000 = (var14 = var3.y - var1.maxY) == 0.0 ? 0 : (var14 < 0.0 ? -1 : 1);
            if (var4 != 0) {
               break label114;
            }

            if (var10000 < 0) {
               return Direction.UP;
            }
         }

         double var15;
         var10000 = (var15 = Math.abs(var2.z - var1.minZ) - 0.001) == 0.0 ? 0 : (var15 < 0.0 ? -1 : 1);
      }

      label107:
      if (var4 == 0) {
         if (var10000 < 0) {
            double var16;
            var10000 = (var16 = var3.z - var1.minZ) == 0.0 ? 0 : (var16 < 0.0 ? -1 : 1);
            if (var4 != 0) {
               break label107;
            }

            if (var10000 > 0) {
               return Direction.NORTH;
            }
         }

         double var17;
         var10000 = (var17 = Math.abs(var2.z - var1.maxZ) - 0.001) == 0.0 ? 0 : (var17 < 0.0 ? -1 : 1);
      }

      label100: {
         if (var4 == 0) {
            if (var10000 >= 0) {
               break label100;
            }

            double var18;
            var10000 = (var18 = var3.z - var1.maxZ) == 0.0 ? 0 : (var18 < 0.0 ? -1 : 1);
         }

         if (var10000 < 0) {
            return Direction.SOUTH;
         }
      }

      LocalPlayer var6 = G.player;
      if (var4 == 0) {
         if (G.player == null) {
            return Direction.UP;
         }

         var6 = G.player;
      }

      Vec3 var5 = var6.getViewVector(1.0F);
      return Direction.getApproximateNearest(var5.x, var5.y, var5.z).getOpposite();
   }

   private AABB I(BlockEntity var1) {
      int var2 = ChestStealer.d$I();
      BlockEntity var10000 = var1;
      if (var2 != 0) {
         if (var1 instanceof ChestBlockEntity var4) {
            return this.b(var4);
         }

         var10000 = var1;
      }

      BlockPos var3 = var10000.getBlockPos();
      BlockPos var5 = var3;
      if (var2 != 0) {
         if (!com.elowen.utils.BlockUtils.N(var3)) {
            return null;
         }

         var5 = var3;
      }

      return com.elowen.utils.BlockUtils.l(var5);
   }

   private AABB b(ChestBlockEntity var1) {
      int var10000 = ChestStealer.m();
      BlockState var3 = var1.getBlockState();
      int var2 = var10000;
      BlockState var10 = var3;
      EnumProperty var10001 = ChestBlock.TYPE;
      if (var2 == 0) {
         if (!var3.hasProperty(ChestBlock.TYPE)) {
            return null;
         }

         var10 = var3;
         var10001 = ChestBlock.TYPE;
      }

      ChestType var4 = (ChestType)var10.getValue(var10001);
      if (var4 == ChestType.LEFT) {
         return null;
      }

      BlockPos var5 = var1.getBlockPos();
      AABB var6 = com.elowen.utils.BlockUtils.l(var5);
      if (var6 == null) {
         return null;
      }

      if (var4 == ChestType.RIGHT) {
         Direction var7 = ChestBlock.getConnectedDirection(var3);
         BlockPos var8 = var5.relative(var7);
         BlockPos var11 = var8;
         if (var2 == 0) {
            if (!com.elowen.utils.BlockUtils.N(var8)) {
               return var6;
            }

            var11 = var8;
         }

         AABB var9 = com.elowen.utils.BlockUtils.l(var11);
         if (var2 != 0) {
            return var9;
         }

         if (var9 != null) {
            var6 = var6.minmax(var9);
         }
      }

      return var6;
   }

   @EventTarget
   public void s(EventRender2D var1) {
      int var2 = ChestStealer.d$I();
      boolean var10000 = this.w();
      if (var2 != 0) {
         if (!var10000) {
            return;
         }

         var10000 = this.C.w();
      }

      if (var10000) {
         Minecraft var5 = G;
         if (var2 != 0) {
            if (G.player == null) {
               return;
            }

            var5 = G;
         }

         if (var5.level != null) {
            try {
               this.I.G$Canvas();
               this.C$h();
               this.I.p();
               this.I.G$V();
            } catch (RuntimeException var4) {
               System.err.println("[GhostHand] Render failed: " + var4);
            }

            return;
         }
      }
   }

   private void C$h() {
      float var2 = G.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var3 = G.gameRenderer.mainCamera().position();
      int var10000 = ChestStealer.d$I();
      double var4 = this.u$D();
      int var1 = var10000;
      float var6 = 16.0F;
      Typeface var7 = com.elowen.utils.renderer.Fonts.H(var6).getTypeface();

      for (BlockEntity var9 : (Iterable<BlockEntity>)(com.elowen.utils.ChunkUtils.R$Stream()::iterator)) {
         BlockEntity var16 = var9;
         if (var1 != 0) {
            if (var9 == null) {
               continue;
            }

            var16 = var9;
         }

         Block var10 = var16.getBlockState().getBlock();
         if (this.A(var10)) {
            BlockPos var11 = var9.getBlockPos();
            Vec3 var12 = new Vec3(var11.getX() + 0.5, var11.getY() + 1.2, var11.getZ() + 0.5);
            double var17 = var12.distanceTo(var3);
            double var10001 = var4;
            if (var1 != 0) {
               if (var17 > var4) {
                  continue;
               }

               var17 = var12.x;
               var10001 = var12.y;
            }

            Vector2f var13 = com.elowen.utils.ProjectionUtils.g(var17, var10001, var12.z, var2);
            Vector2f var18 = var13;
            if (var1 != 0) {
               if (var13 == null) {
                  continue;
               }

               var18 = var13;
            }

            float var20;
            var10000 = (var20 = var18.x - (Float.MAX_VALUE)) == 0.0F ? 0 : (var20 < 0.0F ? -1 : 1);
            if (var1 != 0) {
               if ((var10000 == 0)) {
                  continue;
               }

               float var21;
               var10000 = (var21 = var13.y - (Float.MAX_VALUE)) == 0.0F ? 0 : (var21 < 0.0F ? -1 : 1);
            }

            if (((var10000) != 0)) {
               double var14 = var12.distanceTo(var3);
               this.I.Y(this.W(var10, var14), var13.x, var13.y, var7, var6, this.h(var10).getRGB());
               if (var1 == 0) {
                  break;
               }
            }
         }
      }
   }

   private String W(Block var1, double var2) {
      String[] var5 = c;
      String var4 = ((java.lang.String)(J.getOrDefault(var1, "Chest")));
      return String.format("[%s] [%.1fm]", var4, var2);
   }

   private Color h(Block var1) {
      return ((java.awt.Color)(f.getOrDefault(var1, Color.YELLOW)));
   }

   @Override
   public void q$V() {
      super.q$V();
   }

   static {
      label82:
      m = new HashSet();
      Y = new HashSet();
      Z = new HashSet();
      J = new HashMap();
      f = new HashMap();
      for (Block var8 : Blocks.DYED_SHULKER_BOX.asList()) {
      Y.add(var8);
      }
      Y.add(Blocks.SHULKER_BOX);
      Z.addAll(Arrays.asList(Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER));
      m.add(Blocks.CHEST);
      m.add(Blocks.ENDER_CHEST);
      m.add(Blocks.TRAPPED_CHEST);
      m.addAll(Y);
      m.addAll(Z);
      m.add(Blocks.BREWING_STAND);
      String[] var14 = c;
      J.put(Blocks.CHEST, "Chest");
      J.put(Blocks.ENDER_CHEST, "Ender Chest");
      J.put(Blocks.TRAPPED_CHEST, "Trapped Chest");
      J.put(Blocks.FURNACE, "Furnace");
      J.put(Blocks.BLAST_FURNACE, "Blast Furnace");
      J.put(Blocks.SMOKER, "Smoker");
      J.put(Blocks.BREWING_STAND, "Brewing Stand");
      List var15 = Blocks.DYED_SHULKER_BOX.asList();
      DyeColor[] var16 = DyeColor.values();
      for (int var9 = 0; var9 < var16.length && var9 < var15.size(); var9++) {
      Block var10 = (Block)var15.get(var9);
      String var11 = var16[var9].getName();
      J.put(var10, var11.substring(0, 1).toUpperCase() + var11.substring(1) + " Shulker");
      }
      f.put(Blocks.ENDER_CHEST, Color.MAGENTA);
      f.put(Blocks.TRAPPED_CHEST, Color.RED);
      f.put(Blocks.FURNACE, Color.GRAY);
      f.put(Blocks.BLAST_FURNACE, Color.GRAY);
      f.put(Blocks.SMOKER, Color.GRAY);
      f.put(Blocks.BREWING_STAND, new Color(128, 0, 128));
      DyeColor[] var17 = DyeColor.values();
      List var18 = Blocks.DYED_SHULKER_BOX.asList();
      for (int var19 = 0; var19 < var17.length && var19 < var18.size(); var19++) {
      f.put((Block)var18.get(var19), A(var17[var19]));
      }
      f.put(Blocks.SHULKER_BOX, new Color(180, 80, 255));
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
