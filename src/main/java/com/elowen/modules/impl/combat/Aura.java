package com.elowen.modules.impl.combat;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventRespawn;
import com.elowen.events.impl.EventTick;
import com.elowen.events.impl.EventRender;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.misc.KillSay;
import com.elowen.modules.impl.move.Blink;
import com.elowen.modules.impl.move.Stuck;
import com.elowen.utils.ChatUtils;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.GhostHitBoxes;
import com.elowen.utils.RayTraceUtils;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.utils.renderer.threeD.Skija3DRenderer;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.utils.rotation.Rotation;
import com.elowen.utils.rotation.RotationUtils$Data;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

@ModuleInfo(R = "KillAura", a = "Automatically attacks entities", M = Category.COMBAT)
public class Aura extends Module {
   private static final float[] c8;
   private static final float[] i;
   public static Entity cj;
   public static Entity Z;
   public static List l;
   public static Vector2f e;
   public static boolean r;
   private boolean d = false;
   private int U = 0;
   private final Skija3DRenderer D = new Skija3DRenderer();
   com.elowen.values.impl.BooleanValue S;
   com.elowen.values.impl.BooleanValue b;
   com.elowen.values.impl.BooleanValue cE;
   com.elowen.values.impl.BooleanValue y;
   com.elowen.values.impl.BooleanValue t;
   com.elowen.values.impl.BooleanValue j;
   com.elowen.values.impl.BooleanValue m;
   com.elowen.values.impl.BooleanValue B;
   com.elowen.values.impl.BooleanValue cP;
   com.elowen.values.impl.BooleanValue c_;
   com.elowen.values.impl.BooleanValue K;
   public com.elowen.values.impl.BooleanValue Y;
   public FloatValue C;
   com.elowen.values.impl.BooleanValue v;
   FloatValue cV;
   private boolean cv;
   private float o;
   FloatValue X;
   FloatValue cr;
   FloatValue cD;
   FloatValue cF;
   FloatValue c;
   FloatValue V;
   FloatValue cc;
   ModeValue c3;
   RotationUtils$Data x;
   RotationUtils$Data F;
   int ch;
   public float R;
   private long I;
   private int h;
   private float q;
   private float M;
   private float E;
   private float Q;
   private long P;
   private Random z;
   private float cH;
   private float p;
   private float f;
   private float T;
   private Vector2f J;
   private static final String[] bb = new String[]{"Aim Range", "Normal", "Target ESP", "Attack Mobs", "Attacking Bot!", "Offset Range", "Switch", "Attack Animals", "Switch Delay (Attack Times)", "Player FoV", "Hurt Time", "More Particles", "Range", "Keep FoV", "Attack Per Second", "[KillAura] Target ESP render failed: ", "Infinity Switch", "Range", "Multi Attack", "Rotation Speed", "Delay", "Attack Player", "Lowest HurtTime", "Health", "Attack Invisible", "Single", "Multi", "Allow Offhand Use", "FoV", "Random Offset", "Health", "Switch Size", "FoV", "None", "Prefer Baby", "Priority"};
   public Aura() {
      String[] var2 = bb;
      this.S = com.elowen.values.ValueBuilder.m(this, "Target ESP").h(true).f$K().f$O();
      this.b = com.elowen.values.ValueBuilder.m(this, "Attack Player").h(true).f$K().f$O();
      this.cE = com.elowen.values.ValueBuilder.m(this, "Attack Invisible").h(false).f$K().f$O();
      boolean var10000 = Velocity.p();
      this.y = com.elowen.values.ValueBuilder.m(this, "Attack Animals").h(false).f$K().f$O();
      this.t = com.elowen.values.ValueBuilder.m(this, "Attack Mobs").h(false).f$K().f$O();
      this.j = com.elowen.values.ValueBuilder.m(this, "Multi Attack").h(false).f$K().f$O();
      this.m = com.elowen.values.ValueBuilder.m(this, "Infinity Switch").h(false).f$K().f$O();
      this.B = com.elowen.values.ValueBuilder.m(this, "Prefer Baby").h(false).f$K().f$O();
      boolean var1 = var10000;
      this.cP = com.elowen.values.ValueBuilder.m(this, "More Particles").h(false).f$K().f$O();
      this.c_ = com.elowen.values.ValueBuilder.m(this, "Lowest HurtTime").h(true).f$K().f$O();
      this.K = com.elowen.values.ValueBuilder.m(this, "Allow Offhand Use").h(false).f$K().f$O();
      this.Y = com.elowen.values.ValueBuilder.m(this, "Keep FoV").h(false).f$K().f$O();
      this.C = com.elowen.values.ValueBuilder.m(this, "Player FoV").d(1.15F).M(2.0F).w(1.0F).V(0.05F).l(this::deobfLambda$new$0).f$K().L();
      this.v = com.elowen.values.ValueBuilder.m(this, "Random Offset").h(false).f$K().f$O();
      this.cV = com.elowen.values.ValueBuilder.m(this, "Offset Range").d(0.5F).V(0.05F).w(0.0F).M(1.0F).l(this::deobfLambda$new$1).f$K().L();
      this.cv = false;
      this.o = 0.0F;
      this.X = com.elowen.values.ValueBuilder.m(this, "Rotation Speed").d(180.0F).V(1.0F).w(0.0F).M(180.0F).f$K().L();
      this.cr = com.elowen.values.ValueBuilder.m(this, "Aim Range").d(5.0F).V(0.1F).w(1.0F).M(6.0F).f$K().L();
      this.cD = com.elowen.values.ValueBuilder.m(this, "Attack Per Second").d(10.0F).V(1.0F).w(0.0F).M(20.0F).f$K().L();
      this.cF = com.elowen.values.ValueBuilder.m(this, "Switch Size").d(1.0F).V(1.0F).w(1.0F).M(5.0F).l(this::deobfLambda$new$2).f$K().L();
      this.c = com.elowen.values.ValueBuilder.m(this, "Switch Delay (Attack Times)").d(1.0F).V(1.0F).w(1.0F).M(10.0F).f$K().L();
      this.V = com.elowen.values.ValueBuilder.m(this, "FoV").d(360.0F).V(1.0F).w(10.0F).M(360.0F).f$K().L();
      this.cc = com.elowen.values.ValueBuilder.m(this, "Hurt Time").d(10.0F).V(1.0F).w(0.0F).M(10.0F).f$K().L();
      this.c3 = com.elowen.values.ValueBuilder.m(this, "Priority").W(new String[]{"Health", "FoV", "Range", "None"}).f$K().T$t();
      this.ch = 0;
      this.R = 0.0F;
      this.I = 0L;
      this.q = 0.0F;
      this.M = 0.0F;
      this.E = 0.0F;
      this.Q = 0.0F;
      this.P = 0L;
      this.z = new Random();
      this.cH = 0.0F;
      this.p = 0.0F;
      this.f = 0.0F;
      this.T = 0.0F;
      this.J = null;
      if (!com.elowen.values.HasValue.x()) {
         Velocity.Q(!var1);
      }
   }

   private boolean c$Z() {
      boolean var1 = Velocity.o$Z();
      if (G.player == null) {
         return false;
      }

      Input var2 = G.player.input.keyPresses;
      return var2.forward() && !var2.backward() && !var2.left() && !var2.right();
   }

   @EventTarget
   public void E(EventRender var1) {
      boolean var8 = false;
      boolean var2 = Velocity.p();
      Minecraft var10000 = G;
      if (!var2) {
         if (G.level == null) {
            return;
         }

         var10000 = G;
      }

      if (var10000.player != null) {
         label79: {
            label89: {
               var8 = this.S.w();
               if (!var2) {
                  if (!var8) {
                     break label89;
                  }

                  var8 = this.w();
               }

               if (var2) {
                  break label79;
               }

               if (var8) {
                  var8 = true;
                  break label79;
               }
            }

            var8 = false;
         }

         boolean var3 = var8;
         if (var3) {
            try {
               float var4 = G.getDeltaTracker().getGameTimeDeltaPartialTick(false);
               Camera var5 = G.gameRenderer.mainCamera();
               if (!var5.isInitialized()) {
                  return;
               }

               Canvas var6 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
               if (!var2) {
                  if (var6 == null) {
                     return;
                  }

                  this.D.I(var6, new WorldProjector(var5, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), com.elowen.utils.renderer.ViewBob.g$Matrix4f()));
               }

               boolean var9 = var3;
               if (!var2) {
                  if (var3) {
                     this.u(this.D, var4);
                  }

                  this.D.G$V();
                  com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
                  var9 = this.D.s$Z();
               }

               if (!var9) {
                  com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
               }
            } catch (RuntimeException var7) {
               System.err.println("[KillAura] Target ESP render failed: " + var7);
            }
         }
      }
   }

   private void u(Skija3DRenderer var1, float var2) {
      int var10000 = ((Velocity.p()) ? 1 : 0);
      Iterator var4 = l.iterator();
      boolean var3 = (boolean)((var10000) != 0);

      while (var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         Entity var17 = var5;
         if (!var3) {
            if (!(var5 instanceof LivingEntity)) {
               continue;
            }

            var17 = var5;
         }

         AABB var6 = var17.getBoundingBox();
         double var7 = Mth.lerp(var2, var5.xOld, var5.getX()) - var5.getX();
         double var9 = Mth.lerp(var2, var5.yOld, var5.getY()) - var5.getY();
         double var11 = Mth.lerp(var2, var5.zOld, var5.getZ()) - var5.getZ();
         AABB var13 = var6.move(var7, var9, var11);
         int var14 = var5 == cj ? 1 : 0;
         var10000 = var14;
         if (!var3) {
            var10000 = var14 != 0 ? 200 : 0;
         }

         int var15 = var10000;
         var10000 = var14;
         if (!var3) {
            var10000 = var14 != 0 ? 0 : 200;
         }

         int var16 = var10000;
         this.n(var1, var13, com.elowen.utils.renderer.RenderUtils.O(var15, var16, 0, 60));
         if (var3) {
            break;
         }
      }
   }

   private void n(Skija3DRenderer var1, AABB var2, int var3) {
      boolean var10000 = Velocity.o$Z();
      double var5 = var2.minX;
      boolean var4 = var10000;
      double var7 = var2.minY;
      double var9 = var2.minZ;
      double var11 = var2.maxX;
      double var13 = var2.maxY;
      double var15 = var2.maxZ;
      double[][] var17 = new double[][]{
         {var5, var7, var9},
         {var11, var7, var9},
         {var11, var7, var15},
         {var5, var7, var15},
         {var5, var13, var9},
         {var11, var13, var9},
         {var11, var13, var15},
         {var5, var13, var15}
      };
      int[][] var18 = new int[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {0, 1, 5, 4}, {1, 2, 6, 5}, {2, 3, 7, 6}, {3, 0, 4, 7}};

      for (int[] var22 : var18) {
         double[][] var23 = new double[][]{var17[var22[0]], var17[var22[1]], var17[var22[2]], var17[var22[3]]};
         var1.R(var23, var3);
         if (!var4) {
            break;
         }
      }
   }

   @EventTarget
   public void V(com.elowen.events.impl.EventUpdateFoV var1) {
      boolean var2 = Velocity.p();
      int var10000 = ((this.Y.w()) ? 1 : 0);
      if (!var2) {
         if (var10000 == 0) {
            return;
         }

         var10000 = this.U;
      }

      if (var10000 > 0 && G.player != null) {
         var1.F(this.C.o$F() + com.elowen.utils.PlayerUtils.V() * 0.13F);
      }
   }

   @Override
   public void h$V() {
      e = null;
      this.h = 0;
      boolean var10000 = Velocity.o$Z();
      cj = null;
      Z = null;
      boolean var1 = var10000;
      l.clear();
      this.cv = false;
      if (var1) {
         if (G.player != null) {
            this.q = G.player.getYRot();
            this.M = G.player.getXRot();
            this.E = this.q;
            this.Q = this.M;
         }

         this.P = System.currentTimeMillis();
         this.cH = 0.0F;
         this.p = 0.0F;
         this.f = 0.0F;
         this.T = 0.0F;
         this.J = null;
         r = false;
         this.d = false;
         this.U = 0;
         this.I = 0L;
         this.R = 0.0F;
      }
   }

   @Override
   public void q$V() {
      cj = null;
      Z = null;
      this.cv = false;
      e = null;
      this.F = null;
      this.x = null;
      l.clear();
      r = false;
      this.d = false;
      this.U = 0;
      super.q$V();
   }

   @EventTarget
   public void F(EventRespawn var1) {
      cj = null;
      Z = null;
      this.R$V();
   }

   @EventTarget
   public void A(EventTick var1) {
      boolean var28 = false;
      Aura var32 = null;
      boolean var35 = false;
      Vector2f var39 = null;
      float var42 = 0.0F;
      Aura var44 = null;
      boolean var2 = Velocity.p();
      if (var1.s$f() == com.elowen.events.api.types.EventType.PRE && G.player != null) {
         this.d = false;
         Module var10000 = this;
         if (!var2) {
            if (this.U > 0) {
               this.U--;
            }

            var10000 = Elowen.S$Elowen().q$ModuleManager().A(Stuck.class);
         }

         Stuck var3 = (Stuck)var10000;
         var28 = G.gui.screen() instanceof AbstractContainerScreen;
         label522:
         if (!var2) {
            label539:
            if (!var28) {
               int var29 = ((var3.w()) ? 1 : 0);
               if (!var2) {
                  if (var29 != 0) {
                     var28 = var3.S$t().t("Normal");
                     if (var2) {
                        break label522;
                     }

                     if (var28) {
                        break label539;
                     }

                     var28 = var3.S$t().t("Delay");
                     if (var2) {
                        break label522;
                     }

                     if (var28) {
                        break label539;
                     }
                  }

                  var29 = ((com.elowen.utils.InventoryUtils.G$Z()) ? 1 : 0);
               }

               if (!var2) {
                  if (var29 != 0) {
                     break label539;
                  }

                  float var47;
                  var29 = (var47 = this.cF.o$F() - 1.0F) == 0.0F ? 0 : (var47 < 0.0F ? -1 : 1);
               }

               if (!var2) {
                  var29 = var29 > 0 ? 1 : 0;
               }

               int var4;
               String var45;
               label516: {
                  var4 = var29;
                  int var10001 = ((this.j.w()) ? 1 : 0);
                  if (!var2) {
                     if (var10001 != 0) {
                        var45 = "Multi";
                        break label516;
                     }

                     var10001 = var4;
                  }

                  var45 = var10001 != 0 ? "Switch" : "Single";
               }

               this.X(var45);
               this.c$V();
               int var30 = ((l.isEmpty()) ? 1 : 0);
               if (!var2) {
                  if (var30 != 0) {
                     cj = null;
                     Z = null;
                     e = null;
                     r = false;
                     this.I = 0L;
                     this.R = 0.0F;
                     return;
                  }

                  var30 = this.h;
               }

               int var46 = l.size() - 1;
               if (!var2) {
                  if (var30 > var46) {
                     this.h = 0;
                  }

                  var30 = l.size();
                  var46 = 1;
               }

               label507: {
                  label550: {
                     label505:
                     if (!var2) {
                        label551: {
                           label502: {
                              label501:
                              if (var30 > var46) {
                                 float var48;
                                 int var31 = (var48 = this.ch - this.c.o$F()) == 0.0F ? 0 : (var48 < 0.0F ? -1 : 1);
                                 if (!var2) {
                                    if (var31 < 0) {
                                       var32 = this;
                                       if (var2) {
                                          break label502;
                                       }

                                       if (this.F == null) {
                                          break label501;
                                       }

                                       double var49;
                                       var30 = (var49 = this.F.t() - this.cr.o$F()) == 0.0 ? 0 : (var49 < 0.0 ? -1 : 1);
                                       if (var2) {
                                          break label551;
                                       }

                                       if (var30 <= 0) {
                                          break label501;
                                       }
                                    }

                                    this.ch = 0;
                                    var31 = 0;
                                 }

                                 int var5 = var31;

                                 while (var5 < l.size()) {
                                    this.h++;
                                    Object var33 = this;
                                    if (!var2) {
                                       var30 = this.h;
                                       var46 = l.size() - 1;
                                       if (var2) {
                                          break label505;
                                       }

                                       if (var30 > var46) {
                                          this.h = 0;
                                       }

                                       var33 = l.get(this.h);
                                    }

                                    Entity var6 = (Entity)var33;
                                    RotationUtils$Data var7 = com.elowen.utils.rotation.RotationUtils.s(var6);
                                    if (!var2) {
                                       if (var7.t() < this.cr.o$F() && !var2) {
                                          break;
                                       }

                                       var5++;
                                    }

                                    if (var2) {
                                       break;
                                    }
                                 }
                              }

                              var32 = this;
                           }

                           if (var2) {
                              break label550;
                           }

                           var30 = var32.h;
                        }

                        var46 = l.size() - 1;
                     }

                     if (var30 <= var46 && var4 != 0) {
                        break label507;
                     }

                     var32 = this;
                  }

                  var32.h = 0;
               }

               label462: {
                  label556: {
                     cj = (Entity)l.get(this.h);
                     Z = cj;
                     this.x = this.F;
                     Aura var34 = this;
                     if (!var2) {
                        this.F = null;
                        if (Z == null) {
                           break label556;
                        }

                        var34 = this;
                     }

                     var35 = com.elowen.utils.rotation.RotationUtils.F(var34.B$AABB(Z));
                     if (var2) {
                        break label462;
                     }

                     if (var35) {
                        var35 = true;
                        break label462;
                     }
                  }

                  var35 = false;
               }

               boolean var22;
               var22 = var35;
               label439:
               if (Z != null) {
                  boolean var36 = var22;
                  if (!var2) {
                     if (var22) {
                        break label439;
                     }

                     var36 = com.elowen.utils.GhostHitBoxes.R$Z();
                  }

                  label449: {
                     label448: {
                        if (!var2) {
                           if (!var36) {
                              break label448;
                           }

                           var36 = com.elowen.utils.GhostHitBoxes.n(Z.getUUID());
                        }

                        if (var36) {
                           this.F = com.elowen.utils.rotation.RotationUtils.F(Z, com.elowen.utils.GhostHitBoxes.k(Z.getUUID()));
                           if (!var2) {
                              break label449;
                           }
                        }
                     }

                     this.F = com.elowen.utils.rotation.RotationUtils.s(Z);
                  }

                  Aura var37 = this;
                  if (!var2) {
                     if (this.F.e() == null) {
                        break label439;
                     }

                     this.E = this.F.e().H;
                     var37 = this;
                  }

                  var37.Q = this.F.e().E;
               }

               label429: {
                  float var11;
                  float var12;
                  long var23 = System.currentTimeMillis();
                  float var8 = (float)(var23 - this.P) / 1000.0F;
                  this.P = var23;
                  var8 = Math.min(var8, 0.1F);
                  this.q = this.E;
                  this.M = this.Q;
                  float var9 = this.q;
                  float var10 = this.M;
                  var11 = var9;
                  var12 = var10;
                  label428:
                  if (this.v.w()) {
                     Entity var38 = Z;
                     if (!var2) {
                        if (Z == null) {
                           break label428;
                        }

                        var38 = Z;
                     }

                     if (!var2) {
                        if (!(var38 instanceof LivingEntity)) {
                           break label428;
                        }

                        var38 = Z;
                     }

                     LivingEntity var13 = (LivingEntity)var38;
                     Vector2f var14 = this.r(var13, this.cV.o$F());
                     var39 = var14;
                     if (var2) {
                        break label429;
                     }

                     if (var14 != null) {
                        var11 = var14.H;
                        var12 = var14.E;
                     }
                  }

                  var39 = new Vector2f(var11, var12);
               }

               Vector2f var25 = var39;
               boolean var26 = false;
               if (Z != null) {
                  var26 = this.I(Z, var25, this.cr.o$F());
               }

               label559: {
                  boolean var40 = var22;
                  if (!var2) {
                     if (var22) {
                        this.J = null;
                        if (!var2) {
                           break label559;
                        }
                     }

                     var40 = var26;
                  }

                  if (var40) {
                     this.J = var25;
                     if (!var2) {
                        break label559;
                     }
                  }

                  if (Z != null) {
                     label406: {
                        label405: {
                           Vector2f var15 = this.C(Z, this.cr.o$F());
                           if (!var2) {
                              if (var15 == null) {
                                 break label405;
                              }

                              this.J = var15;
                              this.E = var15.H;
                              this.Q = var15.E;
                           }

                           if (!var2) {
                              break label406;
                           }
                        }

                        this.J = null;
                     }

                     if (!var2) {
                        break label559;
                     }
                  }

                  this.J = null;
               }

               label390: {
                  boolean var27 = this.P();
                  if (this.J != null && !var27) {
                     e = this.J;
                     if (!var2) {
                        break label390;
                     }
                  }

                  e = null;
               }

               label384: {
                  if (Z != null && e != null) {
                     r = true;
                     if (!var2) {
                        break label384;
                     }
                  }

                  r = false;
               }

               boolean var41 = l.isEmpty();
               if (!var2) {
                  if (var41) {
                     cj = null;
                     this.I = 0L;
                     this.R = 0.0F;
                     return;
                  }

                  var41 = this.G$Z();
               }

               label377: {
                  label560: {
                     if (!var2) {
                        if (!var41) {
                           break label560;
                        }

                        var41 = l.isEmpty();
                     }

                     if (var2) {
                        break label377;
                     }

                     if (!var41) {
                        var41 = true;
                        break label377;
                     }
                  }

                  var41 = false;
               }

               boolean var16 = var41;
               if (!var2) {
                  if (var16) {
                     float var17;
                     var17 = this.cD.o$F();
                     var42 = ((this.cv) ? 1 : 0);
                     label362:
                     if (!var2) {
                        if (this.cv) {
                           float var50;
                           var42 = (byte)((var50 = this.o - 0.0F) == 0.0F ? 0 : (var50 < 0.0F ? -1 : 1));
                           if (var2) {
                              break label362;
                           }

                           if (var42 > 0) {
                              var17 = Math.min(var17, this.o);
                           }
                        }

                        float var51;
                        var42 = (byte)((var51 = var17 - 0.0F) == 0.0F ? 0 : (var51 < 0.0F ? -1 : 1));
                     }

                     if (var42 <= 0) {
                        return;
                     }

                     label354: {
                        label562: {
                           long var18 = System.currentTimeMillis();
                           long var20 = (long)(1000.0 / var17);
                           long var52;
                           int var43 = (var52 = var18 - this.I) == 0L ? 0 : (var52 < 0L ? -1 : 1);
                           if (!var2) {
                              if (var43 < 0) {
                                 break label562;
                              }

                              this.n$V();
                              this.R = 1.0F;
                              long var53;
                              var43 = (var53 = this.I - 0L) == 0L ? 0 : (var53 < 0L ? -1 : 1);
                           }

                           label563: {
                              if (!var2) {
                                 if (var43 == 0) {
                                    this.I = var18 + var20;
                                    if (!var2) {
                                       break label354;
                                    }
                                 }

                                 this.I += var20;
                                 var44 = this;
                                 if (var2) {
                                    break label563;
                                 }

                                 long var54;
                                 var43 = (var54 = this.I - var18) == 0L ? 0 : (var54 < 0L ? -1 : 1);
                              }

                              if (var43 >= 0) {
                                 break label354;
                              }

                              var44 = this;
                           }

                           var44.I = var18 + var20;
                           if (!var2) {
                              break label354;
                           }
                        }

                        this.R = 0.0F;
                     }

                     if (!var2) {
                        return;
                     }
                  }

                  this.I = 0L;
               }

               this.R = 0.0F;
               return;
            }

            cj = null;
            Z = null;
            this.F = null;
            e = null;
            this.x = null;
            l.clear();
            var28 = false;
         }

         r = var28;
         this.I = 0L;
         this.R = 0.0F;
      }
   }

   private boolean P() {
      boolean var1 = Velocity.o$Z();
      if (G.player == null) {
         return false;
      }

      ItemStack var2 = G.player.getMainHandItem();
      ItemStack var3 = G.player.getOffhandItem();
      return this.X(var2) || this.X(var3);
   }

   private boolean X(ItemStack var1) {
      boolean var2 = Velocity.p();
      ItemStack var10000 = var1;
      if (!var2) {
         if (var1 == null) {
            return false;
         }

         var10000 = var1;
      }

      boolean var3 = var10000.isEmpty();
      if (!var2) {
         if (var3) {
            return false;
         }

         var3 = var1.is(Items.ENDER_PEARL);
      }

      if (!var2) {
         if (!var3) {
            boolean var4 = var1.is(Items.END_CRYSTAL);
            if (var2) {
               return var4;
            }

            if (!var4) {
               boolean var5 = var1.is(Items.FIRE_CHARGE);
               if (var2) {
                  return var5;
               }

               if (!var5) {
                  boolean var6 = var1.is(Items.WIND_CHARGE);
                  if (var2) {
                     return var6;
                  }

                  if (!var6) {
                     return false;
                  }
               }
            }
         }

         var3 = true;
      }

      return var3;
   }

   private boolean G$Z() {
      boolean var1 = Velocity.o$Z();
      boolean var2 = G.player.getUseItem() != null && G.player.getUsedItemHand() == InteractionHand.OFF_HAND;
      boolean var3 = G.player.getUseItem().isEmpty() || this.K.w() && var2;
      return var3
         && G.gui.screen() == null
         && Elowen.o.isEmpty()
         && !com.elowen.utils.PacketUtils.z$Z()
         && !Elowen.S$Elowen().q$ModuleManager().A(Blink.class).w()
         && !Velocity.e();
   }

   private Vector2f r(LivingEntity var1, float var2) {
      boolean var3 = Velocity.p();
      if (var1 != null && G.player != null) {
         AABB var4;
         Vec3 var5;
         label22: {
            var4 = this.B$AABB(var1);
            var5 = com.elowen.utils.rotation.RotationUtils.x(G.player.getEyePosition(), var4);
            Vec3 var10000 = var5;
            if (!var3) {
               if (var5 != null) {
                  break label22;
               }

               var10000 = new Vec3((var4.minX + var4.maxX) / 2.0, (var4.minY + var4.maxY) / 2.0, (var4.minZ + var4.maxZ) / 2.0);
            }

            var5 = var10000;
         }

         double var6 = (var4.maxX - var4.minX) * var2 / 2.0;
         double var8 = (var4.maxY - var4.minY) * var2 / 2.0;
         double var10 = (var4.maxZ - var4.minZ) * var2 / 2.0;
         double var12 = var5.x + (this.z.nextDouble() - 0.5) * 2.0 * var6;
         double var14 = var5.y + (this.z.nextDouble() - 0.5) * 2.0 * var8;
         double var16 = var5.z + (this.z.nextDouble() - 0.5) * 2.0 * var10;
         var12 = Math.max(var4.minX, Math.min(var4.maxX, var12));
         var14 = Math.max(var4.minY, Math.min(var4.maxY, var14));
         var16 = Math.max(var4.minZ, Math.min(var4.maxZ, var16));
         Vec3 var18 = new Vec3(var12, var14, var16);
         Rotation var19 = com.elowen.utils.rotation.RotationUtils.X(G.player.getEyePosition(), var18);
         return var19.M();
      } else {
         return null;
      }
   }

   @EventTarget
   public void i(com.elowen.events.impl.EventClick var1) {
   }

   public Entity O$E() {
      boolean var10000 = Velocity.o$Z();
      Entity var2 = cj;
      boolean var1 = var10000;
      Entity var4 = var2;
      if (var1) {
         label22:
         if (var2 == null) {
            List var3 = this.A$List();
            Object var5 = var3;
            if (var1) {
               if (var3.isEmpty()) {
                  break label22;
               }

               var5 = var3.get(0);
            }

            var2 = (Entity)var5;
         }

         var4 = var2;
      }

      return var4;
   }

   private double o$D() {
      return G.player.isCreative() ? 5.0 : 3.0;
   }

   private Vector2f R(float var1) {
      float var2 = Mth.lerp(var1, G.player.yRotO, G.player.getYRot());
      float var3 = Mth.lerp(var1, G.player.xRotO, G.player.getXRot());
      return new Vector2f(var2, var3);
   }

   public void n$V() {
      boolean var1 = Velocity.o$Z();
      if (!l.isEmpty()) {
         HitResult var2 = G.hitResult;
         Entity var3 = null;
         if (var2 != null && var2.getType() == Type.ENTITY) {
            EntityHitResult var4 = (EntityHitResult)var2;
            if (AntiBots.g(var4.getEntity())) {
               com.elowen.utils.ChatUtils.b("Attacking Bot!");
               return;
            }

            var3 = var4.getEntity();
         }

         boolean var7 = false;
         if (cj != null) {
            float var5 = G.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            Vector2f var6 = this.R(var5);
            if (var6 != null && this.I(cj, var6, this.o$D())) {
               var7 = true;
            }
         }

         if (this.j.w()) {
            if (var3 != null && l.contains(var3)) {
               this.D(var3);
            }

            if (!var7) {
               return;
            }

            this.D(cj);
         }

         if (cj != null) {
            if (var3 == cj) {
               this.D(cj);
            }

            if (var7) {
               this.D(cj);
            }
         }
      }
   }

   public void c$V() {
      l = this.A$List();
   }

   public boolean K(Entity var1) {
      boolean var2 = Velocity.o$Z();
      if (var1 == G.player) {
         return false;
      }

      if (var1 instanceof LivingEntity var3) {
         ;
      }

      return false;
   }

   public boolean B$Z(Entity var1) {
      boolean var2 = Velocity.p();
      int var10000 = ((this.K(var1)) ? 1 : 0);
      if (!var2) {
         if (((var10000) != 0)) {
            Vec3 var3 = com.elowen.utils.rotation.RotationUtils.x(G.player.getEyePosition(), this.B$AABB(var1));
            double var5;
            var10000 = (var5 = var3.distanceTo(G.player.getEyePosition()) - this.cr.o$F()) == 0.0 ? 0 : (var5 < 0.0 ? -1 : 1);
            if (!var2) {
               if (var10000 > 0) {
                  return false;
               }

               var10000 = ((com.elowen.utils.rotation.RotationUtils.b(var1, this.V.o$F() / 2.0F)) ? 1 : 0);
            }

            if (var2) {
               return (boolean)((var10000) != 0);
            }

            if (var10000 != 0) {
               return true;
            }

            return false;
         }

         var10000 = 0;
      }

      return (boolean)((var10000) != 0);
   }

   private AABB B$AABB(Entity var1) {
      boolean var2 = Velocity.o$Z();
      if (var1 != null && com.elowen.utils.GhostHitBoxes.R$Z() && com.elowen.utils.GhostHitBoxes.n(var1.getUUID())) {
         return com.elowen.utils.GhostHitBoxes.k(var1.getUUID());
      } else {
         return var1 != null ? var1.getBoundingBox() : null;
      }
   }

   private boolean I(Entity var1, Vector2f var2, double var3) {
      boolean var5 = Velocity.o$Z();
      if (var1 == null || var2 == null) {
         return false;
      } else if (G.player == null) {
         return false;
      } else if (com.elowen.utils.GhostHitBoxes.R$Z() && com.elowen.utils.GhostHitBoxes.n(var1.getUUID())) {
         Vec3 var9 = G.player.getEyePosition();
         Vec3 var7 = com.elowen.utils.rotation.RotationUtils.n(new Rotation(var2.H, var2.E));
         Vec3 var8 = var9.add(var7.x * var3, var7.y * var3, var7.z * var3);
         return com.elowen.utils.GhostHitBoxes.k(var1.getUUID()).clip(var9, var8).isPresent();
      } else {
         HitResult var6 = com.elowen.utils.RayTraceUtils.G(new Rotation(var2), var3, 0.0F, G.player, var1, false, 1.0F);
         return var6 instanceof EntityHitResult && ((EntityHitResult)var6).getEntity() == var1;
      }
   }

   private Vector2f C(Entity var1, double var2) {
      boolean var4 = Velocity.o$Z();
      if (var1 != null && G.player != null && G.level != null) {
         float var5 = G.getDeltaTracker().getGameTimeDeltaPartialTick(false);
         Vec3 var6 = G.player.getEyePosition(var5);
         AABB var7 = this.B$AABB(var1);
         if (com.elowen.utils.rotation.RotationUtils.F(var7)) {
            return null;
         }

         ArrayList var8 = new ArrayList();
         double var9 = var7.minX;
         double var11 = var7.maxX;
         double var13 = var7.minY;
         double var15 = var7.maxY;
         double var17 = var7.minZ;
         double var19 = var7.maxZ;
         double var21 = (var13 + var15) * 0.5;
         var8.add(new Vec3((var9 + var11) * 0.5, var21, (var17 + var19) * 0.5));
         var8.add(new Vec3(var9, var15, var17));
         var8.add(new Vec3(var9, var15, var19));
         var8.add(new Vec3(var11, var15, var17));
         var8.add(new Vec3(var11, var15, var19));
         var8.add(new Vec3(var9, var13, var17));
         var8.add(new Vec3(var9, var13, var19));
         var8.add(new Vec3(var11, var13, var17));
         var8.add(new Vec3(var11, var13, var19));
         Vector2f var23 = e != null ? e : new Vector2f(G.player.getYRot(), G.player.getXRot());
         Vector2f var24 = null;
         float var25 = Float.POSITIVE_INFINITY;
         boolean var26 = com.elowen.utils.GhostHitBoxes.R$Z() && com.elowen.utils.GhostHitBoxes.n(var1.getUUID());
         Iterator var27 = var8.iterator();
         while (var27.hasNext()) {
            Vec3 var28 = (Vec3)var27.next();
            Rotation var29 = com.elowen.utils.rotation.RotationUtils.X(var6, var28);
            if (var26) {
               Vec3 var31 = com.elowen.utils.rotation.RotationUtils.n(var29);
               Vec3 var32 = var6.add(var31.x * var2, var31.y * var2, var31.z * var2);
               boolean var30 = var7.clip(var6, var32).isPresent();
            }

            HitResult var36 = com.elowen.utils.RayTraceUtils.G(var29, var2, 0.0F, G.player, var1, false, 1.0F);
            boolean var35 = var36 instanceof EntityHitResult && ((EntityHitResult)var36).getEntity() == var1;
            if (var35) {
               Vector2f var37 = var29.M();
               float var38 = Math.abs(com.elowen.utils.rotation.RotationUtils.e(var37.H, var23.H));
               float var33 = Math.abs(com.elowen.utils.rotation.RotationUtils.e(var37.E, var23.E));
               float var34 = var38 + var33 * 2.0F;
               if (var34 < var25) {
                  var24 = var37;
               }
            }
         }

         return var24;
      } else {
         return null;
      }
   }

   public void D(Entity var1) {
      boolean var2;
      int var3;
      boolean var10000 = Velocity.p();
      this.d = true;
      var2 = var10000;
      var3 = ((this.Y.w()) ? 1 : 0);
      label107:
      if (!var2) {
         label105:
         if (var3 != 0) {
            Aura var4 = this;
            if (!var2) {
               if (this.U <= 0) {
                  var3 = ((G.player.isSprinting()) ? 1 : 0);
                  if (var2) {
                     break label107;
                  }

                  if (var3 == 0) {
                     break label105;
                  }

                  var3 = ((this.c$Z()) ? 1 : 0);
                  if (var2) {
                     break label107;
                  }

                  if (var3 == 0) {
                     break label105;
                  }
               }

               var4 = this;
            }

            var4.U = 3;
         }

         var3 = ((var1 instanceof LivingEntity) ? 1 : 0);
      }

      label90: {
         if (!var2) {
            if (var3 == 0) {
               break label90;
            }

            float var7;
            var3 = (byte)((var7 = ((LivingEntity)var1).hurtTime - this.cc.o$F()) == 0.0F ? 0 : (var7 < 0.0F ? -1 : 1));
         }

         if (var3 > 0) {
            return;
         }
      }

      label84: {
         this.ch++;
         Vector2f var5 = e;
         if (!var2) {
            if (e == null) {
               break label84;
            }

            var5 = e;
         }

         com.elowen.utils.rotation.RotationManager.y(var5.H, e.E);
      }

      label111: {
         var10000 = var1 instanceof Player;
         label76:
         if (!var2) {
            if (var10000) {
               var10000 = AntiBots.g(var1);
               if (var2) {
                  break label76;
               }

               if (!var10000) {
                  KillSay.c.add(var1.getName().getString());
               }
            }

            G.gameMode.attack(G.player, var1);
            com.elowen.utils.PlayerUtils.s(InteractionHand.MAIN_HAND);
            if (var2) {
               break label111;
            }

            var10000 = this.cP.w();
         }

         if (!var10000) {
            return;
         }

         G.player.magicCrit(var1);
      }

      G.player.crit(var1);
   }

   private List A$List() {
      boolean var1;
      List var2;
      Comparator var6;
      label28: {
         boolean var10000 = Velocity.p();
         var2 = StreamSupport.<Entity>stream(G.level.entitiesForRendering().spliterator(), true).filter(this::B$Z).collect(Collectors.toList());
         var1 = var10000;
         Aura var5 = this;
         if (!var1) {
            if (this.c_.w()) {
               var6 = Comparator.comparingInt(Aura::deobfLambda$getTargets$0).thenComparing(this.p());
               break label28;
            }

            var5 = this;
         }

         var6 = var5.p();
      }

      Comparator var3 = var6;
      var2.sort(var3);
      int var7 = ((this.m.w()) ? 1 : 0);
      if (!var1) {
         if (var7 != 0) {
            return var2;
         }

         var7 = (int)Math.min(var2.size(), this.cF.o$F());
      }

      int var4 = var7;
      return new ArrayList(var2.subList(0, var4));
   }

   private Comparator p() {
      boolean var1 = Velocity.o$Z();
      if (this.c3.t("Range")) {
         return Comparator.comparingDouble(Aura::deobfLambda$getPriorityComparator$0);
      } else {
         String[] var3 = bb;
         if (this.c3.t("FoV")) {
            float var2 = com.elowen.utils.rotation.RotationManager.a$h().H;
            return Comparator.comparingDouble((Entity var10002) -> deobfLambda$getPriorityComparator$1(var2, var10002));
         } else {
            return this.c3.t("Health")
               ? Comparator.comparingDouble(Aura::deobfLambda$getPriorityComparator$2)
               : Comparator.comparingDouble(Aura::deobfLambda$getPriorityComparator$3);
         }
      }
   }

   public void t(boolean var1, float var2) {
      this.cv = var1;
      this.o = var2;
   }

   public float j$F() {
      return this.X.o$F();
   }

   private static double deobfLambda$getPriorityComparator$3(Entity var0) {
      return 0.0;
   }

   private static double deobfLambda$getPriorityComparator$2(Entity var0) {
      boolean var1 = Velocity.o$Z();
      return var0 instanceof LivingEntity var2 ? var2.getHealth() : 0.0;
   }

   private static double deobfLambda$getPriorityComparator$1(float var0, Entity var1) {
      return com.elowen.utils.rotation.RotationUtils.I(var0, com.elowen.utils.rotation.RotationUtils.B$h(var1).H);
   }

   private static double deobfLambda$getPriorityComparator$0(Entity var0) {
      return var0.distanceTo(G.player);
   }

   private static int deobfLambda$getTargets$0(Entity var0) {
      boolean var1 = Velocity.o$Z();
      return var0 instanceof LivingEntity var2 ? var2.hurtTime : Integer.MAX_VALUE;
   }

   private Boolean deobfLambda$new$2() {
      boolean var1 = Velocity.p();
      boolean var10000 = this.m.w();
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$1() {
      return this.v.w();
   }

   private Boolean deobfLambda$new$0() {
      return this.Y.w();
   }

   static {
      c8 = new float[]{0.78431374F, 0.0F, 0.0F, 0.23529412F};
      i = new float[]{0.0F, 0.78431374F, 0.0F, 0.23529412F};
      l = new ArrayList();
      r = false;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
