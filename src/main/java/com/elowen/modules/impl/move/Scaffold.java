package com.elowen.modules.impl.move;

import com.elowen.Elowen;
import com.elowen.events.api.EventTarget;
import com.elowen.events.api.types.EventType;
import com.elowen.events.impl.EventUpdate;
import com.elowen.events.impl.EventMoveInput;
import com.elowen.events.impl.EventRender2D;
import com.elowen.events.impl.EventClick;
import com.elowen.events.impl.EventUpdateFoV;
import com.elowen.mixin.accessors.KeyMappingAccessor;
import com.elowen.modules.Category;
import com.elowen.modules.Module;
import com.elowen.modules.ModuleInfo;
import com.elowen.modules.impl.move.scaffold.NormalMode;
import com.elowen.modules.impl.move.scaffold.TellyBridgeMode;
import com.elowen.modules.impl.move.scaffold.KeepYMode;
import com.elowen.modules.impl.move.scaffold.SnapMode;
import com.elowen.modules.impl.move.scaffold.rotation.NoneRotationHandler;
import com.elowen.modules.impl.move.scaffold.rotation.CurveRotationHandler;
import com.elowen.modules.impl.move.scaffold.rotation.AcaRotationHandler;
import com.elowen.modules.impl.move.scaffold.rotation.LinearRotationHandler;
import com.elowen.modules.impl.render.Theme;
import com.elowen.utils.PlayerUtils;
import com.elowen.utils.InventoryUtils;
import com.elowen.utils.Vector2f;
import com.elowen.utils.MoveUtils;
import com.elowen.utils.MathHelper;
import com.elowen.utils.renderer.RenderUtils;
import com.elowen.utils.renderer.SkiaRenderManager;
import com.elowen.utils.renderer.WorldSkiaRenderer;
import com.elowen.utils.renderer.ViewBob;
import com.elowen.utils.renderer.SkijaRenderer;
import com.elowen.utils.renderer.threeD.WorldProjector;
import com.elowen.utils.rotation.RotationUtils;
import com.elowen.values.HasValue;
import com.elowen.values.ValueBuilder;
import com.elowen.values.impl.BooleanValue;
import com.elowen.values.impl.FloatValue;
import com.elowen.values.impl.ModeValue;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult.Success;
import net.minecraft.world.InteractionResult.SwingSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(R = "Scaffold", a = "Automatically places blocks", M = Category.MOVEMENT)
public class Scaffold extends Module {
   public static final List v;
   public Vector2f I = new Vector2f();
   public Vector2f j = new Vector2f();
   public Vector2f J = new Vector2f();
   public ModeValue o;
   public ModeValue P;
   public BooleanValue f;
   public BooleanValue aB;
   public BooleanValue aa;
   public BooleanValue C;
   public BooleanValue c;
   public BooleanValue i;
   public FloatValue a5;
   public FloatValue y;
   public FloatValue r;
   public FloatValue E;
   public BooleanValue p;
   public BooleanValue l;
   public BooleanValue K;
   public FloatValue aR;
   public BooleanValue a_;
   public FloatValue aJ;
   private com.elowen.modules.impl.move.scaffold.rotation.RotationHandler m;
   int b;
   public BlockPos Y;
   public int t;
   public int V;
   public int z;
   public int e;
   public int R;
   public int aP;
   public static int U;
   public boolean q;
   public boolean al;
   public BlockPos B;
   public long Z;
   public long x;
   private long F;
   private boolean ak;
   private int d;
   private final SkijaRenderer as;
   private final com.elowen.utils.renderer.threeD.Skija3DRenderer Q;
   private com.elowen.modules.impl.move.scaffold.ScaffoldMode M;
   private final NormalMode D;
   private final SnapMode T;
   private final TellyBridgeMode X;
   private final KeepYMode S;
   private static boolean h;
   private static final String[] bb = new String[]{"None", "None", "点击", "Rotation Mode", "Keep FoV", "Heypixel", "Heypixel", "Render Item Spoof", "HUD Y", "ACA", "Normal", "Snap", "Sneak", "Telly Bridge", "Click", "Hide Snap Rotation", "ACA", "Normal", "Back Rotation Speed", "[Scaffold] HUD render failed: ", "Linear", "Block: ", "Swing", "Jump Sprint", "Curve", "Curve Rotation Ticks", "Block Count", "Telly Bridge", "Curve", "Rotation Speed", "Snap", "Packet Order N", "[Scaffold] ESP render failed: ", "Keep Y", "FoV", "Mode", "Eagle", "Place Delay (Ticks)", "Sideway Rotation", "Keep Y"};
   public Scaffold() {
      String[] var2 = bb;
      this.o = com.elowen.values.ValueBuilder.m(this, "Mode").m(0).W(new String[]{"Normal", "Snap", "Telly Bridge", "Keep Y"}).f$K().T$t();
      this.P = com.elowen.values.ValueBuilder.m(this, "Rotation Mode").m(0).W(new String[]{"Linear", "ACA", "None", "Curve", "Heypixel"}).f$K().T$t();
      this.f = com.elowen.values.ValueBuilder.m(this, "Eagle").h(false).l(this::deobfLambda$new$0).f$K().f$O();
      this.aB = com.elowen.values.ValueBuilder.m(this, "Sneak").h(true).f$K().f$O();
      this.aa = com.elowen.values.ValueBuilder.m(this, "Hide Snap Rotation").h(false).l(this::deobfLambda$new$1).f$K().f$O();
      this.C = com.elowen.values.ValueBuilder.m(this, "Render Item Spoof").h(false).f$K().f$O();
      this.c = com.elowen.values.ValueBuilder.m(this, "Keep FoV").h(false).f$K().f$O();
      this.i = com.elowen.values.ValueBuilder.m(this, "Jump Sprint").h(false).l(this::deobfLambda$new$2).f$K().f$O();
      this.a5 = com.elowen.values.ValueBuilder.m(this, "Rotation Speed").d(50.0F).M(180.0F).w(30.0F).V(1.0F).l(this::deobfLambda$new$3).f$K().L();
      this.y = com.elowen.values.ValueBuilder.m(this, "Back Rotation Speed").d(180.0F).M(180.0F).w(30.0F).V(1.0F).l(this::deobfLambda$new$4).f$K().L();
      this.r = com.elowen.values.ValueBuilder.m(this, "Place Delay (Ticks)").d(0.0F).M(2.0F).w(0.0F).V(1.0F).f$K().L();
      this.E = com.elowen.values.ValueBuilder.m(this, "FoV").d(1.15F).M(2.0F).w(1.0F).V(0.05F).l(this::deobfLambda$new$5).f$K().L();
      this.p = com.elowen.values.ValueBuilder.m(this, "Swing").h(false).f$K().f$O();
      this.l = com.elowen.values.ValueBuilder.m(this, "Packet Order N").h(false).f$K().f$O();
      this.K = com.elowen.values.ValueBuilder.m(this, "Block Count").h(true).f$K().f$O();
      this.aR = com.elowen.values.ValueBuilder.m(this, "HUD Y").d(-50.0F).M(500.0F).w(-500.0F).V(1.0F).l(this::deobfLambda$new$6).f$K().L();
      this.a_ = com.elowen.values.ValueBuilder.m(this, "Sideway Rotation").h(false).l(this::deobfLambda$new$7).f$K().f$O();
      this.aJ = com.elowen.values.ValueBuilder.m(this, "Curve Rotation Ticks").d(4.0F).M(20.0F).w(1.0F).V(1.0F).l(this::deobfLambda$new$8).f$K().L();
      this.t = 0;
      this.V = 0;
      boolean var10000 = S$Z();
      this.z = 0;
      this.e = 0;
      this.R = 0;
      boolean var1 = var10000;
      this.aP = 0;
      this.q = true;
      this.al = false;
      this.Z = -1L;
      this.x = -1L;
      this.F = 0L;
      this.ak = false;
      this.d = 0;
      this.as = com.elowen.utils.renderer.SkiaRenderManager.X$m();
      this.Q = new com.elowen.utils.renderer.threeD.Skija3DRenderer();
      this.D = new NormalMode(this);
      this.T = new SnapMode(this);
      this.X = new TellyBridgeMode(this);
      this.S = new KeepYMode(this);
      this.M = this.D;
      if (!var1) {
         com.elowen.values.HasValue.d(com.elowen.values.HasValue.X$Z());
      }
   }

   public Minecraft D$M() {
      return G;
   }

   public float X$F() {
      return this.a5.o$F();
   }

   public float v$F() {
      return this.y.o$F();
   }

   public com.elowen.modules.impl.move.scaffold.rotation.RotationHandler r() {
      boolean var1 = k();
      com.elowen.modules.impl.move.scaffold.rotation.RotationHandler var10000 = this.m;
      if (!var1) {
         if (this.m == null) {
            this.y$V();
         }

         var10000 = this.m;
      }

      return var10000;
   }

   private void y$V() {
      boolean var1;
      com.elowen.modules.impl.move.scaffold.rotation.RotationHandler var2;
      label76: {
         var1 = k();
         boolean var10000 = this.P.t("ACA");
         if (!var1) {
            if (var10000) {
               var2 = new AcaRotationHandler();
               if (!var1) {
                  break label76;
               }
            }

            var10000 = this.P.t("None");
         }

         if (!var1) {
            if (var10000) {
               var2 = new NoneRotationHandler();
               if (!var1) {
                  break label76;
               }
            }

            var10000 = this.P.t("Curve");
         }

         if (!var1) {
            if (var10000) {
               var2 = new CurveRotationHandler();
               if (!var1) {
                  break label76;
               }
            }

            var10000 = this.P.t("Heypixel");
         }

         if (var10000) {
            var2 = new com.elowen.modules.impl.move.scaffold.rotation.HeypixelRotationHandler();
            if (!var1) {
               break label76;
            }
         }

         var2 = new LinearRotationHandler();
      }

      Scaffold var3;
      var3 = this;
      label49:
      if (!var1) {
         if (this.m != null) {
            var3 = this;
            if (var1) {
               break label49;
            }

            if (this.m.getClass() == var2.getClass()) {
               return;
            }
         }

         var3 = this;
      }

      var3.m = var2;
   }

   private void O$h() {
      Scaffold var3 = null;
      boolean var1;
      com.elowen.modules.impl.move.scaffold.ScaffoldMode var2;
      label74: {
         var1 = k();
         boolean var10000 = this.o.t("Normal");
         if (!var1) {
            if (var10000) {
               var2 = this.D;
               if (!var1) {
                  break label74;
               }
            }

            var10000 = this.o.t("Snap");
         }

         if (!var1) {
            if (var10000) {
               var2 = this.T;
               if (!var1) {
                  break label74;
               }
            }

            var10000 = this.o.t("Telly Bridge");
         }

         label75: {
            if (!var1) {
               if (var10000) {
                  var2 = this.X;
                  if (!var1) {
                     break label74;
                  }
               }

               var3 = this;
               if (var1) {
                  break label75;
               }

               var10000 = this.o.t("Keep Y");
            }

            if (var10000) {
               var2 = this.S;
               if (!var1) {
                  break label74;
               }
            }

            var3 = this;
         }

         var2 = var3.D;
      }

      com.elowen.modules.impl.move.scaffold.ScaffoldMode var4 = this.M;
      if (!var1) {
         if (this.M == var2) {
            return;
         }

         this.M.F();
         this.M = var2;
         var4 = this.M;
      }

      var4.T$V();
   }

   public static boolean s(ItemStack var0) {
      boolean var1 = S$Z();
      if (var0 == null || !(var0.getItem() instanceof BlockItem) || var0.getCount() <= 0) {
         return false;
      } else if (!com.elowen.utils.InventoryUtils.p(var0)) {
         return false;
      } else {
         String var2 = var0.getDisplayName().getString();
         if (!var2.contains("Click") && !var2.contains("点击")) {
            Block var3 = ((BlockItem)var0.getItem()).getBlock();
            return !(var3 instanceof FlowerBlock) && !(var3 instanceof BushBlock) && !(var3 instanceof NetherFungusBlock) && !(var3 instanceof CropBlock)
               ? !(var3 instanceof SlabBlock) && !v.contains(var3)
               : false;
         } else {
            return false;
         }
      }
   }

   public InteractionHand y$InteractionHand() {
      boolean var1 = S$Z();
      if (G.player == null) {
         return null;
      }

      ItemStack var2 = G.player.getMainHandItem();
      if (s(var2)) {
         return InteractionHand.MAIN_HAND;
      }

      ItemStack var3 = G.player.getOffhandItem();
      return s(var3) ? InteractionHand.OFF_HAND : null;
   }

   public static Vec3 P(BlockPos var0, BlockState var1) {
      VoxelShape var2 = var1.getShape(G.level, var0);
      if (var2.isEmpty()) {
         return null;
      }

      Vec3 var3 = G.player.getEyePosition();
      double var4 = com.elowen.utils.MathHelper.P(var3.x, var0.getX(), var0.getX() + var2.max(Axis.X));
      double var6 = com.elowen.utils.MathHelper.P(var3.y, var0.getY(), var0.getY() + var2.max(Axis.Y));
      double var8 = com.elowen.utils.MathHelper.P(var3.z, var0.getZ(), var0.getZ() + var2.max(Axis.Z));
      return new Vec3(var4, var6, var8);
   }

   public static boolean c(float var0) {
      boolean var1 = k();
      boolean var10000 = G.level.getCollisions(G.player, G.player.getBoundingBox().move(0.0, -0.5, 0.0).inflate(-var0, 0.0, -var0)).iterator().hasNext();
      if (!var1) {
         var10000 = !var10000;
      }

      return var10000;
   }

   public boolean a$Z() {
      boolean var1 = k();
      Minecraft var10000 = G;
      if (!var1) {
         if (G.player == null) {
            return false;
         }

         var10000 = G;
      }

      if (!var1) {
         if (var10000.level == null) {
            return false;
         }

         var10000 = G;
      }

      BlockPos var2 = var10000.player.blockPosition().below();
      BlockState var3 = G.level.getBlockState(var2);
      return var3.isSolidRender();
   }

   public BlockHitResult i(Vector2f var1, BlockPos var2) {
      boolean var3 = S$Z();
      if (G.player != null && G.level != null && var2 != null && var1 != null) {
         BlockState var4 = G.level.getBlockState(var2);
         if (var4.getShape(G.level, var2).isEmpty()) {
            return null;
         } else {
            Vec3 var5 = G.player.getEyePosition();
            Vec3 var6 = Vec3.directionFromRotation(var1.p(), var1.S$F());
            double var7 = G.player.blockInteractionRange();
            Vec3 var9 = var5.add(var6.x * var7, var6.y * var7, var6.z * var7);
            HitResult var10 = com.elowen.utils.RayTraceUtils.c(var5, var9, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, G.player);
            if (var10 != null && var10.getType() == Type.BLOCK) {
               BlockHitResult var11 = (BlockHitResult)var10;
               return var11.getBlockPos().equals(var2) ? var11 : null;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public boolean e(Vector2f var1, BlockPos var2) {
      return this.i(var1, var2) != null;
   }

   public boolean D$Z(Vector2f var1) {
      boolean var9 = false;
      int var10000 = ((S$Z()) ? 1 : 0);
      InteractionHand var3 = this.y$InteractionHand();
      boolean var2 = (boolean)((var10000) != 0);
      if (this.Y != null && var3 != null && var1 != null) {
         var10000 = Elowen.U;
         if (var2) {
            if (Elowen.U <= 0) {
               BlockHitResult var4 = this.i(var1, this.Y);
               BlockHitResult var8 = var4;
               if (var2) {
                  if (var4 == null) {
                     return false;
                  }

                  var8 = var4;
               }

               label96: {
                  if (var8.getDirection() == Direction.UP) {
                     var9 = G.player.onGround();
                     if (!var2) {
                        break label96;
                     }

                     if (!var9) {
                        var9 = this.e();
                        if (!var2) {
                           break label96;
                        }

                        if (var9) {
                           var9 = this.s$Z();
                           if (!var2) {
                              break label96;
                           }

                           if (!var9) {
                              return false;
                           }
                        }
                     }
                  }

                  var9 = this.l.w();
               }

               if (var9) {
                  G.gameMode.useItem(G.player, var3);
               }

               InteractionResult var5 = G.gameMode.useItemOn(G.player, var3, var4);
               boolean var10 = var5 instanceof Success;
               if (var2) {
                  if (var10) {
                     Success var6 = (Success)var5;
                     if (var2) {
                        var10 = this.p.w();
                        if (var2) {
                           if (var10 && var6.swingSource() == SwingSource.PREDICTED) {
                              G.player.swing(InteractionHand.MAIN_HAND, G.player.getMainHandItem().getInteractAnimation(), false);
                              G.player.itemUsed(var3);
                           }

                           com.elowen.utils.PlayerUtils.x();
                           var10 = true;
                        }

                        return var10;
                     }
                  }

                  var10 = false;
               }

               return var10;
            }

            var10000 = 0;
         }

         return (boolean)((var10000) != 0);
      } else {
         return false;
      }
   }

   public boolean U$Z() {
      return this.D$Z(this.Q());
   }

   private void o$V() {
      boolean var1 = S$Z();
      if (G.player != null && G.level != null && G.getConnection() != null) {
         long var2 = this.F;
         if (var2 != this.x) {
            this.x = var2;
            Vector2f var4 = this.Q();
            G.getConnection().send(new Rot(var4.H, var4.E, G.player.onGround(), false));
         }
      }
   }

   private void t() {
      boolean var1 = k();
      Scaffold var10000 = this;
      if (!var1) {
         label85:
         if (this.Y != null) {
            Minecraft var5 = G;
            if (!var1) {
               if (G.player == null) {
                  break label85;
               }

               var5 = G;
            }

            if (var5.level != null) {
               var10000 = this;
               if (!var1) {
                  if (this.a$Z()) {
                     this.ak = false;
                     this.q = true;
                     this.z = 0;
                     this.e = 0;
                     return;
                  }

                  var10000 = this;
               }

               Vector2f var2 = var10000.Q();
               Vector2f var3 = new Vector2f(var2.H + 1.0E-4F * U, var2.E);
               int var4 = 0;
               int var7 = ((this.e(var3, this.Y)) ? 1 : 0);
               if (!var1) {
                  if (var7 != 0) {
                     var4 = ((this.D$Z(var3)) ? 1 : 0);
                  }

                  var7 = var4;
               }

               label74:
               if (!var1) {
                  if (var7 == 0) {
                     var7 = ((this.e(var2, this.Y)) ? 1 : 0);
                     if (var1) {
                        break label74;
                     }

                     if (var7 != 0) {
                        var4 = ((this.D$Z(var2)) ? 1 : 0);
                     }
                  }

                  var7 = var4;
               }

               label67: {
                  if (!var1) {
                     if (var7 != 0) {
                        this.Z = this.F;
                        this.aP = 0;
                        U *= -1;
                        if (!var1) {
                           break label67;
                        }
                     }

                     var7 = 0;
                  }

                  Elowen.U = var7;
                  this.aP = 3;
                  this.ak = false;
               }

               this.z = 0;
               this.e = 0;
               this.q = true;
               return;
            }
         }

         var10000 = this;
      }

      var10000.ak = false;
   }

   public Vector2f Q() {
      return G.player == null ? null : new Vector2f(G.player.getYRot(), G.player.getXRot());
   }

   public BlockPos B$BlockPos() {
      BlockPos var2 = BlockPos.containing(G.player.getX(), G.player.getY() - 1.0, G.player.getZ());
      int var10000 = ((S$Z()) ? 1 : 0);
      ArrayList var3 = new ArrayList();
      boolean var1 = (boolean)((var10000) != 0);
      HashMap var4 = new HashMap();
      int var5 = 0;
      double var6 = Math.abs(G.player.getDeltaMovement().y);
      double var22;
      var10000 = (var22 = var6 - 0.6) == 0.0 ? 0 : (var22 < 0.0 ? -1 : 1);
      if (var1) {
         if (var10000 > 0) {
            var5 = Math.min(6, (int)Math.ceil((var6 - 0.6) / 0.2));
         }

         var10000 = (int)Math.floor(G.player.getEyeY() - 5.0);
      }

      int var8 = var10000;
      int var9 = Math.max(var2.getY() - 1 - var5, var8);
      int var10 = var2.getX() - 5;

      label117:
      while (true) {
         var10000 = var10;

         label114:
         while (var10000 <= var2.getX() + 5) {
            var10000 = var9;
            if (!var1) {
               break label117;
            }

            int var11 = var9;

            label111:
            while (true) {
               var10000 = var11;

               label109:
               while (true) {
                  if (var10000 > var2.getY()) {
                     break label111;
                  }

                  var10000 = var2.getZ() - 5;
                  if (!var1) {
                     continue label114;
                  }

                  int var12 = var10000;

                  while (var12 <= var2.getZ() + 5) {
                     BlockPos var13 = new BlockPos(var10, var11, var12);
                     label103:
                     if (var1) {
                        var10000 = ((this.J(var13)) ? 1 : 0);
                        if (!var1) {
                           continue label109;
                        }

                        if (var10000 != 0) {
                           BlockState var14 = G.level.getBlockState(var13);
                           Vec3 var15 = P(var13, var14);
                           if (!var1) {
                              break label103;
                           }

                           if (var15 != null) {
                              var3.add(var15);
                              var4.put(var15, var13);
                           }
                        }

                        var12++;
                     }

                     if (!var1) {
                        break;
                     }
                  }

                  var11++;
                  if (!var1) {
                     break label111;
                  }
                  break;
               }
            }

            var10++;
            if (var1) {
               continue label117;
            }
            break;
         }

         var10000 = ((var3.isEmpty()) ? 1 : 0);
         break;
      }

      if (var1) {
         if (var10000 != 0) {
            return null;
         }

         var3.sort(Comparator.comparingDouble(this::T));
         if (!var1) {
            return var2;
         }

         var10000 = ((this.c$Z()) ? 1 : 0);
      }

      if (var10000 != 0) {
         BlockPos var20 = (BlockPos)var4.get(var3.get(0));
         if (!var1) {
            return var20;
         }

         if (var20.getY() != G.player.getY() - 1.5) {
            return BlockPos.containing(G.player.getX(), G.player.getY() - 1.5, G.player.getZ());
         }
      }

      return (BlockPos)var4.get(var3.get(0));
   }

   public boolean J(BlockPos var1) {
      boolean var10000 = S$Z();
      Block var3 = G.level.getBlockState(var1).getBlock();
      boolean var2 = var10000;
      var10000 = var3 instanceof LiquidBlock;
      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof AirBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof ChestBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof FurnaceBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof EnderChestBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof TallGrassBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof SnowLayerBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof EnchantingTableBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof AnvilBlock;
      }

      if (var2) {
         if (var10000) {
            return false;
         }

         var10000 = var3 instanceof CraftingTableBlock;
      }

      return !var2 ? var10000 : !var10000;
   }

   public boolean c$Z() {
      boolean var1 = S$Z();
      return this.s$Z() && !this.e();
   }

   private double T(Vec3 var1) {
      return G.player.getEyePosition().distanceToSqr(var1.x, var1.y, var1.z);
   }

   private boolean X(KeyMapping var1) {
      return InputConstants.isKeyDown(((KeyMappingAccessor)var1).elowen$getKey().getValue());
   }

   public boolean s$Z() {
      return this.X(G.options.keyJump);
   }

   private boolean e() {
      boolean var1 = k();
      LocalPlayer var10000 = G.player;
      if (!var1) {
         if (G.player == null) {
            return false;
         }

         var10000 = G.player;
      }

      double var3;
      int var2 = (var3 = var10000.getDeltaMovement().horizontalDistance() - 0.01) == 0.0 ? 0 : (var3 < 0.0 ? -1 : 1);
      return (boolean)(var1 ? var2 : var2 > 0);
   }

   public void I() {
      boolean var1 = S$Z();
      if (G.player != null) {
         boolean var2 = s(G.player.getMainHandItem());
         boolean var3 = s(G.player.getOffhandItem());
         if (!var2 && !var3) {
            int var4 = 0;
            while (var4 < 9) {
               ItemStack var5 = G.player.getInventory().getItem(var4);
               if (var5.getItem() instanceof BlockItem && s(var5)) {
                  if (G.player.getInventory().getSelectedSlot() == var4) {
                     return;
                  }

                  G.player.getInventory().setSelectedSlot(var4);
               }

               var4++;
            }
         }
      }
   }

   public float c$F() {
      boolean var1 = S$Z();
      return com.elowen.utils.rotation.RotationManager.X != null ? com.elowen.utils.MathHelper.N(com.elowen.utils.rotation.RotationManager.X.H) : G.player.getYRot();
   }

   public float a$F() {
      boolean var1 = k();
      Vector2f var10000 = com.elowen.utils.rotation.RotationManager.X;
      if (!var1) {
         if (com.elowen.utils.rotation.RotationManager.X == null) {
            return G.player.getXRot();
         }

         var10000 = com.elowen.utils.rotation.RotationManager.X;
      }

      return com.elowen.utils.MathHelper.K(var10000.E, -90.0F, 90.0F);
   }

   public Vector2f x$H() {
      boolean var10000 = k();
      float var2 = this.c$F() - 180.0F;
      boolean var1 = var10000;
      Scaffold var4 = this;
      if (!var1) {
         if (this.c$Z()) {
            HitResult var3 = G.hitResult;
            if (var3 != null) {
               return new Vector2f(var2, 90.0F);
            }
         }

         var4 = this;
      }

      return var4.B$h();
   }

   public Vector2f B$h() {
      boolean var1;
      Vector2f var4;
      float var5;
      float var6;
      label146: {
         boolean var10000 = k();
         float var2 = this.c$F() - 180.0F;
         float var3 = 82.0F;
         var1 = var10000;
         var4 = new Vector2f(var2, var3);
         var5 = this.c$F();
         var6 = RandomUtils.nextFloat(0.0F, 0.5F) - 0.25F;
         var10000 = G.options.keyDown.isDown();
         if (!var1) {
            if (var10000) {
               var5 += 180.0F;
               var10000 = G.options.keyLeft.isDown();
               if (!var1) {
                  if (var10000) {
                     var5 += 45.0F;
                     if (!var1) {
                        break label146;
                     }
                  }

                  var10000 = G.options.keyRight.isDown();
               }

               if (!var10000) {
                  break label146;
               }

               var5 -= 45.0F;
               if (!var1) {
                  break label146;
               }
            }

            var10000 = G.options.keyUp.isDown();
         }

         if (!var1) {
            if (var10000) {
               var10000 = G.options.keyLeft.isDown();
               if (!var1) {
                  if (var10000) {
                     var5 -= 45.0F;
                     if (!var1) {
                        break label146;
                     }
                  }

                  var10000 = G.options.keyRight.isDown();
               }

               if (!var10000) {
                  break label146;
               }

               var5 += 45.0F;
               if (!var1) {
                  break label146;
               }
            }

            var10000 = G.options.keyRight.isDown();
         }

         if (!var1) {
            if (var10000) {
               var5 += 90.0F;
               if (!var1) {
                  break label146;
               }
            }

            var10000 = G.options.keyLeft.isDown();
         }

         if (var10000) {
            var5 -= 90.0F;
         }
      }

      ArrayList var14;
      ArrayList var23;
      label155: {
         float var7 = var5 - 180.0F + var6;
         boolean var19 = this.a_.w();
         if (!var1) {
            if (var19) {
               float var8 = this.j.H;
               float var9 = var7 - 45.0F;
               float var10 = var7 + 45.0F;
               float var11 = Math.abs(com.elowen.utils.MathHelper.N(var9 - var8));
               float var12 = Math.abs(com.elowen.utils.MathHelper.N(var10 - var8));
               float var20 = var11;
               if (!var1) {
                  var20 = var11 <= var12 ? var9 : var10;
               }

               var7 = var20;
            }

            if (var1) {
               return var4;
            }

            var4.Z(var7);
            var19 = this.N();
         }

         if (var19) {
            HitResult var13 = this.D$HitResult(var4);
            Scaffold var22 = this;
            if (!var1) {
               if (this.f(var13)) {
                  return var4;
               }

               var22 = this;
            }

            var14 = var22.j(var7);
            var23 = var14;
            if (var1) {
               break label155;
            }

            if (!var14.isEmpty()) {
               var23 = var14;
               break label155;
            }

            Vector2f var15 = this.N(var7);
            if (var1) {
               return var15;
            }

            if (var15 != null) {
               return var15;
            }
         }

         return var4;
      }

      var23.sort(Comparator.comparingDouble(this::K));
      var4.t((Float)var14.get(0));
      return var4;
   }

   private boolean N() {
      BlockPos var1 = BlockPos.containing(G.player.getX(), G.player.getY() - 0.5, G.player.getZ());
      return G.level.isEmptyBlock(var1) && this.y$InteractionHand() != null;
   }

   private ArrayList j(float var1) {
      boolean var10000 = k();
      ArrayList var3 = new ArrayList();
      boolean var2 = var10000;
      float var4 = Math.max(this.j.E - 30.0F, -90.0F);

      while (var4 < Math.min(this.j.E + 20.0F, 90.0F)) {
         Vector2f var5 = com.elowen.utils.rotation.RotationUtils.w(var1, var4, this.j.H, this.j.E);
         HitResult var6 = this.D$HitResult(new Vector2f(var1, var5.E));
         if (!var2) {
            if (this.f(var6)) {
               var3.add(var5.E);
            }

            var4 += 0.3F;
         }

         if (var2) {
            break;
         }
      }

      return var3;
   }

   private HitResult D$HitResult(Vector2f var1) {
      return com.elowen.utils.RayTraceUtils.B(1.0F, var1);
   }

   private boolean f(HitResult var1) {
      boolean var2 = k();
      HitResult var10000 = var1;
      if (!var2) {
         if (var1.getType() != Type.BLOCK) {
            return false;
         }

         var10000 = var1;
      }

      BlockHitResult var3 = (BlockHitResult)var10000;
      boolean var4 = this.J(var3.getBlockPos());
      if (!var2) {
         if (!var4) {
            return false;
         }

         var4 = this.W(var3.getBlockPos());
      }

      if (var4) {
         Direction var5 = var3.getDirection();
         Direction var10001 = Direction.DOWN;
         if (!var2) {
            if (var5 == Direction.DOWN) {
               return false;
            }

            var5 = var3.getDirection();
            var10001 = Direction.UP;
         }

         if (var5 != var10001) {
            return true;
         }
      }

      return false;
   }

   private boolean W(BlockPos var1) {
      boolean var2 = k();
      int var10000 = ((G.player.onGround()) ? 1 : 0);
      if (!var2) {
         if (var10000 == 0) {
            return var1.equals(this.Y);
         }

         var10000 = this.Y.getX() - 1;
      }

      int var3 = var10000;

      label58:
      while (true) {
         var10000 = var3;

         label54:
         while (true) {
            if (var10000 <= this.Y.getX() + 1) {
               var10000 = this.Y.getZ() - 1;
               if (var2) {
                  break;
               }

               int var4 = var10000;

               while (var4 <= this.Y.getZ() + 1) {
                  var10000 = ((var1.equals(new BlockPos(var3, this.Y.getY(), var4))) ? 1 : 0);
                  if (var2) {
                     continue label54;
                  }

                  if (var2) {
                     return (boolean)((var10000) != 0);
                  }

                  if (var10000 != 0) {
                     return true;
                  }

                  var4++;
                  if (var2) {
                     break;
                  }
               }

               var3++;
               if (!var2) {
                  continue label58;
               }
            }

            var10000 = 0;
            break;
         }

         return (boolean)((var10000) != 0);
      }
   }

   private double K(float var1) {
      return Math.abs(var1 - this.j.E);
   }

   private Vector2f N(float var1) {
      float var18 = 0.0F;
      int var10000 = ((k()) ? 1 : 0);
      float var3 = 0.0F;
      boolean var2 = (boolean)((var10000) != 0);

      label83:
      while (true) {
         float var19;
         var10000 = (var19 = var3 - 180.0F) == 0.0F ? 0 : (var19 < 0.0F ? -1 : 1);

         label81:
         while (var10000 < 0) {
            float var4 = this.j.E;
            float var5 = 0.0F;

            label78:
            while (var5 < 25.0F) {
               var10000 = 0;
               if (var2) {
                  continue label81;
               }

               int var6 = 0;

               label76:
               while (true) {
                  var10000 = var6;

                  float var10001;
                  label73:
                  while (true) {
                     if (var10000 < 2) {
                        var18 = var4;
                        var10001 = var5;
                        if (var2) {
                           break;
                        }

                        int var10002 = var6;
                        if (!var2) {
                           var10002 = var6 == 0 ? 1 : -1;
                        }

                        float var7 = var4 - var5 * var10002;
                        float[][] var8 = new float[][]{{var1 + var3, var7}, {var1 - var3, var7}};
                        float[][] var9 = var8;
                        int var10 = var9.length;
                        int var11 = 0;

                        while (var11 < var10) {
                           float[] var12 = var9[var11];
                           float var13 = com.elowen.utils.MathHelper.K(var12[1], -90.0F, 90.0F);
                           Vector2f var14 = com.elowen.utils.rotation.RotationUtils.w(var12[0], var13, this.j.H, this.j.E);
                           HitResult var15 = this.D$HitResult(var14);
                           if (!var2) {
                              var10000 = ((this.f(var15)) ? 1 : 0);
                              if (var2) {
                                 continue label73;
                              }

                              if (var10000 != 0) {
                                 return var14;
                              }

                              var11++;
                           }

                           if (var2) {
                              break;
                           }
                        }

                        var6++;
                        if (!var2) {
                           continue label76;
                        }
                     }

                     var18 = var5;
                     var10001 = 1.0F;
                     break;
                  }

                  var5 = var18 + var10001;
                  if (var2) {
                     break label78;
                  }
                  break;
               }
            }

            var3++;
            if (var2) {
               return null;
            }
            continue label83;
         }

         return null;
      }
   }

   public void w(BlockPos var1) {
      boolean var2 = S$Z();
      if (G.options.keyUse.isDown() && var1 != null && G.level != null) {
         long var3 = this.F;
         int var5 = (int)this.r.o$F();
         if (var5 <= 0 || this.Z < 0L || var3 - this.Z >= var5) {
            if (this.e(this.Q(), var1)) {
               boolean var6 = this.U$Z();
               if (var6) {
                  this.Z = var3;
                  this.z = 0;
                  this.e = 0;
                  this.q = true;
               }
            }
         }
      }
   }

   public void R(BlockPos var1) {
      boolean var2 = S$Z();
      if (G.player != null && G.gui.screen() == null && var1 != null) {
         if (this.F != this.Z) {
            this.c(var1);
         }
      }
   }

   public void c(BlockPos var1) {
      int var20 = 0;
      Scaffold var24 = null;
      boolean var2 = k();
      if (var1 != null) {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.player.getDeltaMovement().y < -0.1) {
               double var3 = G.player.getX();
               double var5 = G.player.getY();
               double var7 = G.player.getZ();
               double var9 = G.player.getDeltaMovement().x;
               double var11 = G.player.getDeltaMovement().y;
               double var13 = G.player.getDeltaMovement().z;
               int var15 = 0;

               while (true) {
                  label214:
                  if (var15 < 2) {
                     double var19 = var11 - 0.08;
                     var11 = var19 * 0.98;
                     var3 += var9;
                     var5 += var11;
                     var7 += var13;
                     if (!var2) {
                        double var27;
                        var20 = (var27 = var11 - 0.0) == 0.0 ? 0 : (var27 < 0.0 ? -1 : 1);
                        if (var2) {
                           break;
                        }

                        if (var20 < 0 && !G.level.isEmptyBlock(BlockPos.containing(var3, var5 - 0.5, var7))) {
                           var5 = Math.floor(var5) + 0.5;
                           var11 = 0.0;
                           if (!var2) {
                              break label214;
                           }
                        }

                        var15++;
                     }

                     if (!var2) {
                        continue;
                     }
                  }

                  double var28;
                  var20 = (var28 = var1.getY() - var5) == 0.0 ? 0 : (var28 < 0.0 ? -1 : 1);
                  break;
               }

               if (var20 > 0) {
                  this.q = false;
               }
            }

            var10000 = G;
         }

         BlockState var16 = var10000.level.getBlockState(var1);
         Vec3 var4 = P(var1, var16);
         Vec3 var21 = var4;
         if (!var2) {
            if (var4 == null) {
               return;
            }

            var21 = var4.subtract(G.player.getEyePosition());
         }

         double var29;
         int var22 = (var29 = var21.lengthSqr() - 20.25) == 0.0 ? 0 : (var29 < 0.0 ? -1 : 1);
         if (!var2) {
            if (var22 > 0) {
               return;
            }

            var22 = (int)this.r.o$F();
         }

         label195: {
            label194: {
               int var17 = var22;
               int var23 = var17;
               if (!var2) {
                  if (var17 <= 0) {
                     break label194;
                  }

                  var24 = this;
                  if (var2) {
                     break label195;
                  }

                  long var30;
                  var23 = (var30 = this.Z - 0L) == 0L ? 0 : (var30 < 0L ? -1 : 1);
               }

               if (var23 >= 0) {
                  var24 = this;
                  if (var2) {
                     break label195;
                  }

                  if (this.F - this.Z < var17) {
                     return;
                  }
               }
            }

            var24 = this;
         }

         Vector2f var6 = var24.Q();
         int var25 = ((this.e(var6, var1)) ? 1 : 0);
         if (!var2) {
            if (var25 == 0) {
               return;
            }

            var25 = this.aP;
         }

         label179:
         if (!var2) {
            label177:
            if (var25 <= 0) {
               var25 = ((this.q) ? 1 : 0);
               if (!var2) {
                  if (this.q) {
                     var25 = this.R;
                     if (var2) {
                        break label179;
                     }

                     if (this.R <= 0) {
                        break label177;
                     }
                  }

                  var25 = this.z;
               }

               if (var2) {
                  break label179;
               }

               if (var25 < 8) {
                  int var26 = this.e;
                  if (!var2) {
                     if (this.e >= 7) {
                        this.q = true;
                        this.z = 0;
                        return;
                     }

                     var26 = ((this.a$Z()) ? 1 : 0);
                  }

                  if (!var2) {
                     if (var26 != 0) {
                        this.q = true;
                        this.z = 0;
                        return;
                     }

                     var26 = ((this.ak) ? 1 : 0);
                  }

                  if (!var2) {
                     if (var26 != 0) {
                        return;
                     }

                     var26 = Elowen.U;
                  }

                  if (!var2) {
                     if (var26 > 0) {
                        return;
                     }

                     var26 = 1;
                  }

                  Elowen.U = var26;
                  this.e++;
                  this.z++;
                  this.ak = true;
                  this.o$V();
                  if (!var2) {
                     return;
                  }
               }
            }

            var25 = ((this.D$Z(var6)) ? 1 : 0);
         }

         int var18 = var25;
         if (!var2) {
            if (var18 != 0) {
               this.Z = this.F;
            }

            this.z = 0;
         }

         this.e = 0;
      }
   }

   public void E(com.elowen.events.impl.EventPacket var1) {
      boolean var10000 = S$Z();
      Packet var4 = var1.R$Packet();
      boolean var2 = var10000;
      Packet var7 = var4;
      if (var2) {
         if (!(var4 instanceof ClientboundSetEntityMotionPacket)) {
            return;
         }

         var7 = var4;
      }

      ClientboundSetEntityMotionPacket var3 = (ClientboundSetEntityMotionPacket)var7;
      if (G.player != null && var3.id() == com.elowen.utils.PlayerUtils.S$I()) {
         double var6 = new Vec3(var3.movement().x, 0.0, var3.movement().z).lengthSqr();
         if (var6 >= 1.5) {
            this.R = 60;
         }
      }
   }

   public void p(EventUpdate var1) {
      label21: {
         label20: {
            boolean var2 = k();
            if (!var2) {
               if (!G.player.onGround()) {
                  break label20;
               }

               this.V = 0;
            }

            if (!var2) {
               break label21;
            }
         }

         this.V++;
      }

      this.S$V();
   }

   private void S$V() {
      ItemStack var5 = null;
      boolean var10000 = S$Z();
      this.d = 0;
      boolean var1 = var10000;
      if (G.player != null) {
         int var2 = 0;

         while (true) {
            if (var2 < 9) {
               ItemStack var3 = G.player.getInventory().getItem(var2);
               if (var1) {
                  var5 = var3;
                  if (!var1) {
                     break;
                  }

                  if (var3.getItem() instanceof BlockItem && s(var3)) {
                     this.d = this.d + var3.getCount();
                  }

                  var2++;
               }

               if (var1) {
                  continue;
               }
            }

            var5 = G.player.getOffhandItem();
            break;
         }

         ItemStack var4 = var5;
         var10000 = var4.getItem() instanceof BlockItem;
         if (var1) {
            if (!var10000) {
               return;
            }

            var10000 = s(var4);
         }

         if (var10000) {
            this.d = this.d + var4.getCount();
         }
      }
   }

   @Override
   public void h$V() {
      boolean var1 = k();
      if (!var1) {
         if (G.player == null) {
            return;
         }

         this.b = G.player.getInventory().getSelectedSlot();
         this.j.D(this.c$F(), this.a$F());
         this.J.D(G.player.yRotO, G.player.xRotO);
         this.Y = null;
         this.B = null;
         this.R = 0;
         this.z = 0;
         this.e = 0;
         this.q = true;
         this.al = false;
         this.t = 0;
         this.Z = -1L;
         this.x = -1L;
         this.F = 0L;
         this.aP = 0;
         this.V = 0;
         this.ak = false;
         this.I();
         this.O$h();
         this.y$V();
         this.M.T$V();
      }
   }

   @Override
   public void q$V() {
      boolean var1 = k();
      if (!var1) {
         if (G.player != null) {
            G.player.getInventory().setSelectedSlot(this.b);
            G.options.keyJump.setDown(this.X(G.options.keyJump));
            G.options.keyShift.setDown(this.X(G.options.keyShift));
            G.options.keySprint.setDown(this.X(G.options.keySprint));
            G.options.keyUse.setDown(false);
         }

         Elowen.U = 0;
         this.M.F();
      }
   }

   @EventTarget
   public void A(EventRender2D var1) {
      boolean var2 = S$Z();
      if (this.K.w() && G.player != null) {
         this.S$V();
         int var3 = G.getWindow().getGuiScaledWidth();
         int var4 = G.getWindow().getGuiScaledHeight();
         float var5 = this.aR.o$F();
         String[] var19 = bb;
         String var6 = "Block: " + this.d;
         float var7 = 9.5564F;
         Typeface var8 = com.elowen.utils.renderer.Fonts.H(var7).getTypeface();
         float var9 = this.as.W(var6, var8, var7);
         float var10 = var9 + 10.0F;
         float var11 = 20.5F;
         float var12 = (var3 - var10) / 2.0F;
         float var13 = var4 / 2 - var5;
         float var14 = 5.0F;
         Theme var15 = Theme.K();
         int var16 = var15 != null ? var15.g$I() : new Color(6, 176, 241, 255).getRGB();
         int var17 = var15 != null ? Theme.f : new Color(20, 20, 20, 180).getRGB();

         try {
            Canvas var18 = this.as.G$Canvas();
            if (var18 == null) {
               return;
            }

            com.elowen.utils.renderer.SkijaEffects.T(this.as, var18, var12, var13, var10, var11, var14, List.of(new float[]{var12, var13, var10, var11}));
            var18.save();
            var18.clipRRect(RRect.makeLTRB(var12, var13, var12 + var10, var13 + var11, var14), true);
            this.as.d(var12, var13, var10, var11, var14, var17);
            this.as.d(var12, var13, var10, 3.0F, 0.0F, var16);
            this.as.Y(var6, var12 + 5.0F, var13 + 6.0F, var8, var7, -1);
            var18.restore();
            this.as.p();
            this.as.G$V();
         } catch (RuntimeException var20) {
            System.err.println("[Scaffold] HUD render failed: " + var20);
         }
      }
   }

   @EventTarget
   public void e(com.elowen.events.impl.EventRender var1) {
      boolean var2 = S$Z();
      if (this.B != null && G.level != null && G.player != null) {
         try {
            Camera var3 = G.gameRenderer.mainCamera();
            if (!var3.isInitialized()) {
               return;
            }

            Canvas var4 = com.elowen.utils.renderer.WorldSkiaRenderer.A$Canvas();
            if (var4 == null) {
               return;
            }

            this.Q.I(var4, new WorldProjector(var3, com.elowen.utils.renderer.WorldSkiaRenderer.c$I(), com.elowen.utils.renderer.WorldSkiaRenderer.n$I(), com.elowen.utils.renderer.ViewBob.g$Matrix4f()));
            int var5 = com.elowen.utils.renderer.RenderUtils.O(51, 115, 255, 64);
            int var6 = com.elowen.utils.renderer.RenderUtils.O(51, 115, 255, 255);
            this.Q
               .u(
                  this.B.getX() - 0.002,
                  this.B.getY() - 0.002,
                  this.B.getZ() - 0.002,
                  this.B.getX() + 1.002,
                  this.B.getY() + 1.002,
                  this.B.getZ() + 1.002,
                  var5,
                  var6,
                  2.0F
               );
            this.Q.G$V();
            com.elowen.utils.renderer.WorldSkiaRenderer.c$V();
            if (!this.Q.s$Z()) {
               com.elowen.utils.renderer.WorldSkiaRenderer.n$V();
            }
         } catch (RuntimeException var7) {
            System.err.println("[Scaffold] ESP render failed: " + var7);
         }
      }
   }

   @EventTarget
   public void Q(EventUpdateFoV var1) {
      boolean var2 = S$Z();
      if (this.c.w() && com.elowen.utils.MoveUtils.V()) {
         var1.F(this.E.o$F() + com.elowen.utils.PlayerUtils.V() * 0.13F);
      }
   }

   @EventTarget
   public void b(com.elowen.events.impl.EventUpdateHeldItem var1) {
      boolean var2 = k();
      if (this.C.w()) {
         com.elowen.events.impl.EventUpdateHeldItem var10000 = var1;
         if (!var2) {
            if (var1.j$InteractionHand() != InteractionHand.MAIN_HAND) {
               return;
            }

            var10000 = var1;
         }

         var10000.w(G.player.getInventory().getItem(this.b));
      }
   }

   @EventTarget(1)
   public void c(com.elowen.events.impl.EventTick var1) {
      int var4 = 0;
      boolean var2 = k();
      if (!var2) {
         if (var1.s$f() != com.elowen.events.api.types.EventType.PRE) {
            return;
         }

         this.F++;
      }

      LocalPlayer var3;
      label132: {
         Minecraft var10000 = G;
         if (!var2) {
            if (G.gui.screen() == null) {
               var3 = G.player;
               if (var2) {
                  break label132;
               }

               if (G.player != null) {
                  this.y$V();
                  this.I();
                  this.O$h();
                  this.Y = this.B$BlockPos();
                  this.B = this.Y;
                  var4 = ((this.ak) ? 1 : 0);
                  label123:
                  if (!var2) {
                     if (this.ak) {
                        this.t();
                        var4 = ((this.ak) ? 1 : 0);
                        if (var2) {
                           break label123;
                        }

                        if (this.ak) {
                           this.o$V();
                           Elowen.U = 1;
                        }
                     }

                     var4 = ((this.aB.w()) ? 1 : 0);
                  }

                  label116:
                  if (!var2) {
                     label114:
                     if (var4 != 0) {
                        this.t++;
                        var4 = this.t;
                        byte var10001 = 18;
                        if (!var2) {
                           if (this.t == 18) {
                              Minecraft var5 = G;
                              if (!var2) {
                                 if (G.player.isSprinting()) {
                                    G.options.keySprint.setDown(false);
                                    G.player.setSprinting(false);
                                 }

                                 var5 = G;
                              }

                              var5.options.keyShift.setDown(true);
                              if (!var2) {
                                 break label114;
                              }
                           }

                           var4 = this.t;
                           if (var2) {
                              break label116;
                           }

                           var10001 = 21;
                        }

                        if (var4 >= var10001) {
                           G.options.keyShift.setDown(false);
                           this.t = 0;
                        }
                     }

                     this.al = this.s$Z();
                     this.M.Y(var1, this.Y);
                     var4 = this.R;
                  }

                  if (!var2) {
                     if (var4 > 0) {
                        this.R--;
                     }

                     var4 = this.aP;
                  }

                  if (!var2) {
                     if (var4 <= 0) {
                        return;
                     }

                     this.aP--;
                     var4 = 0;
                  }

                  Elowen.U = var4;
                  return;
               }
            }

            var10000 = G;
         }

         var3 = var10000.player;
      }

      if (var3 != null) {
         this.j.D(this.c$F(), this.a$F());
      }
   }

   @EventTarget
   public void a(EventClick var1) {
      var1.c(true);
      this.O$h();
      this.M.v(var1, this.Y);
   }

   @EventTarget
   public void N(com.elowen.events.impl.EventPacket var1) {
      this.O$h();
      this.M.a(var1);
   }

   @EventTarget
   public void Z(EventUpdate var1) {
      this.O$h();
      this.M.q(var1);
   }

   @EventTarget
   public void e(EventMoveInput var1) {
      this.O$h();
      this.M.z(var1);
   }

   private Boolean deobfLambda$new$8() {
      return this.P.t("Curve");
   }

   private Boolean deobfLambda$new$7() {
      boolean var1 = S$Z();
      return this.o.t("Normal") || this.o.t("Telly Bridge") || this.o.t("Keep Y");
   }

   private Boolean deobfLambda$new$6() {
      return this.K.w();
   }

   private Boolean deobfLambda$new$5() {
      return this.c.w();
   }

   private Boolean deobfLambda$new$4() {
      boolean var1 = k();
      boolean var10000 = this.o.t("Normal");
      if (!var1) {
         if (!var10000) {
            var10000 = this.o.t("Telly Bridge");
            if (var1) {
               return var10000;
            }

            if (!var10000) {
               var10000 = this.o.t("Keep Y");
               if (var1) {
                  return var10000;
               }

               if (!var10000) {
                  return false;
               }
            }
         }

         var10000 = true;
      }

      return var10000;
   }

   private Boolean deobfLambda$new$3() {
      boolean var1 = S$Z();
      return !this.P.t("Heypixel") && (this.o.t("Normal") || this.o.t("Telly Bridge") || this.o.t("Keep Y") || this.P.t("Curve"));
   }

   private Boolean deobfLambda$new$2() {
      return this.o.t("Normal");
   }

   private Boolean deobfLambda$new$1() {
      return this.o.t("Snap");
   }

   private Boolean deobfLambda$new$0() {
      boolean var1 = S$Z();
      return this.o.t("Normal") || this.o.t("Snap");
   }

   static {
      u(true);
      v = Arrays.asList(
      Blocks.AIR,
      Blocks.WATER,
      Blocks.LAVA,
      Blocks.ENCHANTING_TABLE,
      Blocks.GLASS_PANE,
      Blocks.IRON_BARS,
      Blocks.SNOW,
      Blocks.COAL_ORE,
      Blocks.DIAMOND_ORE,
      Blocks.EMERALD_ORE,
      Blocks.CHEST,
      Blocks.TRAPPED_CHEST,
      Blocks.TORCH,
      Blocks.ANVIL,
      Blocks.NOTE_BLOCK,
      Blocks.JUKEBOX,
      Blocks.TNT,
      Blocks.GOLD_ORE,
      Blocks.IRON_ORE,
      Blocks.LAPIS_ORE,
      Blocks.STONE_PRESSURE_PLATE,
      Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
      Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
      Blocks.STONE_BUTTON,
      Blocks.LEVER,
      Blocks.TALL_GRASS,
      Blocks.TRIPWIRE,
      Blocks.TRIPWIRE_HOOK,
      Blocks.RAIL,
      Blocks.CORNFLOWER,
      Blocks.RED_MUSHROOM,
      Blocks.BROWN_MUSHROOM,
      Blocks.VINE,
      Blocks.SUNFLOWER,
      Blocks.LADDER,
      Blocks.FURNACE,
      Blocks.SAND,
      Blocks.CACTUS,
      Blocks.DISPENSER,
      Blocks.DROPPER,
      Blocks.CRAFTING_TABLE,
      Blocks.COBWEB,
      Blocks.PUMPKIN,
      Blocks.COBBLESTONE_WALL,
      Blocks.OAK_FENCE,
      Blocks.REDSTONE_TORCH,
      Blocks.FLOWER_POT
      );
      U = 1;
   }

   public static void u(boolean var0) {
      h = var0;
   }

   public static boolean S$Z() {
      return h;
   }

   public static boolean k() {
      return !S$Z();
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
