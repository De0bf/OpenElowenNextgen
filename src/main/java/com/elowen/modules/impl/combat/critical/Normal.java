package com.elowen.modules.impl.combat.critical;

import com.elowen.Elowen;
import com.elowen.events.impl.EventMotion;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventAttack;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.impl.combat.Critical;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class Normal implements CriticalMode {
   private Critical z;
   private boolean p;
   private static final String a;

   @Override
   public void O(Critical var1) {
      this.z = var1;
   }

   @Override
   public void f(EventTick var1) {
      this.z.X(a);
   }

   @Override
   public void w() {
      this.r();
   }

   @Override
   public void x() {
      this.r();
   }

   private void r() {
      boolean var1 = Vanilla.p();
      if (this.p) {
         Elowen.U = 0;
         this.p = false;
      }
   }

   @Override
   public void m(EventMoveInput var1) {
      boolean var2 = Vanilla.K();
      Critical var10000 = this.z;
      if (var2) {
         if (this.z == null) {
            return;
         }

         var10000 = this.z;
      }

      if (var2) {
         if (!var10000.d.w()) {
            return;
         }

         var10000 = this.z;
      }

      Minecraft var4 = var10000.d$Minecraft();
      if (var2) {
         if (var4.player == null) {
            return;
         }

         var4 = this.z.d$Minecraft();
      }

      label106: {
         if (var2) {
            if (var4.level == null) {
               return;
            }

            var10000 = this.z;
            if (!var2) {
               break label106;
            }

            var4 = this.z.d$Minecraft();
         }

         if (var4.options == null) {
            return;
         }

         var10000 = this.z;
      }

      label108: {
         boolean var6 = var10000.q$Z();
         if (var2) {
            if (var6) {
               return;
            }

            var10000 = this.z;
            if (!var2) {
               break label108;
            }

            var6 = this.z.d$Minecraft().options.keyJump.isDown();
         }

         if (var6) {
            return;
         }

         var10000 = this.z;
      }

      Entity var3 = var10000.u$Entity();
      if (var3 != null) {
         int var8 = ((this.z.d$Minecraft().player.onGround()) ? 1 : 0);
         if (var2) {
            if (var8 == 0) {
               return;
            }

            float var9;
            var8 = (byte)((var9 = this.z.d$Minecraft().player.distanceTo(var3) - 3.0F) == 0.0F ? 0 : (var9 < 0.0F ? -1 : 1));
         }

         if (var8 <= 0) {
            var1.A(true);
         }
      }
   }

   @Override
   public void G(EventMoveInput var1) {
      boolean var2 = Vanilla.K();
      Critical var10000 = this.z;
      if (var2) {
         if (this.z == null) {
            return;
         }

         var10000 = this.z;
      }

      Entity var3 = var10000.u$Entity();
      if (var3 != null) {
         int var4 = ((this.z.q$Z()) ? 1 : 0);
         if (var2) {
            if (var4 != 0) {
               this.r();
               return;
            }

            var4 = ((this.z.T(var3)) ? 1 : 0);
         }

         if (var2) {
            if (var4 != 0) {
               this.r();
               return;
            }

            double var6;
            var4 = (var6 = this.z.d$Minecraft().player.getDeltaMovement().y - 0.0) == 0.0 ? 0 : (var6 < 0.0 ? -1 : 1);
         }

         if (var2) {
            if (var4 >= 0) {
               return;
            }

            var4 = ((this.z.d$Minecraft().player.onGround()) ? 1 : 0);
         }

         label80: {
            if (var2) {
               if (var4 != 0) {
                  return;
               }

               var10000 = this.z;
               if (!var2) {
                  break label80;
               }

               float var7;
               var4 = (var7 = this.z.d$Minecraft().player.distanceTo(var3) - this.z.J.o$F()) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1);
            }

            if (var4 > 0) {
               return;
            }

            var10000 = this.z;
         }

         var10000.e();
      }
   }

   @Override
   public void K(EventMotion var1) {
      Normal var6 = null;
      boolean var2 = Vanilla.K();
      Critical var10000 = this.z;
      if (var2) {
         if (this.z == null) {
            return;
         }

         var10000 = this.z;
      }

      Entity var3 = var10000.u$Entity();
      if (var3 != null) {
         int var4 = ((this.z.q$Z()) ? 1 : 0);
         if (var2) {
            if (var4 != 0) {
               this.r();
               return;
            }

            var4 = ((this.z.T(var3)) ? 1 : 0);
         }

         if (var2) {
            if (var4 != 0) {
               this.r();
               return;
            }

            var4 = ((this.z.y$Z()) ? 1 : 0);
         }

         label118: {
            label107:
            if (var2) {
               if (var4 == 0) {
                  double var7;
                  var4 = (byte)((var7 = this.z.d$Minecraft().player.getDeltaMovement().y - 0.0) == 0.0 ? 0 : (var7 < 0.0 ? -1 : 1));
                  if (!var2) {
                     break label107;
                  }

                  if (var4 < 0) {
                     var4 = ((this.z.d$Minecraft().player.onGround()) ? 1 : 0);
                     if (!var2) {
                        break label107;
                     }

                     if (var4 == 0) {
                        float var8;
                        var4 = (byte)((var8 = this.z.d$Minecraft().player.distanceTo(var3) - this.z.J.o$F()) == 0.0F ? 0 : (var8 < 0.0F ? -1 : 1));
                        if (!var2) {
                           break label107;
                        }

                        if (var4 <= 0) {
                           int var5 = ((this.z.I.w()) ? 1 : 0);
                           if (var2) {
                              if (var5 == 0) {
                                 return;
                              }

                              var5 = Elowen.U;
                           }

                           if (var2) {
                              if (var5 > 0) {
                                 return;
                              }

                              var5 = Elowen.U + 1;
                           }

                           Elowen.U = var5;
                           this.p = true;
                           if (var2) {
                              return;
                           }
                        }
                     }
                  }
               }

               var6 = this;
               if (!var2) {
                  break label118;
               }

               var4 = ((this.z.y$Z()) ? 1 : 0);
            }

            if (var4 != 0) {
               return;
            }

            var6 = this;
         }

         var6.r();
      }
   }

   @Override
   public void T(EventAttack var1) {
      boolean var2 = Vanilla.p();
      if (this.z != null) {
         Entity var3 = this.z.u$Entity();
         if (var3 != null && var1.d$Entity() == var3) {
            if (this.z.q$Z()) {
               this.r();
            } else {
               if (this.z.d$Minecraft().player.getDeltaMovement().y < 0.0
                  && !this.z.d$Minecraft().player.onGround()
                  && this.z.d$Minecraft().player.distanceTo(var3) <= this.z.J.o$F()) {
                  this.z.e();
               }

               if (this.z.T(var3)) {
                  this.r();
               }
            }
         }
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      a = "Normal";
   }
}
