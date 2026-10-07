package com.elowen.modules.impl.render;

import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventRender3D;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.player.ChestStealer;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.Colors;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.SkijaFonts;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Typeface;
import java.awt.Color;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import org.joml.Vector2f;

@ModuleInfo(R = "ItemTags", a = "Show item tags.", M = Category.RENDER)
public class ItemTags extends Module {
   private final ConcurrentHashMap<ItemEntity, Vector2f> V = new ConcurrentHashMap<>();
   private final SkijaRenderer r = SkiaRenderManager.X$m();
   public FloatValue m;
   public BooleanValue e;
   public BooleanValue j;
   public BooleanValue c;
   public BooleanValue q;
   public BooleanValue X;
   public BooleanValue z;
   public BooleanValue T;
   public BooleanValue P;
   private static final String[] b = new String[]{"Diamond", "Gold", "Golden Apple", "Scale", "All Items", " * ", "Iron", "Ender Pearl", "[ItemTags] Render failed: ", "Useful Item", "God Items"};
   public ItemTags() {
      String[] var1 = b;
      this.m = com.elowen.values.ValueBuilder.m(this, "Scale").d(0.25F).V(0.01F).w(0.1F).M(0.5F).f$K().L();
      this.e = com.elowen.values.ValueBuilder.m(this, "All Items").h(false).f$K().f$O();
      this.j = com.elowen.values.ValueBuilder.m(this, "God Items").h(true).l(this::deobfLambda$new$0).f$K().f$O();
      this.c = com.elowen.values.ValueBuilder.m(this, "Diamond").h(true).l(this::deobfLambda$new$1).f$K().f$O();
      this.q = com.elowen.values.ValueBuilder.m(this, "Gold").h(true).l(this::deobfLambda$new$2).f$K().f$O();
      this.X = com.elowen.values.ValueBuilder.m(this, "Iron").h(true).l(this::deobfLambda$new$3).f$K().f$O();
      this.z = com.elowen.values.ValueBuilder.m(this, "Ender Pearl").h(true).l(this::deobfLambda$new$4).f$K().f$O();
      this.T = com.elowen.values.ValueBuilder.m(this, "Golden Apple").h(true).l(this::deobfLambda$new$5).f$K().f$O();
      this.P = com.elowen.values.ValueBuilder.m(this, "Useful Item").h(true).l(this::deobfLambda$new$6).f$K().f$O();
   }

   private static String C(ItemEntity var0) {
      ItemStack var1 = var0.getItem();
      return var1.getDisplayName().getString() + " * " + var1.getCount();
   }

   private boolean H(ItemStack var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1 == null) {
         return false;
      }

      if (var1.isEmpty()) {
         return false;
      }

      if (this.e.w()) {
         return true;
      }

      if (this.j.w()) {
         if (com.elowen.utils.InventoryUtils.M(var1)) {
            return true;
         }

         if (com.elowen.utils.InventoryUtils.Q(var1)) {
            return true;
         }

         if (com.elowen.utils.InventoryUtils.X(var1)) {
            return true;
         }
      }

      if (this.c.w() && var1.getItem() == Items.DIAMOND) {
         return true;
      }

      if (this.q.w() && var1.getItem() == Items.GOLD_INGOT) {
         return true;
      }

      if (this.X.w() && var1.getItem() == Items.IRON_INGOT) {
         return true;
      }

      if (this.z.w() && var1.getItem() == Items.ENDER_PEARL) {
         return true;
      }

      if (this.T.w() && var1.getItem() == Items.GOLDEN_APPLE) {
         return true;
      }

      if (this.P.w()) {
         if (var1.getItem() instanceof BlockItem && var1.getCount() < 8) {
            return false;
         }

         if ((var1.getItem() instanceof SnowballItem || var1.getItem() instanceof EggItem) && var1.getCount() < 3) {
            return false;
         }

         if (ChestStealer.e$Z(var1)) {
            return true;
         }
      }

      return false;
   }

   private boolean D(ItemStack var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (com.elowen.utils.InventoryUtils.M(var1)) {
         return true;
      } else if (com.elowen.utils.InventoryUtils.Q(var1)) {
         return true;
      } else {
         return com.elowen.utils.InventoryUtils.Z(var1) ? true : com.elowen.utils.InventoryUtils.X(var1);
      }
   }

   private void W(float var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      this.V.clear();
      HasValue[] var2 = var10000;
      Minecraft var15 = G;
      if (var2 != null) {
         if (G.level == null) {
            return;
         }

         var15 = G;
      }

      if (var2 != null) {
         if (var15.player == null) {
            return;
         }

         var15 = G;
      }

      Camera var3 = var15.gameRenderer.mainCamera();
      if (var3.isInitialized()) {
         WorldProjector var4 = new WorldProjector(var3, G.getWindow().getGuiScaledWidth(), G.getWindow().getGuiScaledHeight(), com.elowen.utils.renderer.ViewBob.g$Matrix4f());

         for (Entity var6 : G.level.entitiesForRendering()) {
            Entity var16 = var6;
            if (var2 != null) {
               if (!(var6 instanceof ItemEntity)) {
                  continue;
               }

               var16 = var6;
            }

            ItemEntity var7 = (ItemEntity)var16;
            if (this.H(var7.getItem())) {
               double var8 = com.elowen.utils.MathUtils.X(var1, var6.xOld, var6.getX());
               double var10 = com.elowen.utils.MathUtils.X(var1, var6.yOld, var6.getY()) + var6.getBbHeight() + 0.5;
               double var12 = com.elowen.utils.MathUtils.X(var1, var6.zOld, var6.getZ());
               float[] var14 = var4.p(var8, var10, var12);
               if (var2 != null) {
                  if (var14 == null) {
                     continue;
                  }

                  this.V.put(var7, new Vector2f(var14[0], var14[1] - 2.0F));
               }

               if (var2 == null) {
                  break;
               }
            }
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void o(EventRender3D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (G.level != null && G.player != null && this.w()) {
         try {
            this.W(var1.d$F());
         } catch (Exception var4) {
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void J(EventRender2D var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (G.level != null && G.player != null && this.w()) {
         if (!this.V.isEmpty()) {
            try {
               this.r.G$Canvas();
               float var3 = this.m.o$F() * 27.304F;
               Typeface var4 = SkijaFonts.R(com.elowen.utils.renderer.SkijaFonts$FontType.HARMONY);
               if (var4 == null) {
                  var4 = this.r.c$Typeface();
               }

               Font var5 = SkijaFonts.j(var4, var3);
               float var6 = this.r.G$I();
               float var7 = this.r.q$I();

               for (Entry var9 : this.V.entrySet()) {
                  ItemEntity var10 = (ItemEntity)var9.getKey();
                  if (var10 != null) {
                     Vector2f var11 = (Vector2f)var9.getValue();
                     String var12 = C(var10);
                     float var13 = var5.measureTextWidth(SkijaFonts.b(var12));
                     float var14 = var11.x - var13 / 2.0F;
                     float var15 = var11.y - 12.0F;
                     if (!(var14 + var13 < 0.0F) && !(var14 > var6) && !(var15 < -20.0F) && !(var15 > var7 + 20.0F)) {
                        int var16 = this.D(var10.getItem()) ? Colors.y(Color.RED) : Colors.y(Color.WHITE);
                        this.r.Y(var12, var14, var15, var4, var3, var16);
                        break;
                     }
                  }
               }

               this.r.p();
               this.r.G$V();
            } catch (RuntimeException var17) {
               System.err.println("[ItemTags] Render failed: " + var17);
            }
         }
      }
   }

   private Boolean deobfLambda$new$6() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$5() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$4() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$3() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$2() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$1() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private Boolean deobfLambda$new$0() {
      HasValue[] var1 = Theme.s$ArrQ();
      return !this.e.w();
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
