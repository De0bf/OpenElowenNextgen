package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventRenderHand;
import com.elowen.modules.impl.render.Animations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity.SwingDescription;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class MixinFirstPersonHandsAndItemsRenderer {
   @Unique
   private boolean elowen$blockPose;
   @Unique
   private float elowen$attack;

   @Unique
   private static Animations elowen$animations() {
      Elowen var0 = Elowen.S$Elowen();
      if (var0 == null) {
         return null;
      }

      try {
         return (Animations)var0.q$ModuleManager().A(Animations.class);
      } catch (Exception var2) {
         return null;
      }
   }

   @Inject(method = "submitHandsWithItems", at = @At("HEAD"))
   private void applyPositionOffset(
      float var1, PoseStack var2, SubmitNodeCollector var3, PlayerRenderState var4, FirstPersonHandsAndItemsRenderState var5, CallbackInfo var6
   ) {
      Animations var7 = elowen$animations();
      if (var7 != null && var7.w()) {
         var2.translate(var7.y.o$F(), var7.o.o$F(), var7.r.o$F());
      }
   }

   @Inject(method = "submitArmWithItem", at = @At("HEAD"), cancellable = true)
   private void onRenderArmWithItem(
      PlayerRenderState var1,
      FirstPersonHandsAndItemsRenderState var2,
      float var3,
      float var4,
      InteractionHand var5,
      float var6,
      ItemStack var7,
      float var8,
      PoseStack var9,
      SubmitNodeCollector var10,
      int var11,
      CallbackInfo var12
   ) {
      this.elowen$blockPose = false;
      this.elowen$attack = var6;
      EventRenderHand var13 = new EventRenderHand(var5, var3, 0.0F, var6, var7, var9, var10, var11);
      Elowen.S$Elowen().e().B(var13);
      if (var13.L()) {
         var12.cancel();
      } else {
         Animations var14 = elowen$animations();
         this.elowen$blockPose = var14 != null && var14.v(var5);
      }
   }

   @Inject(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = Shift.AFTER))
   private void applyHandGap(
      PlayerRenderState var1,
      FirstPersonHandsAndItemsRenderState var2,
      float var3,
      float var4,
      InteractionHand var5,
      float var6,
      ItemStack var7,
      float var8,
      PoseStack var9,
      SubmitNodeCollector var10,
      int var11,
      CallbackInfo var12
   ) {
      Animations var13 = elowen$animations();
      if (var13 != null && var13.w()) {
         AvatarRenderState var14 = var1.avatarRenderState;
         if (var14 != null) {
            var13.N(var9, var14.mainArm, var5);
         }
      }
   }

   @Redirect(
      method = "submitArmWithItem",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V",
         ordinal = 4
      )
   )
   private void applyBlockPoseOrVanillaTransform(FirstPersonHandsAndItemsRenderer var1, PoseStack var2, HumanoidArm var3, float var4) {
      int var5 = var3 == HumanoidArm.RIGHT ? 1 : -1;
      if (this.elowen$blockPose) {
         Animations var6 = elowen$animations();
         if (var6 != null) {
            var6.I(var2, var4, this.elowen$attack, var5);
            return;
         }
      }

      var2.translate(var5 * 0.56F, -0.52F + var4 * -0.6F, -0.72F);
   }

   @Redirect(
      method = "submitArmWithItem",
      at = @At(
         value = "FIELD",
         opcode = 180,
         target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;currentSwing:Lnet/minecraft/world/entity/LivingEntity$SwingDescription;"
      )
   )
   private SwingDescription hideSwingWhileBlocking(AvatarRenderState var1) {
      return this.elowen$blockPose ? null : var1.currentSwing;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
