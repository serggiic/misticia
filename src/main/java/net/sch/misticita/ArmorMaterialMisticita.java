package net.sch.misticita;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ArmorMaterialMisticita implements ArmorMaterial {
    MISTICITA("misticita:misticita", 45, new int[]{4, 7, 9, 4}, 20,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0f, 0.2f,
            () -> Ingredient.of(Misticita.MISTICITA_INGOT.get()));

    private static final java.util.Map<ArmorItem.Type, Integer> HEALTH_PER_SLOT = java.util.Map.of(
            ArmorItem.Type.HELMET, 13,
            ArmorItem.Type.CHESTPLATE, 15,
            ArmorItem.Type.LEGGINGS, 16,
            ArmorItem.Type.BOOTS, 11
    );
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ArmorMaterialMisticita(String name, int durabilityMultiplier, int[] slotProtections,
                        int enchantmentValue, SoundEvent sound, float toughness,
                        float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }
    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }
    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_PER_SLOT.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.slotProtections[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
