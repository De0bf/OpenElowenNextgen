package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.utils.ProjectionUtils;
import com.elowen.utils.renderer.SkijaEffects;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;

@ModuleInfo(R = "TargetHUD", a = "Displays current KillAura target's health with animated health bar", M = Category.RENDER)
public class TargetHUD extends Module {
   private static final float q = 30.0F;
   private static final float U = 5.0F;
   private static final float V = 9.5564F;
   private static final float y = 9.5564F;
   private static final float o = 10.9216F;
   private static final int x;
   private static final int C;
   private final FloatValue d;
   private final BooleanValue h;
   private final FloatValue F;
   private float X;
   private Entity c;
   private long K;
   private float v;
   private float Q;
   private boolean p;
   private final SkijaRenderer M;
   private static final String[] b = new String[]{"Follow Target", "Animation Duration", "[TargetHUD] Render failed: ", " (Baby)", "World Offset", "HP: "};
   public TargetHUD() {
      String[] var1 = b;
      this.d = com.elowen.values.ValueBuilder.m(this, "Animation Duration").d(0.2F).V(0.01F).w(0.05F).M(1.0F).f$K().L();
      this.h = com.elowen.values.ValueBuilder.m(this, "Follow Target").h(false).f$K().f$O();
      this.F = com.elowen.values.ValueBuilder.m(this, "World Offset").l(this.h::w).d(0.5F).V(0.1F).w(0.0F).M(5.0F).f$K().L();
      this.X = -1.0F;
      this.c = null;
      this.K = 0L;
      this.v = 0.0F;
      this.Q = 0.0F;
      this.p = false;
      this.M = SkiaRenderManager.X$m();
   }

   @Override
   public void h$V() {
      this.X = -1.0F;
      this.c = null;
      this.p = false;
      this.K = 0L;
      this.v = 0.0F;
      this.Q = 0.0F;
   }

   @com.elowen.events.api.EventTarget
   public void v(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (this.w() && NameTags$NameTagData.level != null && NameTags$NameTagData.player != null) {
         Aura var3 = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
         if (var3 != null && var3.w()) {
            if (Aura.cj instanceof LivingEntity var5) {
               ;
            }
         }
      }
   }

   private void S(Entity var1, float var2) {
      HasValue[] var3 = Theme.s$ArrQ();
      if (var1 != this.c || Math.abs(var2 - this.Q) > 0.001F) {
         this.v = this.X;
         this.Q = var2;
         this.K = System.currentTimeMillis();
         this.p = true;
         this.c = var1;
      }

      if (this.p) {
         long var4 = System.currentTimeMillis();
         float var6 = (float)(var4 - this.K) / 1000.0F;
         float var7 = this.d.o$F();
         if (var6 >= var7) {
            this.X = this.Q;
            this.p = false;
         }

         float var8 = var6 / var7;
         float var9 = 1.0F - (float)Math.pow(1.0F - var8, 3.0);
         this.X = this.v + (this.Q - this.v) * var9;
         this.X = Math.max(0.0F, Math.min(1.0F, this.X));
      }

      if (this.X == -1.0F) {
         this.X = var2;
         this.v = var2;
         this.Q = var2;
      }
   }

   private void j(Canvas var1, LivingEntity var2) {
      HasValue[] var3 = Theme.s$ArrQ();
      String var4 = var2.getName().getString() + (var2.isBaby() ? " (Baby)" : "");
      String var23 = var2.getAbsorptionAmount() > 0.0F ? "+" + Math.round(var2.getAbsorptionAmount()) : "";
      int var24 = Math.round(var2.getHealth());
      String var5 = "HP: " + var24 + var23;
      Typeface var6 = com.elowen.utils.renderer.Fonts.H(9.5564F).getTypeface();
      if (var6 == null) {
         var6 = this.M.c$Typeface();
      }

      float var7 = this.M.W(var4, var6, 10.9216F);
      float var8 = this.M.W(var5, var6, 9.5564F);
      float var9 = Math.max(var7 + 10.0F, 60.0F);
      var9 = Math.max(var9, var8 + 10.0F);
      if (this.h.w()) {
         float var12 = NameTags$NameTagData.getDeltaTracker().getGameTimeDeltaPartialTick(false);
         Vec3 var13 = var2.getPosition(var12).add(0.0, var2.getBbHeight() / 2.0, 0.0);
         float var14 = (float)Math.toRadians(NameTags$NameTagData.player.getYRot());
         double var15 = -Math.cos(var14);
         double var17 = -Math.sin(var14);
         double var19 = this.F.o$F();
         Vec3 var21 = var13.add(var15 * var19, 0.0, var17 * var19);
         Vector2f var22 = ProjectionUtils.g(var21.x, var21.y, var21.z, var12);
         if (var22 == null || var22.x == Float.MAX_VALUE || var22.y == Float.MAX_VALUE) {
            return;
         }

         float var10 = var22.x - var9 / 2.0F;
         float var11 = var22.y - 15.0F;
      }

      float var26 = NameTags$NameTagData.getWindow().getGuiScaledWidth() / 2.0F + 10.0F;
      float var27 = NameTags$NameTagData.getWindow().getGuiScaledHeight() / 2.0F + 10.0F;
      Theme var28 = Theme.K();
      if (var28 != null) {
         var28.g$I();
      }

      int var29 = var28 != null ? Theme.f : C;
      com.elowen.utils.renderer.SkijaEffects.T(this.M, var1, var26, var27, var9, 30.0F, 5.0F, List.of(new float[]{var26, var27, var9, 30.0F}));
      var1.save();
      var1.clipRRect(RRect.makeLTRB(var26, var27, var26 + var9, var27 + 30.0F, 5.0F), true);
      this.M.d(var26, var27, var9, 30.0F, 5.0F, var29);
      float var30 = var26 + var9 * this.X;
      if (var30 > var26) {
         var1.save();
         var1.clipRect(Rect.makeLTRB(var26, var27, var30, var27 + 5.0F));
         this.v(var28, var26, var27, var9, 3.0F);
         var1.restore();
      }

      var1.restore();
      this.M.Y(var4, var26 + 5.0F, var27 + 6.0F, var6, 9.5564F, -1);
      this.M.Y(var5, var26 + 5.0F, var27 + 17.0F, var6, 9.5564F, -1);
   }

   private void v(Theme var1, float var2, float var3, float var4, float var5) {
      HasValue[] var6 = Theme.s$ArrQ();
      if (var1 == null) {
         this.M.d(var2, var3, var4, var5, 0.0F, x);
      } else {
         var1.A(var2, var3, var4, var5);
      }
   }

   @Override
   public void q$V() {
      super.q$V();
   }

   static {
      x = new Color(6, 176, 241, 255).getRGB();
      C = new Color(20, 20, 20, 180).getRGB();
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
