package com.elowen.modules.impl.move.scaffold;

import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventClick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.impl.move.Scaffold;
import net.minecraft.core.BlockPos;

public interface ScaffoldMode {
   void T$V();

   void F();

   void Y(com.elowen.events.impl.EventTick var1, BlockPos var2);

   void v(EventClick var1, BlockPos var2);

   void a(EventPacket var1);

   void q(EventUpdate var1);

   void z(EventMoveInput var1);

   Scaffold l$Scaffold();
}
