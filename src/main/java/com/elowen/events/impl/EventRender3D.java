package com.elowen.events.impl;

import com.mojang.blaze3d.vertex.PoseStack;

public class EventRender3D implements com.elowen.events.api.events.Event {
   private final float G;
   private final PoseStack R;

   public float d$F() {
      return this.G;
   }

   public PoseStack P() {
      return this.R;
   }

   public EventRender3D(float var1, PoseStack var2) {
      this.G = var1;
      this.R = var2;
   }
}
