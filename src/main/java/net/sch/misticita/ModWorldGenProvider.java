package net.sch.misticita;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext; // <-- 1.20.1: CON "t"
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.sch.misticita.Misticita;
import net.sch.misticita.datagen.ModBootstrapProviders;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            // Aquí le decimos: "Usa el método bootstrapConfigured de ESTA clase"
            .add(Registries.CONFIGURED_FEATURE, ModWorldGenProvider::bootstrapConfigured)
            // Aquí le decimos: "Usa el método bootstrapPlaced de ESTA clase"
            .add(Registries.PLACED_FEATURE, ModWorldGenProvider::bootstrapPlaced);

    public static final ResourceKey<ConfiguredFeature<?, ?>> MISTICITA_ORE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(Misticita.MODID, "misticita_ore"));

    public static final ResourceKey<PlacedFeature> MISTICITA_ORE_PLACED_KEY =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(Misticita.MODID, "misticita_ore"));

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, BUILDER, Set.of(Misticita.MODID));


    }


    public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context) {
        List<OreConfiguration.TargetBlockState> targetStates = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                        ForgeRegistries.BLOCKS.getValue(ResourceLocation.fromNamespaceAndPath(Misticita.MODID, "misticita_ore")).defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                        ForgeRegistries.BLOCKS.getValue(ResourceLocation.fromNamespaceAndPath(Misticita.MODID, "deepslate_misticita_ore")).defaultBlockState())
        );

        context.register(MISTICITA_ORE_KEY, new ConfiguredFeature<>(
                Feature.ORE,
                new OreConfiguration(targetStates, 64)
        ));
    }

    public static void bootstrapPlaced(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Holder<ConfiguredFeature<?, ?>> misticitaOreHolder = configuredFeatures.getOrThrow(MISTICITA_ORE_KEY);

        List<PlacementModifier> placement = List.of(
                CountPlacement.of(150), // Cantidad exagerada para encontrarlo ya
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(320)
                )

        );

        context.register(MISTICITA_ORE_PLACED_KEY, new PlacedFeature(misticitaOreHolder, placement));
    }
}