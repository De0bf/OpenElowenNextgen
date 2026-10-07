package com.elowen.utils.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.mojang.renderpearl.backend.vulkan.VulkanDevice;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import java.lang.reflect.Field;

public abstract class SkiaGpuRenderer implements AutoCloseable {
   private static final String[] y = new String[]{"No 'backend' field in ", "true", "OpenGL uses direct rendering; offscreen is only for Vulkan", "elowen.vulkanDirect", "true", "backend", "Failed to read device 'backend' field", "Failed to access VulkanDevice backend", " hierarchy", "Vulkan", "[SkiaGpuRenderer] splitFrameSubmit failed: ", "elowen.vulkanDirect", "Failed to access VulkanDevice backend for direct rendering", "Vulkan", "Direct Skija rendering requires the OpenGL backend"};
   public abstract Canvas y$Canvas();

   public abstract void t();

   public abstract void m();

   public abstract Canvas d(int var1, int var2, GpuTexture var3);

   public abstract int u$I();

   public abstract int H();

   public abstract GpuTexture e();

   public abstract GpuTextureView K();

   public abstract DirectContext L();

   public abstract Surface U$Surface();

   public void p() {
   }

   public void F() {
   }

   @Override
   public abstract void close();

   public static boolean S$Z() {
      boolean var10000 = SkijaRenderer.L();
      GpuDevice var1 = RenderSystem.getDevice();
      boolean var0 = var10000;
      GpuDevice var3 = var1;
      if (var0) {
         if (var1 == null) {
            return false;
         }

         var3 = var1;
      }

      String var2 = var3.getDeviceInfo().backendName();
      var10000 = "Vulkan".equals(var2);
      if (var0) {
         if (var10000) {
            return "true".equalsIgnoreCase(System.getProperty("elowen.vulkanDirect"));
         }

         var10000 = true;
      }

      return var10000;
   }

   public static boolean c$Z() {
      boolean var10000 = SkijaRenderer.L();
      GpuDevice var1 = RenderSystem.getDevice();
      boolean var0 = var10000;
      if (var1 == null) {
         return false;
      }

      var10000 = "Vulkan".equals(var1.getDeviceInfo().backendName());
      if (var0) {
         if (!var10000) {
            return false;
         }

         var10000 = "true".equalsIgnoreCase(System.getProperty("elowen.vulkanDirect"));
      }

      return !var0 ? var10000 : var10000;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static void Y() {
      boolean var10000 = SkijaRenderer.x();
      GpuDevice var1 = RenderSystem.getDevice();
      boolean var0 = var10000;
      GpuDevice var5 = var1;
      if (var0) {
         try {
            l(var5).createCommandEncoder().submit();
         } catch (Exception var3) {
            System.err.println("[SkiaGpuRenderer] splitFrameSubmit failed: " + var3.getMessage());
         }
      } else if (var1 != null) {
         label21:
         try {
            var5 = var1;
            break label21;
         } catch (Exception var4) {
            System.err.println("[SkiaGpuRenderer] splitFrameSubmit failed: " + var4.getMessage());
         }
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static VulkanDevice l(Object var0) {
      boolean var10000 = SkijaRenderer.x();
      Class var2 = var0.getClass();
      boolean var1 = var10000;

      while (true) {
         Class var12 = var2;
         if (!var1) {
            if (var2 == null) {
               break;
            }

            var12 = var2;
         }

         try {
            String[] var10 = y;
            Field var3 = var12.getDeclaredField("backend");
            var3.setAccessible(true);
            return (VulkanDevice)var3.get(var0);
         } catch (NoSuchFieldException var5) {
            var2 = var2.getSuperclass();
            if (!var1) {
               continue;
            }
            break;
         } catch (ReflectiveOperationException var6) {
            String[] var9 = y;
            throw new RuntimeException("Failed to read device 'backend' field", var6);
         }
      }

      String var10002 = var0.getClass().getName();
      String[] var11 = y;
      throw new RuntimeException("No 'backend' field in " + var10002 + " hierarchy");
   }

   public static SkiaGpuRenderer M() {
      boolean var5;
      label29: {
         var5 = SkijaRenderer.L();
         GpuDevice var1 = RenderSystem.getDevice();
         boolean var0 = var5;
         if (var1 != null) {
            var5 = "Vulkan".equals(var1.getDeviceInfo().backendName());
            if (!var0) {
               break label29;
            }

            if (var5) {
               try {
                  return SkiaVulkanRenderer.X(l(var1));
               } catch (Exception var4) {
                  String[] var3 = y;
                  throw new RuntimeException("Failed to access VulkanDevice backend for direct rendering", var4);
               }
            }
         }

         var5 = S$Z();
      }

      if (!var5) {
         throw new UnsupportedOperationException("Direct Skija rendering requires the OpenGL backend");
      } else {
         return new SkiaOpenglRenderer();
      }
   }

   public static SkiaGpuRenderer Y(int var0, int var1, SurfaceOrigin var2) {
      GpuDevice var3 = RenderSystem.getDevice();
      String[] var5 = y;
      if ("Vulkan".equals(var3.getDeviceInfo().backendName())) {
         try {
            return new SkiaVulkanRenderer(var0, var1, l(var3), var2);
         } catch (Exception var6) {
            var5 = y;
            throw new RuntimeException("Failed to access VulkanDevice backend", var6);
         }
      } else {
         throw new UnsupportedOperationException("OpenGL uses direct rendering; offscreen is only for Vulkan");
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
