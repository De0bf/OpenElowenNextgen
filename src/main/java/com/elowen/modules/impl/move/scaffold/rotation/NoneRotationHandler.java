package com.elowen.modules.impl.move.scaffold.rotation;

import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import com.elowen.values.HasValue;

public class NoneRotationHandler implements RotationHandler {
   private static HasValue[] Q;

   @Override
   public Vector2f Z(Vector2f var1, Vector2f var2, float var3, Scaffold var4) {
      return new Vector2f(var4.c$F(), var4.a$F());
   }

   public static void W(HasValue[] var0) {
      Q = var0;
   }

   public static HasValue[] i() {
      return Q;
   }

   static {
      if (i() != null) {
         W(new HasValue[5]);
      }
   }
}
