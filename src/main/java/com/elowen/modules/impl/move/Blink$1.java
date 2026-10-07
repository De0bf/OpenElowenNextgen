package com.elowen.modules.impl.move;

import java.util.HashSet;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.network.protocol.ping.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;

class Blink$1 extends HashSet {
   Blink$1() {
      this.add(ClientIntentionPacket.class);
      this.add(ServerboundStatusRequestPacket.class);
      this.add(ServerboundPingRequestPacket.class);
      this.add(ServerboundHelloPacket.class);
      this.add(ServerboundKeyPacket.class);
   }
}
