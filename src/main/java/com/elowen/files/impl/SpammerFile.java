package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.files.ClientFile;
import com.elowen.modules.impl.misc.Spammer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SpammerFile extends ClientFile {
   private static final String[] a = new String[]{"true", "spammers.cfg", "false"};
   public SpammerFile() {
      super("spammers.cfg");
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      String var10000 = InvSlotsFile.B$String();
      Spammer var3 = (Spammer)Elowen.S$Elowen().q$ModuleManager().A(Spammer.class);
      var3.A(Elowen.S$Elowen().E$C());
      String var2 = var10000;

      while (true) {
         String var4;
         if ((var4 = var1.readLine()) != null) {
            int var5 = var4.lastIndexOf(58);
            if (var2 == null) {
               break;
            }

            if (var2 != null) {
               label39:
               if (var5 > 0) {
                  String var6 = var4.substring(var5 + 1);
                  if (var2 != null) {
                     if (!var6.equals("true") && !var6.equals("false")) {
                        break label39;
                     }

                     var3.C(var4.substring(0, var5), Boolean.parseBoolean(var6));
                  }

                  if (var2 != null) {
                     continue;
                  }
               }

               var3.C(var4, false);
            }

            if (var2 != null) {
               continue;
            }
         }

         var3.J$V();
         break;
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
   }

   private static IOException a(IOException var0) {
      return var0;
   }

   static {
   }
}
