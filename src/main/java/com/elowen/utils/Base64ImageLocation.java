package com.elowen.utils;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64ImageLocation {
   private static final Logger q = LoggerFactory.getLogger(Base64ImageLocation.class);
   private static int I = 0;
   private final String H;
   private Identifier J;
   public boolean m = false;

   public Base64ImageLocation(String var1) {
      this.H = var1;
   }

   public String T$String() {
      return this.H;
   }
}
