package com.elowen.commands.impl;

import com.elowen.Elowen;
import com.elowen.commands.Command;
import com.elowen.commands.CommandInfo;
import com.elowen.config.ConfigManager;
import java.io.IOException;
import java.util.List;

@CommandInfo(V = "config", Z = "Manage client configs.", C = {"conf", "c", "cf"})
public class CommandConfig extends Command {
   private static final String[] a = new String[]{"reload", "Failed to open config folder.", "Saved config ", "Configs: ", "remove", "remove", "Saved config ", "load", "list", "list", "Usage: .config [reload|list|save [name]|load <name>|remove <name>]", "Failed to save config.", "Loaded config ", "No config saved yet.", "No configs found.", "load", "Removed config ", "save", "reload", "save", "explorer ", ", ", "Failed to load config ", "Failed to save config ", "Reloaded config ", "Failed to remove config "};
   @Override
   public void l(String[] var1) {
      boolean var2 = CommandBind.q$Z();
      if (var1.length == 0) {
         try {
            Runtime var10000 = Runtime.getRuntime();
            String var10001 = ConfigManager.q$File().getAbsolutePath();
            String[] var10 = a;
            var10000.exec("explorer " + var10001);
         } catch (IOException var8) {
            String[] var7 = a;
            com.elowen.utils.ChatUtils.b("Failed to open config folder.");
         }
      } else {
         ConfigManager var3 = Elowen.S$Elowen().q$S().E$E();
         String var4 = var1[0].toLowerCase();
         byte var5 = -1;
         switch (var4.hashCode()) {
            case -934641255:
               if (!var4.equals("reload")) {
                  break;
               }

               var5 = 0;
            case 3322014:
               if (!var4.equals("list")) {
                  break;
               }

               var5 = 1;
            case 3327206:
               if (!var4.equals("load")) {
                  break;
               }

               var5 = 2;
            case 3522941:
               if (!var4.equals("save")) {
                  break;
               }

               var5 = 3;
            case -934610812:
               if (var4.equals("remove")) {
                  var5 = 4;
               }
         }

         switch (var5) {
            case 0:
               String var6 = var3.v$String();
               if (var3.A(var6)) {
                  com.elowen.utils.ChatUtils.b("Reloaded config " + var6 + ".");
               } else {
                  com.elowen.utils.ChatUtils.b("No config saved yet.");
               }
               break;
            case 1:
               List var9 = var3.o$List();
               if (var9.isEmpty()) {
                  com.elowen.utils.ChatUtils.b("No configs found.");
               } else {
                  com.elowen.utils.ChatUtils.b("Configs: " + String.join(", ", var9));
               }
               break;
            case 2:
               if (var1.length < 2) {
                  this.g$V();
               } else if (var3.A(var1[1])) {
                  com.elowen.utils.ChatUtils.b("Loaded config " + var1[1] + ".");
               } else {
                  com.elowen.utils.ChatUtils.b("Failed to load config " + var1[1] + ".");
               }
               break;
            case 3:
               if (var1.length >= 2) {
                  if (var3.W(var1[1])) {
                     com.elowen.utils.ChatUtils.b("Saved config " + var1[1] + ".");
                  } else {
                     com.elowen.utils.ChatUtils.b("Failed to save config " + var1[1] + ".");
                  }
               } else if (var3.B$Z()) {
                  com.elowen.utils.ChatUtils.b("Saved config " + var3.v$String() + ".");
               } else {
                  com.elowen.utils.ChatUtils.b("Failed to save config.");
               }
               break;
            case 4:
               if (var1.length < 2) {
                  this.g$V();
               } else if (var3.f(var1[1])) {
                  com.elowen.utils.ChatUtils.b("Removed config " + var1[1] + ".");
               } else {
                  com.elowen.utils.ChatUtils.b("Failed to remove config " + var1[1] + ".");
               }
               break;
            default:
               this.g$V();
         }
      }
   }

   private void g$V() {
      com.elowen.utils.ChatUtils.b("Usage: .config [reload|list|save [name]|load <name>|remove <name>]");
   }

   @Override
   public String[] X(String[] var1) {
      String var6;
      label52: {
         boolean var2 = CommandBind.P();
         int var10000 = var1.length;
         byte var10001 = 1;
         if (!var2) {
            if (var10000 == 1) {
               String var5 = var1[0].toLowerCase();
               String[] var4 = a;
               return List.of("reload", "list", "load", "save", "remove").stream().filter(s -> deobfLambda$onTab$0(var5, s)).toArray(CommandConfig::deobfLambda$onTab$1);
            }

            var10000 = var1.length;
            if (var2) {
               return new String[var10000];
            }

            var10001 = 2;
         }

         label60: {
            if (var10000 == var10001) {
               var6 = var1[0];
               if (var2) {
                  break label52;
               }

               if (var6.equalsIgnoreCase("load")) {
                  break label60;
               }

               byte var7 = (((byte)((var1[0].equalsIgnoreCase("remove")) ? 1 : 0)));
               if (var2) {
                  return new String[var7];
               }

               if (var7 != 0) {
                  break label60;
               }
            }

            return new String[0];
         }

         var6 = var1[1].toLowerCase();
      }

      String var3 = var6;
      return ((List<String>)Elowen.S$Elowen().q$S().E$E().o$List()).stream().filter(s -> deobfLambda$onTab$2(var3, s)).toArray(CommandConfig::deobfLambda$onTab$3);
   }

   private static String[] deobfLambda$onTab$3(int var0) {
      return new String[var0];
   }

   private static boolean deobfLambda$onTab$2(String var0, String var1) {
      return var1.toLowerCase().startsWith(var0);
   }

   private static String[] deobfLambda$onTab$1(int var0) {
      return new String[var0];
   }

   private static boolean deobfLambda$onTab$0(String var0, String var1) {
      return var1.startsWith(var0);
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
