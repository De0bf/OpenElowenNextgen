package com.elowen.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec2;

public class MoveUtils {
   private static final Minecraft C = Minecraft.getInstance();
   public static boolean I = false;

   private static float i(float var0, float var1) {
      int var14 = 0;
      int var15 = 0;
      String var10000 = Vector2f.e();
      float var3 = C.player.getYRot();
      String var2 = var10000;
      float var18;
      int var10 = (var18 = var0 - 0.0F) == 0.0F ? 0 : (var18 < 0.0F ? -1 : 1);
      if (var2 == null) {
         var10 = var10 > 0 ? 1 : 0;
      }

      int var4 = var10;
      float var19;
      int var11 = (var19 = var0 - 0.0F) == 0.0F ? 0 : (var19 < 0.0F ? -1 : 1);
      if (var2 == null) {
         var11 = var11 < 0 ? 1 : 0;
      }

      int var5 = var11;
      float var20;
      int var12 = (var20 = var1 - 0.0F) == 0.0F ? 0 : (var20 < 0.0F ? -1 : 1);
      if (var2 == null) {
         var12 = var12 > 0 ? 1 : 0;
      }

      int var6 = var12;
      float var21;
      int var13 = (var21 = var1 - 0.0F) == 0.0F ? 0 : (var21 < 0.0F ? -1 : 1);
      if (var2 == null) {
         var13 = var13 < 0 ? 1 : 0;
      }

      int var7;
      var7 = var13;
      var14 = var6;
      label239:
      if (var2 == null) {
         if (var6 == 0) {
            var14 = var7;
            if (var2 != null) {
               break label239;
            }

            if (var7 == 0) {
               var14 = 0;
               break label239;
            }
         }

         var14 = 1;
      }

      int var8;
      var8 = var14;
      var15 = var4;
      label231:
      if (var2 == null) {
         if (var4 == 0) {
            var15 = var5;
            if (var2 != null) {
               break label231;
            }

            if (var5 == 0) {
               var15 = 0;
               break label231;
            }
         }

         var15 = 1;
      }

      int var9 = var15;
      float var22;
      int var16 = (var22 = var0 - 0.0F) == 0.0F ? 0 : (var22 < 0.0F ? -1 : 1);
      if (var2 == null) {
         if (var16 == 0) {
            if (var2 != null) {
               return var1;
            }

            if (var1 == 0.0F) {
               return var3;
            }
         }

         var16 = var5;
      }

      label213:
      if (var2 == null) {
         if (var16 != 0) {
            var16 = var8;
            if (var2 != null) {
               break label213;
            }

            if (var8 == 0) {
               return var3 + 180.0F;
            }
         }

         var16 = var4;
      }

      label205:
      if (var2 == null) {
         if (var16 != 0) {
            var16 = var7;
            if (var2 != null) {
               break label205;
            }

            if (var7 != 0) {
               return var3 + 45.0F;
            }
         }

         var16 = var4;
      }

      label197:
      if (var2 == null) {
         if (var16 != 0) {
            var16 = var6;
            if (var2 != null) {
               break label197;
            }

            if (var6 != 0) {
               return var3 - 45.0F;
            }
         }

         var16 = var9;
      }

      label189:
      if (var2 == null) {
         if (var16 == 0) {
            var16 = var7;
            if (var2 != null) {
               break label189;
            }

            if (var7 != 0) {
               return var3 + 90.0F;
            }
         }

         var16 = var9;
      }

      label181:
      if (var2 == null) {
         if (var16 == 0) {
            var16 = var6;
            if (var2 != null) {
               break label181;
            }

            if (var6 != 0) {
               return var3 - 90.0F;
            }
         }

         var16 = var5;
      }

      if (var2 == null) {
         if (var16 != 0) {
            if (var2 != null) {
               return var7 != 0 ? var3 - 135.0F : var3;
            }

            if (var7 != 0) {
               return var3 + 135.0F;
            }
         }

         var16 = var5;
      }

      return var16 != 0 ? var3 - 135.0F : var3;
   }

   public static void b(com.elowen.events.impl.EventMoveInput var0, float var1) {
      String var2;
      byte var5;
      float var6;
      float var7;
      double var8;
      float var12;
      float var14;
      int var16;
      label71: {
         label74: {
            var12 = var0.I();
            String var10000 = Vector2f.e();
            var14 = var0.g$F();
            var5 = 45;
            var6 = 22.5F;
            var7 = Math.max(Math.abs(var12), Math.abs(var14));
            var8 = MathHelper.N(i(var12, var14) - var1);
            double var10 = Math.abs(var8);
            var2 = var10000;
            var12 = 0.0F;
            var14 = 0.0F;
            double var17;
            var16 = (var17 = var10 - (var5 + var6)) == 0.0 ? 0 : (var17 < 0.0 ? -1 : 1);
            if (var2 == null) {
               if (var16 <= 0) {
                  var12++;
                  if (var2 == null) {
                     break label74;
                  }
               }

               double var18;
               var16 = (var18 = var10 - (180.0F - var5 - var6)) == 0.0 ? 0 : (var18 < 0.0 ? -1 : 1);
            }

            if (var2 != null) {
               break label71;
            }

            if (var16 >= 0) {
               var12--;
            }
         }

         double var19;
         var16 = (var19 = var8 - (var5 - var6)) == 0.0 ? 0 : (var19 < 0.0 ? -1 : 1);
      }

      label75: {
         label59:
         if (var2 == null) {
            if (var16 >= 0) {
               double var20;
               var16 = (var20 = var8 - (180.0F - var5 + var6)) == 0.0 ? 0 : (var20 < 0.0 ? -1 : 1);
               if (var2 != null) {
                  break label59;
               }

               if (var16 <= 0) {
                  var14--;
                  if (var2 == null) {
                     break label75;
                  }
               }
            }

            double var21;
            var16 = (var21 = var8 - (-var5 + var6)) == 0.0 ? 0 : (var21 < 0.0 ? -1 : 1);
         }

         if (var2 == null) {
            if (var16 > 0) {
               break label75;
            }

            double var22;
            var16 = (var22 = var8 - (-180.0F + var5 - var6)) == 0.0 ? 0 : (var22 < 0.0 ? -1 : 1);
         }

         if (var16 >= 0) {
            var14++;
         }
      }

      var12 *= var7;
      var14 *= var7;
      var0.b(var12);
      var0.m(var14);
      if (com.elowen.values.HasValue.X$Z()) {
         Vector2f.M("u9WzE");
      }
   }

   public static boolean V() {
      String var10000 = Vector2f.e();
      Vec2 var1 = C.player.input.getMoveVector();
      String var0 = var10000;
      float var3;
      int var2 = (var3 = var1.x - 0.0F) == 0.0F ? 0 : (var3 < 0.0F ? -1 : 1);
      if (var0 == null) {
         if ((var2 == 0)) {
            float var4;
            int var5 = (var4 = var1.y - 0.0F) == 0.0F ? 0 : (var4 < 0.0F ? -1 : 1);
            if (var0 != null) {
               return (boolean)((var5) != 0);
            }

            if ((var5 == 0)) {
               boolean var6 = C.options.keyJump.isDown();
               if (var0 != null) {
                  return var6;
               }

               if (!var6) {
                  boolean var7 = C.options.keyLeft.isDown();
                  if (var0 != null) {
                     return var7;
                  }

                  if (!var7) {
                     boolean var8 = C.options.keyRight.isDown();
                     if (var0 != null) {
                        return var8;
                     }

                     if (!var8) {
                        boolean var9 = C.options.keyUp.isDown();
                        if (var0 != null) {
                           return var9;
                        }

                        if (!var9) {
                           boolean var10 = C.options.keyDown.isDown();
                           if (var0 != null) {
                              return var10;
                           }

                           if (!var10) {
                              return false;
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = 1;
      }

      return (boolean)((var2) != 0);
   }

   private static com.elowen.exceptions.NoSuchModuleException a(com.elowen.exceptions.NoSuchModuleException var0) {
      return var0;
   }
}
