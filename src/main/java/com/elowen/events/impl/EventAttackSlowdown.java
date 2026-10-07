package com.elowen.events.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;

public class EventAttackSlowdown extends com.elowen.events.api.events.callables.EventCancellable {
   public static void Z() {
      Minecraft var1 = Minecraft.getInstance();
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      LocalPlayer var2 = var1.player;
      com.elowen.values.HasValue[] var0 = var10000;
      LocalPlayer var3 = var2;
      if (var0 == null) {
         if (var2 == null) {
            return;
         }

         var3 = var2;
      }

      if (var3.isSprinting()) {
         ClientPacketListener var4 = var1.getConnection();
         if (var0 == null) {
            if (var4 == null) {
               return;
            }

            var4 = var1.getConnection();
         }

         var4.send(new ServerboundPlayerCommandPacket(var2, Action.STOP_SPRINTING));
      }
   }

   public static void M() {
      com.elowen.values.HasValue[] var10000 = EventPacket.C();
      Minecraft var1 = Minecraft.getInstance();
      LocalPlayer var2 = var1.player;
      com.elowen.values.HasValue[] var0 = var10000;
      LocalPlayer var3 = var2;
      if (var0 == null) {
         if (var2 == null) {
            return;
         }

         var3 = var2;
      }

      if (var3.isSprinting()) {
         ClientPacketListener var4 = var1.getConnection();
         if (var0 == null) {
            if (var4 == null) {
               return;
            }

            var4 = var1.getConnection();
         }

         var4.send(new ServerboundPlayerCommandPacket(var2, Action.START_SPRINTING));
      }
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
