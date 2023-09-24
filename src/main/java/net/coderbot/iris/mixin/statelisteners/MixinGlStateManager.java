package net.coderbot.iris.mixin.statelisteners;

import com.mojang.blaze3d.platform.GlStateManager;
import net.coderbot.iris.gl.state.StateUpdateNotifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GlStateManager.class, remap = false)
public class MixinGlStateManager {
	private static Runnable blendFuncListener;

	@Inject(method = "_blendFunc", at = @At("RETURN"))
	private static void iris$onBlendFunc(int srcRgb, int dstRgb, CallbackInfo ci) {
		if (blendFuncListener != null) {
			blendFuncListener.run();
		}
	}

	@Inject(method = "_blendFuncSeparate", at = @At("RETURN"))
	private static void iris$onBlendFuncSeparate(int srcRgb, int dstRgb, int srcAlpha, int dstAlpha, CallbackInfo ci) {
		if (blendFuncListener != null) {
			blendFuncListener.run();
		}
	}

	static {
		StateUpdateNotifiers.blendFuncNotifier = listener -> blendFuncListener = listener;
	}
}
