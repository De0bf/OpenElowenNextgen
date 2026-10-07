package com.elowen.events.api.types;

import com.elowen.exceptions.NoSuchModuleException;

public enum EventType {
   PRE,
   POST,
   SEND,
   RECEIVE,
   NAME,
   FOOTER,
   HEADER,
   TITLE,
   SUBTITLE,
   BLUR,
   SHADOW;

   private static boolean G;

   public static void E(boolean var0) {
      G = var0;
   }

   public static boolean Z() {
      return G;
   }

   public static boolean F() {
      boolean var0 = Z();
      return !var0;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
