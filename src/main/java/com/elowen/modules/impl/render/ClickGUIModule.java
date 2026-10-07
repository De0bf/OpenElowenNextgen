package com.elowen.modules.impl.render;

import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.ui.ClickGUI;
import com.elowen.values.HasValue;

@ModuleInfo(R = "ClickGUI", M = Category.RENDER, a = "The ClickGUI")
public class ClickGUIModule extends Module {
   ClickGUI S = null;

   @Override
   protected void Z() {
      super.Z();
      this.V(344);
   }

   @Override
   public void h$V() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (this.S == null) {
         this.S = new ClickGUI();
      }

      if (G.gui.screen() != this.S) {
         G.gui.setScreen(this.S);
      }

      this.R$V();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
