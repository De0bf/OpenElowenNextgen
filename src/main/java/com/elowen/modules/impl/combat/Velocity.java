package com.elowen.modules.impl.combat;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventStuckInBlock;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.velocity.JumpReset;
import com.elowen.modules.impl.combat.velocity.Packet;
import com.elowen.modules.impl.combat.velocity.ElytraPacket;
import com.elowen.modules.impl.combat.velocity.Reduce;
import com.elowen.modules.impl.combat.velocity.Cancel;
import com.elowen.modules.impl.combat.velocity.Zen;
import com.elowen.modules.impl.combat.velocity.Set;
import com.elowen.modules.impl.combat.velocity.VelocityMode;
import com.elowen.modules.impl.combat.velocity.NoXZ;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import net.minecraft.client.Minecraft;

@ModuleInfo(R = "Velocity", M = Category.COMBAT, a = "Reduces knockback.")
public class Velocity extends Module {
   private final BooleanValue ZR;
   private final ModeValue X;
   private final BooleanValue h;
   private final FloatValue Y;
   private final FloatValue K;
   private final FloatValue e;
   private final FloatValue P;
   private final FloatValue Zj;
   private final FloatValue J;
   private final FloatValue U;
   private final FloatValue D;
   private final FloatValue d;
   private final BooleanValue p;
   private final BooleanValue r;
   private final BooleanValue o;
   private final BooleanValue B;
   private final FloatValue C;
   private final BooleanValue M;
   private final FloatValue y;
   private final BooleanValue x;
   private final FloatValue Z;
   private final FloatValue ZZ;
   private final FloatValue Q;
   private final FloatValue m;
   private final BooleanValue R;
   private final FloatValue V;
   private final FloatValue v;
   private final BooleanValue F;
   private final BooleanValue l;
   private final BooleanValue S;
   private final BooleanValue E;
   private final FloatValue Z7;
   public VelocityMode j;
   private final NoXZ z;
   private final JumpReset c;
   private final Zen i;
   private final Cancel f;
   private final Set T;
   private final Packet t;
   private final Reduce b;
   private final ElytraPacket q;
   private static boolean I;
   private static final String[] bb = new String[]{"Reduce", "Only Delay s12", "Alink Time", "Zen", "Packet", "NoXZ", "Only KillAura", "Cancel", "Jump Reset", "Zen", "Min Knockback Threshold", "Set", "Attack in Hurt Time", "Hurt Time Threshold", "Limit CPS", "Reduce", "Mode", "Instant Attack", "ElytraPacket", "Air Check Depth", "Resume Delay (Seconds)", "Horizontal %", "Aim Target", "Attack Distance", "ElytraPacket", "Attack in Same Height", "Packet", "Reduce Min Knockback", "Ground Tick", "Clear on Target Lost", "Jump Reset", "Logging", "Cancel Alink After Attack (Test)", "Attack Counts", "Sprint State Check", "Alink Time (ticks)", "Delay Until Ground", "Only Sprint", "Attack in Alink", "Vertical %", "NoXZ", "Stuck Cooldown Ticks", "Pearl Clear Delay (Seconds)", "Attack Delay Ticks", "Cancel", "Set", "Cooldown Ticks", "Rotation"};
   public Velocity() {
      boolean var10000 = o$Z();
      String[] var2 = bb;
      this.ZR = com.elowen.values.ValueBuilder.m(this, "Logging").h(true).f$K().f$O();
      boolean var1 = var10000;
      this.X = com.elowen.values.ValueBuilder.m(this, "Mode")
         .W(new String[]{"NoXZ", "Reduce", "Jump Reset", "Zen", "Cancel", "Set", "Packet", "ElytraPacket"})
         .m(1)
         .f$K()
         .T$t();
      this.h = com.elowen.values.ValueBuilder.m(this, "Only KillAura").h(true).l(this::deobfLambda$new$0).f$K().f$O();
      this.Y = com.elowen.values.ValueBuilder.m(this, "Limit CPS").d(0.0F).V(1.0F).w(0.0F).M(20.0F).l(this::deobfLambda$new$1).f$K().L();
      this.K = com.elowen.values.ValueBuilder.m(this, "Attack Counts").d(3.0F).V(1.0F).w(0.0F).M(20.0F).l(this::deobfLambda$new$2).f$K().L();
      this.e = com.elowen.values.ValueBuilder.m(this, "Alink Time").d(5000.0F).V(50.0F).w(0.0F).M(10000.0F).l(this::deobfLambda$new$3).f$K().L();
      this.P = com.elowen.values.ValueBuilder.m(this, "Attack Delay Ticks").d(1.0F).V(1.0F).w(0.0F).M(5.0F).l(this::deobfLambda$new$4).f$K().L();
      this.Zj = com.elowen.values.ValueBuilder.m(this, "Attack Distance").d(2.0F).V(0.01F).w(0.0F).M(3.0F).l(this::deobfLambda$new$5).f$K().L();
      this.J = com.elowen.values.ValueBuilder.m(this, "Stuck Cooldown Ticks").d(7.0F).w(0.0F).M(20.0F).V(1.0F).l(this::deobfLambda$new$6).f$K().L();
      this.U = com.elowen.values.ValueBuilder.m(this, "Min Knockback Threshold").d(0.2F).w(0.0F).M(1.0F).V(0.01F).l(this::deobfLambda$new$7).f$K().L();
      this.D = com.elowen.values.ValueBuilder.m(this, "Pearl Clear Delay (Seconds)").d(0.2F).V(0.05F).w(0.0F).M(1.0F).l(this::deobfLambda$new$8).f$K().L();
      this.d = com.elowen.values.ValueBuilder.m(this, "Resume Delay (Seconds)").d(0.0F).V(0.1F).w(0.0F).M(5.0F).l(this::deobfLambda$new$9).f$K().L();
      this.p = com.elowen.values.ValueBuilder.m(this, "Instant Attack").h(false).l(this::deobfLambda$new$10).f$K().f$O();
      this.r = com.elowen.values.ValueBuilder.m(this, "Cancel Alink After Attack (Test)").h(false).l(this::deobfLambda$new$11).f$K().f$O();
      this.o = com.elowen.values.ValueBuilder.m(this, "Only Sprint").h(true).l(this::deobfLambda$new$12).f$K().f$O();
      this.B = com.elowen.values.ValueBuilder.m(this, "Attack in Hurt Time").h(false).l(this::deobfLambda$new$13).f$K().f$O();
      this.C = com.elowen.values.ValueBuilder.m(this, "Hurt Time Threshold").d(0.0F).w(0.0F).M(10.0F).V(1.0F).l(this::deobfLambda$new$14).f$K().L();
      this.M = com.elowen.values.ValueBuilder.m(this, "Delay Until Ground").h(false).l(this::deobfLambda$new$15).f$K().f$O();
      this.y = com.elowen.values.ValueBuilder.m(this, "Ground Tick").d(0.0F).V(1.0F).w(0.0F).M(3.0F).l(this::deobfLambda$new$16).f$K().L();
      this.x = com.elowen.values.ValueBuilder.m(this, "Sprint State Check").h(true).l(this::deobfLambda$new$17).f$K().f$O();
      this.Z = com.elowen.values.ValueBuilder.m(this, "Horizontal %").d(50.0F).V(1.0F).w(-100.0F).M(200.0F).l(this::deobfLambda$new$18).f$K().L();
      this.ZZ = com.elowen.values.ValueBuilder.m(this, "Vertical %").d(50.0F).V(1.0F).w(-100.0F).M(200.0F).l(this::deobfLambda$new$19).f$K().L();
      this.Q = com.elowen.values.ValueBuilder.m(this, "Alink Time (ticks)").d(20.0F).V(1.0F).w(0.0F).M(200.0F).l(this::deobfLambda$new$20).f$K().L();
      this.m = com.elowen.values.ValueBuilder.m(this, "Cooldown Ticks").d(10.0F).V(1.0F).w(0.0F).M(40.0F).l(this::deobfLambda$new$21).f$K().L();
      this.R = com.elowen.values.ValueBuilder.m(this, "Only Delay s12").h(false).l(this::deobfLambda$new$22).f$K().f$O();
      this.V = com.elowen.values.ValueBuilder.m(this, "Reduce Min Knockback").d(0.2F).V(0.01F).w(0.0F).M(2.0F).l(this::deobfLambda$new$23).f$K().L();
      this.v = com.elowen.values.ValueBuilder.m(this, "Aim Target").d(0.0F).V(1.0F).w(0.0F).M(20.0F).l(this::deobfLambda$new$24).f$K().L();
      this.F = com.elowen.values.ValueBuilder.m(this, "Rotation").h(false).l(this::deobfLambda$new$25).f$K().f$O();
      this.l = com.elowen.values.ValueBuilder.m(this, "Attack in Alink").h(false).l(this::deobfLambda$new$26).f$K().f$O();
      this.S = com.elowen.values.ValueBuilder.m(this, "Clear on Target Lost").h(true).l(this::deobfLambda$new$27).f$K().f$O();
      this.E = com.elowen.values.ValueBuilder.m(this, "Attack in Same Height").h(false).l(this::deobfLambda$new$28).f$K().f$O();
      this.Z7 = com.elowen.values.ValueBuilder.m(this, "Air Check Depth").d(2.0F).w(0.0F).M(5.0F).V(0.1F).l(this::deobfLambda$new$29).f$K().L();
      this.z = new NoXZ();
      this.c = new JumpReset();
      this.i = new Zen();
      this.f = new Cancel();
      this.T = new Set();
      this.t = new Packet();
      this.b = new Reduce();
      this.q = new ElytraPacket();
      this.z.C(this);
      this.c.C(this);
      this.i.C(this);
      this.f.C(this);
      this.T.C(this);
      this.t.C(this);
      this.b.C(this);
      this.q.C(this);
      this.j = this.z;
      if (!var1) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }
   }

   private void S$V() {
      Velocity var3 = null;
      boolean var1;
      VelocityMode var2;
      label126: {
         var1 = p();
         boolean var10000 = this.X.t("NoXZ");
         if (!var1) {
            if (var10000) {
               var2 = this.z;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Jump Reset");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.c;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Zen");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.i;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Cancel");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.f;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Set");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.T;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Packet");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.t;
               if (!var1) {
                  break label126;
               }
            }

            var10000 = this.X.t("Reduce");
         }

         label127: {
            if (!var1) {
               if (var10000) {
                  var2 = this.b;
                  if (!var1) {
                     break label126;
                  }
               }

               var3 = this;
               if (var1) {
                  break label127;
               }

               var10000 = this.X.t("ElytraPacket");
            }

            if (var10000) {
               var2 = this.q;
               if (!var1) {
                  break label126;
               }
            }

            var3 = this;
         }

         var2 = var3.z;
      }

      VelocityMode var4 = this.j;
      if (!var1) {
         if (this.j == var2) {
            return;
         }

         this.j.z$V();
         this.j = var2;
         var4 = this.j;
      }

      var4.c$V();
   }

   @EventTarget
   public void c(EventPacket var1) {
      this.S$V();
      this.j.J(var1);
   }

   @EventTarget
   public void Z(EventTick var1) {
      this.S$V();
      this.j.Z(var1);
   }

   @EventTarget
   public void A(EventTick var1) {
      this.S$V();
      this.j.b(var1);
   }

   @EventTarget(4)
   public void d(EventMoveInput var1) {
      this.S$V();
      this.j.E(var1);
   }

   @EventTarget
   public void E(EventStuckInBlock var1) {
      this.S$V();
      this.j.f(var1);
   }

   @Override
   public void h$V() {
      this.S$V();
      this.j.c$V();
   }

   @Override
   public void q$V() {
      this.j.z$V();
   }

   public void F(String var1) {
      if (this.ZR.w()) {
         com.elowen.utils.ChatUtils.b(var1);
      }
   }

   @Override
   public void X(String var1) {
      super.X(var1);
   }

   public Minecraft i$MC() {
      return G;
   }

   public static boolean r() {
      boolean var10000 = o$Z();
      Module var1 = Elowen.S$Elowen().q$ModuleManager().A(Velocity.class);
      boolean var0 = var10000;
      Module var4 = var1;
      if (var0) {
         if (!(var1 instanceof Velocity)) {
            return false;
         }

         var4 = var1;
      }

      Velocity var2 = (Velocity)var4;
      Velocity var5 = var2;
      if (var0) {
         if (!var2.w()) {
            return false;
         }

         var5 = var2;
      }

      VelocityMode var3 = var5.j;
      var10000 = var3 instanceof Reduce;
      if (var0) {
         if (!var10000) {
            return false;
         }

         var10000 = ((Reduce)var3).X$Z();
      }

      return !var0 ? var10000 : var10000;
   }

   public static boolean e() {
      boolean var10000 = p();
      Module var1 = Elowen.S$Elowen().q$ModuleManager().A(Velocity.class);
      boolean var0 = var10000;
      Module var4 = var1;
      if (!var0) {
         if (!(var1 instanceof Velocity)) {
            return false;
         }

         var4 = var1;
      }

      Velocity var2 = (Velocity)var4;
      Velocity var5 = var2;
      if (!var0) {
         if (!var2.w()) {
            return false;
         }

         var5 = var2;
      }

      VelocityMode var3 = var5.j;
      var10000 = var3 instanceof Reduce;
      if (!var0) {
         if (!var10000) {
            return false;
         }

         var10000 = ((Reduce)var3).c$Z();
      }

      return var0 ? var10000 : var10000;
   }

   public boolean m() {
      return this.h.w();
   }

   public float q$F() {
      return this.Y.o$F();
   }

   public float I() {
      return this.K.o$F();
   }

   public float s$F() {
      return this.e.o$F();
   }

   public float h$F() {
      return this.P.o$F();
   }

   public float S$F() {
      return this.Zj.o$F();
   }

   public float D$F() {
      return this.J.o$F();
   }

   public float f$F() {
      return this.U.o$F();
   }

   public float x$F() {
      return this.D.o$F();
   }

   public float X$F() {
      return this.d.o$F();
   }

   public boolean s$Z() {
      return this.p.w();
   }

   public boolean j$Z() {
      return this.r.w();
   }

   public boolean J$Z() {
      return this.o.w();
   }

   public boolean a$Z() {
      return this.B.w();
   }

   public float V() {
      return this.C.o$F();
   }

   public boolean Y() {
      return this.M.w();
   }

   public float v$F() {
      return this.y.o$F();
   }

   public boolean t() {
      return this.x.w();
   }

   public float k() {
      return this.Z.o$F();
   }

   public float g$F() {
      return this.ZZ.o$F();
   }

   public float o$F() {
      return this.Q.o$F();
   }

   public float N() {
      return this.m.o$F();
   }

   public boolean f$Z() {
      return this.R.w();
   }

   public float P() {
      return this.V.o$F();
   }

   public float a$F() {
      return this.v.o$F();
   }

   public boolean z$Z() {
      return this.F.w();
   }

   public boolean d$Z() {
      boolean var1 = o$Z();
      return this.w() && this.F.w() && this.j instanceof Reduce;
   }

   public boolean K() {
      return this.l.w();
   }

   public boolean g$Z() {
      return this.S.w();
   }

   public boolean v$Z() {
      return this.E.w();
   }

   public float j$F() {
      return this.Z7.o$F();
   }

   public boolean q$Z() {
      boolean var1 = p();
      Velocity var10000 = this;
      if (!var1) {
         if (!this.w()) {
            return false;
         }

         var10000 = this;
      }

      VelocityMode var2 = var10000.j;
      VelocityMode var3 = var2;
      if (!var1) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.X$Z();
      return var1 ? var4 : var4;
   }

   private Boolean deobfLambda$new$29() {
      boolean var1 = o$Z();
      return this.X.t("Reduce") && this.E.w();
   }

   private Boolean deobfLambda$new$28() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$27() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$26() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$25() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$24() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$23() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$22() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$21() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$20() {
      return this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$19() {
      return this.X.t("Set");
   }

   private Boolean deobfLambda$new$18() {
      return this.X.t("Set");
   }

   private Boolean deobfLambda$new$17() {
      return this.X.t("Zen");
   }

   private Boolean deobfLambda$new$16() {
      boolean var1 = o$Z();
      return (this.X.t("NoXZ") || this.X.t("Reduce")) && this.M.w();
   }

   private Boolean deobfLambda$new$15() {
      boolean var1 = p();
      boolean var10000 = this.X.t("NoXZ");
      if (!var1) {
         if (!var10000) {
            var10000 = this.X.t("Reduce");
            if (var1) {
               return var10000;
            }

            if (!var10000) {
               return false;
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$14() {
      boolean var1 = p();
      boolean var10000 = this.X.t("NoXZ");
      if (!var1) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.w();
      }

      if (var1) {
         return var10000;
      } else {
         return var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$13() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$12() {
      boolean var1 = p();
      boolean var10000 = this.X.t("NoXZ");
      if (!var1) {
         if (!var10000) {
            var10000 = this.X.t("Reduce");
            if (var1) {
               return var10000;
            }

            if (!var10000) {
               return false;
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$11() {
      boolean var1 = o$Z();
      return this.X.t("NoXZ") && this.p.w();
   }

   private Boolean deobfLambda$new$10() {
      boolean var1 = p();
      boolean var10000 = this.X.t("NoXZ");
      if (!var1) {
         if (!var10000) {
            var10000 = this.X.t("Zen");
            if (var1) {
               return var10000;
            }

            if (!var10000) {
               return false;
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$9() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$8() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$7() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$6() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$5() {
      boolean var1 = o$Z();
      return this.X.t("NoXZ") || this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$4() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$3() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$2() {
      boolean var1 = o$Z();
      return this.X.t("NoXZ") || this.X.t("Zen") || this.X.t("Reduce");
   }

   private Boolean deobfLambda$new$1() {
      return this.X.t("NoXZ");
   }

   private Boolean deobfLambda$new$0() {
      return this.X.t("NoXZ");
   }

   public static void Q(boolean var0) {
      I = var0;
   }

   public static boolean o$Z() {
      return I;
   }

   public static boolean p() {
      return !o$Z();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      Q(true);
   }
}
