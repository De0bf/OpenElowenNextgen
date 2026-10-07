package com.elowen.utils.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.Vector2f;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

public class Rotation {
   float Q;
   float t;
   public double x;
   public Runnable d;
   public Runnable A;
   static final boolean g;
   private static final String[] a = new String[]{", distanceSq=", ", pitch=", ", postTask=", ", task=", "Rotation(yaw="};
   public Rotation() {
      this.Q = 0.0F;
      this.t = 0.0F;
   }

   public Rotation(float var1, float var2) {
      this.Q = var1;
      this.t = var2;
   }

   public Rotation(Vector2f var1) {
      this.Q = var1.S$F();
      this.t = var1.p();
   }

   public Rotation(Vec3 var1, Vec3 var2) {
      Vec3 var3 = var2.subtract(var1);
      this.Q = Mth.wrapDegrees((float)Math.toDegrees(Math.atan2(var3.z, var3.x)) - 90.0F);
      this.t = Mth.wrapDegrees((float)(-Math.toDegrees(Math.atan2(var3.y, Math.sqrt(var3.x * var3.x + var3.z * var3.z)))));
   }

   public Vector2f M() {
      return new Vector2f(this.Q, this.t);
   }

   public Rotation u(Rotation var1) {
      return new Rotation(this.Q - var1.Q, this.t - var1.t);
   }

   public Rotation p() {
      return new Rotation(-this.Q, -this.t);
   }

   public Rotation n(Runnable var1) {
      this.d = var1;
      return this;
   }

   public Rotation Q(Runnable var1) {
      this.A = var1;
      return this;
   }

   public void e() {
      Minecraft.getInstance().player.setYRot(this.Q);
      Minecraft.getInstance().player.setXRot(this.t);
   }

   public void M(Player var1) {
      boolean var2 = RotationUtils.T$Z();
      if (!Float.isNaN(this.Q) && !Float.isNaN(this.t)) {
         this.a(((Double)Minecraft.getInstance().options.sensitivity().get()).floatValue());
         var1.setYRot(this.Q);
         var1.setXRot(this.t);
      }
   }

   public void a(Float var1) {
      float var2 = var1 * 0.6F + 0.2F;
      float var3 = var2 * var2 * var2 * 1.2F;
      this.Q = this.Q - this.Q % var3;
      this.t = this.t - this.t % var3;
   }

   public static float k(float var0, float var1, float var2) {
      int var10000 = ((RotationUtils.T$Z()) ? 1 : 0);
      float var4 = Mth.wrapDegrees(var1 - var0);
      boolean var3 = (boolean)((var10000) != 0);
      float var7;
      var10000 = (var7 = var4 - var2) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
      if (!var3) {
         if (var10000 > 0) {
            var4 = var2;
         }

         float var10001 = -var2;
         if (var3) {
            return var4 + var10001;
         }

         float var8;
         var10000 = (var8 = var4 - var10001) == 0.0F ? 0 : (var8 < 0.0F ? -1 : 1);
      }

      if (var10000 < 0) {
         var4 = -var2;
      }

      return var0 + var4;
   }

   public double o(Rotation var1) {
      float var2 = Mth.wrapDegrees(this.Q);
      float var3 = Mth.wrapDegrees(var1.Q);
      float var4 = Mth.wrapDegrees(var2 - var3);
      float var5 = Mth.wrapDegrees(this.t);
      float var6 = Mth.wrapDegrees(var1.t);
      float var7 = Mth.wrapDegrees(var5 - var6);
      return Math.sqrt(var4 * var4 + var7 * var7);
   }

   public float Z(float var1, float var2, float var3) {
      int var14 = 0;
      boolean var4;
      float var5;
      float var15;
      label61: {
         label62: {
            label63: {
               boolean var10000 = RotationUtils.t();
               var5 = k(var2, var3, var1 + RandomUtils.nextFloat(0.0F, 15.0F));
               var4 = var10000;
               double var6 = Mth.wrapDegrees(var3 - var2);
               double var17;
               int var13 = (var17 = -var1 - var6) == 0.0 ? 0 : (var17 < 0.0 ? -1 : 1);
               if (var4) {
                  if (var13 <= 0) {
                     double var18;
                     var14 = (var18 = var6 - var1) == 0.0 ? 0 : (var18 < 0.0 ? -1 : 1);
                     if (!var4) {
                        break label62;
                     }

                     if (var14 <= 0) {
                        break label63;
                     }
                  }

                  var13 = ((g) ? 1 : 0);
               }

               if (var13 == 0 && Minecraft.getInstance().player == null) {
                  throw new AssertionError();
               }

               var5 += (float)(RandomUtils.nextFloat(1.0F, 2.0F) * Math.sin(Minecraft.getInstance().player.getXRot() * Math.PI));
            }

            var15 = var5;
            if (!var4) {
               break label61;
            }

            float var19;
            var14 = (var19 = var5 - var2) == 0.0F ? 0 : (var19 < 0.0F ? -1 : 1);
         }

         if (var14 == 0) {
            return var2;
         }

         var15 = ((Double)Minecraft.getInstance().options.sensitivity().get()).floatValue();
      }

      float var8 = var15;
      var15 = var8;
      if (var4) {
         if (var8 == 0.5) {
            var8 = 0.47887325F;
         }

         var15 = var8 * 0.6F + 0.2F;
      }

      float var9 = var15;
      float var10 = var9 * var9 * var9 * 8.0F;
      int var11 = (int)((6.667 * var5 - 6.666666666666667 * var2) / var10);
      float var12 = var11 * var10;
      return (float)(var2 + var12 * 0.15);
   }

   public float B(float var1, float[] var2, float var3) {
      boolean var4;
      float var5;
      float var13;
      label38: {
         boolean var10000 = RotationUtils.t();
         var5 = k(var2[0], var3, var1 + RandomUtils.nextFloat(0.0F, 15.0F));
         var4 = var10000;
         float var15;
         int var12 = (var15 = var5 - var3) == 0.0F ? 0 : (var15 < 0.0F ? -1 : 1);
         if (var4) {
            if (((var12) != 0)) {
               var5 += (float)(RandomUtils.nextFloat(1.0F, 2.0F) * Math.sin(var2[1] * Math.PI));
            }

            var13 = var5;
            if (!var4) {
               break label38;
            }

            float var16;
            var12 = (var16 = var5 - var2[0]) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
         }

         if ((var12 == 0)) {
            return var2[0];
         }

         var13 = ((Double)Minecraft.getInstance().options.sensitivity().get()).floatValue();
      }

      float var6 = var13;
      var5 += (float)(ThreadLocalRandom.current().nextGaussian() * 0.2);
      var13 = var6;
      if (var4) {
         if (var6 == 0.5) {
            var6 = 0.47887325F;
         }

         var13 = var6 * 0.6F + 0.2F;
      }

      float var7 = var13;
      float var8 = var7 * var7 * var7 * 8.0F;
      int var9 = (int)((6.667 * var5 - 6.6666667 * var2[0]) / var8);
      float var10 = var9 * var8;
      return (float)(var2[0] + var10 * 0.15);
   }

   public float y(float var1, float var2, float var3) {
      boolean var10000 = RotationUtils.t();
      float var5 = k(var2, var3, var1 + RandomUtils.nextFloat(0.0F, 15.0F));
      boolean var4 = var10000;
      float var12 = var5;
      if (var4) {
         if (var5 != var3) {
            var5 += (float)(RandomUtils.nextFloat(1.0F, 2.0F) * Math.sin(Minecraft.getInstance().player.getYRot() * Math.PI));
         }

         var12 = ((Double)Minecraft.getInstance().options.sensitivity().get()).floatValue();
      }

      float var6 = var12;
      float var13 = var6;
      if (var4) {
         if (var6 == 0.5) {
            var6 = 0.47887325F;
         }

         var13 = var6 * 0.6F + 0.2F;
      }

      float var7 = var13;
      float var8 = var7 * var7 * var7 * 8.0F;
      int var9 = (int)((6.667 * var5 - 6.666667 * var2) / var8) * -1;
      float var10 = var9 * var8;
      float var11 = (float)(var2 - var10 * 0.15);
      return Mth.clamp(var11, -90.0F, 90.0F);
   }

   public float Y(float var1, float[] var2, float var3) {
      boolean var10000 = RotationUtils.T$Z();
      float var5 = k(var2[1], var3, var1 + RandomUtils.nextFloat(0.0F, 15.0F));
      boolean var4 = var10000;
      float var12 = var5;
      if (!var4) {
         if (var5 != var3) {
            var5 += (float)(RandomUtils.nextFloat(1.0F, 2.0F) * Math.sin(var2[0] * Math.PI));
         }

         var12 = ((Double)Minecraft.getInstance().options.sensitivity().get()).floatValue();
      }

      float var6 = var12;
      float var13 = var6;
      if (!var4) {
         if (var6 == 0.5) {
            var6 = 0.47887325F;
         }

         var13 = var6 * 0.6F + 0.2F;
      }

      float var7 = var13;
      float var8 = var7 * var7 * var7 * 8.0F;
      int var9 = (int)((6.667 * var5 - 6.666667 * var2[1]) / var8) * -1;
      float var10 = var9 * var8;
      float var11 = (float)(var2[1] - var10 * 0.15);
      return Mth.clamp(var11, -90.0F, 90.0F);
   }

   public void m(float var1, float var2) {
      this.Q = var1;
      this.t = var2;
   }

   public float o$F() {
      return this.Q;
   }

   public float y$F() {
      return this.t;
   }

   public double q$D() {
      return this.x;
   }

   public Runnable U$Runnable() {
      return this.d;
   }

   public Runnable T$Runnable() {
      return this.A;
   }

   public void A(float var1) {
      this.Q = var1;
   }

   public void b(float var1) {
      this.t = var1;
   }

   public void T(double var1) {
      this.x = var1;
   }

   public void R(Runnable var1) {
      this.d = var1;
   }

   public void g(Runnable var1) {
      this.A = var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      }

      if (!(var1 instanceof Rotation)) {
         return false;
      }

      Rotation var3 = (Rotation)var1;
      if (Float.compare(this.o$F(), var3.o$F()) != 0) {
         return false;
      }

      if (Float.compare(this.y$F(), var3.y$F()) != 0) {
         return false;
      }

      if (Double.compare(this.q$D(), var3.q$D()) != 0) {
         return false;
      }

      Runnable var4 = this.U$Runnable();
      Runnable var5 = var3.U$Runnable();
      if (var4 == null) {
         if (var5 != null) {
            return false;
         }
      } else if (!var4.equals(var5)) {
         return false;
      }

      Runnable var6 = this.T$Runnable();
      Runnable var7 = var3.T$Runnable();
      if (var6 == null) {
         return var7 == null;
      } else {
         return var6.equals(var7);
      }
   }

   protected boolean J(Object var1) {
      return var1 instanceof Rotation;
   }

   @Override
   public int hashCode() {
      boolean var1;
      int var12;
      int var14;
      label31: {
         byte var2 = 59;
         boolean var10000 = RotationUtils.T$Z();
         int var3 = 1;
         var3 = var3 * 59 + Float.floatToIntBits(this.o$F());
         var3 = var3 * 59 + Float.floatToIntBits(this.y$F());
         long var4 = Double.doubleToLongBits(this.q$D());
         var3 = var3 * 59 + (int)(var4 >>> 32 ^ var4);
         var1 = var10000;
         Runnable var6 = this.U$Runnable();
         var12 = var3 * 59;
         Runnable var10001 = var6;
         if (!var1) {
            if (var6 == null) {
               var14 = 43;
               break label31;
            }

            var10001 = var6;
         }

         var14 = var10001.hashCode();
      }

      int var11 = var12 + var14;
      Runnable var7 = this.T$Runnable();
      var12 = var11 * 59;
      Runnable var15 = var7;
      if (!var1) {
         if (var7 == null) {
            return var12 + 43;
         }

         var15 = var7;
      }

      return var12 + var15.hashCode();
   }

   @Override
   public String toString() {
      float var10000 = this.o$F();
      float var10001 = this.y$F();
      double var10002 = this.q$D();
      String var1 = String.valueOf(this.T$Runnable());
      String var2 = String.valueOf(this.U$Runnable());
      double var3 = var10002;
      float var5 = var10001;
      float var6 = var10000;
      String[] var7 = a;
      return "Rotation(yaw=" + var6 + ", pitch=" + var5 + ", distanceSq=" + var3 + ", task=" + var2 + ", postTask=" + var1 + ")";
   }

   static {
      label50:
      g = !Rotation.class.desiredAssertionStatus();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
