package com.elowen.modules.impl.player;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventUpdateHeldItem;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "AutoTools", a = "Automatically switches to the best tool for the job", M = Category.PLAYER)
public class AutoTools extends Module {
   private final BooleanValue p;
   private final BooleanValue f;
   private final BooleanValue S;
   private int e;
   private static final String[] b = new String[]{"Switch Back", "Check Sword", "Silent"};
   public AutoTools() {
      String[] var1 = b;
      this.p = ValueBuilder.m(this, "Check Sword").h(true).f$K().f$O();
      this.f = ValueBuilder.m(this, "Switch Back").h(true).f$K().f$O();
      this.S = ValueBuilder.m(this, "Silent").l(this.f::w).h(true).f$K().f$O();
      this.e = -1;
   }

   private int P(ResourceKey<Enchantment> var1, ItemStack var2) {
      int var3 = ChestStealer.d$I();
      if (G.level != null) {
         int var10000 = ((var2.isEmpty()) ? 1 : 0);
         if (var3 != 0) {
            if (var10000 != 0) {
               return 0;
            }

            var10000 = G.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(var1).map(ref -> deobfLambda$getEnchantLevel$0(var2, ref)).orElse(0);
         }

         return var10000;
      } else {
         return 0;
      }
   }

   @EventTarget
   public void z(EventUpdateHeldItem var1) {
      int var2 = ChestStealer.d$I();
      boolean var10000 = this.f.w();
      if (var2 != 0) {
         if (!var10000) {
            return;
         }

         var10000 = this.S.w();
      }

      if (var10000 && var1.j$InteractionHand() == InteractionHand.MAIN_HAND && this.e != -1) {
         var1.w(G.player.getInventory().getItem(this.e));
      }
   }

   @EventTarget
   public void d(EventMotion var1) {
      Minecraft var6 = null;
      AutoTools var9 = null;
      AutoTools var11 = null;
      int var2 = ChestStealer.d$I();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE) {
         int var10000 = ((G.gameMode.isDestroying()) ? 1 : 0);
         if (var2 != 0) {
            if ((var10000 == 0)) {
               return;
            }

            var10000 = ((this.p.w()) ? 1 : 0);
         }

         label117: {
            if (((var10000) != 0)) {
               var6 = G;
               if (var2 == 0) {
                  break label117;
               }

               ItemStack var3 = G.player.getMainHandItem();
               if (var3.is(ItemTags.SWORDS)) {
                  return;
               }
            }

            var6 = G;
         }

         label125: {
            HitResult var7 = var6.hitResult;
            if (var2 != 0) {
               if (var6.hitResult.getType() != Type.BLOCK) {
                  return;
               }

               var7 = G.hitResult;
            }

            BlockHitResult var5 = (BlockHitResult)var7;
            int var4 = this.F(var5.getBlockPos());
            var10000 = var4;
            int var10001 = -1;
            if (var2 != 0) {
               if (var4 == -1) {
                  break label125;
               }

               var10000 = var4;
               var10001 = G.player.getInventory().getSelectedSlot();
            }

            label109: {
               label126: {
                  if (var2 != 0) {
                     if (var10000 == var10001) {
                        break label125;
                     }

                     var9 = this;
                     if (var2 == 0) {
                        break label126;
                     }

                     var10000 = this.e;
                     var10001 = -1;
                  }

                  if (var10000 != var10001) {
                     break label109;
                  }

                  var9 = this;
               }

               var9.e = G.player.getInventory().getSelectedSlot();
            }

            G.player.getInventory().setSelectedSlot(var4);
         }

         if (var2 != 0) {
            return;
         }
      }

      int var10 = ((G.gameMode.isDestroying()) ? 1 : 0);
      if (var2 != 0) {
         if (var10 != 0) {
            return;
         }

         var10 = ((this.f.w()) ? 1 : 0);
      }

      label128: {
         if (var2 != 0) {
            if (var10 == 0) {
               return;
            }

            var11 = this;
            if (var2 == 0) {
               break label128;
            }

            var10 = this.e;
         }

         if (var10 == -1) {
            return;
         }

         G.player.getInventory().setSelectedSlot(this.e);
         var11 = this;
      }

      var11.e = -1;
   }

   private int F(BlockPos var1) {
      ItemStack var13 = null;
      int var10000 = ChestStealer.d$I();
      BlockState var3 = G.level.getBlockState(var1);
      Block var4 = var3.getBlock();
      int var5 = 0;
      int var2 = var10000;
      float var6 = 1.0F;
      int var7 = 0;

      while (true) {
         if (var7 < 9) {
            ItemStack var8 = G.player.getInventory().getItem(var7);
            if (var2 != 0) {
               var10000 = ((com.elowen.utils.InventoryUtils.C$Z(var8)) ? 1 : 0);
               if (var2 == 0) {
                  break;
               }

               label121:
               if (var10000 == 0) {
                  int var12 = ((var8.isEmpty()) ? 1 : 0);
                  if (var2 != 0) {
                     if (((var12) != 0)) {
                        break label121;
                     }

                     var12 = ((var3.isAir()) ? 1 : 0);
                  }

                  label133: {
                     if (var2 != 0) {
                        if (((var12) != 0)) {
                           break label121;
                        }

                        var13 = var8;
                        if (var2 == 0) {
                           break label133;
                        }

                        var12 = ((var8.is(ItemTags.SWORDS)) ? 1 : 0);
                     }

                     if (var12 != 0 && !(var4 instanceof WebBlock)) {
                        break label121;
                     }

                     var13 = var8;
                  }

                  float var9;
                  var9 = var13.getItem().getDestroySpeed(var8, var3);
                  float var15;
                  var12 = (var15 = var9 - 1.0F) == 0.0F ? 0 : (var15 < 0.0F ? -1 : 1);
                  label101:
                  if (var2 != 0) {
                     if (var12 > 0) {
                        var12 = ((var4 instanceof DropExperienceBlock) ? 1 : 0);
                        if (var2 == 0) {
                           break label101;
                        }

                        if (var12 == 0) {
                           var12 = ((var4 instanceof RedStoneOreBlock) ? 1 : 0);
                           if (var2 == 0) {
                              break label101;
                           }

                           if (var12 == 0) {
                              int var10 = this.P(Enchantments.EFFICIENCY, var8);
                              var12 = var10;
                              if (var2 == 0) {
                                 break label101;
                              }

                              if (var10 > 0) {
                                 var9 += var10 * var10 + 1;
                              }
                           }
                        }
                     }

                     float var16;
                     var12 = (var16 = var9 - var6) == 0.0F ? 0 : (var16 < 0.0F ? -1 : 1);
                  }

                  if (var2 != 0) {
                     if (var12 <= 0) {
                        break label121;
                     }

                     var12 = var7;
                  }

                  var5 = var12;
                  var6 = var9;
               }

               var7++;
            }

            if (var2 != 0) {
               continue;
            }
         }

         float var17;
         var10000 = (var17 = var6 - 1.0F) == 0.0F ? 0 : (var17 < 0.0F ? -1 : 1);
         break;
      }

      if (var2 != 0) {
         var10000 = var10000 > 0 ? var5 : -1;
      }

      return var10000;
   }

   private static Integer deobfLambda$getEnchantLevel$0(ItemStack var0, Reference var1) {
      return EnchantmentHelper.getItemEnchantmentLevel(var1, var0);
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
