package com.elowen.commands;

import com.elowen.commands.impl.CommandToggle;
import com.elowen.commands.impl.CommandConfig;
import com.elowen.commands.impl.CommandFriend;
import com.elowen.commands.impl.CommandBind;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventClientChat;
import com.elowen.utils.ChatUtils;
import com.elowen.values.HasValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandManager {
   public static final String g = ".";
   public final Map y = new HashMap();
   private final List t;
   private static final String[] a = new String[]{"Invalid command.", "Invalid command.", "([^\"]\\S*|\".+?\")\\s*"};
   public CommandManager() {
      String var10000 = Command.V();
      this.t = new ArrayList();
      String var1 = var10000;

      try {
         this.k();
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }

      if (var1 == null) {
         HasValue.d(HasValue.x());
      }
   }

   private void k() {
      this.t(new CommandBind());
      this.t(new CommandToggle());
      this.t(new CommandConfig());
      this.t(new CommandFriend());
   }

   private void t(Command var1) {
      var1.c$V();
      String var10000 = Command.V();
      this.t.add(var1);
      String var2 = var10000;
      this.y.put(var1.I().toLowerCase(), var1);

      for (String var6 : var1.u$ArrString()) {
         this.y.put(var6.toLowerCase(), var1);
         if (var2 == null) {
            break;
         }
      }
   }

   @EventTarget
   public void F(EventClientChat var1) {
      String var2 = Command.V();
      if (var1.n$String().startsWith(".")) {
         var1.c(true);
         String var3 = var1.n$String().substring(".".length());
         String[] var4 = var3.split(" ");
         if (var4.length < 1) {
            ChatUtils.b("Invalid command.");
            return;
         }

         String var5 = var4[0].toLowerCase();
         Command var6 = (Command)this.y.get(var5);
         if (var6 == null) {
            ChatUtils.b("Invalid command.");
            return;
         }

         String[] var7 = this.M(var3);
         String[] var8 = var7.length > 1 ? Arrays.copyOfRange(var7, 1, var7.length) : new String[0];
         int var9 = var3.indexOf(32);
         String var10 = var9 == -1 ? "" : var3.substring(var9 + 1);
         var6.m(var8, var10);
      }
   }

   private String[] M(String var1) {
      String var10000 = Command.V();
      ArrayList<String> var3 = new ArrayList<>();
      Matcher var4 = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(var1);
      String var2 = var10000;

      while (var4.find()) {
         String var5 = var4.group(1);
         boolean var6 = var5.startsWith("\"");
         label29:
         if (var2 != null) {
            if (var6) {
               boolean var7 = var5.endsWith("\"");
               if (var2 == null) {
                  break label29;
               }

               if (var7) {
                  var5 = var5.substring(1, var5.length() - 1);
               }
            }

            var3.add(var5);
         }

         if (var2 == null) {
            break;
         }
      }

      return var3.toArray(new String[0]);
   }

   public CommandManager$Completions W(String var1, int var2) {
      String var3 = Command.V();
      if (var1.startsWith(".") && var2 >= ".".length() && var2 <= var1.length()) {
         String var4 = var1.substring(0, var2);
         int var5 = var4.lastIndexOf(32) + 1;
         if (var5 <= ".".length()) {
            String var10 = var4.substring(".".length()).toLowerCase();
            ArrayList var11 = new ArrayList();
            Iterator var12 = this.t.iterator();
            while (var12.hasNext()) {
               Command var13 = (Command)var12.next();
               if (var13.I().toLowerCase().startsWith(var10)) {
                  var11.add(var13.I());
               }
            }

            return new CommandManager$Completions(".".length(), var11);
         } else {
            String[] var6 = this.M(var4.substring(".".length()));
            if (var6.length == 0) {
               return CommandManager$Completions.y;
            }

            Command var7 = (Command)this.y.get(var6[0].toLowerCase());
            if (var7 == null) {
               return CommandManager$Completions.y;
            }

            ArrayList<String> var8 = new ArrayList<>(Arrays.asList(var6).subList(1, var6.length));
            if (var4.endsWith(" ")) {
               var8.add("");
            }

            String[] var9 = var7.X(var8.toArray(new String[0]));
            return var9 != null && var9.length != 0 ? new CommandManager$Completions(var5, Arrays.asList(var9)) : CommandManager$Completions.y;
         }
      } else {
         return CommandManager$Completions.y;
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
