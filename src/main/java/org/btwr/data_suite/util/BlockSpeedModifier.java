package org.btwr.data_suite.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;

public class BlockSpeedModifier {
    private final double modifier;
    private final Identifier id;
    private final EntityAttributeModifier.Operation operation;

    public BlockSpeedModifier(Identifier id, double modifier, EntityAttributeModifier.Operation operation) {
        this.modifier = modifier;
        this.id = id;
        this.operation = operation;
    }

    public EntityAttributeModifier toAttributeModifier() {
        return new EntityAttributeModifier(id, modifier, operation);
    }

    public Identifier getId() {
        return id;
    }
}