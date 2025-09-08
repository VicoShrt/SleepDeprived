package me.shortman.bunker_additions.common.registry;

import me.shortman.bunker_additions.BunkerAdditions;
import me.shortman.bunker_additions.common.block.entity.DryingTableBlockEntity;
import me.shortman.bunker_additions.common.drug.alcohol.AlcoholEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, BunkerAdditions.MOD_ID);

    public static final Holder<Attribute> ALCOHOL_LEVEL = ATTRIBUTES.register("alcohol_level", () -> new RangedAttribute(
            "attributes.bunker_additions.alcohol_level",
            0,
            0,
            500
    ));

    public static final Holder<Attribute> ALCOHOL_TOLERANCE_MULTIPLIER = ATTRIBUTES.register("alcohol_tolerance_multiplier", () -> new RangedAttribute(
            "attributes.bunker_additions.alcohol_level",
            1,
            0,
            1
    ));

    public static final Holder<Attribute> TICKS_SINCE_LAST_ALCOHOL = ATTRIBUTES.register("ticks_since_last_alcohol", () -> new RangedAttribute(
            "attributes.bunker_additions.alcohol_level",
            0,
            0,
            AlcoholEvents.MAX_TICKS_SINCE_LAST_ALCOHOL
    ));


    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
