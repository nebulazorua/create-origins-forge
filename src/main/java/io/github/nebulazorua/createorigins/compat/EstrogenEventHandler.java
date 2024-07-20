package io.github.nebulazorua.createorigins.compat;

import dev.mayaqq.estrogen.registry.EstrogenEffects;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.nebulazorua.createorigins.OriginsPowers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EstrogenEventHandler {
    // Allows the girl_power Origin power to work
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event)
    {
        if(IPowerContainer.hasPower(event.player, OriginsPowers.GIRL_POWER.get()))
            event.player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 200, 0, true, false, true));
        
    }
}
