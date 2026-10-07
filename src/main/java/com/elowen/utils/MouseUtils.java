package com.elowen.utils;

import org.lwjgl.sdl.SDLMouse;
import org.lwjgl.system.MemoryStack;

public class MouseUtils {
   public static boolean Y(int var0) {
      String var1 = Vector2f.e();

      int var2 = switch (var0) {
         case 0 -> SDLMouse.SDL_BUTTON_LMASK;
         case 1 -> SDLMouse.SDL_BUTTON_RMASK;
         case 2 -> SDLMouse.SDL_BUTTON_MMASK;
         case 3 -> SDLMouse.SDL_BUTTON_X1MASK;
         case 4 -> SDLMouse.SDL_BUTTON_X2MASK;
         default -> 0;
      };
      if (var2 == 0) {
         return false;
      }

      MemoryStack var3 = MemoryStack.stackPush();

      boolean var4;
      try {
         var4 = (SDLMouse.SDL_GetMouseState(var3.mallocFloat(1), var3.mallocFloat(1)) & var2) != 0;
      } catch (Throwable var7) {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }
         }

         throw var7;
      }

      if (var3 != null) {
         var3.close();
      }

      return var4;
   }

   public static boolean J(int var0, int var1, float var2, float var3, float var4, float var5) {
      String var6 = Vector2f.e();
      return var0 >= var2 && var0 <= var4 && var1 >= var3 && var1 <= var5;
   }

   public static boolean R(int var0, int var1, double var2, double var4, double var6, double var8) {
      String var10 = Vector2f.e();
      return var0 >= var2 && var0 <= var6 && var1 >= var4 && var1 <= var8;
   }

   public static boolean E(int var0, int var1, float var2, float var3, float var4, float var5) {
      String var6 = Vector2f.e();
      return var0 >= var2 && var0 <= var2 + var4 && var1 >= var3 && var1 <= var3 + var5;
   }

   public static boolean s(int var0, int var1, double var2, double var4, double var6, double var8) {
      String var10 = Vector2f.e();
      return var0 >= var2 && var0 <= var2 + var6 && var1 >= var4 && var1 <= var4 + var8;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
