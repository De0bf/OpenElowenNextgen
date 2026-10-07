package com.elowen.modules.impl.render;

import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;

@ModuleInfo(R = "Scoreboard", a = "Modifies the scoreboard", M = Category.RENDER)
public class Scoreboard extends Module {
   public BooleanValue T;
   public FloatValue e;
   private static final String[] b = new String[]{"Down", "Hide Red Score"};
   public Scoreboard() {
      String[] var1 = b;
      this.T = ValueBuilder.m(this, "Hide Red Score").h(true).f$K().f$O();
      this.e = ValueBuilder.m(this, "Down").d(120.0F).V(1.0F).w(0.0F).M(300.0F).f$K().L();
   }

   static {
   }
}
