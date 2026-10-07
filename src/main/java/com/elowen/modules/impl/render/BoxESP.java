package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "BoxESP", a = "Renders 3D boxes around entities or blocks (software 3D via Skija)", M = Category.RENDER)
public class BoxESP extends Module {
   public ModeValue d;
   public BooleanValue U;
   public FloatValue S;
   public FloatValue m;
   public FloatValue M;
   public FloatValue i;
   public FloatValue J;
   public FloatValue e;
   private final com.elowen.utils.renderer.threeD.Skija3DRenderer q;
   private static final String[] b = new String[]{"Block", "Fill Alpha", "Green", "Block", "Fill", "Players", "Line Width", "Red", "Range", "Both", "[BoxESP] Render failed: ", "Both", "Blue", "Mode", "Players"};
   public BoxESP() {
      String[] var1 = b;
      this.d = ValueBuilder.m(this, "Mode").m(0).W(new String[]{"Players", "Block", "Both"}).f$K().T$t();
      this.U = ValueBuilder.m(this, "Fill").h(true).f$K().f$O();
      this.S = ValueBuilder.m(this, "Fill Alpha").l(this.U::w).w(10.0F).M(120.0F).d(45.0F).V(1.0F).f$K().L();
      this.m = ValueBuilder.m(this, "Line Width").w(0.5F).M(4.0F).d(1.5F).V(0.1F).f$K().L();
      this.M = ValueBuilder.m(this, "Range").w(5.0F).M(256.0F).d(64.0F).V(1.0F).f$K().L();
      this.i = ValueBuilder.m(this, "Red").w(0.0F).M(255.0F).d(6.0F).V(1.0F).f$K().L();
      this.J = ValueBuilder.m(this, "Green").w(0.0F).M(255.0F).d(176.0F).V(1.0F).f$K().L();
      this.e = ValueBuilder.m(this, "Blue").w(0.0F).M(255.0F).d(241.0F).V(1.0F).f$K().L();
      this.q = new com.elowen.utils.renderer.threeD.Skija3DRenderer();
   }

   @com.elowen.events.api.EventTarget
   public void j(EventRender var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (G.level != null && G.player != null && this.w()) {
         try {
            float var3 = G.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            Camera var4 = G.gameRenderer.mainCamera();
            if (!var4.isInitialized()) {
               return;
            }

            Canvas var5 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
            if (var5 == null) {
               return;
            }

            this.q.I(var5, new WorldProjector(var4, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), ViewBob.g$Matrix4f()));
            if (this.d.t("Players") || this.d.t("Both")) {
               this.t(this.q, var3);
            }

            if (this.d.t("Block") || this.d.t("Both")) {
               this.U(this.q);
            }

            this.q.G$V();
            com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
            if (!this.q.s$Z()) {
               com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
            }
         } catch (RuntimeException var6) {
            System.err.println("[BoxESP] Render failed: " + var6);
         }
      }
   }

   private void t(com.elowen.utils.renderer.threeD.Skija3DRenderer var1, float var2) {
      HasValue[] var10000 = Theme.s$ArrQ();
      double var4 = (double)this.M.o$F() * this.M.o$F();
      HasValue[] var3 = var10000;

      for (Entity var7 : G.level.entitiesForRendering()) {
         Entity var16 = var7;
         if (var3 != null) {
            if (!(var7 instanceof Player)) {
               continue;
            }

            var16 = var7;
         }

         label48: {
            LocalPlayer var10001 = G.player;
            if (var3 != null) {
               if (var16 == G.player) {
                  continue;
               }

               var16 = var7;
               if (var3 == null) {
                  break label48;
               }

               var10001 = G.player;
            }

            if (var16.distanceToSqr(var10001) > var4) {
               continue;
            }

            var16 = var7;
         }

         AABB var8 = var16.getBoundingBox();
         double var9 = Mth.lerp(var2, var7.xOld, var7.getX()) - var7.getX();
         double var11 = Mth.lerp(var2, var7.yOld, var7.getY()) - var7.getY();
         double var13 = Mth.lerp(var2, var7.zOld, var7.getZ()) - var7.getZ();
         AABB var15 = var8.move(var9, var11, var13);
         this.V(var1, var15.minX, var15.minY, var15.minZ, var15.maxX, var15.maxY, var15.maxZ);
         if (var3 == null) {
            break;
         }
      }
   }

   private void U(com.elowen.utils.renderer.threeD.Skija3DRenderer var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      HitResult var3 = G.hitResult;
      HasValue[] var2 = var10000;
      HitResult var5 = var3;
      if (var2 != null) {
         if (var3 == null) {
            return;
         }

         var5 = var3;
      }

      if (var2 != null) {
         if (var5.getType() != Type.BLOCK) {
            return;
         }

         var5 = var3;
      }

      BlockPos var4 = ((BlockHitResult)var5).getBlockPos();
      this.V(var1, var4.getX(), var4.getY(), var4.getZ(), var4.getX() + 1.0, var4.getY() + 1.0, var4.getZ() + 1.0);
   }

   private void V(com.elowen.utils.renderer.threeD.Skija3DRenderer var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      HasValue[] var10000 = Theme.s$ArrQ();
      int var15 = (int)this.i.o$F();
      HasValue[] var14 = var10000;
      int var16 = (int)this.J.o$F();
      int var17 = (int)this.e.o$F();
      int var18 = com.elowen.utils.renderer.RenderUtils.O(var15, var16, var17, 255);
      int var20 = ((this.U.w()) ? 1 : 0);
      if (var14 != null) {
         var20 = (byte)(var20 != 0 ? com.elowen.utils.renderer.RenderUtils.O(var15, var16, var17, (int)this.S.o$F()) : 0);
      }

      byte var19 = (byte)var20;
      var1.u(var2, var4, var6, var8, var10, var12, var19, var18, this.m.o$F());
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
