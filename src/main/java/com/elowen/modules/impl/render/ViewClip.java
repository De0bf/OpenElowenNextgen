package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender2D;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import net.minecraft.client.CameraType;

@ModuleInfo(R = "ViewClip", a = "Allows you to see through blocks", M = Category.RENDER)
public class ViewClip extends Module {
   public FloatValue z;
   public BooleanValue c;
   public FloatValue Z;
   public SmoothAnimationTimer S;
   CameraType V;
   private static final String[] b = new String[]{"Scale", "Animation", "Animation Speed"};
   public ViewClip() {
      String[] var1 = b;
      this.z = com.elowen.values.ValueBuilder.m(this, "Scale").w(0.5F).M(2.0F).d(1.0F).V(0.01F).f$K().L();
      this.c = com.elowen.values.ValueBuilder.m(this, "Animation").h(true).f$K().f$O();
      this.Z = com.elowen.values.ValueBuilder.m(this, "Animation Speed").w(0.01F).M(0.5F).d(0.3F).V(0.01F).l(this::deobfLambda$new$0).f$K().L();
      this.S = new SmoothAnimationTimer(100.0F);
   }

   @com.elowen.events.api.EventTarget
   public void J(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (this.V != G.options.getCameraType()) {
         this.V = G.options.getCameraType();
         if (this.V == CameraType.FIRST_PERSON || this.V == CameraType.THIRD_PERSON_BACK) {
            this.S.l = 0.0F;
         }
      }

      this.S.G = this.Z.o$F();
      this.S.F(true);
   }

   private Boolean deobfLambda$new$0() {
      return this.c.w();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
