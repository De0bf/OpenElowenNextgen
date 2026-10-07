package com.elowen.modules.impl.render;

import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventRender;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.BlockUtils;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.utils.renderer.threeD.Skija3DRenderer;
import com.elowen.values.HasValue;
import io.github.humbleui.skija.Canvas;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;

@ModuleInfo(R = "ChestESP", a = "Highlights chests", M = Category.RENDER)
public class ChestESP extends Module {
   private static final int h;
   private static final int M;
   private final List m = new CopyOnWriteArrayList();
   private final List I = new CopyOnWriteArrayList();
   private final Skija3DRenderer S = new Skija3DRenderer();
   private static final String b;

   @Override
   public void q$V() {
   }

   @com.elowen.events.api.EventTarget
   public void T(EventRespawn var1) {
      this.m.clear();
   }

   @com.elowen.events.api.EventTarget
   public void l(EventPacket var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.M() == EventType.RECEIVE && var1.R$Packet() instanceof ClientboundBlockEventPacket) {
         ClientboundBlockEventPacket var3 = (ClientboundBlockEventPacket)var1.R$Packet();
         if ((var3.getBlock() == Blocks.CHEST || var3.getBlock() == Blocks.TRAPPED_CHEST) && var3.getB0() == 1 && var3.getB1() == 1) {
            this.m.add(var3.getPos());
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void z(com.elowen.events.impl.EventMotion var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.Q() == EventType.PRE) {
         java.util.ArrayList var3 = ((java.util.ArrayList)(com.elowen.utils.ChunkUtils.R$Stream().collect(Collectors.toCollection(java.util.ArrayList::new))));
         this.I.clear();
         Iterator var4 = var3.iterator();
         while (var4.hasNext()) {
            BlockEntity var5 = (BlockEntity)var4.next();
            if (var5 instanceof ChestBlockEntity var6) {
               AABB var7 = this.r(var6);
               if (var7 != null) {
                  this.I.add(var7);
               }
            }
         }
      }
   }

   private AABB r(ChestBlockEntity var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      BlockState var3 = var1.getBlockState();
      HasValue[] var2 = var10000;
      BlockState var9 = var3;
      EnumProperty var10001 = ChestBlock.TYPE;
      if (var2 != null) {
         if (!var3.hasProperty(ChestBlock.TYPE)) {
            return null;
         }

         var9 = var3;
         var10001 = ChestBlock.TYPE;
      }

      ChestType var4 = (ChestType)var9.getValue(var10001);
      if (var4 == ChestType.LEFT) {
         return null;
      }

      BlockPos var5 = var1.getBlockPos();
      AABB var6 = BlockUtils.l(var5);
      if (var4 != ChestType.SINGLE) {
         BlockPos var7 = var5.relative(ChestBlock.getConnectedDirection(var3));
         BlockPos var10 = var7;
         if (var2 != null) {
            if (!BlockUtils.N(var7)) {
               return var6;
            }

            var10 = var7;
         }

         AABB var8 = BlockUtils.l(var10);
         var6 = var6.minmax(var8);
      }

      return var6;
   }

   @com.elowen.events.api.EventTarget
   public void D(EventRender var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (G.level != null && G.player != null && this.w()) {
         if (!this.I.isEmpty()) {
            try {
               Camera var3 = G.gameRenderer.mainCamera();
               if (!var3.isInitialized()) {
                  return;
               }

               Canvas var4 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
               if (var4 == null) {
                  return;
               }

               this.S.I(var4, new WorldProjector(var3, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), ViewBob.g$Matrix4f()));
               Iterator var5 = this.I.iterator();
               while (var5.hasNext()) {
                  AABB var6 = (AABB)var5.next();
                  boolean var7 = this.m.contains(BlockPos.containing(var6.minX, var6.minY, var6.minZ));
                  this.Q(this.S, var6, var7 ? M : h);
               }

               this.S.G$V();
               com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
               if (!this.S.s$Z()) {
                  com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
               }
            } catch (RuntimeException var8) {
               System.err.println(b + var8);
            }
         }
      }
   }

   private void Q(Skija3DRenderer var1, AABB var2, int var3) {
      double var5 = var2.minX;
      double var7 = var2.minY;
      double var9 = var2.minZ;
      double var11 = var2.maxX;
      HasValue[] var10000 = Theme.s$ArrQ();
      double var13 = var2.maxY;
      double var15 = var2.maxZ;
      double[][] var17 = new double[][]{
         {var5, var7, var9},
         {var11, var7, var9},
         {var11, var7, var15},
         {var5, var7, var15},
         {var5, var13, var9},
         {var11, var13, var9},
         {var11, var13, var15},
         {var5, var13, var15}
      };
      int[][] var18 = new int[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {0, 1, 5, 4}, {1, 2, 6, 5}, {2, 3, 7, 6}, {3, 0, 4, 7}};
      int[][] var19 = var18;
      int var20 = var19.length;
      HasValue[] var4 = var10000;
      int var21 = 0;

      while (var21 < var20) {
         int[] var22 = var19[var21];
         double[][] var23 = new double[][]{var17[var22[0]], var17[var22[1]], var17[var22[2]], var17[var22[3]]};
         var1.R(var23, var3);
         var21++;
         if (var4 == null) {
            break;
         }
      }
   }

   static {
      b = "[ChestESP] Render failed: ";
      h = com.elowen.utils.renderer.RenderUtils.O(0, 255, 0, 60);
      M = com.elowen.utils.renderer.RenderUtils.O(255, 0, 0, 60);
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
