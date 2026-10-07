package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.api.EventManager;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventSlowdown;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventPlayerTick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.render.AntiNausea;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.PosRot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer {
   @Shadow
   @Final
   public ClientPacketListener connection;
   @Shadow
   @Final
   protected Minecraft minecraft;
   @Shadow
   private double xLast;
   @Shadow
   private double yLast;
   @Shadow
   private double zLast;
   @Shadow
   private float yRotLast;
   @Shadow
   private float xRotLast;
   @Shadow
   private int positionReminder;
   @Shadow
   private boolean lastOnGround;
   @Shadow
   private boolean lastHorizontalCollision;
   @Shadow
   private boolean autoJumpEnabled;
   @Shadow
   public float portalEffectIntensity;
   @Shadow
   public float oPortalEffectIntensity;

   @Shadow
   protected abstract boolean isControlledCamera();

   @Shadow
   protected abstract void sendIsSprintingIfNeeded();

   public MixinLocalPlayer(ClientLevel var1, GameProfile var2) {
      super(var1, var2);
   }

   @Unique
   private static EventManager safeEventManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.e() : null;
   }

   @Unique
   private static ModuleManager safeModuleManager() {
      Elowen var0 = Elowen.S$Elowen();
      return var0 != null ? var0.q$ModuleManager() : null;
   }

   @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;tick()V", shift = Shift.BEFORE))
   private void injectUpdateEventPre(CallbackInfo var1) {
      EventManager var2 = safeEventManager();
      if (var2 != null) {
         var2.B(new EventUpdate(EventType.PRE));
      }
   }

   @Inject(method = "tick", at = @At("RETURN"))
   private void injectUpdateEventPost(CallbackInfo var1) {
      EventManager var2 = safeEventManager();
      if (var2 != null) {
         var2.B(new EventUpdate(EventType.POST));
      }
   }

   @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
   private void onRespawnCheck(CallbackInfo var1) {
      EventManager var2 = safeEventManager();
      if (var2 != null) {
         if (this.tickCount <= 1) {
            var2.B(new EventRespawn());
         }
      }
   }

   @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
   private void onPlayerTick(CallbackInfo var1) {
      EventManager var2 = safeEventManager();
      if (var2 != null) {
         EventPlayerTick var3 = new EventPlayerTick();
         var2.B(var3);
         if (var3.c$Z()) {
            var1.cancel();
         }
      }
   }

   @Inject(method = "sendPosition", at = @At("HEAD"), cancellable = true)
   private void onSendPosition(CallbackInfo var1) {
      var1.cancel();
      this.sendIsSprintingIfNeeded();
      if (this.isControlledCamera()) {
         EventManager var2 = safeEventManager();
         if (var2 == null) {
            this.sendPositionOriginal();
         } else {
            EventMotion var3 = new EventMotion(EventType.PRE, this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot(), this.onGround());
            var2.B(var3);
            if (var3.c$Z()) {
               var2.B(new EventMotion(EventType.POST, var3.k(), var3.g$F()));
            } else {
               this.sendPositionPacket(var3.y$D(), var3.D(), var3.G$D(), var3.k(), var3.g$F(), var3.r());
               var2.B(new EventMotion(EventType.POST, var3.k(), var3.g$F()));
            }
         }
      }
   }

   @Unique
   private void sendPositionOriginal() {
      double var1 = this.getX();
      double var3 = this.getY();
      double var5 = this.getZ();
      float var7 = this.getYRot();
      float var8 = this.getXRot();
      boolean var9 = this.onGround();
      this.sendPositionPacket(var1, var3, var5, var7, var8, var9);
   }

   @Unique
   private void sendPositionPacket(double var1, double var3, double var5, float var7, float var8, boolean var9) {
      double var10 = var1 - this.xLast;
      double var12 = var3 - this.yLast;
      double var14 = var5 - this.zLast;
      double var16 = var7 - this.yRotLast;
      double var18 = var8 - this.xRotLast;
      this.positionReminder++;
      boolean var20 = Mth.lengthSquared(var10, var12, var14) > Mth.square(2.0E-4) || this.positionReminder >= 20;
      boolean var21 = var16 != 0.0 || var18 != 0.0;
      if (var20 && var21) {
         this.connection.send(new PosRot(new Vec3(var1, var3, var5), var7, var8, var9, this.horizontalCollision));
      } else if (var20) {
         this.connection.send(new Pos(new Vec3(var1, var3, var5), var9, this.horizontalCollision));
      } else if (var21) {
         this.connection.send(new Rot(var7, var8, var9, this.horizontalCollision));
      } else if (this.lastOnGround != var9 || this.lastHorizontalCollision != this.horizontalCollision) {
         this.connection.send(new StatusOnly(var9, this.horizontalCollision));
      }

      if (var20) {
         this.xLast = var1;
         this.yLast = var3;
         this.zLast = var5;
         this.positionReminder = 0;
      }

      if (var21) {
         this.yRotLast = var7;
         this.xRotLast = var8;
      }

      this.lastOnGround = var9;
      this.lastHorizontalCollision = this.horizontalCollision;
      this.autoJumpEnabled = (Boolean)this.minecraft.options.autoJump().get();
   }

   @Inject(method = "sendChanges", at = @At("HEAD"))
   private void onVehicleEventMotion(CallbackInfo var1) {
      EventManager var2 = safeEventManager();
      if (var2 != null && this.isPassenger()) {
         EventMotion var3 = new EventMotion(EventType.PRE, this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot(), this.onGround());
         var2.B(var3);
         var2.B(new EventMotion(EventType.POST, var3.k(), var3.g$F()));
      }
   }

   @Redirect(method = "modifyInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
   public boolean onSlowdown(LocalPlayer var1) {
      EventSlowdown var2 = new EventSlowdown(var1.isUsingItem());
      EventManager var3 = safeEventManager();
      if (var3 != null) {
         var3.B(var2);
      }

      return var2.B$Z();
   }

   @Inject(method = "handlePortalTransitionEffect", at = @At("HEAD"), cancellable = true)
   private void onPortalEffect(boolean var1, CallbackInfo var2) {
      ModuleManager var3 = safeModuleManager();
      if (var3 != null) {
         AntiNausea var4 = (AntiNausea)var3.A(AntiNausea.class);
         if (var4 != null && var4.w()) {
            this.oPortalEffectIntensity = this.portalEffectIntensity;
            if (var1) {
               this.portalEffectIntensity = 0.0F;
            } else if (this.portalEffectIntensity > 0.0F) {
               this.portalEffectIntensity = Math.max(0.0F, this.portalEffectIntensity - 0.05F);
            }

            var2.cancel();
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
