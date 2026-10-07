package com.elowen.files.impl;

import com.elowen.files.ClientFile;
import com.elowen.utils.FriendManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public class FriendFile extends ClientFile {
   private static final String a;

   public FriendFile() {
      super(a);
   }

   @Override
   public void N(BufferedReader var1) throws IOException {
      String var2 = InvSlotsFile.B$String();
      String var3;
      if ((var3 = var1.readLine()) != null) {
         FriendManager.V(var3);
      }
   }

   @Override
   public void X(BufferedWriter var1) throws IOException {
      String var10000 = InvSlotsFile.B$String();
      Iterator var3 = FriendManager.Q().iterator();
      String var2 = var10000;

      while (var3.hasNext()) {
         String var4 = (String)var3.next();
         var1.write(var4 + "\n");
         if (var2 == null) {
            break;
         }
      }
   }

   static {
      a = "friend.cfg";
   }
}
