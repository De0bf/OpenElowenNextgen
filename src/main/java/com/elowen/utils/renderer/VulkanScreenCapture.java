package com.elowen.utils.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.backend.vulkan.VulkanConst;
import com.mojang.renderpearl.backend.vulkan.VulkanGpuTexture;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

public final class VulkanScreenCapture {
   private static GpuTexture e;
   private static int r;
   private static int o;
   private static Image M;
   private static DirectContext N;
   private static int E;
   private static int g;
   private static final String[] a = new String[]{"[VulkanScreenCapture] wrap capture texture failed: ", "elowen_world_capture_vk"};
   private VulkanScreenCapture() {
   }

   public static void l$V() {
      int var10000 = ((SkijaRenderer.L()) ? 1 : 0);
      Minecraft var1 = Minecraft.getInstance();
      boolean var0 = (boolean)((var10000) != 0);
      Minecraft var8 = var1;
      if (var0) {
         if (var1 == null) {
            return;
         }

         var8 = var1;
      }

      GameRenderer var9 = var8.gameRenderer;
      if (var0) {
         if (var8.gameRenderer == null) {
            return;
         }

         var9 = var1.gameRenderer;
      }

      RenderTarget var2 = var9.mainRenderTarget();
      RenderTarget var10 = var2;
      if (var0) {
         if (var2 == null) {
            return;
         }

         var10 = var2;
      }

      GpuTexture var3 = var10.getColorTexture();
      if (var3 != null) {
         int var4 = var2.width;
         int var5 = var2.height;
         var10000 = var4;
         if (var0) {
            if (var4 <= 0) {
               return;
            }

            var10000 = var5;
         }

         if (var10000 > 0) {
            GpuDevice var6 = RenderSystem.getDevice();
            GpuDevice var12 = var6;
            if (var0) {
               if (var6 == null) {
                  return;
               }

               F();
               e = var6.createTexture(VulkanScreenCapture::deobfLambda$captureWorld$0, 15, var3.getFormat(), var4, var5, 1, 1);
               r = var4;
               o = var5;
               var12 = var6;
            }

            CommandEncoder var7 = var12.createCommandEncoder();
            var7.copyTextureToTexture(var3, e, 0, 0, 0, 0, 0, var4, var5);
            var7.submit();
            E++;
         }
      }
   }

   public static Image Q(DirectContext var0) {
      boolean var1 = SkijaRenderer.L();
      if (e == null || var0 == null) {
         return null;
      }

      if (E == 0) {
         return null;
      }

      if (M == null || N != var0 || g != E) {
         if (M != null) {
            M.close();
         }

         M = null;
         VulkanGpuTexture var2 = (VulkanGpuTexture)e;
         int var3 = VulkanConst.toVk(e.getFormat());
         int var4 = VulkanConst.textureUsageToVk(e.usage(), e.getFormat());
         BackendRenderTarget var5 = BackendRenderTarget.makeVulkan(r, o, var2.vkImage(), 0, 1, var3, var4, 1, 1);
         Surface var6 = null;

         try {
            var6 = Surface.wrapBackendRenderTarget(var0, var5, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, ColorSpace.getSRGB());
            M = var6.makeImageSnapshot();
         } catch (RuntimeException var11) {
            System.err.println("[VulkanScreenCapture] wrap capture texture failed: " + var11.getMessage());
            M = null;
         } finally {
            if (var6 != null) {
               var6.close();
            }

            var5.close();
         }

         N = var0;
         g = E;
      }

      return M;
   }

   public static void u$V() {
      F();
      N = null;
   }

   private static void F() {
      boolean var0;
      label27: {
         var0 = SkijaRenderer.x();
         Image var10000 = M;
         if (!var0) {
            if (M == null) {
               break label27;
            }

            var10000 = M;
         }

         var10000.close();
         M = null;
      }

      label21: {
         GpuTexture var1 = e;
         if (!var0) {
            if (e == null) {
               break label21;
            }

            var1 = e;
         }

         var1.close();
         e = null;
      }

      r = -1;
      o = -1;
      E = 0;
      g = -1;
   }

   private static String deobfLambda$captureWorld$0() {
      return "elowen_world_capture_vk";
   }

   static {
      r = -1;
      o = -1;
      g = -1;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
