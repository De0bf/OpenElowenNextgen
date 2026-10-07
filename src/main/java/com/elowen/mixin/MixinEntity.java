package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventStrafe;
import com.elowen.events.impl.EventStuckInBlock;
import com.elowen.events.impl.EventRayTrace;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.BlinkingPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {
   @Shadow
   protected Vec3 stuckSpeedMultiplier;

   @Shadow
   public abstract float getViewXRot(float var1);

   @Shadow
   public abstract float getViewYRot(float var1);

   @Shadow
   protected static Vec3 calculateViewVector(float var0, float var1) {
      throw new AssertionError();
   }

   @Overwrite
   public final Vec3 getViewVector(float var1) {
      float var2 = this.getViewXRot(var1);
      float var3 = this.getViewYRot(var1);
      Entity var4 = (Entity)(Object)this;
      if (var4 == Minecraft.getInstance().player) {
         EventRayTrace var5 = new EventRayTrace(var4, var3, var2);
         Elowen.S$Elowen().e().B(var5);
         var3 = var5.b;
         var2 = var5.i;
      }

      return calculateViewVector(var2, var3);
   }

   @ModifyArg(
      method = "moveRelative",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;",
         ordinal = 0
      ),
      index = 2
   )
   private float modifyYaw(float var1) {
      Entity var2 = (Entity)(Object)this;
      if (var2 != Minecraft.getInstance().player) {
         return var1;
      }

      EventStrafe var3 = new EventStrafe(var1);
      Elowen.S$Elowen().e().B(var3);
      return var3.q$F();
   }

   @Inject(method = "makeStuckInBlock", at = @At("RETURN"))
   private void makeStuckInBlock(BlockState var1, Vec3 var2, CallbackInfo var3) {
      Entity var4 = (Entity)(Object)this;
      if (Minecraft.getInstance().player == var4) {
         EventStuckInBlock var5 = new EventStuckInBlock(var1, var2);
         Elowen.S$Elowen().e().B(var5);
         if (var5.c$Z()) {
            this.stuckSpeedMultiplier = Vec3.ZERO;
            return;
         }

         this.stuckSpeedMultiplier = var5.O();
      }
   }

   @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   public void push(Entity var1, CallbackInfo var2) {
      if (var1 instanceof BlinkingPlayer) {
         var2.cancel();
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
