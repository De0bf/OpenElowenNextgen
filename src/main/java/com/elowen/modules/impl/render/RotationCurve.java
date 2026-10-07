package com.elowen.modules.impl.render;

import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventRender;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(R = "RotationCurve", a = "Draws a trail curve of the current view direction endpoint", M = Category.RENDER)
public class RotationCurve extends Module {
   private static final int p = 20;
   private static final int Y = -13312;
   private static final float R = 0.2F;
   private static final int q = 10;
   public BooleanValue E;
   public FloatValue h;
   public FloatValue Q;
   public BooleanValue r;
   private final com.elowen.utils.renderer.threeD.Skija3DRenderer J;
   private final Deque l;
   private long P;
   private float Z;
   private ClientLevel o;
   private static final String[] b = new String[]{"Block", "Capture", "Disappear Time", "[RotationCurve] Render failed: ", "Never Disappear"};
   public RotationCurve() {
      String[] var1 = b;
      this.E = ValueBuilder.m(this, "Capture").h(true).f$K().f$O();
      this.h = ValueBuilder.m(this, "Block").w(0.0F).M(5.0F).V(0.01F).d(3.0F).f$K().L();
      this.Q = ValueBuilder.m(this, "Disappear Time").w(0.1F).M(30.0F).V(0.1F).d(5.0F).f$K().L();
      this.r = ValueBuilder.m(this, "Never Disappear").h(false).f$K().f$O();
      this.J = new com.elowen.utils.renderer.threeD.Skija3DRenderer();
      this.l = new ArrayDeque();
      this.Z = Float.NaN;
   }

   @com.elowen.events.api.EventTarget
   public void I(EventTick var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.s$f() == EventType.POST) {
         this.P++;
         if (NameTags$NameTagData.player != null && NameTags$NameTagData.level != null) {
            if (NameTags$NameTagData.level != this.o) {
               this.l.clear();
               this.o = NameTags$NameTagData.level;
            }

            float var3 = this.h.o$F();
            if (var3 != this.Z) {
               this.l.clear();
               this.Z = var3;
            }

            if (this.E.w()) {
               Vec3 var4 = com.elowen.utils.rotation.RotationUtils.b(NameTags$NameTagData.player.getYRot(), NameTags$NameTagData.player.getXRot());
               Vec3 var5 = NameTags$NameTagData.player.getEyePosition(1.0F);
               Vec3 var6 = var5.add(var4.x * var3, var4.y * var3, var4.z * var3);
               this.l.addLast(new RotationCurve$TrailPoint(var6, this.P));
            }

            if (!this.r.w()) {
               long var7 = (long)(this.Q.o$F() * 20.0F);
               if (this.l.size() > 1 && this.P - ((RotationCurve$TrailPoint)this.l.peekFirst()).c > var7) {
                  this.l.removeFirst();
               }
            }
         } else {
            this.l.clear();
            this.o = null;
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void l(EventRender var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null && this.w()) {
         if (!this.l.isEmpty()) {
            try {
               Camera var3 = NameTags$NameTagData.gameRenderer.mainCamera();
               if (!var3.isInitialized()) {
                  return;
               }

               Canvas var4 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
               if (var4 == null) {
                  return;
               }

               this.J.I(var4, new WorldProjector(var3, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), ViewBob.g$Matrix4f()));
               double[][] var5 = new double[this.l.size()][3];
               int var6 = 0;
               Iterator var7 = this.l.iterator();
               while (var7.hasNext()) {
                  RotationCurve$TrailPoint var8 = (RotationCurve$TrailPoint)var7.next();
                  var5[var6][0] = var8.L.x;
                  var5[var6][1] = var8.L.y;
                  var5[var6][2] = var8.L.z;
                  var6++;
               }

               boolean var11 = false;
               if (var5.length >= 2) {
                  this.J.N(var5, -13312, 0.2F, 10);
                  var11 = true;
               }

               this.J.G$V();
               RotationCurve$TrailPoint var12 = (RotationCurve$TrailPoint)this.l.peekLast();
               if (var12 != null) {
                  this.J.G(var12.L.x, var12.L.y, var12.L.z, -13312, 3.0F);
                  var11 = true;
               }

               com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
               if (var11) {
                  com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
               }
            } catch (RuntimeException var9) {
               System.err.println("[RotationCurve] Render failed: " + var9);
            }
         }
      }
   }

   @Override
   public void h$V() {
      super.h$V();
      this.l.clear();
      this.P = 0L;
      this.Z = Float.NaN;
   }

   @Override
   public void q$V() {
      super.q$V();
      this.l.clear();
      this.P = 0L;
      this.Z = Float.NaN;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }

   static {
   }
}
