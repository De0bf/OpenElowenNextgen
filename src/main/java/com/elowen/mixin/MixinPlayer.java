package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventStayingOnGroundSurface;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {
   protected MixinPlayer(EntityType var1, Level var2) {
      super(var1, var2);
   }

   @Inject(method = "isStayingOnGroundSurface", at = @At("RETURN"), cancellable = true)
   private void hookIsStayingOnGroundSurface(CallbackInfoReturnable var1) {
      EventStayingOnGroundSurface var2 = new EventStayingOnGroundSurface((Boolean)var1.getReturnValue());
      Elowen.S$Elowen().e().B(var2);
      var1.setReturnValue(var2.o$Z());
   }
}
