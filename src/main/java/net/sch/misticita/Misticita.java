package net.sch.misticita;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Misticita.MODID)
public class Misticita {
    public static final String MODID = "misticita";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> MISTICITA_INGOT = ITEMS.register("misticita_ingot",
            () -> new Item(new Item.Properties()));

    public Misticita(FMLJavaModLoadingContext context) {
        // Obtenemos el bus directamente del contexto que Forge nos da
        IEventBus modEventBus = context.getModEventBus();

        // Registramos los ítems
        ITEMS.register(modEventBus);
    }
}