package com.elowen.modules;

import com.elowen.utils.FontIcons;

public enum Category {
   COMBAT("Combat", FontIcons.c),
   MOVEMENT("Movement", FontIcons.p),
   PLAYER("Player", FontIcons.M),
   RENDER("Render", FontIcons.G),
   MISC("Misc", FontIcons.m),
   FUN("Fun", FontIcons.m),
   TEST("Test", FontIcons.N);

   private final String B;
   private final String b;

   Category(String var3, String var4) {
      this.B = var3;
      this.b = var4;
   }

   public String G$String() {
      return this.B;
   }

   public String B$String() {
      return this.b;
   }
}
