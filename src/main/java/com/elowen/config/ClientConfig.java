package com.elowen.config;

import com.elowen.Elowen;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.exceptions.NoSuchValueException;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleManager;
import com.elowen.modules.impl.misc.KillSay;
import com.elowen.modules.impl.misc.Spammer;
import com.elowen.modules.impl.player.InventoryCleaner;
import com.elowen.values.HasValueManager;
import com.elowen.values.Value;
import com.elowen.values.HasValue;
import com.elowen.values.impl.ModeValue;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientConfig {
   private static final Logger h;
   private final String j;
   private final File Z;
   private static final String[] a = new String[]{"Unknown module '{}' in config '{}', skipping.", "state", "Failed to load config '{}'", "values", "Failed to apply value '{}' for module '{}' in config '{}'.", "Saved config '{}' to {}.", "Unknown value type of {}!", "values", "state", "Invalid mode value '{}' for '{}' in config '{}'.", "Unknown value '{}' for module '{}' in config '{}', skipping.", "Unknown value type of {}!", "Failed to save config '{}'", ".cfg"};
   public ClientConfig(String var1) {
      this.j = var1;
      ConfigManager.g$ArrString();
      this.Z = new File(ConfigManager.q$File(), var1 + ".cfg");
      if (!HasValue.x()) {
         ConfigManager.c(new String[3]);
      }
   }

   public String y$String() {
      return this.j;
   }

   public File C() {
      return this.Z;
   }

   public boolean Z() {
      String[] var1 = ConfigManager.g$ArrString();
      if (!this.Z.exists()) {
         return false;
      }

      ModuleManager var2 = Elowen.S$Elowen().q$ModuleManager();
      HasValueManager var3 = Elowen.S$Elowen().E$C();
      Iterator var4 = var2.i().iterator();
      while (var4.hasNext()) {
         Module var5 = (Module)var4.next();
         if (var5.w()) {
            var5.M(false);
         }
      }

      KillSay var17 = (KillSay)var2.A(KillSay.class);
      Spammer var18 = (Spammer)var2.A(Spammer.class);
      var17.f(var3);
      var18.A(var3);

      try {
         String var6 = Files.readString(this.Z.toPath(), StandardCharsets.UTF_8);
         JsonObject var7 = JsonParser.parseString(var6).getAsJsonObject();
         Iterator var8 = var7.entrySet().iterator();
         while (var8.hasNext()) {
            Entry var9 = (Entry)var8.next();
            if (!((JsonElement)var9.getValue()).isJsonObject()) {
               continue;
            }

            JsonObject var10 = ((JsonElement)var9.getValue()).getAsJsonObject();
            Module var11 = this.z(var2, (String)var9.getKey());
            if (var11 == null) {
               h.warn("Unknown module '{}' in config '{}', skipping.", var9.getKey(), this.j);
               continue;
            }

            if (var10.has("state")) {
               var11.M(var10.get("state").getAsBoolean());
            }

            String[] var15 = a;
            if (var10.has("values") && var10.get("values").isJsonObject()) {
               JsonObject var12 = var10.getAsJsonObject("values");
               Iterator var13 = var12.entrySet().iterator();
               while (var13.hasNext()) {
                  Entry var14 = (Entry)var13.next();
                  this.B(var11, (String)var14.getKey(), (JsonElement)var14.getValue(), var3);
               }
            }
         }

         this.W$V();
         return true;
      } catch (Exception var16) {
         h.error("Failed to load config '{}'", this.j, var16);
         return false;
      }
   }

   public boolean j$Z() {
      try {
         ModuleManager var2 = Elowen.S$Elowen().q$ModuleManager();
         HasValueManager var3 = Elowen.S$Elowen().E$C();
         JsonObject var4 = new JsonObject();

         for (Module var6 : (List<Module>)var2.i()) {
            JsonObject var7 = new JsonObject();
            var7.addProperty("state", var6.w());
            JsonObject var8 = new JsonObject();
            int var9 = 0;

            for (Value var11 : (List<Value>)var3.G(var6)) {
               if (var11.A$Q() instanceof InventoryCleaner && com.elowen.files.impl.InvSlotsFile.p(var11)) {
                  continue;
               }

               switch (ClientConfig$1.p[var11.J$H().ordinal()]) {
                  case 1:
                     var8.addProperty(var11.r(), var11.f$O().w());
                     var9 = 1;
                     break;
                  case 2:
                     var8.addProperty(var11.r(), var11.L().o$F());
                     break;
                  case 3:
                     var8.addProperty(var11.r(), var11.N().R$String());
                     break;
                  case 4:
                     var8.addProperty(var11.r(), var11.T$t().C());
                     break;
                  default:
                     h.error("Unknown value type of {}!", var11.r());
                     break;
               }
            }

            if (var9 == 0 && var6 instanceof KillSay) {
               for (String var13 : KillSay.d) {
                  var8.addProperty(var13, false);
               }
            }

            if (var6 instanceof Spammer) {
               for (String var13 : Spammer.i) {
                  var8.addProperty(var13, false);
               }
            }

            var7.add("values", var8);
            var4.add(var6.i(), var7);
         }

         File var14 = this.Z.getParentFile();
         if (var14 != null && !var14.exists()) {
            var14.mkdirs();
         }

         try (BufferedWriter var5 = Files.newBufferedWriter(this.Z.toPath(), StandardCharsets.UTF_8)) {
            var5.write(new GsonBuilder().setPrettyPrinting().create().toJson(var4));
            var5.newLine();
         }

         h.info("Saved config '{}' to {}.", this.j, this.Z.getAbsolutePath());
         return true;
      } catch (Exception var16) {
         h.error("Failed to save config '{}'", this.j, var16);
         return false;
      }
   }

   private void B(Module var1, String var2, JsonElement var3, HasValueManager var4) {
      String[] var5 = ConfigManager.g$ArrString();

      Value var6;
      try {
         var6 = var4.z(var1, var2);
      } catch (NoSuchValueException var9) {
         if (var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isBoolean() && var1 instanceof KillSay) {
            ((KillSay)var1).p(var2, var3.getAsBoolean());
         }

         if (var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isBoolean() && var1 instanceof Spammer) {
            ((Spammer)var1).C(var2, var3.getAsBoolean());
         }

         h.warn("Unknown value '{}' for module '{}' in config '{}', skipping.", var2, var1.i(), this.j);
         return;
      }

      if (!(var6.A$Q() instanceof InventoryCleaner) || !com.elowen.files.impl.InvSlotsFile.p(var6)) {
         try {
            switch (ClientConfig$1.p[var6.J$H().ordinal()]) {
               case 1:
                  var6.f$O().P(var3.getAsBoolean());
                  break;
               case 2:
                  var6.L().I(var3.getAsFloat());
                  break;
               case 3:
                  var6.N().j(var3.getAsString());
                  break;
               case 4:
                  int var7 = this.n(var6.T$t(), var3);
                  if (var7 >= 0) {
                     var6.T$t().y(var7);
                  } else {
                     h.warn("Invalid mode value '{}' for '{}' in config '{}'.", var3, var2, this.j);
                  }
                  break;
               default:
                  h.error("Unknown value type of {}!", var2);
            }
         } catch (Exception var8) {
            h.warn("Failed to apply value '{}' for module '{}' in config '{}'.", var2, var1.i(), this.j);
         }
      }
   }

   private int n(ModeValue var1, JsonElement var2) {
      String[] var3 = ConfigManager.g$ArrString();
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

   private void W$V() {
      KillSay var1 = (KillSay)Elowen.S$Elowen().q$ModuleManager().A(KillSay.class);
      Spammer var2 = (Spammer)Elowen.S$Elowen().q$ModuleManager().A(Spammer.class);
      var1.G$V();
      var2.J$V();
   }

   private Module z(ModuleManager var1, String var2) {
      try {
         return var1.k(var2);
      } catch (NoSuchModuleException var4) {
         return null;
      }
   }

   static {
      h = LogManager.getLogger(ClientConfig.class);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
