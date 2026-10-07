package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;

@ModuleInfo(R = "TimeChanger", a = "Change the time of the world", M = Category.RENDER)
public class TimeChanger extends Module {
   FloatValue Q;
   private static final String b;

   public TimeChanger() {
      this.Q = ValueBuilder.m(this, b).d(8000.0F).V(1.0F).w(0.0F).M(24000.0F).f$K().L();
   }

   @com.elowen.events.api.EventTarget
   public void G(EventTick var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null) {
         NameTags$NameTagData.level.setTimeFromServer((long)this.Q.o$F());
         NameTags$NameTagData.level.getLevelData().setGameTime((long)this.Q.o$F());
      }
   }

   @com.elowen.events.api.EventTarget
   public void r(EventPacket var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.R$Packet() instanceof ClientboundSetTimePacket) {
         var1.c(true);
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      b = "World Time";
   }
}
