package com.elowen.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChatUtils {
   private static final String f;

   public static void f(Component var0) {
      String var10000 = Vector2f.e();
      Minecraft var2 = Minecraft.getInstance();
      String var1 = var10000;
      if (var1 == null) {
         if (!RenderSystem.isOnRenderThread()) {
            var2.execute(() -> deobfLambda$component$0(var2, var0));
            return;
         }

         var2.gui.hud.getChat().addClientSystemMessage(var0);
      }
   }

   public static void b(String var0) {
      G(true, var0);
   }

   public static void G(boolean var0, String var1) {
      f(Component.nullToEmpty((var0 ? f : "") + var1));
   }

   private static void deobfLambda$component$0(Minecraft var0, Component var1) {
      var0.gui.hud.getChat().addClientSystemMessage(var1);
   }

   static {
      f = "§7[§bE§7] ";
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
