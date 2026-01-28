package net.sch.misticita;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Misticita.MODID);

    public static final RegistryObject<CreativeModeTab> MISTICITA_TAB = CREATIVE_MODE_TABS.register("misticita_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.misticita_tab"))
                    .icon(() -> new ItemStack(Misticita.MISTICITA_INGOT.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(Misticita.MISTICITA_INGOT.get());
                        output.accept(Misticita.MISTICITA_ORE_ITEM.get());
                        output.accept(Misticita.DEEPSLATE_MISTICITA_ORE_ITEM.get());
                        output.accept(Misticita.MISTICITA_BLOCK_ITEM.get());
                        output.accept(Misticita.MISTICITA_BRICKS_ITEM.get());
                        output.accept(Misticita.MISTICITA_SWORD.get());
                        output.accept(Misticita.MISTICITA_PICKAXE.get());
                        output.accept(Misticita.MISTICITA_AXE.get());
                        output.accept(Misticita.MISTICITA_SHOVEL.get());
                        output.accept(Misticita.MISTICITA_HELMET.get());
                        output.accept(Misticita.MISTICITA_CHESTPLATE.get());
                        output.accept(Misticita.MISTICITA_LEGGINGS.get());
                        output.accept(Misticita.MISTICITA_BOOTS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
