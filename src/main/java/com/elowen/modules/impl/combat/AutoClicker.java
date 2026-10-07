package com.elowen.modules.impl.combat;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.mixin.accessors.MinecraftAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "AutoClicker", a = "Automatically clicks for you", M = Category.COMBAT)
public class AutoClicker extends Module {
   private final FloatValue d;
   private final BooleanValue K;
   private float r;
   private static final String[] b = new String[]{"CPS", "Item Check"};
   public AutoClicker() {
      String[] var1 = b;
      this.d = ValueBuilder.m(this, "CPS").d(10.0F).V(1.0F).w(5.0F).M(20.0F).f$K().L();
      this.K = ValueBuilder.m(this, "Item Check").h(true).f$K().f$O();
      this.r = 0.0F;
   }

   @EventTarget
   public void C(EventMotion var1) {
      boolean var2 = Velocity.o$Z();
      if (var1.Q() == EventType.PRE) {
         MinecraftAccessor var3 = (MinecraftAccessor)G;
         ItemStack var4 = G.player.getMainHandItem();
         if (G.options.keyAttack.isDown() && (var4.is(ItemTags.SWORDS) || var4.is(ItemTags.AXES) || !this.K.w()) && G.hitResult.getType() != Type.BLOCK) {
            this.r = this.r + this.d.o$F() / 20.0F;
            if (!(this.r >= 1.0F / this.d.o$F())) {
               return;
            }

            var3.setMissTime(0);
            G.options.keyAttack.setDown(true);
            G.options.keyAttack.setDown(false);
            this.r--;
         }

         this.r = 0.0F;
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
   }
}
