package com.elowen.modules.impl.player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;

enum ChestStealer$ContainerType {
   CHEST {
      @Override
      public List<Integer> c(AbstractContainerMenu var1) {
         return IntStream.range(0, ((ChestMenu)var1).getRowCount() * 9).boxed().collect(Collectors.toList());
      }
   },
   FURNACE {
      @Override
      public List<Integer> c(AbstractContainerMenu var1) {
         return IntStream.range(0, 3).boxed().collect(Collectors.toList());
      }
   },
   BREWING {
      @Override
      public List<Integer> c(AbstractContainerMenu var1) {
         return IntStream.range(0, 5).boxed().collect(Collectors.toList());
      }
   };

   public abstract List<Integer> c(AbstractContainerMenu var1);
}
