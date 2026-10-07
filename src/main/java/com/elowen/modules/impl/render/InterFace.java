package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;

@ModuleInfo(R = "InterFace", a = "Global interface settings", M = Category.RENDER)
public class InterFace extends Module {
   public BooleanValue P;
   private static final String b;

   public InterFace() {
      this.P = ValueBuilder.m(this, b).h(true).f$K().f$O();
   }

   public static InterFace q$InterFace() {
      try {
         return (InterFace)Elowen.S$Elowen().q$ModuleManager().A(InterFace.class);
      } catch (RuntimeException var1) {
         return null;
      }
   }

   static {
      b = "Module Toggle Sound";
   }
}
