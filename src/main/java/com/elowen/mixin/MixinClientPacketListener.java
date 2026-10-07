package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventServerSetPosition;
import com.elowen.modules.impl.render.NameProtect;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
   @Redirect(
      method = "handleMovePlayer",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V", ordinal = 0)
   )
   public void onSendPacket(Connection var1, Packet var2) {
      EventServerSetPosition var3 = new EventServerSetPosition(var2);
      Elowen.S$Elowen().e().B(var3);
      var1.send(var3.e());
   }

   @Redirect(
      method = "handleSystemChat",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket;content()Lnet/minecraft/network/chat/Component;")
   )
   public Component onSystemChat(ClientboundSystemChatPacket var1) {
      return NameProtect.s(var1.content());
   }

   @Redirect(
      method = "handleDisguisedChat",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket;message()Lnet/minecraft/network/chat/Component;"
      )
   )
   public Component onDisguisedChat(ClientboundDisguisedChatPacket var1) {
      return NameProtect.s(var1.message());
   }
}
