package com.elowen.files;

import com.elowen.exceptions.NoSuchModuleException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public abstract class ClientFile {
   private final String q;
   private final File z;
   private static boolean U;

   public ClientFile(String var1) {
      this.q = var1;
      this.z = new File(FileManager.u, var1);
   }

   public abstract void N(BufferedReader var1) throws IOException;

   public abstract void X(BufferedWriter var1) throws IOException;

   public String Y() {
      return this.q;
   }

   public File H() {
      return this.z;
   }

   public static void K(boolean var0) {
      U = var0;
   }

   public static boolean D() {
      return U;
   }

   public static boolean R$Z() {
      return !D();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      if (R$Z()) {
         K(true);
      }
   }
}
