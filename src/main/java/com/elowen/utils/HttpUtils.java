package com.elowen.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class HttpUtils {
   public static final String H;
   private static final String[] a = new String[]{"User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0", "GET", "GET"};
   public HttpUtils() {
      HttpURLConnection.setFollowRedirects(true);
   }

   public static HttpURLConnection E(String var0, String var1, String var2) throws IOException {
      HttpURLConnection var3 = (HttpURLConnection)new URL(var0).openConnection();
      var3.setRequestMethod(var1);
      var3.setConnectTimeout(5000);
      var3.setReadTimeout(10000);
      var3.setRequestProperty("User-Agent", var2);
      var3.setInstanceFollowRedirects(true);
      var3.setDoOutput(true);
      return var3;
   }

   public static String a(String var0, String var1, String var2) throws IOException {
      String var10000 = Vector2f.e();
      HttpURLConnection var4 = E(var0, var1, var2);
      String var3 = var10000;

      try (BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.getInputStream()))) {
         StringBuilder var6 = new StringBuilder();

         String var7;
         while ((var7 = var5.readLine()) != null) {
            StringBuilder var11 = var6.append(var7).append("\n");
            if (var3 != null) {
               return var11.toString();
            }

            if (var3 != null) {
               break;
            }
         }

         return var6.toString();
      }
   }

   public static String m(String var0, String var1, String var2) throws IOException {
      HttpURLConnection var4 = E(var0, var1, var2);
      String var3 = Vector2f.e();

      try (BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.getInputStream()))) {
         StringBuilder var6 = new StringBuilder();

         String var7;
         while ((var7 = var5.readLine()) != null) {
            StringBuilder var11 = var6.append(var7);
            if (var3 != null) {
               return var11.toString();
            }

            if (var3 != null) {
               break;
            }
         }

         return var6.toString();
      }
   }

   public static String k(String var0) throws IOException {
      String[] var1 = a;
      return a(var0, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
   }

   public static String B(String var0) throws IOException {
      String[] var1 = a;
      return m(var0, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
   }

   public static void b(String var0, File var1) throws IOException {
      String[] var2 = a;
      FileUtils.copyInputStreamToFile(E(var0, "GET", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0").getInputStream(), var1);
   }

   public static String P(InputStream var0) throws IOException {
      StringBuilder var5 = null;
      String var10000 = Vector2f.e();
      BufferedReader var2 = new BufferedReader(new InputStreamReader(var0));
      String var1 = var10000;
      StringBuilder var3 = new StringBuilder();

      while (true) {
         String var4;
         if ((var4 = var2.readLine()) != null) {
            var5 = var3.append(var4);
            if (var1 != null) {
               break;
            }

            if (var1 == null) {
               continue;
            }
         }

         var5 = var3;
         break;
      }

      return var5.toString();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
      H = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
   }
}
