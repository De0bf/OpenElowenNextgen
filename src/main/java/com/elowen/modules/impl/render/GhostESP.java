package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.GhostHitBoxes;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.AABB;

@ModuleInfo(R = "GhostESP", a = "Draws the real GhostHitBoxes that Velocity Reduce records for aiming", M = Category.RENDER)
public class GhostESP extends Module {
   public FloatValue b;
   public BooleanValue i;
   private final com.elowen.utils.renderer.threeD.Skija3DRenderer p;
   private static final String[] c = new String[]{"Alpha", "[GhostESP] Render failed: ", "Show Alink"};
   public GhostESP() {
      String[] var1 = c;
      this.b = ValueBuilder.m(this, "Alpha").w(10.0F).M(150.0F).d(60.0F).V(1.0F).f$K().L();
      this.i = ValueBuilder.m(this, "Show Alink").h(true).f$K().f$O();
      this.p = new com.elowen.utils.renderer.threeD.Skija3DRenderer();
   }

   @com.elowen.events.api.EventTarget
   public void K(EventRender var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null && this.w()) {
         if (this.i.w()) {
            if (GhostHitBoxes.R$Z()) {
               try {
                  Camera var3 = NameTags$NameTagData.gameRenderer.mainCamera();
                  if (!var3.isInitialized()) {
                     return;
                  }

                  Canvas var4 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
                  if (var4 == null) {
                     return;
                  }

                  this.p.I(var4, new WorldProjector(var3, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), ViewBob.g$Matrix4f()));
                  int var5 = com.elowen.utils.renderer.RenderUtils.O(255, 150, 0, (int)this.b.o$F());
                  Iterator var6 = GhostHitBoxes.Y().iterator();
                  while (var6.hasNext()) {
                     Entry var7 = (Entry)var6.next();
                     this.h(this.p, (AABB)var7.getValue(), var5);
                  }

                  this.p.G$V();
                  com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
                  if (!this.p.s$Z()) {
                     com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
                  }
               } catch (RuntimeException var8) {
                  System.err.println("[GhostESP] Render failed: " + var8);
               }
            }
         }
      }
   }

   @Override
   public void h$V() {
   }

   @Override
   public void q$V() {
   }

   private void h(com.elowen.utils.renderer.threeD.Skija3DRenderer var1, AABB var2, int var3) {
      double var5 = var2.minX;
      double var7 = var2.minY;
      double var9 = var2.minZ;
      double var11 = var2.maxX;
      double var13 = var2.maxY;
      HasValue[] var10000 = Theme.s$ArrQ();
      double var15 = var2.maxZ;
      HasValue[] var4 = var10000;
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

      for (int[] var22 : var18) {
         double[][] var23 = new double[][]{var17[var22[0]], var17[var22[1]], var17[var22[2]], var17[var22[3]]};
         var1.R(var23, var3);
         if (var4 == null) {
            break;
         }
      }
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
