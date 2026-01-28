package net.sch.misticita.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.sch.misticita.Misticita;
import net.sch.misticita.ModWorldGenProvider;

@Mod.EventBusSubscriber(modid = Misticita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBootstrapProviders {

    // RegistrySetBuilder para registrar nuestros métodos bootstrap
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModWorldGenProvider::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, ModWorldGenProvider::bootstrapPlaced);
}