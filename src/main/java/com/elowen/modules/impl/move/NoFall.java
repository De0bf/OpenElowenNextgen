package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.move.nofall.NoFallMode;
import com.elowen.modules.impl.move.nofall.GrimGroundSpoof;
import com.elowen.modules.impl.move.nofall.ElytraPacket;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

@ModuleInfo(R = "NoFall", a = "Prevents fall damage", M = Category.MOVEMENT)
public class NoFall extends Module {
   private final ModeValue e;
   private final FloatValue D;
   private final FloatValue r;
   public boolean Q;
   private NoFallMode P;
   private final GrimGroundSpoof c;
   private final ElytraPacket j;
   private static final String[] b = new String[]{"Fall Distance", "Grim GroundSpoof", "ElytraPacket", "Mode", "Grim GroundSpoof", "ElytraPacket", "Skip Tick"};
   public NoFall() {
      String[] var1 = b;
      this.e = ValueBuilder.m(this, "Mode").W(new String[]{"Grim GroundSpoof", "ElytraPacket"}).m(0).f$K().T$t();
      this.D = ValueBuilder.m(this, "Fall Distance").d(3.0F).V(0.1F).w(3.0F).M(15.0F).f$K().L();
      this.r = ValueBuilder.m(this, "Skip Tick").d(1.0F).V(1.0F).w(0.0F).M(10.0F).l(this::deobfLambda$new$0).f$K().L();
      this.Q = false;
      this.c = new GrimGroundSpoof();
      this.j = new ElytraPacket();
      this.c.Q(this);
      this.j.Q(this);
      this.P = this.c;
   }

   public ModeValue V() {
      return this.e;
   }

   public Minecraft v$Minecraft() {
      return G;
   }

   public float A$F() {
      return this.r.o$F();
   }

   public float J$F() {
      boolean var10000 = Scaffold.S$Z();
      float var2 = this.D.o$F();
      boolean var1 = var10000;
      LocalPlayer var4 = G.player;
      if (var1) {
         if (G.player == null) {
            return var2;
         }

         var4 = G.player;
      }

      MobEffectInstance var3 = var4.getEffect(MobEffects.JUMP_BOOST);
      return var3 == null ? var2 : var2 + (var3.getAmplifier() + 1);
   }

   private void f$V() {
      boolean var1;
      NoFallMode var3;
      label24: {
         boolean var10000 = Scaffold.S$Z();
         String var2 = this.e.C();
         var1 = var10000;
         if ("ElytraPacket".equals(var2)) {
            var3 = this.j;
            if (var1) {
               break label24;
            }
         }

         var3 = this.c;
      }

      NoFallMode var4 = this.P;
      if (var1) {
         if (this.P == var3) {
            return;
         }

         this.P.l$V();
         this.P = var3;
         var4 = this.P;
      }

      var4.J$V();
   }

   @Override
   public void h$V() {
      this.Q = false;
      this.f$V();
      this.P.J$V();
   }

   @Override
   public void q$V() {
      this.P.l$V();
      this.Q = false;
   }

   @EventTarget
   public void o(EventTick var1) {
      this.f$V();
      this.P.R(var1);
   }

   @EventTarget
   public void b(EventMotion var1) {
      this.f$V();
      this.P.g(var1);
   }

   @EventTarget
   public void J(EventMoveInput var1) {
      this.f$V();
      this.P.T(var1);
   }

   @EventTarget
   public void A(EventRespawn var1) {
      this.f$V();
      this.P.e(var1);
   }

   @EventTarget(1)
   public void N(com.elowen.events.impl.EventPacket var1) {
      boolean var2 = Scaffold.k();
      if (!var2) {
         if (var1.M() == EventType.RECEIVE && var1.c$Z()) {
            return;
         }

         this.f$V();
         this.P.h(var1);
      }
   }

   private Boolean deobfLambda$new$0() {
      return this.e.t("Grim GroundSpoof");
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
