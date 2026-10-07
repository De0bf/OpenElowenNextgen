package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.files.ClientFile;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleManager;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleFile extends ClientFile {
   private static final Logger l;
   private static final Map b;
   private static final String[] a = new String[]{"rcontrol", "right.control", "mouse", "key.keyboard.", "lcontrol", "mouse", "left.shift", "%s:%s\n", "NONE", "key.keyboard.", "left.control", "Failed to find module {}!", "NONE", "lalt", "rshift", "key.mouse.", "right.alt", "left.alt", "right.shift", "ralt", "key.mouse.", "lshift", "Failed to read line {}!", "bind.cfg"};
   public ModuleFile() {
      super("bind.cfg");
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      String var10000 = InvSlotsFile.B$String();
      ModuleManager var3 = Elowen.S$Elowen().q$ModuleManager();
      String var2 = var10000;

      String var4;
      while ((var4 = var1.readLine()) != null) {
         label60: {
            String[] var5 = var4.split(":", 3);
            String[] var12 = var5;
            if (var2 != null) {
               if (var5.length < 2) {
                  l.error("Failed to read line {}!", var4);
                  if (var2 != null) {
                     break label60;
                  }
               }

               var12 = var5;
            }

            String var6 = var12[0];
            String var7 = var5[1];

            int var8;
            try {
               var8 = Integer.parseInt(var7);
            } catch (NumberFormatException var10) {
               var8 = n(var7);
            }

            try {
               label44: {
                  Module var9 = var3.k(var6);
                  Module var13 = var9;
                  int var10001 = var8;
                  if (var2 != null) {
                     var9.V(var8);
                     if (var5.length != 3) {
                        break label44;
                     }

                     var13 = var9;
                     var10001 = ((Boolean.parseBoolean(var5[2])) ? 1 : 0);
                  }

                  var13.M((boolean)((var10001) != 0));
               }
            } catch (NoSuchModuleException var11) {
               l.error("Failed to find module {}!", var6);
            }
         }

         if (var2 == null) {
            break;
         }
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
      String var10000 = InvSlotsFile.B$String();
      ModuleManager var3 = Elowen.S$Elowen().q$ModuleManager();
      String var2 = var10000;

      for (Module var5 : (java.util.List<Module>)(new ArrayList(var3.i()))) {
         var1.write(String.format("%s:%s\n", var5.i(), M(var5.U$I())));
         if (var2 == null) {
            break;
         }
      }
   }

   public static String M(int var0) {
      String var1 = InvSlotsFile.B$String();
      if (var0 == 0 || var0 == InputConstants.UNKNOWN.getValue()) {
         return "NONE";
      }

      if (var0 < 0) {
         int var10000 = -var0;
         String[] var7 = a;
         return "mouse" + var10000;
      }

      Key var2 = Type.KEYBOARD.getOrCreate(var0);
      if (var2 != null && var2 != InputConstants.UNKNOWN) {
         String var3 = var2.getName();
         if (var3 == null) {
            return "NONE";
         }

         if (var3.startsWith("key.keyboard.")) {
            String[] var6 = a;
            var3 = var3.substring("key.keyboard.".length());
         }

         if (var3.startsWith("key.mouse.")) {
            var3 = var3.substring("key.mouse.".length());
         }

         Iterator var4 = b.entrySet().iterator();
         while (var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            if (((String)var5.getValue()).equals(var3)) {
               return (String)var5.getKey();
            }
         }

         return var3;
      } else {
         return "NONE";
      }
   }

   public static int n(String var0) {
      String var1 = InvSlotsFile.B$String();
      if (var0 == null) {
         return 0;
      }

      String var2 = var0.trim();
      if (!var2.isEmpty() && !var2.equalsIgnoreCase("NONE")) {
         String var3 = var2.toLowerCase(Locale.ROOT);
         if (var3.startsWith("mouse")) {
            try {
               String[] var11 = a;
               int var9 = Integer.parseInt(var3.substring("mouse".length()));
               if (var9 > 0) {
                  return -var9;
               }
            } catch (NumberFormatException var8) {
            }

            return 0;
         } else {
            String var4 = var3;
            Iterator var5 = b.entrySet().iterator();
            while (var5.hasNext()) {
               Entry var6 = (Entry)var5.next();
               if (((String)var6.getKey()).equals(var3)) {
                  var4 = (String)var6.getValue();
               }
            }

            String[] var7 = a;
            Key var10 = InputConstants.getKey("key.keyboard." + var4);
            if (var10 == null || var10 == InputConstants.UNKNOWN) {
               var10 = InputConstants.getKey("key.keyboard." + var3);
            }

            return var10 != null && var10 != InputConstants.UNKNOWN ? var10.getValue() : 0;
         }
      } else {
         return 0;
      }
   }

   static {
      l = LogManager.getLogger(ModuleFile.class);
      String[] var9 = a;
      b = Map.of(
      "rshift",
      "right.shift",
      "lshift",
      "left.shift",
      "rcontrol",
      "right.control",
      "lcontrol",
      "left.control",
      "ralt",
      "right.alt",
      "lalt",
      "left.alt"
      );
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
