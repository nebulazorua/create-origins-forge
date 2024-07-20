package io.github.nebulazorua.createorigins;

import io.github.edwinmindcraft.apoli.common.power.DummyPower;
import net.minecraftforge.registries.RegistryObject;

public class OriginsPowers {
    public static final RegistryObject<DummyPower> MECHANICAL_SIGHT = CreateOrigins.POWER_FACTORIES.register("goggles", DummyPower::new);
    public static final RegistryObject<DummyPower> GIRL_POWER = CreateOrigins.POWER_FACTORIES.register("girl_power", DummyPower::new);

    public static void register() {
    }
    
}
