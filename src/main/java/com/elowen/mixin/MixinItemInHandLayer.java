package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventUpdateHeldItem;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemInHandLayer.class)
public class MixinItemInHandLayer {
   @ModifyArg(
      method = "submit",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;submitArmWithItem(Lnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V",
         ordinal = 0
      ),
      index = 2
   )
   private ItemStack modifyMainHandItem(ItemStack var1) {
      EventUpdateHeldItem var2 = new EventUpdateHeldItem(InteractionHand.MAIN_HAND, var1);
      Elowen.S$Elowen().e().B(var2);
      return var2.x();
   }

   @ModifyArg(
      method = "submit",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;submitArmWithItem(Lnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V",
         ordinal = 1
      ),
      index = 2
   )
   private ItemStack modifyOffhandItem(ItemStack var1) {
      EventUpdateHeldItem var2 = new EventUpdateHeldItem(InteractionHand.OFF_HAND, var1);
      Elowen.S$Elowen().e().B(var2);
      return var2.x();
   }
}
