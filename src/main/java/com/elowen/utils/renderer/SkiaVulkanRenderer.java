package com.elowen.utils.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.commands.RenderPassDescriptor;
import com.mojang.renderpearl.api.commands.RenderPass.RenderArea;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.mojang.renderpearl.backend.vulkan.VulkanCommandEncoder;
import com.mojang.renderpearl.backend.vulkan.VulkanConst;
import com.mojang.renderpearl.backend.vulkan.VulkanDevice;
import com.mojang.renderpearl.backend.vulkan.VulkanGpuTexture;
import com.mojang.renderpearl.backend.vulkan.VulkanUtils;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSynchronization2;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK12;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDependencyInfo;
import org.lwjgl.vulkan.VkImageMemoryBarrier2;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkPhysicalDevice;

public class SkiaVulkanRenderer extends SkiaGpuRenderer {
   private final VulkanDevice D;
   private final boolean s;
   private DirectContext C;
   private GpuTexture U;
   private GpuTextureView t;
   private Surface H;
   private long O = 0L;
   private int M = -1;
   private int S = -1;
   private static boolean K;
   private static boolean j;
   private static final String[] a = new String[]{"InSampler", "Failed to create Skia Vulkan context", "Failed to end restore-layout command buffer", "skia_vk", "vkGetDeviceProcAddr", "Not a direct Vulkan renderer", "[SkiaVulkan] MODE=DIRECT: wrapping MAIN TARGET vkImage=", "skija_vk_blit", ", height=", "[SkiaVulkan] MODE=OFFSCREEN: private renderTexture created (width=", "[SkiaVulkan] Texture pre-clear failed, will rely on Skia clear: ", "[SkiaVulkan] restore main target layout failed: ", "Unsupported main target format: ", "[SkiaVulkan] restoreMainTargetLayout: recorded layout transition COLOR_ATTACHMENT_OPTIMAL -> GENERAL", "No physical device", " (no private texture, no blit)", "[SkiaVulkan] createDirect: direct Vulkan renderer created", "vkGetInstanceProcAddr"};
   public SkiaVulkanRenderer(int var1, int var2, VulkanDevice var3) {
      this(var1, var2, var3, SurfaceOrigin.TOP_LEFT);
   }

   public SkiaVulkanRenderer(int var1, int var2, VulkanDevice var3, SurfaceOrigin var4) {
      boolean var10000 = SkijaRenderer.L();
      super();
      boolean var5 = var10000;
      this.O = 0L;
      this.M = -1;
      this.S = -1;
      this.D = var3;
      this.s = false;
      if (var5) {
         if (!j) {
            j = true;
            System.out.println("[SkiaVulkan] MODE=OFFSCREEN: private renderTexture created (width=" + var1 + ", height=" + var2 + ")");
         }

         String[] var7 = a;
         this.U = var3.createTexture("skia_vk", 15, GpuFormat.RGBA8_UNORM, var1, var2, 1, 1);
         this.t = var3.createTextureView(this.U, 0, 1);
         this.C = x(var3);

         try {
            var3.createCommandEncoder().clearColorTexture(this.U, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F));
         } catch (Exception var8) {
            System.err.println("[SkiaVulkan] Texture pre-clear failed, will rely on Skia clear: " + var8.getMessage());
         }
      }

      this.H = this.P(this.U, var1, var2, var4);
      this.H.getCanvas().clear(0);
      this.C.flushAndSubmit(true);
   }

   public static SkiaVulkanRenderer X(VulkanDevice var0) {
      System.out.println("[SkiaVulkan] createDirect: direct Vulkan renderer created");
      return new SkiaVulkanRenderer(var0);
   }

   private SkiaVulkanRenderer(VulkanDevice var1) {
      this.D = var1;
      this.s = true;
      this.C = x(var1);
   }

   private static DirectContext x(VulkanDevice var0) {
      boolean var10000 = SkijaRenderer.x();
      long var2 = M(var0);
      long var10001 = var0.instance().vkInstance().address();
      long var10003 = var0.vkDevice().address();
      long var10004 = var0.graphicsQueue().vkQueue().address();
      int var10005 = var0.graphicsQueue().queueFamilyIndex();
      FunctionProvider var10006 = VK.getFunctionProvider();
      String[] var5 = a;
      DirectContext var4 = DirectContext.makeVulkan(
         var10001,
         var2,
         var10003,
         var10004,
         var10005,
         var10006.getFunctionAddress("vkGetInstanceProcAddr"),
         VK.getFunctionProvider().getFunctionAddress("vkGetDeviceProcAddr"),
         VK12.VK_API_VERSION_1_2
      );
      boolean var1 = var10000;
      DirectContext var6 = var4;
      if (!var1) {
         if (var4 == null) {
            throw new RuntimeException("Failed to create Skia Vulkan context");
         }

         var6 = var4;
      }

      return var6;
   }

   private Surface P(GpuTexture var1, int var2, int var3, SurfaceOrigin var4) {
      VulkanGpuTexture var5 = (VulkanGpuTexture)var1;
      int var6 = VulkanConst.toVk(var1.getFormat());
      int var7 = VulkanConst.textureUsageToVk(var1.usage(), var1.getFormat());
      BackendRenderTarget var8 = BackendRenderTarget.makeVulkan(var2, var3, var5.vkImage(), 0, 1, var6, var7, 1, 1);

      try {
         return Surface.wrapBackendRenderTarget(this.C, var8, var4, ColorType.RGBA_8888, ColorSpace.getSRGB(), null);
      } finally {
         var8.close();
      }
   }

   @Override
   public Canvas y$Canvas() {
      boolean var1 = SkijaRenderer.x();
      Surface var10000 = this.H;
      if (!var1) {
         if (this.H == null) {
            return null;
         }

         var10000 = this.H;
      }

      return var10000.getCanvas();
   }

   @Override
   public Canvas d(int var1, int var2, GpuTexture var3) {
      boolean var4 = SkijaRenderer.L();
      if (!this.s) {
         throw new UnsupportedOperationException("Not a direct Vulkan renderer");
      }

      if (var3.getFormat() != GpuFormat.RGBA8_UNORM) {
         throw new UnsupportedOperationException("Unsupported main target format: " + var3.getFormat());
      }

      if (this.H != null) {
         this.H.close();
      }

      this.H = this.P(var3, var1, var2, SurfaceOrigin.BOTTOM_LEFT);
      this.M = var1;
      this.S = var2;
      this.O = ((VulkanGpuTexture)var3).vkImage();
      if (!j) {
         j = true;
         System.out.println("[SkiaVulkan] MODE=DIRECT: wrapping MAIN TARGET vkImage=" + Long.toHexString(this.O) + " (no private texture, no blit)");
      }

      return this.H.getCanvas();
   }

   @Override
   public void t() {
      boolean var1 = SkijaRenderer.x();
      boolean var10001 = this.s;
      if (!var1) {
         var10001 = !this.s;
      }

      this.C.flushAndSubmit(var10001);
      SkiaVulkanRenderer var10000 = this;
      if (!var1) {
         if (!this.s) {
            return;
         }

         var10000 = this;
      }

      var10000.M$h();
   }

   private void M$h() {
      RenderTarget var2 = Minecraft.getInstance().gameRenderer.mainRenderTarget();
      if (var2 != null) {
         GpuTexture var3 = var2.getColorTexture();
         if (var3 != null) {
            try {
               VulkanCommandEncoder var4 = this.D.createCommandEncoder();
               MemoryStack var5 = MemoryStack.stackPush();

               try {
                  VkCommandBuffer var6 = var4.allocateAndBeginTransientCommandBuffer();
                  VkImageMemoryBarrier2.Buffer var7 = VkImageMemoryBarrier2.calloc(1, var5).sType$Default();
                  var7.srcStageMask(4096L);
                  var7.srcAccessMask(256L);
                  var7.dstStageMask(65536L);
                  var7.dstAccessMask(98304L);
                  var7.oldLayout(2);
                  var7.newLayout(1);
                  var7.srcQueueFamilyIndex(-1);
                  var7.dstQueueFamilyIndex(-1);
                  var7.image(((VulkanGpuTexture)var3).vkImage());
                  VkImageSubresourceRange var8 = var7.subresourceRange();
                  var8.aspectMask(1);
                  var8.baseMipLevel(0);
                  var8.levelCount(1);
                  var8.baseArrayLayer(0);
                  var8.layerCount(1);
                  VkDependencyInfo var9 = VkDependencyInfo.calloc(var5).sType$Default();
                  var9.pImageMemoryBarriers(var7);
                  KHRSynchronization2.vkCmdPipelineBarrier2KHR(var6, var9);
                  VulkanUtils.crashIfFailure(this.D, VK12.vkEndCommandBuffer(var6), "Failed to end restore-layout command buffer");
                  var4.execute(var6);
                  if (!K) {
                     K = true;
                     System.out.println("[SkiaVulkan] restoreMainTargetLayout: recorded layout transition COLOR_ATTACHMENT_OPTIMAL -> GENERAL");
                  }
               } catch (Throwable var12) {
                  if (var5 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var11) {
                        var12.addSuppressed(var11);
                     }
                  }

                  throw var12;
               }

               if (var5 != null) {
                  var5.close();
               }
            } catch (Exception var13) {
               System.err.println("[SkiaVulkan] restore main target layout failed: " + var13.getMessage());
            }
         }
      }
   }

   @Override
   public void m() {
      boolean var1 = SkijaRenderer.L();
      if (!this.s) {
         RenderTarget var2 = Minecraft.getInstance().gameRenderer.mainRenderTarget();
         RenderPass var3 = RenderSystem.getDevice()
            .createCommandEncoder()
            .createRenderPass(
               RenderPassDescriptor.builder(SkiaVulkanRenderer::deobfLambda$blitToMainTarget$0)
                  .withColorAttachment(var2.getColorTextureView())
                  .withDepthAttachment(var2.getDepthTextureView())
                  .withRenderArea(new RenderArea(0, 0, var2.width, var2.height))
                  .build()
            );

         try {
            var3.setPipeline(RenderSystem.getCompiledPipeline(ElowenRenderPipelines.i));
            RenderSystem.bindDefaultUniforms(var3);
            var3.setUniform("InSampler", this.t, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
            var3.draw(3, 1, 0, 0);
         } catch (Throwable var7) {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (var3 != null) {
            var3.close();
         }
      }
   }

   @Override
   public int u$I() {
      boolean var1 = SkijaRenderer.L();
      return this.H != null ? this.H.getWidth() : 0;
   }

   @Override
   public int H() {
      boolean var1 = SkijaRenderer.L();
      return this.H != null ? this.H.getHeight() : 0;
   }

   @Override
   public GpuTexture e() {
      return this.U;
   }

   @Override
   public GpuTextureView K() {
      return this.t;
   }

   @Override
   public Surface U$Surface() {
      return this.H;
   }

   @Override
   public DirectContext L() {
      return this.C;
   }

   @Override
   public void close() {
      boolean var1 = SkijaRenderer.x();
      SkiaVulkanRenderer var10000 = this;
      if (!var1) {
         if (this.H != null) {
            this.H.close();
         }

         this.H = null;
         this.C.close();
         var10000 = this;
      }

      if (!var1) {
         if (var10000.t != null) {
            this.t.close();
         }

         var10000 = this;
      }

      if (!var1) {
         if (var10000.U != null) {
            this.U.close();
         }

         this.t = null;
         this.U = null;
         var10000 = this;
      }

      var10000.O = 0L;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static long M(VulkanDevice var0) {
      long var27 = 0L;
      int var24 = 0;
      org.lwjgl.system.MemoryStack var26 = null;
      long var25 = 0L;
      boolean var10000 = SkijaRenderer.x();
      MemoryStack var2 = MemoryStack.stackPush();
      boolean var1 = var10000;

      Throwable var3;
      label127: {
         label137: {
            VkInstance var4;
            IntBuffer var5;
            try {
               var24 = var0.graphicsQueue().queueFamilyIndex();
               var4 = var0.instance().vkInstance();
               var5 = var2.callocInt(1);
               VK10.vkEnumeratePhysicalDevices(var4, var5, null);
               if (var5.get(0) == 0) {
                  throw new RuntimeException("No physical device");
               }
            } catch (Throwable var23) {
               var3 = var23;
               var26 = var2;
               if (var1) {
                  break label127;
               }
               break label137;
            }

            PointerBuffer var6;
            int var7;
            try {
               var6 = var2.callocPointer(var5.get(0));
               VK10.vkEnumeratePhysicalDevices(var4, var5, var6);
               var7 = 0;
            } catch (Throwable var20) {
               var3 = var20;
               var26 = var2;
               if (var1) {
                  break label127;
               }
               break label137;
            }

            long var8;
            label138: {
               label115: {
                  while (true) {
                     label112: {
                        try {
                           if (var7 >= var5.get(0)) {
                              break;
                           }

                           var27 = var6.get(var7);
                           if (var1) {
                              break label115;
                           }

                           var8 = var27;
                           VkPhysicalDevice var10 = new VkPhysicalDevice(var8, var4);
                           IntBuffer var11 = var2.callocInt(1);
                           VK10.vkGetPhysicalDeviceQueueFamilyProperties(var10, var11, null);
                           if (var1) {
                              break label112;
                           }

                           if (var11.get(0) > var24) {
                              break label138;
                           }
                        } catch (Throwable var22) {
                           var3 = var22;
                           var26 = var2;
                           if (var1) {
                              break label127;
                           }
                           break label137;
                        }

                        try {
                           var7++;
                        } catch (Throwable var19) {
                           var3 = var19;
                           var26 = var2;
                           if (var1) {
                              break label127;
                           }
                           break label137;
                        }
                     }

                     try {
                        if (!var1) {
                           continue;
                        }
                        break;
                     } catch (Throwable var21) {
                        var3 = var21;
                        var26 = var2;
                        if (var1) {
                           break label127;
                        }
                        break label137;
                     }
                  }

                  try {
                     var27 = var6.get(0);
                  } catch (Throwable var18) {
                     var3 = var18;
                     var26 = var2;
                     if (var1) {
                        break label127;
                     }
                     break label137;
                  }
               }

               try {
                  var25 = var27;
               } catch (Throwable var17) {
                  var3 = var17;
                  var26 = var2;
                  if (var1) {
                     break label127;
                  }
                  break label137;
               }

               MemoryStack var29 = var2;
               if (!var1) {
                  if (var2 == null) {
                     return var25;
                  }

                  var29 = var2;
               }

               var29.close();
               return var25;
            }

            long var12;
            try {
               var12 = var8;
            } catch (Throwable var16) {
               var3 = var16;
               var26 = var2;
               if (var1) {
                  break label127;
               }
               break label137;
            }

            MemoryStack var28 = var2;
            if (!var1) {
               if (var2 == null) {
                  return var12;
               }

               var28 = var2;
            }

            var28.close();
            return var12;
         }

         if (var26 == null) {
            throw a(var3);
         }

         try {
            var26 = var2;
         } catch (Throwable var15) {
            var3.addSuppressed(var15);
            throw a(var3);
         }
      }

      try {
         var26.close();
      } catch (Throwable var14) {
         var3.addSuppressed(var14);
      }

      throw a(var3);
   }

   private static String deobfLambda$blitToMainTarget$0() {
      return "skija_vk_blit";
   }

   private static RuntimeException a(Throwable var0) {
      if (var0 instanceof RuntimeException) {
         return (RuntimeException)var0;
      } else if (var0 instanceof Error) {
         throw (Error)var0;
      } else {
         return new RuntimeException(var0);
      }
   }

   static {
   }
}
