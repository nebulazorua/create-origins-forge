package io.github.nebulazorua.createorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;

import io.github.apace100.origins.power.OriginsPowerTypes;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

@Mixin(RemainingAirOverlay.class)
public abstract class RemainingAirOverlayMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private boolean renderProxy(LocalPlayer player, TagKey<Fluid> fluidKey){
         if(IPowerContainer.hasPower(player, OriginsPowerTypes.WATER_BREATHING.get()))
            return !player.isEyeInFluid(fluidKey);

        return player.isEyeInFluid(fluidKey);
    }
}
