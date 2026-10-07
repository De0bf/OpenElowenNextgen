package com.elowen.modules.impl.player;

import com.elowen.exceptions.NoSuchModuleException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;

class ChestStealer$ContainerSession {
   final AbstractContainerMenu n;
   final ChestStealer$ContainerType x;
   final Set y;
   final boolean J;
   boolean b;
   int P;
   final ChestStealer I;

   ChestStealer$ContainerSession(ChestStealer var1, AbstractContainerMenu var2, ChestStealer$ContainerType var3, boolean var4) {
      Objects.requireNonNull(var1);
      this.I = var1;
      super();
      this.n = var2;
      int var10000 = ChestStealer.m();
      this.x = var3;
      this.J = var4;
      this.y = new HashSet();
      this.b = false;
      int var5 = var10000;
      int var6 = 0;
      int var7 = var1.G(var2);
      int var8 = 0;

      while (true) {
         label77:
         if (var8 < var7) {
            var10000 = ((var2.getSlot(var8).getItem().isEmpty()) ? 1 : 0);
            if (var5 != 0) {
               break;
            }

            label74: {
               if (var5 == 0) {
                  if (var10000 == 0) {
                     break label74;
                  }

                  var10000 = var8;
               }

               var6 = var10000;
               if (var5 == 0) {
                  break label77;
               }
            }

            var8++;
            if (var5 == 0) {
               continue;
            }
         }

         var10000 = ((var1.Y.w()) ? 1 : 0);
         break;
      }

      label63: {
         if (var10000 != 0) {
            this.P = var1.r(var2, 0, var6);
            if (var5 == 0) {
               break label63;
            }
         }

         this.P = var6;
      }

      for (int var9 : var3.c(var2)) {
         label85: {
            ItemStack var10 = var2.getSlot(var9).getItem();
            boolean var13 = var10.isEmpty();
            if (var5 == 0) {
               if (var13) {
                  break label85;
               }

               var13 = var1.U(var10, var2, var3);
            }

            if (var5 == 0 && var13) {
               this.y.add(var9);
            }
         }

         if (var5 != 0) {
            break;
         }
      }
   }

   boolean f$Z() {
      return this.y.isEmpty();
   }

   boolean S$Z() {
      return this.b;
   }

   void i() {
      this.b = true;
   }

   void s$V() {
      this.y.clear();
   }

   void T$V() {
      int var1 = ChestStealer.m();
      ChestStealer$ContainerSession var10000 = this;
      if (var1 == 0) {
         if (this.y.isEmpty()) {
            return;
         }

         var10000 = this;
      }

      for (int var4 : var10000.I.I(new ArrayList(this.y), this.x, this.n, this.P)) {
         this.D(var4);
         if (var1 != 0) {
            break;
         }
      }
   }

   boolean E$Z() {
      int var1 = ChestStealer.d$I();
      int var10000 = ((this.y.isEmpty()) ? 1 : 0);
      if (var1 != 0) {
         if ((var10000 == 0)) {
            ArrayList var2;
            label47: {
               var2 = new ArrayList(this.y);
               ChestStealer$ContainerSession var6 = this;
               if (var1 != 0) {
                  if (this.P >= 0) {
                     var10000 = this.I.R(this.P, var2, this.x, this.n);
                     break label47;
                  }

                  var6 = this;
               }

               var10000 = var6.I.R(0, var2, this.x, this.n);
            }

            int var3;
            label41: {
               var3 = var10000;
               var10000 = var3;
               if (var1 != 0) {
                  if (var3 != -1) {
                     break label41;
                  }

                  var10000 = (Integer)var2.get(0);
               }

               var3 = var10000;
            }

            double var4 = this.I.j(this.P, var3, this.n);
            boolean var9 = this.I.N(false, var4);
            if (var1 != 0) {
               if (!var9) {
                  return false;
               }

               var9 = this.D(var3);
            }

            return var9;
         }

         var10000 = 0;
      }

      return (boolean)((var10000) != 0);
   }

   private boolean D(int var1) {
      int var10000 = ChestStealer.m();
      this.I.c$V();
      int var2 = var10000;
      var10000 = ((this.I.Y.w()) ? 1 : 0);
      if (var2 == 0) {
         if (var10000 != 0) {
            this.P = this.I.r(this.n, this.P, var1);
         }

         var10000 = this.m();
      }

      int var3 = var10000;
      var10000 = var3;
      if (var2 == 0) {
         if (var3 != -1) {
            ChestStealer.v$Minecraft().gameMode.handleContainerInput(this.n.containerId, var1, var3, ContainerInput.SWAP, ChestStealer.R$Minecraft().player);
            this.P = var1;
            this.y.remove(var1);
            return true;
         }

         var10000 = this.U$I();
      }

      int var4 = var10000;
      var10000 = var4;
      if (var2 == 0) {
         if (var4 == -1) {
            return false;
         }

         var10000 = this.y$I();
      }

      int var5 = var10000;
      var10000 = var5;
      int var10001 = -1;
      if (var2 == 0) {
         if (var5 == -1) {
            return false;
         }

         var10000 = var5;
         var10001 = this.I.G(this.n) + 27;
      }

      int var6 = var10000 - var10001;
      boolean var11 = this.I.Y.w();
      if (var2 == 0) {
         if (var11) {
            this.P = this.I.r(this.n, this.P, var4);
         }

         ChestStealer.s$Minecraft().gameMode.handleContainerInput(this.n.containerId, var4, var6, ContainerInput.SWAP, ChestStealer.L().player);
         var11 = this.I.Y.w();
      }

      if (var2 == 0) {
         if (var11) {
            this.P = var4;
         }

         var11 = this.I.Y.w();
      }

      if (var2 == 0) {
         if (var11) {
            this.P = this.I.r(this.n, this.P, var1);
         }

         ChestStealer.O$MC().gameMode.handleContainerInput(this.n.containerId, var1, var6, ContainerInput.SWAP, ChestStealer.G$Minecraft().player);
         this.P = var1;
         this.y.remove(var1);
         var11 = true;
      }

      return var11;
   }

   private int m() {
      int var10000 = ChestStealer.m();
      int var2 = this.I.G(this.n);
      int var3 = var2 + 27;
      int var1 = var10000;
      int var4 = 8;

      while (true) {
         if (var4 >= 0) {
            int var5 = var3 + var4;
            if (var1 == 0) {
               var10000 = var5;
               if (var1 != 0) {
                  break;
               }

               if (var5 < this.n.slots.size()) {
                  int var7 = ((this.n.getSlot(var5).getItem().isEmpty()) ? 1 : 0);
                  if (var1 != 0) {
                     return var7;
                  }

                  if (var7 != 0) {
                     return var4;
                  }
               }

               var4--;
            }

            if (var1 == 0) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   private int U$I() {
      int var10000 = ChestStealer.m();
      int var2 = this.I.G(this.n);
      int var1 = var10000;
      int var3 = var2;
      int var4 = var3 + 27;
      int var5 = var3;

      while (true) {
         if (var5 < var4) {
            var10000 = var5;
            if (var1 != 0) {
               break;
            }

            label47: {
               if (var1 == 0) {
                  if (var5 >= this.n.slots.size()) {
                     break label47;
                  }

                  var10000 = ((this.n.getSlot(var5).getItem().isEmpty()) ? 1 : 0);
               }

               if (var1 != 0) {
                  return var10000;
               }

               if (var10000 != 0) {
                  return var5;
               }
            }

            var5++;
            if (var1 == 0) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   private int y$I() {
      int var10000 = ChestStealer.d$I();
      int var2 = this.I.G(this.n);
      int var3 = var2 + 27;
      int var1 = var10000;
      int var4 = 8;

      while (true) {
         if (var4 >= 0) {
            int var5 = var3 + var4;
            if (var1 != 0) {
               var10000 = var5;
               if (var1 == 0) {
                  break;
               }

               if (var5 < this.n.slots.size()) {
                  int var7 = ((this.n.getSlot(var5).getItem().isEmpty()) ? 1 : 0);
                  if (var1 == 0) {
                     return var7;
                  }

                  if (var7 == 0) {
                     return var5;
                  }
               }

               var4--;
            }

            if (var1 != 0) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
