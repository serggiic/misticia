package net.sch.misticita;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sch.misticita.Misticita;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Misticita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        // Generadores
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider, existingFileHelper));

        // Tags
        generator.addProvider(event.includeServer(),
                new BlockTagsProvider(packOutput, lookupProvider, Misticita.MODID, existingFileHelper) {
                    @Override
                    protected void addTags(HolderLookup.Provider provider) {
                        // Aquí defines tags para tus bloques
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                                .add(Misticita.MISTICITA_ORE.get())
                                .add(Misticita.DEEPSLATE_MISTICITA_ORE.get())
                                .add(Misticita.MISTICITA_BLOCK.get())
                                .add(Misticita.MISTICITA_BRICKS.get());

                        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                                .add(Misticita.MISTICITA_ORE.get())
                                .add(Misticita.DEEPSLATE_MISTICITA_ORE.get())
                                .add(Misticita.MISTICITA_BLOCK.get())
                                .add(Misticita.MISTICITA_BRICKS.get());
                    }
                });
    }
}
