package com.elowen.modules.impl.test;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.ChatUtils;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;

@ModuleInfo(R = "CheckS08", M = Category.TEST, a = "Detect S08 position/look sync packets")
public class CheckS08 extends Module {
   private static final String b;

   @EventTarget
   public void W(EventPacket var1) {
      String var2 = NoInterpolation.U$String();
      if (var1.M() == EventType.RECEIVE) {
         if (var1.R$Packet() instanceof ClientboundPlayerPositionPacket || var1.R$Packet() instanceof ClientboundPlayerLookAtPacket) {
            ChatUtils.b(b);
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      b = "S08 Detected!";
   }
}
