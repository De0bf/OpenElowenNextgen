package com.elowen.utils.renderer.threeD;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.values.HasValue;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class WorldProjector {
   public static final float d = 0.05F;
   private final float T;
   private final float h;
   private final float U;
   private final Matrix4f z;
   private final int J;
   private final int A;
   private static String y;

   public WorldProjector(Camera var1, int var2, int var3, Matrix4f var4) {
      E$String();
      this.J = var2;
      this.A = var3;
      this.z = l(var1, var4);
      Vec3 var6 = var1.position();
      this.T = (float)var6.x;
      this.h = (float)var6.y;
      this.U = (float)var6.z;
      if (!HasValue.x()) {
         z("zWW2Yb");
      }
   }

   private static Matrix4f l(Camera var0, Matrix4f var1) {
      String var10000 = E$String();
      CameraRenderState var3 = Minecraft.getInstance().gameRenderer.gameRenderState().levelRenderState.cameraRenderState;
      String var2 = var10000;
      if (var3.initialized) {
         Matrix4f var4 = new Matrix4f(var3.projectionMatrix);
         Matrix4f var5 = var1;
         if (var2 == null) {
            if (var1 != null) {
               var4.mul(var1);
            }

            var5 = var4.mul(new Matrix4f(var3.viewRotationMatrix));
         }

         return var5;
      } else {
         return var0.getViewRotationProjectionMatrix(new Matrix4f());
      }
   }

   public int l$I() {
      return this.J;
   }

   public int j$I() {
      return this.A;
   }

   public float t() {
      return this.T;
   }

   public float g$F() {
      return this.h;
   }

   public float k() {
      return this.U;
   }

   public Vector4f F(double var1, double var3, double var5) {
      float var7 = (float)var1 - this.T;
      float var8 = (float)var3 - this.h;
      float var9 = (float)var5 - this.U;
      return this.z.transform(var7, var8, var9, 1.0F, new Vector4f());
   }

   public float[] p(double var1, double var3, double var5) {
      Vector4f var7 = this.F(var1, var3, var5);
      if (var7.w <= 0.05F) {
         return null;
      }

      float[] var8 = this.N(var7);
      return new float[]{var8[0], var8[1], var7.w};
   }

   public float[] N(Vector4f var1) {
      String var10000 = E$String();
      float var3 = 1.0F / var1.w;
      String var2 = var10000;
      float var4 = var1.x * var3;
      float var5 = var1.y * var3;
      float var6 = (var4 * 0.5F + 0.5F) * this.J;
      float var7 = (0.5F - var5 * 0.5F) * this.A;
      float[] var8 = new float[]{var6, var7};
      if (var2 != null) {
         HasValue.d(HasValue.x());
      }

      return var8;
   }

   public float[] d(Vector4f var1, Vector4f var2) {
      String var3 = E$String();
      boolean var4 = var1.w > 0.05F;
      boolean var5 = var2.w > 0.05F;
      if (!var4 && !var5) {
         return null;
      } else if (var4 && var5) {
         float[] var9 = this.N(var1);
         float[] var11 = this.N(var2);
         return new float[]{var9[0], var9[1], var11[0], var11[1], (var1.w + var2.w) * 0.5F};
      } else {
         Vector4f var6 = o(var1, var2);
         if (var4) {
            float[] var10 = this.N(var1);
            float[] var12 = this.N(var6);
            return new float[]{var10[0], var10[1], var12[0], var12[1], (var1.w + 0.05F) * 0.5F};
         } else {
            float[] var7 = this.N(var6);
            float[] var8 = this.N(var2);
            return new float[]{var7[0], var7[1], var8[0], var8[1], (0.05F + var2.w) * 0.5F};
         }
      }
   }

   public static List a(List var0) {
      String var10000 = E$String();
      ArrayList var2 = new ArrayList(var0.size() + 1);
      int var3 = var0.size();
      String var1 = var10000;
      int var4 = 0;

      while (var4 < var3) {
         Vector4f var5 = (Vector4f)var0.get(var4);
         Vector4f var6 = (Vector4f)var0.get((var4 + var3 - 1) % var3);
         float var12;
         int var9 = (var12 = var5.w - 0.05F) == 0.0F ? 0 : (var12 < 0.0F ? -1 : 1);
         if (var1 == null) {
            var9 = var9 > 0 ? 1 : 0;
         }

         int var7 = var9;
         float var13;
         int var10 = (var13 = var6.w - 0.05F) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
         if (var1 == null) {
            var10 = var10 > 0 ? 1 : 0;
         }

         label76: {
            int var8 = var10;
            int var11 = var7;
            if (var1 == null) {
               if (var7 != 0) {
                  if (var1 == null) {
                     if (var8 == 0) {
                        var2.add(o(var6, var5));
                     }

                     var2.add(var5);
                  }

                  if (var1 == null) {
                     break label76;
                  }
               }

               var11 = var8;
            }

            if (var1 == null && var11 != 0) {
               var2.add(o(var6, var5));
            }
         }

         var4++;
         if (var1 != null) {
            break;
         }
      }

      return var2;
   }

   private static Vector4f o(Vector4f var0, Vector4f var1) {
      float var2 = (0.05F - var0.w) / (var1.w - var0.w);
      return new Vector4f().set(var0).lerp(var1, var2);
   }

   public static void z(String var0) {
      y = var0;
   }

   public static String E$String() {
      return y;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (E$String() != null) {
         z("T12Qac");
      }
   }
}
