package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.AntiBots;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.modules.impl.render.Theme;
import com.elowen.modules.impl.render.projectiles.ProjectileData;
import com.elowen.modules.impl.render.projectiles.datas.BasicProjectileData;
import com.elowen.utils.FriendManager;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.SmoothAnimationTimer;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.BlinkingPlayer;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "Blink", M = Category.MOVEMENT, a = "Suspends all movement packets for teleporting!")
public class Blink extends Module {
   private final com.elowen.modules.impl.render.projectiles.datas.EntityArrowData y = new com.elowen.modules.impl.render.projectiles.datas.EntityArrowData();
   private final BasicProjectileData t;
   private final BasicProjectileData c;
   public static final Set R;
   private final Queue m;
   private final SmoothAnimationTimer C;
   public FloatValue x;
   public FloatValue D;
   public FloatValue V;
   public FloatValue p;
   public FloatValue f;
   public FloatValue Z;
   public BooleanValue o;
   private boolean T;
   private RemotePlayer P;
   private double U;
   private double X;
   private double i;
   private float d;
   private float l;
   private int B;
   private int K;
   private boolean F;
   private final SkijaRenderer h;
   private static final String[] b = new String[]{"Fake Player HitBoxes", " Ticks Behind", "Max Ticks", "Player Distance", "Release Ticks on Damage", "TNT Distance", "Box Mode", "Release Speed (Tick)"};
   public Blink() {
      boolean var10000 = Scaffold.S$Z();
      this.t = new BasicProjectileData(Collections.singleton(ThrownEgg.class), new Color(255, 238, 154));
      this.c = new BasicProjectileData(Collections.singleton(Snowball.class), new Color(255, 255, 255));
      this.m = new ConcurrentLinkedQueue();
      this.C = new SmoothAnimationTimer(0.0F, 0.2F);
      String[] var2 = b;
      this.x = com.elowen.values.ValueBuilder.m(this, "Release Ticks on Damage").w(0.0F).M(50.0F).d(20.0F).V(1.0F).f$K().L();
      this.D = com.elowen.values.ValueBuilder.m(this, "Release Speed (Tick)").w(0.0F).M(20.0F).d(10.0F).V(1.0F).f$K().L();
      this.V = com.elowen.values.ValueBuilder.m(this, "Max Ticks").w(0.0F).M(100.0F).d(200.0F).V(1.0F).f$K().L();
      this.p = com.elowen.values.ValueBuilder.m(this, "Player Distance").w(0.0F).M(10.0F).d(4.0F).V(0.1F).f$K().L();
      this.f = com.elowen.values.ValueBuilder.m(this, "TNT Distance").w(0.0F).M(10.0F).d(5.0F).V(0.1F).f$K().L();
      this.Z = com.elowen.values.ValueBuilder.m(this, "Fake Player HitBoxes").w(0.0F).M(3.0F).d(0.2F).V(0.01F).f$K().L();
      boolean var1 = var10000;
      this.o = com.elowen.values.ValueBuilder.m(this, "Box Mode").h(false).f$K().f$O();
      this.T = false;
      this.B = 0;
      this.K = 0;
      this.F = false;
      this.h = com.elowen.utils.renderer.SkiaRenderManager.X$m();
      if (!HasValue.x()) {
         Scaffold.u(!var1);
      }
   }

   private boolean I() {
      boolean var10000 = Scaffold.S$Z();
      Theme var2 = Theme.K();
      boolean var1 = var10000;
      Theme var3 = var2;
      if (var1) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      var10000 = var3.B.w();
      if (var1) {
         if (!var10000) {
            return false;
         }

         var10000 = var2.K.w();
      }

      return !var1 ? var10000 : var10000;
   }

   private int k(float var1) {
      boolean var10000 = Scaffold.S$Z();
      Theme var3 = Theme.K();
      boolean var2 = var10000;
      Theme var6 = var3;
      if (var2) {
         if (var3 == null) {
            return new Color(6, 176, 241, 255).getRGB();
         }

         var6 = var3;
      }

      float var4 = var6.h.o$F();
      float var5 = var3.j.o$F();
      return com.elowen.utils.renderer.RenderUtils.A((int)(-var1 * var5), 1.0F, 1.0F, (21.0F - var4) * 1000.0F);
   }

   private int q$I() {
      boolean var10000 = Scaffold.k();
      Theme var2 = Theme.K();
      boolean var1 = var10000;
      Theme var3 = var2;
      if (!var1) {
         if (var2 == null) {
            return new Color(6, 176, 241, 255).getRGB();
         }

         var3 = var2;
      }

      return var3.g$I();
   }

   private void q(SkijaRenderer var1, float var2, int var3, int var4) {
      float var6 = 100.0F;
      float var7 = 5.0F;
      float var8 = 2.0F;
      int var10000 = ((Scaffold.S$Z()) ? 1 : 0);
      var1.d(var3, var4, var6, var7, var8, Integer.MIN_VALUE);
      float var9 = Mth.clamp(var2, 0.0F, var6);
      boolean var5 = (boolean)((var10000) != 0);
      if (var9 > 0.0F) {
         Canvas var10;
         label37: {
            label41: {
               var10 = var1.E$Canvas();
               var10.save();
               var10.clipRRect(RRect.makeLTRB(var3, var4, var3 + var9, var4 + var7, var8, var8), true);
               var10000 = ((this.I()) ? 1 : 0);
               if (var5) {
                  if (var10000 == 0) {
                     break label41;
                  }

                  var10000 = 0;
               }

               int var11 = var10000;

               while (var11 < (int)var9) {
                  int var12 = this.k((float)(var3 + var11));
                  var1.d(var3 + var11, var4, 1.0F, var7, 0.0F, var12);
                  var11++;
                  if (!var5) {
                     break label37;
                  }

                  if (!var5) {
                     break;
                  }
               }

               if (var5) {
                  break label37;
               }
            }

            var1.d(var3, var4, var9, var7, var8, this.q$I());
         }

         var10.restore();
      }
   }

   private long O$h() {
      return this.m.stream().filter(var0 -> deobfLambda$getBlinkTicks$0((Packet)var0)).count();
   }

   private void T(ServerboundMovePlayerPacket var1) {
      boolean var2 = Scaffold.k();
      Blink var10000 = this;
      if (!var2) {
         if (this.o.w()) {
            this.U = var1.getX(this.U);
            this.X = var1.getY(this.X);
            this.i = var1.getZ(this.i);
            if (!var2) {
               if (!var1.hasRotation()) {
                  return;
               }

               this.d = var1.getYRot(this.d);
               this.l = var1.getXRot(this.l);
            }

            if (!var2) {
               return;
            }
         }

         var10000 = this;
      }

      RemotePlayer var3 = var10000.P;
      if (!var2) {
         if (var10000.P == null) {
            return;
         }

         var3 = this.P;
      }

      var3.setPos(var1.getX(this.P.getX()), var1.getY(this.P.getY()), var1.getZ(this.P.getZ()));
      if (!var2) {
         if (!var1.hasRotation()) {
            return;
         }

         this.P.setYRot(var1.getYRot(this.P.getYRot()));
         this.P.setYHeadRot(var1.getYRot(this.P.getYRot()));
      }

      this.P.setXRot(var1.getXRot(this.P.getXRot()));
   }

   private void f$V() {
      boolean var1 = Scaffold.k();

      while (!this.m.isEmpty()) {
         label25: {
            Packet var2 = (Packet)this.m.poll();
            com.elowen.utils.PacketUtils.c(var2);
            if (!var1) {
               if (!(var2 instanceof ServerboundMovePlayerPacket)) {
                  break label25;
               }

               this.K++;
               this.T((ServerboundMovePlayerPacket)var2);
            }

            if (!var1) {
               break;
            }
         }

         if (var1) {
            break;
         }
      }
   }

   @Override
   public void h$V() {
      this.m.clear();
      boolean var10000 = Scaffold.S$Z();
      this.B = 0;
      this.T = false;
      boolean var1 = var10000;
      this.F = this.o.w();
      Blink var2 = this;
      if (var1) {
         if (this.F) {
            this.U = G.player.getX();
            this.X = G.player.getY();
            this.i = G.player.getZ();
            this.d = G.player.getYRot();
            this.l = G.player.getXRot();
            this.P = null;
            if (var1) {
               return;
            }
         }

         this.P = new BlinkingPlayer(G.player);
         this.P.setSprinting(G.player.isSprinting());
         var2 = this;
      }

      var2.P.setId(-1337);
      G.level.addEntity(this.P);
   }

   @Override
   public void q$V() {
      boolean var1 = Scaffold.S$Z();
      if (!this.o.w() && this.P != null) {
         G.level.removeEntity(this.P.getId(), RemovalReason.DISCARDED);
         this.P = null;
      }

      this.F = false;
   }

   @EventTarget
   public void r(EventRender2D var1) {
      try {
         this.C.F(true);
         int var2 = G.getWindow().getGuiScaledWidth() / 2 - 50;
         int var3 = G.getWindow().getGuiScaledHeight() / 2 + 15;
         this.h.G$Canvas();
         this.q(this.h, this.C.l, var2, var3);
         this.h.p();
         this.h.G$V();
      } catch (RuntimeException var4) {
      }
   }

   private boolean p(double var1) {
      boolean var3;
      AABB var4;
      label42: {
         var3 = Scaffold.k();
         Blink var10000 = this;
         if (!var3) {
            if (this.o.w()) {
               var4 = new AABB(this.U - 0.3, this.X, this.i - 0.3, this.U + 0.3, this.X + 1.8, this.i + 0.3);
               if (!var3) {
                  break label42;
               }
            }

            var10000 = this;
         }

         RemotePlayer var8 = var10000.P;
         if (!var3) {
            if (var10000.P == null) {
               return false;
            }

            var8 = this.P;
         }

         var4 = var8.getBoundingBox();
      }

      AABB var5 = var4;
      long var6 = G.level.players().stream().filter(var0 -> deobfLambda$isPlayerNear$0(var5, var1, var0)).count();
      long var10;
      int var9 = (var10 = var6 - 0L) == 0L ? 0 : (var10 < 0L ? -1 : 1);
      if (!var3) {
         var9 = var9 > 0 ? 1 : 0;
      }

      return (boolean)((var9) != 0);
   }

   private boolean w(double var1) {
      boolean var3;
      Vec3 var4;
      label42: {
         var3 = Scaffold.k();
         Blink var10000 = this;
         if (!var3) {
            if (this.o.w()) {
               var4 = new Vec3(this.U, this.X + 0.9, this.i);
               if (!var3) {
                  break label42;
               }
            }

            var10000 = this;
         }

         RemotePlayer var8 = var10000.P;
         if (!var3) {
            if (var10000.P == null) {
               return false;
            }

            var8 = this.P;
         }

         var4 = var8.position();
      }

      Vec3 var12 = var4;
      Stream var5 = StreamSupport.stream(G.level.entitiesForRendering().spliterator(), true);
      long var6 = var5.filter(var0 -> deobfLambda$isTNTNear$0(var12, var1, (Entity)var0)).count();
      long var10;
      int var9 = (var10 = var6 - 0L) == 0L ? 0 : (var10 < 0L ? -1 : 1);
      if (!var3) {
         var9 = var9 > 0 ? 1 : 0;
      }

      return (boolean)((var9) != 0);
   }

   private boolean g(double var1) {
      boolean var10000 = Scaffold.S$Z();
      Iterator var4 = G.level.entitiesForRendering().iterator();
      boolean var3 = var10000;

      while (true) {
         if (var4.hasNext()) {
            Entity var5 = (Entity)var4.next();
            BasicProjectileData var6 = null;
            var10000 = var5 instanceof Arrow;
            if (!var3) {
               break;
            }

            label66: {
               if (var3) {
                  if (var10000) {
                     var6 = this.y;
                     break label66;
                  }

                  var10000 = var5 instanceof ThrownEgg;
               }

               if (var3) {
                  if (var10000) {
                     var6 = this.t;
                     break label66;
                  }

                  var10000 = var5 instanceof Snowball;
               }

               if (var10000) {
                  var6 = this.c;
               }
            }

            if (var6 != null) {
               var10000 = this.E(var5, var6, var1);
               if (!var3) {
                  return var10000;
               }

               if (var10000) {
                  return true;
               }
            }

            if (var3) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   private boolean E(Entity var1, ProjectileData var2, double var3) {
      LocalPlayer var6 = G.player;
      ClientLevel var7 = G.level;
      double var8 = var1.getX();
      boolean var10000 = Scaffold.S$Z();
      double var10 = var1.getY();
      boolean var5 = var10000;
      double var12 = var1.getZ();
      double var14 = var1.getDeltaMovement().x;
      double var16 = var1.getDeltaMovement().y;
      double var18 = var1.getDeltaMovement().z;
      Blink var33 = this;
      if (var5) {
         if (this.o.w()) {
            AABB var30 = new AABB(this.U - 0.3, this.X, this.i - 0.3, this.U + 0.3, this.X + 1.8, this.i + 0.3).inflate(var3);
            Vec3 var31 = new Vec3(var8, var10, var12);
            Vec3 var32 = var31.add(new Vec3(var14, var16, var18));
            return var30.intersects(var31, var32);
         }

         var33 = this;
      }

      RemotePlayer var20 = var33.P;
      if (var20 == null) {
         return false;
      }

      while (true) {
         float var21 = var2.h$F();
         float var22 = var2.P();
         AABB var23 = new AABB(var8 - var21, var10, var12 - var21, var8 + var21, var10 + var22, var12 + var21);
         Vec3 var24 = new Vec3(var8, var10, var12);
         Vec3 var25 = new Vec3(var8 + var14, var10 + var16, var12 + var18);
         HitResult var26 = com.elowen.utils.RayTraceUtils.g(var24, var25, false, var1 instanceof Arrow, false, var1);
         List var27 = var7.getEntities(var6, var23.contract(var14, var16, var18).expandTowards(1.0, 1.0, 1.0).inflate(var3, var3, var3));
         if (var27.contains(var20)) {
            var10000 = true;
            if (var5) {
               return true;
            }
         } else {
            var8 += var14;
            var10 += var16;
            var12 += var18;
            var10000 = var26.getType().equals(Type.MISS);
         }

         if (!var5) {
            break;
         }

         label69:
         if (var10000) {
            double var35 = var10;
            if (var5) {
               if (var10 < -128.0) {
                  break label69;
               }

               var35 = var14;
            }

            var14 = var35 * (var1.isInWater() ? 0.8 : 0.99);
            double var28 = var16 * (var1.isInWater() ? 0.8 : 0.99);
            var18 *= var1.isInWater() ? 0.8 : 0.99;
            var16 = var28 - var2.y$F();
            continue;
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   private boolean a$Z() {
      boolean var1 = Scaffold.S$Z();
      return this.w(this.f.o$F()) || this.p(this.p.o$F()) || this.g(this.Z.o$F());
   }

   @Override
   public void M(boolean var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player != null) {
         if (var1) {
            super.M(true);
         }

         if (!this.T) {
            this.T = true;
         }

         if (this.m.isEmpty()) {
            super.M(false);
         }
      }
   }

   @EventTarget
   public void t(com.elowen.events.impl.EventMotion var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE) {
         if (G.player == null || G.level == null || G.player.isDeadOrDying()) {
            if (!this.m.isEmpty()) {
               com.elowen.utils.PacketUtils.c((Packet)this.m.poll());
            }

            this.T = false;
            this.B = 0;
            super.M(false);
            return;
         }

         boolean var3 = this.o.w();
         if (var3 != this.F) {
            if (!this.F && this.P != null) {
               G.level.removeEntity(this.P.getId(), RemovalReason.DISCARDED);
               this.P = null;
            }

            if (var3) {
               this.U = G.player.getX();
               this.X = G.player.getY();
               this.i = G.player.getZ();
               this.d = G.player.getYRot();
               this.l = G.player.getXRot();
            }

            this.P = new BlinkingPlayer(G.player);
            this.P.setSprinting(G.player.isSprinting());
            this.P.setId(-1337);
            G.level.addEntity(this.P);
            this.F = var3;
         }

         this.X(this.O$h() + " Ticks Behind");
         this.C.J = Mth.clamp((float)this.O$h() / this.V.o$F() * 100.0F, 0.0F, 100.0F);
         this.K = 0;
         if (G.player.hurtTime == 10) {
            this.B = this.B + (int)this.x.o$F();
         }

         if (this.K < this.D.o$F() && this.B > 0 && !this.m.isEmpty()) {
            this.f$V();
            this.B--;
         }

         if (this.K < this.D.o$F() && this.a$Z() && !this.m.isEmpty()) {
            this.f$V();
         }

         if (this.K < this.D.o$F() && (float)this.O$h() >= this.V.o$F() && !this.m.isEmpty()) {
            this.f$V();
         }

         if (this.T) {
            if (this.K < this.D.o$F() && !this.m.isEmpty()) {
               this.f$V();
            }

            if (this.m.isEmpty()) {
               this.M(false);
            }
         }
      }
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   @EventTarget(4)
   public void e(EventPacket var1) {
      boolean var2 = Scaffold.k();
      EventType var10000 = var1.M();
      EventType var10001 = com.elowen.events.api.types.EventType.RECEIVE;
      if (!var2) {
         if (var10000 == com.elowen.events.api.types.EventType.RECEIVE) {
            Packet var3 = var1.R$Packet();
            boolean var6 = var3 instanceof ClientboundRespawnPacket;
            if (!var2) {
               if (!var6) {
                  var6 = var3 instanceof ClientboundDisconnectPacket;
                  if (!var2) {
                     if (!var6) {
                        return;
                     }

                     var6 = this.m.isEmpty();
                  }
               } else {
                  var6 = this.m.isEmpty();
               }
            }

            while (true) {
               if (var6) {
                  this.T = false;
                  this.B = 0;
                  break;
               }

               com.elowen.utils.PacketUtils.c((Packet)this.m.poll());
               if (var2) {
                  break;
               }

               if (var2) {
                  this.T = false;
                  this.B = 0;
                  break;
               }

               var6 = this.m.isEmpty();
            }

            Blink var7 = this;
            if (!var2) {
               if (!this.w()) {
                  return;
               }

               var7 = this;
            }

            var7.M(false);
            return;
         }

         var10000 = var1.M();
         var10001 = com.elowen.events.api.types.EventType.SEND;
      }

      if (var10000 == var10001) {
         Minecraft var4 = G;
         if (!var2) {
            if (G.player == null) {
               return;
            }

            var4 = G;
         }

         if (!var2) {
            if (var4.level == null) {
               return;
            }

            var4 = G;
         }

         boolean var5 = var4.player.isDeadOrDying();
         if (!var2) {
            if (var5) {
               return;
            }

            var5 = R.contains(var1.R$Packet().getClass());
         }

         if (!var2) {
            if (var5) {
               return;
            }

            var1.c(true);
            this.m.offer(var1.R$Packet());
         }
      }
   }

   private static boolean deobfLambda$isTNTNear$0(Vec3 var0, double var1, Entity var3) {
      boolean var4 = Scaffold.k();
      int var10000 = ((var3 instanceof PrimedTnt) ? 1 : 0);
      if (!var4) {
         if (var10000 == 0) {
            return false;
         }

         double var5;
         var10000 = (byte)((var5 = var0.distanceTo(var3.position()) - var1) == 0.0 ? 0 : (var5 < 0.0 ? -1 : 1));
      }

      return (boolean)(var4 ? var10000 : var10000 <= 0);
   }

   private static boolean deobfLambda$isPlayerNear$0(AABB var0, double var1, AbstractClientPlayer var3) {
      boolean var4 = Scaffold.S$Z();
      if (var3 == G.player) {
         return false;
      }

      if (var3 instanceof BlinkingPlayer) {
         return false;
      }

      if (Teams.T(var3)) {
         return false;
      }

      if (com.elowen.utils.FriendManager.A(var3)) {
         return false;
      }

      if (AntiBots.g(var3)) {
         return false;
      }

      Vec3 var5 = var3.getEyePosition();
      Vec3 var6 = com.elowen.utils.rotation.RotationUtils.x(var5, var0);
      return var5.distanceTo(var6) < var1;
   }

   private static boolean deobfLambda$getBlinkTicks$0(Packet var0) {
      return var0 instanceof ServerboundMovePlayerPacket;
   }

   static {
      R = new Blink$1();
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
