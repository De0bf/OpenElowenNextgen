package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventUseItemRayTrace;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Item.class)
public class MixinItem {
   @Redirect(method = "getPlayerPOVHitResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getYRot()F"))
   private static float hookRayTraceYRot(Player var0) {
      EventUseItemRayTrace var1 = new EventUseItemRayTrace(var0.getYRot(), var0.getXRot());
      Elowen.S$Elowen().e().B(var1);
      return var1.h$F();
   }

   @Redirect(method = "getPlayerPOVHitResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getXRot()F"))
   private static float hookRayTraceXRot(Player var0) {
      EventUseItemRayTrace var1 = new EventUseItemRayTrace(var0.getYRot(), var0.getXRot());
      Elowen.S$Elowen().e().B(var1);
      return var1.a$F();
   }
}
