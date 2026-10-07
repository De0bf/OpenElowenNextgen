package com.elowen.utils.renderer;

import com.elowen.values.HasValue;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.mojang.renderpearl.backend.opengl.GlTexture;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

public class SkiaOpenglRenderer extends SkiaGpuRenderer {
   private GpuTexture b;
   private Surface M;
   private int g = -1;
   private int q = -1;
   private int j = -1;
   private int n = -1;
   private int e = -1;
   private final DirectContext l;
   private final int Y;
   private static Class u;
   private static Field o;
   private static Field i;
   private static Field W;
   private final int[] C = new int[4];
   private int A;
   private int r;
   private int U;
   private int G;
   private boolean F;
   private int B;
   private int D;
   private int x;
   private int Q;
   private int h;
   private int I;
   private boolean m;
   private boolean p;
   private int X;
   private boolean J;
   private int f;
   private int K;
   private int t;
   private int d;
   private int O;
   private int w;
   private int P;
   private final boolean[] k = new boolean[4];
   private boolean a;
   private int R;
   private int T;
   private int L;
   private boolean H;
   private final int[] N = new int[4];
   private int c;
   private int v;
   private int V;
   private final int[] Z = new int[16];
   private int S;
   private int E;
   private static final String[] s = new String[]{"lastProgram", "lastPipeline", "Unsupported main target format: ", "com.mojang.renderpearl.backend.opengl.GlCommandEncoder", "OpenGL renders directly; blitToMainTarget is not supported", "encoder", "lastVertexArray"};
   private static Field w(String var0) {
      if (u == null) {
         return null;
      }

      try {
         Field var1 = u.getDeclaredField(var0);
         var1.setAccessible(true);
         return var1;
      } catch (NoSuchFieldException var2) {
         return null;
      }
   }

   public SkiaOpenglRenderer() {
      this.Y = GL31.glGenFramebuffers();
      this.l = DirectContext.makeGL();
   }

   @Override
   public Canvas y$Canvas() {
      boolean var1 = com.elowen.utils.renderer.SkijaRenderer.x();
      Surface var10000 = this.M;
      if (!var1) {
         if (this.M == null) {
            return null;
         }

         var10000 = this.M;
      }

      return var10000.getCanvas();
   }

   @Override
   public Canvas d(int var1, int var2, GpuTexture var3) {
      boolean var4 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (var3.getFormat() != GpuFormat.RGBA8_UNORM) {
         throw new UnsupportedOperationException("Unsupported main target format: " + var3.getFormat());
      }

      this.b = var3;
      if (this.M == null || this.g != var1 || this.q != var2) {
         if (this.M != null) {
            this.M.close();
         }

         this.n(var1, var2);
         BackendRenderTarget var5 = BackendRenderTarget.makeGL(var1, var2, 0, 8, this.Y, 32856);
         this.M = Surface.wrapBackendRenderTarget(this.l, var5, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, ColorSpace.getSRGB());
         this.g = var1;
         this.q = var2;
      }

      this.l.resetGLAll();
      GlTexture var6 = (GlTexture)this.b;
      GL30.glBindFramebuffer(36160, this.Y);
      GL30.glFramebufferTexture2D(36160, 36064, 3553, var6.glId(), 0);
      GL11.glDrawBuffer(36064);
      GL11.glReadBuffer(36064);
      return this.M.getCanvas();
   }

   private void n(int var1, int var2) {
      boolean var3 = com.elowen.utils.renderer.SkijaRenderer.L();
      if (this.j == -1) {
         this.j = GL30.glGenRenderbuffers();
      }

      if (var1 != this.n || var2 != this.e) {
         GL30.glBindRenderbuffer(36161, this.j);
         GL30.glRenderbufferStorage(36161, 36168, var1, var2);
         this.n = var1;
         this.e = var2;
      }

      GL30.glBindFramebuffer(36160, this.Y);
      GL30.glFramebufferRenderbuffer(36160, 36128, 36161, this.j);
   }

   @Override
   public void p() {
      SkiaOpenglRenderer var5 = null;
      GL11.glGetIntegerv(2978, this.C);
      this.A = GL11.glGetInteger(36006);
      this.r = GL11.glGetInteger(36010);
      this.U = GL11.glGetInteger(34229);
      this.G = GL11.glGetInteger(35725);
      this.F = GL11.glIsEnabled(3042);
      this.B = GL11.glGetInteger(32969);
      this.D = GL11.glGetInteger(32968);
      this.x = GL11.glGetInteger(32971);
      this.Q = GL11.glGetInteger(32970);
      this.h = GL11.glGetInteger(32777);
      this.I = GL11.glGetInteger(34877);
      this.m = GL11.glIsEnabled(2929);
      this.p = GL11.glGetBoolean(2930);
      this.X = GL11.glGetInteger(2932);
      this.J = GL11.glIsEnabled(2960);
      this.f = GL11.glGetInteger(2962);
      int var10000 = ((com.elowen.utils.renderer.SkijaRenderer.L()) ? 1 : 0);
      this.K = GL11.glGetInteger(2967);
      this.t = GL11.glGetInteger(2963);
      this.d = GL11.glGetInteger(2964);
      this.O = GL11.glGetInteger(2965);
      this.w = GL11.glGetInteger(2966);
      this.P = GL11.glGetInteger(2968);
      boolean var1 = (boolean)((var10000) != 0);
      ByteBuffer var2 = ByteBuffer.allocateDirect(16);
      GL11.glGetBooleanv(3107, var2);
      int var3 = 0;

      while (true) {
         if (var3 < 4) {
            var5 = this;
            if (!var1) {
               break;
            }

            boolean[] var6 = this.k;
            byte var10002 = var2.get(var3);
            if (var1) {
               var10002 = (byte)(var10002 != 0 ? 1 : 0);
            }

            var6[var3] = (boolean)((var10002) != 0);
            var3++;
            if (var1) {
               continue;
            }

            com.elowen.values.HasValue.d(com.elowen.values.HasValue.x());
         }

         this.a = GL11.glIsEnabled(2884);
         this.R = GL11.glGetInteger(2885);
         this.T = GL11.glGetInteger(2886);
         this.L = GL11.glGetInteger(2880);
         this.H = GL11.glIsEnabled(3089);
         var5 = this;
         break;
      }

      var10000 = ((var5.H) ? 1 : 0);
      if (var1) {
         if (var5.H) {
            GL11.glGetIntegerv(3088, this.N);
         }

         this.c = GL11.glGetInteger(3073);
         this.v = GL11.glGetInteger(3074);
         this.V = GL11.glGetInteger(34016);
         var10000 = 0;
      }

      var3 = var10000;

      while (true) {
         if (var3 < this.Z.length) {
            GL13.glActiveTexture(33984 + var3);
            this.Z[var3] = GL11.glGetInteger(32873);
            var3++;
            if (!var1) {
               break;
            }

            if (var1) {
               continue;
            }
         }

         GL13.glActiveTexture(this.V);
         this.S = GL11.glGetInteger(3317);
         this.E = GL11.glGetInteger(3333);
         break;
      }
   }

   @Override
   public void F() {
      boolean var1;
      label116: {
         int var10000 = ((com.elowen.utils.renderer.SkijaRenderer.x()) ? 1 : 0);
         GL11.glViewport(this.C[0], this.C[1], this.C[2], this.C[3]);
         GL30.glBindFramebuffer(36009, this.A);
         var1 = (boolean)((var10000) != 0);
         GL30.glBindFramebuffer(36008, this.r);
         GL30.glBindVertexArray(this.U);
         GL20.glUseProgram(this.G);
         var10000 = ((this.F) ? 1 : 0);
         if (!var1) {
            if (this.F) {
               GL11.glEnable(3042);
               if (!var1) {
                  break label116;
               }
            }

            var10000 = 3042;
         }

         GL11.glDisable(var10000);
      }

      label109: {
         GL14.glBlendFuncSeparate(this.B, this.D, this.x, this.Q);
         GL20.glBlendEquationSeparate(this.h, this.I);
         int var4 = ((this.m) ? 1 : 0);
         if (!var1) {
            if (this.m) {
               GL11.glEnable(2929);
               if (!var1) {
                  break label109;
               }
            }

            var4 = 2929;
         }

         GL11.glDisable(var4);
      }

      label102: {
         GL11.glDepthMask(this.p);
         GL11.glDepthFunc(this.X);
         int var5 = ((this.J) ? 1 : 0);
         if (!var1) {
            if (this.J) {
               GL11.glEnable(2960);
               if (!var1) {
                  break label102;
               }
            }

            var5 = 2960;
         }

         GL11.glDisable(var5);
      }

      label95: {
         GL11.glStencilFunc(this.f, this.K, this.t);
         GL11.glStencilOp(this.d, this.O, this.w);
         GL11.glStencilMask(this.P);
         GL11.glColorMask(this.k[0], this.k[1], this.k[2], this.k[3]);
         int var6 = ((this.a) ? 1 : 0);
         if (!var1) {
            if (this.a) {
               GL11.glEnable(2884);
               if (!var1) {
                  break label95;
               }
            }

            var6 = 2884;
         }

         GL11.glDisable(var6);
      }

      label88: {
         GL11.glCullFace(this.R);
         GL11.glFrontFace(this.T);
         GL11.glPolygonMode(1032, this.L);
         int var7 = ((this.H) ? 1 : 0);
         if (!var1) {
            if (this.H) {
               GL11.glEnable(3089);
               GL11.glScissor(this.N[0], this.N[1], this.N[2], this.N[3]);
               if (!var1) {
                  break label88;
               }
            }

            var7 = 3089;
         }

         GL11.glDisable(var7);
      }

      GL11.glDrawBuffer(this.c);
      GL11.glReadBuffer(this.v);
      int var2 = 0;

      while (true) {
         if (var2 < this.Z.length) {
            GL13.glActiveTexture(33984 + var2);
            GL11.glBindTexture(3553, this.Z[var2]);
            var2++;
            if (var1) {
               break;
            }

            if (!var1) {
               continue;
            }
         }

         GL13.glActiveTexture(this.V);
         GL11.glPixelStorei(3317, this.S);
         GL11.glPixelStorei(3333, this.E);
         break;
      }

      if (com.elowen.values.HasValue.X$Z()) {
         com.elowen.utils.renderer.SkijaRenderer.J(!var1);
      }
   }

   @Override
   public void t() {
      this.l.resetGLAll();
      boolean var10000 = com.elowen.utils.renderer.SkijaRenderer.L();
      this.l.flush();
      GL11.glFlush();
      boolean var1 = var10000;

      try {
         GpuDevice var2 = RenderSystem.getDevice();
         Field var3 = var2.getClass().getDeclaredField("encoder");
         var3.setAccessible(true);
         Object var4 = var3.get(var2);
         Object var6 = var4;
         if (var1) {
            if (var4 == null) {
               return;
            }

            C(var4, o);
            C(var4, i);
            var6 = var4;
         }

         C(var6, W);
      } catch (Exception var5) {
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static void C(Object var0, Field var1) {
      boolean var2 = com.elowen.utils.renderer.SkijaRenderer.x();
      Field var10000 = var1;
      if (var2) {
         try {
            var10000.set(var0, null);
         } catch (Exception var4) {
         }
      } else if (var1 != null) {
         label21:
         try {
            var10000 = var1;
            break label21;
         } catch (Exception var5) {
         }
      }
   }

   @Override
   public void m() {
      throw new UnsupportedOperationException("OpenGL renders directly; blitToMainTarget is not supported");
   }

   @Override
   public int u$I() {
      boolean var1 = com.elowen.utils.renderer.SkijaRenderer.L();
      return this.M != null ? this.M.getWidth() : 0;
   }

   @Override
   public int H() {
      boolean var1 = com.elowen.utils.renderer.SkijaRenderer.L();
      return this.M != null ? this.M.getHeight() : 0;
   }

   @Override
   public GpuTexture e() {
      return null;
   }

   @Override
   public GpuTextureView K() {
      return null;
   }

   @Override
   public Surface U$Surface() {
      return this.M;
   }

   @Override
   public DirectContext L() {
      return this.l;
   }

   @Override
   public void close() {
      boolean var1 = com.elowen.utils.renderer.SkijaRenderer.x();
      SkiaOpenglRenderer var10000 = this;
      if (!var1) {
         if (this.M != null) {
            this.M.close();
         }

         this.l.close();
         GL31.glDeleteFramebuffers(this.Y);
         var10000 = this;
      }

      int var2 = var10000.j;
      if (!var1) {
         if (var10000.j == -1) {
            return;
         }

         var2 = this.j;
      }

      GL30.glDeleteRenderbuffers(var2);
      this.j = -1;
   }

   static {
      label52:
      try {
      String[] var11 = s;
      u = Class.forName("com.mojang.renderpearl.backend.opengl.GlCommandEncoder");
      } catch (ClassNotFoundException var8) {
      u = null;
      }
      String[] var12 = s;
      o = w("lastPipeline");
      i = w("lastProgram");
      W = w("lastVertexArray");
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}
