package com.elowen.modules.impl.combat.critical;

import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Critical;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.FallingPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.phys.AABB;

public class DoubleDamage implements CriticalMode {
   private Critical P;
   private boolean H;
   private boolean t;
   private float S;
   private static final String a;

   @Override
   public void O(Critical var1) {
      this.P = var1;
   }

   @Override
   public void w() {
      this.t = false;
      this.H = false;
      this.S = 0.0F;
   }

   @Override
   public void x() {
      this.w();
   }

   @Override
   public void f(EventTick var1) {
      this.P.X(a);
   }

   @Override
   public void m(EventMoveInput var1) {
      boolean var2 = Vanilla.K();
      Critical var10000 = this.P;
      if (var2) {
         if (this.P == null) {
            return;
         }

         var10000 = this.P;
      }

      if (var2) {
         if (!var10000.d.w()) {
            return;
         }

         var10000 = this.P;
      }

      LocalPlayer var3 = var10000.d$Minecraft().player;
      if (var3 != null) {
         Minecraft var6 = this.P.d$Minecraft();
         if (var2) {
            if (var6.level == null) {
               return;
            }

            var6 = this.P.d$Minecraft();
         }

         if (var6.options != null) {
            int var7 = ((var3.onGround()) ? 1 : 0);
            if (var2) {
               if (var7 == 0) {
                  return;
               }

               float var11;
               var7 = (var11 = var3.getAttackStrengthScale(0.5F) - 0.95F) == 0.0F ? 0 : (var11 < 0.0F ? -1 : 1);
            }

            label166: {
               if (var2) {
                  if (var7 < 0) {
                     return;
                  }

                  var10000 = this.P;
                  if (!var2) {
                     break label166;
                  }

                  var7 = ((this.P.d$Minecraft().options.keyJump.isDown()) ? 1 : 0);
               }

               if (var7 != 0) {
                  return;
               }

               var10000 = this.P;
            }

            Entity var4 = var10000.u$Entity();
            Entity var9 = var4;
            if (var2) {
               if (var4 == null) {
                  return;
               }

               var9 = var4;
            }

            if (var2) {
               if (var9 == var3) {
                  return;
               }

               var9 = var4;
            }

            if (var2) {
               if (!(var9 instanceof LivingEntity)) {
                  return;
               }

               var9 = var4;
            }

            LivingEntity var5 = (LivingEntity)var9;
            if (var2) {
               int var10 = ((var5.isDeadOrDying()) ? 1 : 0);
               if (var2) {
                  if (var10 != 0) {
                     return;
                  }

                  float var12;
                  var10 = (var12 = var5.getHealth() - 0.0F) == 0.0F ? 0 : (var12 < 0.0F ? -1 : 1);
               }

               if (var2) {
                  if (var10 <= 0) {
                     return;
                  }

                  var10 = var5.hurtTime;
               }

               label108:
               if (var2) {
                  if (var10 > 0) {
                     float var13;
                     var10 = (var13 = this.o$F() - this.S) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
                     if (!var2) {
                        break label108;
                     }

                     if (var10 <= 0) {
                        return;
                     }
                  }

                  float var14;
                  var10 = (var14 = var3.distanceTo(var4) - this.P.J.o$F()) == 0.0F ? 0 : (var14 < 0.0F ? -1 : 1);
               }

               if (var10 <= 0) {
                  var1.A(true);
               }

               return;
            }

            return;
         }
      }
   }

   @Override
   public void K(EventMotion var1) {
      boolean var2 = Vanilla.p();
      if (this.P != null && this.P.d$Minecraft().player != null && this.P.d$Minecraft().level != null) {
         if (this.P.d$Minecraft().player.onGround() && !this.H) {
            this.t = false;
         }

         this.H = this.P.d$Minecraft().player.onGround();
         Entity var3 = this.P.u$Entity();
         if (var3 != null && var3 != this.P.d$Minecraft().player && var3 instanceof LivingEntity var4 && !var4.isDeadOrDying() && !(var4.getHealth() <= 0.0F)) {
            if (!this.t) {
               if (!(this.P.d$Minecraft().player.distanceTo(var3) > this.P.J.o$F())) {
                  if (this.Z(var4)) {
                     this.P.e();
                     this.P.d$Minecraft().gameMode.attack(this.P.d$Minecraft().player, var3);
                     PlayerUtils.s(InteractionHand.MAIN_HAND);
                     this.t = true;
                  }
               }
            }
         }
      }
   }

   private boolean Z(LivingEntity var1) {
      LocalPlayer var9 = null;
      double var11 = 0.0;
      int var10000 = ((Vanilla.K()) ? 1 : 0);
      LocalPlayer var3 = this.P.d$Minecraft().player;
      boolean var2 = (boolean)((var10000) != 0);
      var10000 = ((this.s$Z()) ? 1 : 0);
      if (var2) {
         if (var10000 != 0) {
            return false;
         }

         var10000 = var1.hurtTime;
      }

      label105: {
         label100:
         if (var2) {
            if (var10000 > 0) {
               float var14;
               var10000 = (var14 = this.o$F() - this.S) == 0.0F ? 0 : (var14 < 0.0F ? -1 : 1);
               if (!var2) {
                  break label100;
               }

               if (var10000 <= 0) {
                  return false;
               }
            }

            var9 = var3;
            if (!var2) {
               break label105;
            }

            float var15;
            var10000 = (var15 = var3.getAttackStrengthScale(0.5F) - 0.95F) == 0.0F ? 0 : (var15 < 0.0F ? -1 : 1);
         }

         if (var10000 < 0) {
            return false;
         }

         var9 = var3;
      }

      double var10001;
      label107: {
         double var4 = var9.getDeltaMovement().y;
         double var16;
         var10000 = (var16 = var4 - -0.08) == 0.0 ? 0 : (var16 < 0.0 ? -1 : 1);
         if (var2) {
            if (var10000 < 0) {
               this.S = this.o$F();
               return true;
            }

            var11 = var4;
            var10001 = 0.0;
            if (!var2) {
               break label107;
            }

            double var17;
            var10000 = (var17 = var4 - 0.0) == 0.0 ? 0 : (var17 < 0.0 ? -1 : 1);
         }

         if (var10000 > 0) {
            return false;
         }

         var11 = var4;
         var10001 = 0.08;
      }

      float var6 = (float)(var11 / var10001);
      float var18;
      var10000 = (var18 = var6 - this.P.C.o$F()) == 0.0F ? 0 : (var18 < 0.0F ? -1 : 1);
      if (var2) {
         if (var10000 > 0) {
            return false;
         }

         var10000 = ((this.R((int)(Math.max(0.0F, var6) * 1.3F))) ? 1 : 0);
      }

      if (var2) {
         var10000 = var10000 == 0 ? 1 : 0;
      }

      int var7 = var10000;
      var10000 = var7;
      if (var2) {
         if (var7 != 0) {
            this.S = this.o$F();
         }

         var10000 = var7;
      }

      return (boolean)((var10000) != 0);
   }

   private float o$F() {
      int var10000 = ((Vanilla.p()) ? 1 : 0);
      LocalPlayer var2 = this.P.d$Minecraft().player;
      boolean var1 = (boolean)((var10000) != 0);
      float var3 = var2.getAttackStrengthScale(0.5F);
      float var4 = 0.2F + var3 * var3 * 0.8F;
      var10000 = ((this.s$Z()) ? 1 : 0);
      if (!var1) {
         if (var10000 != 0) {
            return var4;
         }

         double var6;
         var10000 = (byte)((var6 = var2.getDeltaMovement().y - -0.08) == 0.0 ? 0 : (var6 < 0.0 ? -1 : 1));
      }

      if (var10000 < 0) {
         var4 *= 1.5F;
      }

      return var4;
   }

   private boolean s$Z() {
      boolean var10000 = Vanilla.K();
      LocalPlayer var2 = this.P.d$Minecraft().player;
      ClientLevel var3 = this.P.d$Minecraft().level;
      boolean var1 = var10000;
      if (var2 != null && var3 != null) {
         var10000 = var2.onGround();
         if (var1) {
            if (var10000) {
               return true;
            }

            var10000 = this.P.q$Z();
         }

         if (var1) {
            if (var10000) {
               return true;
            }

            var10000 = var2.isFallFlying();
         }

         if (var1) {
            if (!var10000) {
               var10000 = var2.isPassenger();
               if (!var1) {
                  return var10000;
               }

               if (!var10000) {
                  var10000 = var2.isInWater();
                  if (!var1) {
                     return var10000;
                  }

                  if (!var10000) {
                     var10000 = var2.isInLava();
                     if (!var1) {
                        return var10000;
                     }

                     if (!var10000) {
                        var10000 = var2.onClimbable();
                        if (!var1) {
                           return var10000;
                        }

                        if (!var10000) {
                           var10000 = var2.isNoGravity();
                           if (!var1) {
                              return var10000;
                           }

                           label124:
                           if (!var10000) {
                              var10000 = var2.getAbilities().flying;
                              if (var1) {
                                 if (var10000) {
                                    break label124;
                                 }

                                 var10000 = var2.hasEffect(MobEffects.LEVITATION);
                              }

                              if (var1) {
                                 if (!var10000) {
                                    var10000 = var2.hasEffect(MobEffects.BLINDNESS);
                                    if (!var1) {
                                       return var10000;
                                    }

                                    label102:
                                    if (!var10000) {
                                       var10000 = var2.hasEffect(MobEffects.SLOW_FALLING);
                                       if (var1) {
                                          if (var10000) {
                                             break label102;
                                          }

                                          var10000 = this.V(var2, var3);
                                       }

                                       return var10000;
                                    }
                                 }

                                 var10000 = true;
                              }

                              return var10000;
                           }
                        }
                     }
                  }
               }
            }

            var10000 = true;
         }

         return var10000;
      } else {
         return true;
      }
   }

   private boolean V(Entity var1, Level var2) {
      int var10000 = ((Vanilla.p()) ? 1 : 0);
      AABB var4 = var1.getBoundingBox();
      int var5 = Mth.floor(var4.minX);
      boolean var3 = (boolean)((var10000) != 0);

      label64:
      while (true) {
         var10000 = var5;

         label60:
         while (true) {
            if (var10000 < Mth.ceil(var4.maxX)) {
               var10000 = Mth.floor(var4.minY);
               if (var3) {
                  break;
               }

               int var6 = var10000;

               label55:
               do {
                  var10000 = var6;

                  label52:
                  while (true) {
                     if (var10000 >= Mth.ceil(var4.maxY)) {
                        break label55;
                     }

                     var10000 = Mth.floor(var4.minZ);
                     if (var3) {
                        continue label60;
                     }

                     int var7 = var10000;

                     while (true) {
                        if (var7 >= Mth.ceil(var4.maxZ)) {
                           break label52;
                        }

                        var10000 = ((var2.getBlockState(new BlockPos(var5, var6, var7)).getBlock() instanceof WebBlock) ? 1 : 0);
                        if (var3) {
                           break;
                        }

                        if (var3) {
                           return (boolean)((var10000) != 0);
                        }

                        if (var10000 != 0) {
                           return true;
                        }

                        var7++;
                        if (var3) {
                           break label52;
                        }
                     }
                  }

                  var6++;
               } while (!var3);

               var5++;
               if (!var3) {
                  continue label64;
               }
            }

            var10000 = 0;
            break;
         }

         return (boolean)((var10000) != 0);
      }
   }

   private boolean R(int var1) {
      boolean var10000 = Vanilla.K();
      LocalPlayer var3 = this.P.d$Minecraft().player;
      ClientLevel var4 = this.P.d$Minecraft().level;
      boolean var2 = var10000;
      FallingPlayer var5 = new FallingPlayer(var3);
      int var6 = 0;

      while (true) {
         if (var6 < var1) {
            var5.u(1);
            BlockPos var7 = new BlockPos(Mth.floor(var5.O), Mth.floor(var5.q - 1.0E-4), Mth.floor(var5.j));
            if (var2) {
               var10000 = var4.getBlockState(var7).getCollisionShape(var4, var7).isEmpty();
               if (!var2) {
                  break;
               }

               if (!var10000) {
                  return true;
               }

               var6++;
            }

            if (var2) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "Double Damage";
   }
}
