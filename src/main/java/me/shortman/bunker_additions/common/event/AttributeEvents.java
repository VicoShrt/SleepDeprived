package me.shortman.bunker_additions.common.event;

import me.shortman.bunker_additions.common.registry.ModAttributes;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

public class AttributeEvents {
    public static void modifyDefaultAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.ALCOHOL_LEVEL, 0);
        event.add(EntityType.PLAYER, ModAttributes.ALCOHOL_TOLERANCE_MULTIPLIER, 1);
        event.add(EntityType.PLAYER, ModAttributes.TICKS_SINCE_LAST_ALCOHOL, 24000);
    }
}
