package com.elowen.commands.impl;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventKey;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Module;
import com.elowen.utils.ChatUtils;
import com.elowen.values.HasValue;
import com.mojang.blaze3d.platform.InputConstants.Key;
import java.util.Objects;

class CommandBind$1 {
   final Module S;
   final String R;
   private static final String[] a = new String[]{"Bound ", " to "};
   CommandBind$1(CommandBind var1, Module var2, String var3) {
      boolean var10000 = CommandBind.P();
      this.S = var2;
      boolean var4 = var10000;
      this.R = var3;
      Objects.requireNonNull(var1);
      super();
      if (HasValue.X$Z()) {
         CommandBind.H(!var4);
      }
   }

   @EventTarget
   public void n(EventKey var1) {
      boolean var2 = CommandBind.q$Z();
      if (var1.U$Z()) {
         this.S.V(var1.n$I());
         Key var3 = CommandBind.I(var1.n$I());
         String var4 = var3.getDisplayName().getString().toUpperCase();
         String[] var5 = a;
         ChatUtils.b("Bound " + this.R + " to " + var4 + ".");
         Elowen.S$Elowen().e().R(this);
         Elowen.S$Elowen().q$S().X$V();
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
