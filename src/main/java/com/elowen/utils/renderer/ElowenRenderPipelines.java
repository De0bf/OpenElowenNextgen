package com.elowen.utils.renderer;

import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline.Snippet;
import java.util.Optional;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;

public final class ElowenRenderPipelines {
   public static final RenderPipeline i;

   private ElowenRenderPipelines() {
   }

   static {
      i = RenderPipeline.builder(new Snippet[]{RenderPipelines.GLOBALS_SNIPPET})
         .withLocation("elowen/pipeline/blit")
         .withVertexShader("core/screenquad")
         .withFragmentShader("core/blit_screen")
         .withBindGroupLayout(BindGroupLayouts.IN_SAMPLER)
         .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA), GpuFormat.RGBA8_UNORM, 7))
         .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
         .build();
   }
}
