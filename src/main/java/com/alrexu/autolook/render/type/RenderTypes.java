package com.alrexu.autolook.render.type;

import com.alrexu.autolook.render.aim.AimRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public abstract class RenderTypes extends RenderState {
	public static final RenderType AIM = RenderType.create(
			"aim",
			DefaultVertexFormats.POSITION_TEX,
			7, 64,
			RenderType.State.builder()
					.setTextureState(new TextureState(AimRenderer.AIM, false, true))
					.setCullState(NO_CULL)
					.setAlphaState(RenderState.DEFAULT_ALPHA)
					.setDepthTestState(NO_DEPTH_TEST)
					.createCompositeState(true)
	);

	public RenderTypes(String p_i225973_1_, Runnable p_i225973_2_, Runnable p_i225973_3_) {
		super(p_i225973_1_, p_i225973_2_, p_i225973_3_);
	}
}
