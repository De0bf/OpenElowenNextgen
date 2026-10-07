package com.elowen.modules.impl.player;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventSprint;
import com.elowen.events.impl.EventPacket;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.ui.notification.NotificationLevel;
import com.elowen.ui.notification.Notification;
import com.elowen.utils.TickTimeHelper;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.MoveUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;

@ModuleInfo(R = "InventoryManager", a = "Automatically manage your inventory", M = Category.PLAYER)
public class InventoryCleaner extends Module {
   private static final TickTimeHelper J;
   private static final EquipmentSlot[] BG;
   private final com.elowen.values.impl.FloatValue t;
   private final com.elowen.values.impl.FloatValue o;
   private final com.elowen.values.impl.FloatValue i;
   private final BooleanValue V;
   private final com.elowen.values.impl.FloatValue P;
   private boolean l;
   com.elowen.values.impl.ModeValue B;
   BooleanValue I;
   BooleanValue BP;
   BooleanValue v;
   com.elowen.values.impl.FloatValue Bq;
   BooleanValue C;
   com.elowen.values.impl.FloatValue BI;
   com.elowen.values.impl.FloatValue BQ;
   BooleanValue c;
   com.elowen.values.impl.FloatValue j;
   BooleanValue Y;
   com.elowen.values.impl.FloatValue BA;
   BooleanValue B6;
   com.elowen.values.impl.FloatValue X;
   com.elowen.values.impl.ModeValue Q;
   com.elowen.values.impl.FloatValue r;
   BooleanValue U;
   com.elowen.values.impl.FloatValue Bj;
   BooleanValue e;
   com.elowen.values.impl.FloatValue BN;
   BooleanValue R;
   com.elowen.values.impl.FloatValue F;
   BooleanValue M;
   com.elowen.values.impl.FloatValue B3;
   BooleanValue p;
   com.elowen.values.impl.FloatValue y;
   com.elowen.values.impl.FloatValue Bb;
   BooleanValue Bd;
   BooleanValue Z;
   com.elowen.values.impl.FloatValue h;
   com.elowen.values.impl.FloatValue E;
   BooleanValue T;
   com.elowen.values.impl.FloatValue x;
   private final BooleanValue f;
   private final BooleanValue m;
   private final com.elowen.values.impl.FloatValue K;
   private final com.elowen.values.impl.FloatValue BO;
   private final BooleanValue Ba;
   private final BooleanValue BT;
   private final com.elowen.values.impl.FloatValue q;
   private final BooleanValue b;
   private final BooleanValue BW;
   private int D;
   private boolean By;
   int d;
   private boolean S;
   private boolean Br;
   private int Bf;
   private int Be;
   private boolean Bs;
   private int z;
   private boolean BD;
   private static final String[] bb = new String[]{"Switch Pickaxe", "Fishing Rod", "Switch Axe", "Switch Golden Apple", "Keep Eggs & Snowballs", "Move Arrow", "Move Compass", "Keep Wind Charge", "Punch Bow", "Max Delay (Ticks)", "Bow Slot", "Power Bow", "Mace Slot", "Offhand Items", "Fireball Slot", "Keep Lava Buckets", "Bow Priority", "Switch Fireball", "Axe Slot", "Max Block Size", "Fishing Rod", "Switch Water Bucket", "Rod Slot", "Switch Bow or Crossbow", "Crossbow", "Switch Ender Pearl", "Switch Block", "Block Slot", "First Delay (Ticks)", "Power Bow", "Crossbow", "Projectile", "None", "Switch Rod", "Switch Mace", "Min Delay (Ticks)", "Keep Mace", "Keep Water Buckets", "Block", "Max Eggs & Snowballs Size", "Max Wind Charge Size", "Golden Apple Slot", "Golden Apple", "Water Bucket Slot", "Switch Wind Charge", "Items", "Switch Sword", "Wind Charge Slot", "Inventory Only", "Golden Apple", "Always Mode", "点击使用", "Sword Slot", "Ender Pearl Slot", "Eggs & Snowballs Slot", "Projectile", "Max Arrow Size", "Duplicate slot config in Inventory Manager! Please check your config!", "Switch Eggs & Snowballs", "Pickaxe Slot", "Throw Items", "Auto Armor", "Block"};
   public InventoryCleaner() {
      String[] var2 = bb;
      this.t = com.elowen.values.ValueBuilder.m(this, "Min Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).f$K().L();
      this.o = com.elowen.values.ValueBuilder.m(this, "Max Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).f$K().L();
      this.i = com.elowen.values.ValueBuilder.m(this, "First Delay (Ticks)").d(3.0F).V(1.0F).w(0.0F).M(10.0F).f$K().L();
      int var10000 = ChestStealer.d$I();
      this.V = com.elowen.values.ValueBuilder.m(this, "Always Mode").h(false).l(this::deobfLambda$new$0).f$K().f$O();
      this.P = com.elowen.values.ValueBuilder.m(this, "Items").d(1.0F).V(1.0F).w(1.0F).M(5.0F).l(this::deobfLambda$new$1).f$K().L();
      this.l = false;
      this.B = com.elowen.values.ValueBuilder.m(this, "Offhand Items").W(new String[]{"None", "Golden Apple", "Projectile", "Fishing Rod", "Block"}).f$K().T$t();
      this.I = com.elowen.values.ValueBuilder.m(this, "Auto Armor").h(true).f$K().f$O();
      this.BP = com.elowen.values.ValueBuilder.m(this, "Inventory Only").h(true).f$K().f$O();
      this.v = com.elowen.values.ValueBuilder.m(this, "Switch Sword").h(true).f$K().f$O();
      this.Bq = com.elowen.values.ValueBuilder.m(this, "Sword Slot").d(1.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$2).f$K().L();
      this.C = com.elowen.values.ValueBuilder.m(this, "Switch Block").l(this::deobfLambda$new$3).h(true).f$K().f$O();
      this.BI = com.elowen.values.ValueBuilder.m(this, "Block Slot").d(2.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$4).f$K().L();
      this.BQ = com.elowen.values.ValueBuilder.m(this, "Max Block Size").d(256.0F).V(64.0F).w(64.0F).M(512.0F).l(this::deobfLambda$new$5).f$K().L();
      this.c = com.elowen.values.ValueBuilder.m(this, "Switch Pickaxe").h(true).f$K().f$O();
      this.j = com.elowen.values.ValueBuilder.m(this, "Pickaxe Slot").d(3.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$6).f$K().L();
      this.Y = com.elowen.values.ValueBuilder.m(this, "Switch Axe").h(true).f$K().f$O();
      this.BA = com.elowen.values.ValueBuilder.m(this, "Axe Slot").d(4.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$7).f$K().L();
      this.B6 = com.elowen.values.ValueBuilder.m(this, "Switch Bow or Crossbow").h(true).f$K().f$O();
      this.X = com.elowen.values.ValueBuilder.m(this, "Bow Slot").d(5.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$8).f$K().L();
      this.Q = com.elowen.values.ValueBuilder.m(this, "Bow Priority").W(new String[]{"Crossbow", "Power Bow", "Punch Bow"}).l(this::deobfLambda$new$9).f$K().T$t();
      this.r = com.elowen.values.ValueBuilder.m(this, "Max Arrow Size").d(256.0F).V(64.0F).w(64.0F).M(512.0F).l(this::deobfLambda$new$10).f$K().L();
      this.U = com.elowen.values.ValueBuilder.m(this, "Switch Water Bucket").h(true).f$K().f$O();
      this.Bj = com.elowen.values.ValueBuilder.m(this, "Water Bucket Slot").d(6.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$11).f$K().L();
      this.e = com.elowen.values.ValueBuilder.m(this, "Switch Ender Pearl").h(true).f$K().f$O();
      this.BN = com.elowen.values.ValueBuilder.m(this, "Ender Pearl Slot").d(7.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$12).f$K().L();
      this.R = com.elowen.values.ValueBuilder.m(this, "Switch Fireball").h(true).f$K().f$O();
      this.F = com.elowen.values.ValueBuilder.m(this, "Fireball Slot").d(8.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$13).f$K().L();
      int var1 = var10000;
      this.M = com.elowen.values.ValueBuilder.m(this, "Switch Golden Apple").l(this::deobfLambda$new$14).h(true).f$K().f$O();
      this.B3 = com.elowen.values.ValueBuilder.m(this, "Golden Apple Slot").d(9.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$15).f$K().L();
      this.p = com.elowen.values.ValueBuilder.m(this, "Throw Items").h(true).f$K().f$O();
      this.y = com.elowen.values.ValueBuilder.m(this, "Keep Water Buckets").d(1.0F).V(1.0F).w(0.0F).M(5.0F).l(this::deobfLambda$new$16).f$K().L();
      this.Bb = com.elowen.values.ValueBuilder.m(this, "Keep Lava Buckets").d(1.0F).V(1.0F).w(0.0F).M(5.0F).l(this::deobfLambda$new$17).f$K().L();
      this.Bd = com.elowen.values.ValueBuilder.m(this, "Keep Eggs & Snowballs").h(true).f$K().f$O();
      this.Z = com.elowen.values.ValueBuilder.m(this, "Switch Eggs & Snowballs").h(false).l(this::deobfLambda$new$18).f$K().f$O();
      this.h = com.elowen.values.ValueBuilder.m(this, "Eggs & Snowballs Slot").d(9.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$19).f$K().L();
      this.E = com.elowen.values.ValueBuilder.m(this, "Max Eggs & Snowballs Size").d(64.0F).V(16.0F).w(16.0F).M(256.0F).l(this::deobfLambda$new$20).f$K().L();
      this.T = com.elowen.values.ValueBuilder.m(this, "Switch Rod").l(this::deobfLambda$new$21).h(false).f$K().f$O();
      this.x = com.elowen.values.ValueBuilder.m(this, "Rod Slot").d(9.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$22).f$K().L();
      this.f = com.elowen.values.ValueBuilder.m(this, "Keep Wind Charge").h(true).f$K().f$O();
      this.m = com.elowen.values.ValueBuilder.m(this, "Switch Wind Charge").h(false).l(this::deobfLambda$new$23).f$K().f$O();
      this.K = com.elowen.values.ValueBuilder.m(this, "Wind Charge Slot").d(9.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$24).f$K().L();
      this.BO = com.elowen.values.ValueBuilder.m(this, "Max Wind Charge Size").d(64.0F).V(16.0F).w(16.0F).M(256.0F).l(this::deobfLambda$new$25).f$K().L();
      this.Ba = com.elowen.values.ValueBuilder.m(this, "Keep Mace").h(true).f$K().f$O();
      this.BT = com.elowen.values.ValueBuilder.m(this, "Switch Mace").h(false).l(this::deobfLambda$new$26).f$K().f$O();
      this.q = com.elowen.values.ValueBuilder.m(this, "Mace Slot").d(4.0F).V(1.0F).w(1.0F).M(9.0F).l(this::deobfLambda$new$27).f$K().L();
      this.b = com.elowen.values.ValueBuilder.m(this, "Move Arrow").h(false).f$K().f$O();
      this.BW = com.elowen.values.ValueBuilder.m(this, "Move Compass").h(false).f$K().f$O();
      this.D = -1;
      this.By = false;
      this.d = 0;
      this.S = false;
      this.Br = false;
      this.Bf = 0;
      this.Be = -1;
      this.Bs = false;
      this.z = -1;
      this.BD = false;
      if (var1 == 0) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }
   }

   private float s$F() {
      int var2 = (int)this.t.o$F();
      int var10000 = ChestStealer.m();
      int var3 = (int)this.o.o$F();
      int var1 = var10000;
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

   private boolean Z$b() {
      return J.M(this.s$F());
   }

   private boolean r() {
      return J.M(this.s$F());
   }

   private boolean q(Item var1) {
      boolean var5 = false;
      int var10000 = ChestStealer.d$I();
      int var3 = 0;
      int var2 = var10000;

      while (true) {
         if (var3 < 9) {
            ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var3);
            if (var2 != 0) {
               var5 = var4.isEmpty();
               if (var2 == 0) {
                  break;
               }

               if (!var5 && var4.getItem() == var1) {
                  return true;
               }

               var3++;
            }

            if (var2 != 0) {
               continue;
            }
         }

         var5 = false;
         break;
      }

      return var5;
   }

   private ItemStack o$ItemStack() {
      net.minecraft.world.item.ItemStack var5 = null;
      int var10000 = ChestStealer.m();
      Iterator var2 = G.player.getInventory().getNonEquipmentItems().iterator();
      int var1 = var10000;

      while (true) {
         if (var2.hasNext()) {
            ItemStack var3 = (ItemStack)var2.next();
            var5 = var3;
            if (var1 != 0) {
               break;
            }

            label74: {
               if (var1 == 0) {
                  if (var3.isEmpty()) {
                     break label74;
                  }

                  var5 = var3;
               }

               if (var1 != 0) {
                  return var5;
               }

               if (var5.getItem() == Items.MACE) {
                  return var3;
               }
            }

            if (var1 == 0) {
               continue;
            }
         }

         var5 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
         break;
      }

      ItemStack var4 = var5;
      ItemStack var6 = var4;
      if (var1 == 0) {
         if (var4.isEmpty()) {
            return null;
         }

         var6 = var4;
      }

      if (var1 != 0) {
         return var6;
      } else {
         return var6.getItem() == Items.MACE ? var4 : null;
      }
   }

   private int u$I() {
      net.minecraft.world.item.ItemStack var6 = null;
      int var10000 = ChestStealer.m();
      int var2 = 0;
      int var1 = var10000;
      Iterator var3 = G.player.getInventory().getNonEquipmentItems().iterator();

      while (true) {
         if (var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            var6 = var4;
            if (var1 != 0) {
               break;
            }

            label42: {
               if (var1 == 0) {
                  if (var4.isEmpty()) {
                     break label42;
                  }

                  var6 = var4;
               }

               if (var6.getItem() == Items.WIND_CHARGE) {
                  var2 += var4.getCount();
               }
            }

            if (var1 == 0) {
               continue;
            }
         }

         var6 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
         break;
      }

      ItemStack var5 = var6;
      var10000 = ((var5.isEmpty()) ? 1 : 0);
      if (var1 == 0) {
         if (var10000 == 0 && var5.getItem() == Items.WIND_CHARGE) {
            var2 += var5.getCount();
         }

         var10000 = var2;
      }

      return var10000;
   }

   private ItemStack z$ItemStack() {
      net.minecraft.world.item.ItemStack var5 = null;
      int var10000 = ChestStealer.m();
      ItemStack var2 = null;
      int var1 = var10000;
      Iterator var3 = G.player.getInventory().getNonEquipmentItems().iterator();

      while (true) {
         if (var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            var5 = var4;
            if (var1 != 0) {
               break;
            }

            label66: {
               if (var1 == 0) {
                  if (var4.isEmpty()) {
                     break label66;
                  }

                  var5 = var4;
               }

               if (var1 == 0) {
                  if (var5.getItem() != Items.WIND_CHARGE) {
                     break label66;
                  }

                  var5 = var2;
               }

               label52:
               if (var1 == 0) {
                  if (var5 != null) {
                     var5 = var4;
                     if (var1 != 0) {
                        break label52;
                     }

                     if (var4.getCount() <= var2.getCount()) {
                        break label66;
                     }
                  }

                  var5 = var4;
               }

               var2 = var5;
            }

            if (var1 == 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private ItemStack w$Stack() {
      net.minecraft.world.item.ItemStack var5 = null;
      net.minecraft.world.item.ItemStack var7 = null;
      int var10000 = ChestStealer.m();
      ItemStack var2 = null;
      int var1 = var10000;
      Iterator var3 = G.player.getInventory().getNonEquipmentItems().iterator();

      while (true) {
         if (var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            var5 = var4;
            if (var1 != 0) {
               break;
            }

            if (var1 == 0) {
               if (var4.isEmpty()) {
                  continue;
               }

               var5 = var4;
            }

            label86: {
               label87: {
                  Item var6 = var5.getItem();
                  Item var10001 = Items.WIND_CHARGE;
                  if (var1 == 0) {
                     if (var6 == Items.WIND_CHARGE) {
                        continue;
                     }

                     var7 = var4;
                     if (var1 != 0) {
                        break label87;
                     }

                     var6 = var4.getItem();
                     var10001 = Items.EGG;
                  }

                  if (var6 != var10001) {
                     var7 = var4;
                     if (var1 != 0) {
                        break label87;
                     }

                     if (var4.getItem() != Items.SNOWBALL) {
                        break label86;
                     }
                  }

                  var7 = var2;
               }

               label63:
               if (var1 == 0) {
                  if (var7 != null) {
                     var7 = var4;
                     if (var1 != 0) {
                        break label63;
                     }

                     if (var4.getCount() <= var2.getCount()) {
                        break label86;
                     }
                  }

                  var7 = var4;
               }

               var2 = var7;
            }

            if (var1 == 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private ItemStack I() {
      net.minecraft.world.item.ItemStack var5 = null;
      net.minecraft.world.item.ItemStack var7 = null;
      int var10000 = ChestStealer.d$I();
      ItemStack var2 = null;
      int var1 = var10000;
      Iterator var3 = G.player.getInventory().getNonEquipmentItems().iterator();

      while (true) {
         if (var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            var5 = var4;
            if (var1 == 0) {
               break;
            }

            if (var1 != 0) {
               if (var4.isEmpty()) {
                  continue;
               }

               var5 = var4;
            }

            label86: {
               label87: {
                  Item var6 = var5.getItem();
                  Item var10001 = Items.WIND_CHARGE;
                  if (var1 != 0) {
                     if (var6 == Items.WIND_CHARGE) {
                        continue;
                     }

                     var7 = var4;
                     if (var1 == 0) {
                        break label87;
                     }

                     var6 = var4.getItem();
                     var10001 = Items.EGG;
                  }

                  if (var6 != var10001) {
                     var7 = var4;
                     if (var1 == 0) {
                        break label87;
                     }

                     if (var4.getItem() != Items.SNOWBALL) {
                        break label86;
                     }
                  }

                  var7 = var2;
               }

               label63:
               if (var1 != 0) {
                  if (var7 != null) {
                     var7 = var4;
                     if (var1 == 0) {
                        break label63;
                     }

                     if (var4.getCount() >= var2.getCount()) {
                        break label86;
                     }
                  }

                  var7 = var4;
               }

               var2 = var7;
            }

            if (var1 != 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private boolean G(int var1) {
      int var2 = ChestStealer.m();
      int var10000 = ((this.C.w()) ? 1 : 0);
      if (var2 == 0) {
         if (var10000 == 0) {
            return false;
         }

         var10000 = ((this.B.t("Block")) ? 1 : 0);
      }

      if (var2 == 0) {
         if (var10000 != 0) {
            return false;
         }

         var10000 = var1;
      }

      return (boolean)(var2 != 0 ? var10000 : var10000 == (int)(this.BI.o$F() - 1.0F));
   }

   private boolean h(int var1) {
      int var2 = ChestStealer.d$I();
      int var10000 = ((this.Z.w()) ? 1 : 0);
      if (var2 != 0) {
         if (var10000 == 0) {
            return false;
         }

         var10000 = ((this.Bd.w()) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 == 0) {
            return false;
         }

         var10000 = ((this.B.t("Projectile")) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 != 0) {
            return false;
         }

         var10000 = var1;
      }

      return (boolean)(var2 == 0 ? var10000 : var10000 == (int)(this.h.o$F() - 1.0F));
   }

   private boolean R(int var1) {
      int var2 = ChestStealer.m();
      int var10000 = ((this.m.w()) ? 1 : 0);
      if (var2 == 0) {
         if (var10000 == 0) {
            return false;
         }

         var10000 = var1;
      }

      return (boolean)(var2 != 0 ? var10000 : var10000 == (int)(this.K.o$F() - 1.0F));
   }

   private ItemStack M() {
      net.minecraft.world.item.ItemStack var5 = null;
      net.minecraft.world.item.ItemStack var7 = null;
      int var10000 = ChestStealer.m();
      net.minecraft.world.item.ItemStack var2 = null;
      int var1 = var10000;
      int var3 = 0;

      while (true) {
         if (var3 < G.player.getInventory().getNonEquipmentItems().size()) {
            ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var3);
            if (var1 == 0) {
               var5 = var4;
               if (var1 != 0) {
                  break;
               }

               label83:
               if (!var4.isEmpty()) {
                  boolean var6 = var4.getItem() instanceof BlockItem;
                  if (var1 == 0) {
                     if (!var6) {
                        break label83;
                     }

                     var6 = Scaffold.s(var4);
                  }

                  if (var1 == 0) {
                     if (!var6) {
                        break label83;
                     }

                     var6 = com.elowen.utils.InventoryUtils.p(var4);
                  }

                  if (var1 == 0) {
                     if (!var6) {
                        break label83;
                     }

                     var6 = this.G(var3);
                  }

                  label67:
                  if (!var6) {
                     var7 = var2;
                     label65:
                     if (var1 == 0) {
                        if (var2 != null) {
                           var7 = var4;
                           if (var1 != 0) {
                              break label65;
                           }

                           if (var4.getCount() >= var2.getCount()) {
                              break label67;
                           }
                        }

                        var7 = var4;
                     }

                     var2 = var7;
                  }
               }

               var3++;
            }

            if (var1 == 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private ItemStack c$ItemStack() {
      net.minecraft.world.item.ItemStack var5 = null;
      net.minecraft.world.item.ItemStack var7 = null;
      int var10000 = ChestStealer.m();
      net.minecraft.world.item.ItemStack var2 = null;
      int var3 = 0;
      int var1 = var10000;

      while (true) {
         if (var3 < G.player.getInventory().getNonEquipmentItems().size()) {
            ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var3);
            if (var1 == 0) {
               var5 = var4;
               if (var1 != 0) {
                  break;
               }

               label78:
               if (!var4.isEmpty()) {
                  Item var6 = var4.getItem();
                  Item var10001 = Items.WIND_CHARGE;
                  if (var1 == 0) {
                     if (var6 == Items.WIND_CHARGE) {
                        break label78;
                     }

                     var6 = var4.getItem();
                     var10001 = Items.EGG;
                  }

                  label71: {
                     if (var1 == 0) {
                        if (var6 == var10001) {
                           break label71;
                        }

                        var6 = var4.getItem();
                        var10001 = Items.SNOWBALL;
                     }

                     if (var6 != var10001) {
                        break label78;
                     }
                  }

                  label64:
                  if (!this.h(var3)) {
                     var7 = var2;
                     label62:
                     if (var1 == 0) {
                        if (var2 != null) {
                           var7 = var4;
                           if (var1 != 0) {
                              break label62;
                           }

                           if (var4.getCount() >= var2.getCount()) {
                              break label64;
                           }
                        }

                        var7 = var4;
                     }

                     var2 = var7;
                  }
               }

               var3++;
            }

            if (var1 == 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private ItemStack t() {
      net.minecraft.world.item.ItemStack var5 = null;
      net.minecraft.world.item.ItemStack var6 = null;
      net.minecraft.world.item.ItemStack var2 = null;
      int var10000 = ChestStealer.d$I();
      int var3 = 0;
      int var1 = var10000;

      while (true) {
         if (var3 < G.player.getInventory().getNonEquipmentItems().size()) {
            ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var3);
            if (var1 != 0) {
               var5 = var4;
               if (var1 == 0) {
                  break;
               }

               label55:
               if (!var4.isEmpty() && var4.getItem() == Items.WIND_CHARGE && !this.R(var3)) {
                  var6 = var2;
                  label51:
                  if (var1 != 0) {
                     if (var2 != null) {
                        var6 = var4;
                        if (var1 == 0) {
                           break label51;
                        }

                        if (var4.getCount() >= var2.getCount()) {
                           break label55;
                        }
                     }

                     var6 = var4;
                  }

                  var2 = var6;
               }

               var3++;
            }

            if (var1 != 0) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   private int g$I() {
      int var10000 = ChestStealer.m();
      int var2 = 0;
      int var1 = var10000;
      Iterator var3 = G.player.getInventory().getNonEquipmentItems().iterator();

      while (true) {
         if (var3.hasNext()) {
            ItemStack var4 = (ItemStack)var3.next();
            ItemStack var5 = var4;
            if (var1 == 0) {
               var10000 = ((var4.isEmpty()) ? 1 : 0);
               if (var1 != 0) {
                  break;
               }

               if (var10000 != 0) {
                  continue;
               }

               var5 = var4;
            }

            Item var7 = var5.getItem();
            Item var10001 = Items.WIND_CHARGE;
            if (var1 == 0) {
               if (var7 == Items.WIND_CHARGE) {
                  continue;
               }

               var7 = var4.getItem();
               var10001 = Items.EGG;
            }

            label47: {
               label46: {
                  if (var1 == 0) {
                     if (var7 == var10001) {
                        break label46;
                     }

                     var7 = var4.getItem();
                     var10001 = Items.SNOWBALL;
                  }

                  if (var7 != var10001) {
                     break label47;
                  }
               }

               var2 += var4.getCount();
            }

            if (var1 == 0) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public static int b$I() {
      return (int)((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).BQ.o$F();
   }

   public static boolean e() {
      return ((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).Bd.w();
   }

   public static int G$I() {
      return (int)((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).E.o$F();
   }

   public static int C$I() {
      return (int)((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).r.o$F();
   }

   public static int j$I() {
      return (int)((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).y.o$F();
   }

   public static int H() {
      return (int)((InventoryCleaner)Elowen.S$Elowen().q$ModuleManager().A(InventoryCleaner.class)).Bb.o$F();
   }

   public boolean h(ItemStack var1) {
      boolean var10 = false;
      int var2 = ChestStealer.d$I();
      Item var9 = null;
      ItemStack var10000 = var1;
      if (var2 != 0) {
         if (var1.isEmpty()) {
            return false;
         }

         var10000 = var1;
      }

      label393: {
         Item var6 = var10000.getItem();
         Item var10001 = Items.WIND_CHARGE;
         if (var2 != 0) {
            if (var6 == Items.WIND_CHARGE) {
               return true;
            }

            var10000 = var1;
            if (var2 == 0) {
               break label393;
            }

            var6 = var1.getItem();
            var10001 = Items.MACE;
         }

         if (var6 == var10001) {
            return this.Ba.w();
         }

         var10000 = var1;
      }

      boolean var8 = com.elowen.utils.InventoryUtils.C$Z(var10000);
      if (var2 != 0) {
         if (var8) {
            return true;
         }

         var8 = var1.getDisplayName().getString().contains("点击使用");
      }

      if (var2 != 0) {
         if (var8) {
            return true;
         }

         var8 = com.elowen.utils.InventoryUtils.u(var1);
      }

      if (var2 != 0) {
         if (var8) {
            float var3 = com.elowen.utils.InventoryUtils.e$F(var1);
            EquipmentSlot var4 = com.elowen.utils.InventoryUtils.R$EquipmentSlot(var1);
            float var14 = com.elowen.utils.InventoryUtils.p(var4);
            if (var2 != 0) {
               if (var14 >= var3) {
                  return false;
               }

               var14 = com.elowen.utils.InventoryUtils.T(var4);
            }

            float var5 = var14;
            float var18;
            int var15 = (var18 = var3 - var5) == 0.0F ? 0 : (var18 < 0.0F ? -1 : 1);
            if (var2 != 0) {
               var15 = var15 >= 0 ? 1 : 0;
            }

            return (boolean)((var15) != 0);
         }

         var8 = var1.is(ItemTags.SWORDS);
      }

      if (var2 != 0) {
         if (var8) {
            return com.elowen.utils.InventoryUtils.c$ItemStack() == var1;
         }

         var8 = var1.is(ItemTags.PICKAXES);
      }

      if (var2 != 0) {
         if (var8) {
            return com.elowen.utils.InventoryUtils.i() == var1;
         }

         var8 = var1.is(ItemTags.AXES);
      }

      label375:
      if (var2 != 0) {
         if (var8) {
            var8 = com.elowen.utils.InventoryUtils.R$Z(var1);
            if (var2 == 0) {
               break label375;
            }

            if (!var8) {
               return com.elowen.utils.InventoryUtils.g$ItemStack() == var1;
            }
         }

         var8 = var1.is(ItemTags.SHOVELS);
      }

      if (var2 != 0) {
         if (var8) {
            return com.elowen.utils.InventoryUtils.y$ItemStack() == var1;
         }

         var8 = var1.getItem() instanceof CrossbowItem;
      }

      if (var2 != 0) {
         if (var8) {
            return com.elowen.utils.InventoryUtils.n$ItemStack() == var1;
         }

         var8 = var1.getItem() instanceof BowItem;
      }

      label365:
      if (var2 != 0) {
         if (var8) {
            var8 = com.elowen.utils.InventoryUtils.a(var1);
            if (var2 == 0) {
               break label365;
            }

            if (var8) {
               return com.elowen.utils.InventoryUtils.p() == var1;
            }
         }

         var8 = var1.getItem() instanceof BowItem;
      }

      label396: {
         label357:
         if (var2 != 0) {
            if (var8) {
               var8 = com.elowen.utils.InventoryUtils.F(var1);
               if (var2 == 0) {
                  break label357;
               }

               if (var8) {
                  return com.elowen.utils.InventoryUtils.F() == var1;
               }
            }

            var9 = var1.getItem();
            if (var2 == 0) {
               break label396;
            }

            var8 = var9 instanceof BowItem;
         }

         if (var8) {
            var9 = Items.BOW;
            if (var2 == 0) {
               break label396;
            }

            if (com.elowen.utils.InventoryUtils.d(Items.BOW) > 1) {
               return false;
            }
         }

         var9 = var1.getItem();
      }

      label398: {
         label399: {
            label400: {
               Item var16 = Items.WATER_BUCKET;
               if (var2 != 0) {
                  label337: {
                     if (var9 == Items.WATER_BUCKET) {
                        var9 = Items.WATER_BUCKET;
                        if (var2 == 0) {
                           break label337;
                        }

                        if (com.elowen.utils.InventoryUtils.d(Items.WATER_BUCKET) > j$I()) {
                           return false;
                        }
                     }

                     var9 = var1.getItem();
                  }

                  if (var2 == 0) {
                     break label400;
                  }

                  var16 = Items.LAVA_BUCKET;
               }

               if (var9 == var16) {
                  var10 = ((com.elowen.utils.InventoryUtils.d(Items.LAVA_BUCKET)) != 0);
                  if (var2 == 0) {
                     break label399;
                  }

                  if (((var10) ? 1 : 0) > H()) {
                     return false;
                  }
               }

               var9 = var1.getItem();
            }

            if (var2 == 0) {
               break label398;
            }

            var10 = var9 instanceof FishingRodItem;
         }

         if (((var10) ? 1 : 0) != 0) {
            var9 = Items.FISHING_ROD;
            if (var2 == 0) {
               break label398;
            }

            if (com.elowen.utils.InventoryUtils.d(Items.FISHING_ROD) > 1) {
               return false;
            }
         }

         var9 = var1.getItem();
      }

      label312: {
         label404: {
            label310: {
               Item var17 = Items.SNOWBALL;
               if (var2 != 0) {
                  if (var9 == Items.SNOWBALL) {
                     break label310;
                  }

                  var10000 = var1;
                  if (var2 == 0) {
                     break label312;
                  }

                  var9 = var1.getItem();
                  var17 = Items.EGG;
               }

               if (var9 != var17) {
                  break label404;
               }
            }

            boolean var12 = e();
            if (var2 == 0) {
               return var12 ? false : com.elowen.utils.InventoryUtils.U$Z(var1);
            }

            if (!var12) {
               return false;
            }
         }

         var10000 = var1;
      }

      if (var2 == 0) {
         return com.elowen.utils.InventoryUtils.U$Z(var10000);
      } else {
         return com.elowen.utils.InventoryUtils.e$Z(var10000) ? false : com.elowen.utils.InventoryUtils.U$Z(var1);
      }
   }

   @Override
   public void h$V() {
      super.h$V();
      this.D = 0;
      this.By = false;
      this.Be = -1;
      this.Bs = false;
      this.z = -1;
      this.BD = false;
   }

   @Override
   public void q$V() {
      this.l = false;
      this.Br = false;
      this.S = false;
      this.Bf = 0;
      this.Be = -1;
      this.Bs = false;
      this.z = -1;
      this.BD = false;
   }

   @EventTarget
   public void l(EventPacket var1) {
      int var2 = ChestStealer.d$I();
      EventPacket var10000 = var1;
      if (var2 != 0) {
         if (var1.M() != com.elowen.events.api.types.EventType.SEND) {
            return;
         }

         var10000 = var1;
      }

      Packet var3 = var10000.R$Packet();
      boolean var4 = var3 instanceof ServerboundContainerClosePacket;
      if (var2 != 0) {
         if (var4) {
            this.Br = false;
         }

         var4 = this.Br;
      }

      if (var2 != 0) {
         if (!var4) {
            return;
         }

         var4 = this.BP.w();
      }

      if (var2 != 0) {
         if (var4) {
            return;
         }

         var4 = var3 instanceof ServerboundMovePlayerPacket;
      }

      if (var2 != 0) {
         if (var4) {
            if (!com.elowen.utils.MoveUtils.V()) {
               return;
            }

            G.getConnection().send(new ServerboundContainerClosePacket(G.player.inventoryMenu.containerId));
            if (var2 != 0) {
               return;
            }
         }

         var4 = var3 instanceof ServerboundUseItemOnPacket;
      }

      label111: {
         if (var2 != 0) {
            if (var4) {
               break label111;
            }

            var4 = var3 instanceof ServerboundUseItemPacket;
         }

         if (var2 != 0) {
            if (var4) {
               break label111;
            }

            var4 = var3 instanceof ServerboundInteractPacket;
         }

         if (var2 != 0) {
            if (var4) {
               break label111;
            }

            var4 = var3 instanceof ServerboundPlayerActionPacket;
         }

         if (!var4) {
            return;
         }
      }

      G.getConnection().send(new ServerboundContainerClosePacket(G.player.inventoryMenu.containerId));
   }

   @EventTarget
   public void P(EventSprint var1) {
      int var2 = ChestStealer.m();
      if (var2 == 0) {
         if (G.player == null) {
            return;
         }

         this.l$V();
      }

      boolean var10000;
      label53: {
         label57: {
            var10000 = this.V.w();
            if (var2 == 0) {
               if (!var10000) {
                  break label57;
               }

               var10000 = this.BP.w();
            }

            if (var2 != 0) {
               break label53;
            }

            if (!var10000) {
               var10000 = true;
               break label53;
            }
         }

         var10000 = false;
      }

      boolean var3 = var10000;
      var10000 = var3;
      if (var2 == 0) {
         if (!var3) {
            return;
         }

         var10000 = this.l;
      }

      if (var10000) {
         var1.W(-1);
      }
   }

   private void l$V() {
      InventoryCleaner var75 = null;
      ItemStack var77 = null;
      int var82 = 0;
      ItemStack var88 = null;
      ItemStack var89 = null;
      InventoryCleaner var90 = null;
      InventoryCleaner var97 = null;
      InventoryCleaner var98 = null;
      InventoryCleaner var99 = null;
      ItemStack var14 = null;
      int var10000 = ChestStealer.d$I();
      this.l = false;
      int var1 = var10000;
      if (G.player != null) {
         if (com.elowen.utils.InventoryUtils.G$Z()) {
            this.l = false;
         } else {
            Iterator var2 = G.player.getInventory().getNonEquipmentItems().iterator();

            label1174: {
               while (true) {
                  if (var2.hasNext()) {
                     ItemStack var3 = (ItemStack)var2.next();
                     var10000 = ((var3.isEmpty()) ? 1 : 0);
                     if (var1 == 0) {
                        break;
                     }

                     label1164: {
                        if (var1 != 0) {
                           if (var10000 != 0) {
                              break label1164;
                           }

                           var75 = this;
                           if (var1 == 0) {
                              break label1174;
                           }

                           var10000 = ((this.h(var3)) ? 1 : 0);
                        }

                        if (var10000 == 0) {
                           var75 = this;
                           break label1174;
                        }
                     }

                     if (var1 != 0) {
                        continue;
                     }
                  }

                  var10000 = ((this.p.w()) ? 1 : 0);
                  break;
               }

               label1150:
               if (var1 != 0) {
                  if (var10000 != 0) {
                     float var102;
                     var10000 = (var102 = com.elowen.utils.InventoryUtils.M() - this.BQ.o$F()) == 0.0F ? 0 : (var102 < 0.0F ? -1 : 1);
                     if (var1 != 0) {
                        if (var10000 > 0) {
                           this.l = true;
                           return;
                        }

                        float var103;
                        var10000 = (var103 = com.elowen.utils.InventoryUtils.d(Items.ARROW) - this.r.o$F()) == 0.0F ? 0 : (var103 < 0.0F ? -1 : 1);
                     }

                     if (var1 != 0) {
                        if (var10000 > 0) {
                           this.l = true;
                           return;
                        }

                        float var104;
                        var10000 = (var104 = com.elowen.utils.InventoryUtils.d(Items.WATER_BUCKET) - this.y.o$F()) == 0.0F ? 0 : (var104 < 0.0F ? -1 : 1);
                     }

                     if (var1 != 0) {
                        if (var10000 > 0) {
                           this.l = true;
                           return;
                        }

                        float var105;
                        var10000 = (var105 = com.elowen.utils.InventoryUtils.d(Items.LAVA_BUCKET) - this.Bb.o$F()) == 0.0F ? 0 : (var105 < 0.0F ? -1 : 1);
                     }

                     if (var1 != 0) {
                        if (var10000 > 0) {
                           this.l = true;
                           return;
                        }

                        var10000 = ((this.Bd.w()) ? 1 : 0);
                     }

                     label1139:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           float var106;
                           var10000 = (var106 = this.g$I() - this.E.o$F()) == 0.0F ? 0 : (var106 < 0.0F ? -1 : 1);
                           if (var1 == 0) {
                              break label1139;
                           }

                           if (var10000 > 0) {
                              this.l = true;
                              return;
                           }
                        }

                        var10000 = ((this.f.w()) ? 1 : 0);
                     }

                     label1131:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           float var107;
                           var10000 = (var107 = this.u$I() - this.BO.o$F()) == 0.0F ? 0 : (var107 < 0.0F ? -1 : 1);
                           if (var1 == 0) {
                              break label1131;
                           }

                           if (var10000 > 0) {
                              this.l = true;
                              return;
                           }
                        }

                        var10000 = com.elowen.utils.InventoryUtils.d(Items.FISHING_ROD);
                     }

                     byte var10001 = 1;
                     if (var1 != 0) {
                        if (var10000 > 1) {
                           this.l = true;
                           return;
                        }

                        var10000 = com.elowen.utils.InventoryUtils.d(Items.BOW);
                        if (var1 == 0) {
                           break label1150;
                        }

                        var10001 = 1;
                     }

                     if (var10000 > var10001) {
                        this.l = true;
                        return;
                     }
                  }

                  var10000 = ((this.I.w()) ? 1 : 0);
               }

               label1261: {
                  label1262: {
                     label1182: {
                        label1114:
                        if (var1 != 0) {
                           if (var10000 != 0) {
                              int var10 = 0;

                              while (var10 < 4) {
                                 ItemStack var29 = G.player.getItemBySlot(BG[var10]);
                                 if (var1 != 0) {
                                    var10000 = ((com.elowen.utils.InventoryUtils.u(var29)) ? 1 : 0);
                                    if (var1 == 0) {
                                       break label1182;
                                    }

                                    label1102:
                                    if (var10000 != 0) {
                                       byte var76 = (((byte)((var29.isEmpty()) ? 1 : 0)));
                                       if (var1 != 0) {
                                          if (var76 != 0) {
                                             break label1102;
                                          }

                                          float var108;
                                          var76 = (byte)((var108 = com.elowen.utils.InventoryUtils.T(BG[var10]) - com.elowen.utils.InventoryUtils.e$F(var29)) == 0.0F
                                             ? 0
                                             : (var108 < 0.0F ? -1 : 1));
                                       }

                                       if (var76 > 0) {
                                          this.l = true;
                                          return;
                                       }
                                    }

                                    var10++;
                                 }

                                 if (var1 == 0) {
                                    break;
                                 }
                              }

                              for (ItemStack var30 : G.player.getInventory().getNonEquipmentItems()) {
                                 var10000 = ((var30.isEmpty()) ? 1 : 0);
                                 if (var1 == 0) {
                                    break label1114;
                                 }

                                 label1188: {
                                    label1189: {
                                       if (var1 != 0) {
                                          if (var10000 != 0) {
                                             break label1188;
                                          }

                                          var77 = var30;
                                          if (var1 == 0) {
                                             break label1189;
                                          }

                                          var10000 = ((com.elowen.utils.InventoryUtils.u(var30)) ? 1 : 0);
                                       }

                                       if (var10000 == 0) {
                                          break label1188;
                                       }

                                       var77 = var30;
                                    }

                                    EquipmentSlot var4 = com.elowen.utils.InventoryUtils.R$EquipmentSlot(var77);
                                    float var5 = com.elowen.utils.InventoryUtils.e$F(var30);
                                    float var109;
                                    var10000 = (var109 = com.elowen.utils.InventoryUtils.T(var4) - var5) == 0.0F ? 0 : (var109 < 0.0F ? -1 : 1);
                                    if (var1 != 0) {
                                       if (var10000 != 0) {
                                          break label1188;
                                       }

                                       float var110;
                                       var10000 = (var110 = com.elowen.utils.InventoryUtils.p(var4) - var5) == 0.0F ? 0 : (var110 < 0.0F ? -1 : 1);
                                    }

                                    if (var10000 < 0) {
                                       this.l = true;
                                       return;
                                    }
                                 }

                                 if (var1 == 0) {
                                    break;
                                 }
                              }
                           }

                           String[] var9 = bb;
                           var10000 = ((this.B.t("Golden Apple")) ? 1 : 0);
                        }

                        label1064: {
                           label1191: {
                              if (var1 != 0) {
                                 if (var10000 != 0) {
                                    ItemStack var15 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                    ItemStack var33 = com.elowen.utils.InventoryUtils.p(Items.GOLDEN_APPLE);
                                    ItemStack var84 = var33;
                                    if (var1 != 0) {
                                       if (var33 == null) {
                                          break label1191;
                                       }

                                       var84 = var15;
                                    }

                                    if (var1 != 0) {
                                       if (var84.getItem() != Items.GOLDEN_APPLE) {
                                          this.l = true;
                                          return;
                                       }

                                       var84 = var15;
                                    }

                                    var10000 = var84.getCount() + var33.getCount();
                                    int var101 = 64;
                                    if (var1 != 0) {
                                       if (var10000 <= 64) {
                                          this.l = true;
                                          return;
                                       }

                                       var10000 = var15.getCount();
                                       var101 = var33.getCount();
                                    }

                                    if (var10000 < var101) {
                                       this.l = true;
                                       return;
                                    }
                                    break label1191;
                                 }

                                 String[] var70 = bb;
                                 var10000 = ((this.B.t("Projectile")) ? 1 : 0);
                              }

                              if (var1 != 0) {
                                 if (var10000 != 0) {
                                    label1030: {
                                       label1029: {
                                          var14 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                          Item var81 = var14.getItem();
                                          Item var100 = Items.EGG;
                                          if (var1 != 0) {
                                             if (var81 == Items.EGG) {
                                                break label1029;
                                             }

                                             var81 = var14.getItem();
                                             var100 = Items.SNOWBALL;
                                          }

                                          if (var81 != var100) {
                                             var82 = 0;
                                             break label1030;
                                          }
                                       }

                                       var82 = 1;
                                    }

                                    byte var32 = ((byte)(var82));
                                    ItemStack var47 = this.w$Stack();
                                    if (var47 != null) {
                                       var10000 = var32;
                                       if (var1 != 0) {
                                          if (var32 == 0) {
                                             break label1261;
                                          }

                                          var10000 = var14.getCount();
                                       }

                                       if (var10000 < var47.getCount()) {
                                          break label1261;
                                       }
                                    }
                                    break label1191;
                                 }

                                 String[] var71 = bb;
                                 var10000 = ((this.B.t("Fishing Rod")) ? 1 : 0);
                              }

                              if (var1 != 0) {
                                 if (var10000 != 0) {
                                    ItemStack var13 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                    Item var80 = var13.getItem();
                                    if (var1 != 0) {
                                       if (var80 == Items.FISHING_ROD) {
                                          break label1191;
                                       }

                                       var80 = Items.FISHING_ROD;
                                    }

                                    if (com.elowen.utils.InventoryUtils.X(var80) != -1) {
                                       this.l = true;
                                       return;
                                    }
                                    break label1191;
                                 }

                                 String[] var72 = bb;
                                 var10000 = ((this.B.t("Block")) ? 1 : 0);
                              }

                              if (var1 == 0) {
                                 break label1064;
                              }

                              label1037:
                              if (var10000 != 0) {
                                 ItemStack var12 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                 ItemStack var31 = com.elowen.utils.InventoryUtils.b$ItemStack();
                                 ItemStack var79 = var31;
                                 if (var1 != 0) {
                                    if (var31 == null) {
                                       break label1037;
                                    }

                                    var79 = var12;
                                 }

                                 var10000 = ((Scaffold.s(var79)) ? 1 : 0);
                                 if (var1 != 0) {
                                    if (var10000 == 0) {
                                       break label1262;
                                    }

                                    var10000 = var12.getCount();
                                 }

                                 if (var1 == 0) {
                                    break label1064;
                                 }

                                 if (var10000 < var31.getCount()) {
                                    break label1262;
                                 }
                              }
                           }

                           var10000 = ((this.M.w()) ? 1 : 0);
                        }

                        label993:
                        if (var1 != 0) {
                           label1235: {
                              if (var10000 != 0) {
                                 var10000 = ((this.B.t("Golden Apple")) ? 1 : 0);
                                 if (var1 == 0) {
                                    break label993;
                                 }

                                 if (var10000 == 0) {
                                    int var16 = (int)(this.B3.o$F() - 1.0F);
                                    ItemStack var34 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var16);
                                    int var48 = com.elowen.utils.InventoryUtils.X(Items.GOLDEN_APPLE);
                                    var10000 = var48;
                                    if (var1 == 0) {
                                       break label993;
                                    }

                                    label1007:
                                    if (var48 != -1) {
                                       ItemStack var62 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var48);
                                       if (var1 == 0) {
                                          return;
                                       }

                                       if (var34.getItem() != Items.GOLDEN_APPLE) {
                                          break label1235;
                                       }

                                       ItemStack var86 = var34;
                                       if (var1 != 0) {
                                          if (var34.getItem() != Items.GOLDEN_APPLE) {
                                             break label1007;
                                          }

                                          var86 = var34;
                                       }

                                       var10000 = var86.getCount();
                                       if (var1 == 0) {
                                          break label993;
                                       }

                                       if (var10000 < var62.getCount()) {
                                          break label1235;
                                       }
                                    }
                                 }
                              }

                              var10000 = ((this.C.w()) ? 1 : 0);
                              break label993;
                           }

                           this.l = true;
                           return;
                        }
                     }

                     label972:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           var10000 = ((this.B.t("Block")) ? 1 : 0);
                           if (var1 == 0) {
                              break label972;
                           }

                           if (var10000 == 0) {
                              int var17 = (int)(this.BI.o$F() - 1.0F);
                              ItemStack var35 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var17);
                              var10000 = ((Scaffold.s(var35)) ? 1 : 0);
                              if (var1 == 0) {
                                 break label972;
                              }

                              label966:
                              if (var10000 == 0) {
                                 ItemStack var49 = com.elowen.utils.InventoryUtils.b$ItemStack();
                                 ItemStack var87 = var49;
                                 if (var1 != 0) {
                                    if (var49 == null) {
                                       break label966;
                                    }

                                    var87 = var49;
                                 }

                                 if (var87 != var35) {
                                    this.l = true;
                                    return;
                                 }
                              }
                           }
                        }

                        var10000 = ((this.v.w()) ? 1 : 0);
                     }

                     if (var1 != 0) {
                        label943:
                        if (var10000 != 0) {
                           ItemStack var36;
                           ItemStack var50;
                           int var18 = (int)(this.Bq.o$F() - 1.0F);
                           var36 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var18);
                           var50 = com.elowen.utils.InventoryUtils.c$ItemStack();
                           ItemStack var63 = com.elowen.utils.InventoryUtils.j$ItemStack();
                           var88 = var63;
                           label952:
                           if (var1 != 0) {
                              if (var63 != null) {
                                 var88 = var63;
                                 if (var1 == 0) {
                                    break label952;
                                 }

                                 if (var63.getItem() != Items.MACE) {
                                    var88 = var63;
                                    if (var1 == 0) {
                                       break label952;
                                    }

                                    if (com.elowen.utils.InventoryUtils.q(var63) > com.elowen.utils.InventoryUtils.f(var50)) {
                                       var50 = var63;
                                    }
                                 }
                              }

                              var88 = var50;
                           }

                           if (var1 != 0) {
                              if (var88 == null) {
                                 break label943;
                              }

                              var88 = var50;
                           }

                           if (var88 != var36) {
                              this.l = true;
                              return;
                           }
                        }

                        var10000 = ((this.c.w()) ? 1 : 0);
                     }

                     label936:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           var10000 = (int)(this.j.o$F() - 1.0F);
                           if (var1 == 0) {
                              break label936;
                           }

                           int var19 = var10000;
                           ItemStack var37 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var19);
                           ItemStack var51 = com.elowen.utils.InventoryUtils.i();
                           if (var51 != null) {
                              var10000 = ((var51.is(ItemTags.PICKAXES)) ? 1 : 0);
                              if (var1 == 0) {
                                 break label936;
                              }

                              if (var10000 != 0 && var51 != var37) {
                                 this.l = true;
                                 return;
                              }
                           }
                        }

                        var10000 = ((this.Y.w()) ? 1 : 0);
                     }

                     label926:
                     if (var1 != 0) {
                        label924:
                        if (var10000 != 0) {
                           ItemStack var38;
                           ItemStack var52;
                           int var20 = (int)(this.BA.o$F() - 1.0F);
                           var38 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var20);
                           var52 = com.elowen.utils.InventoryUtils.g$ItemStack();
                           var89 = var52;
                           label921:
                           if (var1 != 0) {
                              if (var52 != null) {
                                 var89 = var52;
                                 if (var1 == 0) {
                                    break label921;
                                 }

                                 if (var52.getItem() == Items.MACE) {
                                    var52 = null;
                                 }
                              }

                              var89 = var52;
                           }

                           if (var1 != 0) {
                              if (var89 == null) {
                                 break label924;
                              }

                              var89 = var52;
                           }

                           var10000 = ((var89.is(ItemTags.AXES)) ? 1 : 0);
                           if (var1 == 0) {
                              break label926;
                           }

                           if (var10000 != 0 && var52 != var38) {
                              this.l = true;
                              return;
                           }
                        }

                        var10000 = ((this.BT.w()) ? 1 : 0);
                     }

                     label906:
                     if (var1 != 0) {
                        label904: {
                           if (var10000 != 0) {
                              int var21 = (int)(this.q.o$F() - 1.0F);
                              ItemStack var39 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var21);
                              var90 = this;
                              if (var1 == 0) {
                                 break label904;
                              }

                              ItemStack var53 = this.o$ItemStack();
                              if (var53 != null) {
                                 var10000 = ((ItemStack.isSameItemSameComponents(var39, var53)) ? 1 : 0);
                                 if (var1 == 0) {
                                    break label906;
                                 }

                                 if (var10000 == 0) {
                                    this.l = true;
                                    return;
                                 }
                              }
                           }

                           var90 = this;
                        }

                        var10000 = ((var90.T.w()) ? 1 : 0);
                     }

                     label892:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           var10000 = ((this.B.t("Fishing Rod")) ? 1 : 0);
                           if (var1 == 0) {
                              break label892;
                           }

                           if (var10000 == 0) {
                              int var22 = (int)(this.x.o$F() - 1.0F);
                              ItemStack var40 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var22);
                              var10000 = ((var40.getItem() instanceof FishingRodItem) ? 1 : 0);
                              if (var1 == 0) {
                                 break label892;
                              }

                              if (var10000 == 0) {
                                 ItemStack var54 = com.elowen.utils.InventoryUtils.A$ItemStack();
                                 if (var1 == 0) {
                                    return;
                                 }

                                 if (var54 != null) {
                                    this.l = true;
                                    return;
                                 }
                              }
                           }
                        }

                        var10000 = ((this.B6.w()) ? 1 : 0);
                     }

                     label876:
                     if (var1 != 0) {
                        label874:
                        if (var10000 != 0) {
                           ItemStack var41;
                           ItemStack var56;
                           float var65;
                           float var69;
                           label872: {
                              int var23 = (int)(this.X.o$F() - 1.0F);
                              var41 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var23);
                              Object var55 = null;
                              var65 = 0.0F;
                              var69 = 0.0F;
                              boolean var91 = this.Q.t("Crossbow");
                              if (var1 != 0) {
                                 if (var91) {
                                    var56 = com.elowen.utils.InventoryUtils.n$ItemStack();
                                    var65 = com.elowen.utils.InventoryUtils.S(var56);
                                    var69 = com.elowen.utils.InventoryUtils.S(var41);
                                    break label872;
                                 }

                                 String[] var73 = bb;
                                 var91 = this.Q.t("Power Bow");
                              }

                              if (var91) {
                                 var56 = com.elowen.utils.InventoryUtils.F();
                                 var65 = com.elowen.utils.InventoryUtils.U$F(var56);
                                 var69 = com.elowen.utils.InventoryUtils.U$F(var41);
                              } else {
                                 var56 = com.elowen.utils.InventoryUtils.p();
                                 var65 = com.elowen.utils.InventoryUtils.z(var56);
                                 var69 = com.elowen.utils.InventoryUtils.z(var41);
                              }
                           }

                           ItemStack var92 = var56;
                           if (var1 != 0) {
                              if (var56 == null) {
                                 var56 = com.elowen.utils.InventoryUtils.n$ItemStack();
                                 var65 = com.elowen.utils.InventoryUtils.S(var56);
                                 var69 = com.elowen.utils.InventoryUtils.S(var41);
                              }

                              var92 = var56;
                           }

                           if (var1 != 0) {
                              if (var92 == null) {
                                 var56 = com.elowen.utils.InventoryUtils.F();
                                 var65 = com.elowen.utils.InventoryUtils.U$F(var56);
                                 var69 = com.elowen.utils.InventoryUtils.U$F(var41);
                              }

                              var92 = var56;
                           }

                           if (var1 != 0) {
                              if (var92 == null) {
                                 var56 = com.elowen.utils.InventoryUtils.p();
                                 var65 = com.elowen.utils.InventoryUtils.z(var56);
                                 var69 = com.elowen.utils.InventoryUtils.z(var41);
                              }

                              var92 = var56;
                           }

                           if (var1 != 0) {
                              if (var92 == null) {
                                 break label874;
                              }

                              var92 = var41;
                           }

                           var10000 = ((var92.isEmpty()) ? 1 : 0);
                           if (var1 != 0) {
                              if (var10000 != 0) {
                                 this.l = true;
                                 return;
                              }

                              float var111;
                              var10000 = (var111 = var65 - var69) == 0.0F ? 0 : (var111 < 0.0F ? -1 : 1);
                           }

                           if (var1 != 0) {
                              if (var10000 > 0) {
                                 this.l = true;
                                 return;
                              }

                              float var112;
                              var10000 = (var112 = var65 - var69) == 0.0F ? 0 : (var112 < 0.0F ? -1 : 1);
                           }

                           if (var1 == 0) {
                              break label876;
                           }

                           if (var10000 == 0) {
                              int var7 = this.g$I(var41);
                              int var8 = this.g$I(var56);
                              var10000 = var8;
                              if (var1 == 0) {
                                 break label876;
                              }

                              if (var8 < var7) {
                                 this.l = true;
                                 return;
                              }
                           }
                        }

                        var10000 = ((this.e.w()) ? 1 : 0);
                     }

                     label827:
                     if (var1 != 0) {
                        label1240: {
                           if (var10000 != 0) {
                              int var24 = (int)(this.BN.o$F() - 1.0F);
                              ItemStack var42 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var24);
                              int var57 = com.elowen.utils.InventoryUtils.X(Items.ENDER_PEARL);
                              var10000 = var57;
                              if (var1 == 0) {
                                 break label827;
                              }

                              label840:
                              if (var57 != -1) {
                                 ItemStack var66 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var57);
                                 if (var1 == 0) {
                                    return;
                                 }

                                 if (var42.getItem() != Items.ENDER_PEARL) {
                                    break label1240;
                                 }

                                 ItemStack var93 = var42;
                                 if (var1 != 0) {
                                    if (var42.getItem() != Items.ENDER_PEARL) {
                                       break label840;
                                    }

                                    var93 = var42;
                                 }

                                 var10000 = var93.getCount();
                                 if (var1 == 0) {
                                    break label827;
                                 }

                                 if (var10000 < var66.getCount()) {
                                    break label1240;
                                 }
                              }
                           }

                           var10000 = ((this.U.w()) ? 1 : 0);
                           break label827;
                        }

                        this.l = true;
                        return;
                     }

                     label798:
                     if (var1 != 0) {
                        label1242: {
                           if (var10000 != 0) {
                              int var25 = (int)(this.Bj.o$F() - 1.0F);
                              ItemStack var43 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var25);
                              int var58 = com.elowen.utils.InventoryUtils.X(Items.WATER_BUCKET);
                              var10000 = var58;
                              if (var1 == 0) {
                                 break label798;
                              }

                              label811:
                              if (var58 != -1) {
                                 ItemStack var67 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var58);
                                 if (var1 == 0) {
                                    return;
                                 }

                                 if (var43.getItem() != Items.WATER_BUCKET) {
                                    break label1242;
                                 }

                                 ItemStack var94 = var43;
                                 if (var1 != 0) {
                                    if (var43.getItem() != Items.WATER_BUCKET) {
                                       break label811;
                                    }

                                    var94 = var43;
                                 }

                                 var10000 = var94.getCount();
                                 if (var1 == 0) {
                                    break label798;
                                 }

                                 if (var10000 < var67.getCount()) {
                                    break label1242;
                                 }
                              }
                           }

                           var10000 = ((this.R.w()) ? 1 : 0);
                           break label798;
                        }

                        this.l = true;
                        return;
                     }

                     label769:
                     if (var1 != 0) {
                        label1244: {
                           if (var10000 != 0) {
                              int var26 = (int)(this.F.o$F() - 1.0F);
                              ItemStack var44 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var26);
                              int var59 = com.elowen.utils.InventoryUtils.X(Items.FIRE_CHARGE);
                              var10000 = var59;
                              if (var1 == 0) {
                                 break label769;
                              }

                              label782:
                              if (var59 != -1) {
                                 ItemStack var68 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var59);
                                 if (var1 == 0) {
                                    return;
                                 }

                                 if (var44.getItem() != Items.FIRE_CHARGE) {
                                    break label1244;
                                 }

                                 ItemStack var95 = var44;
                                 if (var1 != 0) {
                                    if (var44.getItem() != Items.FIRE_CHARGE) {
                                       break label782;
                                    }

                                    var95 = var44;
                                 }

                                 var10000 = var95.getCount();
                                 if (var1 == 0) {
                                    break label769;
                                 }

                                 if (var10000 < var68.getCount()) {
                                    break label1244;
                                 }
                              }
                           }

                           var10000 = ((this.Z.w()) ? 1 : 0);
                           break label769;
                        }

                        this.l = true;
                        return;
                     }

                     label761:
                     if (var1 != 0) {
                        label759: {
                           if (var10000 != 0) {
                              var10000 = ((this.B.t("Projectile")) ? 1 : 0);
                              if (var1 == 0) {
                                 break label761;
                              }

                              if (var10000 == 0) {
                                 var10000 = (int)(this.h.o$F() - 1.0F);
                                 if (var1 == 0) {
                                    break label761;
                                 }

                                 int var27 = var10000;
                                 ItemStack var45 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var27);
                                 label754:
                                 if (var45.getItem() != Items.EGG) {
                                    ItemStack var96 = var45;
                                    if (var1 != 0) {
                                       if (var45.getItem() == Items.SNOWBALL) {
                                          break label754;
                                       }

                                       var97 = this;
                                       if (var1 == 0) {
                                          break label759;
                                       }

                                       var96 = this.w$Stack();
                                    }

                                    ItemStack var60 = var96;
                                    if (var60 != null && var60 != var45) {
                                       this.l = true;
                                       return;
                                    }
                                 }
                              }
                           }

                           var97 = this;
                        }

                        var10000 = ((var97.m.w()) ? 1 : 0);
                     }

                     label741:
                     if (var1 != 0) {
                        label739: {
                           if (var10000 != 0) {
                              var10000 = (int)(this.K.o$F() - 1.0F);
                              if (var1 == 0) {
                                 break label741;
                              }

                              int var28 = var10000;
                              ItemStack var46 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var28);
                              if (var46.getItem() != Items.WIND_CHARGE) {
                                 var98 = this;
                                 if (var1 == 0) {
                                    break label739;
                                 }

                                 ItemStack var61 = this.z$ItemStack();
                                 if (var61 != null) {
                                    var10000 = ((ItemStack.isSameItemSameComponents(var46, var61)) ? 1 : 0);
                                    if (var1 == 0) {
                                       break label741;
                                    }

                                    if (var10000 == 0) {
                                       this.l = true;
                                       return;
                                    }
                                 }
                              }
                           }

                           var98 = this;
                        }

                        var10000 = ((var98.b.w()) ? 1 : 0);
                     }

                     label726:
                     if (var1 != 0) {
                        if (var10000 != 0) {
                           var10000 = ((this.q(Items.ARROW)) ? 1 : 0);
                           if (var1 == 0) {
                              break label726;
                           }

                           if (var10000 != 0) {
                              this.l = true;
                              return;
                           }
                        }

                        var10000 = ((this.BW.w()) ? 1 : 0);
                     }

                     label1267: {
                        if (var1 != 0) {
                           if (var10000 == 0) {
                              return;
                           }

                           var99 = this;
                           if (var1 == 0) {
                              break label1267;
                           }

                           var10000 = ((this.q(Items.COMPASS)) ? 1 : 0);
                        }

                        if (var10000 == 0) {
                           return;
                        }

                        var99 = this;
                     }

                     var99.l = true;
                     return;
                  }

                  this.l = true;
                  return;
               }

               this.l = true;
               return;
            }

            var75.l = true;
         }
      }
   }

   @EventTarget
   public void i(com.elowen.events.impl.EventMotion var1) {
      int var10000 = 0;
      boolean var83 = false;
      boolean var87 = false;
      boolean var88 = false;
      int var89 = 0;
      int var99 = 0;
      int var100 = 0;
      int var24 = 0;
      net.minecraft.world.item.ItemStack var111 = null;
      int var29 = 0;
      net.minecraft.world.item.ItemStack var123 = null;
      ItemStack var20 = null;
      float var112 = 0.0F;
      float var114 = 0.0F;
      Object var116 = null;
      label2012: {
         InventoryCleaner var131;
         label1877: {
            label1878: {
               int var2 = ChestStealer.m();
               if (var1.Q() == com.elowen.events.api.types.EventType.PRE) {
                  byte var3;
                  var3 = (((byte)((G.gui.screen() instanceof InventoryScreen) ? 1 : 0)));
                  var10000 = var3;
                  label1868:
                  if (var2 == 0) {
                     if (var3 != 0) {
                        var10000 = ((this.By) ? 1 : 0);
                        if (var2 != 0) {
                           break label1868;
                        }

                        if (!this.By) {
                           this.D = 0;
                        }
                     }

                     var10000 = var3;
                  }

                  label1860:
                  if (var2 == 0) {
                     if (var10000 == 0) {
                        var10000 = ((this.By) ? 1 : 0);
                        if (var2 != 0) {
                           break label1860;
                        }

                        if (this.By) {
                           this.D = -1;
                        }
                     }

                     this.By = (boolean)((var3) != 0);
                     var10000 = ((this.BP.w()) ? 1 : 0);
                  }

                  label1853: {
                     label1852: {
                        if (var2 == 0) {
                           if (var10000 == 0) {
                              var131 = this;
                              if (var2 != 0) {
                                 break label1853;
                              }

                              if (!this.V.w()) {
                                 break label1852;
                              }
                           }

                           var131 = this;
                           if (var2 != 0) {
                              break label1853;
                           }

                           var10000 = this.D;
                        }

                        if (var10000 != -1) {
                           var131 = this;
                           if (var2 != 0) {
                              break label1853;
                           }

                           if (this.D < this.i.o$F()) {
                              this.D++;
                              return;
                           }
                        }
                     }

                     var131 = this;
                  }

                  label1837: {
                     if (var2 == 0) {
                        var131.l$V();
                        label1821:
                        if (G.player != null) {
                           var83 = G.player.isUsingItem();
                           if (var2 == 0) {
                              if (((var83) ? 1 : 0) != 0) {
                                 this.z = -1;
                                 this.BD = true;
                                 return;
                              }

                              var83 = this.BD;
                           }

                           label1832: {
                              label1884: {
                                 if (var2 == 0) {
                                    if (((var83) ? 1 : 0) != 0) {
                                       this.z = 0;
                                       if (var2 == 0) {
                                          break label1884;
                                       }
                                    }

                                    var83 = ((this.z) != 0);
                                 }

                                 if (var2 != 0) {
                                    break label1832;
                                 }

                                 if (((var83) ? 1 : 0) >= 0) {
                                    this.z++;
                                 }
                              }

                              this.BD = false;
                              var83 = this.BP.w();
                           }

                           if (var2 == 0) {
                              if (((var83) ? 1 : 0) == 0) {
                                 var83 = this.V.w();
                                 if (var2 != 0) {
                                    break label1837;
                                 }

                                 if (((var83) ? 1 : 0) == 0) {
                                    break label1821;
                                 }
                              }

                              var83 = ((this.z) != 0);
                           }

                           if (var2 != 0) {
                              break label1837;
                           }

                           if (((var83) ? 1 : 0) >= 0) {
                              float var135;
                              var83 = (((var135 = this.z - this.i.o$F()) == 0.0F ? 0 : (var135 < 0.0F ? -1 : 1)) != 0);
                              if (var2 != 0) {
                                 break label1837;
                              }

                              if (((var83) ? 1 : 0) < 0) {
                                 return;
                              }
                           }
                        }

                        var131 = this;
                     }

                     var83 = var131.V.w();
                  }

                  label1805: {
                     label1885: {
                        if (var2 == 0) {
                           if (((var83) ? 1 : 0) == 0) {
                              break label1885;
                           }

                           var83 = this.BP.w();
                        }

                        if (var2 != 0) {
                           break label1805;
                        }

                        if (((var83) ? 1 : 0) == 0) {
                           var83 = ((1) != 0);
                           break label1805;
                        }
                     }

                     var83 = ((0) != 0);
                  }

                  int var4 = ((var83) ? 1 : 0);
                  int var84 = var4;
                  if (var2 == 0) {
                     if (var4 != 0) {
                        byte var5;
                        label1790: {
                           label1887: {
                              var5 = (((byte)((G.player.isSprinting()) ? 1 : 0)));
                              int var85 = var5;
                              if (var2 == 0) {
                                 if (var5 != 0) {
                                    this.Be = -1;
                                    if (var2 == 0) {
                                       break label1887;
                                    }
                                 }

                                 var85 = ((this.Bs) ? 1 : 0);
                              }

                              if (var2 == 0) {
                                 if (var85 != 0) {
                                    this.Be = 0;
                                    if (var2 == 0) {
                                       break label1887;
                                    }
                                 }

                                 var131 = this;
                                 if (var2 != 0) {
                                    break label1790;
                                 }

                                 var85 = this.Be;
                              }

                              if (var85 >= 0) {
                                 this.Be++;
                              }
                           }

                           var131 = this;
                        }

                        var131.Bs = (boolean)((var5) != 0);
                     }

                     var84 = var4;
                  }

                  label1760:
                  if (var2 == 0) {
                     label1769:
                     if (var84 == 0) {
                        var84 = ((ChestStealer.a$Z()) ? 1 : 0);
                        if (var2 == 0) {
                           if (var84 != 0) {
                              break label2012;
                           }

                           var84 = ((Elowen.S$Elowen().q$ModuleManager().A(Scaffold.class).w()) ? 1 : 0);
                        }

                        if (var2 == 0) {
                           if (var84 != 0) {
                              break label2012;
                           }

                           var84 = ((this.BP.w()) ? 1 : 0);
                        }

                        if (var2 == 0) {
                           if (var84 != 0) {
                              var84 = ((G.gui.screen() instanceof InventoryScreen) ? 1 : 0);
                              if (var2 != 0) {
                                 break label1760;
                              }

                              if (var84 == 0) {
                                 break label2012;
                              }
                              break label1769;
                           }

                           var84 = this.d;
                        }

                        if (var2 != 0) {
                           break label1760;
                        }

                        if (var84 <= 1) {
                           break label2012;
                        }
                     }

                     var84 = var4;
                  }

                  label1752:
                  if (var2 == 0) {
                     if (var84 != 0) {
                        var84 = ((G.player.isSprinting()) ? 1 : 0);
                        if (var2 == 0) {
                           if (var84 != 0) {
                              this.S = false;
                              return;
                           }

                           var84 = this.Be;
                        }

                        if (var2 != 0) {
                           break label1752;
                        }

                        if (var84 >= 0) {
                           float var136;
                           var84 = (var136 = this.Be - this.i.o$F()) == 0.0F ? 0 : (var136 < 0.0F ? -1 : 1);
                           if (var2 != 0) {
                              break label1752;
                           }

                           if (var84 < 0) {
                              this.S = false;
                              return;
                           }
                        }
                     }

                     var84 = ((G.gui.screen() instanceof com.elowen.ui.ClickGUI) ? 1 : 0);
                  }

                  label1740:
                  if (var2 == 0) {
                     if (var84 == 0) {
                        var84 = ((this.W$Z()) ? 1 : 0);
                        if (var2 != 0) {
                           break label1740;
                        }

                        if (var84 == 0) {
                           String[] var13 = bb;
                           Notification var14 = new Notification(com.elowen.ui.notification.NotificationLevel.ERROR, "Duplicate slot config in Inventory Manager! Please check your config!", 8000L);
                           Elowen.S$Elowen().Q().m(var14);
                           this.R$V();
                           return;
                        }
                     }

                     var84 = ((com.elowen.utils.InventoryUtils.G$Z()) ? 1 : 0);
                  }

                  if (var2 == 0) {
                     if (var84 != 0) {
                        return;
                     }

                     var84 = ((com.elowen.utils.MoveUtils.V()) ? 1 : 0);
                  }

                  label1732: {
                     if (var84 != 0) {
                        this.d = 0;
                        if (var2 == 0) {
                           break label1732;
                        }
                     }

                     this.d++;
                  }

                  Screen var6 = G.gui.screen();
                  var87 = var6 instanceof AbstractContainerScreen;
                  label1726:
                  if (var2 == 0) {
                     if (((var87) ? 1 : 0) != 0) {
                        AbstractContainerScreen var15 = (AbstractContainerScreen)var6;
                        var87 = ((var15.getMenu().containerId) != 0);
                        if (var2 != 0) {
                           break label1726;
                        }

                        if (((var87) ? 1 : 0) != G.player.inventoryMenu.containerId) {
                           return;
                        }
                     }

                     var87 = G.gui.screen() instanceof InventoryScreen;
                  }

                  int var16;
                  var16 = ((var87) ? 1 : 0);
                  var88 = this.I.w();
                  label1648:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        int var17 = 0;

                        label1714: {
                           while (var17 < BG.length) {
                              ItemStack var7 = G.player.getItemBySlot(BG[var17]);
                              if (var2 == 0) {
                                 var89 = ((com.elowen.utils.InventoryUtils.u(var7)) ? 1 : 0);
                                 if (var2 != 0) {
                                    break label1714;
                                 }

                                 label1706:
                                 if (var89 != 0) {
                                    byte var90 = (((byte)((var7.isEmpty()) ? 1 : 0)));
                                    if (var2 == 0) {
                                       if (var90 != 0) {
                                          break label1706;
                                       }

                                       var90 = (((byte)((this.K()) ? 1 : 0)));
                                    }

                                    if (var2 == 0) {
                                       if (var90 != 0) {
                                          break label1706;
                                       }

                                       var90 = (((byte)((this.r()) ? 1 : 0)));
                                    }

                                    if (var2 == 0) {
                                       if (var90 == 0) {
                                          break label1706;
                                       }

                                       float var137;
                                       var90 = (byte)((var137 = com.elowen.utils.InventoryUtils.T(BG[var17]) - com.elowen.utils.InventoryUtils.e$F(var7)) == 0.0F
                                          ? 0
                                          : (var137 < 0.0F ? -1 : 1));
                                    }

                                    if (var90 > 0) {
                                       G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, 5 + var17, 1, ContainerInput.THROW, G.player);
                                       this.Br = true;
                                       this.Bf++;
                                       J.e();
                                    }
                                 }

                                 var17++;
                              }

                              if (var2 != 0) {
                                 break;
                              }
                           }

                           var89 = 0;
                        }

                        int var18 = var89;

                        while (var18 < G.player.getInventory().getNonEquipmentItems().size()) {
                           ItemStack var36 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var18);
                           label1683:
                           if (var2 == 0) {
                              var88 = var36.isEmpty();
                              if (var2 != 0) {
                                 break label1648;
                              }

                              label1680:
                              if (((var88) ? 1 : 0) == 0) {
                                 ItemStack var91 = var36;
                                 if (var2 == 0) {
                                    if (!com.elowen.utils.InventoryUtils.u(var36)) {
                                       break label1680;
                                    }

                                    var91 = var36;
                                 }

                                 EquipmentSlot var8 = com.elowen.utils.InventoryUtils.R$EquipmentSlot(var91);
                                 float var9 = com.elowen.utils.InventoryUtils.e$F(var36);
                                 float var138;
                                 int var92 = (var138 = com.elowen.utils.InventoryUtils.T(var8) - var9) == 0.0F ? 0 : (var138 < 0.0F ? -1 : 1);
                                 if (var2 == 0) {
                                    var92 = (var92 == 0) ? 1 : 0;
                                 }

                                 int var10 = var92;
                                 float var139;
                                 int var93 = (var139 = com.elowen.utils.InventoryUtils.p(var8) - var9) == 0.0F ? 0 : (var139 < 0.0F ? -1 : 1);
                                 if (var2 == 0) {
                                    var93 = var93 < 0 ? 1 : 0;
                                 }

                                 int var11 = var93;
                                 if (var2 != 0) {
                                    break label1683;
                                 }

                                 label1671:
                                 if (((var10) != 0)) {
                                    int var94 = var11;
                                    if (var2 == 0) {
                                       if (var11 == 0) {
                                          break label1671;
                                       }

                                       var94 = ((this.Z$b()) ? 1 : 0);
                                    }

                                    if (var2 == 0) {
                                       if (var94 == 0) {
                                          break label1671;
                                       }

                                       var94 = var18;
                                    }

                                    label1659: {
                                       if (var94 < 9) {
                                          G.gameMode
                                             .handleContainerInput(G.player.inventoryMenu.containerId, var18 + 36, 0, ContainerInput.QUICK_MOVE, G.player);
                                          if (var2 == 0) {
                                             break label1659;
                                          }
                                       }

                                       G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var18, 0, ContainerInput.QUICK_MOVE, G.player);
                                    }

                                    this.Br = true;
                                    this.Bf++;
                                    J.e();
                                 }
                              }

                              var18++;
                           }

                           if (var2 != 0) {
                              break;
                           }
                        }
                     }

                     var88 = this.S;
                  }

                  label1643:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        var88 = this.Z$b();
                        if (var2 != 0) {
                           break label1643;
                        }

                        if (((var88) ? 1 : 0) != 0) {
                           G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, 45, 0, ContainerInput.PICKUP, G.player);
                           this.Br = true;
                           this.S = false;
                           this.Bf++;
                           J.e();
                        }
                     }

                     String[] var81 = bb;
                     var88 = this.B.t("Golden Apple");
                  }

                  label1636: {
                     label1905: {
                        if (var2 == 0) {
                           if (((var88) ? 1 : 0) != 0) {
                              label1630: {
                                 ItemStack var19 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                 ItemStack var37 = com.elowen.utils.InventoryUtils.p(Items.GOLDEN_APPLE);
                                 int var54 = com.elowen.utils.InventoryUtils.c(var37);
                                 int var95 = var54;
                                 if (var2 == 0) {
                                    if (var54 == -1) {
                                       break label1630;
                                    }

                                    var95 = ((this.Z$b()) ? 1 : 0);
                                 }

                                 label1625:
                                 if (var95 != 0) {
                                    label2003: {
                                       ItemStack var96 = var19;
                                       if (var2 == 0) {
                                          if (var19.getItem() != Items.GOLDEN_APPLE) {
                                             break label2003;
                                          }

                                          var96 = var19;
                                       }

                                       int var97 = var96.getCount() + var37.getCount();
                                       int var10001 = 64;
                                       if (var2 == 0) {
                                          if (var97 <= 64) {
                                             label1613: {
                                                if (var54 < 9) {
                                                   G.gameMode
                                                      .handleContainerInput(G.player.inventoryMenu.containerId, var54 + 36, 0, ContainerInput.PICKUP, G.player);
                                                   if (var2 == 0) {
                                                      break label1613;
                                                   }
                                                }

                                                G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var54, 0, ContainerInput.PICKUP, G.player);
                                             }

                                             this.Br = true;
                                             this.S = true;
                                             this.Bf++;
                                             J.e();
                                             if (var2 == 0) {
                                                break label1625;
                                             }
                                          }

                                          var97 = var19.getCount();
                                          var10001 = var37.getCount();
                                       }

                                       if (var97 >= var10001) {
                                          break label1625;
                                       }

                                       this.t(var54);
                                       if (var2 == 0) {
                                          break label1625;
                                       }
                                    }

                                    this.t(var54);
                                 }
                              }

                              if (var2 == 0) {
                                 break label1905;
                              }
                           }

                           var88 = this.B.t("Projectile");
                        }

                        if (var2 == 0) {
                           if (((var88) ? 1 : 0) != 0) {
                              label1593: {
                                 label1592: {
                                    var20 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                    Item var98 = var20.getItem();
                                    Item var132 = Items.EGG;
                                    if (var2 == 0) {
                                       if (var98 == Items.EGG) {
                                          break label1592;
                                       }

                                       var98 = var20.getItem();
                                       var132 = Items.SNOWBALL;
                                    }

                                    if (var98 != var132) {
                                       var99 = 0;
                                       break label1593;
                                    }
                                 }

                                 var99 = 1;
                              }

                              byte var38 = ((byte)(var99));
                              ItemStack var55 = this.w$Stack();
                              label1584:
                              if (var55 != null) {
                                 var100 = var38;
                                 label1582:
                                 if (var2 == 0) {
                                    if (var38 != 0) {
                                       var100 = var20.getCount();
                                       if (var2 != 0) {
                                          break label1582;
                                       }

                                       if (var100 >= var55.getCount()) {
                                          break label1584;
                                       }
                                    }

                                    var100 = com.elowen.utils.InventoryUtils.c(var55);
                                 }

                                 int var70;
                                 label1911: {
                                    var70 = var100;
                                    int var101 = var70;
                                    if (var2 == 0) {
                                       if (var70 == -1) {
                                          break label1584;
                                       }

                                       var131 = this;
                                       if (var2 != 0) {
                                          break label1911;
                                       }

                                       var101 = ((this.Z$b()) ? 1 : 0);
                                    }

                                    if (var101 == 0) {
                                       break label1584;
                                    }

                                    var131 = this;
                                 }

                                 var131.t(var70);
                              }

                              if (var2 == 0) {
                                 break label1905;
                              }
                           }

                           var88 = this.B.t("Fishing Rod");
                        }

                        if (var2 == 0) {
                           if (((var88) ? 1 : 0) != 0) {
                              label1914: {
                                 ItemStack var21 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                                 Item var103 = var21.getItem();
                                 if (var2 == 0) {
                                    if (var103 == Items.FISHING_ROD) {
                                       break label1914;
                                    }

                                    var103 = Items.FISHING_ROD;
                                 }

                                 int var39;
                                 label1915: {
                                    var39 = com.elowen.utils.InventoryUtils.X(var103);
                                    int var104 = var39;
                                    if (var2 == 0) {
                                       if (var39 == -1) {
                                          break label1914;
                                       }

                                       var131 = this;
                                       if (var2 != 0) {
                                          break label1915;
                                       }

                                       var104 = ((this.Z$b()) ? 1 : 0);
                                    }

                                    if (var104 == 0) {
                                       break label1914;
                                    }

                                    var131 = this;
                                 }

                                 var131.t(var39);
                              }

                              if (var2 == 0) {
                                 break label1905;
                              }
                           }

                           var88 = this.B.t("Block");
                        }

                        if (var2 != 0) {
                           break label1636;
                        }

                        label1533:
                        if (((var88) ? 1 : 0) != 0) {
                           ItemStack var22 = G.player.getItemBySlot(EquipmentSlot.OFFHAND);
                           ItemStack var40 = com.elowen.utils.InventoryUtils.b$ItemStack();
                           ItemStack var106 = var40;
                           if (var2 == 0) {
                              if (var40 == null) {
                                 break label1533;
                              }

                              var106 = var22;
                           }

                           int var107 = ((Scaffold.s(var106)) ? 1 : 0);
                           if (var2 == 0) {
                              if (var107 != 0) {
                                 var88 = ((var22.getCount()) != 0);
                                 if (var2 != 0) {
                                    break label1636;
                                 }

                                 if (((var88) ? 1 : 0) >= var40.getCount()) {
                                    break label1533;
                                 }
                              }

                              var107 = com.elowen.utils.InventoryUtils.c(var40);
                           }

                           int var56 = var107;
                           var88 = ((var56) != 0);
                           if (var2 != 0) {
                              break label1636;
                           }

                           if (var56 != -1) {
                              var88 = this.Z$b();
                              if (var2 != 0) {
                                 break label1636;
                              }

                              if (((var88) ? 1 : 0) != 0) {
                                 this.t(var56);
                              }
                           }
                        }
                     }

                     var88 = this.M.w();
                  }

                  label1510:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        var88 = this.B.t("Golden Apple");
                        if (var2 != 0) {
                           break label1510;
                        }

                        if (((var88) ? 1 : 0) == 0) {
                           this.A((int)(this.B3.o$F() - 1.0F), Items.GOLDEN_APPLE);
                        }
                     }

                     var88 = this.C.w();
                  }

                  label1465:
                  if (var2 == 0) {
                     label1501: {
                        if (((var88) ? 1 : 0) != 0) {
                           var88 = this.B.t("Block");
                           if (var2 != 0) {
                              break label1465;
                           }

                           if (((var88) ? 1 : 0) == 0) {
                              label1496: {
                                 label1921: {
                                    int var23 = (int)(this.BI.o$F() - 1.0F);
                                    ItemStack var41 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var23);
                                    var88 = ((var16) != 0);
                                    if (var2 == 0) {
                                       if (var16 != 0) {
                                          label1923: {
                                             ItemStack var57 = com.elowen.utils.InventoryUtils.b$ItemStack();
                                             ItemStack var108 = var57;
                                             if (var2 == 0) {
                                                if (var57 == null) {
                                                   break label1923;
                                                }

                                                var108 = var57;
                                             }

                                             label1484: {
                                                int var109 = var108.getCount();
                                                if (var2 == 0) {
                                                   if (var109 > var41.getCount()) {
                                                      break label1484;
                                                   }

                                                   var109 = ((Scaffold.s(var41)) ? 1 : 0);
                                                }

                                                if (var109 != 0) {
                                                   break label1923;
                                                }
                                             }

                                             this.I(var23, var57);
                                          }

                                          if (var2 == 0) {
                                             break label1921;
                                          }
                                       }

                                       var88 = Scaffold.s(var41);
                                    }

                                    if (var2 != 0) {
                                       break label1496;
                                    }

                                    if (((var88) ? 1 : 0) == 0) {
                                       ItemStack var58 = com.elowen.utils.InventoryUtils.b$ItemStack();
                                       if (var58 != null) {
                                          this.I(var23, var58);
                                       }
                                    }
                                 }

                                 float var140;
                                 var88 = (((var140 = com.elowen.utils.InventoryUtils.M() - this.BQ.o$F()) == 0.0F ? 0 : (var140 < 0.0F ? -1 : 1)) != 0);
                              }

                              if (var2 != 0) {
                                 break label1465;
                              }

                              if (((var88) ? 1 : 0) > 0) {
                                 var131 = this;
                                 if (var2 != 0) {
                                    break label1501;
                                 }

                                 ItemStack var59 = this.M();
                                 if (var59 != null) {
                                    this.l(var59);
                                 }
                              }
                           }
                        }

                        var131 = this;
                     }

                     var88 = var131.v.w();
                  }

                  label1429:
                  if (var2 == 0) {
                     label1458:
                     if (((var88) ? 1 : 0) != 0) {
                        ItemStack var42;
                        ItemStack var60;
                        var24 = (int)(this.Bq.o$F() - 1.0F);
                        var42 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var24);
                        var60 = com.elowen.utils.InventoryUtils.c$ItemStack();
                        ItemStack var71 = com.elowen.utils.InventoryUtils.j$ItemStack();
                        var111 = var71;
                        label1455:
                        if (var2 == 0) {
                           if (var71 != null) {
                              var111 = var71;
                              if (var2 != 0) {
                                 break label1455;
                              }

                              if (var71.getItem() != Items.MACE) {
                                 var111 = var71;
                                 if (var2 != 0) {
                                    break label1455;
                                 }

                                 if (com.elowen.utils.InventoryUtils.q(var71) > com.elowen.utils.InventoryUtils.f(var60)) {
                                    var60 = var71;
                                 }
                              }
                           }

                           var111 = var60;
                        }

                        if (var2 == 0) {
                           if (var111 == null) {
                              break label1458;
                           }

                           var111 = var42;
                        }

                        label1440: {
                           if (var2 == 0) {
                              if (var111.is(ItemTags.SWORDS)) {
                                 var112 = com.elowen.utils.InventoryUtils.f(var42);
                                 break label1440;
                              }

                              var111 = var42;
                           }

                           var112 = com.elowen.utils.InventoryUtils.q(var111);
                        }

                        float var76;
                        label1434: {
                           var76 = var112;
                           ItemStack var113 = var60;
                           if (var2 == 0) {
                              if (var60.is(ItemTags.SWORDS)) {
                                 var114 = com.elowen.utils.InventoryUtils.f(var60);
                                 break label1434;
                              }

                              var113 = var60;
                           }

                           var114 = com.elowen.utils.InventoryUtils.q(var113);
                        }

                        float var78 = var114;
                        float var141;
                        var88 = (((var141 = var78 - var76) == 0.0F ? 0 : (var141 < 0.0F ? -1 : 1)) != 0);
                        if (var2 != 0) {
                           break label1429;
                        }

                        if (((var88) ? 1 : 0) > 0) {
                           this.I(var24, var60);
                        }
                     }

                     var88 = this.c.w();
                  }

                  label1408:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        var88 = (((int)(this.j.o$F() - 1.0F)) != 0);
                        if (var2 != 0) {
                           break label1408;
                        }

                        int var25 = ((var88) ? 1 : 0);
                        ItemStack var43 = com.elowen.utils.InventoryUtils.i();
                        ItemStack var61 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var25);
                        if (var43 != null) {
                           var88 = var43.is(ItemTags.PICKAXES);
                           if (var2 != 0) {
                              break label1408;
                           }

                           label1418:
                           if (((var88) ? 1 : 0) != 0) {
                              label1928: {
                                 float var142;
                                 var88 = (((var142 = com.elowen.utils.InventoryUtils.k(var43) - com.elowen.utils.InventoryUtils.k(var61)) == 0.0F ? 0 : (var142 < 0.0F ? -1 : 1)) != 0);
                                 if (var2 == 0) {
                                    if (((var88) ? 1 : 0) > 0) {
                                       break label1928;
                                    }

                                    var88 = var61.is(ItemTags.PICKAXES);
                                 }

                                 if (var2 != 0) {
                                    break label1408;
                                 }

                                 if (((var88) ? 1 : 0) != 0) {
                                    break label1418;
                                 }
                              }

                              this.I(var25, var43);
                           }
                        }
                     }

                     var88 = this.Y.w();
                  }

                  label1373:
                  if (var2 == 0) {
                     label1400:
                     if (((var88) ? 1 : 0) != 0) {
                        int var26 = (int)(this.BA.o$F() - 1.0F);
                        ItemStack var44 = com.elowen.utils.InventoryUtils.g$ItemStack();
                        ItemStack var115 = var44;
                        if (var2 == 0) {
                           label1396: {
                              if (var44 != null) {
                                 var116 = var44.getItem();
                                 if (var2 != 0) {
                                    break label1396;
                                 }

                                 if (var116 == Items.MACE) {
                                    var44 = null;
                                 }
                              }

                              var116 = G.player.getInventory().getNonEquipmentItems().get(var26);
                           }

                           var115 = (ItemStack)var116;
                        }

                        ItemStack var62 = var115;
                        ItemStack var117 = var44;
                        if (var2 == 0) {
                           if (var44 == null) {
                              break label1400;
                           }

                           var117 = var44;
                        }

                        var88 = var117.is(ItemTags.AXES);
                        if (var2 != 0) {
                           break label1373;
                        }

                        label1383:
                        if (((var88) ? 1 : 0) != 0) {
                           label1932: {
                              float var143;
                              var88 = (((var143 = com.elowen.utils.InventoryUtils.k(var44) - com.elowen.utils.InventoryUtils.k(var62)) == 0.0F ? 0 : (var143 < 0.0F ? -1 : 1)) != 0);
                              if (var2 == 0) {
                                 if (((var88) ? 1 : 0) > 0) {
                                    break label1932;
                                 }

                                 var88 = var62.is(ItemTags.AXES);
                              }

                              if (var2 != 0) {
                                 break label1373;
                              }

                              if (((var88) ? 1 : 0) != 0) {
                                 break label1383;
                              }
                           }

                           this.I(var26, var44);
                        }
                     }

                     var88 = this.BT.w();
                  }

                  label1351:
                  if (var2 == 0) {
                     label1366: {
                        if (((var88) ? 1 : 0) != 0) {
                           int var27 = (int)(this.q.o$F() - 1.0F);
                           var131 = this;
                           if (var2 != 0) {
                              break label1366;
                           }

                           ItemStack var45 = this.o$ItemStack();
                           label1363:
                           if (var45 != null) {
                              label1934: {
                                 ItemStack var63 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var27);
                                 var88 = ItemStack.isSameItemSameComponents(var63, var45);
                                 if (var2 == 0) {
                                    if (((var88) ? 1 : 0) == 0) {
                                       break label1934;
                                    }

                                    var88 = ((var63.getCount()) != 0);
                                 }

                                 if (var2 != 0) {
                                    break label1351;
                                 }

                                 if (((var88) ? 1 : 0) >= var45.getCount()) {
                                    break label1363;
                                 }
                              }

                              this.I(var27, var45);
                           }
                        }

                        var131 = this;
                     }

                     var88 = var131.T.w();
                  }

                  label1346:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        var88 = this.B.t("Fishing Rod");
                        if (var2 != 0) {
                           break label1346;
                        }

                        if (((var88) ? 1 : 0) == 0) {
                           int var28 = (int)(this.x.o$F() - 1.0F);
                           ItemStack var46 = com.elowen.utils.InventoryUtils.A$ItemStack();
                           ItemStack var64 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var28);
                           var88 = var64.getItem() instanceof FishingRodItem;
                           if (var2 != 0) {
                              break label1346;
                           }

                           if (((var88) ? 1 : 0) == 0) {
                              this.I(var28, var46);
                           }
                        }
                     }

                     var88 = this.B6.w();
                  }

                  label1294:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        ItemStack var47;
                        ItemStack var65;
                        float var72;
                        float var77;
                        label1938: {
                           var29 = (int)(this.X.o$F() - 1.0F);
                           var47 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var29);
                           boolean var119 = this.Q.t("Crossbow");
                           if (var2 == 0) {
                              if (var119) {
                                 var65 = com.elowen.utils.InventoryUtils.n$ItemStack();
                                 var72 = com.elowen.utils.InventoryUtils.S(var65);
                                 var77 = com.elowen.utils.InventoryUtils.S(var47);
                                 if (var2 == 0) {
                                    break label1938;
                                 }
                              }

                              var119 = this.Q.t("Power Bow");
                           }

                           if (var119) {
                              var65 = com.elowen.utils.InventoryUtils.F();
                              var72 = com.elowen.utils.InventoryUtils.U$F(var65);
                              var77 = com.elowen.utils.InventoryUtils.U$F(var47);
                              if (var2 == 0) {
                                 break label1938;
                              }
                           }

                           var65 = com.elowen.utils.InventoryUtils.p();
                           var72 = com.elowen.utils.InventoryUtils.z(var65);
                           var77 = com.elowen.utils.InventoryUtils.z(var47);
                        }

                        ItemStack var120 = var65;
                        if (var2 == 0) {
                           if (var65 == null) {
                              var65 = com.elowen.utils.InventoryUtils.n$ItemStack();
                              var72 = com.elowen.utils.InventoryUtils.S(var65);
                              var77 = com.elowen.utils.InventoryUtils.S(var47);
                           }

                           var120 = var65;
                        }

                        if (var2 == 0) {
                           if (var120 == null) {
                              var65 = com.elowen.utils.InventoryUtils.F();
                              var72 = com.elowen.utils.InventoryUtils.U$F(var65);
                              var77 = com.elowen.utils.InventoryUtils.U$F(var47);
                           }

                           var120 = var65;
                        }

                        if (var2 == 0) {
                           if (var120 == null) {
                              var65 = com.elowen.utils.InventoryUtils.p();
                              var72 = com.elowen.utils.InventoryUtils.z(var65);
                              var77 = com.elowen.utils.InventoryUtils.z(var47);
                           }

                           var120 = var65;
                        }

                        label1318: {
                           label1939: {
                              if (var2 == 0) {
                                 if (var120 == null) {
                                    break label1939;
                                 }

                                 var120 = var47;
                              }

                              var88 = var120.isEmpty();
                              if (var2 == 0) {
                                 if (((var88) ? 1 : 0) != 0) {
                                    this.I(var29, var65);
                                    if (var2 == 0) {
                                       break label1939;
                                    }
                                 }

                                 float var144;
                                 var88 = (((var144 = var72 - var77) == 0.0F ? 0 : (var144 < 0.0F ? -1 : 1)) != 0);
                              }

                              if (var2 == 0) {
                                 if (((var88) ? 1 : 0) > 0) {
                                    this.I(var29, var65);
                                    if (var2 == 0) {
                                       break label1939;
                                    }
                                 }

                                 float var145;
                                 var88 = (((var145 = var72 - var77) == 0.0F ? 0 : (var145 < 0.0F ? -1 : 1)) != 0);
                              }

                              if (var2 != 0) {
                                 break label1318;
                              }

                              if (((var88) ? 1 : 0) == 0) {
                                 int var79 = this.g$I(var47);
                                 int var12 = this.g$I(var65);
                                 var88 = ((var12) != 0);
                                 if (var2 != 0) {
                                    break label1318;
                                 }

                                 if (var12 < var79) {
                                    this.I(var29, var65);
                                 }
                              }
                           }

                           float var146;
                           var88 = (((var146 = com.elowen.utils.InventoryUtils.d(Items.ARROW) - this.r.o$F()) == 0.0F ? 0 : (var146 < 0.0F ? -1 : 1)) != 0);
                        }

                        if (var2 != 0) {
                           break label1294;
                        }

                        if (((var88) ? 1 : 0) > 0) {
                           ItemStack var80 = com.elowen.utils.InventoryUtils.Z();
                           this.l(var80);
                        }
                     }

                     var88 = this.e.w();
                  }

                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        this.A((int)(this.BN.o$F() - 1.0F), Items.ENDER_PEARL);
                     }

                     var88 = this.U.w();
                  }

                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        this.A((int)(this.Bj.o$F() - 1.0F), Items.WATER_BUCKET);
                     }

                     var88 = this.R.w();
                  }

                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        this.A((int)(this.F.o$F() - 1.0F), Items.FIRE_CHARGE);
                     }

                     var88 = this.Bd.w();
                  }

                  label1248:
                  if (var2 == 0) {
                     label1283: {
                        if (((var88) ? 1 : 0) != 0) {
                           float var147;
                           var88 = (((var147 = this.g$I() - this.E.o$F()) == 0.0F ? 0 : (var147 < 0.0F ? -1 : 1)) != 0);
                           if (var2 == 0) {
                              label1278: {
                                 if (((var88) ? 1 : 0) > 0) {
                                    var131 = this;
                                    if (var2 != 0) {
                                       break label1278;
                                    }

                                    ItemStack var30 = this.c$ItemStack();
                                    if (var30 != null) {
                                       this.l(var30);
                                    }
                                 }

                                 var131 = this;
                              }

                              var88 = var131.Z.w();
                           }

                           if (var2 != 0) {
                              break label1248;
                           }

                           if (((var88) ? 1 : 0) != 0) {
                              var88 = this.B.t("Projectile");
                              if (var2 != 0) {
                                 break label1248;
                              }

                              label1268:
                              if (((var88) ? 1 : 0) == 0) {
                                 int var31 = (int)(this.h.o$F() - 1.0F);
                                 ItemStack var48 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var31);
                                 if (var16 != 0) {
                                    ItemStack var66 = this.w$Stack();
                                    if (var66 != null) {
                                       this.I(var31, var66);
                                    }

                                    if (var2 == 0) {
                                       break label1268;
                                    }
                                 }

                                 label1944: {
                                    Item var122 = var48.getItem();
                                    Item var133 = Items.EGG;
                                    if (var2 == 0) {
                                       if (var122 == Items.EGG) {
                                          break label1268;
                                       }

                                       var123 = var48;
                                       if (var2 != 0) {
                                          break label1944;
                                       }

                                       var122 = var48.getItem();
                                       var133 = Items.SNOWBALL;
                                    }

                                    if (var122 == var133) {
                                       break label1268;
                                    }

                                    var131 = this;
                                    if (var2 != 0) {
                                       break label1283;
                                    }

                                    var123 = this.w$Stack();
                                 }

                                 ItemStack var67 = var123;
                                 if (var67 != null) {
                                    this.I(var31, var67);
                                 }
                              }
                           }
                        }

                        var131 = this;
                     }

                     var88 = var131.f.w();
                  }

                  label1200:
                  if (var2 == 0) {
                     label1241: {
                        if (((var88) ? 1 : 0) != 0) {
                           int var32 = this.u$I();
                           float var148;
                           var88 = (((var148 = var32 - this.BO.o$F()) == 0.0F ? 0 : (var148 < 0.0F ? -1 : 1)) != 0);
                           if (var2 == 0) {
                              label1236: {
                                 if (((var88) ? 1 : 0) > 0) {
                                    var131 = this;
                                    if (var2 != 0) {
                                       break label1236;
                                    }

                                    ItemStack var49 = this.t();
                                    if (var49 != null) {
                                       this.l(var49);
                                    }
                                 }

                                 var131 = this;
                              }

                              var88 = var131.m.w();
                           }

                           if (var2 != 0) {
                              break label1200;
                           }

                           if (((var88) ? 1 : 0) != 0) {
                              var88 = this.B.t("Projectile");
                              if (var2 != 0) {
                                 break label1200;
                              }

                              label1226:
                              if (((var88) ? 1 : 0) == 0) {
                                 int var50 = (int)(this.K.o$F() - 1.0F);
                                 ItemStack var68 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var50);
                                 if (var16 != 0) {
                                    label1950: {
                                       ItemStack var73 = this.z$ItemStack();
                                       ItemStack var126 = var73;
                                       if (var2 == 0) {
                                          if (var73 == null) {
                                             break label1950;
                                          }

                                          var126 = var68;
                                       }

                                       label1220: {
                                          int var127 = ((ItemStack.isSameItemSameComponents(var126, var73)) ? 1 : 0);
                                          if (var2 == 0) {
                                             if (var127 == 0) {
                                                break label1220;
                                             }

                                             var127 = var68.getCount();
                                          }

                                          if (var127 >= var73.getCount()) {
                                             break label1950;
                                          }
                                       }

                                       this.I(var50, var73);
                                    }

                                    if (var2 == 0) {
                                       break label1226;
                                    }
                                 }

                                 ItemStack var128 = var68;
                                 if (var2 == 0) {
                                    if (var68.getItem() == Items.WIND_CHARGE) {
                                       break label1226;
                                    }

                                    var131 = this;
                                    if (var2 != 0) {
                                       break label1241;
                                    }

                                    var128 = this.z$ItemStack();
                                 }

                                 ItemStack var74 = var128;
                                 if (var74 != null) {
                                    this.I(var50, var74);
                                 }
                              }
                           }
                        }

                        var131 = this;
                     }

                     var88 = var131.p.w();
                  }

                  label1172:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        List<Integer> var33 = IntStream.range(0, G.player.getInventory().getNonEquipmentItems().size()).boxed().collect(Collectors.toList());
                        Collections.shuffle(var33);

                        for (Integer var69 : var33) {
                           ItemStack var75 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var69);
                           var88 = var75.isEmpty();
                           if (var2 != 0) {
                              break label1172;
                           }

                           label1188: {
                              ItemStack var134;
                              label1954: {
                                 if (var2 == 0) {
                                    if (((var88) ? 1 : 0) != 0) {
                                       break label1188;
                                    }

                                    var131 = this;
                                    var134 = var75;
                                    if (var2 != 0) {
                                       break label1954;
                                    }

                                    var88 = this.h(var75);
                                 }

                                 if (((var88) ? 1 : 0) != 0) {
                                    break label1188;
                                 }

                                 var131 = this;
                                 var134 = var75;
                              }

                              var131.l(var134);
                           }

                           if (var2 != 0) {
                              break;
                           }
                        }
                     }

                     var88 = this.V.w();
                  }

                  label1167:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        var88 = this.BP.w();
                        if (var2 != 0) {
                           break label1167;
                        }

                        if (((var88) ? 1 : 0) == 0) {
                           var88 = ((this.Bf) != 0);
                           if (var2 != 0) {
                              break label1167;
                           }

                           if (this.Bf >= (int)this.P.o$F()) {
                              G.getConnection().send(new ServerboundContainerClosePacket(G.player.inventoryMenu.containerId));
                              this.Br = false;
                              this.Bf = 0;
                           }
                        }
                     }

                     var88 = this.b.w();
                  }

                  label1138:
                  if (var2 == 0) {
                     if (((var88) ? 1 : 0) != 0) {
                        int var34 = 0;

                        while (var34 < 9) {
                           ItemStack var52 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var34);
                           if (var2 == 0) {
                              var88 = var52.isEmpty();
                              if (var2 != 0) {
                                 break label1138;
                              }

                              if (((var88) ? 1 : 0) == 0 && var52.getItem() == Items.ARROW) {
                                 var88 = this.Z$b();
                                 if (var2 != 0) {
                                    break label1138;
                                 }

                                 if (((var88) ? 1 : 0) == 0) {
                                    break;
                                 }

                                 G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var34 + 36, 0, ContainerInput.QUICK_MOVE, G.player);
                                 this.Br = true;
                                 this.Bf++;
                                 J.e();
                                 if (var2 == 0) {
                                    break;
                                 }
                              }

                              var34++;
                           }

                           if (var2 != 0) {
                              break;
                           }
                        }
                     }

                     var88 = this.BW.w();
                  }

                  if (var2 != 0) {
                     break label1878;
                  }

                  if (((var88) ? 1 : 0) != 0) {
                     int var35 = 0;

                     while (var35 < 9) {
                        ItemStack var53 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var35);
                        if (var2 == 0) {
                           var88 = var53.isEmpty();
                           if (var2 != 0) {
                              break label1878;
                           }

                           if (((var88) ? 1 : 0) == 0 && var53.getItem() == Items.COMPASS) {
                              var88 = this.Z$b();
                              if (var2 != 0) {
                                 break label1878;
                              }

                              if (((var88) ? 1 : 0) == 0) {
                                 break;
                              }

                              G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var35 + 36, 0, ContainerInput.QUICK_MOVE, G.player);
                              this.Br = true;
                              this.Bf++;
                              J.e();
                              if (var2 == 0) {
                                 break;
                              }
                           }

                           var35++;
                        }

                        if (var2 != 0) {
                           break;
                        }
                     }
                  }
               }

               var131 = this;
               if (var2 != 0) {
                  break label1877;
               }

               var88 = ((this.D) != 0);
            }

            if (((var88) ? 1 : 0) == -1) {
               return;
            }

            var131 = this;
         }

         var131.D++;
         return;
      }

      this.S = false;
   }

   private int g$I(ItemStack var1) {
      int var2 = ChestStealer.d$I();
      ItemStack var10000 = var1;
      if (var2 != 0) {
         if (var1 == null) {
            return 999;
         }

         var10000 = var1;
      }

      if (var2 != 0) {
         if (var10000.isEmpty()) {
            return 999;
         }

         var10000 = var1;
      }

      Item var3 = var10000.getItem();
      short var4 = (((short)((var3 instanceof CrossbowItem) ? 1 : 0)));
      if (var2 != 0) {
         if (var4 != 0) {
            return 0;
         }

         var4 = (((short)((var3 instanceof BowItem) ? 1 : 0)));
      }

      if (var2 != 0) {
         if (var4 != 0) {
            byte var5 = (((byte)((com.elowen.utils.InventoryUtils.F(var1)) ? 1 : 0)));
            if (var2 != 0) {
               if (var5 != 0) {
                  return 1;
               }

               var5 = (((byte)((com.elowen.utils.InventoryUtils.a(var1)) ? 1 : 0)));
            }

            if (var2 != 0) {
               if (var5 != 0) {
                  return 2;
               }

               var5 = 3;
            }

            return var5;
         }

         var4 = 999;
      }

      return var4;
   }

   private void t(int var1) {
      label21: {
         label20: {
            int var2 = ChestStealer.d$I();
            if (var2 != 0) {
               if (var1 >= 9) {
                  break label20;
               }

               G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var1 + 36, 40, ContainerInput.SWAP, G.player);
            }

            if (var2 != 0) {
               break label21;
            }
         }

         G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var1, 40, ContainerInput.SWAP, G.player);
      }

      this.Br = true;
      this.Bf++;
      J.e();
   }

   private boolean K() {
      int var10000 = ChestStealer.d$I();
      Aura var2 = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
      int var1 = var10000;
      Aura var3 = var2;
      if (var1 != 0) {
         if (var2 == null) {
            return false;
         }

         var3 = var2;
      }

      boolean var4 = var3.w();
      if (var1 != 0) {
         if (!var4) {
            return false;
         }

         var4 = Aura.r;
      }

      return var1 == 0 ? var4 : var4;
   }

   private void l(ItemStack var1) {
      int var2 = ChestStealer.d$I();
      int var10000 = ((this.K()) ? 1 : 0);
      if (var2 != 0) {
         if (var10000 != 0) {
            return;
         }

         var10000 = ((com.elowen.utils.InventoryUtils.p(var1)) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 == 0) {
            return;
         }

         var10000 = ((this.r()) ? 1 : 0);
      }

      if (var2 != 0) {
         if (var10000 == 0) {
            return;
         }

         var10000 = com.elowen.utils.InventoryUtils.c(var1);
      }

      int var3 = var10000;
      var10000 = var3;
      byte var10001 = -1;
      if (var2 != 0) {
         if (var3 == -1) {
            return;
         }

         var10000 = var3;
         var10001 = 9;
      }

      label42: {
         if (var10000 < var10001) {
            G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var3 + 36, 1, ContainerInput.THROW, G.player);
            if (var2 != 0) {
               break label42;
            }
         }

         G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var3, 1, ContainerInput.THROW, G.player);
      }

      this.Br = true;
      this.Bf++;
      J.e();
   }

   private void I(int var1, ItemStack var2) {
      int var10000 = ChestStealer.d$I();
      ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var1);
      int var3 = var10000;
      ItemStack var6 = var4;
      if (var3 != 0) {
         if (!com.elowen.utils.InventoryUtils.p(var4)) {
            return;
         }

         var6 = var2;
      }

      if (var6 != var4) {
         var10000 = ((this.Z$b()) ? 1 : 0);
         if (var3 != 0) {
            if (var10000 == 0) {
               return;
            }

            var10000 = com.elowen.utils.InventoryUtils.c(var2);
         }

         int var5 = var10000;
         var10000 = var5;
         byte var10001 = -1;
         if (var3 != 0) {
            if (var5 == -1) {
               return;
            }

            var10000 = var5;
            var10001 = 9;
         }

         label36: {
            if (var10000 < var10001) {
               G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var5 + 36, var1, ContainerInput.SWAP, G.player);
               if (var3 != 0) {
                  break label36;
               }
            }

            G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var5, var1, ContainerInput.SWAP, G.player);
         }

         this.Br = true;
         this.Bf++;
         J.e();
      }
   }

   private void A(int var1, Item var2) {
      int var10000 = ChestStealer.m();
      ItemStack var4 = (ItemStack)G.player.getInventory().getNonEquipmentItems().get(var1);
      int var3 = var10000;
      int var7 = ((com.elowen.utils.InventoryUtils.p(var4)) ? 1 : 0);
      if (var3 == 0) {
         if (var7 == 0) {
            return;
         }

         var7 = ((this.Z$b()) ? 1 : 0);
      }

      if (((var7) != 0)) {
         ItemStack var5 = com.elowen.utils.InventoryUtils.p(var2);
         int var6 = com.elowen.utils.InventoryUtils.c(var5);
         if (var6 != -1) {
            int var10001;
            label49: {
               label60: {
                  ItemStack var8 = var4;
                  if (var3 == 0) {
                     if (var4.getItem() != var2) {
                        break label60;
                     }

                     var8 = var4;
                  }

                  var7 = var8.getCount();
                  var10001 = var5.getCount();
                  if (var3 != 0) {
                     break label49;
                  }

                  if (var7 >= var10001) {
                     return;
                  }
               }

               var7 = var6;
               var10001 = 9;
            }

            label37: {
               if (var7 < var10001) {
                  G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var6 + 36, var1, ContainerInput.SWAP, G.player);
                  if (var3 == 0) {
                     break label37;
                  }
               }

               G.gameMode.handleContainerInput(G.player.inventoryMenu.containerId, var6, var1, ContainerInput.SWAP, G.player);
            }

            this.Br = true;
            this.Bf++;
            J.e();
         }
      }
   }

   private boolean W$Z() {
      int var10000 = ChestStealer.d$I();
      ArrayList var2 = new ArrayList();
      int var1 = var10000;
      int var10 = ((this.Bd.w()) ? 1 : 0);
      if (var1 != 0) {
         if (var10 == 0) {
            this.Z.P(false);
         }

         var2.add(Pair.of(this.v, this.Bq));
         var2.add(Pair.of(this.c, this.j));
         var2.add(Pair.of(this.Y, this.BA));
         var2.add(Pair.of(this.B6, this.X));
         var2.add(Pair.of(this.U, this.Bj));
         var2.add(Pair.of(this.e, this.BN));
         var2.add(Pair.of(this.R, this.F));
         String[] var7 = bb;
         var10 = ((this.B.t("Golden Apple")) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var10 == 0) {
            var2.add(Pair.of(this.M, this.B3));
         }

         String[] var8 = bb;
         var10 = ((this.B.t("Projectile")) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var10 == 0) {
            var2.add(Pair.of(this.Z, this.h));
         }

         String[] var9 = bb;
         var10 = ((this.B.t("Fishing Rod")) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var10 == 0) {
            var2.add(Pair.of(this.T, this.x));
         }

         var10 = ((this.B.t("Block")) ? 1 : 0);
      }

      if (var1 != 0) {
         if (var10 == 0) {
            var2.add(Pair.of(this.C, this.BI));
         }

         var10 = ((this.m.w()) ? 1 : 0);
      }

      if (var1 != 0) {
         if (((var10) != 0)) {
            var2.add(Pair.of(this.m, this.K));
         }

         var2.add(Pair.of(this.BT, this.q));
      }

      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while (true) {
         if (var4.hasNext()) {
            Pair var5 = (Pair)var4.next();
            var10 = ((((BooleanValue)var5.getKey()).w()) ? 1 : 0);
            if (var1 == 0) {
               break;
            }

            label113: {
               if (var1 != 0) {
                  if (var10 == 0) {
                     break label113;
                  }

                  var10 = (int)(((com.elowen.values.impl.FloatValue)var5.getValue()).o$F() - 1.0F);
               }

               int var6 = var10;
               boolean var12 = var3.contains(var6);
               if (var1 != 0) {
                  if (var12) {
                     return false;
                  }

                  var3.add(var6);
               }
            }

            if (var1 != 0) {
               continue;
            }
         }

         var10 = 1;
         break;
      }

      return (boolean)((var10) != 0);
   }

   private Boolean deobfLambda$new$27() {
      return this.BT.w();
   }

   private Boolean deobfLambda$new$26() {
      return this.Ba.w();
   }

   private Boolean deobfLambda$new$25() {
      return this.f.w();
   }

   private Boolean deobfLambda$new$24() {
      return this.m.w();
   }

   private Boolean deobfLambda$new$23() {
      return this.f.w();
   }

   private Boolean deobfLambda$new$22() {
      int var1 = ChestStealer.m();
      boolean var10000 = this.T.w();
      if (var1 == 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.t("Fishing Rod");
      }

      if (var1 != 0) {
         return var10000;
      } else {
         return !var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$21() {
      int var1 = ChestStealer.d$I();
      boolean var10000 = this.B.t("Fishing Rod");
      if (var1 != 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$20() {
      return this.Bd.w();
   }

   private Boolean deobfLambda$new$19() {
      int var1 = ChestStealer.d$I();
      boolean var10000 = this.Z.w();
      if (var1 != 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.Bd.w();
      }

      if (var1 != 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.t("Projectile");
      }

      if (var1 == 0) {
         return var10000;
      } else {
         return !var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$18() {
      int var1 = ChestStealer.d$I();
      boolean var10000 = this.Bd.w();
      if (var1 != 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.t("Projectile");
      }

      if (var1 == 0) {
         return var10000;
      } else {
         return !var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$17() {
      return this.p.w();
   }

   private Boolean deobfLambda$new$16() {
      return this.p.w();
   }

   private Boolean deobfLambda$new$15() {
      int var1 = ChestStealer.m();
      boolean var10000 = this.M.w();
      if (var1 == 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.t("Golden Apple");
      }

      if (var1 != 0) {
         return var10000;
      } else {
         return !var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$14() {
      int var1 = ChestStealer.m();
      boolean var10000 = this.B.t("Golden Apple");
      if (var1 == 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$13() {
      return this.R.w();
   }

   private Boolean deobfLambda$new$12() {
      return this.e.w();
   }

   private Boolean deobfLambda$new$11() {
      return this.U.w();
   }

   private Boolean deobfLambda$new$10() {
      return this.B6.w();
   }

   private Boolean deobfLambda$new$9() {
      return this.B6.w();
   }

   private Boolean deobfLambda$new$8() {
      return this.B6.w();
   }

   private Boolean deobfLambda$new$7() {
      return this.Y.w();
   }

   private Boolean deobfLambda$new$6() {
      return this.c.w();
   }

   private Boolean deobfLambda$new$5() {
      return this.C.w();
   }

   private Boolean deobfLambda$new$4() {
      int var1 = ChestStealer.d$I();
      boolean var10000 = this.C.w();
      if (var1 != 0) {
         if (!var10000) {
            return false;
         }

         var10000 = this.B.t("Block");
      }

      if (var1 == 0) {
         return var10000;
      } else {
         return !var10000 ? true : false;
      }
   }

   private Boolean deobfLambda$new$3() {
      int var1 = ChestStealer.m();
      boolean var10000 = this.B.t("Block");
      if (var1 == 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$2() {
      return this.v.w();
   }

   private Boolean deobfLambda$new$1() {
      return this.V.w();
   }

   private Boolean deobfLambda$new$0() {
      int var1 = ChestStealer.m();
      boolean var10000 = this.BP.w();
      if (var1 == 0) {
         var10000 = !var10000;
      }

      return var10000;
   }

   static {
      J = new TickTimeHelper();
      BG = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
