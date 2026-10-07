package com.elowen.modules.impl.move.scaffold.rotation;

import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Vector2f;
import com.elowen.utils.rotation.RotationUtils;
import java.util.Random;

public class LinearRotationHandler implements RotationHandler {
   private final Random T = new Random();

   @Override
   public Vector2f Z(Vector2f var1, Vector2f var2, float var3, Scaffold var4) {
      float var5 = RotationUtils.i(var3, var1.H, var2.H);
      float var6 = RotationUtils.q(var1.E, var2.E, var3);
      return new Vector2f(var5, var6);
   }
}
