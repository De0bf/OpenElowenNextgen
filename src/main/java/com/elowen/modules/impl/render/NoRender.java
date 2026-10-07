package com.elowen.modules.impl.render;

import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;

@ModuleInfo(R = "NoRender", a = "Disables rendering", M = Category.RENDER)
public class NoRender extends Module {
   public BooleanValue i;
   private static final String b;

   public NoRender() {
      this.i = ValueBuilder.m(this, b).h(true).f$K().f$O();
   }

   static {
      b = "Disable Effects";
   }
   }
