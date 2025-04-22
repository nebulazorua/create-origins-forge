package io.github.nebulazorua.createorigins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.nebulazorua.createorigins.compat.EstrogenEventHandler;
import io.github.apace100.origins.power.OriginsPowerTypes;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateOrigins.MODID)
public class CreateOrigins
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createorigins";
    public static final DeferredRegister<PowerFactory<?>> POWER_FACTORIES = DeferredRegister.create(ApoliRegistries.POWER_FACTORY_KEY, MODID);
    
	// Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger(); 

	public CreateOrigins()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        POWER_FACTORIES.register(modEventBus);
        OriginsPowers.register();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GogglesItem.addIsWearingPredicate(player -> IPowerContainer.hasPower(player, OriginsPowers.MECHANICAL_SIGHT.get()));
        if (ModList.get().isLoaded("estrogen")) {
            MinecraftForge.EVENT_BUS.register(EstrogenEventHandler.class);
        }
	}


	public static boolean requiresTank(LivingEntity entity){
		if (IPowerContainer.hasPower(entity, OriginsPowerTypes.WATER_BREATHING.get()))
			return entity.getEyeInFluidType() != ForgeMod.WATER_TYPE.get() && !(entity.hasEffect(MobEffects.CONDUIT_POWER) || entity.level().isRainingAt(entity.blockPosition()));
		
		return false;
	} 

   
    public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}
}

