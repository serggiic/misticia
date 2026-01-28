package net.sch.misticita;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.sch.misticita.CreativeTab;

import java.util.concurrent.CompletableFuture;


@Mod(Misticita.MODID)
public class Misticita {
    public static final String MODID = "misticita";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Registro para Bloques
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // ========== BLOQUES ==========
    // El Bloque de Mineral
    public static final RegistryObject<Block> MISTICITA_ORE = BLOCKS.register("misticita_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(3.0f) // Dureza (el hierro es 3)
                    .requiresCorrectToolForDrops(), // Necesitas pico para que caiga el ítem
                    UniformInt.of(3, 7))); // Suelta entre 3 y 7 de experiencia

    // Registramos el bloque como un Ítem para que aparezca en el inventario
    public static final RegistryObject<Item> MISTICITA_ORE_ITEM = ITEMS.register("misticita_ore",
            () -> new BlockItem(MISTICITA_ORE.get(),
                    new Item.Properties()));

    // El Bloque de Deepslate
    public static final RegistryObject<Block> DEEPSLATE_MISTICITA_ORE = BLOCKS.register("deepslate_misticita_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                    .strength(4.5f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));

    // El Ítem del bloque
    public static final RegistryObject<Item> DEEPSLATE_MISTICITA_ORE_ITEM = ITEMS.register("deepslate_misticita_ore",
            () -> new BlockItem(DEEPSLATE_MISTICITA_ORE.get(),
                    new Item.Properties()));

    //Bloque de misticita
    public static final RegistryObject<Block> MISTICITA_BLOCK = BLOCKS.register("misticita_block",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                .strength(50.0f, 1200.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.NETHERITE_BLOCK)));

    //Ítem del bloque de misticita
    public static final RegistryObject<Item> MISTICITA_BLOCK_ITEM = ITEMS.register("misticita_block",
            () -> new BlockItem(MISTICITA_BLOCK.get(),
                    new Item.Properties().fireResistant()));

    //Ladrillos de Misticita
    public static final RegistryObject<Block> MISTICITA_BRICKS = BLOCKS.register("misticita_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                    .strength(45.0f, 1200.0f)
                    .requiresCorrectToolForDrops()));

    //Ítem de los ladrillos de Misticita
    public static final RegistryObject<Item> MISTICITA_BRICKS_ITEM = ITEMS.register("misticita_bricks",
        () -> new BlockItem(MISTICITA_BRICKS.get(),
                new Item.Properties().fireResistant()));

    // ========== ÍTEMS ==========

    public static final RegistryObject<Item> MISTICITA_INGOT = ITEMS.register("misticita_ingot",
            () -> new Item(new Item.Properties().fireResistant()));

    // ========== HERRAMIENTAS ==========
    // Espada
    public static final RegistryObject<Item> MISTICITA_SWORD = ITEMS.register("misticita_sword",
            () -> new SwordItem(Tiers.NETHERITE, 8, -2.4f, new Item.Properties().fireResistant()) {
            @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                if (attacker instanceof Player player) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0));
                }
                return super.hurtEnemy(stack, target, attacker);
            }
            });

    // Pico
    public static final RegistryObject<Item> MISTICITA_PICKAXE = ITEMS.register("misticita_pickaxe",
            () -> new PickaxeItem(Tiers.NETHERITE, 1, -2.8f, new Item.Properties()) {
                @Override
                public float getDestroySpeed(ItemStack stack, BlockState state) {
                    // Si el bloque es picable con este pico, devolvemos 11.0f (Netherite es 9.0f)
                    return super.getDestroySpeed(stack, state) > 1.0f ? 11.0f : 1.0f;
                }

                @Override
                public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
                    if(!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
                        if(miner instanceof Player player) {
                            player.giveExperiencePoints(1);
                        }
                    }
                    return super.mineBlock(stack, level, state, pos, miner);
                }
            });

    // Hacha
    public static final RegistryObject<Item> MISTICITA_AXE = ITEMS.register("misticita_axe",
            () -> new AxeItem(Tiers.NETHERITE, 10, -3.0f,
                    new Item.Properties().fireResistant()) {
            @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {


                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                return super.hurtEnemy(stack, target, attacker);
            }

            @Override
                public float getDestroySpeed(ItemStack stack, BlockState state) {
                return super.getDestroySpeed(stack, state) > 1.0f ? 10.0f : 1.0f;
                }
            });

    //Pala
    public static final RegistryObject<Item> MISTICITA_SHOVEL = ITEMS.register("misticita_shovel",
            () -> new ShovelItem(Tiers.NETHERITE, 1.5f, -3.0f,
                new Item.Properties().fireResistant()) {

                @Override
                public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
                if(state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                }
                return super.mineBlock(stack, level, state, pos, miner);
        }
    });

    // ========== ARMADURA ==========
    public static final RegistryObject<Item> MISTICITA_HELMET = ITEMS.register("misticita_helmet",
            () -> new ArmorItem(ArmorMaterialMisticita.MISTICITA, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> MISTICITA_CHESTPLATE = ITEMS.register("misticita_chestplate",
            () -> new ArmorItem(ArmorMaterialMisticita.MISTICITA, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> MISTICITA_LEGGINGS = ITEMS.register("misticita_leggings",
            () -> new ArmorItem(ArmorMaterialMisticita.MISTICITA, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> MISTICITA_BOOTS = ITEMS.register("misticita_boots",
            () -> new ArmorItem(ArmorMaterialMisticita.MISTICITA, ArmorItem.Type.BOOTS, new Item.Properties()));

    public Misticita(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::gatherData);

        BLOCKS.register(modEventBus);  // IMPORTANTE: Esto primero
        ITEMS.register(modEventBus);
        CreativeTab.register(modEventBus);

        // Esto DEBE ir DESPUÉS de BLOCKS.register()
        //ModEvents.register(modEventBus);  // Porque ModEvents depende de los bloques
    }


    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        // Añadimos esta línea para obtener el 'ayudante' de archivos
        ExistingFileHelper efh = event.getExistingFileHelper();

        // Ahora pasamos los 4 argumentos que el constructor necesita
        //generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider, efh));
    }
}