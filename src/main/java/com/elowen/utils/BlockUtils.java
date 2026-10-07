package com.elowen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockUtils {
   private static final Minecraft g = Minecraft.getInstance();

   public static AABB l(BlockPos var0) {
      return u(var0).bounds().move(var0);
   }

   private static VoxelShape u(BlockPos var0) {
      return c(var0).getShape(g.level, var0);
   }

   public static BlockState c(BlockPos var0) {
      return g.level.getBlockState(var0);
   }

   public static boolean N(BlockPos var0) {
      return u(var0) != Shapes.empty();
   }

   public static boolean f(BlockPos var0) {
      String var1 = Vector2f.e();
      if (g.level != null && g.player != null) {
         Block var2 = g.level.getBlockState(var0).getBlock();
         return var2 instanceof AirBlock;
      } else {
         return false;
      }
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
