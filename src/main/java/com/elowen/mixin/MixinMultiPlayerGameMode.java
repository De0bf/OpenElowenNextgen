package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.EventManager;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUseItem;
import com.elowen.events.impl.EventPositionItem;
import com.elowen.events.impl.EventDestroyBlock;
import com.elowen.events.impl.EventAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode {
   @Shadow
   @Final
   private Minecraft minecraft;

   @Unique
   private static EventManager safeEventManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.e() : null;
   }

   @Redirect(
      method = "startPrediction",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V")
   )
   private void onSendPredictionPacket(ClientPacketListener var1, Packet var2) {
      EventManager var3 = safeEventManager();
      if (var3 != null) {
         EventPositionItem var4 = new EventPositionItem(var2);
         var3.B(var4);
         if (!var4.c$Z()) {
            var1.send(var4.e());
         }
      } else {
         var1.send(var2);
      }
   }

   @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
   private void onUseItem(Player var1, InteractionHand var2, CallbackInfoReturnable var3) {
      EventManager var4 = safeEventManager();
      if (var4 != null) {
         EventUseItem var5 = new EventUseItem(var2, var1.getItemInHand(var2));
         var4.B(var5);
         if (var5.c$Z()) {
            var3.setReturnValue(InteractionResult.PASS);
         }
      }
   }

   @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
   private void onUseItemOn(LocalPlayer var1, InteractionHand var2, BlockHitResult var3, CallbackInfoReturnable var4) {
      EventManager var5 = safeEventManager();
      if (var5 != null) {
         EventUseItem var6 = new EventUseItem(var2, var1.getItemInHand(var2), true);
         var5.B(var6);
         if (var6.c$Z()) {
            var4.setReturnValue(InteractionResult.PASS);
         }
      }
   }

   @Inject(method = "startDestroyBlock", at = @At("HEAD"))
   private void onStartDestroyBlock(BlockPos var1, Direction var2, CallbackInfoReturnable var3) {
      EventManager var4 = safeEventManager();
      if (var4 != null) {
         var4.B(new EventDestroyBlock(var1, var2));
      }
   }

   @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
   private void onAttack(Player var1, Entity var2, CallbackInfo var3) {
      EventManager var4 = safeEventManager();
      if (var4 != null) {
         EventAttack var5 = new EventAttack(var2, EventType.PRE);
         var4.B(var5);
         if (var5.c$Z()) {
            var3.cancel();
         }
      }
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
