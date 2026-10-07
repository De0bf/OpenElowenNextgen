package com.elowen.utils.renderer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.backend.opengl.GlStateManager;
import com.mojang.renderpearl.backend.opengl.GlTexture;
import io.github.humbleui.skija.BackendTexture;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.GLTextureInfo;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.SurfaceOrigin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class OpenGLScreenCapture {
   private static GpuTexture G;
   private static int B;
   private static int s;
   private static int z;
   private static BackendTexture X;
   private static Image S;
   private static DirectContext Z;
   private static int I;
   private static int Q;
   private static final String[] a = new String[]{"elowen_world_capture_gl", "Read framebuffer incomplete for screen capture"};
   private OpenGLScreenCapture() {
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void o$V() {
      int var10000 = ((SkijaRenderer.L()) ? 1 : 0);
      Minecraft var1 = Minecraft.getInstance();
      boolean var0 = (boolean)((var10000) != 0);
      Minecraft var21 = var1;
      if (var0) {
         if (var1 == null) {
            return;
         }

         var21 = var1;
      }

      GameRenderer var22 = var21.gameRenderer;
      if (var0) {
         if (var21.gameRenderer == null) {
            return;
         }

         var22 = var1.gameRenderer;
      }

      RenderTarget var2 = var22.mainRenderTarget();
      RenderTarget var23 = var2;
      if (var0) {
         if (var2 == null) {
            return;
         }

         var23 = var2;
      }

      GpuTexture var3 = var23.getColorTexture();
      GpuTexture var24 = var3;
      if (var0) {
         if (!(var3 instanceof GlTexture)) {
            return;
         }

         var24 = var3;
      }

      GlTexture var4 = (GlTexture)var24;
      if (var0) {
         int var5 = var2.width;
         int var6 = var2.height;
         var10000 = var5;
         if (var0) {
            if (var5 <= 0) {
               return;
            }

            var10000 = var6;
         }

         if (var10000 > 0) {
            GpuDevice var7 = RenderSystem.getDevice();
            if (var7 != null) {
               label189: {
                  label188: {
                     label187: {
                        GpuTexture var26 = G;
                        if (var0) {
                           if (G != null) {
                              var10000 = B;
                              if (!var0) {
                                 break label187;
                              }

                              if (B == var5) {
                                 var10000 = s;
                                 if (!var0) {
                                    break label189;
                                 }

                                 if (s == var6) {
                                    break label188;
                                 }
                              }
                           }

                           J$V();
                           var26 = var7.createTexture(OpenGLScreenCapture::deobfLambda$captureWorld$0, 5, var3.getFormat(), var5, var6, 1, 1);
                        }

                        G = var26;
                        B = var5;
                        var10000 = var6;
                     }

                     s = var10000;
                  }

                  var10000 = var4.glId();
               }

               int var8 = var10000;
               int var9 = ((GlTexture)G).glId();
               int var10 = GL11.glGetInteger(34016);
               int var11 = GL11.glGetInteger(32873);
               int var12 = GL11.glGetInteger(36010);
               int var13 = GL11.glGetInteger(3074);
               boolean var16 = false /* VF: Semaphore variable */;

               int var10001;
               label172: {
                  try {
                     var16 = true;
                     var10000 = z;
                     var10001 = -1;
                     if (var0) {
                        if (z == -1) {
                           z = GL30.glGenFramebuffers();
                        }

                        GlStateManager._glBindFramebuffer(36008, z);
                        GL30.glFramebufferTexture2D(36008, 36064, 3553, var8, 0);
                        var10000 = GL30.glCheckFramebufferStatus(36008);
                        var10001 = 36053;
                     }

                     if (!var0) {
                        var16 = false;
                        break label172;
                     }

                     if (var10000 != var10001) {
                        throw new RuntimeException("Read framebuffer incomplete for screen capture");
                     }

                     GL11.glReadBuffer(36064);
                     GlStateManager._activeTexture(33984);
                     GlStateManager._bindTexture(var9);
                     GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var5, var6);
                     I++;
                     var16 = false;
                  } finally {
                     if (var16) {
                        GlStateManager._activeTexture(var10);
                        GlStateManager._bindTexture(var11);
                        GlStateManager._glBindFramebuffer(36008, var12);
                        GL11.glReadBuffer(var13);
                     }
                  }

                  GlStateManager._activeTexture(var10);
                  GlStateManager._bindTexture(var11);
                  var10000 = 36008;
                  var10001 = var12;
               }

               GlStateManager._glBindFramebuffer(var10000, var10001);
               GL11.glReadBuffer(var13);
            }
         }
      }
   }

   public static Image K(DirectContext var0) {
      boolean var1 = SkijaRenderer.L();
      if (G == null || var0 == null) {
         return null;
      }

      if (I == 0) {
         return null;
      }

      if (S == null || Z != var0 || X == null || Q != I) {
         if (S != null) {
            S.close();
         }

         if (X != null) {
            X.close();
         }

         X = null;
         S = null;
         GlTexture var2 = (GlTexture)G;
         GLTextureInfo var3 = new GLTextureInfo(3553, var2.glId(), 32856, false);
         X = BackendTexture.makeGL(B, s, false, var3);
         S = Image.borrowTextureFrom(var0, X, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB(), null);
         Z = var0;
         Q = I;
      }

      return S;
   }

   public static void g$V() {
      boolean var10000 = SkijaRenderer.x();
      J$V();
      boolean var0 = var10000;
      if (!var0) {
         if (z != -1) {
            GL30.glDeleteFramebuffers(z);
            z = -1;
         }

         Z = null;
      }
   }

   private static void J$V() {
      boolean var0 = SkijaRenderer.L();
      if (S != null) {
         S.close();
         S = null;
      }

      if (X != null) {
         X.close();
         X = null;
      }

      if (G != null) {
         G.close();
         G = null;
      }

      B = -1;
      s = -1;
      I = 0;
      Q = -1;
   }

   private static String deobfLambda$captureWorld$0() {
      return "elowen_world_capture_gl";
   }

   static {
      B = -1;
      s = -1;
      z = -1;
      Q = -1;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
