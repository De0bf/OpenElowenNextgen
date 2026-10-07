package com.elowen.modules.impl.move.scaffold.rotation;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;

public class CurveRotationHandler implements RotationHandler {
   private static final float U = 0.85F;
   private static final float J = 5.0F;
   private float S = 0.0F;
   private boolean k = true;

   @Override
   public Vector2f Z(Vector2f var1, Vector2f var2, float var3, Scaffold var4) {
      HasValue[] var5 = com.elowen.modules.impl.move.scaffold.rotation.NoneRotationHandler.i();
      if (this.k) {
         this.S = var3;
         this.k = false;
      }

      if (this.S <= 0.0F) {
         this.S = var3;
      }

      float var6 = RotationUtils.e(var1.H, var2.H);
      float var7 = var2.E - var1.E;
      if (Math.abs(var6) < 0.5F && Math.abs(var7) < 0.5F) {
         this.k = true;
         return new Vector2f(var2.H, var2.E);
      }

      float var8 = Math.max(-this.S, Math.min(this.S, var6));
      float var9 = Math.max(-this.S, Math.min(this.S, var7));
      float var10 = var1.H + var8;
      float var11 = var1.E + var9;
      this.S = Math.max(5.0F, this.S * 0.85F);
      Vector2f var10000 = new Vector2f(var10, var11);
      if (HasValue.X$Z()) {
         com.elowen.modules.impl.move.scaffold.rotation.NoneRotationHandler.W(new HasValue[5]);
      }

      return var10000;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
