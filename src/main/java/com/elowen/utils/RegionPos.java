package com.elowen.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

public class RegionPos {
   private int S;
   private int P;

   public static RegionPos U(BlockPos var0) {
      return new RegionPos(var0.getX() >> 9 << 9, var0.getZ() >> 9 << 9);
   }

   public static RegionPos W(ChunkPos var0) {
      return new RegionPos(var0.x() >> 5 << 9, var0.z() >> 5 << 9);
   }

   public RegionPos P() {
      return new RegionPos(-this.S, -this.P);
   }

   public Vec3 g$Vec3() {
      return new Vec3(this.S, 0.0, this.P);
   }

   public BlockPos x() {
      return new BlockPos(this.S, 0, this.P);
   }

   public RegionPos(int var1, int var2) {
      this.S = var1;
      this.P = var2;
   }
}
