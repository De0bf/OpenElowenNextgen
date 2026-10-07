package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventUpdateFoV;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {
   @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
   private void hookFoV(CallbackInfoReturnable var1) {
      Float var2 = (Float)var1.getReturnValue();
      EventUpdateFoV var3 = new EventUpdateFoV(var2);
      Elowen.S$Elowen().e().B(var3);
      var1.setReturnValue(var3.F());
   }
}
