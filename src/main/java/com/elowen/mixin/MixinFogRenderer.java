package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.render.AntiBlindness;
import com.elowen.modules.impl.render.ClearLava;
import com.elowen.modules.impl.render.ClearWater;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {
   @Inject(method = "setupFog", at = @At("RETURN"))
   private void onSetupFog(Camera var1, int var2, DeltaTracker var3, float var4, ClientLevel var5, CallbackInfoReturnable var6) {
      if (Elowen.S$Elowen().q$ModuleManager() != null) {
         FogData var7 = (FogData)var6.getReturnValue();
         FogType var8 = var1.getFluidInCamera();
         if (var8 == FogType.LAVA) {
            ClearLava var9 = (ClearLava)Elowen.S$Elowen().q$ModuleManager().A(ClearLava.class);
            if (var9 != null && var9.w()) {
               this.setFogFar(var7);
            }
         } else if (var8 == FogType.WATER) {
            ClearWater var12 = (ClearWater)Elowen.S$Elowen().q$ModuleManager().A(ClearWater.class);
            if (var12 != null && var12.w()) {
               this.setFogFar(var7);
            }
         }

         AntiBlindness var13 = (AntiBlindness)Elowen.S$Elowen().q$ModuleManager().A(AntiBlindness.class);
         if (var13 != null && var13.w() && var1.entity() instanceof LivingEntity var11 && var11.hasEffect(MobEffects.BLINDNESS)) {
            this.setFogFar(var7);
         }
      }
   }

   private void setFogFar(FogData var1) {
      var1.environmentalStart = Float.MAX_VALUE;
      var1.environmentalEnd = Float.MAX_VALUE;
      var1.renderDistanceStart = Float.MAX_VALUE;
      var1.renderDistanceEnd = Float.MAX_VALUE;
      var1.skyEnd = Float.MAX_VALUE;
      var1.cloudEnd = Float.MAX_VALUE;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
