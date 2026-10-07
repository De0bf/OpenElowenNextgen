package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender2D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.BlinkingPlayer;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.Point;
import java.util.Iterator;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

@ModuleInfo(R = "Compass", a = "Shows a compass", M = Category.RENDER)
public class Compass extends Module {
   public BooleanValue M;
   public BooleanValue K;
   private final SkijaRenderer z;
   private static final String[] b = new String[]{"No Player Only", "Compass Only", "[Compass] HUD render failed: "};
   public Compass() {
      String[] var1 = b;
      this.M = ValueBuilder.m(this, "Compass Only").h(true).f$K().f$O();
      this.K = ValueBuilder.m(this, "No Player Only").h(true).f$K().f$O();
      this.z = SkiaRenderManager.X$m();
   }

   private BlockPos R(ClientLevel var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 == null ? null : var1.getLevelData().getRespawnData().pos();
   }

   private boolean p() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null) {
         Iterator var2 = NameTags$NameTagData.level.entitiesForRendering().iterator();
         while (var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if (var3 != NameTags$NameTagData.player && !(var3 instanceof BlinkingPlayer) && var3 instanceof Player) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @com.elowen.events.api.EventTarget
   public void B(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null && this.w()) {
         if (!this.M.w() || InventoryUtils.C(Items.COMPASS)) {
            if (!this.K.w() || !this.p()) {
               BlockPos var3 = this.R(NameTags$NameTagData.level);
               if (var3 != null) {
                  float var4 = NameTags$NameTagData.getDeltaTracker().getGameTimeDeltaPartialTick(false);
                  double var5 = Mth.lerp(var4, NameTags$NameTagData.player.xOld, NameTags$NameTagData.player.getX());
                  double var7 = Mth.lerp(var4, NameTags$NameTagData.player.zOld, NameTags$NameTagData.player.getZ());
                  float var9 = com.elowen.utils.rotation.RotationManager.X != null
                     ? com.elowen.utils.rotation.RotationManager.X.H
                     : Mth.wrapDegrees(Mth.lerp(var4, NameTags$NameTagData.player.yRotO, NameTags$NameTagData.player.getYRot()));
                  float var10 = (float)(Math.toDegrees(Math.atan2(var3.getZ() - var7, var3.getX() - var5)) - 90.0 - var9);
                  float var11 = NameTags$NameTagData.getWindow().getGuiScaledWidth() / 2.0F;
                  float var12 = NameTags$NameTagData.getWindow().getGuiScaledHeight() / 2.0F;

                  try {
                     Canvas var13 = this.z.G$Canvas();
                     if (var13 == null) {
                        return;
                     }

                     this.Q(var13, var11, var12, var10);
                     this.z.p();
                     this.z.G$V();
                  } catch (RuntimeException var14) {
                     System.err.println("[Compass] HUD render failed: " + var14);
                  }
               }
            }
         }
      }
   }

   private void Q(Canvas param1, float param2, float param3, float param4) {
      param1.save();
      HasValue[] var5 = Theme.s$ArrQ();
      param1.translate(param2, param3);
      param1.rotate(param4);
      param1.translate(-param2, -param3);
      float var6 = param3 - 45.0F;
      float var7 = param3 - 35.0F;
      float var8 = 5.0F;
      Paint var9 = new Paint();

      try {
         Path var10 = Path.makePolygon(new Point[]{new Point(param2, var6), new Point(param2 - var8, var7), new Point(param2 + var8, var7)}, true);

         try {
            var9.setColor(-1);
            var9.setAntiAlias(true);
            var9.setMode(PaintMode.FILL);
            param1.drawPath(var10, var9);
         } catch (Throwable var15) {
            if (var10 != null) {
               try {
                  var10.close();
               } catch (Throwable var12) {
                  var15.addSuppressed(var12);
               }
            }

            throw var15;
         }

         if (var10 != null) {
            var10.close();
         }
      } catch (Throwable var16) {
         try {
            var9.close();
         } catch (Throwable var14) {
            var16.addSuppressed(var14);
         }

         throw var16;
      }

      param1.restore();
   }

   @Override
   public void q$V() {
      super.q$V();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
   }
}
