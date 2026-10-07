package com.elowen.utils.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.device.GpuDevice;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Image;

public final class GpuScreenCapture {
   private static final String[] a = new String[]{"[GpuScreenCapture] captureWorld failed: ", "Vulkan", "[GpuScreenCapture] getWorldImage failed: ", "Vulkan"};
   private GpuScreenCapture() {
   }

   public static void i() {
      boolean var0 = SkijaRenderer.L();

      try {
         GpuDevice var1 = RenderSystem.getDevice();
         if (var1 == null) {
            return;
         }

         if ("Vulkan".equals(var1.getDeviceInfo().backendName())) {
            VulkanScreenCapture.l$V();
         }

         OpenGLScreenCapture.o$V();
      } catch (Exception var2) {
         System.err.println("[GpuScreenCapture] captureWorld failed: " + var2.getMessage());
      }
   }

   public static Image N(DirectContext var0) {
      try {
         GpuDevice var1 = RenderSystem.getDevice();
         if (var1 != null && var0 != null) {
            String[] var2 = a;
            return "Vulkan".equals(var1.getDeviceInfo().backendName()) ? VulkanScreenCapture.Q(var0) : OpenGLScreenCapture.K(var0);
         } else {
            return null;
         }
      } catch (Exception var3) {
         System.err.println("[GpuScreenCapture] getWorldImage failed: " + var3.getMessage());
         return null;
      }
   }

   public static void H() {
      try {
         OpenGLScreenCapture.g$V();
      } catch (Exception var2) {
      }

      try {
         VulkanScreenCapture.u$V();
      } catch (Exception var1) {
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
