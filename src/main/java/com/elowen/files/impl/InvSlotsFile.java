package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.files.ClientFile;
import com.elowen.modules.impl.player.InventoryCleaner;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.impl.ModeValue;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvSlotsFile extends ClientFile {
   private static final Logger e;
   private static String K;
   private static final String[] a = new String[]{"Keep ", "Unknown value '{}' in config/invslots.cfg, skipping.", "InventoryManager", "config/invslots.cfg", " Size", "Unknown value type of {}!", "InventoryManager", "Failed to load config/invslots.cfg!", "values", " Slot", "Switch ", "Unknown value type of {}!", "values", "Failed to apply value '{}' from config/invslots.cfg.", "Max "};
   public InvSlotsFile() {
      super("config/invslots.cfg");
   }

   public static boolean p(Value var0) {
      String var10000 = B$String();
      String var2 = var0.r();
      String var1 = var10000;
      boolean var3 = var2.endsWith(" Slot");
      if (var1 != null) {
         if (!var3) {
            boolean var4 = var2.startsWith("Switch ");
            if (var1 == null) {
               return var4;
            }

            if (!var4) {
               boolean var5 = var2.startsWith("Keep ");
               if (var1 == null) {
                  return var5;
               }

               if (!var5) {
                  boolean var6 = var2.startsWith("Max ");
                  if (var1 != null) {
                     if (!var6) {
                        return false;
                     }

                     var6 = var2.endsWith(" Size");
                  }

                  if (var1 == null) {
                     return var6;
                  }

                  if (!var6) {
                     return false;
                  }
               }
            }
         }

         var3 = true;
      }

      return var3;
   }

   private InventoryCleaner O() {
      return (InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class);
   }

   @Override
   public void N(BufferedReader param1) throws IOException {
      InventoryCleaner var3 = this.O();
      com.elowen.values.HasValueManager var4 = Elowen.S$Elowen().E$C();

      try {
         JsonObject var5 = JsonParser.parseReader(param1).getAsJsonObject();
         JsonObject var6 = var5.getAsJsonObject("InventoryManager");
         if (var6 == null) {
            return;
         }

         if (!var6.has("values") || !var6.get("values").isJsonObject()) {
            return;
         }

         Iterator var7 = var6.getAsJsonObject("values").entrySet().iterator();

         while (var7.hasNext()) {
            java.util.Map.Entry var8 = (java.util.Map.Entry)var7.next();

            try {
               Value var9 = var4.z(var3, (String)var8.getKey());
               if (p(var9)) {
                  this.A(var9, (JsonElement)var8.getValue());
               }
            } catch (Exception var10) {
               e.warn("Unknown value '{}' in config/invslots.cfg, skipping.", var8.getKey());
            }
         }
      } catch (Exception var11) {
         e.error("Failed to load config/invslots.cfg!", var11);
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
      InventoryCleaner var3 = this.O();
      com.elowen.values.HasValueManager var4 = Elowen.S$Elowen().E$C();
      String var10000 = B$String();
      JsonObject var5 = new JsonObject();
      String var2 = var10000;
      JsonObject var6 = new JsonObject();
      JsonObject var7 = new JsonObject();
      Iterator var8 = var4.G(var3).iterator();

      while (true) {
         if (var8.hasNext()) {
            Value var9 = (Value)var8.next();
            if (var2 == null) {
               break;
            }

            int var11 = ((p(var9)) ? 1 : 0);
            if (var2 != null) {
               if (var11 == 0) {
                  if (var2 != null) {
                     continue;
                  }

                  HasValue.d(HasValue.x());
               }

               var11 = InvSlotsFile$1.j[var9.J$H().ordinal()];
            }

            switch (var11) {
               case 1:
                  var7.addProperty(var9.r(), var9.f$O().w());
                  if (var2 != null) {
                     break;
                  }
               case 2:
                  var7.addProperty(var9.r(), var9.L().o$F());
                  if (var2 != null) {
                     break;
                  }
               case 3:
                  var7.addProperty(var9.r(), var9.N().R$String());
                  if (var2 != null) {
                     break;
                  }
               case 4:
                  var7.addProperty(var9.r(), var9.T$t().C());
                  if (var2 != null) {
                     break;
                  }
               default:
                  e.error("Unknown value type of {}!", var9.r());
            }

            if (var2 != null) {
               continue;
            }
         }

         String[] var10 = a;
         var6.add("values", var7);
         var5.add("InventoryManager", var6);
         var1.write(new GsonBuilder().setPrettyPrinting().create().toJson(var5));
         var1.newLine();
         break;
      }
   }

   private void A(Value var1, JsonElement var2) {
      String var3 = B$String();

      try {
         switch (InvSlotsFile$1.j[var1.J$H().ordinal()]) {
            case 1:
               var1.f$O().P(var2.getAsBoolean());
               break;
            case 2:
               var1.L().I(var2.getAsFloat());
               break;
            case 3:
               var1.N().j(var2.getAsString());
               break;
            case 4:
               int var4 = this.x(var1.T$t(), var2);
               if (var4 < 0) {
                  break;
               }

               var1.T$t().y(var4);
               break;
            default:
               e.error("Unknown value type of {}!", var1.r());
         }
      } catch (Exception var5) {
         e.warn("Failed to apply value '{}' from config/invslots.cfg.", var1.r());
      }
   }

   private int x(ModeValue var1, JsonElement var2) {
      String var3 = B$String();
      if (var2.isJsonPrimitive() && var2.getAsJsonPrimitive().isNumber()) {
         int var6 = var2.getAsInt();
         return var6 >= 0 && var6 < var1.T$ArrString().length ? var6 : -1;
      }

      String var4 = var2.getAsString();
      int var5 = 0;
      while (var5 < var1.T$ArrString().length) {
         if (var1.T$ArrString()[var5].equalsIgnoreCase(var4)) {
            return var5;
         }

         var5++;
      }

      return -1;
   }

   static {
      G("OAn7kb");
      e = LogManager.getLogger(InvSlotsFile.class);
   }

   public static void G(String var0) {
      K = var0;
   }

   public static String B$String() {
      return K;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
