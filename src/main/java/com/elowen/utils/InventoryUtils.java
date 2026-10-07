package com.elowen.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;

public class InventoryUtils {
   private static final Minecraft M;
   private static final String[] a = new String[]{"Teleport", "golden", "使用", "再来", "点击", "iron", "Click", "copper", "长按点击", "选择一个队伍", "diamond", "chainmail", "netherite", "离开游戏", "再来一局", "leather", "传送", "点击使用", "Right"};
   private static int E(ResourceKey<Enchantment> var0, ItemStack var1) {
      String var10000 = Vector2f.e();
      Minecraft var3 = Minecraft.getInstance();
      String var2 = var10000;
      if (var3.level != null) {
         int var4 = ((var1.isEmpty()) ? 1 : 0);
         if (var2 == null) {
            if (var4 != 0) {
               return 0;
            }

            var4 = var3.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(var0).map(var10001 -> deobfLambda$getEnchantLevel$0(var1, var10001)).orElse(0);
         }

         return var4;
      } else {
         return 0;
      }
   }

   private static ItemStack O(int var0) {
      return M.player.getInventory().getItem(var0);
   }

   public static List<ItemStack> C() {
      List<ItemStack> var1 = new ArrayList<>(40);

      for (int var2 = 0; var2 < 40; var2++) {
         var1.add(O(var2));
      }

      return var1;
   }

   public static boolean G$Z() {
      return C().stream().anyMatch(InventoryUtils::deobfLambda$shouldDisableFeatures$0);
   }

   public static boolean t(ItemStack var0) {
      String var1 = Vector2f.e();
      return var0.isEmpty() ? false : var0.getItem() == Items.PLAYER_HEAD;
   }

   public static boolean R$Z(ItemStack var0) {
      String var1 = Vector2f.e();
      if (!var0.isEmpty() && var0.is(ItemTags.AXES)) {
         int var2 = E(Enchantments.SHARPNESS, var0);
         return var2 >= 8 && var2 < 50;
      } else {
         return false;
      }
   }

   public static boolean X(ItemStack var0) {
      String var1 = Vector2f.e();
      return var0.isEmpty() ? false : var0.getItem() == Items.GOLDEN_AXE && E(Enchantments.SHARPNESS, var0) > 100;
   }

   public static boolean Q(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() == Items.ENCHANTED_GOLDEN_APPLE;
   }

   public static boolean Z(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() == Items.END_CRYSTAL;
   }

   public static boolean M(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() == Items.SLIME_BALL && E(Enchantments.KNOCKBACK, var0) > 1;
   }

   public static boolean B(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() == Items.STICK && E(Enchantments.KNOCKBACK, var0) > 1;
   }

   public static int J$I() {
      boolean var2 = false;
      String var10000 = Vector2f.e();
      int var1 = 9;
      String var0 = var10000;

      while (true) {
         if (var1 < 36) {
            var2 = O(var1).isEmpty();
            if (var0 != null) {
               break;
            }

            if (var0 != null) {
               return ((var2) ? 1 : 0);
            }

            if (((var2) ? 1 : 0) != 0) {
               return var1;
            }

            var1++;
            if (var0 == null) {
               continue;
            }
         }

         var2 = ((-1) != 0);
         break;
      }

      return ((var2) ? 1 : 0);
   }

   public static int L() {
      boolean var2 = false;
      String var10000 = Vector2f.e();
      int var1 = 0;
      String var0 = var10000;

      while (true) {
         if (var1 < 9) {
            var2 = O(var1).isEmpty();
            if (var0 != null) {
               break;
            }

            if (var0 != null) {
               return ((var2) ? 1 : 0);
            }

            if (((var2) ? 1 : 0) != 0) {
               return var1;
            }

            var1++;
            if (var0 == null) {
               continue;
            }
         }

         var2 = ((-1) != 0);
         break;
      }

      return ((var2) ? 1 : 0);
   }

   public static int C$I(ItemStack var0) {
      return E(Enchantments.PUNCH, var0);
   }

   public static int g$I(ItemStack var0) {
      return E(Enchantments.POWER, var0);
   }

   public static float e$F(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 == null || var0.isEmpty()) {
         return 0.0F;
      }

      if (!var0.is(ItemTags.HEAD_ARMOR) && !var0.is(ItemTags.CHEST_ARMOR) && !var0.is(ItemTags.LEG_ARMOR) && !var0.is(ItemTags.FOOT_ARMOR)) {
         return 0.0F;
      }

      String var3 = BuiltInRegistries.ITEM.getKey(var0.getItem()).getPath();
      if (var3.contains("netherite")) {
         short var2 = 600;
      }

      if (var3.contains("diamond")) {
         short var7 = 500;
      }

      if (var3.contains("iron")) {
         short var8 = 400;
      }

      if (var3.contains("golden")) {
         short var9 = 300;
      }

      if (var3.contains("chainmail")) {
         short var10 = 200;
      }

      if (var3.contains("copper")) {
         short var11 = 150;
      }

      if (var3.contains("leather")) {
         byte var12 = 100;
      }

      int var13 = 0;
      ItemAttributeModifiers var4 = (ItemAttributeModifiers)var0.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      Iterator var5 = var4.modifiers().iterator();
      while (var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         if (var6.attribute().is(Attributes.ARMOR) && var6.modifier().operation() == Operation.ADD_VALUE) {
            var13 += (int)(var6.modifier().amount() * 100.0);
         }
      }

      var13 += E(Enchantments.PROTECTION, var0);
      return var13;
   }

   public static float T(EquipmentSlot var0) {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestArmorScore$0).filter(var10000 -> deobfLambda$getBestArmorScore$1(var0, var10000)).map(InventoryUtils::e$F).max(Float::compareTo).orElse(0.0F);
   }

   public static float p(EquipmentSlot var0) {
      String var1 = Vector2f.e();

      byte var2 = switch (InventoryUtils$1.B[var0.ordinal()]) {
         case 1 -> 39;
         case 2 -> 38;
         case 3 -> 37;
         case 4 -> 36;
         default -> -1;
      };
      return var2 < 0 ? 0.0F : e$F(O(var2));
   }

   private static double j(ItemStack var0, EquipmentSlot var1) {
      String var2 = Vector2f.e();
      if (var0.isEmpty()) {
         return 0.0;
      }

      ItemAttributeModifiers var3 = (ItemAttributeModifiers)var0.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
      double var4 = 0.0;
      Iterator var6 = var3.modifiers().iterator();
      while (var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         if (var7.attribute().is(Attributes.ATTACK_DAMAGE) && var7.slot().test(var1) && var7.modifier().operation() == Operation.ADD_VALUE) {
            var4 += var7.modifier().amount();
         }
      }

      return var4;
   }

   public static float f(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty() && var0.is(ItemTags.SWORDS)) {
         double var2 = j(var0, EquipmentSlot.MAINHAND);
         int var4 = E(Enchantments.SHARPNESS, var0);
         if (var4 > 0) {
            var2 += 0.5 * var4 + 0.5;
         }

         return (float)var2;
      } else {
         return 0.0F;
      }
   }

   public static float q(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty() && var0.is(ItemTags.AXES) && R$Z(var0)) {
         double var2 = j(var0, EquipmentSlot.MAINHAND);
         int var4 = E(Enchantments.SHARPNESS, var0);
         if (var4 > 0) {
            var2 += 0.5 * var4 + 0.5;
         }

         return (float)var2;
      } else {
         return 0.0F;
      }
   }

   public static ItemStack c$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestSword$0).max(Comparator.comparingInt(InventoryUtils::deobfLambda$getBestSword$1)).orElse(null);
   }

   public static float B$F() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestSwordDamage$0).map(InventoryUtils::f).max(Float::compareTo).orElse(0.0F);
   }

   public static float k(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 == null || var0.isEmpty()) {
         return 0.0F;
      }

      if (!C$Z(var0) && !R$Z(var0)) {
         float var2 = 0.0F;
         if (var0.is(ItemTags.PICKAXES)) {
            var2 = var0.getDestroySpeed(Blocks.STONE.defaultBlockState());
         }

         if (var0.is(ItemTags.AXES)) {
            var2 = var0.getDestroySpeed(Blocks.OAK_LOG.defaultBlockState());
         }

         if (var0.is(ItemTags.SHOVELS)) {
            var2 = var0.getDestroySpeed(Blocks.DIRT.defaultBlockState());
         }

         return 0.0F;
      } else {
         return 0.0F;
      }
   }

   public static ItemStack i() {
      return n(ItemTags.PICKAXES);
   }

   public static ItemStack g$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestAxe$0).max(Comparator.comparingInt(InventoryUtils::deobfLambda$getBestAxe$1)).orElse(null);
   }

   public static ItemStack j$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestShapeAxe$0).max(Comparator.comparingInt(InventoryUtils::deobfLambda$getBestShapeAxe$1)).orElse(null);
   }

   public static ItemStack y$ItemStack() {
      return n(ItemTags.SHOVELS);
   }

   private static ItemStack n(TagKey var0) {
      return C().stream().filter(var10000 -> deobfLambda$getBestTool$0(var0, var10000)).max(Comparator.comparingInt(InventoryUtils::deobfLambda$getBestTool$1)).orElse(null);
   }

   public static float m() {
      return z(ItemTags.PICKAXES);
   }

   public static float I() {
      return z(ItemTags.AXES);
   }

   public static float b$F() {
      return z(ItemTags.SHOVELS);
   }

   private static float z(TagKey var0) {
      return C().stream().filter(var10000 -> deobfLambda$getBestToolScore$0(var0, var10000)).map(InventoryUtils::k).max(Float::compareTo).orElse(0.0F);
   }

   public static float S(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty() && var0.getItem() instanceof CrossbowItem) {
         int var2 = 0;
         var2 += E(Enchantments.QUICK_CHARGE, var0);
         var2 += E(Enchantments.MULTISHOT, var0);
         var2 += E(Enchantments.PIERCING, var0);
         return var2;
      } else {
         return 0.0F;
      }
   }

   public static float z(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty() && var0.getItem() instanceof BowItem) {
         float var2 = 10.0F;
         var2 += E(Enchantments.PUNCH, var0);
         var2 += E(Enchantments.INFINITY, var0);
         var2 += E(Enchantments.FLAME, var0);
         var2 += E(Enchantments.POWER, var0) / 10.0F;
         return var2 + (float)var0.getDamageValue() / var0.getMaxDamage();
      } else {
         return 0.0F;
      }
   }

   public static float U$F(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 != null && !var0.isEmpty() && var0.getItem() instanceof BowItem) {
         float var2 = 10.0F;
         var2 += E(Enchantments.PUNCH, var0) / 10.0F;
         var2 += E(Enchantments.INFINITY, var0);
         var2 += E(Enchantments.FLAME, var0);
         var2 += E(Enchantments.POWER, var0);
         return var2 + (float)var0.getDamageValue() / var0.getMaxDamage();
      } else {
         return 0.0F;
      }
   }

   public static ItemStack n$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestCrossbow$0).max(Comparator.comparingDouble(InventoryUtils::S)).orElse(null);
   }

   public static float o$F() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestCrossbowScore$0).map(InventoryUtils::S).max(Float::compareTo).orElse(0.0F);
   }

   public static float G$F() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestPunchBowScore$0).map(InventoryUtils::z).max(Float::compareTo).orElse(0.0F);
   }

   public static float A$F() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestPowerBowScore$0).map(InventoryUtils::U$F).max(Float::compareTo).orElse(0.0F);
   }

   public static ItemStack p() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestPunchBow$0).max(Comparator.comparingDouble(InventoryUtils::z)).orElse(null);
   }

   public static ItemStack F() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestPowerBow$0).max(Comparator.comparingDouble(InventoryUtils::U$F)).orElse(null);
   }

   public static boolean a(ItemStack var0) {
      String var1 = Vector2f.e();
      return z(var0) > 10.0F && p(var0);
   }

   public static boolean F(ItemStack var0) {
      String var1 = Vector2f.e();
      return U$F(var0) > 10.0F && p(var0);
   }

   public static ItemStack Q() {
      return C().stream().filter(InventoryUtils::deobfLambda$getBestProjectile$0).max(Comparator.comparingInt(ItemStack::getCount)).orElse(null);
   }

   public static ItemStack W$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getWorstProjectile$0).min(Comparator.comparingInt(ItemStack::getCount)).orElse(null);
   }

   public static ItemStack Z() {
      return C().stream().filter(InventoryUtils::deobfLambda$getWorstArrow$0).min(Comparator.comparingInt(ItemStack::getCount)).orElse(null);
   }

   private static boolean d(ItemStack var0) {
      String var1 = Vector2f.e();
      if (!var0.isEmpty() && var0.getItem() instanceof BlockItem var2) {
         ;
      }

      return false;
   }

   public static ItemStack b$ItemStack() {
      return C().stream().filter(InventoryUtils::d).filter(InventoryUtils::p).max(Comparator.comparingInt(ItemStack::getCount)).orElse(null);
   }

   public static ItemStack a$ItemStack() {
      return C().stream().filter(InventoryUtils::d).filter(InventoryUtils::p).min(Comparator.comparingInt(ItemStack::getCount)).orElse(null);
   }

   public static int M() {
      return C().stream().filter(InventoryUtils::d).filter(InventoryUtils::p).mapToInt(ItemStack::getCount).sum();
   }

   public static ItemStack A$ItemStack() {
      return C().stream().filter(InventoryUtils::deobfLambda$getFishingRod$0).findAny().orElse(null);
   }

   public static boolean p(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0.isEmpty()) {
         return true;
      }

      if (var0.getItem() == Items.PLAYER_HEAD) {
         return false;
      }

      String var2 = var0.getHoverName().getString();
      return !var2.contains("Click")
         && !var2.contains("Right")
         && !var2.contains("点击")
         && !var2.contains("Teleport")
         && !var2.contains("使用")
         && !var2.contains("传送")
         && !var2.contains("再来");
   }

   public static boolean i(ItemStack var0) {
      String var1 = Vector2f.e();
      return var0 != null && !var0.isEmpty() ? var0.getItem() == Items.MACE : false;
   }

   public static boolean g$Z(ItemStack var0) {
      String var1 = Vector2f.e();
      return var0 != null && !var0.isEmpty() ? var0.getItem() == Items.WIND_CHARGE : false;
   }

   public static boolean u(ItemStack var0) {
      return R$EquipmentSlot(var0) != null;
   }

   public static EquipmentSlot R$EquipmentSlot(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 == null || var0.isEmpty()) {
         return null;
      } else if (var0.is(ItemTags.HEAD_ARMOR)) {
         return EquipmentSlot.HEAD;
      } else if (var0.is(ItemTags.CHEST_ARMOR)) {
         return EquipmentSlot.CHEST;
      } else if (var0.is(ItemTags.LEG_ARMOR)) {
         return EquipmentSlot.LEGS;
      } else {
         return var0.is(ItemTags.FOOT_ARMOR) ? EquipmentSlot.FEET : null;
      }
   }

   public static boolean e$Z(ItemStack var0) {
      String var1 = Vector2f.e();
      if (!var0.isEmpty() && var0.getItem() instanceof BlockItem var2) {
         ;
      }

      return false;
   }

   public static int X(Item var0) {
      int var3 = 0;
      String var10000 = Vector2f.e();
      int var2 = 0;
      String var1 = var10000;

      while (true) {
         if (var2 < 36) {
            var3 = var2;
            if (var1 != null) {
               break;
            }

            if (var1 != null) {
               return var2;
            }

            if (O(var2).getItem() == var0) {
               return var2;
            }

            var2++;
            if (var1 == null) {
               continue;
            }
         }

         var3 = -1;
         break;
      }

      return var3;
   }

   public static ItemStack p(Item var0) {
      net.minecraft.world.item.ItemStack var5 = null;
      String var10000 = Vector2f.e();
      ItemStack var2 = null;
      String var1 = var10000;
      int var3 = 0;

      while (true) {
         if (var3 < 36) {
            ItemStack var4 = O(var3);
            if (var1 == null) {
               var5 = var4;
               if (var1 != null) {
                  break;
               }

               label62:
               if (!var4.isEmpty()) {
                  ItemStack var6 = var4;
                  if (var1 == null) {
                     if (var4.getItem() != var0 && var1 == null) {
                        break label62;
                     }

                     var6 = var2;
                  }

                  label54:
                  if (var1 == null) {
                     if (var6 != null) {
                        var6 = var4;
                        if (var1 != null) {
                           break label54;
                        }

                        if (var4.getCount() <= var2.getCount()) {
                           break label62;
                        }
                     }

                     var6 = var4;
                  }

                  var2 = var6;
               }

               var3++;
            }

            if (var1 == null) {
               continue;
            }
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   public static int c(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0 == null) {
         return -1;
      }

      int var2 = 0;
      while (var2 < 36) {
         if (O(var2) == var0) {
            return var2;
         }

         var2++;
      }

      return -1;
   }

   public static boolean C(Item var0) {
      return C().stream().anyMatch(var10000 -> deobfLambda$hasItem$0(var0, var10000));
   }

   public static int d(Item var0) {
      return C().stream().filter(var10000 -> deobfLambda$getItemCount$0(var0, var10000)).mapToInt(ItemStack::getCount).sum();
   }

   public static boolean C$Z(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0.isEmpty()) {
         return false;
      } else if (var0.getItem() == Items.GOLDEN_AXE && var0.is(ItemTags.AXES) && E(Enchantments.SHARPNESS, var0) > 100) {
         return true;
      } else {
         return var0.getItem() == Items.SLIME_BALL && E(Enchantments.KNOCKBACK, var0) > 1
            ? true
            : var0.getItem() == Items.TOTEM_OF_UNDYING || var0.getItem() == Items.END_CRYSTAL;
      }
   }

   public static boolean U$Z(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0.isEmpty()) {
         return true;
      }

      Item var2 = var0.getItem();
      return var2 instanceof BlockItem var3
         ? var3.getBlock() != Blocks.ENCHANTING_TABLE && var3.getBlock() != Blocks.COBWEB
         : var2 != Items.BOOK
            && var2 != Items.WRITABLE_BOOK
            && var2 != Items.WRITTEN_BOOK
            && var2 != Items.ENCHANTED_BOOK
            && var2 != Items.EXPERIENCE_BOTTLE
            && var2 != Items.FIREWORK_ROCKET
            && var2 != Items.WHEAT_SEEDS
            && var2 != Items.BEETROOT_SEEDS
            && var2 != Items.MELON_SEEDS
            && var2 != Items.PUMPKIN_SEEDS
            && var2 != Items.FLINT_AND_STEEL;
   }

   private static boolean deobfLambda$getItemCount$0(Item var0, ItemStack var1) {
      String var2 = Vector2f.e();
      return !var1.isEmpty() && var1.getItem() == var0;
   }

   private static boolean deobfLambda$hasItem$0(Item var0, ItemStack var1) {
      String var2 = Vector2f.e();
      return !var1.isEmpty() && var1.getItem() == var0;
   }

   private static boolean deobfLambda$getFishingRod$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof FishingRodItem && p(var0);
   }

   private static boolean deobfLambda$getWorstArrow$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof ArrowItem && p(var0);
   }

   private static boolean deobfLambda$getWorstProjectile$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && (var0.getItem() == Items.EGG || var0.getItem() == Items.SNOWBALL) && p(var0);
   }

   private static boolean deobfLambda$getBestProjectile$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && (var0.getItem() == Items.EGG || var0.getItem() == Items.SNOWBALL) && p(var0);
   }

   private static boolean deobfLambda$getBestPowerBow$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof BowItem && p(var0);
   }

   private static boolean deobfLambda$getBestPunchBow$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof BowItem && p(var0);
   }

   private static boolean deobfLambda$getBestPowerBowScore$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof BowItem && F(var0) && p(var0);
   }

   private static boolean deobfLambda$getBestPunchBowScore$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof BowItem && a(var0) && p(var0);
   }

   private static boolean deobfLambda$getBestCrossbowScore$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof CrossbowItem && p(var0);
   }

   private static boolean deobfLambda$getBestCrossbow$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.getItem() instanceof CrossbowItem && p(var0);
   }

   private static boolean deobfLambda$getBestToolScore$0(TagKey var0, ItemStack var1) {
      String var2 = Vector2f.e();
      return !var1.isEmpty() && var1.is(var0) && p(var1);
   }

   private static int deobfLambda$getBestTool$1(ItemStack var0) {
      return (int)(k(var0) * 100.0F);
   }

   private static boolean deobfLambda$getBestTool$0(TagKey var0, ItemStack var1) {
      String var2 = Vector2f.e();
      return !var1.isEmpty() && var1.is(var0) && p(var1);
   }

   private static int deobfLambda$getBestShapeAxe$1(ItemStack var0) {
      return (int)(q(var0) * 100.0F);
   }

   private static boolean deobfLambda$getBestShapeAxe$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.is(ItemTags.AXES) && R$Z(var0) && !X(var0) && !i(var0) && p(var0);
   }

   private static int deobfLambda$getBestAxe$1(ItemStack var0) {
      return (int)(k(var0) * 100.0F);
   }

   private static boolean deobfLambda$getBestAxe$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.is(ItemTags.AXES) && !R$Z(var0) && !i(var0) && p(var0);
   }

   private static boolean deobfLambda$getBestSwordDamage$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.is(ItemTags.SWORDS) && p(var0);
   }

   private static int deobfLambda$getBestSword$1(ItemStack var0) {
      return (int)(f(var0) * 100.0F);
   }

   private static boolean deobfLambda$getBestSword$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && var0.is(ItemTags.SWORDS) && p(var0);
   }

   private static boolean deobfLambda$getBestArmorScore$1(EquipmentSlot var0, ItemStack var1) {
      String var2 = Vector2f.e();
      if (var0 == EquipmentSlot.HEAD && var1.is(ItemTags.HEAD_ARMOR)) {
         return true;
      } else if (var0 == EquipmentSlot.CHEST && var1.is(ItemTags.CHEST_ARMOR)) {
         return true;
      } else {
         return var0 == EquipmentSlot.LEGS && var1.is(ItemTags.LEG_ARMOR) ? true : var0 == EquipmentSlot.FEET && var1.is(ItemTags.FOOT_ARMOR);
      }
   }

   private static boolean deobfLambda$getBestArmorScore$0(ItemStack var0) {
      String var1 = Vector2f.e();
      return !var0.isEmpty() && (var0.is(ItemTags.HEAD_ARMOR) || var0.is(ItemTags.CHEST_ARMOR) || var0.is(ItemTags.LEG_ARMOR) || var0.is(ItemTags.FOOT_ARMOR));
   }

   private static boolean deobfLambda$shouldDisableFeatures$0(ItemStack var0) {
      String var1 = Vector2f.e();
      if (var0.isEmpty()) {
         return false;
      }

      String var2 = var0.getHoverName().getString();
      return var2.contains("长按点击") || var2.contains("点击使用") || var2.contains("离开游戏") || var2.contains("选择一个队伍") || var2.contains("再来一局");
   }

   private static Integer deobfLambda$getEnchantLevel$0(ItemStack var0, Reference var1) {
      return EnchantmentHelper.getItemEnchantmentLevel(var1, var0);
   }

   static {
      M = Minecraft.getInstance();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
