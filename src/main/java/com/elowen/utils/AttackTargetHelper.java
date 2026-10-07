package com.elowen.utils;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRender2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;

public class AttackTargetHelper {
   private static int M;
   public static final Map w;
   private static final String[] a = new String[]{"belowHealth", "health"};
   @EventTarget(0)
   public void Q(com.elowen.events.impl.EventPacket var1) {
      String var2 = Vector2f.e();
      if (var1.M() == EventType.RECEIVE) {
         if (!var1.c$Z()) {
            if (var1.R$Packet() instanceof ClientboundPingPacket) {
               M++;
            }

            if (var1.R$Packet() instanceof ClientboundSetScorePacket var3
               && Minecraft.getInstance().level != null
               && ("belowHealth".equals(var3.objectiveName()) || "health".equals(var3.objectiveName()))
               && !var3.owner().equals(Minecraft.getInstance().player.getGameProfile().name())) {
               if (!w.containsKey(var3.owner())) {
                  AtomicInteger var6 = new AtomicInteger();
                  w.put(var3.owner(), var6);
               }

               ((AtomicInteger)w.get(var3.owner())).set(var3.score());
            }

            if (var1.R$Packet() instanceof ClientboundSetHealthPacket var5 && var5.getHealth() > 20.0F) {
               var1.c(true);
            }
         }
      }
   }

   @EventTarget
   public void c(EventRender2D var1) {
      String var10000 = Vector2f.e();
      Minecraft var3 = Minecraft.getInstance();
      String var2 = var10000;
      ClientLevel var6 = var3.level;
      if (var2 == null) {
         if (var3.level == null) {
            return;
         }

         var6 = var3.level;
      }

      for (AbstractClientPlayer var5 : var6.players()) {
         if (var5 != var3.player && w.containsKey(var5.getName().getString())) {
            var5.setHealth(Math.max(1, ((AtomicInteger)w.get(var5.getName().getString())).get()));
         }

         if (var2 != null) {
            break;
         }
      }
   }

   @EventTarget
   public void Z(com.elowen.events.impl.EventRespawn var1) {
      M = 0;
   }

   public static int h$I() {
      return M;
   }

   static {
      M = 0;
      w = new ConcurrentHashMap();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
