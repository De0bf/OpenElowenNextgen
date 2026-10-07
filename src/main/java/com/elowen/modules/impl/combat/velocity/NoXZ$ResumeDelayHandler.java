package com.elowen.modules.impl.combat.velocity;

import com.elowen.exceptions.NoSuchModuleException;
import java.util.Objects;

class NoXZ$ResumeDelayHandler {
   long F;
   boolean K;
   boolean k;
   long s;
   final NoXZ o;

   NoXZ$ResumeDelayHandler(NoXZ var1) {
      Objects.requireNonNull(var1);
      this.o = var1;
      super();
   }

   void u(float var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1 <= 0.0F) {
         this.o.x();
      }

      this.F = System.currentTimeMillis() + (long)(var1 * 1000.0F);
      this.K = true;
   }

   void V(float var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1 <= 0.0F) {
         this.k = false;
         this.s = 0L;
      }

      this.k = true;
      this.s = System.currentTimeMillis() + (long)(var1 * 1000.0F);
      if (this.o.m == NoXZ$Stage.DELAY || this.o.m == NoXZ$Stage.ATTACK || this.o.m == NoXZ$Stage.PRE_ATTACK) {
         this.o.w.clear();
         this.o.m = NoXZ$Stage.NONE;
         this.o.H = false;
         this.o.N = 0;
         this.o.o = 0;
         this.o.I = null;
         this.o.d = false;
         this.o.n = 0L;
         this.o.R = 0;
      }
   }

   void s(float var1) {
      com.elowen.values.HasValue[] var2 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (var1 <= 0.0F) {
         this.k = false;
         this.s = 0L;
      }

      this.k = true;
      this.s = System.currentTimeMillis() + (long)(var1 * 1000.0F);
   }

   boolean f$Z() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.K && this.o.L && System.currentTimeMillis() >= this.F) {
         this.o.x();
         return true;
      } else {
         return false;
      }
   }

   boolean O() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      if (this.k && System.currentTimeMillis() >= this.s) {
         this.o.d$V();
         return true;
      } else {
         return false;
      }
   }

   boolean c$Z() {
      com.elowen.values.HasValue[] var1 = com.elowen.modules.impl.combat.velocity.NoXZ.c$ArrQ();
      return this.k && System.currentTimeMillis() < this.s;
   }

   void C() {
      this.K = false;
      this.F = 0L;
   }

   void u$V() {
      this.k = false;
      this.s = 0L;
   }

   void e() {
      this.C();
      this.u$V();
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
