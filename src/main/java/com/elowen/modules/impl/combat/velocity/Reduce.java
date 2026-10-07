package com.elowen.modules.impl.combat.velocity;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventStuckInBlock;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventPacket;
import com.elowen.modules.Module;
import com.elowen.modules.impl.combat.AntiBots;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.move.LongJump;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.FriendManager;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.GhostHitBoxes;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.utils.rotation.Rotation;
import com.elowen.utils.rotation.RotationUtils$Data;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Reduce implements VelocityMode {
   public static Vector2f g;
   private Velocity C;
   private int S = 0;
   private Player a;
   private Reduce$Stage U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
   private int c = 0;
   private int Z = 0;
   private int v = 0;
   private final Queue P = new ConcurrentLinkedQueue();
   private Player e = null;
   private int D = 0;
   private int W = -1;
   private boolean d = false;
   private boolean G = false;
   private int J = 0;
   private static final int A = 2;
   private int b = 0;
   private boolean V = false;
   private boolean L = false;
   private double Q = Double.NaN;
   private static final String[] f = new String[]{"Normal", "Reduce", "Alink", "Web", "Cooldown", "Delay", "Reduce"};
   @Override
   public void C(Velocity var1) {
      this.C = var1;
   }

   @Override
   public void B(com.elowen.events.impl.EventMotion var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (!this.e()) {
         Minecraft var3 = this.C.i$MC();
         if (var3.player != null) {
            if (var1.Q() == com.elowen.events.api.types.EventType.PRE && var1.r()) {
               this.Q = var3.player.getY();
            }
         }
      }
   }

   @Override
   public void J(EventPacket var1) {
      int var28 = 0;
      Reduce var25 = null;
      Reduce var31 = null;
      Reduce var33 = null;
      boolean var42 = false;
      boolean var36 = false;
      boolean var35 = false;
      Reduce var39 = null;
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      Minecraft var3 = this.C.i$MC();
      com.elowen.values.HasValue[] var2 = var10000;
      if (var3.player != null) {
         EventPacket var21 = var1;
         if (var2 == null) {
            if (var1.M() != com.elowen.events.api.types.EventType.RECEIVE) {
               return;
            }

            var21 = var1;
         }

         boolean var22 = var21.c$Z();
         if (var2 == null) {
            if (var22) {
               return;
            }

            var22 = this.e();
         }

         if (!var22) {
            Packet var4 = var1.R$Packet();
            boolean var23 = var4 instanceof ClientboundRespawnPacket;
            if (var2 == null) {
               if (var23) {
                  this.m();
                  this.Q();
                  this.S$V();
                  this.S = 0;
                  this.a = null;
                  this.D = 0;
                  this.e = null;
                  this.v = 0;
                  Reduce var40 = this;
                  if (var2 == null) {
                     if (this.U != com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN) {
                        this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
                     }

                     var40 = this;
                  }

                  var40.Z = 0;
                  return;
               }

               var23 = var4 instanceof ClientboundExplodePacket;
            }

            if (var2 == null) {
               if (var23) {
                  ClientboundExplodePacket var18 = (ClientboundExplodePacket)var4;
                  double var19 = var3.player.getX() - var18.center().x;
                  double var20 = var3.player.getY() - var18.center().y;
                  double var10 = var3.player.getZ() - var18.center().z;
                  double var12 = var19 * var19 + var20 * var20 + var10 * var10;
                  double var14 = 12.0;
                  if (var2 == null) {
                     if (var12 > var14 * var14) {
                        return;
                     }

                     this.m();
                     this.Q();
                     this.S$V();
                     this.S = 0;
                     this.a = null;
                     this.D = 0;
                     this.e = null;
                     this.v = 0;
                     this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
                     this.Z = 0;
                  }

                  return;
               }

               var23 = var4 instanceof ClientboundPlayerPositionPacket;
            }

            label425: {
               if (var2 == null) {
                  if (var23) {
                     break label425;
                  }

                  var23 = var4 instanceof ClientboundPlayerLookAtPacket;
               }

               if (!var23) {
                  label438: {
                     Reduce$Stage var24 = this.U;
                     Reduce$Stage var10001 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
                     if (var2 == null) {
                        if (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN) {
                           return;
                        }

                        var25 = this;
                        if (var2 != null) {
                           break label438;
                        }

                        var24 = this.U;
                        var10001 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK;
                     }

                     if (var24 != var10001) {
                        Packet var26 = var4;
                        if (var2 == null) {
                           if (!(var4 instanceof ClientboundSetEntityMotionPacket)) {
                              return;
                           }

                           var26 = var4;
                        }

                        ClientboundSetEntityMotionPacket var5 = (ClientboundSetEntityMotionPacket)var26;
                        if (var2 == null) {
                           if (var5.id() != PlayerUtils.S$I()) {
                              return;
                           }

                           label401: {
                              Reduce var27 = this;
                              if (var2 == null) {
                                 if (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK) {
                                    var28 = this.S;
                                    if (var2 != null) {
                                       break label401;
                                    }

                                    if (this.S > 0) {
                                       var28 = (this.C.K() ? 1 : 0);
                                       if (var2 != null) {
                                          break label401;
                                       }

                                       if (var28 != 0) {
                                          this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK;
                                          this.V = true;
                                          this.c = 0;
                                          Reduce var29 = this;
                                          boolean var41 = false;
                                          if (var2 == null) {
                                             this.D = 0;
                                             this.e = null;
                                             var29 = this;
                                             var41 = this.a != null;
                                          }

                                          var29.L = var41;
                                          this.v = 0;
                                          Reduce var30 = this;
                                          if (var2 == null) {
                                             if (!this.C.f$Z()) {
                                                return;
                                             }

                                             GhostHitBoxes.O(var3);
                                             var30 = this;
                                          }

                                          var30.d = true;
                                          return;
                                       }
                                    }
                                 }

                                 var27 = this;
                              }

                              var28 = (var27.R$Z() ? 1 : 0);
                           }

                           label442: {
                              if (var2 == null) {
                                 if (var28 != 0) {
                                    return;
                                 }

                                 var31 = this;
                                 if (var2 != null) {
                                    break label442;
                                 }

                                 var28 = (this.U$Z() ? 1 : 0);
                              }

                              if (var28 != 0) {
                                 this.a = this.p(var3, var3.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                                 Reduce var32 = this;
                                 if (var2 == null) {
                                    if (this.a == null) {
                                       return;
                                    }

                                    this.S = (int)this.C.I();
                                    var32 = this;
                                 }

                                 if (var2 == null) {
                                    if (var32.S <= 0) {
                                       return;
                                    }

                                    var32 = this;
                                 }

                                 if (var2 == null) {
                                    if (var32.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK) {
                                       return;
                                    }

                                    this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK;
                                    var32 = this;
                                 }

                                 var32.v = 0;
                                 return;
                              }

                              var31 = this;
                           }

                           label383: {
                              float var6 = var31.C.P();
                              label382:
                              if (var2 == null) {
                                 if (var6 > 0.0F) {
                                    Vec3 var7 = var5.movement();
                                    double var8 = Math.hypot(var7.x, var7.z);
                                    if (var2 != null) {
                                       break label382;
                                    }

                                    if (var8 < var6) {
                                       return;
                                    }
                                 }

                                 this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK;
                                 this.c = 0;
                                 this.a = this.p(var3, var3.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                                 var33 = this;
                                 var42 = false;
                                 if (var2 != null) {
                                    break label383;
                                 }

                                 this.D = 0;
                                 this.e = null;
                              }

                              var33 = this;
                              var42 = this.a != null;
                           }

                           var33.L = var42;
                           this.v = 0;
                           this.P.add(var5);
                           var1.c(true);
                           return;
                        }

                        return;
                     }

                     var25 = this;
                  }

                  boolean var34 = var25.U$Z();
                  if (var2 == null) {
                     if (var34) {
                        this.m();
                        this.Q();
                        this.S$V();
                        this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                        return;
                     }

                     var34 = this.V;
                  }

                  if (var2 == null) {
                     if (var34) {
                        var36 = var4 instanceof ClientboundSetEntityMotionPacket;
                        label330:
                        if (var2 == null) {
                           if (((var36) ? 1 : 0) != 0) {
                              ClientboundSetEntityMotionPacket var17 = (ClientboundSetEntityMotionPacket)var4;
                              var36 = ((var17.id()) != 0);
                              if (var2 != null) {
                                 break label330;
                              }

                              if (((var36) ? 1 : 0) == PlayerUtils.S$I()) {
                                 this.c = 0;
                                 this.D = 0;
                                 this.e = null;
                                 Reduce var37 = this;
                                 if (var2 == null) {
                                    if (!this.C.f$Z()) {
                                       return;
                                    }

                                    GhostHitBoxes.O(var3);
                                    var37 = this;
                                 }

                                 var37.d = true;
                                 return;
                              }
                           }

                           var36 = this.Y(var4);
                        }

                        if (var2 == null) {
                           if (((var36) ? 1 : 0) == 0) {
                              return;
                           }

                           this.P.add(var4);
                        }

                        var1.c(true);
                        return;
                     }

                     var34 = this.C.f$Z();
                  }

                  if (var2 == null) {
                     if (var34) {
                        var35 = var4 instanceof ClientboundSetEntityMotionPacket;
                        label341:
                        if (var2 == null) {
                           if (((var35) ? 1 : 0) != 0) {
                              ClientboundSetEntityMotionPacket var16 = (ClientboundSetEntityMotionPacket)var4;
                              var35 = ((var16.id()) != 0);
                              if (var2 != null) {
                                 break label341;
                              }

                              if (((var35) ? 1 : 0) == PlayerUtils.S$I()) {
                                 this.P.add(var4);
                                 var1.c(true);
                                 return;
                              }
                           }

                           var35 = var4 instanceof ClientboundKeepAlivePacket;
                        }

                        if (var2 == null) {
                           if (((var35) ? 1 : 0) != 0) {
                              this.P.add(var4);
                              var1.c(true);
                              return;
                           }

                           var35 = var4 instanceof ClientboundPingPacket;
                        }

                        if (var2 == null) {
                           if (((var35) ? 1 : 0) == 0) {
                              return;
                           }

                           this.P.add(var4);
                        }

                        var1.c(true);
                        return;
                     }

                     var34 = this.Y(var4);
                  }

                  if (var2 == null) {
                     if (!var34) {
                        return;
                     }

                     this.P.add(var4);
                  }

                  var1.c(true);
                  return;
               }
            }

            label448: {
               Reduce$Stage var38 = this.U;
               Reduce$Stage var43 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK;
               if (var2 == null) {
                  if (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK) {
                     this.P.add(var4);
                     var1.c(true);
                     this.m();
                     this.Q();
                     this.S$V();
                     this.S = 0;
                     this.a = null;
                     this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
                     this.Z = 0;
                     this.D = 0;
                     this.e = null;
                     this.v = 0;
                     return;
                  }

                  var39 = this;
                  if (var2 != null) {
                     break label448;
                  }

                  var38 = this.U;
                  var43 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
               }

               if (var38 != var43) {
                  this.S = 0;
                  this.a = null;
                  this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
               }

               this.S$V();
               this.m();
               this.Z = 0;
               this.D = 0;
               this.e = null;
               var39 = this;
            }

            var39.v = 0;
         }
      }
   }

   @Override
   public void b(EventTick var1) {
   }

   @Override
   public void Z(EventTick param1) {
      final com.elowen.values.HasValue[] c$ArrQ = NoXZ.c$ArrQ();
      final Minecraft i = this.C.i$MC();
      final com.elowen.values.HasValue[] array = c$ArrQ;
      if (i.player == null) {
         return;
      }
      if (param1.s$f() != com.elowen.events.api.types.EventType.PRE) {
         return;
      }
      com.elowen.modules.impl.combat.velocity.Reduce.g = null;
      boolean b;
      boolean onGround;
      final int n = (onGround = (b = this.e())) ? 1 : 0;
      if (array == null) {
         if (n != 0) {
            this.C.X("Web");
            this.m();
            this.Q();
            this.S$V();
            this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
            this.Z = 0;
            this.S = 0;
            this.a = null;
            this.v = 0;
            return;
         }
         final int n2;
         onGround = ((n2 = ((b = (this.J != 0)) ? 1 : 0)) != 0);
      }
      if (array == null) {
         if (n > 0) {
            --this.J;
         }
         b = (onGround = i.player.onGround());
      }
      Label_0244: {
         Label_0227: {
            Reduce q = null;
            Reduce q2 = null;
            Label_0214: {
               if (array == null) {
                  if (!onGround) {
                     break Label_0227;
                  }
                  q = this;
                  q2 = this;
                  if (array != null) {
                     break Label_0214;
                  }
                  b = this.G;
               }
               if (!b) {
                  this.J = 2;
               }
               this.G = true;
               q = this;
               q2 = this;
            }
            q.b = q2.b + 1;
            if (array == null) {
               break Label_0244;
            }
         }
         this.G = false;
         this.b = 0;
      }
      this.p();
      Reduce q3 = this;
      Label_0368: {
         if (array == null) {
            switch (this.U.ordinal()) {
               case 3: {
                  this.C.X("Cooldown");
                  if (array != null)
                  break Label_0368;
               }
               case 1: {
                  this.C.X("Alink");
                  if (array != null)
                  break Label_0368;
               }
               case 2: {
                  this.C.X("Reduce");
                  if (array != null) {
                     break;
                  }
                  break Label_0368;
               }
            }
            q3 = this;
         }
         q3.C.X("Reduce");
      }
      Reduce$Stage e2;
      final Reduce$Stage e = e2 = this.U;
      Reduce$Stage e4;
      final Reduce$Stage e3 = e4 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.COOLDOWN;
      if (array == null) {
         if (e == e3) {
            ++this.Z;
            final int n3 = (int)this.C.N();
            Reduce q4 = this;
            if (array == null) {
               if (this.Z <= n3) {
                  return;
               }
               this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
               this.Z = 0;
               this.a = null;
               this.S = 0;
               this.Q();
               q4 = this;
            }
            q4.v = 0;
            return;
         }
         final Reduce$Stage u;
         e2 = (u = this.U);
         final Reduce$Stage alink;
         e4 = (alink = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK);
      }
      Reduce q23 = null;
      Label_3453: {
         int s2 = 0;
         Label_3449: {
            Reduce q24 = null;
            Label_3435: {
               if (array == null) {
                  Label_2745: {
                     if (e == e3) {
                        int n8;
                        int onGround2;
                        int n7;
                        int n6;
                        int n5;
                        boolean b2;
                        final int n4 = (b2 = ((n5 = (n6 = (n7 = (onGround2 = (n8 = (this.R$Z() ? 1 : 0)))))) != 0)) ? 1 : 0;
                        if (array == null) {
                           if (n4 != 0) {
                              this.m();
                              this.Q();
                              this.S$V();
                              this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                              return;
                           }
                           n5 = ((b2 = ((n6 = (n7 = (onGround2 = (n8 = (this.U$Z() ? 1 : 0))))) != 0)) ? 1 : 0);
                        }
                        if (array == null) {
                           if (b2) {
                              this.m();
                              this.Q();
                              this.S$V();
                              this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                              return;
                           }
                           n6 = (n5 = (n7 = (onGround2 = (n8 = (this.V ? 1 : 0)))));
                        }
                        if (array == null) {
                           if (n5 != 0) {
                              ++this.c;
                              final int n9 = (int)this.C.o$F();
                              int n11;
                              final int n10 = n11 = this.c;
                              if (array == null) {
                                 if (n10 > n9) {
                                    this.t();
                                    return;
                                 }
                                 final int s;
                                 n11 = (s = this.S);
                              }
                              Label_0705: {
                                 Minecraft minecraft = null;
                                 Label_0696: {
                                    if (array == null) {
                                       if (n10 <= 0) {
                                          this.t();
                                          return;
                                       }
                                       minecraft = i;
                                       if (array != null) {
                                          break Label_0696;
                                       }
                                       n11 = (minecraft.player.isUsingItem() ? 1 : 0);
                                    }
                                    if (n11 != 0) {
                                       break Label_0705;
                                    }
                                 }
                                 if (minecraft.gui.screen() == null) {
                                    Reduce q5 = this;
                                    boolean b4 = false;
                                    boolean b3 = false;
                                    Label_0796: {
                                       if (array == null) {
                                          Label_0789: {
                                             if (this.a != null) {
                                                b3 = (b4 = this.a.isAlive());
                                                if (array != null) {
                                                   break Label_0796;
                                                }
                                                if (b3) {
                                                   boolean b7;
                                                   boolean f$Z;
                                                   boolean b6;
                                                   final boolean b5 = b6 = (f$Z = (b7 = this.B(this.a)));
                                                   if (array == null) {
                                                      if (b5) {
                                                         break Label_0789;
                                                      }
                                                      this.L = true;
                                                      f$Z = (b6 = (b7 = this.C.z$Z()));
                                                   }
                                                   boolean b8 = false;
                                                   Label_1142: {
                                                      Label_1135: {
                                                         if (array == null) {
                                                            if (b6) {
                                                               Reduce q6 = this;
                                                               Label_0979: {
                                                                  if (array == null) {
                                                                     Label_0971: {
                                                                        if (this.C.f$Z()) {
                                                                           final boolean r$Z = GhostHitBoxes.R$Z();
                                                                           if (array == null) {
                                                                              if (!r$Z) {
                                                                                 break Label_0971;
                                                                              }
                                                                              q6 = this;
                                                                              if (array != null) {
                                                                                 break Label_0979;
                                                                              }
                                                                              GhostHitBoxes.n(this.a.getUUID());
                                                                           }
                                                                           if (r$Z) {
                                                                              com.elowen.modules.impl.combat.velocity.Reduce.g = this.d$h(this.a);
                                                                              if (array == null) {
                                                                                 break Label_1135;
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                     q6 = this;
                                                                  }
                                                               }
                                                               com.elowen.modules.impl.combat.velocity.Reduce.g = q6.g(this.a);
                                                               if (array == null) {
                                                                  break Label_1135;
                                                               }
                                                            }
                                                            f$Z = this.C.f$Z();
                                                         }
                                                         boolean d$Z = false;
                                                         Label_1118: {
                                                            boolean r$Z2 = false;
                                                            Label_1116: {
                                                               if (array == null) {
                                                                  if (f$Z) {
                                                                     r$Z2 = GhostHitBoxes.R$Z();
                                                                     if (array != null) {
                                                                        break Label_1116;
                                                                     }
                                                                     if (r$Z2) {
                                                                        final boolean n12 = GhostHitBoxes.n(this.a.getUUID());
                                                                        if (array != null) {
                                                                           break Label_1116;
                                                                        }
                                                                        if (n12) {
                                                                           d$Z = this.d$Z(this.a);
                                                                           if (array == null) {
                                                                              break Label_1118;
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                                  this.U(i, this.a, i.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                                                               }
                                                            }
                                                            d$Z = r$Z2;
                                                         }
                                                         b8 = d$Z;
                                                         if (array != null) {
                                                            break Label_1142;
                                                         }
                                                         if (!b8) {
                                                            return;
                                                         }
                                                      }
                                                      i.player.isCreative();
                                                   }
                                                   if (this.E(this.a) > (b8 ? 5.0 : 3.0)) {
                                                      return;
                                                   }
                                                   final Aura killAura = (Aura)Elowen.S$Elowen().q$ModuleManager().A((Class)Aura.class);
                                                   if (array == null) {
                                                      if (killAura != null) {
                                                         killAura.R = 0.0f;
                                                      }
                                                      i.gameMode.attack((Player)i.player, (Entity)this.a);
                                                      PlayerUtils.s(InteractionHand.MAIN_HAND);
                                                      --this.S;
                                                   }
                                                   Reduce q7 = this;
                                                   if (array == null) {
                                                      if (this.S > 0) {
                                                         return;
                                                      }
                                                      q7 = this;
                                                   }
                                                   q7.t();
                                                   return;
                                                }
                                             }
                                          }
                                          q5 = this;
                                       }
                                       final boolean g$Z;
                                       b4 = (g$Z = q5.C.g$Z());
                                    }
                                    Reduce q8 = null;
                                    Label_0836: {
                                       if (array == null) {
                                          if (!b3) {
                                             return;
                                          }
                                          q8 = this;
                                          if (array != null) {
                                             break Label_0836;
                                          }
                                          b4 = this.L;
                                       }
                                       if (!b4) {
                                          return;
                                       }
                                       q8 = this;
                                    }
                                    q8.t();
                                    return;
                                 }
                              }
                              this.t();
                              return;
                           }
                           ++this.c;
                           n7 = (n6 = (onGround2 = (n8 = (this.C.v$Z() ? 1 : 0))));
                        }
                        int n20 = 0;
                        Label_1557: {
                           Label_1474: {
                              if (array == null) {
                                 if (n6 != 0) {
                                    final int n13 = n7 = (onGround2 = (n8 = (Double.isNaN(this.Q) ? 1 : 0)));
                                    if (array != null) {
                                       break Label_1474;
                                    }
                                    if (n13 == 0) {
                                       final boolean w = this.W(this.C.j$F());
                                       if (array == null && w) {}
                                       final int n14 = w ? 1 : 0;
                                       final double n15 = Double.compare(i.player.getY(), this.Q);
                                       if (array == null && n15 > 0) {}
                                       final int n16 = (int)n15;
                                       int n19;
                                       int n18;
                                       final int n17 = n18 = (n19 = n14);
                                       Label_1454: {
                                          Label_1453: {
                                             if (array == null) {
                                                if (n17 == 0) {
                                                   break Label_1453;
                                                }
                                                n19 = (n18 = n16);
                                             }
                                             if (array == null) {
                                                if (n18 != 0) {
                                                   break Label_1453;
                                                }
                                                n19 = 1;
                                             }
                                             break Label_1454;
                                          }
                                          n19 = 0;
                                       }
                                       n20 = n19;
                                       if (array == null) {
                                          break Label_1557;
                                       }
                                    }
                                 }
                                 onGround2 = (n7 = (n8 = (this.C.Y() ? 1 : 0)));
                              }
                           }
                           Label_1555: {
                              Label_1554: {
                                 if (array == null) {
                                    if (n7 == 0) {
                                       break Label_1554;
                                    }
                                    n8 = (onGround2 = (i.player.onGround() ? 1 : 0));
                                 }
                                 Label_1551: {
                                    if (array == null) {
                                       if (onGround2 != 0) {
                                          final float n21 = n8 = Float.compare((float)this.b, this.C.v$F());
                                          if (array != null) {
                                             break Label_1551;
                                          }
                                          if (n21 > 0) {
                                             break Label_1554;
                                          }
                                       }
                                       n8 = 1;
                                    }
                                 }
                                 break Label_1555;
                              }
                              n8 = 0;
                           }
                           n20 = n8;
                        }
                        final int n22 = n20;
                        if (array == null) {
                           if (n22 != 0) {
                              final int n23 = (int)this.C.o$F();
                              Reduce q9 = this;
                              if (array == null) {
                                 if (this.c <= n23) {
                                    return;
                                 }
                                 this.m();
                                 this.Q();
                                 this.S$V();
                                 q9 = this;
                              }
                              q9.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                              return;
                           }
                           final int n24 = (int)this.C.o$F();
                        }
                        final int n25 = n22;
                        Reduce q10 = this;
                        Reduce q11 = this;
                        if (array == null) {
                           if (this.c > n25) {
                              this.m();
                              this.Q();
                              this.S$V();
                              this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                              return;
                           }
                           q10 = this;
                           q11 = this;
                        }
                        if (array == null) {
                           if (q11.a == null) {
                              this.a = this.p(i, i.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                           }
                           q10 = this;
                        }
                        boolean b10;
                        final boolean b9 = b10 = q10.C.z$Z();
                        Reduce q13 = null;
                        Reduce q14 = null;
                        Label_2124: {
                           Label_1906: {
                              if (array == null) {
                                 Label_1888: {
                                    if (b9) {
                                       Reduce q12 = this;
                                       Player a = null;
                                       Label_1885: {
                                          Label_1870: {
                                             if (array == null) {
                                                if (this.a != null) {
                                                   q12 = this;
                                                   if (array != null) {
                                                      break Label_1870;
                                                   }
                                                   if (this.a.isAlive()) {
                                                      q12 = this;
                                                      a = this.a;
                                                      if (array != null) {
                                                         break Label_1885;
                                                      }
                                                      if (!this.B(a)) {
                                                         final double n26 = (b10 = Double.compare(this.c(this.a), (double)this.C.S$F()) != 0) ? 1 : 0;
                                                         if (array != null) {
                                                            break Label_1906;
                                                         }
                                                         if (n26 <= 0) {
                                                            break Label_1888;
                                                         }
                                                      }
                                                   }
                                                }
                                                q12 = this;
                                             }
                                          }
                                          this.E(i, i.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                                       }
                                       q12.a = a;
                                    }
                                 }
                                 q13 = this;
                                 q14 = this;
                                 if (array != null) {
                                    break Label_2124;
                                 }
                                 b10 = this.C.z$Z();
                              }
                           }
                           Label_2123: {
                              if (b10) {
                                 final Player a2 = this.a;
                                 boolean b12 = false;
                                 boolean b11 = false;
                                 Label_2068: {
                                    Reduce q15 = null;
                                    Label_2062: {
                                       if (array == null) {
                                          if (a2 != null) {
                                             q13 = this;
                                             q14 = this;
                                             if (array != null) {
                                                break Label_2124;
                                             }
                                             if (this.c(this.a) <= this.C.S$F()) {
                                                break Label_2123;
                                             }
                                          }
                                          q15 = this;
                                          if (array != null) {
                                             break Label_2062;
                                          }
                                          final Player a3 = this.a;
                                       }
                                       Label_2054: {
                                          if (a2 != null) {
                                             b11 = (b12 = this.a.isAlive());
                                             if (array != null) {
                                                break Label_2068;
                                             }
                                             if (b11) {
                                                Reduce q16 = this;
                                                if (array == null) {
                                                   if (this.B(this.a)) {
                                                      break Label_2054;
                                                   }
                                                   this.D = 0;
                                                   q16 = this;
                                                }
                                                q16.e = null;
                                                return;
                                             }
                                          }
                                       }
                                       q15 = this;
                                    }
                                    final boolean g$Z2;
                                    b12 = (g$Z2 = q15.C.g$Z());
                                 }
                                 Reduce q17 = null;
                                 Label_2108: {
                                    if (array == null) {
                                       if (!b11) {
                                          return;
                                       }
                                       q17 = this;
                                       if (array != null) {
                                          break Label_2108;
                                       }
                                       b12 = this.L;
                                    }
                                    if (!b12) {
                                       return;
                                    }
                                    q17 = this;
                                 }
                                 q17.d$V();
                                 return;
                              }
                           }
                           q13 = this;
                           q14 = this;
                        }
                        boolean b13 = false;
                        boolean b15 = false;
                        Label_2701: {
                           if (array == null) {
                              Label_2687: {
                                 if (q14.a != null) {
                                    final boolean b14;
                                    b13 = (b14 = this.a.isAlive());
                                    if (array != null) {
                                       break Label_2701;
                                    }
                                    if (b13) {
                                       b15 = this.B(this.a);
                                       if (array != null) {
                                          break Label_2701;
                                       }
                                       if (!b15) {
                                          this.L = true;
                                          int z$Z2;
                                          boolean z$Z;
                                          final boolean b16 = z$Z = ((z$Z2 = (this.C.z$Z() ? 1 : 0)) != 0);
                                          if (array == null) {
                                             if (b16) {
                                                com.elowen.modules.impl.combat.velocity.Reduce.g = this.g(this.a);
                                             }
                                             z$Z2 = ((z$Z = this.C.z$Z()) ? 1 : 0);
                                          }
                                          Label_2309: {
                                             if (array == null) {
                                                if (!z$Z) {
                                                   final double n27 = z$Z2 = Double.compare(this.c(this.a), (double)this.C.S$F());
                                                   if (array != null) {
                                                      break Label_2309;
                                                   }
                                                   if (n27 > 0) {
                                                      z$Z2 = 0;
                                                      break Label_2309;
                                                   }
                                                }
                                                z$Z2 = 1;
                                             }
                                          }
                                          final int n28 = z$Z2;
                                          Reduce q18 = this;
                                          if (array == null) {
                                             Label_2665: {
                                                if (this.U(i, this.a, i.getDeltaTracker().getGameTimeDeltaPartialTick(false))) {
                                                   int n30;
                                                   int usingItem;
                                                   final int n29 = usingItem = (n30 = n28);
                                                   if (array == null) {
                                                      if (n29 == 0) {
                                                         break Label_2665;
                                                      }
                                                      final int n31;
                                                      usingItem = (n31 = (n30 = (this.C.J$Z() ? 1 : 0)));
                                                   }
                                                   Label_2441: {
                                                      if (array == null) {
                                                         if (n29 != 0) {
                                                            final int n32 = usingItem = (n30 = (i.player.isSprinting() ? 1 : 0));
                                                            if (array != null) {
                                                               break Label_2441;
                                                            }
                                                            if (n32 == 0) {
                                                               break Label_2665;
                                                            }
                                                         }
                                                         n30 = (usingItem = (i.player.isUsingItem() ? 1 : 0));
                                                      }
                                                   }
                                                   if (array == null) {
                                                      if (usingItem != 0) {
                                                         this.a = null;
                                                         return;
                                                      }
                                                      n30 = (int)this.C.a$F();
                                                   }
                                                   final int n34;
                                                   final int n33 = n34 = n30;
                                                   Reduce q21 = null;
                                                   Label_2657: {
                                                      Label_2611: {
                                                         if (array == null) {
                                                            if (n34 > 0) {
                                                               Reduce q19 = this;
                                                               Reduce q20 = this;
                                                               Label_2561: {
                                                                  if (array == null) {
                                                                     if (this.a != this.e) {
                                                                        this.D = 1;
                                                                        this.e = this.a;
                                                                        if (array == null) {
                                                                           break Label_2561;
                                                                        }
                                                                     }
                                                                     q19 = this;
                                                                     q20 = this;
                                                                  }
                                                                  q19.D = q20.D + 1;
                                                               }
                                                               final int d = this.D;
                                                               if (array != null) {
                                                                  break Label_2611;
                                                               }
                                                               if (d < n33) {
                                                                  return;
                                                               }
                                                            }
                                                            this.D = 0;
                                                            this.e = null;
                                                            q21 = this;
                                                            if (array != null) {
                                                               break Label_2657;
                                                            }
                                                            final boolean d2 = this.d;
                                                         }
                                                      }
                                                      if (n34 != 0) {
                                                         this.d = false;
                                                         GhostHitBoxes.x();
                                                      }
                                                      this.m();
                                                      this.Q();
                                                      this.S = (int)this.C.I();
                                                      this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK;
                                                      q21 = this;
                                                   }
                                                   q21.v = 0;
                                                   if (array == null) {
                                                      if (array != null) {
                                                         break Label_2687;
                                                      }
                                                      break Label_2745;
                                                   }
                                                }
                                             }
                                             this.D = 0;
                                             q18 = this;
                                          }
                                          q18.e = null;
                                          return;
                                       }
                                    }
                                 }
                              }
                              q13 = this;
                           }
                           q13.C.g$Z();
                        }
                        Reduce q22 = null;
                        Label_2741: {
                           if (array == null) {
                              if (!b13) {
                                 return;
                              }
                              q22 = this;
                              if (array != null) {
                                 break Label_2741;
                              }
                              final boolean b14 = this.L;
                           }
                           if (!b15) {
                              return;
                           }
                           q22 = this;
                        }
                        q22.d$V();
                        return;
                     }
                  }
                  q23 = this;
                  q24 = this;
                  if (array != null) {
                     break Label_3435;
                  }
                  e2 = this.U;
                  e4 = com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK;
               }
               Label_3434: {
                  if (e2 == e4) {
                     s2 = this.S;
                     if (array != null) {
                        break Label_3449;
                     }
                     if (s2 > 0) {
                        final boolean z$Z3 = this.C.z$Z();
                        Label_2950: {
                           Minecraft minecraft2 = null;
                           Label_2941: {
                              Label_2937: {
                                 if (array == null) {
                                    Label_2919: {
                                       if (z$Z3) {
                                          final Player a4 = this.a;
                                          if (array == null) {
                                             if (a4 == null) {
                                                break Label_2919;
                                             }
                                             final Player a5 = this.a;
                                          }
                                          final boolean alive = a4.isAlive();
                                          if (array != null) {
                                             break Label_2937;
                                          }
                                          if (alive) {
                                             final boolean b17 = this.B(this.a);
                                             if (array != null) {
                                                break Label_2937;
                                             }
                                             if (!b17) {
                                                com.elowen.modules.impl.combat.velocity.Reduce.g = this.g(this.a);
                                             }
                                          }
                                       }
                                    }
                                    minecraft2 = i;
                                    if (array != null) {
                                       break Label_2941;
                                    }
                                    minecraft2.player.isUsingItem();
                                 }
                              }
                              if (z$Z3) {
                                 break Label_2950;
                              }
                           }
                           if (minecraft2.gui.screen() == null) {
                              Reduce q25 = this;
                              Label_3085: {
                                 if (array == null) {
                                    Label_3056: {
                                       if (this.a != null) {
                                          q25 = this;
                                          if (array != null) {
                                             break Label_3085;
                                          }
                                          if (this.a.isAlive()) {
                                             final int b18 = this.B(this.a) ? 1 : 0;
                                             if (array == null) {
                                                if (b18 != 0) {
                                                   break Label_3056;
                                                }
                                                this.U(i, this.a, i.getDeltaTracker().getGameTimeDeltaPartialTick(false));
                                             }
                                             final int n35 = b18;
                                             final double n36 = Double.compare(this.E(this.a), i.player.isCreative() ? 5.0 : 3.0);
                                             if (array == null && n36 > 0) {}
                                             final int n37 = (int)n36;
                                             int n39;
                                             final int n38 = n39 = n35;
                                             Label_3232: {
                                                if (array == null) {
                                                   if (n38 != 0) {
                                                      final int n40 = n39 = n37;
                                                      if (array != null) {
                                                         break Label_3232;
                                                      }
                                                      if (n40 != 0) {
                                                         this.v = 0;
                                                         final Aura killAura2 = (Aura)Elowen.S$Elowen().q$ModuleManager().A((Class)Aura.class);
                                                         if (array == null) {
                                                            if (killAura2 != null) {
                                                               killAura2.R = 0.0f;
                                                            }
                                                            i.gameMode.attack((Player)i.player, (Entity)this.a);
                                                            PlayerUtils.s(InteractionHand.MAIN_HAND);
                                                            --this.S;
                                                         }
                                                         final int s3 = this.S;
                                                         if (array != null) {
                                                            break Label_3449;
                                                         }
                                                         if (s3 <= 0) {
                                                            this.a = null;
                                                            this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                                                            this.S$V();
                                                         }
                                                         break Label_3434;
                                                      }
                                                   }
                                                   ++this.v;
                                                   n39 = (int)this.C.o$F();
                                                }
                                             }
                                             final int n42;
                                             final int n41 = n42 = n39;
                                             Reduce q26 = null;
                                             Label_3306: {
                                                if (array == null) {
                                                   if (n42 <= 0) {
                                                      return;
                                                   }
                                                   q26 = this;
                                                   if (array != null) {
                                                      break Label_3306;
                                                   }
                                                   final int v = this.v;
                                                }
                                                if (n42 <= n41) {
                                                   return;
                                                }
                                                this.S = 0;
                                                this.a = null;
                                                this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                                                this.S$V();
                                                q26 = this;
                                             }
                                             q26.v = 0;
                                             return;
                                          }
                                       }
                                    }
                                    this.S = 0;
                                    this.a = null;
                                    this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                                    this.S$V();
                                    q25 = this;
                                 }
                              }
                              q25.v = 0;
                              return;
                           }
                        }
                        this.S = 0;
                        this.a = null;
                        this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
                        this.S$V();
                        this.v = 0;
                        return;
                     }
                  }
               }
               q23 = this;
               q24 = this;
            }
            if (array != null) {
               break Label_3453;
            }
            final int s4 = q24.S;
         }
         if (s2 > 0) {
            return;
         }
         q23 = this;
      }
      q23.a = null;
   }

   @Override
   public void E(EventMoveInput var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (!this.e()) {
         Minecraft var3 = this.C.i$MC();
         if (var3.player != null) {
            if (this.J > 0
               && (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK || this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK)
               && this.a != null
               && this.c(this.a) <= this.C.S$F() + 2.0) {
               var1.A(false);
            }

            if (this.a != null) {
               if (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK
                  || this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK && this.U(this.C.i$MC(), this.a)) {
                  var1.b(1.0F);
                  var1.m(0.0F);
                  var1.v(false);
               }
            }
         }
      }
   }

   @Override
   public void c$V() {
      LocalPlayer var10001 = null;
      double var3;
      label30: {
         label29: {
            label32: {
               this.S = 0;
               this.a = null;
               this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
               com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
               this.c = 0;
               com.elowen.values.HasValue[] var1 = var10000;
               this.Z = 0;
               this.D = 0;
               this.e = null;
               this.V = false;
               this.L = false;
               this.W = -1;
               this.P.clear();
               this.S$V();
               this.G = false;
               this.J = 0;
               this.b = 0;
               this.v = 0;
               Minecraft var2 = this.C.i$MC();
               var10001 = var2.player;
               if (var1 == null) {
                  if (var2.player == null) {
                     break label32;
                  }

                  var10001 = var2.player;
               }

               if (var1 != null) {
                  break label29;
               }

               if (var10001.onGround()) {
                  var10001 = var2.player;
                  break label29;
               }
            }

            var3 = Double.NaN;
            break label30;
         }

         var3 = var10001.getY();
      }

      this.Q = var3;
      g = null;
   }

   @Override
   public void z$V() {
      this.S = 0;
      this.a = null;
      this.m();
      this.Q();
      this.S$V();
      this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
      this.Z = 0;
      this.D = 0;
      this.e = null;
      this.W = -1;
      this.G = false;
      this.J = 0;
      this.b = 0;
      this.v = 0;
      this.Q = Double.NaN;
      g = null;
   }

   @Override
   public boolean z$Z() {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      return this.e()
         ? false
         : (this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK || this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK) && this.a != null;
   }

   @Override
   public boolean X$Z() {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      return this.e() ? false : this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK && !this.U$Z();
   }

   public boolean c$Z() {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      return this.e() ? false : this.U == com.elowen.modules.impl.combat.velocity.Reduce$Stage.ATTACK;
   }

   private boolean e() {
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      Minecraft var2 = this.C.i$MC();
      com.elowen.values.HasValue[] var1 = var10000;
      LocalPlayer var3 = var2.player;
      if (var1 == null) {
         if (var2.player == null) {
            return false;
         }

         var3 = var2.player;
      }

      int var4 = var3.tickCount;
      if (var1 == null) {
         var4 = var3.tickCount == this.W ? 1 : 0;
      }

      return (boolean)((var4) != 0);
   }

   private void m() {
      this.C.i$MC().execute(this::I);
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void I() {
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      ClientPacketListener var2 = this.C.i$MC().getConnection();
      com.elowen.values.HasValue[] var1 = var10000;
      if (var1 == null) {
         if (var2 != null) {
            while (!this.P.isEmpty()) {
               label35: {
                  Packet var3 = (Packet)this.P.poll();
                  Packet var7 = var3;
                  if (var1 == null) {
                     if (var3 == null) {
                        continue;
                     }

                     try {
                        var7 = var3;
                     } catch (Exception var6) {
                        var6.printStackTrace();
                        break label35;
                     }
                  }

                  try {
                     var7.handle(var2);
                  } catch (Exception var5) {
                     var5.printStackTrace();
                  }
               }

               if (var1 != null) {
                  break;
               }
            }

            return;
         }

         this.P.clear();
      }
   }

   private void Q() {
      this.c = 0;
      this.D = 0;
      this.e = null;
      this.V = false;
      this.L = false;
   }

   private void d$V() {
      this.m();
      this.Q();
      this.S$V();
      this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
      this.Z = 0;
      this.S = 0;
      this.a = null;
      this.v = 0;
   }

   private boolean W(float var1) {
      boolean var14 = false;
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      Minecraft var3 = this.C.i$MC();
      com.elowen.values.HasValue[] var2 = var10000;
      Minecraft var13 = var3;
      if (var2 == null) {
         if (var3.player == null) {
            return false;
         }

         var13 = var3;
      }

      if (var2 == null) {
         if (var13.level == null) {
            return false;
         }

         var13 = var3;
      }

      double var4 = var13.player.getX();
      double var6 = var3.player.getY();
      double var8 = var3.player.getZ();
      double var10 = var6;

      while (true) {
         if (var10 >= var6 - var1) {
            AABB var12 = new AABB(var4 - 1.0E-4, var10 - 1.0E-4F, var8 - 1.0E-4, var4 + 1.0E-4, var10, var8 + 1.0E-4);
            if (var2 == null) {
               var14 = var3.level.getCollisions(var3.player, var12).iterator().hasNext();
               if (var2 != null) {
                  break;
               }

               if (var14) {
                  return false;
               }

               var10--;
            }

            if (var2 == null) {
               continue;
            }
         }

         var14 = true;
         break;
      }

      return var14;
   }

   private void t() {
      GhostHitBoxes.x();
      this.m();
      this.Q();
      this.S$V();
      this.V = false;
      this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
      this.Z = 0;
      this.S = 0;
      this.a = null;
      this.v = 0;
   }

   private Vector2f d$h(Player var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1 == null) {
         return null;
      }

      AABB var3 = GhostHitBoxes.k(var1.getUUID());
      if (var3 == null) {
         return null;
      }

      RotationUtils$Data var4 = RotationUtils.F(var1, var3);
      if (var4 != null && var4.e() != null) {
         return var4.e();
      }

      Minecraft var5 = this.C.i$MC();
      float var6 = var5.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var7 = var5.player.getEyePosition(var6);
      return RotationUtils.X(var7, var3.getCenter()).M();
   }

   private boolean d$Z(Player var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1 == null) {
         return false;
      }

      AABB var3 = GhostHitBoxes.k(var1.getUUID());
      if (var3 == null) {
         return false;
      }

      Minecraft var4 = this.C.i$MC();
      float var5 = var4.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var6 = var4.player.getEyePosition(var5);
      double var7 = Math.toRadians(var4.player.getYRot());
      double var9 = Math.toRadians(var4.player.getXRot());
      Vec3 var11 = new Vec3(-Math.sin(var7) * Math.cos(var9), -Math.sin(var9), Math.cos(var7) * Math.cos(var9));
      double var12 = var4.player.isCreative() ? 5.0 : 3.0;
      Vec3 var14 = var6.add(var11.x * var12, var11.y * var12, var11.z * var12);
      return var3.clip(var6, var14).isPresent();
   }

   private void S$V() {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      if (this.d) {
         this.d = false;
         GhostHitBoxes.x();
      }

      this.v = 0;
   }

   private void p() {
      com.elowen.values.HasValue[] var1;
      Minecraft var2;
      Reduce var4;
      label66: {
         com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
         var2 = this.C.i$MC();
         var1 = var10000;
         boolean var3 = this.D(var2);
         if (var1 == null) {
            if (var3) {
               var4 = this;
               if (var1 == null) {
                  if (!this.d) {
                     return;
                  }

                  var4 = this;
               }

               var4.d = false;
               GhostHitBoxes.x();
               return;
            }

            var4 = this;
            if (var1 != null) {
               break label66;
            }

            var3 = this.C.f$Z();
         }

         if (!var3) {
            return;
         }

         var4 = this;
      }

      if (var1 == null) {
         if (var4.U != com.elowen.modules.impl.combat.velocity.Reduce$Stage.ALINK) {
            return;
         }

         var4 = this;
      }

      if (var1 == null) {
         if (var4.d) {
            return;
         }

         GhostHitBoxes.O(var2);
         var4 = this;
      }

      var4.d = true;
   }

   private boolean D(Minecraft var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1.gui.screen() instanceof AbstractContainerScreen) {
         return true;
      }

      Stuck var3 = (Stuck)Elowen.S$Elowen().q$ModuleManager().A(Stuck.class);
      return var3 == null || !var3.w() || !var3.S$t().t("Normal") && !var3.S$t().t("Delay") ? com.elowen.utils.InventoryUtils.G$Z() : true;
   }

   private AABB G(Player var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      return var1 == null ? null : var1.getBoundingBox();
   }

   private boolean U$Z() {
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      Module var2 = Elowen.S$Elowen().q$ModuleManager().A(Scaffold.class);
      com.elowen.values.HasValue[] var1 = var10000;
      Module var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      return var1 != null ? var4 : var4;
   }

   private boolean R$Z() {
      com.elowen.values.HasValue[] var10000 = NoXZ.c$ArrQ();
      Module var2 = Elowen.S$Elowen().q$ModuleManager().A(LongJump.class);
      com.elowen.values.HasValue[] var1 = var10000;
      Module var3 = var2;
      if (var1 == null) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      return var1 != null ? var4 : var4;
   }

   private Player p(Minecraft var1, float var2) {
      com.elowen.values.HasValue[] var3 = NoXZ.c$ArrQ();
      if (var1.player != null && var1.level != null) {
         double var4 = Math.toRadians(var1.player.getYRot());
         double var6 = Math.toRadians(var1.player.getXRot());
         Vec3 var8 = var1.player.getEyePosition(var2);
         Vec3 var9 = new Vec3(-Math.sin(var4) * Math.cos(var6), -Math.sin(var6), Math.cos(var4) * Math.cos(var6));
         double var10 = var1.player.isCreative() ? 5.0 : 3.0;
         Vec3 var12 = var8.add(var9.x * var10, var9.y * var10, var9.z * var10);
         BlockHitResult var13 = var1.level.clip(new ClipContext(var8, var12, Block.COLLIDER, Fluid.NONE, var1.player));
         double var14 = var13 != null && var13.getType() != Type.MISS ? var8.distanceToSqr(var13.getLocation()) : Double.MAX_VALUE;
         EntityHitResult var16 = null;
         double var17 = var10 * var10;
         AABB var19 = var1.player.getBoundingBox().expandTowards(var9.scale(var10)).inflate(1.0);

         for (Entity var21 : var1.level.getEntities(var1.player, var19, (Entity var10002) -> deobfLambda$getAimedTarget$0(var10002))) {
            if (var21 instanceof Player var22 && var22 != var1.player && !this.B(var22)) {
               AABB var23 = var21.getBoundingBox().inflate(var21.getPickRadius());
               Optional var24 = var23.clip(var8, var12);
               if (var24.isPresent()) {
                  double var25 = var8.distanceToSqr((Vec3)var24.get());
                  if (var25 < var17) {
                     var16 = new EntityHitResult(var21, (Vec3)var24.get());
                     var17 = var25;
                  }
               }
               break;
            }
         }

         return var16 != null && var17 < var14 ? (Player)var16.getEntity() : null;
      } else {
         return null;
      }
   }

   private boolean U(Minecraft var1, Player var2, float var3) {
      Player var4 = this.p(var1, var3);
      return var4 == var2;
   }

   private boolean U(Minecraft var1, Player var2) {
      com.elowen.values.HasValue[] var3 = NoXZ.c$ArrQ();
      return var2 != null && this.U(var1, var2, var1.getDeltaTracker().getGameTimeDeltaPartialTick(false));
   }

   private double E(Player var1) {
      if (var1 == null) {
         return Double.MAX_VALUE;
      }

      Minecraft var2 = this.C.i$MC();
      float var3 = var2.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var4 = var2.player.getEyePosition(var3);
      AABB var5 = this.G(var1);
      return var5 != null ? var4.distanceTo(RotationUtils.x(var4, var5)) : Double.MAX_VALUE;
   }

   private double c(Player var1) {
      if (var1 == null) {
         return Double.MAX_VALUE;
      }

      Minecraft var2 = this.C.i$MC();
      float var3 = var2.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var4 = var2.player.getEyePosition(var3);
      AABB var5 = var1.getBoundingBox();
      return var5 != null ? var4.distanceTo(RotationUtils.x(var4, var5)) : Double.MAX_VALUE;
   }

   private Vector2f g(Player var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1 == null) {
         return null;
      }

      if (RotationUtils.F(var1.getBoundingBox())) {
         return null;
      }

      RotationUtils$Data var3 = RotationUtils.s(var1);
      if (var3 != null && var3.e() != null) {
         return var3.e();
      }

      Minecraft var4 = this.C.i$MC();
      float var5 = var4.getDeltaTracker().getGameTimeDeltaPartialTick(false);
      Vec3 var6 = var4.player.getEyePosition(var5);
      return RotationUtils.X(var6, var1.getBoundingBox().getCenter()).M();
   }

   private Player E(Minecraft var1, float var2) {
      com.elowen.values.HasValue[] var3 = NoXZ.c$ArrQ();
      if (var1.level != null && var1.player != null) {
         Player var4 = null;
         float var5 = Float.MAX_VALUE;
         float var6 = this.C.S$F();
         Vec3 var7 = var1.player.getEyePosition(var2);
         float var8 = var1.player.getYRot();

         for (Player var10 : var1.level.players()) {
            if (var10 != var1.player && !this.B(var10) && !(this.c(var10) > var6)) {
               AABB var11 = var10.getBoundingBox();
               Rotation var12 = RotationUtils.X(var7, var11.getCenter());
               float var13 = Math.abs(RotationUtils.e(var8, var12.o$F()));
               if (var13 < var5) {
                  var4 = var10;
               }
               break;
            }
         }

         return var4;
      } else {
         return null;
      }
   }

   private boolean B(Player var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1 == null) {
         return true;
      } else if (var1.isRemoved() || var1.isDeadOrDying()) {
         return true;
      } else {
         return AntiBots.g(var1) || AntiBots.O(var1) ? true : com.elowen.utils.FriendManager.A(var1);
      }
   }

   private boolean Y(Packet var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (var1 instanceof ClientboundMoveEntityPacket || var1 instanceof ClientboundEntityPositionSyncPacket || var1 instanceof ClientboundTeleportEntityPacket
         )
       {
         return true;
      } else if (var1 instanceof ClientboundSetEntityMotionPacket) {
         return true;
      } else {
         return var1 instanceof ClientboundRotateHeadPacket ? true : var1 instanceof ClientboundKeepAlivePacket || var1 instanceof ClientboundPingPacket;
      }
   }

   @Override
   public void f(EventStuckInBlock var1) {
      com.elowen.values.HasValue[] var2 = NoXZ.c$ArrQ();
      if (this.C.i$MC().player != null) {
         if (var1.k().getBlock() == Blocks.COBWEB) {
            int var3 = this.C.i$MC().player.tickCount;
            if (var3 != this.W) {
               this.W = var3;
               this.m();
               this.Q();
               this.S$V();
               this.U = com.elowen.modules.impl.combat.velocity.Reduce$Stage.NONE;
               this.Z = 0;
               this.S = 0;
               this.a = null;
               this.D = 0;
               this.e = null;
               this.v = 0;
            }
         }
      }
   }

   private static boolean deobfLambda$getAimedTarget$0(Entity var0) {
      com.elowen.values.HasValue[] var1 = NoXZ.c$ArrQ();
      return !var0.isSpectator() && var0.isPickable();
   }

   static {
      g = null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
