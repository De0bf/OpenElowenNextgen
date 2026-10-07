package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventUpdateHeldItem;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.rotation.RotationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FirstPersonHandsAndItems.class)
public class MixinFirstPersonHandsAndItems {
   @Redirect(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getMainHandItem()Lnet/minecraft/world/item/ItemStack;")
   )
   public ItemStack hookMainHand(LocalPlayer var1) {
      EventUpdateHeldItem var2 = new EventUpdateHeldItem(InteractionHand.MAIN_HAND, var1.getMainHandItem());
      if (var1 == Minecraft.getInstance().player) {
         Elowen.S$Elowen().e().B(var2);
      }

      return var2.x();
   }

   @Redirect(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getOffhandItem()Lnet/minecraft/world/item/ItemStack;")
   )
   public ItemStack hookOffHand(LocalPlayer var1) {
      EventUpdateHeldItem var2 = new EventUpdateHeldItem(InteractionHand.OFF_HAND, var1.getOffhandItem());
      if (var1 == Minecraft.getInstance().player) {
         Elowen.S$Elowen().e().B(var2);
      }

      return var2.x();
   }

   @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getViewYRot(F)F"))
   private float handViewYaw(LocalPlayer var1, float var2) {
      return RotationManager.L && RotationManager.X != null ? RotationManager.X.H : var1.getViewYRot(var2);
   }

   @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getViewXRot(F)F"))
   private float handViewPitch(LocalPlayer var1, float var2) {
      return RotationManager.L && RotationManager.X != null ? RotationManager.X.E : var1.getViewXRot(var2);
   }

   @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F", ordinal = 0))
   private float handXBobLerp(float var1, float var2, float var3) {
      if (RotationManager.L && RotationManager.X != null) {
         float var4 = Mth.lerp(var1, RotationManager.c, RotationManager.f);
         return RotationManager.X.E - var4;
      } else {
         return Mth.lerp(var1, var2, var3);
      }
   }

   @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F", ordinal = 1))
   private float handYBobLerp(float var1, float var2, float var3) {
      if (RotationManager.L && RotationManager.X != null) {
         float var4 = Mth.lerp(var1, RotationManager.e, RotationManager.w);
         return RotationManager.X.H - var4;
      } else {
         return Mth.lerp(var1, var2, var3);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
