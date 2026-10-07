package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;

@ModuleInfo(R = "LowFire", M = Category.RENDER, a = "Lowers the fire overlay position")
public class LowFire extends Module {
   public final FloatValue q;
   private static final String b;

   public LowFire() {
      this.q = ValueBuilder.m(this, b).d(0.0F).w(-1.3F).M(1.3F).V(0.01F).f$K().L();
   }

   public static float h$F() {
      HasValue[] var0 = Theme.s$ArrQ();

      try {
         LowFire var1 = (LowFire)Elowen.S$Elowen().q$ModuleManager().A(LowFire.class);
         if (var1 != null && var1.w()) {
            return var1.q.o$F();
         }
      } catch (Exception var2) {
      }

      return 0.0F;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b = "Offset";
   }
}
