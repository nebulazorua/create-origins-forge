package io.github.nebulazorua.createorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

import io.github.apace100.origins.power.OriginsPowerTypes;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import net.minecraftforge.fluids.FluidType;
import net.minecraft.world.entity.LivingEntity;

@Mixin(DivingHelmetItem.class)
public abstract class DivingHelmetItemMixin {
    @Redirect(remap = false, method = "breatheUnderwater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canDrownInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"))
    private static boolean canDrownInFluidTypeProxy(LivingEntity entity, FluidType fluidtype) {
        if(IPowerContainer.hasPower(entity, OriginsPowerTypes.WATER_BREATHING.get()))
            return !entity.canDrownInFluidType(fluidtype);

        return entity.canDrownInFluidType(fluidtype);

    }
}