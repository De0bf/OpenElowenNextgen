package com.elowen.utils.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.utils.Vector2f;
import net.minecraft.world.phys.Vec3;

public class RotationUtils$Data {
   private final Vec3 F;
   private final Vec3 f;
   private final double g;
   private final Vector2f E;
   private static final String[] a = new String[]{"RotationUtils.Data(eye=", ", hitVec=", ", distance=", ", rotation="};
   public RotationUtils$Data(Vec3 var1, Vec3 var2, double var3, Vector2f var5) {
      this.F = var1;
      this.f = var2;
      this.g = var3;
      this.E = var5;
   }

   public Vec3 x() {
      return this.F;
   }

   public Vec3 b$Vec3() {
      return this.f;
   }

   public double t() {
      return this.g;
   }

   public Vector2f e() {
      return this.E;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = RotationUtils.T$Z();
      if (var1 == this) {
         return true;
      }

      if (var1 instanceof RotationUtils$Data var3) {
         ;
      }

      return false;
   }

   protected boolean e(Object var1) {
      return var1 instanceof RotationUtils$Data;
   }

   @Override
   public int hashCode() {
      boolean var1;
      int var12;
      int var15;
      label45: {
         byte var2 = 59;
         int var3 = 1;
         boolean var10000 = RotationUtils.T$Z();
         long var4 = Double.doubleToLongBits(this.t());
         var3 = var3 * 59 + (int)(var4 >>> 32 ^ var4);
         var1 = var10000;
         Vec3 var6 = this.x();
         var12 = var3 * 59;
         Vec3 var10001 = var6;
         if (!var1) {
            if (var6 == null) {
               var15 = 43;
               break label45;
            }

            var10001 = var6;
         }

         var15 = var10001.hashCode();
      }

      label39: {
         int var10 = var12 + var15;
         Vec3 var7 = this.b$Vec3();
         var12 = var10 * 59;
         Vec3 var16 = var7;
         if (!var1) {
            if (var7 == null) {
               var15 = 43;
               break label39;
            }

            var16 = var7;
         }

         var15 = var16.hashCode();
      }

      int var11 = var12 + var15;
      Vector2f var8 = this.e();
      var12 = var11 * 59;
      Vector2f var18 = var8;
      if (!var1) {
         if (var8 == null) {
            return var12 + 43;
         }

         var18 = var8;
      }

      return var12 + var18.hashCode();
   }

   @Override
   public String toString() {
      String var10000 = String.valueOf(this.x());
      String var10001 = String.valueOf(this.b$Vec3());
      String var1 = String.valueOf(this.e());
      double var2 = this.t();
      String var4 = var10001;
      String var5 = var10000;
      String[] var6 = a;
      return "RotationUtils.Data(eye=" + var5 + ", hitVec=" + var4 + ", distance=" + var2 + ", rotation=" + var1 + ")";
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
