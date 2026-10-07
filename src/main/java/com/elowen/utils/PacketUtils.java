package com.elowen.utils;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketUtils {
   private static final ThreadLocal k;
   private static final TimeHelper b;
   private static final com.elowen.ui.notification.Notification P;
   private static long d;
   public static final Logger u;
   private static final String[] a = new String[]{"Server lagging. Aura disabled! (", "ms)", "Server lagging!", "PacketUtil", "Server currently online! (", "ms)"};
   public static void d(Packet var0) {
      String var1 = Vector2f.e();
      if (var0 != null) {
         Set var2 = (Set)k.get();
         if (var2 == null) {
            var2 = Collections.newSetFromMap(new IdentityHashMap());
            k.set(var2);
         }

         var2.add(var0);
      }
   }

   private static void W(Packet var0) {
      String var1 = Vector2f.e();
      if (var0 != null) {
         Set var2 = (Set)k.get();
         if (var2 != null) {
            var2.remove(var0);
            if (var2.isEmpty()) {
               k.remove();
            }
         }
      }
   }

   public static boolean z$Z() {
      return b.e(500.0);
   }

   public static boolean g(Packet var0, PacketListener var1) {
      String var2 = Vector2f.e();
      if (var0 != null) {
         Set var3 = (Set)k.get();
         if (var3 != null && var3.remove(var0)) {
            if (var3.isEmpty()) {
               k.remove();
            }

            return false;
         }
      }

      return var1 != null && var1.protocol() == ConnectionProtocol.PLAY;
   }

   public static void c(Packet var0) {
      String var1 = Vector2f.e();
      if (var0 != null) {
         ClientPacketListener var2 = Minecraft.getInstance().getConnection();
         if (var2 != null) {
            d(var0);

            try {
               var2.send(var0);
            } finally {
               W(var0);
            }
         }
      }
   }

   @EventTarget
   public void i(com.elowen.events.impl.EventMotion var1) {
      String var2 = Vector2f.e();
      if (var1.Q() == EventType.PRE) {
         if (z$Z()) {
            Elowen.S$Elowen().Q().m(P);
            P.I(System.currentTimeMillis());
            P.j(com.elowen.ui.notification.NotificationLevel.WARNING);
            d = Math.round(b.d$D());
            P.X("Server lagging. Aura disabled! (" + d + "ms)");
         }

         P.j(com.elowen.ui.notification.NotificationLevel.SUCCESS);
         P.X("Server currently online! (" + d + "ms)");
      }
   }

   @EventTarget(4)
   public void D(com.elowen.events.impl.EventGlobalPacket var1) {
      String var2 = Vector2f.e();
      if (var1.g$Packet() instanceof ClientboundPingPacket
         || var1.g$Packet() instanceof ClientboundMoveEntityPacket
         || var1.g$Packet() instanceof ClientboundSetTimePacket
         || var1.g$Packet() instanceof ClientboundSetPlayerTeamPacket) {
         b.p();
      }

      if (var1.f$f() == EventType.SEND) {
         if (!var1.c$Z()) {
            Packet var3 = var1.g$Packet();
            com.elowen.events.impl.EventPacket var4 = new com.elowen.events.impl.EventPacket(var1.f$f(), var3);
            Elowen.S$Elowen().e().B(var4);
            if (var4.c$Z()) {
               var1.c(true);
            }

            var1.g(var4.R$Packet());
         }
      }
   }

   static {
      k = new ThreadLocal();
      b = new TimeHelper();
      String[] var9 = a;
      P = new com.elowen.ui.notification.Notification(com.elowen.ui.notification.NotificationLevel.WARNING, "Server lagging!", 2000L);
      d = 0L;
      u = LogManager.getLogger("PacketUtil");
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
