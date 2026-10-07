package com.elowen.commands.impl;

import com.elowen.Elowen;
import com.elowen.commands.Command;
import com.elowen.commands.CommandInfo;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Module;
import com.elowen.utils.ChatUtils;

@CommandInfo(V = "toggle", Z = "Toggle a module", C = "t")
public class CommandToggle extends Command {
   private static final String[] a = new String[]{"Invalid module.", "Invalid module."};
   @Override
   public void l(String[] var1) {
      boolean var2 = CommandBind.q$Z();
      if (var1.length == 1) {
         String var3 = var1[0];

         try {
            Module var4 = Elowen.S$Elowen().q$ModuleManager().k(var3);
            if (var4 != null) {
               var4.R$V();
            }

            ChatUtils.b("Invalid module.");
         } catch (NoSuchModuleException var5) {
            ChatUtils.b("Invalid module.");
         }
      }
   }

   @Override
   public String[] X(String[] var1) {
      boolean var2 = CommandBind.P();
      String[] var10000 = var1;
      if (!var2) {
         if (var1.length > 1) {
            return new String[0];
         }

         var10000 = ((java.util.List<Module>)Elowen.S$Elowen().q$ModuleManager().i()).stream().map(m -> ((Module)m).i()).filter(s -> deobfLambda$onTab$0(var1, s)).toArray(CommandToggle::deobfLambda$onTab$1);
      }

      return var10000;
   }

   private static String[] deobfLambda$onTab$1(int var0) {
      return new String[var0];
   }

   private static boolean deobfLambda$onTab$0(String[] var0, String var1) {
      boolean var2 = CommandBind.P();
      String var10000 = var1.toLowerCase();
      String[] var10001 = var0;
      if (!var2) {
         if (var0.length == 0) {
            return var10000.startsWith("");
         }

         var10001 = var0;
      }

      return var10000.startsWith(var10001[0].toLowerCase());
   }

   private static NoSuchModuleException b(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
