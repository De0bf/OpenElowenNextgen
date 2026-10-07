package com.elowen.events.impl;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public class EventRender2D implements com.elowen.events.api.events.Event {
   private final Matrix3x2fStack f;
   private final GuiGraphicsExtractor j;

   public EventRender2D(Matrix3x2fStack var1, GuiGraphicsExtractor var2) {
      this.f = var1;
      this.j = var2;
   }

   public Matrix3x2fStack n$Matrix3x2fStack() {
      return this.f;
   }

   public GuiGraphicsExtractor e() {
      return this.j;
   }
}
