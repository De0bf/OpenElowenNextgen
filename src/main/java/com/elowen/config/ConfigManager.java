package com.elowen.config;

import com.elowen.Elowen;
import com.elowen.files.FileManager;
import com.elowen.files.impl.InfoFile;
import com.elowen.values.HasValue;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
   public static final String l;
   private static final Logger x;
   private static final String[] P = new String[]{"values.cfg", "killsays.cfg", "spammers.cfg", "invslots.cfg", "modules.cfg"};
   private static final String k;
   private ClientConfig d;
   private static String[] G;
   private static final String[] b = new String[]{"values.cfg", "config", "killsays.cfg", "Failed to create config folder at {}.", "default", "default", "Failed to remove legacy config file {}.", "modules.cfg", ".cfg", "invslots.cfg", "spammers.cfg", "active.txt", "Failed to remove old active.txt marker.", "Removed legacy config file {}.", ".*[/\\\\:*?\"<>|].*", "invslots.cfg", ".cfg"};
   public static File q$File() {
      return new File(FileManager.u, "config");
   }

   public void R$V() {
      String[] var1;
      File var8;
      boolean var11 = false;
      String[] var10000 = g$ArrString();
      File var2 = q$File();
      var1 = var10000;
      var8 = var2;
      label90:
      if (var1 == null) {
         if (!var2.exists()) {
            var8 = var2;
            if (var1 != null) {
               break label90;
            }

            if (!var2.mkdirs()) {
               x.error("Failed to create config folder at {}.", var2.getAbsolutePath());
            }
         }

         String[] var5 = b;
         var8 = new File(var2, "active.txt");
      }

      label83: {
         File var3 = var8;
         boolean var9 = var3.exists();
         if (var1 == null) {
            if (!var9) {
               break label83;
            }

            var9 = var3.delete();
         }

         if (!var9) {
            x.warn("Failed to remove old active.txt marker.");
         }
      }

      String var4;
      label77: {
         label76: {
            var4 = this.I();
            String var10 = var4;
            if (var1 == null) {
               if (var4 != null) {
                  var11 = this.g(var4);
                  if (var1 != null) {
                     break label77;
                  }

                  if (var11) {
                     break label76;
                  }
               }

               String[] var6 = b;
               var10 = "default";
            }

            var4 = var10;
         }

         var11 = this.g(var4);
      }

      if (var1 == null) {
         if (var11) {
            this.d = new ClientConfig(var4);
            this.d.Z();
            if (var1 == null) {
               return;
            }

            HasValue.d(HasValue.x());
         }

         String[] var7 = b;
         this.d = new ClientConfig("default");
         this.d.j$Z();
      }

      this.m("default");
   }

   public boolean L() {
      String[] var10000 = g$ArrString();
      File[] var2 = q$File().listFiles(this::deobfLambda$hasAnyConfig$0);
      String[] var1 = var10000;
      File[] var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      return (boolean)(var1 != null ? var3.length : var3.length > 0);
   }

   public boolean A(String var1) {
      String[] var2 = g$ArrString();
      if (this.Z(var1) && this.g(var1)) {
         ClientConfig var3 = new ClientConfig(var1);
         if (!var3.Z()) {
            return false;
         }

         this.d = var3;
         this.m(var1);
         this.O();
         return true;
      } else {
         return false;
      }
   }

   public boolean B$Z() {
      String[] var1 = g$ArrString();
      if (this.d == null) {
         this.d = new ClientConfig("default");
      }

      if (!this.d.j$Z()) {
         return false;
      }

      this.k();
      this.m(this.d.y$String());
      this.y$V();
      return true;
   }

   public boolean W(String var1) {
      String[] var2 = g$ArrString();
      if (!this.Z(var1)) {
         return false;
      }

      ClientConfig var3 = new ClientConfig(var1);
      if (!var3.j$Z()) {
         return false;
      }

      this.k();
      this.d = var3;
      this.m(var1);
      this.y$V();
      return true;
   }

   public boolean f(String var1) {
      String[] var2 = g$ArrString();
      if (!this.Z(var1)) {
         return false;
      }

      ClientConfig var3 = new ClientConfig(var1);
      if (var3.C().exists() && var3.C().delete()) {
         if (this.d != null && this.d.y$String().equalsIgnoreCase(var1)) {
            this.d = new ClientConfig("default");
            if (this.d.C().exists()) {
               this.d.Z();
            }

            this.m(this.d.y$String());
         }

         this.O();
         return true;
      } else {
         return false;
      }
   }

   private void O() {
      String[] var10000 = g$ArrString();
      FileManager var2 = Elowen.S$Elowen().q$S();
      String[] var1 = var10000;
      FileManager var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return;
         }

         var3 = var2;
      }

      var3.D();
   }

   private void k() {
      String[] var10000 = g$ArrString();
      FileManager var2 = Elowen.S$Elowen().q$S();
      String[] var1 = var10000;
      FileManager var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return;
         }

         var3 = var2;
      }

      var3.N();
   }

   public List o$List() {
      String[] var10000 = g$ArrString();
      ArrayList var2 = new ArrayList();
      String[] var1 = var10000;
      File[] var3 = q$File().listFiles(this::deobfLambda$list$0);
      File[] var8 = var3;
      if (var1 == null) {
         if (var3 == null) {
            return var2;
         }

         Arrays.sort(var3, Comparator.comparing(File::getName));
         var8 = var3;
      }

      File[] var4 = var8;
      int var5 = var4.length;
      int var6 = 0;

      while (var6 < var5) {
         File var7 = var4[var6];
         if (var1 == null) {
            if (var7.isFile()) {
               var2.add(var7.getName().substring(0, var7.getName().length() - 4));
            }

            var6++;
         }

         if (var1 != null) {
            break;
         }
      }

      return var2;
   }

   public ClientConfig g$Y() {
      return this.d;
   }

   public String v$String() {
      String[] var1 = g$ArrString();
      return this.d == null ? "default" : this.d.y$String();
   }

   private boolean d(String var1) {
      String[] var2 = g$ArrString();
      return var1.endsWith(".cfg") && !var1.equalsIgnoreCase("invslots.cfg");
   }

   private boolean g(String var1) {
      return new ClientConfig(var1).C().exists();
   }

   private String I() {
      String[] var10000 = g$ArrString();
      InfoFile var2 = Elowen.S$Elowen().q$S().i();
      String[] var1 = var10000;
      InfoFile var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return null;
         }

         var3 = var2;
      }

      return var3.a$String();
   }

   private void m(String var1) {
      String[] var10000 = g$ArrString();
      InfoFile var3 = Elowen.S$Elowen().q$S().i();
      String[] var2 = var10000;
      InfoFile var4 = var3;
      if (var2 == null) {
         if (var3 == null) {
            return;
         }

         var4 = var3;
      }

      var4.m(var1);
   }

   private boolean Z(String var1) {
      String[] var2 = g$ArrString();
      if (var1 != null && !var1.trim().isEmpty()) {
         String[] var3 = b;
         if (var1.matches(".*[/\\\\:*?\"<>|].*")) {
            return false;
         }

         var3 = b;
         return !(var1 + ".cfg").equalsIgnoreCase("invslots.cfg");
      } else {
         return false;
      }
   }

   private void y$V() {
      String[] var10000 = g$ArrString();
      String[] var2 = P;
      int var3 = var2.length;
      int var4 = 0;
      String[] var1 = var10000;

      while (var4 < var3) {
         String var5 = var2[var4];
         File var6 = new File(FileManager.u, var5);

         try {
            label33: {
               boolean var9 = var6.exists();
               if (var1 == null) {
                  if (!var9) {
                     break label33;
                  }

                  var9 = var6.delete();
               }

               if (var9) {
                  x.info("Removed legacy config file {}.", var5);
               }
            }
         } catch (Exception var8) {
            x.error("Failed to remove legacy config file {}.", var5, var8);
         }

         var4++;
         if (var1 != null) {
            break;
         }
      }
   }

   private boolean deobfLambda$list$0(File var1, String var2) {
      return this.d(var2);
   }

   private boolean deobfLambda$hasAnyConfig$0(File var1, String var2) {
      return this.d(var2);
   }

   static {
      c(null);
      String[] var11 = b;
      k = "invslots.cfg";
      l = var11[4];
      x = LogManager.getLogger(ConfigManager.class);
      String[] var12 = new String[5];
      String[] var9 = b;
      var12[0] = "values.cfg";
      var12[1] = "killsays.cfg";
      var12[2] = "spammers.cfg";
      var12[3] = "invslots.cfg";
      var12[4] = "modules.cfg";
   }

   public static void c(String[] var0) {
      G = var0;
   }

   public static String[] g$ArrString() {
      return G;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
