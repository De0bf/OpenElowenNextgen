package com.elowen.modules.impl.player;

import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventMotion;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.mixin.accessors.MinecraftAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import net.minecraft.world.item.BlockItem;

@ModuleInfo(R = "FastPlace", a = "Place blocks faster", M = Category.PLAYER)
public class FastPlace extends Module {
   private final FloatValue U;
   private float i;
   private static final String b;

   public FastPlace() {
      this.U = ValueBuilder.m(this, b).d(10.0F).V(1.0F).w(5.0F).M(20.0F).f$K().L();
      this.i = 0.0F;
   }

   @EventTarget
   public void w(EventMotion var1) {
      FastPlace var4 = null;
      int var2 = ChestStealer.m();
      if (var1.Q() == EventType.PRE) {
         label48: {
            MinecraftAccessor var3 = (MinecraftAccessor)G;
            int var10000 = ((G.options.keyUse.isDown()) ? 1 : 0);
            if (var2 == 0) {
               if (var10000 == 0) {
                  break label48;
               }

               var10000 = ((G.player.getMainHandItem().getItem() instanceof BlockItem) ? 1 : 0);
            }

            label49: {
               if (var2 == 0) {
                  if (var10000 == 0) {
                     break label48;
                  }

                  this.i = this.i + this.U.o$F() / 20.0F;
                  var4 = this;
                  if (var2 != 0) {
                     break label49;
                  }

                  float var5;
                  var10000 = (byte)((var5 = this.i - 1.0F / this.U.o$F()) == 0.0F ? 0 : (var5 < 0.0F ? -1 : 1));
               }

               if (var10000 < 0) {
                  return;
               }

               var3.setRightClickDelay(0);
               var4 = this;
            }

            var4.i--;
            if (var2 == 0) {
               return;
            }
         }

         this.i = 0.0F;
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }

   static {
      b = "CPS";
   }
}
