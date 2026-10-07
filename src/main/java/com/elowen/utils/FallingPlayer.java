package com.elowen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FallingPlayer {
   public double O;
   public double q;
   public double j;
   private double A;
   private double B;
   private double e;
   private final float t;
   private final float p;
   private final float l;
   private float K;
   private Minecraft U = Minecraft.getInstance();

   public FallingPlayer(double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15) {
      this.O = var1;
      this.q = var3;
      this.j = var5;
      this.A = var7;
      this.B = var9;
      this.e = var11;
      this.t = var13;
      this.p = var14;
      this.l = var15;
   }

   public FallingPlayer(Player var1) {
      this(
         var1.getX(),
         var1.getY(),
         var1.getZ(),
         var1.getDeltaMovement().x,
         var1.getDeltaMovement().y,
         var1.getDeltaMovement().z,
         var1.getYRot(),
         var1.xxa,
         var1.zza
      );
      this.K = var1.isSprinting() ? 0.026F : 0.02F;
   }

   private void n$V() {
      String var10000 = Vector2f.e();
      float var2 = this.p;
      float var3 = this.l;
      String var1 = var10000;
      float var4 = var2 * var2 + var3 * var3;
      if (var1 == null) {
         if (var4 >= 1.0E-4F) {
            var4 = Mth.sqrt(var4);
            float var10 = var4;
            if (var1 == null) {
               if (var4 < 1.0F) {
                  var4 = 1.0F;
               }

               var10 = this.K;
            }

            float var5 = var10;
            FallingPlayer var11 = this;
            if (var1 == null) {
               if (this.U.player.isSprinting()) {
                  var5 *= 1.3F;
               }

               var4 = var5 / var4;
               var2 *= var4;
               var3 *= var4;
               var11 = this;
            }

            float var6 = Mth.sin(var11.t * (float) Math.PI / 180.0F);
            float var7 = Mth.cos(this.t * (float) Math.PI / 180.0F);
            this.A += var2 * var7 - var3 * var6;
            this.e += var3 * var7 + var2 * var6;
         }

         this.B -= 0.08;
         this.B *= 0.98F;
         this.O = this.O + this.A;
         this.q = this.q + this.B;
         this.j = this.j + this.e;
      }
   }

   private void x() {
      String var10000 = Vector2f.e();
      float var2 = this.p * 0.98F;
      String var1 = var10000;
      float var3 = this.l * 0.98F;
      float var4 = var2 * var2 + var3 * var3;
      if (var1 == null) {
         if (var4 >= 1.0E-4F) {
            var4 = Mth.sqrt(var4);
            float var10 = var4;
            if (var1 == null) {
               if (var4 < 1.0F) {
                  var4 = 1.0F;
               }

               var10 = this.K;
            }

            float var5 = var10;
            FallingPlayer var11 = this;
            if (var1 == null) {
               if (this.U.player.isSprinting()) {
                  var5 *= 1.3F;
               }

               var4 = var5 / var4;
               var2 *= var4;
               var3 *= var4;
               var11 = this;
            }

            float var6 = Mth.sin(var11.t * (float) Math.PI / 180.0F);
            float var7 = Mth.cos(this.t * (float) Math.PI / 180.0F);
            this.A += var2 * var7 - var3 * var6;
            this.e += var3 * var7 + var2 * var6;
         }

         this.B -= 0.08;
         this.B *= 0.98F;
         this.O = this.O + this.A;
         this.q = this.q + this.B;
         this.j = this.j + this.e;
         this.A *= 0.91;
         this.e *= 0.91;
      }
   }

   public void W(int var1) {
      String var10000 = Vector2f.e();
      int var3 = 0;
      String var2 = var10000;

      while (var3 < var1) {
         this.n$V();
         var3++;
         if (var2 != null) {
            break;
         }
      }
   }

   public void u(int var1) {
      String var10000 = Vector2f.e();
      int var3 = 0;
      String var2 = var10000;

      while (var3 < var1) {
         this.x();
         var3++;
         if (var2 != null) {
            break;
         }
      }
   }

   public Vec3 g$Vec3() {
      return new Vec3(this.O, this.q, this.j);
   }

   private void l$V() {
      float var2 = this.p;
      String var10000 = Vector2f.e();
      float var3 = this.l;
      String var1 = var10000;
      float var4 = this.U.player.isSprinting() ? 0.026F : 0.02F;
      float var5 = var2 * var2 + var3 * var3;
      if (var1 == null) {
         if (var5 >= 1.0E-7F) {
            var5 = Mth.sqrt(var5);
            float var9 = var5;
            float var10001 = 1.0F;
            if (var1 == null) {
               if (var5 > 1.0F) {
                  var2 /= var5;
                  var3 /= var5;
               }

               var2 *= var4;
               var3 *= var4;
               var9 = this.t * (float) Math.PI;
               var10001 = 180.0F;
            }

            float var6 = Mth.sin(var9 / var10001);
            float var7 = Mth.cos(this.t * (float) Math.PI / 180.0F);
            this.A += var2 * var7 - var3 * var6;
            this.e += var3 * var7 + var2 * var6;
         }

         this.O = this.O + this.A;
         this.q = this.q + this.B;
         this.j = this.j + this.e;
         this.B -= 0.08;
         this.A *= 0.91;
         this.B *= 0.98;
         this.e *= 0.91;
      }
   }

   public void k(int var1) {
      String var10000 = Vector2f.e();
      int var3 = 0;
      String var2 = var10000;

      while (var3 < var1) {
         this.l$V();
         var3++;
         if (var2 != null) {
            break;
         }
      }
   }

   public Vec3 u$Vec3() {
      return new Vec3(this.O, this.q + this.U.player.getEyeHeight(), this.j);
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
