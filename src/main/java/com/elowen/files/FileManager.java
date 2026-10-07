package com.elowen.files;

import com.elowen.config.ConfigManager;
import com.elowen.files.impl.InfoFile;
import com.elowen.files.impl.KillSaysFile;
import com.elowen.files.impl.InvSlotsFile;
import com.elowen.files.impl.SpammerFile;
import com.elowen.files.impl.ModuleFile;
import com.elowen.files.impl.ValueFile;
import com.elowen.files.impl.FriendFile;
import com.elowen.files.impl.InvManagerFile;
import com.elowen.files.impl.CGuiFile;
import com.elowen.values.HasValue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileManager {
   public static final Logger K;
   public static final File u;
   private final List q = new ArrayList();
   private final ConfigManager S;
   private static final String[] a = new String[]{"invslots.cfg", "modules.cfg", "Failed to create parent folder for ", " in ", "Failed to load file ", "Created file ", "Saved all files to ", "Failed to reload file ", "bind.cfg", "Migrated legacy invslots.cfg into config/invslots.cfg.", "Failed to migrate legacy bind file modules.cfg!", " from ", "Migrated legacy bind file modules.cfg -> bind.cfg.", "Failed to load legacy config file ", "Loaded legacy config file ", "Created file ", "Failed to migrate legacy invslots.cfg!", "bind.cfg.tmp", "Elowen-NextGen", "Failed to create parent folder for ", "Failed to rename {} to bind.cfg!", " in ", " from ", "bind.cfg", "Renamed {} to bind.cfg."};
   public FileManager() {
      boolean var10000 = ClientFile.D();
      this.S = new ConfigManager();
      boolean var1 = var10000;
      this.q.add(new ModuleFile());
      this.q.add(new InvSlotsFile());
      this.q.add(new CGuiFile());
      this.q.add(new FriendFile());
      this.q.add(new InfoFile());
      if (!var1) {
         HasValue.d(HasValue.x());
      }
   }

   public void U$V() {
      boolean var1 = ClientFile.D();
      if (!this.S.L()) {
         this.m(new KillSaysFile());
         this.m(new SpammerFile());
         this.m(new ValueFile());
      }

      this.M();
      this.v$V();
      this.I();
      Iterator var2 = this.q.iterator();
      while (var2.hasNext()) {
         ClientFile var3 = (ClientFile)var2.next();
         File var4 = var3.H();

         try {
            File var5 = var4.getParentFile();
            if (var5 != null && !var5.exists() && !var5.mkdirs()) {
               K.error("Failed to create parent folder for " + var4.getName() + "!");
            }

            if (!var4.exists() && var4.createNewFile()) {
               K.info("Created file " + var4.getName() + " in " + u.getAbsolutePath() + "!");
               this.U(var3);
            }

            BufferedReader var6 = new BufferedReader(new InputStreamReader(Files.newInputStream(var4.toPath()), StandardCharsets.UTF_8));
            var3.N(var6);
            var6.close();
         } catch (IOException var8) {
            Logger var10000 = K;
            String var10001 = var4.getName();
            String var10002 = u.getAbsolutePath();
            String[] var7 = a;
            var10000.error("Failed to load file " + var10001 + " from " + var10002 + "!", var8);
            this.U(var3);
         }
      }

      this.S.R$V();
      if (HasValue.X$Z()) {
         ClientFile.K(false);
      }
   }

   private void m(ClientFile var1) {
      File var2 = var1.H();
      if (var2.exists()) {
         try {
            BufferedReader var3 = new BufferedReader(new InputStreamReader(Files.newInputStream(var2.toPath()), StandardCharsets.UTF_8));
            var1.N(var3);
            var3.close();
            Logger var10000 = K;
            String var10001 = var2.getName();
            String[] var4 = a;
            var10000.info("Loaded legacy config file " + var10001 + ".");
         } catch (IOException var5) {
            K.error("Failed to load legacy config file " + var2.getName() + "!", var5);
         }
      }
   }

   private void M() {
      java.io.File var14 = null;
      boolean var10000 = ClientFile.R$Z();
      File[] var2 = u.listFiles();
      boolean var1 = var10000;
      File[] var12 = var2;
      if (!var1) {
         if (var2 == null) {
            return;
         }

         var12 = var2;
      }

      File[] var3 = var12;
      int var4 = var3.length;
      int var5 = 0;

      while (var5 < var4) {
         File var6 = var3[var5];
         if (!var1) {
            label72:
            if (var6.isFile()) {
               label100: {
                  var10000 = var6.getName().equalsIgnoreCase("bind.cfg");
                  if (!var1) {
                     if (!var10000) {
                        break label72;
                     }

                     var14 = var6;
                     if (var1) {
                        break label100;
                     }

                     var10000 = var6.getName().equals("bind.cfg");
                  }

                  if (var10000) {
                     break label72;
                  }

                  var14 = new File(u, "bind.cfg.tmp");
               }

               File var7 = var14;
               String[] var10 = a;
               File var8 = new File(u, "bind.cfg");

               try {
                  var10000 = var6.renameTo(var7);
                  label78:
                  if (!var1) {
                     if (var10000) {
                        var10000 = var7.renameTo(var8);
                        if (var1) {
                           break label78;
                        }

                        if (var10000) {
                           K.info("Renamed {} to bind.cfg.", var6.getName());
                           if (!var1) {
                              break label78;
                           }
                        }
                     }

                     var7.delete();
                  }
               } catch (Exception var11) {
                  K.error("Failed to rename {} to bind.cfg!", var6.getName(), var11);
               }
            }

            var5++;
         }

         if (var1) {
            break;
         }
      }
   }

   private void v$V() {
      boolean var10000 = ClientFile.D();
      String[] var5 = a;
      File var2 = new File(u, "modules.cfg");
      boolean var1 = var10000;
      File var3 = new File(u, "bind.cfg");
      var10000 = var2.exists();
      if (var1) {
         if (!var10000) {
            return;
         }

         var10000 = var3.exists();
      }

      if (!var10000) {
         try {
            BufferedReader var4 = new BufferedReader(new InputStreamReader(Files.newInputStream(var2.toPath()), StandardCharsets.UTF_8));
            new ModuleFile().N(var4);
            var4.close();
            var5 = a;
            K.info("Migrated legacy bind file modules.cfg -> bind.cfg.");
         } catch (IOException var6) {
            K.error("Failed to migrate legacy bind file modules.cfg!", var6);
         }
      }
   }

   private void I() {
      String[] var3 = a;
      File var1 = new File(u, "invslots.cfg");
      if (var1.exists()) {
         try {
            BufferedReader var2 = new BufferedReader(new InputStreamReader(Files.newInputStream(var1.toPath()), StandardCharsets.UTF_8));
            new InvManagerFile().N(var2);
            var2.close();
            var3 = a;
            K.info("Migrated legacy invslots.cfg into config/invslots.cfg.");
         } catch (IOException var4) {
            K.error("Failed to migrate legacy invslots.cfg!", var4);
         }
      }
   }

   public void X$V() {
      boolean var10000 = ClientFile.R$Z();
      boolean var2 = this.S.B$Z();
      boolean var1 = var10000;
      Iterator var3 = this.q.iterator();

      while (true) {
         if (var3.hasNext()) {
            ClientFile var4 = (ClientFile)var3.next();
            if (var1) {
               break;
            }

            if (!var1) {
               if (var2 && var4 instanceof InvSlotsFile && !var1) {
                  continue;
               }

               this.U(var4);
            }

            if (!var1) {
               continue;
            }
         }

         K.info("Saved all files to " + u.getAbsolutePath() + "!");
         break;
      }
   }

   private void U(ClientFile var1) {
      boolean var10000 = ClientFile.D();
      File var3 = var1.H();
      boolean var2 = var10000;

      try {
         label66: {
            File var4 = var3.getParentFile();
            File var7 = var4;
            if (var2) {
               if (var4 != null) {
                  var10000 = var4.exists();
                  if (!var2) {
                     break label66;
                  }

                  if (!var10000) {
                     var10000 = var4.mkdirs();
                     if (!var2) {
                        break label66;
                     }

                     if (!var10000) {
                        K.error("Failed to create parent folder for " + var3.getName() + "!");
                     }
                  }
               }

               var7 = var3;
            }

            var10000 = var7.exists();
         }

         label55: {
            if (var2) {
               if (var10000) {
                  break label55;
               }

               var10000 = var3.createNewFile();
            }

            if (var10000) {
               K.info("Created file " + var3.getName() + " in " + u.getAbsolutePath() + "!");
            }
         }

         BufferedWriter var5 = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(var3.toPath()), StandardCharsets.UTF_8));
         var1.X(var5);
         var5.flush();
         var5.close();
      } catch (IOException var6) {
         throw new RuntimeException(var6);
      }
   }

   public void D() {
      boolean var10000 = ClientFile.D();
      InvSlotsFile var2 = this.t();
      boolean var1 = var10000;
      InvSlotsFile var7 = var2;
      if (var1) {
         if (var2 == null) {
            return;
         }

         var7 = var2;
      }

      File var3 = var7.H();
      if (var3.exists()) {
         try {
            BufferedReader var4 = new BufferedReader(new InputStreamReader(Files.newInputStream(var3.toPath()), StandardCharsets.UTF_8));
            var2.N(var4);
            var4.close();
         } catch (IOException var6) {
            Logger var8 = K;
            String var10001 = var3.getName();
            String var10002 = u.getAbsolutePath();
            String[] var5 = a;
            var8.error("Failed to reload file " + var10001 + " from " + var10002 + "!", var6);
         }
      }
   }

   public void N() {
      InvSlotsFile var1 = this.t();
      if (var1 != null) {
         this.U(var1);
      }
   }

   public InvSlotsFile t() {
      ClientFile var4 = null;
      boolean var10000 = ClientFile.D();
      Iterator var2 = this.q.iterator();
      boolean var1 = var10000;

      while (true) {
         if (var2.hasNext()) {
            ClientFile var3 = (ClientFile)var2.next();
            var4 = var3;
            if (!var1) {
               break;
            }

            if (var3 instanceof InvSlotsFile) {
               var4 = var3;
               break;
            }

            if (var1) {
               continue;
            }
         }

         return null;
      }

      return (InvSlotsFile)var4;
   }

   public InfoFile i() {
      ClientFile var4 = null;
      boolean var10000 = ClientFile.D();
      Iterator var2 = this.q.iterator();
      boolean var1 = var10000;

      while (true) {
         if (var2.hasNext()) {
            ClientFile var3 = (ClientFile)var2.next();
            var4 = var3;
            if (!var1) {
               break;
            }

            if (var3 instanceof InfoFile) {
               var4 = var3;
               break;
            }

            if (var1) {
               continue;
            }
         }

         return null;
      }

      return (InfoFile)var4;
   }

   public ConfigManager E$E() {
      return this.S;
   }

   static {
      K = LogManager.getLogger(FileManager.class);
      u = FabricLoader.getInstance().getGameDir().resolve("Elowen-NextGen").toFile();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
