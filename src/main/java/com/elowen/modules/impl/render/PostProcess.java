package com.elowen.modules.impl.render;

import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;

@ModuleInfo(R = "PostProcess", a = "Post process effects", M = Category.RENDER)
public class PostProcess extends Module {
   public final BooleanValue r;
   public final FloatValue S;
   public final BooleanValue U;
   private static final String[] b = new String[]{"Blur Strength", "Bloom", "Blur"};
   public PostProcess() {
      String[] var1 = b;
      this.r = ValueBuilder.m(this, "Blur").h(true).f$K().f$O();
      this.S = ValueBuilder.m(this, "Blur Strength").l(this.r::w).d(2.0F).w(0.0F).M(19.0F).V(1.0F).f$K().L();
      this.U = ValueBuilder.m(this, "Bloom").h(true).f$K().f$O();
   }

   static {
   }
}
