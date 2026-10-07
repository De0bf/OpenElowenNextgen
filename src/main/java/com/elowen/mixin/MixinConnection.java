package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventGlobalPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.PacketUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class MixinConnection extends SimpleChannelInboundHandler {
   @Shadow
   @Final
   private static Logger LOGGER;
   @Shadow
   @Nullable
   private PacketListener packetListener;

   @Shadow
   private static void genericsFtw(Packet var0, PacketListener var1) {
   }

   @Shadow
   private void sendPacket(Packet var1, @Nullable ChannelFutureListener var2, boolean var3) {
      throw new AssertionError();
   }

   @Redirect(
      method = "channelRead0",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/Connection;genericsFtw(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;)V"
      )
   )
   private static void onGenericsFtw(Packet var0, PacketListener var1) {
      EventGlobalPacket var2 = new EventGlobalPacket(EventType.RECEIVE, var0);
      Elowen.S$Elowen().e().B(var2);
      if (!var2.c$Z()) {
         genericsFtw(var2.g$Packet(), var1);
      }
   }

   @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;Z)V", at = @At("HEAD"), cancellable = true)
   private void onSend(Packet var1, @Nullable ChannelFutureListener var2, boolean var3, CallbackInfo var4) {
      if (PacketUtils.g(var1, this.packetListener)) {
         EventGlobalPacket var5 = new EventGlobalPacket(EventType.SEND, var1);
         Elowen.S$Elowen().e().B(var5);
         if (var5.c$Z()) {
            var4.cancel();
         } else {
            if (var5.g$Packet() != var1) {
               var4.cancel();
               PacketUtils.d(var5.g$Packet());
               this.sendPacket(var5.g$Packet(), var2, var3);
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
