package com.elowen.modules.impl.render;

import com.elowen.Elowen;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRenderHand;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.joml.Quaternionf;

@ModuleInfo(R = "Animations", a = "Old versions hitting animations with multiple styles", M = Category.RENDER)
public class Animations extends Module {
   public final ModeValue I;
   public final FloatValue y;
   public final FloatValue o;
   public final FloatValue r;
   public final FloatValue b;
   public final FloatValue Q;
   public final BooleanValue R;
   public final FloatValue C;
   public final BooleanValue E;
   public final BooleanValue j;
   public final BooleanValue P;
   private boolean J;
   private static final String[] c = new String[]{"Punch", "Push", "Equipped Progress", "Swang", "None", "Hide offhand", "Punch", "Swang", "Swong", "Hand Gap", "Sile", "Smooth", "Vanilla", "Aura Auto Block", "Position Y", "Custom Equipped Progress", "Only Aura", "Push", "Swing", "None", "Swing", "Swaing", "Block Mode", "Swing Speed", "Sile", "1.7", "Position Z", "Swaing", "Swong", "Position X", "1.7", "Smooth"};
   public Animations() {
      String[] var2 = c;
      this.I = ValueBuilder.m(this, "Block Mode")
         .W(new String[]{"Vanilla", "Smooth", "1.7", "Push", "Swing", "Swang", "Swong", "Swaing", "Punch", "Sile", "None"})
         .m(2)
         .f$K()
         .T$t();
      this.y = ValueBuilder.m(this, "Position X").d(0.0F).w(-1.0F).M(1.0F).V(0.01F).f$K().L();
      this.o = ValueBuilder.m(this, "Position Y").d(0.0F).w(-1.0F).M(1.0F).V(0.01F).f$K().L();
      this.r = ValueBuilder.m(this, "Position Z").d(0.0F).w(-1.0F).M(1.0F).V(0.01F).f$K().L();
      this.b = ValueBuilder.m(this, "Hand Gap").d(0.0F).w(-1.0F).M(1.0F).V(0.01F).f$K().L();
      this.Q = ValueBuilder.m(this, "Swing Speed").d(1.0F).w(0.1F).M(2.0F).V(0.01F).f$K().L();
      this.R = ValueBuilder.m(this, "Custom Equipped Progress").h(false).f$K().f$O();
      Theme.s$ArrQ();
      this.C = ValueBuilder.m(this, "Equipped Progress").d(1.0F).V(0.01F).w(0.0F).M(1.0F).l(this::deobfLambda$new$0).f$K().L();
      this.E = ValueBuilder.m(this, "Hide offhand").h(false).f$K().f$O();
      this.j = ValueBuilder.m(this, "Aura Auto Block").h(true).f$K().f$O();
      this.P = ValueBuilder.m(this, "Only Aura").h(false).f$K().f$O();
      this.J = false;
      if (!com.elowen.values.HasValue.x()) {
         Theme.U(new HasValue[4]);
      }
   }

   @com.elowen.events.api.EventTarget
   public void I(EventRenderHand var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      if (var1.J$InteractionHand() == InteractionHand.OFF_HAND) {
         if (this.E.w() && this.N()) {
            var1.v(true);
         }
      }
   }

   @com.elowen.events.api.EventTarget
   public void J(com.elowen.events.impl.EventMotion var1) {
      if (var1.Q() == EventType.PRE && NameTags$NameTagData.player != null) {
         this.L();
      }
   }

   public boolean v(InteractionHand var1) {
      HasValue[] var2 = Theme.s$ArrQ();
      return var1 == InteractionHand.MAIN_HAND && this.N();
   }

   public void I(PoseStack var1, float var2, float var3, int var4) {
      HasValue[] var10000 = Theme.s$ArrQ();
      float var6 = var2;
      HasValue[] var5 = var10000;
      Animations var7 = this;
      if (var5 != null) {
         if (this.R.w()) {
            var6 *= this.C.o$F();
         }

         var7 = this;
      }

      var7.q(var1, var6, var3, var4);
   }

   public void N(PoseStack var1, HumanoidArm var2, InteractionHand var3) {
      float var4 = this.b.o$F();
      if (!(Math.abs(var4) < 1.0E-4F)) {
         HumanoidArm var5 = var3 == InteractionHand.MAIN_HAND ? var2 : var2.getOpposite();
         var1.translate((var5 == HumanoidArm.RIGHT ? 1 : -1) * var4, 0.0F, 0.0F);
      }
   }

   public int F(int var1) {
      HasValue[] var10000 = Theme.s$ArrQ();
      float var3 = this.Q.o$F();
      HasValue[] var2 = var10000;
      float var5;
      int var4 = (var5 = Math.abs(var3 - 1.0F) - 0.001F) == 0.0F ? 0 : (var5 < 0.0F ? -1 : 1);
      if (var2 != null) {
         if (var4 < 0) {
            return var1;
         }

         var4 = Mth.clamp(Math.round(var1 / var3), 1, 100);
      }

      return var4;
   }

   private void L() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (NameTags$NameTagData.player != null) {
         boolean var2 = NameTags$NameTagData.options.keyUse.isDown();
         if (this.j.w() && this.H()) {
            var2 = true;
         }

         if (this.P.w()) {
            var2 = this.H();
         }

         this.J = var2;
      }
   }

   private boolean N() {
      HasValue[] var1 = Theme.s$ArrQ();
      if (this.w() && !this.I.t("None")) {
         LocalPlayer var2 = NameTags$NameTagData.player;
         if (var2 == null) {
            return false;
         }

         if (!var2.getMainHandItem().is(net.minecraft.tags.ItemTags.SWORDS)) {
            return false;
         }

         boolean var3 = false;
         if (var2.isUsingItem() && var2.getUsedItemHand() == InteractionHand.OFF_HAND) {
            ItemStack var4 = var2.getOffhandItem();
            if (var4.getUseAnimation() != ItemUseAnimation.BLOCK) {
               var3 = true;
            }
         }

         boolean var5 = this.j.w() && this.H();
         if (this.P.w()) {
            return var5;
         } else if (var5) {
            return true;
         } else {
            return var3 ? false : NameTags$NameTagData.options.keyUse.isDown() || this.J;
         }
      } else {
         return false;
      }
   }

   private boolean H() {
      HasValue[] var1 = Theme.s$ArrQ();

      try {
         Aura var2 = (Aura)Elowen.S$Elowen().q$ModuleManager().A(Aura.class);
         return var2 != null && var2.w() && Aura.cj instanceof LivingEntity;
      } catch (Exception var3) {
         return false;
      }
   }

   private void q(PoseStack var1, float var2, float var3, int var4) {
      HasValue[] var5;
      int var8;
      String var6 = this.I.C();
      HasValue[] var10000 = Theme.s$ArrQ();
      byte var7 = -1;
      var5 = var10000;
      var8 = var6.hashCode();
      label162:
      if (var5 != null) {
         switch (var8) {
            case 48570:
               var8 = ((var6.equals("1.7")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 0;
               if (var5 != null) {
                  break;
               }
            case 2499386:
               var8 = ((var6.equals("Push")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 1;
               if (var5 != null) {
                  break;
               }
            case 80301790:
               var8 = ((var6.equals("Swing")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 2;
               if (var5 != null) {
                  break;
               }
            case 80294102:
               var8 = ((var6.equals("Swang")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 3;
               if (var5 != null) {
                  break;
               }
            case 80307556:
               var8 = ((var6.equals("Swong")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 4;
               if (var5 != null) {
                  break;
               }
            case -1805854619:
               var8 = ((var6.equals("Swaing")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 5;
               if (var5 != null) {
                  break;
               }
            case 77476110:
               var8 = ((var6.equals("Punch")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 6;
               if (var5 != null) {
                  break;
               }
            case 2577007:
               var8 = ((var6.equals("Sile")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 == 0) {
                  break;
               }

               var7 = 7;
               if (var5 != null) {
                  break;
               }
            case -1814666802:
               var8 = ((var6.equals("Smooth")) ? 1 : 0);
               if (var5 == null) {
                  break label162;
               }

               if (var8 != 0) {
                  var7 = 8;
               }
         }

         var8 = var7;
      }

      switch (var8) {
         case 0:
            this.W(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 1:
            this.j(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 2:
            this.H(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 3:
            this.P(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 4:
            this.l(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 5:
            this.T(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 6:
            this.k(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 7:
            this.a(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         case 8:
            this.x(var1, var2, var3, var4);
            if (var5 != null) {
               break;
            }
         default:
            this.s(var1, var2, var3, var4);
      }
   }

   private void W(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F, -0.72F);
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      float var6 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      var1.rotate(Axis.YP.rotation(var4 * (45.0F + var5 * -20.0F) * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * var6 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.XP.rotation(var6 * -80.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.YP.rotation(var4 * -45.0F * (float) Math.PI / 180.0F));
      var1.scale(0.9F, 0.9F, 0.9F);
      var1.translate(-0.2F, 0.126F, 0.2F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 15.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 80.0F * (float) Math.PI / 180.0F));
   }

   private void j(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 * -0.6F, -0.82F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      float var6 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      var1.rotate(Axis.XP.rotation(var5 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.YP.rotation(var5 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var5 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.XP.rotation(var6 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.YP.rotation(var6 * -10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var6 * -10.0F * (float) Math.PI / 180.0F));
   }

   private void H(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 / 2.0F * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      float var6 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      var1.rotate(Axis.YP.rotation(var5 * -20.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var6 * -20.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.XP.rotation(var6 * -40.0F * (float) Math.PI / 180.0F));
   }

   private void P(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 / 2.0F * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      float var5 = Mth.sqrt(var3);
      float var6 = Mth.sin(var5 * (float) Math.PI);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      var1.rotate(
         new Quaternionf()
            .rotateXYZ((float)Math.toRadians(var6 * 40.0F), (float)Math.toRadians(-var6 * 30.0F / 2.0F), (float)Math.toRadians(var6 * 30.0F / 2.0F))
      );
   }

   private void l(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 / 2.0F * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      var1.rotate(
         new Quaternionf()
            .rotateXYZ((float)Math.toRadians(-var5 * 30.0F), (float)Math.toRadians(var5 * 40.0F / 2.0F), (float)Math.toRadians(-var5 * 40.0F / 2.0F))
      );
   }

   private void T(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 / 2.0F * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      var1.rotate(new Quaternionf().rotateXYZ((float)Math.toRadians(-var5 * 30.0F), (float)Math.toRadians(-var5 / 19.0F), (float)Math.toRadians(var5 / 20.0F)));
   }

   private void k(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      float var5 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      var1.translate(0.1F, 0.2F, 0.3F);
      var1.rotate(Axis.XP.rotation(-var5 * 10.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.YP.rotation(-var5 * 30.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(-var5 * 30.0F * (float) Math.PI / 180.0F));
   }

   private void a(PoseStack var1, float var2, float var3, int var4) {
      HasValue[] var10000 = Theme.s$ArrQ();
      var1.translate(var4 * 0.56F, -0.52F + var2 * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      float var6 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      float var7 = Mth.sin(var3 * var3 * (float) Math.PI);
      var1.rotate(new Quaternionf().rotateXYZ((float)Math.toRadians(-var6 * 55.0F), (float)Math.toRadians(var4 * -var7 * 35.0F), 0.0F));
      HasValue[] var5 = var10000;
      var1.scale(0.92F, 0.92F, 0.92F);
      float var10001 = var4;
      float var10002 = 0.08F;
      if (var5 != null) {
         var10001 *= 0.08F;
         var10002 = NameTags$NameTagData.player.isCrouching() ? -0.06F : -0.12F;
      }

      var1.translate(var10001, var10002, 0.18F);
   }

   private void x(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
      float var5 = Mth.sin(var3 * var3 * (float) Math.PI);
      float var6 = Mth.sin(Mth.sqrt(var3) * (float) Math.PI);
      var1.rotate(Axis.YP.rotation(var5 * -20.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var6 * -20.0F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.XP.rotation(var6 * -80.0F * (float) Math.PI / 180.0F));
   }

   private void s(PoseStack var1, float var2, float var3, int var4) {
      var1.translate(var4 * 0.56F, -0.52F + var2 * -0.6F, -0.72F);
      var1.translate(var4 * -0.1414214F, 0.08F, 0.1414214F);
      var1.rotate(Axis.XP.rotation(-1.7845992F));
      var1.rotate(Axis.YP.rotation(var4 * 13.365F * (float) Math.PI / 180.0F));
      var1.rotate(Axis.ZP.rotation(var4 * 78.05F * (float) Math.PI / 180.0F));
   }

   private Boolean deobfLambda$new$0() {
      return this.R.w();
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
   }
}
