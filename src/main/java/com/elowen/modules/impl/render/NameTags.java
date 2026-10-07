package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMouseClick;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventRender3D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.misc.Teams;
import com.elowen.ui.notification.NotificationManager;
import com.elowen.ui.notification.NotificationLevel;
import com.elowen.ui.notification.Notification;
import com.elowen.utils.FriendManager;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.ProjectionUtils;
import com.elowen.utils.BlinkingPlayer;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.SkijaEffects;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.SkijaColoredText;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Typeface;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector2f;

@ModuleInfo(R = "NameTags", M = Category.RENDER, a = "Renders name tags")
public class NameTags extends Module {
   public BooleanValue z;
   public BooleanValue h;
   public BooleanValue Z;
   public BooleanValue D;
   public BooleanValue e;
   public FloatValue d;
   private static final int j;
   private static final int B;
   private final SkijaRenderer Q;
   private final Map<Entity, Vector2f> C;
   private final List<NameTags$NameTagData> X;
   private final Map<Player, Integer> T;
   private BlockPos q;
   private Vector2f f;
   private Player J;
   private static final String[] b = new String[]{"Compass Position", "Added ", " as friends!", "HP", "§f | §c", "[NameTags] Render failed: ", "Compass Only", "Shared ESP", "Middle Click Friend", "Removed ", "Compass", " from friends!", "Compass", "No Player Only", "§f | §c", "CIT-", "§aShared§f | ", "§cAiming§f | ", "§aTeam§f | ", "Scale", "§aFriend§f | ", "HP"};
   public NameTags() {
      String[] var1 = b;
      this.z = ValueBuilder.m(this, "Middle Click Friend").h(true).f$K().f$O();
      this.h = ValueBuilder.m(this, "Compass Position").h(true).f$K().f$O();
      this.Z = ValueBuilder.m(this, "Compass Only").h(true).l(this::deobfLambda$new$0).f$K().f$O();
      this.D = ValueBuilder.m(this, "No Player Only").h(true).l(this::deobfLambda$new$1).f$K().f$O();
      this.e = ValueBuilder.m(this, "Shared ESP").h(true).f$K().f$O();
      this.d = ValueBuilder.m(this, "Scale").d(0.3F).V(0.01F).w(0.1F).M(0.5F).f$K().L();
      this.Q = com.elowen.utils.renderer.SkiaRenderManager.X$m();
      this.C = new ConcurrentHashMap<>();
      this.X = new CopyOnWriteArrayList<>();
      this.T = new ConcurrentHashMap<>();
   }

   private boolean R$Z() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null) {
         Iterator var2 = NameTags$NameTagData.level.entitiesForRendering().iterator();
         while (var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if (var3 != NameTags$NameTagData.player && !(var3 instanceof BlinkingPlayer) && var3 instanceof Player) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private BlockPos F(ClientLevel var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 == null ? null : var1.getLevelData().getRespawnData().pos();
   }

   @com.elowen.events.api.EventTarget
   public void O(com.elowen.events.impl.EventMotion var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.Q() == com.elowen.events.api.types.EventType.PRE) {
         if (NameTags$NameTagData.level == null || NameTags$NameTagData.player == null) {
            return;
         }

         if (!this.z.w()) {
            this.J = null;
         }

         Iterator var3 = NameTags$NameTagData.level.players().iterator();
         while (var3.hasNext()) {
            Player var4 = (Player)var3.next();
            label100:
            if (!(var4 instanceof BlinkingPlayer) && var4 != NameTags$NameTagData.player) {
               if (U(var4, NameTags$NameTagData.player.getYRot(), NameTags$NameTagData.player.getXRot())) {
                  this.T.put(var4, this.T.getOrDefault(var4, 0) + 1);
                  if ((Integer)this.T.get(var4) < 10) {
                     break label100;
                  }

                  this.J = var4;
               }

               this.T.put(var4, Math.max(this.T.getOrDefault(var4, 0) - 1, 0));
            }
         }

         if (this.J != null && this.T.getOrDefault(this.J, 0) <= 0) {
            this.J = null;
         }

         this.q = null;
         if (!this.h.w()) {
            return;
         }

         if (this.Z.w() && !InventoryUtils.C(Items.COMPASS)) {
            return;
         }

         if (this.D.w() && this.R$Z()) {
            return;
         }

         this.q = this.F(NameTags$NameTagData.level);
      }
   }

   public static boolean U(Entity var0, float var1, float var2) {
      HasValue[] var3 = Theme.s$ArrQ();
      if (NameTags$NameTagData.player != null && var0 != null) {
         Vec3 var4 = NameTags$NameTagData.player.getEyePosition(1.0F);
         HitResult var5 = com.elowen.utils.rotation.RotationUtils.m(var0.getBoundingBox(), new com.elowen.utils.Vector2f(var1, var2), var4, 150.0);
         return var5 != null && var5.getType() == Type.ENTITY ? var5.getLocation().distanceTo(var4) < 150.0 : false;
      } else {
         return false;
      }
   }

   @com.elowen.events.api.EventTarget
   public void c(EventRender3D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null) {
         try {
            this.O(var1.d$F());
            this.f = null;
            if (this.q != null) {
               Vector2f var3 = ProjectionUtils.g(this.q.getX() + 0.5, this.q.getY() + 1.75, this.q.getZ() + 0.5, var1.d$F());
               if (var3 != null && var3.x != Float.MAX_VALUE && var3.y != Float.MAX_VALUE) {
                  this.f = var3;
               }
            }
         } catch (Exception var4) {
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void q(EventMouseClick var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.player != null && this.J != null) {
         if (var1.v$I() == 2 && !var1.Z() && this.z.w()) {
            String var3 = this.J.getName().getString();
            if (com.elowen.utils.FriendManager.A(this.J)) {
               com.elowen.utils.FriendManager.z(this.J);
               Elowen.S$Elowen().Q().m(new Notification(com.elowen.ui.notification.NotificationLevel.ERROR, "Removed " + var3 + " from friends!", 3000L));
            }

            com.elowen.utils.FriendManager.I(this.J);
            NotificationManager var10000 = Elowen.S$Elowen().Q();
            String[] var4 = b;
            var10000.m(new Notification(com.elowen.ui.notification.NotificationLevel.SUCCESS, "Added " + var3 + " as friends!", 3000L));
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void O(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null && this.w()) {
         try {
            Canvas var3 = this.Q.G$Canvas();
            if (var3 == null) {
               return;
            }

            java.util.ArrayList var4 = new java.util.ArrayList();
            java.util.ArrayList var5 = new java.util.ArrayList();
            if (this.f != null && this.q != null) {
               Vector2f var6 = this.f;
               float var7 = Math.max(80.0F - Mth.sqrt((float)NameTags$NameTagData.player.distanceToSqr(this.q.getX() + 0.5, this.q.getY() + 1.75, this.q.getZ() + 0.5)), 0.0F)
                  * this.d.o$F()
                  / 80.0F;
               if (var7 > 0.0F) {
                  float var8 = var7 * 27.304F;
                  Typeface var9 = this.C(var8);
                  String[] var17 = b;
                  float var10 = SkijaColoredText.V("Compass", var9, var8);
                  float var11 = com.elowen.utils.renderer.Fonts.n(com.elowen.utils.renderer.Fonts.H(var8));
                  var4.add(new float[]{var6.x - var10 / 2.0F - 2.0F, var6.y - 2.0F, var10 + 4.0F, var11 + 2.0F});
                  var5.add(new NameTags$TagDraw(var6.x, var6.y, "Compass", var10, var11, 0.0F, var8, var9));
               }
            }

            for (Entry var23 : this.C.entrySet()) {
               Entity var27 = (Entity)var23.getKey();
               if (var27 != NameTags$NameTagData.player && var27 instanceof Player var31 && !var27.isRemoved()) {
                  Vector2f var35 = (Vector2f)var23.getValue();
                  if (var35 != null && var35.x != Float.MAX_VALUE && var35.y != Float.MAX_VALUE) {
                     String var39 = this.k(var31);
                     float var12 = this.d.o$F() * 27.304F;
                     Typeface var13 = this.C(var12);
                     float var14 = SkijaColoredText.V(var39, var13, var12);
                     float var15 = com.elowen.utils.renderer.Fonts.n(com.elowen.utils.renderer.Fonts.H(var12));
                     float var16 = 1.0F - var31.getHealth() / var31.getMaxHealth();
                     var4.add(new float[]{var35.x - var14 / 2.0F - 2.0F, var35.y - 2.0F, var14 + 4.0F, var15 + 2.0F});
                     var5.add(new NameTags$TagDraw(var35.x, var35.y, var39, var14, var15, var16, var12, var13));
                     break;
                  }
               }
            }

            if (this.e.w()) {
               for (com.elowen.modules.impl.render.NameTags$NameTagData var24 : this.X) {
                  Vector2f var28 = var24.c$Vector2f();
                  if (var28 != null && var28.x != Float.MAX_VALUE && var28.y != Float.MAX_VALUE) {
                     String var10000 = var24.n$String();
                     String[] var46 = b;
                     String var32 = "§aShared§f | " + var10000;
                     float var36 = this.d.o$F() * 27.304F;
                     Typeface var40 = this.C(var36);
                     float var43 = SkijaColoredText.V(var32, var40, var36);
                     float var44 = com.elowen.utils.renderer.Fonts.n(com.elowen.utils.renderer.Fonts.H(var36));
                     float var45 = (float)(1.0 - var24.y$D() / var24.G$D());
                     var4.add(new float[]{var28.x - var43 / 2.0F - 2.0F, var28.y - 2.0F, var43 + 4.0F, var44 + 2.0F});
                     var5.add(new NameTags$TagDraw(var28.x, var28.y, var32, var43, var44, var45, var36, var40));
                     break;
                  }
               }
            }

            if (!var4.isEmpty()) {
               float var21 = Float.MAX_VALUE;
               float var25 = Float.MAX_VALUE;
               float var29 = -Float.MAX_VALUE;
               float var33 = -Float.MAX_VALUE;
               Iterator var37 = var4.iterator();
               while (var37.hasNext()) {
                  float[] var41 = (float[])var37.next();
                  var21 = Math.min(var21, var41[0]);
                  var25 = Math.min(var25, var41[1]);
                  var29 = Math.max(var29, var41[0] + var41[2]);
                  var33 = Math.max(var33, var41[1] + var41[3]);
               }

               com.elowen.utils.renderer.SkijaEffects.T(this.Q, var3, var21, var25, var29 - var21, var33 - var25, 2.0F, var4);
            }

            Iterator var22 = var5.iterator();
            while (var22.hasNext()) {
               NameTags$TagDraw var26 = (NameTags$TagDraw)var22.next();
               float var30 = var26.p - var26.L / 2.0F - 2.0F;
               float var34 = var26.C - 2.0F;
               float var38 = var26.L + 4.0F;
               float var42 = var26.n + 2.0F;
               this.Q.d(var30, var34, var38, var42, 0.0F, j);
               this.Q.d(var30, var34, var38 * (1.0F - var26.q), var42, 0.0F, B);
               this.Q.q(var26.Z, var26.p - var26.L / 2.0F, var26.C - 1.0F, var26.r, var26.t, -855638017);
            }

            this.Q.p();
            this.Q.G$V();
         } catch (RuntimeException var18) {
            System.err.println("[NameTags] Render failed: " + var18);
         }
      }
   }

   private String k(Player var1) {
      HasValue[] var2;
      StringBuilder var3;
      Player var5;
      label52: {
         HasValue[] var10000 = Theme.s$ArrQ();
         var3 = new StringBuilder();
         var2 = var10000;
         boolean var4 = Teams.T(var1);
         if (var2 != null) {
            if (var4) {
               var3.append("§aTeam§f | ");
            }

            var5 = var1;
            if (var2 == null) {
               break label52;
            }

            var4 = com.elowen.utils.FriendManager.A(var1);
         }

         if (var4) {
            var3.append("§aFriend§f | ");
         }

         var5 = this.J;
      }

      if (var2 != null) {
         if (var5 == var1) {
            var3.append("§cAiming§f | ");
         }

         var3.append(var1.getName().getString());
         var3.append("§f | §c").append(Math.round(var1.getHealth()));
         if (var2 == null) {
            return var3.toString();
         }

         var5 = var1;
      }

      if (var5.getAbsorptionAmount() > 0.0F) {
         var3.append("+").append(Math.round(var1.getAbsorptionAmount()));
      }

      var3.append("HP");
      return var3.toString();
   }

   private Typeface C(float var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      Typeface var3 = com.elowen.utils.renderer.Fonts.H(var1).getTypeface();
      HasValue[] var2 = var10000;
      Typeface var4 = var3;
      if (var2 != null) {
         var4 = var3 != null ? var3 : this.Q.c$Typeface();
      }

      return var4;
   }

   private void O(float var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (NameTags$NameTagData.level != null && NameTags$NameTagData.player != null) {
         this.C.clear();
         this.X.clear();

         for (Entity var4 : NameTags$NameTagData.level.entitiesForRendering()) {
            if (var4 instanceof Player var5 && var4 != NameTags$NameTagData.player && !var4.isRemoved()) {
               String var10000 = var4.getName().getString();
               String[] var17 = b;
               if (!var10000.startsWith("CIT-")) {
                  double var6 = com.elowen.utils.MathUtils.X(var1, var4.xOld, var4.getX());
                  double var8 = com.elowen.utils.MathUtils.X(var1, var4.yOld, var4.getY()) + var4.getBbHeight() + 0.5;
                  double var10 = com.elowen.utils.MathUtils.X(var1, var4.zOld, var4.getZ());
                  Vector2f var12 = ProjectionUtils.g(var6, var8, var10, var1);
                  if (var12 != null && var12.x != Float.MAX_VALUE && var12.y != Float.MAX_VALUE) {
                     var12.y -= 2.0F;
                     this.C.put(var4, var12);
                     break;
                  }
               }
            }
         }

         if (this.e.w()) {
            Map<String, com.elowen.utils.SharedESPData> var18 = com.elowen.utils.EntityWatcher.f$Map();

            for (com.elowen.utils.SharedESPData var20 : var18.values()) {
               double var21 = var20.n$D();
               double var22 = var20.X$D() + NameTags$NameTagData.player.getBbHeight() + 0.5;
               double var23 = var20.I();
               Vector2f var24 = ProjectionUtils.g(var21, var22, var23, var1);
               if (var24 != null && var24.x != Float.MAX_VALUE && var24.y != Float.MAX_VALUE) {
                  var24.y -= 2.0F;
                  String var26 = var20.R$String();
                  String var14 = var20.P() > 0.0 ? "+" + Math.round(var20.P()) : "";
                  long var15 = Math.round(var20.g$D());
                  String[] var25 = b;
                  String var13 = var26 + "§f | §c" + var15 + var14 + "HP";
                  this.X.add(new NameTags$NameTagData(var13, var20.g$D(), var20.M(), var20.P(), new Vec3(var21, var22, var23), var24));
                  break;
               }
            }
         }
      }
   }

   private Boolean deobfLambda$new$1() {
      return this.h.w();
   }

   private Boolean deobfLambda$new$0() {
      return this.h.w();
   }

   static {
      j = com.elowen.utils.renderer.RenderUtils.O(0, 0, 0, 40);
      B = com.elowen.utils.renderer.RenderUtils.O(0, 0, 0, 80);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
