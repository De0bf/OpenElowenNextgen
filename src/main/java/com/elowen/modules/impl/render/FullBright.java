package com.elowen.modules.impl.render;

import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;

@ModuleInfo(R = "FullBright", a = "Make your world brighter.", M = Category.RENDER)
public class FullBright extends Module {
   public FloatValue c;
   private static final String b;

   public FullBright() {
      this.c = ValueBuilder.m(this, b).d(1.0F).V(0.1F).w(0.0F).M(1.0F).f$K().L();
   }

   static {
      b = "Brightness";
   }
}
