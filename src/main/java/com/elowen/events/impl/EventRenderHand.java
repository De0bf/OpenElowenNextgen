package com.elowen.events.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class EventRenderHand implements com.elowen.events.api.events.Event {
   private final InteractionHand i;
   private final float A;
   private final float a;
   private final float V;
   private final ItemStack n;
   private final PoseStack O;
   private final SubmitNodeCollector p;
   private final int P;
   private boolean w;

   public EventRenderHand(InteractionHand var1, float var2, float var3, float var4, ItemStack var5, PoseStack var6, SubmitNodeCollector var7, int var8) {
      EventPacket.C();
      this.i = var1;
      this.A = var2;
      this.a = var3;
      this.V = var4;
      this.n = var5;
      this.O = var6;
      this.p = var7;
      this.P = var8;
      this.w = false;
      if (com.elowen.values.HasValue.X$Z()) {
         EventPacket.J(new com.elowen.values.HasValue[2]);
      }
   }

   public InteractionHand J$InteractionHand() {
      return this.i;
   }

   public float J$F() {
      return this.A;
   }

   public float k() {
      return this.a;
   }

   public float N() {
      return this.V;
   }

   public ItemStack l$ItemStack() {
      return this.n;
   }

   public PoseStack m() {
      return this.O;
   }

   public SubmitNodeCollector I() {
      return this.p;
   }

   public int K() {
      return this.P;
   }

   public boolean L() {
      return this.w;
   }

   public void v(boolean var1) {
      this.w = var1;
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
