package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.EventManager;
import com.elowen.events.impl.EventFallFlying;
import com.elowen.events.impl.EventJump;
import com.elowen.events.impl.EventSwing;
import com.elowen.events.impl.EventSprint;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.move.FastWeb;
import com.elowen.modules.impl.render.Animations;
import com.elowen.modules.impl.render.FullBright;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
   @Unique
   private static EventManager safeEventManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.e() : null;
   }

   @Unique
   private static ModuleManager safeModuleManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.q$ModuleManager() : null;
   }

   @Redirect(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getYRot()F"))
   private float modifyJumpYaw(LivingEntity var1) {
      if (var1 != Minecraft.getInstance().player) {
         return var1.getYRot();
      }

      EventManager var2 = safeEventManager();
      if (var2 == null) {
         return var1.getYRot();
      }

      EventJump var3 = new EventJump(var1.getYRot());
      var2.B(var3);
      return var3.h$F();
   }

   @Inject(method = "swing", at = @At("HEAD"), cancellable = true)
   private void onSwing(InteractionHand var1, SwingAnimation var2, boolean var3, CallbackInfoReturnable var4) {
      if ((LivingEntity)(Object)this == Minecraft.getInstance().player) {
         EventManager var5 = safeEventManager();
         if (var5 != null) {
            EventSwing var6 = new EventSwing(var1);
            var5.B(var6);
            if (var6.c$Z()) {
               var4.setReturnValue(false);
               var4.cancel();
            }
         }
      }
   }

   @Inject(method = "getModifiedSwingDuration", at = @At("RETURN"), cancellable = true)
   private void applySwingSpeed(SwingAnimation var1, CallbackInfoReturnable var2) {
      if ((LivingEntity)(Object)this == Minecraft.getInstance().player) {
         ModuleManager var3 = safeModuleManager();
         if (var3 != null) {
            Animations var4 = (Animations)var3.A(Animations.class);
            if (var4 != null && var4.w()) {
               var2.setReturnValue(var4.F(var2.getReturnValueI()));
            }
         }
      }
   }

   @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
   private void onJumpFromGround(CallbackInfo var1) {
      if ((LivingEntity)(Object)this == Minecraft.getInstance().player) {
         ModuleManager var2 = safeModuleManager();
         if (var2 != null) {
            FastWeb var3 = (FastWeb)var2.A(FastWeb.class);
            if (var3 != null && var3.B) {
               var1.cancel();
            }
         }
      }
   }

   @Redirect(method = "updateFallFlyingMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getXRot()F"))
   private float hookModifyFallFlyingPitch(LivingEntity var1) {
      if (var1 != Minecraft.getInstance().player) {
         return var1.getXRot();
      }

      EventManager var2 = safeEventManager();
      if (var2 == null) {
         return var1.getXRot();
      }

      EventFallFlying var3 = new EventFallFlying(var1.getXRot());
      var2.B(var3);
      return var3.x();
   }

   @Inject(method = "hasEffect", at = @At("HEAD"), cancellable = true)
   private void hasEffect(Holder var1, CallbackInfoReturnable var2) {
      if ((LivingEntity)(Object)this == Minecraft.getInstance().player) {
         ModuleManager var3 = safeModuleManager();
         if (var3 != null) {
            FullBright var4 = (FullBright)var3.A(FullBright.class);
            if (var4 != null && var4.w() && var1.is(MobEffects.NIGHT_VISION)) {
               var2.setReturnValue(true);
               var2.cancel();
            }
         }
      }
   }

   @Inject(method = "travel", at = @At("HEAD"))
   private void injectSprintEveryTick(Vec3 var1, CallbackInfo var2) {
      if ((LivingEntity)(Object)this == Minecraft.getInstance().player) {
         EventManager var3 = safeEventManager();
         if (var3 != null) {
            LivingEntity var4 = (LivingEntity)(Object)this;
            boolean var5 = var4.isSprinting();
            EventSprint var6 = new EventSprint(var5);
            var3.B(var6);

            boolean var7 = switch (var6.j$I()) {
               case -1 -> false;
               case 1 -> true;
               default -> var5;
            };
            if (var7 != var5) {
               Minecraft.getInstance().options.keySprint.setDown(var7);
               var4.setSprinting(var7);
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
