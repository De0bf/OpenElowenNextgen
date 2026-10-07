package com.elowen.files.impl;

import com.elowen.files.ClientFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InfoFile extends ClientFile {
   private static final Logger M;
   private String N;
   private static final String[] a = new String[]{"\\\"", "%s:\"%s\"%s", "default", ",default", "ActiveConfig:", "info.cfg", "\\\\", "ActiveConfig", "Failed to parse line: ", "^\\w+:\"((?:\\\\.|[^\"\\\\])*)\"(,default)?$"};
   public InfoFile() {
      super("info.cfg");
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      String var2 = InvSlotsFile.B$String();

      String var3;
      while ((var3 = var1.readLine()) != null) {
         var3 = var3.trim();
         if (!var3.isEmpty() && var3.startsWith("ActiveConfig:")) {
            InfoFile$ParseResult var4 = this.o(var3);
            if (var4 != null) {
               this.N = var4.X;
            }
            break;
         }
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
      String var2 = InvSlotsFile.B$String();
      var1.write(this.M("ActiveConfig", this.N == null ? "default" : this.N, false));
      var1.newLine();
   }

   public String a$String() {
      return this.N;
   }

   public void m(String var1) {
      this.N = var1;
   }

   private InfoFile$ParseResult o(String var1) {
      String var10000 = InvSlotsFile.B$String();
      String[] var6 = a;
      Pattern var3 = Pattern.compile("^\\w+:\"((?:\\\\.|[^\"\\\\])*)\"(,default)?$");
      Matcher var4 = var3.matcher(var1);
      String var2 = var10000;
      if (var2 != null) {
         if (var4.find()) {
            InfoFile$ParseResult var5 = new InfoFile$ParseResult();
            var5.X = this.g(var4.group(1));
            var5.b = var4.group(2) != null;
            return var5;
         }

         M.warn("Failed to parse line: " + var1);
      }

      return null;
   }

   private String M(String var1, String var2, boolean var3) {
      String var10000 = InvSlotsFile.B$String();
      String[] var6 = a;
      String var5 = var2.replace("\\", "\\\\").replace("\"", "\\\"");
      String var4 = var10000;
      Object[] var10001 = new Object[]{var1, null, null};
      Object[] var10002 = var10001;
      byte var10003 = 1;
      String var10004 = var5;
      if (var4 != null) {
         var10001[1] = var5;
         var10002 = var10001;
         var10003 = 2;
         var10004 = var3 ? ",default" : "";
      }

      var10002[var10003] = var10004;
      return String.format("%s:\"%s\"%s", var10001);
   }

   private String g(String var1) {
      int var8 = 0;
      String var10000 = InvSlotsFile.B$String();
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;
      String var2 = var10000;

      while (true) {
         if (var4 < var1.length()) {
            var10000 = var1;
            if (var2 == null) {
               break;
            }

            label56: {
               char var5;
               label66: {
                  label67: {
                     var5 = var1.charAt(var4);
                     var8 = var5;
                     int var10001 = 92;
                     if (var2 != null) {
                        if (var5 != '\\') {
                           break label66;
                        }

                        var8 = var4 + 1;
                        if (var2 == null) {
                           break label67;
                        }

                        var10001 = var1.length();
                     }

                     if (var8 >= var10001) {
                        break label66;
                     }

                     var8 = var1.charAt(var4 + 1);
                  }

                  int var6 = var8;
                  if (var2 != null) {
                     if (var6 != 92 && var6 != 34) {
                        break label66;
                     }

                     var3.append((char)var6);
                     var4++;
                  }

                  if (var2 != null) {
                     break label56;
                  }
               }

               var3.append(var5);
            }

            var4++;
            if (var2 != null) {
               continue;
            }
         }

         var10000 = var3.toString();
         break;
      }

      return var10000;
   }

   static {
      M = LogManager.getLogger(InfoFile.class);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
