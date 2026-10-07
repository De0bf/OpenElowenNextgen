package com.elowen.files.impl;

import com.elowen.Elowen;
import com.elowen.files.ClientFile;
import com.elowen.modules.impl.misc.KillSay;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class KillSaysFile extends ClientFile {
   private static final String[] a = new String[]{"false", "true", "killsays.cfg"};
   public KillSaysFile() {
      super("killsays.cfg");
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      String var10000 = InvSlotsFile.B$String();
      KillSay var3 = (KillSay)Elowen.S$Elowen().q$ModuleManager().A(KillSay.class);
      String var2 = var10000;
      var3.f(Elowen.S$Elowen().E$C());

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

                     var3.p(var4.substring(0, var5), Boolean.parseBoolean(var6));
                  }

                  if (var2 != null) {
                     continue;
                  }
               }

               var3.p(var4, false);
            }

            if (var2 != null) {
               continue;
            }
         }

         var3.G$V();
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
