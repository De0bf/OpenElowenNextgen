package com.elowen.utils;

import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.joml.Vector2f;

public class ProjectionUtils {
   private static final Minecraft o = Minecraft.getInstance();

   public static Vector2f g(double var0, double var2, double var4, float var6) {
      Camera var7 = o.gameRenderer.mainCamera();
      if (!var7.isInitialized()) {
         return v$Vector2f();
      }

      WorldProjector var8 = new WorldProjector(var7, o.getWindow().getGuiScaledWidth(), o.getWindow().getGuiScaledHeight(), ViewBob.g$Matrix4f());
      float[] var9 = var8.p(var0, var2, var4);
      return var9 == null ? v$Vector2f() : new Vector2f(var9[0], var9[1]);
   }

   private static Vector2f v$Vector2f() {
      return new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
