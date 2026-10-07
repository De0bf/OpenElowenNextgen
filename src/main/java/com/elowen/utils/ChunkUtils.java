package com.elowen.utils;

import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkUtils {
   private static final Minecraft V;
   private static final String a;

   public static Stream<BlockEntity> R$Stream() {
      return n$Stream().flatMap(ChunkUtils::deobfLambda$getLoadedBlockEntities$0);
   }

   public static Stream<LevelChunk> n$Stream() {
      int var0 = Math.max(2, V.options.getEffectiveRenderDistance()) + 3;
      int var1 = var0 * 2 + 1;
      ChunkPos var2 = V.player.chunkPosition();
      ChunkPos var3 = new ChunkPos(var2.x() - var0, var2.z() - var0);
      ChunkPos var4 = new ChunkPos(var2.x() + var0, var2.z() + var0);
      return Stream.iterate(var3, var10000 -> deobfLambda$getLoadedChunks$0(var3, var4, var10000))
         .limit((long)var1 * var1)
         .filter(ChunkUtils::deobfLambda$getLoadedChunks$1)
         .map(ChunkUtils::deobfLambda$getLoadedChunks$2)
         .filter(Objects::nonNull);
   }

   private static LevelChunk deobfLambda$getLoadedChunks$2(ChunkPos var0) {
      return V.level.getChunk(var0.x(), var0.z());
   }

   private static boolean deobfLambda$getLoadedChunks$1(ChunkPos var0) {
      return V.level.hasChunk(var0.x(), var0.z());
   }

   private static ChunkPos deobfLambda$getLoadedChunks$0(ChunkPos var0, ChunkPos var1, ChunkPos var2) {
      int var4 = var2.x();
      String var10000 = Vector2f.e();
      int var5 = var2.z();
      var4++;
      String var3 = var10000;
      int var7 = var4;
      int var10001 = var0.x();
      if (var3 == null) {
         if (var4 > var10001) {
            var4 = var1.x();
            var5++;
         }

         var7 = var5;
         var10001 = var0.z();
      }

      if (var7 > var10001) {
         throw new IllegalStateException(a);
      } else {
         return new ChunkPos(var4, var5);
      }
   }

   private static Stream<BlockEntity> deobfLambda$getLoadedBlockEntities$0(LevelChunk var0) {
      return var0.getBlockEntities().values().stream();
   }

   static {
      a = "Stream limit didn't work.";
      V = Minecraft.getInstance();
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
