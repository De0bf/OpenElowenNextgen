package com.elowen.modules.impl.move;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventStuckInBlock;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.utils.PlayerUtils;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

@ModuleInfo(R = "FastWeb", M = Category.MOVEMENT, a = "Allows you to walk faster on cobwebs")
public class FastWeb extends Module {
   public final FloatValue D;
   public final FloatValue C;
   public final FloatValue l;
   private final Queue U;
   private boolean y;
   private int S;
   private int p;
   private boolean j;
   public boolean B;
   private static final String[] b = new String[]{"Up Speed", "Down Speed", "Horizontal Multiplier"};
   public FastWeb() {
      String[] var1 = b;
      this.D = ValueBuilder.m(this, "Horizontal Multiplier").d(0.66F).w(0.01F).M(0.8F).V(0.01F).f$K().L();
      this.C = ValueBuilder.m(this, "Up Speed").d(0.07F).w(0.01F).M(0.07F).V(0.01F).f$K().L();
      this.l = ValueBuilder.m(this, "Down Speed").d(0.18F).w(0.01F).M(0.18F).V(0.01F).f$K().L();
      this.U = new ConcurrentLinkedQueue();
      this.y = false;
      this.S = -1;
      this.p = -1;
      this.j = false;
      this.B = false;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void X$V() {
      boolean var10000 = Scaffold.S$Z();
      ClientPacketListener var2 = G.getConnection();
      boolean var1 = var10000;
      if (var1) {
         if (var2 != null) {
            while (!this.U.isEmpty()) {
               label34: {
                  Packet var3 = (Packet)this.U.poll();
                  Packet var7 = var3;
                  if (var1) {
                     if (var3 == null) {
                        break label34;
                     }

                     try {
                        var7 = var3;
                     } catch (Exception var6) {
                        var6.printStackTrace();
                        break label34;
                     }
                  }

                  try {
                     var7.handle(var2);
                  } catch (Exception var5) {
                     var5.printStackTrace();
                  }
               }

               if (!var1) {
                  break;
               }
            }

            return;
         }

         this.U.clear();
      }
   }

   @EventTarget
   public void p(EventPacket var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player != null) {
         if (var1.M() == EventType.RECEIVE) {
            if (!var1.c$Z()) {
               Packet var3 = var1.R$Packet();
               if (G.player.tickCount == this.S) {
                  if (var3 instanceof ClientboundSetEntityMotionPacket var4 && var4.id() == com.elowen.utils.PlayerUtils.S$I()) {
                     this.y = true;
                     this.U.add(var3);
                     var1.c(true);
                  } else {
                     if (this.y && var3 instanceof ClientboundPingPacket) {
                        this.U.add(var3);
                        var1.c(true);
                     }
                  }
               }
            }
         }
      }
   }

   @EventTarget
   public void v(EventStuckInBlock var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player != null) {
         if (!Velocity.r()) {
            if (var1.k().getBlock() != Blocks.COBWEB) {
               if (!this.U.isEmpty()) {
                  this.X$V();
               }

               this.y = false;
            } else {
               this.S = G.player.tickCount;
               if (this.y) {
                  this.X$V();
                  this.y = false;
               } else {
                  var1.f(new Vec3(1.0, 1.0, 1.0));
               }
            }
         }
      }
   }

   @EventTarget
   public void o(EventMotion var1) {
      boolean var2 = Scaffold.S$Z();
      if (G.player != null && var1.Q() == EventType.PRE) {
         if (!Velocity.r()) {
            if (G.player.tickCount != this.S) {
               this.p = -1;
               this.j = false;
               this.B = false;
            } else {
               this.B = true;
               if (!this.y) {
                  int var3 = G.player.tickCount;
                  if (var3 != this.S) {
                     this.p = -1;
                     this.j = false;
                  } else {
                     boolean var4 = G.options.keyUp.isDown() || G.options.keyDown.isDown() || G.options.keyLeft.isDown() || G.options.keyRight.isDown();
                     if (var4 != this.j) {
                        this.p = var3;
                     }

                     this.j = var4;
                     if (var3 == this.p) {
                        double var5 = 0.0;
                        double var7 = 0.0;
                     }

                     float var9 = 0.0F;
                     float var10 = 0.0F;
                     if (G.options.keyUp.isDown()) {
                        var9++;
                     }

                     if (G.options.keyDown.isDown()) {
                        var9--;
                     }

                     if (G.options.keyLeft.isDown()) {
                        var10++;
                     }

                     if (G.options.keyRight.isDown()) {
                        var10--;
                     }

                     if (var9 != 0.0F && var10 != 0.0F) {
                        var9 *= 0.707F;
                        var10 *= 0.707F;
                     }

                     double var11 = G.player.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.D.o$F();
                     if (G.player.isInWater()) {
                        var11 *= 1.3;
                     }

                     float var13 = G.player.getYRot();
                     double var14 = Math.toRadians(var13);
                     double var16 = (-Math.sin(var14) * var9 + Math.cos(var14) * var10) * var11;
                     double var17 = (Math.cos(var14) * var9 + Math.sin(var14) * var10) * var11;
                     double var18 = G.options.keyJump.isDown() ? this.C.o$F() : (G.options.keyShift.isDown() ? -this.l.o$F() : 0.0);
                     G.player.setDeltaMovement(var16, var18, var17);
                     G.player.fallDistance = 0.0;
                  }
               }
            }
         }
      }
   }

   @Override
   public void h$V() {
      this.S = -1;
      this.p = -1;
      this.j = false;
      this.y = false;
      this.U.clear();
   }

   @Override
   public void q$V() {
      this.X$V();
      this.y = false;
      this.S = -1;
      this.p = -1;
      this.j = false;
      this.B = false;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
