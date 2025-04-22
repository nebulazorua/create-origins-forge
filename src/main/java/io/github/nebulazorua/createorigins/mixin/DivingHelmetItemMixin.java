package io.github.nebulazorua.createorigins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;

import io.github.apace100.origins.power.OriginsPowerTypes;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.nebulazorua.createorigins.CreateOrigins;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

@Mixin(DivingHelmetItem.class)
public abstract class DivingHelmetItemMixin {

    @Redirect(remap = false, method = "breatheUnderwater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canDrownInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"))
    private static boolean canDrownInFluidTypeProxy(LivingEntity entity, FluidType fluidtype) {
        if(IPowerContainer.hasPower(entity, OriginsPowerTypes.WATER_BREATHING.get())){
			return CreateOrigins.requiresTank(entity);
		}

        return entity.canDrownInFluidType(fluidtype);
    }

	// From the original Create Origins
	@ModifyExpressionValue(method = "breatheUnderwater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;isFireResistant()Z"))
	private static boolean shouldNotProvideAirInLava(boolean original, LivingTickEvent event) {
		LivingEntity entity = event.getEntity();

		if (IPowerContainer.hasPower(entity, OriginsPowerTypes.WATER_BREATHING.get())) {
			return CreateOrigins.requiresTank(entity);
		}
		
		return original;
	}

	@ModifyVariable(remap = false, method = "breatheUnderwater", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/equipment/armor/BacktankUtil;consumeAir(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;F)V"), ordinal = 2)
	private static boolean modifyLavaDiving(boolean lavaDiving, LivingTickEvent event) {
		return lavaDiving && !CreateOrigins.requiresTank(event.getEntity());
	}

	@ModifyExpressionValue(remap = false, method = "breatheUnderwater", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;noneMatch(Ljava/util/function/Predicate;)Z"))
	private static boolean shouldCancelIfCopper(boolean original, LivingTickEvent event) {
		return original && !CreateOrigins.requiresTank(event.getEntity());
	}
}