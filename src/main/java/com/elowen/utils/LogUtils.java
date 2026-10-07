package com.elowen.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
   private static final SimpleDateFormat a;
   private static final BufferedWriter p;
   private static final String[] b = new String[]{"[%s] %s\n", "antibots.log", "yyyy-MM-dd HH:mm:ss"};
   public static void o(String var0) {
      try {
         p.write("[%s] %s\n".formatted(a.format(new Date()), var0));
         p.flush();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public static void W$V() {
      try {
         p.close();
      } catch (IOException var1) {
         throw new RuntimeException(var1);
      }
   }

   static {
      String[] var10 = b;
      a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
      p = new BufferedWriter(new FileWriter(new File(com.elowen.files.FileManager.u, "antibots.log")));
      } catch (IOException var8) {
      throw new RuntimeException(var8);
      }
   }
}
