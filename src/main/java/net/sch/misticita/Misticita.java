package net.sch.misticita;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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

    // Registro para Bloques
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // El Bloque de Mineral
    public static final RegistryObject<Block> MISTICITA_ORE = BLOCKS.register("misticita_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(3.0f) // Dureza (el hierro es 3)
                    .requiresCorrectToolForDrops(), // Necesitas pico para que caiga el ítem
                    UniformInt.of(3, 7))); // Suelta entre 3 y 7 de experiencia

    // Registramos el bloque como un Ítem para que aparezca en el inventario
    public static final RegistryObject<Item> MISTICITA_ORE_ITEM = ITEMS.register("misticita_ore",
            () -> new BlockItem(MISTICITA_ORE.get(), new Item.Properties()));

    // El Bloque de depslate
    public static final RegistryObject<Block> DEEPSLATE_MISTICITA_ORE = BLOCKS.register("deepslate_misticita_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(4.5f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));

    // El Ítem del bloque
    public static final RegistryObject<Item> DEEPSLATE_MISTICITA_ORE_ITEM = ITEMS.register("deepslate_misticita_ore",
            () -> new BlockItem(DEEPSLATE_MISTICITA_ORE.get(), 
                new Item.Properties().tab(MISTICITA_TAB)));

    //Bloque de misticita
    public static final RegistryObject<Block> MISTICITA_BLOCK = BLOCKS.register("misticita_block",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                .strength(50.0f, 1200.0f)
                .requiresCorrectToolForDrops();
                .sound(SoundType.NETHERITE_BLOCK)));

    //Ítem del bloque de misticita
    public static final RegistryObject<Item> MISTICITA_BLOCK_ITEM = ITEMS.register("misticita_block",
        () -> new BlockItem(MISTICITA_BLOCK.get(),
                new Item.Properties().tab(MISTICITA_TAB)));

    //Ladrillos de Misticita
    public static final RegistryObject<Block> MISTICITA_BRICKS = BLOCKS.register("misticita_bricks",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK
                .strength(45.0f, 1200.0f)
                .requiresCorrectToolForDrops())));

    //Ítem de los ladrillos de Misticita
    public static final RegistryObject<Item> MISTICITA_BRICKS_ITEM = ITEMS.register("misticita_bricks",
        () -> new BlockItem(MISTICITA_BRICKS.get(),
                new Item.Properties().tab(MISTICITA_TAB)));

    
    public static final RegistryObject<Item> MISTICITA_INGOT = ITEMS.register("misticita_ingot",
            () -> new Item(new Item.Properties().tab(MISTICITA_TAB)));

    public Misticita(FMLJavaModLoadingContext context) {
        // Obtenemos el bus directamente del contexto que Forge nos da
        IEventBus modEventBus = context.getModEventBus();

        // Registramos los ítems y bloques
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
    // Espada
    public static final RegistryObject<Item> MISTICITA_SWORD = ITEMS.register("misticita_sword",
            () -> new SwordItem(Tiers.NETHERITE, 8, -2.4f, new Item.Properties()));

    // Pico
    public static final RegistryObject<Item> MISTICITA_PICKAXE = ITEMS.register("misticita_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 1, -2.8f, new Item.Properties()) {
                @Override
                public float getDestroySpeed(ItemStack stack, BlockState state) {
                    // Si el bloque es picable con este pico, devolvemos 11.0f (Netherite es 9.0f)
                    return super.getDestroySpeed(stack, state) > 1.0f ? 11.0f : 1.0f;
                }
            });

    // Hacha
    public static final RegistryObject<Item> MISTICITA_AXE = ITEMS.register("misticita_axe",
            () -> new AxeItem(Tiers.NETHERITE, 10, -3.0f, new Item.Properties()));

}