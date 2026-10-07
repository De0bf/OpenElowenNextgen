package com.elowen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPunchPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerUtils {
   private static final Minecraft B = Minecraft.getInstance();

   public static void s(InteractionHand var0) {
      String var1 = Vector2f.e();
      if (B.player != null) {
         B.player.swing(var0, B.player.getItemInHand(var0).getAttackAnimation(), false);
         x();
      }
   }

   public static void x() {
      String var0 = Vector2f.e();
      if (B.getConnection() != null) {
         B.getConnection().send(ServerboundPunchPacket.INSTANCE);
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static int S$I() {
      LocalPlayer var1 = B.player;
      if (var1 == null) {
         return -1;
      } else {
         try {
            return var1.getId();
         } catch (IllegalStateException var3) {
            return -1;
         }
      }
   }

   public static boolean r() {
      String var0 = Vector2f.e();
      return B.options.keyUp.isDown() || B.options.keyDown.isDown() || B.options.keyLeft.isDown() || B.options.keyRight.isDown();
   }

   public static int V() {
      String var0 = Vector2f.e();
      return B.player.hasEffect(MobEffects.SPEED) ? B.player.getEffect(MobEffects.SPEED).getAmplifier() + 1 : 0;
   }

   public static Vec3 s(Vector2f var0) {
      float var1 = (float)Math.cos(-var0.S$F() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var2 = (float)Math.sin(-var0.S$F() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var3 = (float)(-Math.cos(-var0.p() * (float) (Math.PI / 180.0)));
      float var4 = (float)Math.sin(-var0.p() * (float) (Math.PI / 180.0));
      return new Vec3(var2 * var3, var4, var1 * var3);
   }

   public static HitResult m(double var0, float var2, float var3) {
      String var4 = Vector2f.e();
      if (B.player != null && B.level != null) {
         Vec3 var5 = B.player.getEyePosition(1.0F);
         Vec3 var6 = s(new Vector2f(var2, var3));
         Vec3 var7 = var5.add(var6.x * var0, var6.y * var0, var6.z * var0);
         return B.level.clip(new ClipContext(var5, var7, Block.OUTLINE, Fluid.NONE, B.player));
      } else {
         return null;
      }
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
