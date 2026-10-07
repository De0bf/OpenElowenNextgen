package com.elowen.utils.rotation;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventUseItemRayTrace;
import com.elowen.exceptions.NoSuchModuleException;
import com.elowen.modules.Module;
import com.elowen.modules.impl.combat.AutoRod;
import com.elowen.modules.impl.combat.AttackCrystal;
import com.elowen.modules.impl.combat.Aura;
import com.elowen.modules.impl.combat.Velocity;
import com.elowen.modules.impl.combat.velocity.Reduce;
import com.elowen.modules.impl.move.AutoMLG;
import com.elowen.modules.impl.move.LongJump;
import com.elowen.modules.impl.move.Scaffold;
import com.elowen.utils.Wrapper;
import com.elowen.utils.Vector2f;
import com.elowen.utils.MoveUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RotationManager implements Wrapper {
   private static final Logger Y = LogManager.getLogger(RotationManager.class);
   public static Vector2f r;
   public static Vector2f G;
   public static boolean L = false;
   private static final int C = 0;
   private static final int E = 1;
   private static final int u = 2;
   private static final int l = 3;
   private static final int K = 14;
   private static final int O = 4;
   private static final int p = 5;
   private static final int m = 6;
   private static final int s = 7;
   private static final int t = 8;
   private static final int x = 9;
   private static final int R = 10;
   private static final int A = 11;
   private static final int d = 12;
   private static final int k = 13;
   private static final int M = 15;
   private static final int a = 16;
   private static int v = 0;
   private static boolean N;
   private static Vector2f i;
   private static boolean y;
   private static Vector2f F;
   public static Vector2f X = null;
   public static float f;
   public static float c;
   public static float w;
   public static float e;
   private static float b;
   private static float S;

   public static void X(double var0, double var2) {
      boolean var4 = RotationUtils.T$Z();
      if (L && X != null) {
         float var5 = X.H + (float)(var0 * 0.15);
         float var6 = Mth.clamp(X.E + (float)(var2 * 0.15), -90.0F, 90.0F);
         X = new Vector2f(Mth.wrapDegrees(var5), var6);
      }
   }

   public static void y(float var0, float var1) {
      boolean var2 = RotationUtils.t();
      LocalPlayer var10000 = q.player;
      if (var2) {
         if (q.player == null) {
            return;
         }

         var10000 = q.player;
      }

      float var3 = var10000.getYRot();
      float var4 = Mth.wrapDegrees(var3);
      float var5 = Mth.wrapDegrees(var0);
      float var6 = Mth.wrapDegrees(var5 - var4);
      q.player.setYRot(var3 + var6);
      q.player.setXRot(Mth.clamp(var1, -90.0F, 90.0F));
      q.player.setOldRot();
   }

   public static void F() {
      boolean var0 = RotationUtils.t();
      label19:
      if (q.player != null) {
         Vector2f var10000 = X;
         if (var0) {
            if (X == null) {
               break label19;
            }

            var10000 = X;
         }

         y(var10000.H, X.E);
      }

      X = null;
      b = 0.0F;
      S = 0.0F;
      e = 0.0F;
      w = 0.0F;
      c = 0.0F;
      f = 0.0F;
   }

   public static boolean s$Z() {
      boolean var0 = RotationUtils.t();
      int var10000 = ((L) ? 1 : 0);
      if (var0) {
         if (!L) {
            return false;
         }

         var10000 = v;
      }

      if (var0) {
         if (var10000 == 0) {
            return false;
         }

         var10000 = v;
      }

      byte var10001 = 5;
      if (var0) {
         if (var10000 == 5) {
            return false;
         }

         var10000 = v;
         if (!var0) {
            return (boolean)((v) != 0);
         }

         var10001 = 6;
      }

      return var10000 != var10001;
   }

   public static boolean k() {
      boolean var0 = RotationUtils.t();
      int var10000 = ((L) ? 1 : 0);
      if (var0) {
         if (!L) {
            return false;
         }

         var10000 = v;
      }

      if (!var0) {
         return (boolean)((var10000) != 0);
      } else if (var10000 == 4) {
         return true;
      } else {
         return (boolean)(!var0 ? v : v == 13);
      }
   }

   private static Module M(Class var0) {
      try {
         return Elowen.S$Elowen().q$ModuleManager().A(var0);
      } catch (NoSuchModuleException var2) {
         return null;
      }
   }

   public static Vector2f a$h() {
      boolean var0 = RotationUtils.T$Z();
      return r != null ? r : new Vector2f(q.player != null ? Mth.wrapDegrees(q.player.getYRot()) : 0.0F, q.player != null ? q.player.getXRot() : 0.0F);
   }

   public static void l(Vector2f var0) {
      boolean var10000 = RotationUtils.t();
      float var2 = Mth.wrapDegrees(var0.H);
      float var3 = Mth.clamp(var0.E, -90.0F, 90.0F);
      Vector2f var4 = new Vector2f(var2, var3);
      boolean var1 = var10000;
      Vector2f var5 = G;
      if (var1) {
         if (G == null) {
            r = var4;
            return;
         }

         var5 = m(G, var4);
      }

      r = var5;
   }

   public static Vector2f m(Vector2f var0, Vector2f var1) {
      float var2 = (float)(((Double)q.options.sensitivity().get()).floatValue() * (1.0 + Math.random() / 100000.0) * 0.6F + 0.2F);
      double var3 = var2 * var2 * var2 * 8.0F * 0.15;
      float var5 = o(var0.H + (float)(Math.round((var1.H - var0.H) / var3) * var3));
      float var6 = w(var0.E + (float)(Math.round((var1.E - var0.E) / var3) * var3));
      return new Vector2f(var5, var6);
   }

   public static float o(float var0) {
      return Mth.wrapDegrees(var0);
   }

   public static float w(float var0) {
      return Mth.clamp(var0, -90.0F, 90.0F);
   }

   @EventTarget
   public void F(EventRespawn var1) {
      G = null;
      r = null;
      N = false;
      i = null;
      y = false;
      F = null;
      X = null;
      b = 0.0F;
      S = 0.0F;
      e = 0.0F;
      w = 0.0F;
      c = 0.0F;
      f = 0.0F;
      v = 0;
   }

   @EventTarget(0)
   public void k(EventTick var1) {
      boolean var2 = RotationUtils.t();
      if (var1.s$f() == com.elowen.events.api.types.EventType.PRE && q.player != null) {
         Vector2f var10000 = X;
         if (var2) {
            if (X != null) {
               return;
            }

            X = new Vector2f(Mth.wrapDegrees(q.player.getYRot()), q.player.getXRot());
            b = X.H;
            var10000 = X;
         }

         S = var10000.E;
         e = 0.0F;
         w = 0.0F;
         c = 0.0F;
         f = 0.0F;
      }
   }

   @EventTarget(0)
   public void t(com.elowen.events.impl.EventMoveInput var1) {
      boolean var2 = RotationUtils.t();
      if (L && r != null && q.player != null) {
         float var6;
         label27: {
            Vector2f var10000 = X;
            if (var2) {
               if (X != null) {
                  var6 = X.H;
                  break label27;
               }

               var10000 = r;
            }

            var6 = var10000.H;
         }

         float var3 = var6;
         float var4 = q.player.getYRot();
         float var5 = Mth.wrapDegrees(var4 + (var4 - var3));
         com.elowen.utils.MoveUtils.b(var1, var5);
      }
   }

   @EventTarget
   public void K(EventUseItemRayTrace var1) {
      if (L && r != null) {
         var1.V(r.H);
         var1.m(r.E);
      }
   }

   @EventTarget(4)
   public void g(EventTick var1) {
      Vector2f var72 = null;
      int var57 = 0;
      int var58 = (byte)0;
      float var62 = 0.0F;
      int var66 = 0;
      float var70 = 0.0F;
      boolean var2 = RotationUtils.t();
      if (var1.s$f() == com.elowen.events.api.types.EventType.PRE && q.player != null) {
         label717: {
            Vector2f var10000;
            var10000 = r;
            label716:
            if (var2) {
               if (r != null) {
                  var10000 = G;
                  if (!var2) {
                     break label716;
                  }

                  if (G != null) {
                     break label717;
                  }
               }

               var10000 = G = new Vector2f(Mth.wrapDegrees(q.player.getYRot()), q.player.getXRot());
            }

            r = var10000;
         }

         Aura var3;
         Scaffold var8;
         int var10;
         byte var11;
         byte var12;
         int var54;
         label707: {
            label723: {
               AttackCrystal var4;
               AutoRod var6;
               LongJump var7;
               Velocity var9;
               label724: {
                  var3 = (Aura)M(Aura.class);
                  var4 = (AttackCrystal)M(AttackCrystal.class);
                  AutoMLG var5 = (AutoMLG)M(AutoMLG.class);
                  var6 = (AutoRod)M(AutoRod.class);
                  var7 = (LongJump)M(LongJump.class);
                  var8 = (Scaffold)M(Scaffold.class);
                  var9 = (Velocity)M(Velocity.class);
                  var10 = v;
                  var11 = 0;
                  var12 = 0;
                  AutoMLG var41 = var5;
                  if (var2) {
                     if (var5 == null) {
                        break label724;
                     }

                     var41 = var5;
                  }

                  if (var2) {
                     if (!var41.w()) {
                        break label724;
                     }

                     var41 = var5;
                  }

                  Vector2f var42 = var41.i$H();
                  if (var2) {
                     if (var42 == null) {
                        break label724;
                     }

                     var42 = var5.i$H();
                  }

                  l(var42);
                  var12 = 1;
                  var11 = 1;
                  if (var2) {
                     break label723;
                  }
               }

               label687: {
                  LongJump var43 = var7;
                  if (var2) {
                     if (var7 == null) {
                        break label687;
                     }

                     var43 = var7;
                  }

                  label682:
                  if (var43.w()) {
                     Rotation var44 = LongJump.r;
                     if (var2) {
                        if (LongJump.r == null) {
                           break label682;
                        }

                        var44 = LongJump.r;
                     }

                     l(var44.M());
                     var12 = 1;
                     var11 = 2;
                     if (var2) {
                        break label723;
                     }
                  }
               }

               label673: {
                  AttackCrystal var45 = var4;
                  if (var2) {
                     if (var4 == null) {
                        break label673;
                     }

                     var45 = var4;
                  }

                  label668:
                  if (var45.w()) {
                     Vector2f var46 = AttackCrystal.D;
                     if (var2) {
                        if (AttackCrystal.D == null) {
                           break label668;
                        }

                        var46 = new Vector2f(AttackCrystal.D.H, AttackCrystal.D.E);
                     }

                     l(var46);
                     var12 = 1;
                     var11 = 3;
                     if (var2) {
                        break label723;
                     }
                  }
               }

               label725: {
                  Scaffold var47 = var8;
                  if (var2) {
                     if (var8 == null) {
                        break label725;
                     }

                     var47 = var8;
                  }

                  if (var2) {
                     if (!var47.w()) {
                        break label725;
                     }

                     var47 = var8;
                  }

                  Vector2f var48 = var47.j;
                  if (var2) {
                     if (var47.j == null) {
                        break label725;
                     }

                     var48 = new Vector2f(var8.j.H, var8.j.E);
                  }

                  l(var48);
                  var12 = 1;
                  var11 = 4;
                  if (var2) {
                     break label723;
                  }
               }

               label641: {
                  Velocity var49 = var9;
                  if (var2) {
                     if (var9 == null) {
                        break label641;
                     }

                     var49 = var9;
                  }

                  label636:
                  if (var49.d$Z()) {
                     Vector2f var50 = Reduce.g;
                     if (var2) {
                        if (Reduce.g == null) {
                           break label636;
                        }

                        var50 = r;
                     }

                     Vector2f var13;
                     label727: {
                        if (var2) {
                           if (var50 != null) {
                              var13 = r;
                              if (var2) {
                                 break label727;
                              }
                           }

                           var50 = G;
                        }

                        if (var2) {
                           if (var50 != null) {
                              var13 = G;
                              if (var2) {
                                 break label727;
                              }
                           }

                           var50 = new Vector2f(Mth.wrapDegrees(q.player.getYRot()), q.player.getXRot());
                        }

                        var13 = var50;
                     }

                     float var14 = 180.0F;
                     float var15 = RotationUtils.i(var14, var13.H, Reduce.g.H);
                     float var16 = RotationUtils.q(var13.E, Reduce.g.E, var14);
                     Vector2f var17 = new Vector2f(o(var15), w(var16));
                     l(var17);
                     var12 = 1;
                     var11 = 16;
                     if (var2) {
                        break label723;
                     }
                  }
               }

               label615: {
                  Aura var51 = var3;
                  if (var2) {
                     if (var3 == null) {
                        break label615;
                     }

                     var51 = var3;
                  }

                  label610:
                  if (var51.w() && Aura.cj != null) {
                     Vector2f var52 = Aura.e;
                     if (var2) {
                        if (Aura.e == null) {
                           break label610;
                        }

                        var52 = r;
                     }

                     Vector2f var22;
                     label729: {
                        if (var2) {
                           if (var52 != null) {
                              var22 = r;
                              if (var2) {
                                 break label729;
                              }
                           }

                           var52 = G;
                        }

                        if (var2) {
                           if (var52 != null) {
                              var22 = G;
                              if (var2) {
                                 break label729;
                              }
                           }

                           var52 = new Vector2f(Mth.wrapDegrees(q.player.getYRot()), q.player.getXRot());
                        }

                        var22 = var52;
                     }

                     float var24 = var3.j$F();
                     float var28 = RotationUtils.i(var24, var22.H, Aura.e.H);
                     float var32 = RotationUtils.q(var22.E, Aura.e.E, var24);
                     Vector2f var35 = new Vector2f(o(var28), w(var32));
                     l(var35);
                     var12 = 1;
                     var11 = 7;
                     if (var2) {
                        break label723;
                     }
                  }
               }

               AutoRod var53 = var6;
               if (var2) {
                  if (var6 == null) {
                     break label723;
                  }

                  var53 = var6;
               }

               var54 = ((var53.w()) ? 1 : 0);
               if (!var2) {
                  break label707;
               }

               label583:
               if (var54 != 0) {
                  Vector2f var55 = var6.f;
                  if (var2) {
                     if (var6.f == null) {
                        break label583;
                     }

                     var55 = new Vector2f(var6.f.H, var6.f.E);
                  }

                  l(var55);
                  var12 = 1;
                  var11 = 11;
               }
            }

            var54 = var12;
         }

         int var23;
         label574: {
            label751: {
               if (var2) {
                  if (var54 != 0) {
                     N = false;
                     i = null;
                     y = false;
                     F = null;
                     if (var2) {
                        break label751;
                     }
                  }

                  var54 = var10;
               }

               label566: {
                  label731: {
                     label564: {
                        byte var10001 = 7;
                        if (var2) {
                           if (var54 == 7) {
                              break label564;
                           }

                           var54 = var10;
                           var10001 = 12;
                        }

                        if (var54 != var10001) {
                           break label731;
                        }
                     }

                     Vector2f var56 = G;
                     if (var2) {
                        if (G == null) {
                           break label731;
                        }

                        var56 = X;
                     }

                     if (var56 != null) {
                        var57 = 1;
                        break label566;
                     }
                  }

                  var57 = 0;
               }

               var23 = ((byte)(var57));
               var58 = ((N) ? 1 : 0);
               label548:
               if (var2) {
                  if (!N) {
                     var58 = var23;
                     if (!var2) {
                        break label548;
                     }

                     if (var23 != 0) {
                        N = true;
                     }
                  }

                  var58 = ((N) ? 1 : 0);
               }

               if (var2) {
                  label539:
                  if (var58 != 0) {
                     Vector2f var59 = X;
                     if (var2) {
                        if (X == null) {
                           break label539;
                        }

                        var59 = X;
                     }

                     i = var59;
                  }

                  var58 = ((N) ? 1 : 0);
               }

               label515:
               if (var2) {
                  label528:
                  if (var58 != 0) {
                     Vector2f var60 = i;
                     if (var2) {
                        if (i == null) {
                           break label528;
                        }

                        var60 = G;
                     }

                     if (var60 != null) {
                        label520: {
                           Aura var61 = var3;
                           if (var2) {
                              if (var3 == null) {
                                 var62 = 30.0F;
                                 break label520;
                              }

                              var61 = var3;
                           }

                           var62 = var61.j$F();
                        }

                        float var25 = var62;
                        Vector2f var63 = r;
                        if (var2) {
                           var63 = r != null ? r : G;
                        }

                        Vector2f var29 = var63;
                        float var33 = RotationUtils.i(var25, var29.H, i.H);
                        float var36 = RotationUtils.q(var29.E, i.E, var25);
                        Vector2f var18 = new Vector2f(o(var33), w(var36));
                        l(var18);
                        var12 = 1;
                        var11 = 12;
                        float var19 = Math.abs(RotationUtils.e(i.H, var18.H));
                        float var20 = Math.abs(RotationUtils.e(i.E, var18.E));
                        float var74;
                        var58 = (byte)((var74 = var19 - 0.25F) == 0.0F ? 0 : (var74 < 0.0F ? -1 : 1));
                        if (!var2) {
                           break label515;
                        }

                        if (var58 < 0) {
                           float var75;
                           var58 = (byte)((var75 = var20 - 0.25F) == 0.0F ? 0 : (var75 < 0.0F ? -1 : 1));
                           if (!var2) {
                              break label515;
                           }

                           if (var58 < 0) {
                              N = false;
                              i = null;
                              var12 = 0;
                              var11 = 0;
                           }
                        }
                     }
                  }

                  var58 = var12;
               }

               if (!var2) {
                  break label574;
               }

               if (var58 == 0) {
                  label504: {
                     label737: {
                        label502: {
                           var54 = var10;
                           byte var73 = 4;
                           if (var2) {
                              if (var10 == 4) {
                                 break label502;
                              }

                              var54 = var10;
                              var73 = 13;
                           }

                           if (var54 != var73) {
                              break label737;
                           }
                        }

                        Vector2f var65 = G;
                        if (var2) {
                           if (G == null) {
                              break label737;
                           }

                           var65 = X;
                        }

                        if (var65 != null) {
                           var66 = 1;
                           break label504;
                        }
                     }

                     var66 = 0;
                  }

                  byte var26 = ((byte)(var66));
                  var58 = ((y) ? 1 : 0);
                  label486:
                  if (var2) {
                     if (!y) {
                        var58 = var26;
                        if (!var2) {
                           break label486;
                        }

                        if (var26 != 0) {
                           y = true;
                           F = X;
                        }
                     }

                     var58 = ((y) ? 1 : 0);
                  }

                  if (var2) {
                     label477:
                     if (var58 != 0) {
                        Vector2f var67 = X;
                        if (var2) {
                           if (X == null) {
                              break label477;
                           }

                           var67 = X;
                        }

                        F = var67;
                     }

                     var58 = ((y) ? 1 : 0);
                  }

                  if (!var2) {
                     break label574;
                  }

                  label468:
                  if (var58 != 0) {
                     Vector2f var68 = F;
                     if (var2) {
                        if (F == null) {
                           break label468;
                        }

                        var68 = G;
                     }

                     if (var68 != null) {
                        label460: {
                           Scaffold var69 = var8;
                           if (var2) {
                              if (var8 == null) {
                                 var70 = 50.0F;
                                 break label460;
                              }

                              var69 = var8;
                           }

                           var70 = var69.v$F();
                        }

                        float var30 = var70;
                        Vector2f var71 = r;
                        if (var2) {
                           var71 = r != null ? r : G;
                        }

                        Vector2f var34 = var71;
                        float var37 = RotationUtils.i(var30, var34.H, F.H);
                        float var38 = RotationUtils.q(var34.E, F.E, var30);
                        Vector2f var39 = new Vector2f(o(var37), w(var38));
                        l(var39);
                        var12 = 1;
                        var11 = 13;
                        float var40 = Math.abs(RotationUtils.e(F.H, var39.H));
                        float var21 = Math.abs(RotationUtils.e(F.E, var39.E));
                        float var76;
                        var58 = (byte)((var76 = var40 - 0.25F) == 0.0F ? 0 : (var76 < 0.0F ? -1 : 1));
                        if (!var2) {
                           break label574;
                        }

                        if (var58 < 0) {
                           float var77;
                           var58 = (byte)((var77 = var21 - 0.25F) == 0.0F ? 0 : (var77 < 0.0F ? -1 : 1));
                           if (!var2) {
                              break label574;
                           }

                           if (var58 < 0) {
                              y = false;
                              F = null;
                              var12 = 0;
                              var11 = 0;
                           }
                        }
                     }
                  }
               }
            }

            var23 = ((L) ? 1 : 0);
            L = (boolean)((var12) != 0);
            v = var11;
            var58 = ((L) ? 1 : 0);
         }

         if (var2) {
            if (var58 == 0) {
               r = null;
            }

            var58 = var23;
         }

         label443:
         if (var2) {
            if (var58 != 0) {
               var58 = ((L) ? 1 : 0);
               if (!var2) {
                  break label443;
               }

               if (!L) {
                  F();
               }
            }

            var58 = ((L) ? 1 : 0);
         }

         if (var2) {
            if (var58 == 0 && X != null) {
               X = null;
            }

            var58 = ((L) ? 1 : 0);
         }

         label435: {
            if (var58 != 0) {
               var72 = r;
               if (!var2) {
                  break label435;
               }

               if (r != null) {
                  y(r.H, r.E);
               }
            }

            var72 = X;
         }

         if (var2) {
            if (var72 == null) {
               return;
            }

            var72 = X;
         }

         float var27 = Mth.wrapDegrees(var72.H - b);
         float var31 = X.E - S;
         e = w;
         w = (w + var27) * 0.5F;
         c = f;
         f = (f + var31) * 0.5F;
         b = X.H;
         S = X.E;
      }
   }

   private static NoSuchModuleException a(NoSuchModuleException var0) {
      return var0;
   }
}
