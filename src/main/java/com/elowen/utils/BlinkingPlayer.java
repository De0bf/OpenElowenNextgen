package com.elowen.utils;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.ClientAsset.Texture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlinkingPlayer extends RemotePlayer {
   private final AbstractClientPlayer a;
   private static final String[] b = new String[]{"textures/entity/player/wide/steve.png", "Real Position"};
   public BlinkingPlayer(AbstractClientPlayer var1) {
      super(Minecraft.getInstance().level, new GameProfile(UUID.randomUUID(), "Real Position"));
      this.a = var1;
      this.copyPosition(var1);
      this.noPhysics = true;
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
      this.yHeadRot = var1.yHeadRot;
      this.yBodyRot = var1.yBodyRot;
      this.yHeadRotO = this.yHeadRot;
      this.yBodyRotO = this.yBodyRot;
      Byte var2 = (Byte)var1.getEntityData().get(Player.DATA_PLAYER_MODE_CUSTOMISATION);
      this.entityData.set(Player.DATA_PLAYER_MODE_CUSTOMISATION, var2);
   }

   public boolean d$Z() {
      return this.a.getSkin().body() != null;
   }

   @NotNull
   public Identifier d$Identifier() {
      String var10000 = Vector2f.e();
      PlayerSkin var2 = this.a.getSkin();
      String var1 = var10000;
      PlayerSkin var4 = var2;
      if (var1 == null) {
         if (var2.body() != null) {
            return var2.body().id();
         }

         var4 = DefaultPlayerSkin.get(this.a.getUUID());
      }

      PlayerSkin var3 = var4;
      Texture var5 = var3.body();
      if (var1 == null) {
         if (var5 == null) {
            return Identifier.withDefaultNamespace("textures/entity/player/wide/steve.png");
         }

         var5 = var3.body();
      }

      return var5.id();
   }

   public boolean G$Z() {
      return this.a.getSkin().cape() != null;
   }

   @Nullable
   public Identifier o$Identifier() {
      String var10000 = Vector2f.e();
      PlayerSkin var2 = this.a.getSkin();
      String var1 = var10000;
      Texture var3 = var2.cape();
      if (var1 == null) {
         if (var3 == null) {
            return null;
         }

         var3 = var2.cape();
      }

      return var3.id();
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
