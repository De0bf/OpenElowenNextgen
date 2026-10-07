package com.elowen.modules.impl.move;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUseItem;
import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventSlowdown;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventGlobalPacket;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.utils.PacketUtils;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.ModeValue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "NoSlow", a = "Removes item-use slowdown", M = Category.MOVEMENT)
public class NoSlow extends Module {
   public ModeValue B;
   private static NoSlow$State I;
   private final Queue Y;
   private boolean i;
   private boolean M;
   private int z;
   private long j;
   private boolean J;
   private InteractionHand y;
   private int l;
   private int o;
   private boolean U;
   private boolean p;
   private int x;
   private boolean q;
   private boolean R;
   private boolean C;
   private ClientLevel v;
   private boolean D;
   private int X;
   private static final String[] b = new String[]{"Vanilla", "100%", "100%", "Vanilla", "50%", "70%", "50%", "30%", "30%", "Mode", "70%"};
   public NoSlow() {
      String[] var1 = b;
      this.B = ValueBuilder.m(this, "Mode").m(0).W(new String[]{"100%", "70%", "50%", "30%", "Vanilla"}).f$K().T$t();
      this.Y = new ConcurrentLinkedQueue();
      this.D = false;
      this.X = 0;
   }

   public static boolean A$Z() {
      return I != com.elowen.modules.impl.move.NoSlow$State.NONE;
   }

   private boolean B$Z() {
      boolean var1 = Scaffold.S$Z();
      return this.w() && this.B.t("100%");
   }

   private boolean W$Z() {
      boolean var10000 = Scaffold.k();
      String var2 = this.B.C();
      boolean var1 = var10000;
      var10000 = this.w();
      if (!var1) {
         if (!var10000) {
            return false;
         }

         var10000 = "70%".equals(var2);
      }

      if (var1) {
         return var10000;
      }

      if (var10000) {
         return true;
      }

      var10000 = "50%".equals(var2);
      if (var1) {
         return var10000;
      }

      if (var10000) {
         return true;
      }

      var10000 = "30%".equals(var2);
      return var1 ? var10000 : var10000;
   }

   private boolean U$Z() {
      boolean var1 = Scaffold.S$Z();
      return this.w() && this.B.t("Vanilla");
   }

   @Override
   public void h$V() {
      this.F();
      this.I();
   }

   @Override
   public void q$V() {
      this.z$V();
      this.I();
   }

   @EventTarget
   public void w(EventTick var1) {
      boolean var2 = Scaffold.S$Z();
      if (var1.s$f() == EventType.PRE) {
         if (this.B$Z()) {
            if (G.player == null) {
               this.F();
            } else if (this.v != G.level) {
               this.v = G.level;
               this.z$V();
            } else {
               this.i = false;
               if (I == com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                  this.F();
               } else {
                  this.O$h();
                  if (I == com.elowen.modules.impl.move.NoSlow$State.FINISH_AFTER_MOVEMENT) {
                     this.j$V();
                  } else {
                     if (I == com.elowen.modules.impl.move.NoSlow$State.USING && !G.player.isUsingItem()) {
                        this.C = false;
                        if (this.q) {
                           G.options.keyUse.setDown(false);
                        }

                        this.z = 0;
                        I = this.M ? com.elowen.modules.impl.move.NoSlow$State.WAIT_RESTORE_MOVEMENT : com.elowen.modules.impl.move.NoSlow$State.WAIT_STOP_MOVEMENT;
                     }
                  }
               }
            }
         } else if (!this.W$Z()) {
            this.I();
         } else if (G.player == null) {
            this.I();
         } else if (G.player.getUseItemRemainingTicks() <= 0) {
            this.I();
         } else {
            ItemStack var3 = G.player.getUseItem();
            if (var3 == null || var3.isEmpty()) {
               this.I();
            } else if (var3.getItem() instanceof BowItem) {
               this.I();
            } else {
               if (!this.D) {
                  this.D = true;
                  this.X = 0;
               }

               this.X++;
            }
         }
      }
   }

   @EventTarget
   public void t(EventUpdate var1) {
      boolean var2 = Scaffold.k();
      if (var1.a$f() == EventType.PRE) {
         if (this.B$Z()) {
            if (G.player != null && I == com.elowen.modules.impl.move.NoSlow$State.NONE) {
               label33: {
                  Scaffold var3 = (Scaffold)Elowen.S$Elowen().q$ModuleManager().A(Scaffold.class);
                  boolean var10000 = var3.w();
                  if (!var2) {
                     if (var10000) {
                        break label33;
                     }

                     var10000 = G.player.getMainHandItem().is(Items.ENDER_PEARL);
                  }

                  if (!var10000) {
                     return;
                  }
               }

               this.z$V();
            }
         }
      }
   }

   @EventTarget
   public void g(EventSlowdown var1) {
      NoSlow var4 = null;
      boolean var2 = Scaffold.k();
      boolean var10000 = this.w();
      if (!var2) {
         if (!var10000) {
            return;
         }

         var10000 = this.B$Z();
      }

      label74: {
         if (!var2) {
            if (var10000) {
               if (G.player != null && !this.i && I == com.elowen.modules.impl.move.NoSlow$State.USING) {
                  if (!this.C) {
                     return;
                  }

                  ItemStack var3 = G.player.getUseItem();
                  var10000 = this.K(var3);
                  if (!var2) {
                     if (var10000) {
                        return;
                     }

                     var10000 = this.H(var3.getUseAnimation());
                  }

                  if (var10000) {
                     var1.N(false);
                  }

                  return;
               }

               return;
            }

            var4 = this;
            if (var2) {
               break label74;
            }

            var10000 = this.U$Z();
         }

         if (var10000) {
            var1.N(false);
            return;
         }

         var4 = this;
      }

      var4.v(var1);
   }

   private boolean D$h() {
      boolean var1 = Scaffold.k();
      LocalPlayer var10000 = G.player;
      if (!var1) {
         if (G.player == null) {
            return false;
         }

         var10000 = G.player;
      }

      boolean var2 = var10000.isPassenger();
      if (!var1) {
         if (var2) {
            return false;
         }

         var2 = G.player.isMobilityRestricted();
      }

      if (!var1) {
         if (var2) {
            return false;
         }

         var2 = G.player.getAbilities().mayfly;
      }

      label100:
      if (!var1) {
         if (!var2) {
            var2 = G.player.getFoodData().hasEnoughFood();
            if (var1) {
               break label100;
            }

            if (!var2) {
               return false;
            }
         }

         var2 = G.player.getAbilities().flying;
      }

      label93:
      if (!var1) {
         if (!var2) {
            var2 = G.player.isInShallowWater();
            if (var1) {
               break label93;
            }

            if (var2) {
               return false;
            }
         }

         var2 = G.player.input.hasForwardImpulse();
      }

      if (!var1) {
         if (!var2) {
            return false;
         }

         var2 = G.player.horizontalCollision;
      }

      if (!var1) {
         if (var2) {
            if (var1) {
               return G.player.minorHorizontalCollision;
            }

            if (!G.player.minorHorizontalCollision) {
               return false;
            }
         }

         var2 = true;
      }

      return var2;
   }

   private void v(EventSlowdown var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.D) {
         if (this.X >= 2) {
            String var3 = this.B.C();
            String var4 = var3;
            byte var5 = -1;
            switch (var4.hashCode()) {
               case 54380:
                  if (!var4.equals("70%")) {
                     break;
                  }

                  var5 = 0;
               case 52458:
                  if (!var4.equals("50%")) {
                     break;
                  }

                  var5 = 1;
               case 50536:
                  if (var4.equals("30%")) {
                     var5 = 2;
                  }
            }

            switch (var5) {
               case 0:
                  int var6 = (this.X - 2) % 3;
                  if (var6 == 0) {
                     break;
                  }

                  var1.N(false);
                  if (!this.D$h()) {
                     break;
                  }

                  G.player.setSprinting(true);
                  break;
               case 1:
                  int var7 = (this.X - 2) % 2;
                  if (var7 != 1) {
                     break;
                  }

                  var1.N(false);
                  if (!this.D$h()) {
                     break;
                  }

                  G.player.setSprinting(true);
                  break;
               case 2:
                  int var8 = (this.X - 2) % 3;
                  if (var8 == 0) {
                     var1.N(false);
                     if (this.D$h()) {
                        G.player.setSprinting(true);
                     }
                  }
            }
         }
      }
   }

   @EventTarget(0)
   public void C(EventUseItem var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.B$Z()) {
         if (G.player != null) {
            if (!var1.C()) {
               if (!(var1.h$ItemStack().getItem() instanceof BucketItem)
                  || G.hitResult == null
                  || G.hitResult.getType() != Type.BLOCK
                  || !G.level.getFluidState(((BlockHitResult)G.hitResult).getBlockPos()).isSource()) {
                  if (I != com.elowen.modules.impl.move.NoSlow$State.NONE && I != com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                     var1.c(true);
                  } else if (var1.D() == InteractionHand.MAIN_HAND
                     && !this.H(var1.h$ItemStack().getUseAnimation())
                     && this.H(G.player.getOffhandItem().getUseAnimation())) {
                     ItemStack var3 = var1.h$ItemStack();
                     if (var3.is(Items.ENDER_PEARL)) {
                        if (G.player.getCooldowns().isOnCooldown(var3)) {
                           var1.c(true);
                        }
                     } else if (!var3.is(Items.FIRE_CHARGE)) {
                        InteractionResult var4 = var1.h$ItemStack().copy().use(G.level, G.player, InteractionHand.MAIN_HAND);
                        if (!var4.consumesAction()) {
                           var1.c(true);
                        }
                     }
                  } else {
                     if (this.I(var1.h$ItemStack()) && !this.K(var1.h$ItemStack())) {
                        I = com.elowen.modules.impl.move.NoSlow$State.PREPARING;
                     }
                  }
               }
            }
         }
      }
   }

   @EventTarget(0)
   public void f(EventGlobalPacket var1) {
      boolean var2 = Scaffold.k();
      if (this.w()) {
         Packet var3 = var1.g$Packet();
         if (this.B$Z()) {
            if (G.player != null) {
               if (var1.f$f() == EventType.SEND) {
                  label154: {
                     Packet var4;
                     label163: {
                        boolean var10000 = var3 instanceof ServerboundMovePlayerPacket;
                        if (!var2) {
                           if (var10000) {
                              label108: {
                                 boolean var9 = var3 instanceof Rot;
                                 if (!var2) {
                                    if (var9) {
                                       break label108;
                                    }

                                    var9 = var3 instanceof StatusOnly;
                                 }

                                 if (!var9) {
                                    this.j = System.currentTimeMillis();
                                 }
                              }

                              this.w$h();
                              this.U$V();
                              return;
                           }

                           var4 = var3;
                           if (var2) {
                              break label163;
                           }

                           var10000 = var3 instanceof ServerboundPlayerActionPacket;
                        }

                        if (!var10000) {
                           break label154;
                        }

                        var4 = var3;
                     }

                     if (((ServerboundPlayerActionPacket)var4).getAction() == Action.RELEASE_USE_ITEM && I == com.elowen.modules.impl.move.NoSlow$State.USING) {
                        NoSlow var8 = this;
                        if (!var2) {
                           if (this.J) {
                              var1.c(true);
                              this.p = true;
                              this.x = 0;
                              if (!var2) {
                                 return;
                              }
                           }

                           var8 = this;
                        }

                        var8.C = false;
                        I = com.elowen.modules.impl.move.NoSlow$State.WAIT_RELEASE_BOUNDARY;
                        return;
                     }
                  }

                  boolean var6;
                  label174: {
                     label142: {
                        label141: {
                           HitResult var5 = G.hitResult;
                           if (!var2) {
                              if (G.hitResult == null) {
                                 break label141;
                              }

                              var5 = G.hitResult;
                           }

                           if (var5.getType() != Type.MISS) {
                              var6 = var3 instanceof ServerboundUseItemPacket;
                              if (!var2) {
                                 if (var6) {
                                    break label142;
                                 }

                                 var6 = var3 instanceof ServerboundUseItemOnPacket;
                              }

                              if (var2) {
                                 break label174;
                              }

                              if (var6) {
                                 break label142;
                              }
                           }
                        }

                        var6 = this.i;
                        break label174;
                     }

                     NoSlow var7 = this;
                     if (!var2) {
                        this.i = true;
                        if (I != com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                           return;
                        }

                        var7 = this;
                     }

                     var7.F();
                     return;
                  }

                  if (!var2) {
                     if (var6) {
                        return;
                     }

                     var6 = var3 instanceof ServerboundUseItemPacket;
                  }

                  if (var6) {
                     this.w(var1, (ServerboundUseItemPacket)var3);
                  }
               }
            }
         }
      }
   }

   private boolean f$Z() {
      boolean var1 = Scaffold.S$Z();
      return G.player != null
         && G.player.getMainHandItem().getItem() instanceof BucketItem
         && G.hitResult != null
         && G.hitResult.getType() == Type.BLOCK
         && G.level.getFluidState(((BlockHitResult)G.hitResult).getBlockPos()).isSource();
   }

   private void w(EventGlobalPacket var1, ServerboundUseItemPacket var2) {
      boolean var3 = Scaffold.S$Z();
      if (G.player != null) {
         if (!this.f$Z()) {
            ItemStack var4 = G.player.getItemInHand(var2.hand());
            if (!this.h(var4) && !this.K(var4)) {
               ItemUseAnimation var5 = var4.getUseAnimation();
               if (!this.H(var5)) {
                  if (I == com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                     this.F();
                  }
               } else if (this.E$Z()) {
                  if (I == com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                     this.F();
                  }
               } else if (!this.b$Z() && var5 != ItemUseAnimation.BOW && var5 != ItemUseAnimation.CROSSBOW && var5 != ItemUseAnimation.SPEAR) {
                  var1.c(true);
                  this.E(true);
                  this.U = true;
                  this.M = true;
                  InteractionHand var6 = var2.hand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                  if (this.J) {
                     this.g$V();
                  }

                  this.J = true;
                  this.y = var6;
                  this.l = var2.sequence();
                  this.o = 0;
               } else {
                  this.E(false);
               }
            } else {
               if (I == com.elowen.modules.impl.move.NoSlow$State.PREPARING) {
                  this.F();
               }
            }
         }
      }
   }

   @EventTarget(0)
   public void U(EventPacket var1) {
      boolean var2 = Scaffold.S$Z();
      if (this.w() && G.player != null && var1.M() == EventType.RECEIVE) {
         if (!var1.c$Z()) {
            if (this.B$Z()) {
               this.A(var1, var1.R$Packet());
            }
         }
      }
   }

   private void A(EventPacket var1, Packet var2) {
      boolean var3 = Scaffold.k();
      boolean var10000 = this.R;
      if (!var3) {
         if (!this.R) {
            return;
         }

         var10000 = var2 instanceof ClientboundPlayerPositionPacket;
      }

      if (!var3) {
         if (var10000) {
            var1.c(true);
            this.Y.add(var2);
            this.x$h();
            this.R = true;
            return;
         }

         var10000 = this.S(var2);
      }

      if (!var3 && !var10000) {
         var1.c(true);
         this.Y.add(var2);
      }
   }

   private void E(boolean var1) {
      this.q = var1;
      this.C = true;
      I = com.elowen.modules.impl.move.NoSlow$State.USING;
      this.l$V();
   }

   private void l$V() {
      boolean var1 = Scaffold.k();
      NoSlow var10000 = this;
      if (!var1) {
         if (this.R) {
            return;
         }

         this.R = true;
         var10000 = this;
      }

      var10000.Y.clear();
   }

   private void g$V() {
      boolean var1 = Scaffold.k();
      NoSlow var10000 = this;
      if (!var1) {
         if (!this.J) {
            return;
         }

         this.O$h();
         var10000 = this;
      }

      var10000.J = false;
      Minecraft var2 = G;
      if (!var1) {
         if (G.getConnection() == null) {
            return;
         }

         var2 = G;
      }

      if (var2.player != null) {
         PacketUtils.c(new ServerboundUseItemPacket(this.y, this.l, G.player.getYRot(), G.player.getXRot()));
      }
   }

   private void w$h() {
      NoSlow var3 = null;
      boolean var1 = Scaffold.k();
      int var10000 = ((this.J) ? 1 : 0);
      if (!var1) {
         if (this.J) {
            NoSlow var2 = this;
            int var10001 = this.o + 1;
            if (!var1) {
               if ((this.o = var10001) < 2) {
                  return;
               }

               this.g$V();
               var2 = this;
               var10001 = 0;
            }

            var2.x = var10001;
            if (!var1) {
               return;
            }
         }

         var10000 = ((this.p) ? 1 : 0);
      }

      label56: {
         if (!var1) {
            if (var10000 == 0) {
               return;
            }

            var3 = this;
            if (var1) {
               break label56;
            }

            var10000 = ++this.x;
         }

         if (var10000 < 1) {
            return;
         }

         var3 = this;
      }

      var3.L();
   }

   private void L() {
      boolean var1 = Scaffold.S$Z();
      if (this.p) {
         this.p = false;
         if (G.getConnection() != null) {
            PacketUtils.c(new ServerboundPlayerActionPacket(Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
            this.C = false;
            I = com.elowen.modules.impl.move.NoSlow$State.WAIT_RELEASE_BOUNDARY;
         }
      }
   }

   private void U$V() {
      boolean var1 = Scaffold.S$Z();
      if (I == com.elowen.modules.impl.move.NoSlow$State.WAIT_RELEASE_BOUNDARY) {
         this.z = 0;
         I = this.M ? com.elowen.modules.impl.move.NoSlow$State.WAIT_RESTORE_MOVEMENT : com.elowen.modules.impl.move.NoSlow$State.FINISH_AFTER_MOVEMENT;
      } else if (I == com.elowen.modules.impl.move.NoSlow$State.WAIT_RESTORE_MOVEMENT) {
         if (++this.z >= 2) {
            this.z = 0;
            this.W$V();
            this.M = false;
            I = com.elowen.modules.impl.move.NoSlow$State.FINISH_AFTER_MOVEMENT;
         }
      } else {
         if (I == com.elowen.modules.impl.move.NoSlow$State.WAIT_STOP_MOVEMENT) {
            I = com.elowen.modules.impl.move.NoSlow$State.FINISH_AFTER_MOVEMENT;
         }
      }
   }

   private void j$V() {
      this.x$h();
      this.F();
   }

   private void z$V() {
      NoSlow var2;
      boolean var10000 = Scaffold.S$Z();
      this.x$h();
      boolean var1 = var10000;
      var2 = this;
      label24:
      if (var1) {
         if (this.M) {
            var2 = this;
            if (!var1) {
               break label24;
            }

            if (!this.U) {
               this.W$V();
            }
         }

         var2 = this;
      }

      var2.F();
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void x$h() {
      boolean var10000 = Scaffold.S$Z();
      this.R = false;
      boolean var1 = var10000;
      ClientPacketListener var2 = G.getConnection();
      if (var1) {
         if (var2 != null) {
            while (!this.Y.isEmpty()) {
               label38: {
                  Packet var3 = (Packet)this.Y.poll();
                  Packet var7 = var3;
                  if (var1) {
                     if (var3 == null && var1) {
                        continue;
                     }

                     try {
                        var7 = var3;
                     } catch (Exception var6) {
                        break label38;
                     }
                  }

                  try {
                     var7.handle(var2);
                  } catch (Exception var5) {
                  }
               }

               if (!var1) {
                  break;
               }
            }

            return;
         }

         this.Y.clear();
      }
   }

   private void F() {
      boolean var1 = Scaffold.k();
      NoSlow var10000 = this;
      if (!var1) {
         if (this.U) {
            this.U = false;
            this.M = false;
            this.J = false;
            this.p = false;
         }

         this.g$V();
         this.L();
         I = com.elowen.modules.impl.move.NoSlow$State.NONE;
         this.C = false;
         this.i = false;
         this.M = false;
         this.z = 0;
         this.o = 0;
         this.x = 0;
         this.q = false;
         this.R = false;
         var10000 = this;
      }

      var10000.Y.clear();
   }

   private void O$h() {
      boolean var1 = Scaffold.k();
      NoSlow var10000 = this;
      if (!var1) {
         if (!this.U) {
            return;
         }

         this.U = false;
         var10000 = this;
      }

      var10000.W$V();
   }

   private void W$V() {
      PacketUtils.c(new ServerboundPlayerActionPacket(Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
   }

   private boolean b$Z() {
      return G.crosshairPickEntity != null;
   }

   private boolean h(ItemStack var1) {
      boolean var2 = Scaffold.k();
      boolean var10000 = var1.getItem() instanceof CrossbowItem;
      if (!var2) {
         if (!var10000) {
            return false;
         }

         var10000 = CrossbowItem.isCharged(var1);
      }

      return var2 ? var10000 : var10000;
   }

   private boolean K(ItemStack var1) {
      boolean var2 = Scaffold.k();
      boolean var10000 = var1.is(Items.MUSHROOM_STEW);
      if (!var2) {
         if (!var10000) {
            var10000 = var1.is(Items.RABBIT_STEW);
            if (var2) {
               return var10000;
            }

            if (!var10000) {
               var10000 = var1.is(Items.BEETROOT_SOUP);
               if (var2) {
                  return var10000;
               }

               if (!var10000) {
                  var10000 = var1.is(Items.SUSPICIOUS_STEW);
                  if (var2) {
                     return var10000;
                  }

                  if (!var10000) {
                     return false;
                  }
               }
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private boolean E$Z() {
      boolean var1 = Scaffold.k();
      if (G.player != null) {
         boolean var10000 = this.W(G.player.getMainHandItem());
         if (!var1) {
            if (!var10000) {
               return false;
            }

            var10000 = this.W(G.player.getOffhandItem());
         }

         if (var1) {
            return var10000;
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   private boolean W(ItemStack var1) {
      boolean var10000 = Scaffold.k();
      ItemUseAnimation var3 = var1.getUseAnimation();
      boolean var2 = var10000;
      ItemUseAnimation var4 = var3;
      ItemUseAnimation var10001 = ItemUseAnimation.EAT;
      if (!var2) {
         if (var3 == ItemUseAnimation.EAT) {
            return true;
         }

         var4 = var3;
         var10001 = ItemUseAnimation.DRINK;
      }

      return var4 == var10001;
   }

   private boolean H(ItemUseAnimation var1) {
      boolean var2 = Scaffold.S$Z();
      return var1 == ItemUseAnimation.EAT
         || var1 == ItemUseAnimation.DRINK
         || var1 == ItemUseAnimation.SPEAR
         || var1 == ItemUseAnimation.BOW
         || var1 == ItemUseAnimation.CROSSBOW;
   }

   private boolean I(ItemStack var1) {
      return this.H(var1.getUseAnimation());
   }

   private boolean S(Packet var1) {
      boolean var2 = Scaffold.k();
      if (G.player == null) {
         return false;
      }

      int var10000 = ((var1 instanceof ClientboundSetDisplayObjectivePacket) ? 1 : 0);
      if (!var2) {
         if ((var10000 == 0)) {
            var10000 = ((var1 instanceof ClientboundSetEquipmentPacket) ? 1 : 0);
            if (var2) {
               return ((var10000) != 0);
            }

            if ((var10000 == 0)) {
               var10000 = ((var1 instanceof ClientboundAddEntityPacket) ? 1 : 0);
               if (var2) {
                  return ((var10000) != 0);
               }

               if ((var10000 == 0)) {
                  var10000 = ((var1 instanceof ClientboundRemoveEntitiesPacket) ? 1 : 0);
                  if (var2) {
                     return ((var10000) != 0);
                  }

                  if ((var10000 == 0)) {
                     var10000 = ((var1 instanceof ClientboundSetHealthPacket) ? 1 : 0);
                     if (var2) {
                        return ((var10000) != 0);
                     }

                     if ((var10000 == 0)) {
                        var10000 = ((var1 instanceof ClientboundContainerSetContentPacket) ? 1 : 0);
                        if (var2) {
                           return ((var10000) != 0);
                        }

                        if ((var10000 == 0)) {
                           var10000 = ((var1 instanceof ClientboundEntityEventPacket) ? 1 : 0);
                           if (var2) {
                              return ((var10000) != 0);
                           }

                           if ((var10000 == 0)) {
                              var10000 = ((var1 instanceof ClientboundSetObjectivePacket) ? 1 : 0);
                              if (var2) {
                                 return ((var10000) != 0);
                              }

                              if ((var10000 == 0)) {
                                 var10000 = ((var1 instanceof ClientboundSetScorePacket) ? 1 : 0);
                                 if (var2) {
                                    return ((var10000) != 0);
                                 }

                                 if ((var10000 == 0)) {
                                    var10000 = ((var1 instanceof ClientboundSetSubtitleTextPacket) ? 1 : 0);
                                    if (var2) {
                                       return ((var10000) != 0);
                                    }

                                    if ((var10000 == 0)) {
                                       var10000 = ((var1 instanceof ClientboundSetTitleTextPacket) ? 1 : 0);
                                       if (var2) {
                                          return ((var10000) != 0);
                                       }

                                       if ((var10000 == 0)) {
                                          var10000 = ((var1 instanceof ClientboundMoveEntityPacket) ? 1 : 0);
                                          if (var2) {
                                             return ((var10000) != 0);
                                          }

                                          if ((var10000 == 0)) {
                                             var10000 = ((var1 instanceof ClientboundTeleportEntityPacket) ? 1 : 0);
                                             if (var2) {
                                                return ((var10000) != 0);
                                             }

                                             if ((var10000 == 0)) {
                                                var10000 = ((var1 instanceof ClientboundEntityPositionSyncPacket) ? 1 : 0);
                                                if (var2) {
                                                   return ((var10000) != 0);
                                                }

                                                if ((var10000 == 0)) {
                                                   var10000 = ((var1 instanceof ClientboundMoveVehiclePacket) ? 1 : 0);
                                                   if (var2) {
                                                      return ((var10000) != 0);
                                                   }

                                                   if ((var10000 == 0)) {
                                                      var10000 = ((var1 instanceof ClientboundRotateHeadPacket) ? 1 : 0);
                                                      if (var2) {
                                                         return ((var10000) != 0);
                                                      }

                                                      label279:
                                                      if ((var10000 == 0)) {
                                                         var10000 = ((var1 instanceof ClientboundSetEntityDataPacket) ? 1 : 0);
                                                         if (!var2) {
                                                            if (((var10000) != 0)) {
                                                               var10000 = ((ClientboundSetEntityDataPacket)var1).id();
                                                               if (var2) {
                                                                  return (boolean)((var10000) != 0);
                                                               }

                                                               if (var10000 != G.player.getId()) {
                                                                  break label279;
                                                               }
                                                            }

                                                            var10000 = ((var1 instanceof ClientboundSetEntityMotionPacket) ? 1 : 0);
                                                         }

                                                         if (!var2) {
                                                            if (((var10000) != 0)) {
                                                               var10000 = ((ClientboundSetEntityMotionPacket)var1).id();
                                                               if (var2) {
                                                                  return (boolean)((var10000) != 0);
                                                               }

                                                               if (var10000 != G.player.getId()) {
                                                                  break label279;
                                                               }
                                                            }

                                                            var10000 = ((var1 instanceof ClientboundDamageEventPacket) ? 1 : 0);
                                                         }

                                                         if (var2) {
                                                            return ((var10000) != 0);
                                                         }

                                                         if ((var10000 == 0)) {
                                                            int var21 = ((var1 instanceof ClientboundSoundPacket) ? 1 : 0);
                                                            if (var2) {
                                                               return (boolean)((var21) != 0);
                                                            }

                                                            if ((var21 == 0)) {
                                                               var21 = ((var1 instanceof ClientboundSoundEntityPacket) ? 1 : 0);
                                                               if (var2) {
                                                                  return ((var21) != 0);
                                                               }

                                                               if ((var21 == 0)) {
                                                                  var21 = ((var1 instanceof ClientboundStopSoundPacket) ? 1 : 0);
                                                                  if (var2) {
                                                                     return ((var21) != 0);
                                                                  }

                                                                  if ((var21 == 0)) {
                                                                     var21 = ((var1 instanceof ClientboundBlockUpdatePacket) ? 1 : 0);
                                                                     if (var2) {
                                                                        return ((var21) != 0);
                                                                     }

                                                                     if ((var21 == 0)) {
                                                                        var21 = ((var1 instanceof ClientboundExplodePacket) ? 1 : 0);
                                                                        if (var2) {
                                                                           return ((var21) != 0);
                                                                        }

                                                                        if ((var21 == 0)) {
                                                                           var21 = ((var1 instanceof ClientboundLevelEventPacket) ? 1 : 0);
                                                                           if (var2) {
                                                                              return ((var21) != 0);
                                                                           }

                                                                           if ((var21 == 0)) {
                                                                              var21 = ((var1 instanceof ClientboundLevelParticlesPacket) ? 1 : 0);
                                                                              if (var2) {
                                                                                 return ((var21) != 0);
                                                                              }

                                                                              if ((var21 == 0)) {
                                                                                 var21 = ((var1 instanceof ClientboundLightUpdatePacket) ? 1 : 0);
                                                                                 if (var2) {
                                                                                    return ((var21) != 0);
                                                                                 }

                                                                                 if ((var21 == 0)) {
                                                                                    var21 = ((var1 instanceof ClientboundSetTimePacket) ? 1 : 0);
                                                                                    if (var2) {
                                                                                       return ((var21) != 0);
                                                                                    }

                                                                                    if ((var21 == 0)) {
                                                                                       var21 = ((var1 instanceof ClientboundSystemChatPacket) ? 1 : 0);
                                                                                       if (var2) {
                                                                                          return ((var21) != 0);
                                                                                       }

                                                                                       if ((var21 == 0)) {
                                                                                          var21 = ((var1 instanceof ClientboundBundlePacket) ? 1 : 0);
                                                                                          if (var2) {
                                                                                             return ((var21) != 0);
                                                                                          }

                                                                                          if ((var21 == 0)) {
                                                                                             var21 = ((var1 instanceof ClientboundPlayerChatPacket) ? 1 : 0);
                                                                                             if (var2) {
                                                                                                return (boolean)((var21) != 0);
                                                                                             }

                                                                                             if (var21 == 0) {
                                                                                                return false;
                                                                                             }
                                                                                          }
                                                                                       }
                                                                                    }
                                                                                 }
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var10000 = 1;
      }

      return (boolean)((var10000) != 0);
   }

   private void I() {
      this.D = false;
      this.X = 0;
   }

   static {
      I = com.elowen.modules.impl.move.NoSlow$State.NONE;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
