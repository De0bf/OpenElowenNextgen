package com.elowen.modules.impl.player;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventGlobalPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.PacketUtils;
import com.elowen.utils.TickTimeHelper;
import com.elowen.utils.InventoryUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundPongPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPunchPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ModuleInfo(R = "ChestStealer", a = "Auto steal items from chests, furnaces, and brewing stands.", M = Category.PLAYER)
public class ChestStealer extends Module {
   private static ChestStealer M;
   private static final long S = 80L;
   private final TickTimeHelper e = new TickTimeHelper();
   private final BooleanValue v;
   private final com.elowen.values.impl.FloatValue z;
   private final com.elowen.values.impl.FloatValue C;
   private final com.elowen.values.impl.FloatValue X;
   private final BooleanValue T;
   private final BooleanValue c;
   private final BooleanValue b;
   private final BooleanValue B;
   private final BooleanValue P;
   private final BooleanValue V;
   private final com.elowen.values.impl.FloatValue F;
   private final BooleanValue D;
   public final BooleanValue Y;
   public Screen r;
   private ChestStealer$ContainerSession y;
   private int t;
   private int K;
   private boolean R;
   public boolean U;
   private int m;
   private final LinkedBlockingQueue E;
   private static int j;
   private static final String[] d = new String[]{"container.enderchest", "Max Delay (Ticks)", "Min Delay (Ticks)", "Brewing Stand", "Ping Spoof", "Furnace", "test", "Smart Delay", "container.chest", "Ender Chest", "First Delay (Ticks)", "container.chestDouble", "Click Empty Slot", "Meaningless Packet", "Bypass Delay (MS)", "Key Take", "Chest", "minecraft", "Fast"};
   public ChestStealer() {
      String[] var2 = d;
      this.v = com.elowen.values.ValueBuilder.m(this, "Smart Delay").h(false).f$K().f$O();
      this.z = com.elowen.values.ValueBuilder.m(this, "Min Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).l(this::deobfLambda$new$0).f$K().L();
      int var10000 = m();
      this.C = com.elowen.values.ValueBuilder.m(this, "Max Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).l(this::deobfLambda$new$1).f$K().L();
      this.X = com.elowen.values.ValueBuilder.m(this, "First Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).f$K().L();
      this.T = com.elowen.values.ValueBuilder.m(this, "Ender Chest").h(false).f$K().f$O();
      this.c = com.elowen.values.ValueBuilder.m(this, "Furnace").h(true).f$K().f$O();
      this.b = com.elowen.values.ValueBuilder.m(this, "Brewing Stand").h(true).f$K().f$O();
      this.B = com.elowen.values.ValueBuilder.m(this, "Fast").h(false).f$K().f$O();
      this.P = com.elowen.values.ValueBuilder.m(this, "Meaningless Packet").h(false).f$K().f$O();
      this.V = com.elowen.values.ValueBuilder.m(this, "Ping Spoof").h(false).f$K().f$O();
      this.F = com.elowen.values.ValueBuilder.m(this, "Bypass Delay (MS)").d(500.0F).V(50.0F).w(0.0F).M(10000.0F).l(this::deobfLambda$new$2).f$K().L();
      this.D = com.elowen.values.ValueBuilder.m(this, "Key Take").h(false).f$K().f$O();
      this.Y = com.elowen.values.ValueBuilder.m(this, "Click Empty Slot").h(true).f$K().f$O();
      this.t = -1;
      int var1 = var10000;
      this.K = 0;
      this.R = false;
      this.U = false;
      this.m = -1;
      this.E = new LinkedBlockingQueue();
      if (!HasValue.x()) {
         g(++var1);
      }
   }

   @Override
   public void h$V() {
      M = this;
      this.v$V();
      this.m = this.X$I();
   }

   @Override
   public void q$V() {
      M = null;
      this.X$V();
      this.v$V();
      this.m = -1;
   }

   public static boolean a$Z() {
      int var0 = m();
      ChestStealer var10000 = M;
      if (var0 == 0) {
         if (M == null) {
            return false;
         }

         var10000 = M;
      }

      boolean var1 = var10000.Y();
      return var0 != 0 ? var1 : var1;
   }

   public boolean Y() {
      int var1 = d$I();
      boolean var10000 = this.R;
      if (var1 != 0) {
         if (this.R) {
            return true;
         }

         var10000 = this.e.m(0);
      }

      if (var1 != 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private void v$V() {
      this.e.e();
      this.y = null;
      this.t = -1;
      this.K = 0;
      this.R = false;
      this.U = false;
      this.E.clear();
   }

   private void X$V() {
      int var1 = m();

      while (!this.E.isEmpty()) {
         label21: {
            ChestStealer$PacketTime var2 = (ChestStealer$PacketTime)this.E.poll();
            ChestStealer$PacketTime var10000 = var2;
            if (var1 == 0) {
               if (var2 == null) {
                  break label21;
               }

               var10000 = var2;
            }

            com.elowen.utils.PacketUtils.c(var10000.K());
         }

         if (var1 != 0) {
            break;
         }
      }
   }

   void c$V() {
      if (this.P.w() && G.getConnection() != null) {
         String[] var1 = d;
         com.elowen.utils.PacketUtils.c(new ServerboundCustomPayloadPacket(new DiscardedPayload(Identifier.fromNamespaceAndPath("minecraft", "test"))));
         com.elowen.utils.PacketUtils.c(new ServerboundPongPacket(Integer.MAX_VALUE));
      }
   }

   private float K() {
      int var10000 = m();
      int var2 = (int)this.z.o$F();
      int var1 = var10000;
      int var3 = (int)this.C.o$F();
      var10000 = var2;
      int var10001 = var3;
      if (var1 == 0) {
         if (var2 > var3) {
            int var4 = var2;
            var2 = var3;
            var3 = var4;
         }

         var10000 = var2;
         var10001 = ThreadLocalRandom.current().nextInt(var3 - var2 + 1);
      }

      return var10000 + var10001;
   }

   private float T$F() {
      return this.X.o$F();
   }

   boolean N(boolean var1, double var2) {
      int var4;
      float var5;
      label51: {
         ChestStealer var6;
         label54: {
            var4 = m();
            boolean var10000 = var1;
            if (var4 == 0) {
               if (var1) {
                  var5 = this.T$F();
                  if (var4 == 0) {
                     break label51;
                  }
               }

               var6 = this;
               if (var4 != 0) {
                  break label54;
               }

               var10000 = this.v.w();
            }

            if (var10000) {
               var5 = var2 <= 1.5 ? 2.0F : 3.0F;
               if (var4 == 0) {
                  break label51;
               }
            }

            var6 = this;
         }

         var5 = var6.K();
      }

      boolean var7 = this.e.M(var5);
      if (var4 == 0) {
         if (var7) {
            this.e.e();
            return true;
         }

         var7 = false;
      }

      return var7;
   }

   @EventTarget(1)
   public void E(com.elowen.events.impl.EventMotion var1) {
      boolean var6 = false;
      int var2 = m();
      if (var1.Q() == EventType.PRE) {
         Minecraft var10000 = G;
         if (var2 == 0) {
            if (G.player == null) {
               return;
            }

            var10000 = G;
         }

         Screen var3 = var10000.gui.screen();
         ChestStealer var4 = this;
         if (var2 == 0) {
            if (!this.R) {
               Screen var5 = var3;
               if (var2 == 0) {
                  if (var3 != this.r) {
                     this.v$V();
                     this.r = var3;
                  }

                  var5 = var3;
               }

               var6 = var5 instanceof ContainerScreen;
               label95:
               if (var2 == 0) {
                  if (var6) {
                     var6 = ((ContainerScreen)var3).getMenu() instanceof ChestMenu;
                     if (var2 != 0) {
                        break label95;
                     }

                     if (var6) {
                        this.q((ContainerScreen)var3);
                        if (var2 == 0) {
                           return;
                        }
                     }
                  }

                  var6 = this.c.w();
               }

               label85:
               if (var2 == 0) {
                  if (var6) {
                     var6 = var3 instanceof FurnaceScreen;
                     if (var2 != 0) {
                        break label85;
                     }

                     if (var6) {
                        this.c((FurnaceScreen)var3);
                        if (var2 == 0) {
                           return;
                        }
                     }
                  }

                  var6 = this.b.w();
               }

               if (var2 == 0) {
                  if (!var6) {
                     return;
                  }

                  var6 = var3 instanceof BrewingStandScreen;
               }

               if (var6) {
                  this.G((BrewingStandScreen)var3);
               }

               return;
            }

            G.player.closeContainer();
            var4 = this;
         }

         var4.R = false;
      }
   }

   @EventTarget(2)
   public void k(EventGlobalPacket var1) {
      int var2;
      boolean var6;
      label70: {
         label73: {
            var2 = d$I();
            EventGlobalPacket var10000 = var1;
            if (var2 != 0) {
               if (var1.f$f() != EventType.RECEIVE) {
                  break label73;
               }

               var10000 = var1;
            }

            Packet var4 = var10000.g$Packet();
            var6 = var4 instanceof ClientboundContainerSetContentPacket;
            if (var2 == 0) {
               break label70;
            }

            if (var6) {
               ClientboundContainerSetContentPacket var3 = (ClientboundContainerSetContentPacket)var4;
               this.m = var3.containerId();
            }
         }

         var6 = this.V.w();
      }

      if (var2 != 0) {
         if (!var6) {
            return;
         }

         var6 = this.w();
      }

      if (var6 && G.player != null) {
         EventGlobalPacket var7 = var1;
         if (var2 != 0) {
            if (var1.f$f() != EventType.SEND) {
               return;
            }

            var7 = var1;
         }

         Packet var5 = var7.g$Packet();
         if (var2 != 0 && !this.k(var5)) {
            var1.c(true);
            this.E.add(new ChestStealer$PacketTime(var5, System.currentTimeMillis()));
         }
      }
   }

   private boolean k(Packet var1) {
      int var2 = d$I();
      boolean var10000 = var1 instanceof ServerboundMovePlayerPacket;
      if (var2 != 0) {
         if (!var10000) {
            var10000 = var1 instanceof ServerboundPongPacket;
            if (var2 == 0) {
               return var10000;
            }

            if (!var10000) {
               var10000 = var1 instanceof ServerboundUseItemPacket;
               if (var2 == 0) {
                  return var10000;
               }

               if (!var10000) {
                  var10000 = var1 instanceof ServerboundUseItemOnPacket;
                  if (var2 == 0) {
                     return var10000;
                  }

                  if (!var10000) {
                     var10000 = var1 instanceof ServerboundPlayerCommandPacket;
                     if (var2 == 0) {
                        return var10000;
                     }

                     if (!var10000) {
                        var10000 = var1 instanceof ServerboundContainerClickPacket;
                        if (var2 == 0) {
                           return var10000;
                        }

                        if (!var10000) {
                           var10000 = var1 instanceof ServerboundContainerClosePacket;
                           if (var2 == 0) {
                              return var10000;
                           }

                           if (!var10000) {
                              var10000 = var1 instanceof ServerboundInteractPacket;
                              if (var2 == 0) {
                                 return var10000;
                              }

                              if (!var10000) {
                                 var10000 = var1 instanceof ServerboundPunchPacket;
                                 if (var2 == 0) {
                                    return var10000;
                                 }

                                 if (!var10000) {
                                    var10000 = var1 instanceof ServerboundPlayerActionPacket;
                                    if (var2 == 0) {
                                       return var10000;
                                    }

                                    if (!var10000) {
                                       var10000 = var1 instanceof ServerboundSetCarriedItemPacket;
                                       if (var2 == 0) {
                                          return var10000;
                                       }

                                       if (!var10000) {
                                          var10000 = var1 instanceof ServerboundClientCommandPacket;
                                          if (var2 == 0) {
                                             return var10000;
                                          }

                                          if (!var10000) {
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

         var10000 = true;
      }

      return var10000;
   }

   @EventTarget(3)
   public void R(EventTick var1) {
      int var2 = d$I();
      boolean var10000 = this.V.w();
      if (var2 != 0) {
         if (!var10000) {
            return;
         }

         var10000 = this.w();
      }

      if (var10000 && var1.s$f() == EventType.PRE) {
         while (!this.E.isEmpty()) {
            ChestStealer$PacketTime var3 = (ChestStealer$PacketTime)this.E.peek();
            if (var2 != 0) {
               if (!((float)(System.currentTimeMillis() - var3.S$J()) >= this.F.o$F())) {
                  break;
               }

               com.elowen.utils.PacketUtils.c(((ChestStealer$PacketTime)this.E.poll()).K());
            }

            if (var2 == 0) {
               break;
            }
         }
      }
   }

   private void q(ContainerScreen var1) {
      boolean var11 = false;
      ChestStealer var12 = null;
      int var2;
      ChestMenu var3;
      String var4;
      String var5;
      String var6;
      int var10;
      label83: {
         label86: {
            var3 = (ChestMenu)var1.getMenu();
            var10 = m();
            var4 = var1.getTitle().getString();
            String[] var9 = d;
            var5 = Component.translatable("container.chest").getString();
            var2 = var10;
            var6 = Component.translatable("container.chestDouble").getString();
            String var7 = Component.translatable("container.enderchest").getString();
            var10 = ((var4.equals(var7)) ? 1 : 0);
            if (var2 == 0) {
               if ((var10 == 0)) {
                  break label86;
               }

               var10 = ((this.T.w()) ? 1 : 0);
            }

            if (var2 != 0) {
               break label83;
            }

            if (((var10) != 0)) {
               var10 = 1;
               break label83;
            }
         }

         var10 = 0;
      }

      label87: {
         boolean var8 = (boolean)((var10) != 0);
         var11 = var4.equals(var5);
         label70:
         if (var2 == 0) {
            if (!var11) {
               var11 = var4.equals(var6);
               if (var2 != 0) {
                  break label70;
               }

               if (!var11) {
                  var11 = var4.equals("Chest");
                  if (var2 != 0) {
                     break label70;
                  }

                  if (!var11) {
                     var11 = var8;
                     if (var2 != 0) {
                        break label70;
                     }

                     if (!var8) {
                        return;
                     }
                  }
               }
            }

            var12 = this;
            if (var2 != 0) {
               break label87;
            }

            var11 = this.D.w();
         }

         if (var11) {
            this.L(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST);
            if (var2 == 0) {
               return;
            }
         }

         var12 = this;
      }

      var12.G(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST);
   }

   private void c(FurnaceScreen var1) {
      int var10000 = d$I();
      FurnaceMenu var3 = (FurnaceMenu)var1.getMenu();
      int var2 = var10000;
      ChestStealer var4 = this;
      if (var2 != 0) {
         if (this.D.w()) {
            this.L(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.FURNACE);
            if (var2 != 0) {
               return;
            }
         }

         var4 = this;
      }

      var4.G(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.FURNACE);
   }

   private void G(BrewingStandScreen var1) {
      int var10000 = m();
      BrewingStandMenu var3 = (BrewingStandMenu)var1.getMenu();
      int var2 = var10000;
      ChestStealer var4 = this;
      if (var2 == 0) {
         if (this.D.w()) {
            this.L(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.BREWING);
            if (var2 == 0) {
               return;
            }
         }

         var4 = this;
      }

      var4.G(var3, com.elowen.modules.impl.player.ChestStealer$ContainerType.BREWING);
   }

   private void G(AbstractContainerMenu var1, ChestStealer$ContainerType var2) {
      ChestStealer var16 = null;
      ChestStealer var19 = null;
      double var20 = 0.0;
      ChestStealer var22 = null;
      List<Integer> var4 = var2.c(var1);
      int var10000 = m();
      List var5 = var4.stream().filter(v -> this.deobfLambda$processSteal$0(var1, var2, v)).collect(Collectors.toList());
      int var3 = var10000;
      var10000 = ((var5.isEmpty()) ? 1 : 0);
      if (var3 == 0) {
         if (var10000 != 0) {
            ChestStealer var23 = this;
            if (var3 == 0) {
               if (!this.w(var1)) {
                  return;
               }

               var23 = this;
            }

            var23.f$V();
            return;
         }

         var10000 = ((this.B.w()) ? 1 : 0);
      }

      if (var3 == 0) {
         if (var10000 != 0) {
            ChestStealer var14 = this;
            if (var3 == 0) {
               if (!this.U) {
                  ChestStealer var15 = this;
                  boolean var10001 = true;
                  if (var3 == 0) {
                     if (!this.N(true, 0.0)) {
                        return;
                     }

                     var15 = this;
                     var10001 = true;
                  }

                  var15.U = var10001;
               }

               var14 = this;
            }

            List var6 = var14.I(var5, var2, var1, this.t);
            Iterator var7 = var6.iterator();

            while (true) {
               if (var7.hasNext()) {
                  int var8 = (Integer)var7.next();
                  var16 = this;
                  if (var3 != 0) {
                     break;
                  }

                  if (var3 == 0) {
                     if (this.Y.w()) {
                        this.K = this.r(var1, this.K, var8);
                     }

                     this.c$V();
                     G.gameMode.handleContainerInput(var1.containerId, var8, 0, ContainerInput.QUICK_MOVE, G.player);
                     var16 = this;
                  }

                  var16.t = var8;
                  if (var3 == 0) {
                     continue;
                  }
               }

               var16 = this;
               break;
            }

            var16.f$V();
            if (var3 == 0) {
               return;
            }
         }

         var10000 = ((var5.isEmpty()) ? 1 : 0);
      }

      if (var3 == 0) {
         if (var10000 != 0) {
            return;
         }

         var10000 = this.t;
      }

      if (var3 == 0) {
         var10000 = var10000 == -1 ? 1 : 0;
      }

      int var10;
      int var11;
      label154: {
         var10 = var10000;
         var10000 = this.t;
         if (var3 == 0) {
            if (this.t != -1) {
               var11 = this.R(this.t, var5, var2, var1);
               if (var3 == 0) {
                  break label154;
               }
            }

            var10000 = this.R(0, var5, var2, var1);
         }

         var11 = var10000;
      }

      label147: {
         label186: {
            var10000 = var11;
            byte var24 = -1;
            if (var3 == 0) {
               if (var11 == -1) {
                  var11 = (Integer)var5.get(0);
               }

               var19 = this;
               if (var3 != 0) {
                  break label186;
               }

               var10000 = this.t;
               var24 = -1;
            }

            if (var10000 == var24) {
               var20 = 0.0;
               break label147;
            }

            var19 = this;
         }

         var20 = var19.j(this.t, var11, var1);
      }

      label188: {
         double var12 = var20;
         boolean var21 = this.N((boolean)((var10) != 0), var12);
         if (var3 == 0) {
            if (!var21) {
               return;
            }

            var22 = this;
            if (var3 != 0) {
               break label188;
            }

            var21 = this.Y.w();
         }

         if (var21) {
            this.K = this.r(var1, this.K, var11);
         }

         this.c$V();
         G.gameMode.handleContainerInput(var1.containerId, var11, 0, ContainerInput.QUICK_MOVE, G.player);
         var22 = this;
      }

      var22.t = var11;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   List<Integer> I(List var1, ChestStealer$ContainerType var2, AbstractContainerMenu var3, int var4) {
      int var5 = m();
      if (var1.isEmpty()) {
         return new ArrayList();
      }

      HashSet var6;
      ArrayList var7;
      int var8;
      int var10;
      label62: {
         var6 = new HashSet(var1);
         var7 = new ArrayList();
         var10 = var4;
         label61:
         if (var5 == 0) {
            if (var4 != -1) {
               var10 = ((var6.contains(var4)) ? 1 : 0);
               if (var5 != 0) {
                  break label61;
               }

               if (var10 != 0) {
                  var8 = var4;
                  var7.add(var8);
                  var6.remove(var8);
                  if (var5 == 0) {
                     var10 = ((var6.isEmpty()) ? 1 : 0);
                     break label62;
                  }
               }
            }

            var10 = this.R(0, new ArrayList(var6), var2, var3);
         }

         var8 = var10;
         var10 = var8;
         if (var5 == 0) {
            if (var8 != -1) {
               var7.add(var8);
               var6.remove(var8);
            }

            var10 = ((var6.isEmpty()) ? 1 : 0);
         }
      }

      while (var10 == 0) {
         int var9 = this.R(var8, new ArrayList(var6), var2, var3);
         var10 = var9;
         if (var5 == 0) {
            if (var9 == -1) {
               break;
            }

            var7.add(var9);
            var6.remove(var9);
            var10 = var9;
         }

         var8 = var10;
         if (var5 != 0) {
            break;
         }

         var10 = ((var6.isEmpty()) ? 1 : 0);
      }

      return var7;
   }

   private void L(AbstractContainerMenu var1, ChestStealer$ContainerType var2) {
      ChestStealer$ContainerSession var4 = null;
      int var3 = d$I();
      ChestStealer var10000 = this;
      if (var3 != 0) {
         if (!this.w(var1)) {
            return;
         }

         var10000 = this;
      }

      label89: {
         label88: {
            if (var3 != 0) {
               if (var10000.y != null) {
                  var4 = this.y;
                  if (var3 == 0) {
                     break label89;
                  }

                  if (this.y.n == var1) {
                     break label88;
                  }
               }

               var10000 = this;
            }

            var10000.y = new ChestStealer$ContainerSession(this, var1, var2, this.B.w());
         }

         var4 = this.y;
      }

      boolean var5 = var4.f$Z();
      if (var3 != 0) {
         if (var5) {
            this.f$V();
            return;
         }

         var5 = this.y.S$Z();
      }

      if (var3 != 0) {
         if (!var5) {
            var10000 = this;
            if (var3 != 0) {
               if (!this.N(true, 0.0)) {
                  return;
               }

               var10000 = this;
            }

            var10000.y.i();
         }

         var5 = this.B.w();
      }

      if (var3 != 0) {
         if (var5) {
            this.y.T$V();
            this.y.s$V();
            this.f$V();
            if (var3 != 0) {
               return;
            }
         }

         this.y.E$Z();
      }
   }

   private void f$V() {
      int var1 = d$I();
      ChestStealer var10000 = this;
      if (var1 != 0) {
         if (this.R) {
            return;
         }

         var10000 = this;
      }

      var10000.R = true;
   }

   private boolean w(AbstractContainerMenu var1) {
      int var2 = d$I();
      int var10000 = this.m;
      if (var2 != 0) {
         var10000 = this.m == var1.containerId ? 1 : 0;
      }

      return (boolean)((var10000) != 0);
   }

   private int X$I() {
      int var1;
      Screen var2;
      int var8;
      var8 = d$I();
      var2 = G.gui.screen();
      var1 = var8;
      var8 = ((var2 instanceof ContainerScreen) ? 1 : 0);
      label41:
      if (var1 != 0) {
         if (var8 != 0) {
            ContainerScreen var3 = (ContainerScreen)var2;
            AbstractContainerMenu var5 = var3.getMenu();
            var8 = ((var5 instanceof ChestMenu) ? 1 : 0);
            if (var1 == 0) {
               break label41;
            }

            if (var8 != 0) {
               ChestMenu var4 = (ChestMenu)var5;
               return var4.containerId;
            }
         }

         var8 = ((var2 instanceof FurnaceScreen) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var8 != 0) {
            FurnaceScreen var7 = (FurnaceScreen)var2;
            return ((FurnaceMenu)var7.getMenu()).containerId;
         }

         var8 = ((var2 instanceof BrewingStandScreen) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var8 != 0) {
            BrewingStandScreen var6 = (BrewingStandScreen)var2;
            return ((BrewingStandMenu)var6.getMenu()).containerId;
         }

         var8 = -1;
      }

      return var8;
   }

   public int G(AbstractContainerMenu var1) {
      int var2 = d$I();
      int var10000 = ((var1 instanceof ChestMenu) ? 1 : 0);
      if (var2 != 0) {
         if (var10000 != 0) {
            return ((ChestMenu)var1).getRowCount() * 9;
         }

         var10000 = ((var1 instanceof FurnaceMenu) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 != 0) {
            return 3;
         }

         var10000 = ((var1 instanceof BrewingStandMenu) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 != 0) {
            return 5;
         }

         var10000 = Math.max(0, var1.slots.size() - 36);
      }

      return var10000;
   }

   public boolean U(ItemStack var1, AbstractContainerMenu var2, ChestStealer$ContainerType var3) {
      int var4 = m();
      boolean var10000 = e$Z(var1);
      if (var4 == 0) {
         if (var10000) {
            if (var3 == com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST) {
               var10000 = var2 instanceof ChestMenu;
               if (var4 != 0) {
                  return var10000;
               }

               if (var10000) {
                  return this.t((ChestMenu)var2, var1);
               }
            }

            return true;
         }

         var10000 = false;
      }

      return var10000;
   }

   private boolean t(ChestMenu var1, ItemStack var2) {
      boolean var8 = false;
      int var3 = d$I();
      ItemStack var10000 = var2;
      if (var3 != 0) {
         if (var2.getItem() == Items.MACE) {
            return true;
         }

         var10000 = var2;
      }

      boolean var7 = com.elowen.utils.InventoryUtils.C$Z(var10000);
      if (var3 != 0) {
         if (!var7) {
            boolean var18 = com.elowen.utils.InventoryUtils.R$Z(var2);
            if (var3 == 0) {
               return var18;
            }

            if (!var18) {
               int var4 = 0;

               while (true) {
                  if (var4 < var1.getRowCount() * 9) {
                     ItemStack var5 = var1.getSlot(var4).getItem();
                     var8 = com.elowen.utils.InventoryUtils.u(var2);
                     if (var3 == 0) {
                        break;
                     }

                     label223: {
                        label197:
                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) != 0) {
                              var8 = com.elowen.utils.InventoryUtils.u(var5);
                              if (var3 == 0) {
                                 break label197;
                              }

                              if (((var8) ? 1 : 0) != 0) {
                                 EquipmentSlot var6 = com.elowen.utils.InventoryUtils.R$EquipmentSlot(var2);
                                 if (var6 == com.elowen.utils.InventoryUtils.R$EquipmentSlot(var5)) {
                                    float var13;
                                    int var9 = (var13 = com.elowen.utils.InventoryUtils.e$F(var5) - com.elowen.utils.InventoryUtils.e$F(var2)) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
                                    if (var3 == 0) {
                                       return (boolean)((var9) != 0);
                                    }

                                    if (var9 > 0) {
                                       return false;
                                    }
                                 }

                                 if (var3 != 0) {
                                    break label223;
                                 }
                              }
                           }

                           var8 = var2.is(ItemTags.SWORDS);
                        }

                        label190:
                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) != 0) {
                              var8 = var5.is(ItemTags.SWORDS);
                              if (var3 == 0) {
                                 break label190;
                              }

                              if (((var8) ? 1 : 0) != 0) {
                                 float var14;
                                 int var10 = (var14 = com.elowen.utils.InventoryUtils.f(var5) - com.elowen.utils.InventoryUtils.f(var2)) == 0.0F ? 0 : (var14 < 0.0F ? -1 : 1);
                                 if (var3 == 0) {
                                    return (boolean)((var10) != 0);
                                 }

                                 if (var10 > 0) {
                                    return false;
                                 }
                                 break label223;
                              }
                           }

                           var8 = var2.is(ItemTags.PICKAXES);
                        }

                        label183:
                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) != 0) {
                              var8 = var5.is(ItemTags.PICKAXES);
                              if (var3 == 0) {
                                 break label183;
                              }

                              if (((var8) ? 1 : 0) != 0) {
                                 float var15;
                                 int var11 = (var15 = com.elowen.utils.InventoryUtils.k(var5) - com.elowen.utils.InventoryUtils.k(var2)) == 0.0F ? 0 : (var15 < 0.0F ? -1 : 1);
                                 if (var3 == 0) {
                                    return (boolean)((var11) != 0);
                                 }

                                 if (var11 > 0) {
                                    return false;
                                 }
                                 break label223;
                              }
                           }

                           var8 = var2.is(ItemTags.AXES);
                        }

                        label176:
                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) != 0) {
                              var8 = var5.is(ItemTags.AXES);
                              if (var3 == 0) {
                                 break label176;
                              }

                              if (((var8) ? 1 : 0) != 0) {
                                 float var16;
                                 int var12 = (var16 = com.elowen.utils.InventoryUtils.k(var5) - com.elowen.utils.InventoryUtils.k(var2)) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
                                 if (var3 == 0) {
                                    return (boolean)((var12) != 0);
                                 }

                                 if (var12 > 0) {
                                    return false;
                                 }
                                 break label223;
                              }
                           }

                           var8 = var2.is(ItemTags.SHOVELS);
                        }

                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) == 0) {
                              break label223;
                           }

                           var8 = var5.is(ItemTags.SHOVELS);
                        }

                        if (var3 != 0) {
                           if (((var8) ? 1 : 0) == 0) {
                              break label223;
                           }

                           float var17;
                           var8 = (((byte)((var17 = com.elowen.utils.InventoryUtils.k(var5) - com.elowen.utils.InventoryUtils.k(var2)) == 0.0F ? 0 : (var17 < 0.0F ? -1 : 1))) != 0);
                        }

                        if (var3 == 0) {
                           return (boolean)var8;
                        }

                        if (((var8) ? 1 : 0) > 0) {
                           return false;
                        }
                     }

                     var4++;
                     if (var3 != 0) {
                        continue;
                     }
                  }

                  var8 = ((1) != 0);
                  break;
               }

               return (boolean)var8;
            }
         }

         var7 = true;
      }

      return var7;
   }

   public static boolean e$Z(ItemStack var0) {
      net.minecraft.world.item.Item var22 = null;
      boolean var26 = false;
      int var29 = 0;
      int var1 = m();
      ItemStack var10000 = var0;
      if (var1 == 0) {
         if (var0.isEmpty()) {
            return false;
         }

         var10000 = var0;
      }

      label462: {
         Item var18 = var10000.getItem();
         Item var10001 = Items.WIND_CHARGE;
         if (var1 == 0) {
            if (var18 == Items.WIND_CHARGE) {
               return true;
            }

            var10000 = var0;
            if (var1 != 0) {
               break label462;
            }

            var18 = var0.getItem();
            var10001 = Items.MACE;
         }

         if (var18 == var10001) {
            return true;
         }

         var10000 = var0;
      }

      boolean var20 = com.elowen.utils.InventoryUtils.C$Z(var10000);
      if (var1 == 0) {
         label448:
         if (!var20) {
            boolean var21 = com.elowen.utils.InventoryUtils.R$Z(var0);
            if (var1 == 0) {
               if (var21) {
                  break label448;
               }

               var21 = com.elowen.utils.InventoryUtils.u(var0);
            }

            if (var1 == 0) {
               if (var21) {
                  float var10 = com.elowen.utils.InventoryUtils.e$F(var0);
                  float var17 = com.elowen.utils.InventoryUtils.T(com.elowen.utils.InventoryUtils.R$EquipmentSlot(var0));
                  float var41;
                  int var39 = (var41 = var10 - var17) == 0.0F ? 0 : (var41 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var39 = var39 > 0 ? 1 : 0;
                  }

                  return (boolean)((var39) != 0);
               }

               var21 = var0.is(ItemTags.SWORDS);
            }

            if (var1 == 0) {
               if (var21) {
                  float var9 = com.elowen.utils.InventoryUtils.f(var0);
                  float var16 = com.elowen.utils.InventoryUtils.B$F();
                  float var42;
                  int var38 = (var42 = var9 - var16) == 0.0F ? 0 : (var42 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var38 = var38 > 0 ? 1 : 0;
                  }

                  return (boolean)((var38) != 0);
               }

               var21 = var0.is(ItemTags.PICKAXES);
            }

            if (var1 == 0) {
               if (var21) {
                  float var8 = com.elowen.utils.InventoryUtils.k(var0);
                  float var15 = com.elowen.utils.InventoryUtils.m();
                  float var43;
                  int var37 = (var43 = var8 - var15) == 0.0F ? 0 : (var43 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var37 = var37 > 0 ? 1 : 0;
                  }

                  return (boolean)((var37) != 0);
               }

               var21 = var0.is(ItemTags.AXES);
            }

            if (var1 == 0) {
               if (var21) {
                  float var7 = com.elowen.utils.InventoryUtils.k(var0);
                  float var14 = com.elowen.utils.InventoryUtils.I();
                  float var44;
                  int var36 = (var44 = var7 - var14) == 0.0F ? 0 : (var44 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var36 = var36 > 0 ? 1 : 0;
                  }

                  return (boolean)((var36) != 0);
               }

               var21 = var0.is(ItemTags.SHOVELS);
            }

            if (var1 == 0) {
               if (var21) {
                  float var6 = com.elowen.utils.InventoryUtils.k(var0);
                  float var13 = com.elowen.utils.InventoryUtils.b$F();
                  float var45;
                  int var35 = (var45 = var6 - var13) == 0.0F ? 0 : (var45 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var35 = var35 > 0 ? 1 : 0;
                  }

                  return (boolean)((var35) != 0);
               }

               var21 = var0.getItem() instanceof CrossbowItem;
            }

            if (var1 == 0) {
               if (var21) {
                  float var5 = com.elowen.utils.InventoryUtils.S(var0);
                  float var12 = com.elowen.utils.InventoryUtils.o$F();
                  float var46;
                  int var34 = (var46 = var5 - var12) == 0.0F ? 0 : (var46 < 0.0F ? -1 : 1);
                  if (var1 == 0) {
                     var34 = var34 > 0 ? 1 : 0;
                  }

                  return (boolean)((var34) != 0);
               }

               var21 = var0.getItem() instanceof BowItem;
            }

            label466: {
               label427:
               if (var1 == 0) {
                  if (var21) {
                     var21 = com.elowen.utils.InventoryUtils.a(var0);
                     if (var1 != 0) {
                        break label427;
                     }

                     if (var21) {
                        float var2 = com.elowen.utils.InventoryUtils.z(var0);
                        float var3 = com.elowen.utils.InventoryUtils.G$F();
                        float var47;
                        int var23 = (var47 = var2 - var3) == 0.0F ? 0 : (var47 < 0.0F ? -1 : 1);
                        if (var1 == 0) {
                           var23 = var23 > 0 ? 1 : 0;
                        }

                        return (boolean)((var23) != 0);
                     }
                  }

                  var22 = var0.getItem();
                  if (var1 != 0) {
                     break label466;
                  }

                  var21 = var22 instanceof BowItem;
               }

               label417: {
                  if (var21) {
                     var10000 = var0;
                     if (var1 != 0) {
                        break label417;
                     }

                     if (com.elowen.utils.InventoryUtils.F(var0)) {
                        float var4 = com.elowen.utils.InventoryUtils.U$F(var0);
                        float var11 = com.elowen.utils.InventoryUtils.A$F();
                        float var48;
                        int var25 = (var48 = var4 - var11) == 0.0F ? 0 : (var48 < 0.0F ? -1 : 1);
                        if (var1 == 0) {
                           var25 = var25 > 0 ? 1 : 0;
                        }

                        return (boolean)((var25) != 0);
                     }
                  }

                  var10000 = var0;
               }

               var22 = var10000.getItem();
            }

            Item var40 = Items.COMPASS;
            if (var1 == 0) {
               if (var22 == Items.COMPASS) {
                  boolean var33 = com.elowen.utils.InventoryUtils.C(var0.getItem());
                  if (var1 == 0) {
                     var33 = !var33;
                  }

                  return var33;
               }

               var22 = var0.getItem();
               var40 = Items.WATER_BUCKET;
            }

            label469: {
               label470: {
                  label471: {
                     if (var1 == 0) {
                        label404: {
                           if (var22 == var40) {
                              var22 = Items.WATER_BUCKET;
                              if (var1 != 0) {
                                 break label404;
                              }

                              if (com.elowen.utils.InventoryUtils.d(Items.WATER_BUCKET) >= InventoryCleaner.j$I()) {
                                 return false;
                              }
                           }

                           var22 = var0.getItem();
                        }

                        if (var1 != 0) {
                           break label471;
                        }

                        var40 = Items.LAVA_BUCKET;
                     }

                     if (var22 == var40) {
                        var26 = ((com.elowen.utils.InventoryUtils.d(Items.LAVA_BUCKET)) != 0);
                        if (var1 != 0) {
                           break label470;
                        }

                        if (((var26) ? 1 : 0) >= InventoryCleaner.H()) {
                           return false;
                        }
                     }

                     var22 = var0.getItem();
                  }

                  if (var1 != 0) {
                     break label469;
                  }

                  var26 = var22 instanceof BlockItem;
               }

               label390: {
                  if (((var26) ? 1 : 0) != 0) {
                     var10000 = var0;
                     if (var1 != 0) {
                        break label390;
                     }

                     if (Scaffold.s(var0)) {
                        int var28 = com.elowen.utils.InventoryUtils.M() + var0.getCount();
                        if (var1 != 0) {
                           return (boolean)((var28) != 0);
                        }

                        if (var28 >= InventoryCleaner.b$I()) {
                           return false;
                        }
                     }
                  }

                  var10000 = var0;
               }

               var22 = var10000.getItem();
            }

            label473: {
               label474: {
                  if (var1 == 0) {
                     if (var22 == Items.ARROW) {
                        var29 = com.elowen.utils.InventoryUtils.d(Items.ARROW) + var0.getCount();
                        if (var1 != 0) {
                           break label474;
                        }

                        if (var29 >= InventoryCleaner.C$I()) {
                           return false;
                        }
                     }

                     var22 = var0.getItem();
                  }

                  if (var1 != 0) {
                     break label473;
                  }

                  var29 = ((var22 instanceof FishingRodItem) ? 1 : 0);
               }

               if (var29 != 0) {
                  var22 = Items.FISHING_ROD;
                  if (var1 != 0) {
                     break label473;
                  }

                  if (com.elowen.utils.InventoryUtils.d(Items.FISHING_ROD) >= 1) {
                     return false;
                  }
               }

               var22 = var0.getItem();
            }

            label356: {
               label500: {
                  if (var1 == 0) {
                     if (var22 != Items.SNOWBALL) {
                        var10000 = var0;
                        if (var1 != 0) {
                           break label356;
                        }

                        if (var0.getItem() != Items.EGG) {
                           break label500;
                        }
                     }

                     var22 = Items.SNOWBALL;
                  }

                  int var31 = com.elowen.utils.InventoryUtils.d(var22) + com.elowen.utils.InventoryUtils.d(Items.EGG) + var0.getCount();
                  if (var1 != 0) {
                     return (boolean)((var31) != 0);
                  }

                  if (var31 < InventoryCleaner.G$I()) {
                     boolean var49 = InventoryCleaner.e();
                     if (var1 != 0) {
                        return var49;
                     }

                     if (var49) {
                        break label500;
                     }
                  }

                  return false;
               }

               var10000 = var0;
            }

            if (var1 == 0) {
               if (com.elowen.utils.InventoryUtils.e$Z(var10000)) {
                  return false;
               }

               var10000 = var0;
            }

            return com.elowen.utils.InventoryUtils.U$Z(var10000);
         }

         var20 = true;
      }

      return var20;
   }

   private int I(AbstractContainerMenu var1, ChestStealer$ContainerType var2) {
      int var3 = m();
      if (var2 == com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST) {
         int var10000 = ((var1 instanceof ChestMenu) ? 1 : 0);
         if (var3 != 0) {
            return var10000;
         }

         if (var10000 != 0) {
            return ((ChestMenu)var1).getRowCount();
         }
      }

      return 1;
   }

   private int N(ChestStealer$ContainerType var1) {
      int var2 = m();
      ChestStealer$ContainerType var10000 = var1;
      ChestStealer$ContainerType var10001 = com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST;
      if (var2 == 0) {
         if (var1 == com.elowen.modules.impl.player.ChestStealer$ContainerType.CHEST) {
            return 9;
         }

         var10000 = var1;
         var10001 = com.elowen.modules.impl.player.ChestStealer$ContainerType.FURNACE;
      }

      if (var2 == 0) {
         if (var10000 == var10001) {
            return 3;
         }

         var10000 = var1;
         var10001 = com.elowen.modules.impl.player.ChestStealer$ContainerType.BREWING;
      }

      return var10000 == var10001 ? 5 : 0;
   }

   private Point O(int var1, ChestStealer$ContainerType var2, AbstractContainerMenu var3) {
      int var10000 = m();
      int var5 = this.N(var2);
      int var4 = var10000;
      int var6 = this.I(var3, var2);
      var10000 = var1;
      if (var4 == 0) {
         if (var1 < 0) {
            return new Point(0, 0);
         }

         var10000 = var1;
      }

      return var10000 < var6 * var5 ? new Point(var1 % var5, var1 / var5) : new Point(0, 0);
   }

   private double e(int var1, int var2, ChestStealer$ContainerType var3, AbstractContainerMenu var4) {
      Point var5 = this.O(var1, var3, var4);
      Point var6 = this.O(var2, var3, var4);
      int var7 = var5.x - var6.x;
      int var8 = var5.y - var6.y;
      return var7 * var7 + var8 * var8;
   }

   int R(int var1, List var2, ChestStealer$ContainerType var3, AbstractContainerMenu var4) {
      int var5 = m();
      int var10000 = ((var2.isEmpty()) ? 1 : 0);
      if (var5 == 0) {
         if (var10000 != 0) {
            return -1;
         }

         var10000 = (Integer)var2.get(0);
      }

      int var6 = var10000;
      double var7 = Double.MAX_VALUE;
      Iterator var9 = var2.iterator();

      while (true) {
         if (var9.hasNext()) {
            int var10 = (Integer)var9.next();
            double var11 = this.e(var1, var10, var3, var4);
            double var14;
            var10000 = (var14 = var11 - var7) == 0.0 ? 0 : (var14 < 0.0 ? -1 : 1);
            if (var5 != 0) {
               break;
            }

            label36: {
               if (var5 == 0) {
                  if (var10000 >= 0) {
                     break label36;
                  }

                  var7 = var11;
                  var10000 = var10;
               }

               var6 = var10000;
            }

            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var6;
         break;
      }

      return var10000;
   }

   private double[] f(int var1, AbstractContainerMenu var2) {
      double var15 = 0.0;
      int var10000 = d$I();
      int var4 = this.G(var2);
      int var3 = var10000;
      var10000 = var1;
      int var10001 = var4;
      if (var3 != 0) {
         if (var1 < var4) {
            var10000 = ((var2 instanceof ChestMenu) ? 1 : 0);
            if (var3 != 0) {
               if (var10000 != 0) {
                  return new double[]{var1 % 9, var1 / 9};
               }

               var10000 = ((var2 instanceof FurnaceMenu) ? 1 : 0);
            }

            if (var3 != 0) {
               if (var10000 != 0) {
                  var10000 = var1;
                  if (var3 != 0) {
                     switch (var1) {
                        case 0:
                           return new double[]{2.5, 0.0};
                        case 1:
                           return new double[]{2.5, 2.0};
                        case 2:
                           return new double[]{6.0, 1.0};
                        default:
                           var10000 = 2;
                     }
                  }

                  double[] var22 = new double[var10000];
                  var22[0] = 0.0;
                  var22[1] = 0.0;
                  return var22;
               }

               var10000 = ((var2 instanceof BrewingStandMenu) ? 1 : 0);
            }

            if (var3 != 0) {
               if (var10000 != 0) {
                  var10000 = var1;
                  if (var3 != 0) {
                     switch (var1) {
                        case 0:
                           return new double[]{5.3, 1.5};
                        case 1:
                           return new double[]{4.0, 2.0};
                        case 2:
                           return new double[]{2.6, 1.5};
                        case 3:
                           return new double[]{4.0, 0.0};
                        case 4:
                           return new double[]{0.5, 0.0};
                        default:
                           var10000 = 2;
                     }
                  }

                  double[] var20 = new double[var10000];
                  var20[0] = 0.0;
                  var20[1] = 0.0;
                  return var20;
               }

               var10000 = this.J(var2);
            }

            int var12 = var10000;
            return new double[]{var1 % var12, var1 / var12};
         }

         var10000 = var1;
         var10001 = var4;
      }

      int var5;
      int var6;
      int var7;
      label97: {
         var5 = var10000 - var10001;
         var6 = var5 % 9;
         var7 = var5 / 9;
         var10000 = ((var2 instanceof ChestMenu) ? 1 : 0);
         if (var3 != 0) {
            if (var10000 == 0) {
               var15 = 3.0;
               break label97;
            }

            var10000 = ((ChestMenu)var2).getRowCount();
         }

         var15 = var10000;
      }

      double var8 = var15;
      double var10 = var8 + var7 + 0.5;
      var10000 = var5;
      if (var3 != 0) {
         if (var5 >= 27) {
            var10 += 0.25;
         }

         var10000 = 2;
      }

      double[] var17 = new double[var10000];
      var17[0] = var6;
      var17[1] = var10;
      return var17;
   }

   double j(int var1, int var2, AbstractContainerMenu var3) {
      double[] var4 = this.f(var1, var3);
      double[] var5 = this.f(var2, var3);
      return Math.hypot(var4[0] - var5[0], var4[1] - var5[1]);
   }

   private int J(AbstractContainerMenu var1) {
      int var2 = d$I();
      int var10000 = ((var1 instanceof ChestMenu) ? 1 : 0);
      if (var2 != 0) {
         if (var10000 != 0) {
            return 9;
         }

         var10000 = ((var1 instanceof FurnaceMenu) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 != 0) {
            return 3;
         }

         var10000 = ((var1 instanceof BrewingStandMenu) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 != 0) {
            return 5;
         }

         var10000 = this.G(var1);
      }

      int var3 = var10000;
      return Math.max(1, var3);
   }

   public int r(AbstractContainerMenu var1, int var2, int var3) {
      int var4 = m();
      int var10000 = var2;
      if (var4 == 0) {
         label198:
         if (var2 >= 0) {
            var10000 = var3;
            if (var4 == 0) {
               if (var3 < 0) {
                  break label198;
               }

               double var27;
               var10000 = (var27 = this.j(var2, var3, var1) - 3.0) == 0.0 ? 0 : (var27 < 0.0 ? -1 : 1);
            }

            if (var4 == 0) {
               if (var10000 <= 0) {
                  return var2;
               }

               var10000 = var1.slots.size();
            }

            int var5 = var10000;
            int var6 = var2;
            boolean[] var7 = new boolean[var5];
            var7[var6] = true;
            int var8 = 0;

            while (true) {
               if (this.j(var6, var3, var1) > 3.0) {
                  var10000 = var8;
                  if (var4 != 0 || var4 != 0) {
                     break;
                  }

                  label183:
                  if (var8 < 20) {
                     var8++;
                     int var9 = -1;
                     double var10 = Double.MAX_VALUE;
                     int var12 = 0;

                     label180: {
                        while (var12 < var5) {
                           var10000 = ((var7[var12]) ? 1 : 0);
                           if (var4 != 0) {
                              break label180;
                           }

                           label176: {
                              if (var10000 == 0) {
                                 double var13 = this.j(var6, var12, var1);
                                 if (var4 != 0) {
                                    break label176;
                                 }

                                 label173:
                                 if (var13 > 0.0) {
                                    double var24 = var13;
                                    if (var4 == 0) {
                                       if (!(var13 <= 3.0)) {
                                          break label173;
                                       }

                                       var24 = this.j(var12, var3, var1);
                                    }

                                    double var15 = var24;
                                    if (var4 != 0) {
                                       break label176;
                                    }

                                    label166:
                                    if (var15 < this.j(var6, var3, var1)) {
                                       double var28;
                                       var10000 = (var28 = var15 - var10) == 0.0 ? 0 : (var28 < 0.0 ? -1 : 1);
                                       if (var4 == 0) {
                                          if (var10000 >= 0) {
                                             break label166;
                                          }

                                          var10000 = var12;
                                       }

                                       var9 = var10000;
                                       var10 = var15;
                                    }
                                 }
                              }

                              var12++;
                           }

                           if (var4 != 0) {
                              break;
                           }
                        }

                        var10000 = var9;
                     }

                     label150:
                     if (var4 == 0) {
                        if (var10000 == -1) {
                           double var19 = Double.MAX_VALUE;
                           int var14 = 0;

                           label145: {
                              while (var14 < var5) {
                                 var10000 = ((var7[var14]) ? 1 : 0);
                                 if (var4 != 0) {
                                    break label145;
                                 }

                                 label141: {
                                    if (var10000 == 0) {
                                       double var20 = this.j(var6, var14, var1);
                                       if (var4 != 0) {
                                          break label141;
                                       }

                                       label138:
                                       if (var20 > 0.0) {
                                          double var26 = var20;
                                          if (var4 == 0) {
                                             if (!(var20 <= 3.0)) {
                                                break label138;
                                             }

                                             var26 = this.j(var14, var3, var1);
                                          }

                                          double var17 = var26;
                                          if (var4 != 0) {
                                             break label141;
                                          }

                                          if (var17 < var19) {
                                             var9 = var14;
                                             var19 = var17;
                                          }
                                       }
                                    }

                                    var14++;
                                 }

                                 if (var4 != 0) {
                                    break;
                                 }
                              }

                              var10000 = var9;
                           }

                           if (var4 != 0) {
                              break label150;
                           }

                           if (var10000 == -1) {
                              break label183;
                           }
                        }

                        var10000 = var9;
                     }

                     var6 = var10000;
                     var7[var6] = true;
                     G.gameMode.handleContainerInput(var1.containerId, var6, 0, ContainerInput.SWAP, G.player);
                     G.gameMode.handleContainerInput(var1.containerId, var6, 0, ContainerInput.SWAP, G.player);
                     if (var4 == 0) {
                        continue;
                     }
                  }
               }

               var10000 = var6;
               break;
            }

            return var10000;
         }

         var10000 = var2;
      }

      return var10000;
   }

   static Minecraft R$Minecraft() {
      return G;
   }

   static Minecraft v$Minecraft() {
      return G;
   }

   static Minecraft L() {
      return G;
   }

   static Minecraft s$Minecraft() {
      return G;
   }

   static Minecraft G$Minecraft() {
      return G;
   }

   static Minecraft O$MC() {
      return G;
   }

   private boolean deobfLambda$processSteal$0(AbstractContainerMenu var1, ChestStealer$ContainerType var2, Integer var3) {
      return this.U(var1.getSlot(var3).getItem(), var1, var2);
   }

   private Boolean deobfLambda$new$2() {
      return this.V.w();
   }

   private Boolean deobfLambda$new$1() {
      int var1 = m();
      boolean var10000 = this.v.w();
      if (var1 == 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$0() {
      int var1 = m();
      boolean var10000 = this.v.w();
      if (var1 == 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   public static void g(int var0) {
      j = var0;
   }

   public static int d$I() {
      return j;
   }

   public static int m() {
      int var0 = d$I();
      return var0 == 0 ? 24 : 0;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      g(73);
   }
}
