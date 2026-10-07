package com.elowen.utils;

import com.elowen.mixin.accessors.ClientLevelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.network.protocol.Packet;

public class NetworkUtils {
   private static final Minecraft H = Minecraft.getInstance();

   public static void h(PredictiveAction var0) {
      String var1 = Vector2f.e();
      if (H.getConnection() != null && H.level != null) {
         BlockStatePredictionHandler var2 = ((ClientLevelAccessor)H.level).invokeGetBlockStatePredictionHandler().startPredicting();

         try {
            int var3 = var2.currentSequence();
            H.getConnection().send(var0.predict(var3));
         } catch (Throwable var10) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }
            }

            throw var10;
         } finally {
            if (var2 != null) {
               var2.close();
            }
         }
      }
   }

   public static void O(PredictiveAction var0) {
      String var1 = Vector2f.e();
      if (H.getConnection() != null && H.level != null) {
         BlockStatePredictionHandler var2 = ((ClientLevelAccessor)H.level).invokeGetBlockStatePredictionHandler().startPredicting();

         try {
            int var3 = var2.currentSequence();
            Packet var4 = var0.predict(var3);
            PacketUtils.c(var4);
         } catch (Throwable var10) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }
            }

            throw var10;
         } finally {
            if (var2 != null) {
               var2.close();
            }
         }
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
