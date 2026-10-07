package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.render.BetterNameTag;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubmitNodeStorage.class)
public class MixinSubmitNodeStorage {
   @Unique
   private static final int SPLIT_WIDTH = Integer.MAX_VALUE;

   @Inject(method = "submitNameTag", at = @At("HEAD"), cancellable = true)
   private void elowen$onSubmitNameTag(PoseStack var1, Vec3 var2, int var3, Component var4, boolean var5, int var6, CameraRenderState var7, CallbackInfo var8) {
      if (var2 != null) {
         BetterNameTag var9 = (BetterNameTag)Elowen.S$Elowen().q$ModuleManager().A(BetterNameTag.class);
         if (var9 != null && var9.w()) {
            Minecraft var10 = Minecraft.getInstance();
            Font var11 = var10.font;
            List var12 = var11.split(var4, Integer.MAX_VALUE);
            if (var12.size() > 1) {
               int var13 = 0;
               int[] var14 = new int[var12.size()];

               for (int var15 = 0; var15 < var12.size(); var15++) {
                  var14[var15] = var11.width((FormattedCharSequence)var12.get(var15));
                  var13 = Math.max(var13, var14[var15]);
               }

               int var27 = 9 + 1;
               int var16 = var12.size() * var27 - 1;
               int var17 = ARGB.color(var10.gameRenderer.gameRenderState().optionsRenderState.getBackgroundOpacity(0.25F), -16777216);
               SubmitNodeStorage var18 = (SubmitNodeStorage)(Object)this;
               var1.pushPose();
               var1.translate(var2.x, var2.y + 0.5, var2.z);
               var1.rotate(var7.orientation);
               var1.scale(0.025F, -0.025F, 0.025F);
               float var19 = -var13 / 2.0F - 1.0F;
               float var20 = var3 - 1.0F;
               float var21 = var13 / 2.0F + 1.0F;
               float var22 = var3 + var16;
               var18.submitTextBackground(var1, var19, var20, var21, var22, var17, var5 ? DisplayMode.SEE_THROUGH : DisplayMode.NORMAL, var6);
               SubmitNodeCollection var23 = var18.order(0);

               for (int var24 = 0; var24 < var12.size(); var24++) {
                  float var25 = -var14[var24] / 2.0F;
                  float var26 = var3 + var24 * var27;
                  if (var5) {
                     var23.submitText(
                        var1,
                        var25,
                        var26,
                        (FormattedCharSequence)var12.get(var24),
                        false,
                        DisplayMode.NORMAL,
                        LightCoordsUtil.lightCoordsWithEmission(var6, 2),
                        -1,
                        0,
                        0
                     );
                     var23.submitText(var1, var25, var26, (FormattedCharSequence)var12.get(var24), false, DisplayMode.SEE_THROUGH, var6, -2130706433, 0, 0);
                  } else {
                     var23.submitText(var1, var25, var26, (FormattedCharSequence)var12.get(var24), false, DisplayMode.NORMAL, var6, -2130706433, 0, 0);
                  }
               }

               var1.popPose();
               var8.cancel();
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
