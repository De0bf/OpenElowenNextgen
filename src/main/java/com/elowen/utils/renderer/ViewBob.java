package com.elowen.utils.renderer;

import com.elowen.exceptions.NoSuchModuleException;
import org.joml.Matrix4f;

public final class ViewBob {
   private static final Matrix4f r = new Matrix4f();
   private static boolean M = false;

   private ViewBob() {
   }

   public static Matrix4f g$Matrix4f() {
      return !M ? null : new Matrix4f(r);
   }

   public static void b(Matrix4f var0) {
      r.set(var0);
      M = true;
   }

   public static void g$V() {
      r.identity();
      M = true;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
