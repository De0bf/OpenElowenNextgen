package com.elowen.events.impl;

import com.elowen.events.api.types.EventType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public class EventShader implements com.elowen.events.api.events.Event {
   private final GuiGraphicsExtractor m;
   private final EventType p;

   public EventShader(GuiGraphicsExtractor var1, EventType var2) {
      this.m = var1;
      this.p = var2;
   }

   public GuiGraphicsExtractor O() {
      return this.m;
   }

   @Deprecated
   public Matrix3x2fStack M() {
      return this.m.pose();
   }

   public EventType e() {
      return this.p;
   }
}
