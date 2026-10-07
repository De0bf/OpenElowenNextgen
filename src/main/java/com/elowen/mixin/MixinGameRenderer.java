package com.elowen.mixin;

import com.elowen.Elowen;
import com.elowen.events.impl.EventRenderAfterGUI;
import com.elowen.events.impl.EventRender;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventRender3D;
import com.elowen.events.impl.EventRenderAfterWorld;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.render.FullBright;
import com.elowen.modules.impl.render.NoHurtCam;
import com.elowen.modules.impl.render.OldHurtCam;
import com.elowen.modules.impl.render.PostProcess;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.GpuScreenCapture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   @Final
   private RenderBuffers renderBuffers;
   @Shadow
   @Final
   private GameRenderState gameRenderState;

   @Inject(method = "renderLevel", at = @At("HEAD"))
   private void onRenderLevelHead(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         float var2 = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
         PoseStack var3 = new PoseStack();
         Elowen.S$Elowen().e().B(new EventRender3D(var2, var3));
      }
   }

   @Inject(method = "renderLevel", at = @At("TAIL"))
   private void onRenderLevelTail(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventRenderAfterWorld());
      }
   }

   @Inject(method = "nightVisionScale", at = @At("HEAD"), cancellable = true)
   private static void nightVisionScale(LivingEntity var0, float var1, CallbackInfoReturnable var2) {
      Elowen var3 = Elowen.S$Elowen();
      if (var3 != null) {
         ModuleManager var4 = var3.q$ModuleManager();
         if (var4 != null) {
            FullBright var5 = (FullBright)var4.A(FullBright.class);
            if (var5 != null && var5.w() && var5.c != null) {
               var2.setReturnValue(var5.c.o$F());
               var2.cancel();
            }
         }
      }
   }

   @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render()V"))
   private void injectRender2DEvent(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         SkiaRenderManager.X$V();

         try {
            Elowen.S$Elowen().e().B(new EventRender());
            SkiaRenderManager.N();
            if (Elowen.S$Elowen().q$ModuleManager() != null) {
               PostProcess var2 = (PostProcess)Elowen.S$Elowen().q$ModuleManager().A(PostProcess.class);
               if (var2 != null && var2.w()) {
                  GpuScreenCapture.i();
               }
            }

            double var10 = this.minecraft.mouseHandler.xpos() * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
            double var4 = this.minecraft.mouseHandler.ypos() * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
            GuiGraphicsExtractor var6 = new GuiGraphicsExtractor(this.minecraft, this.gameRenderState.guiRenderState, (int)var10, (int)var4);
            Elowen.S$Elowen().e().B(new EventRender2D(var6.pose(), var6));
         } finally {
            SkiaRenderManager.W$V();
         }
      }
   }

   @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;endFrame()V", shift = Shift.AFTER))
   private void injectRenderAfterGUI(CallbackInfo var1) {
      if (Elowen.S$Elowen() != null && Elowen.S$Elowen().e() != null) {
         Elowen.S$Elowen().e().B(new EventRenderAfterGUI());
      }
   }

   @Inject(method = "bobHurt", at = @At("HEAD"), cancellable = true)
   private void onBobHurt(CameraRenderState var1, PoseStack var2, CallbackInfo var3) {
      Elowen var4 = Elowen.S$Elowen();
      if (var4 != null) {
         ModuleManager var5 = var4.q$ModuleManager();
         if (var5 != null) {
            NoHurtCam var6 = (NoHurtCam)var5.A(NoHurtCam.class);
            if (var6 != null && var6.w()) {
               var3.cancel();
            } else {
               OldHurtCam var7 = (OldHurtCam)var5.A(OldHurtCam.class);
               if (var7 != null && var7.w()) {
                  var1.entityRenderState.hurtDir = 0.0F;
               }
            }
         }
      }
   }

   @Redirect(
      method = "renderLevel",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;last()Lcom/mojang/blaze3d/vertex/PoseStack$Pose;")
   )
   private Pose captureWorldBob(PoseStack var1) {
      Pose var2 = var1.last();
      ViewBob.b(var2.pose());
      return var2;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
