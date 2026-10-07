package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.EventManager;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.network.PacketProcessor$ListenerAndPacket")
public abstract class MixinListenerAndPacket {
   @Unique
   private static final int MAX_BUNDLE_DEPTH = 32;
   @Unique
   private static final int MAX_BUNDLE_PACKETS = 4096;
   @Shadow
   @Final
   private Packet packet;
   @Shadow
   @Final
   private PacketListener listener;

   @Inject(
      method = "handle",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/Packet;handle(Lnet/minecraft/network/PacketListener;)V"),
      cancellable = true
   )
   private void elowen$onHandle(CallbackInfo var1) {
      EventManager var2 = Elowen.S$Elowen().e();
      if (!(this.packet instanceof ClientboundBundlePacket var3)) {
         EventPacket var7 = new EventPacket(EventType.RECEIVE, this.packet);
         var2.B(var7);
         if (var7.c$Z()) {
            var1.cancel();
         }
      } else {
         var1.cancel();
         int[] var8 = new int[]{4096};

         for (Packet var6 : var3.subPackets()) {
            if (!dispatchSubPacket(var2, var6, this.listener, 1, var8)) {
               break;
            }
         }
      }
   }

   @Unique
   private static boolean dispatchSubPacket(EventManager var0, Packet var1, PacketListener var2, int var3, int[] var4) {
      if (var1 == null) {
         return true;
      }

      if (var4[0]-- <= 0) {
         return false;
      }

      if (var1 instanceof ClientboundBundlePacket var8) {
         if (var3 >= 32) {
            return true;
         }

         for (Packet var7 : var8.subPackets()) {
            if (!dispatchSubPacket(var0, var7, var2, var3 + 1, var4)) {
               return false;
            }
         }

         return true;
      } else {
         EventPacket var5 = new EventPacket(EventType.RECEIVE, var1);
         var0.B(var5);
         if (!var5.c$Z()) {
            handleRaw(var1, var2);
         }

         return true;
      }
   }

   @Unique
   private static void handleRaw(Packet var0, PacketListener var1) {
      var0.handle(var1);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
