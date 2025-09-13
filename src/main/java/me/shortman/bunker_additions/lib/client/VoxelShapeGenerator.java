package me.shortman.bunker_additions.lib.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;
/**
 * Utility class for generating VoxelShape arrays for blocks with multiple growth stages.
 * This class is primarily designed for Minecraft modding to create collision and selection
 * boxes for blocks that grow over ticksToCraft (such as crops). It provides both programmatic
 * generation based on parameters and predefined templates for common block types.
 *
 * @author Vico Shortman
 * @version 1.0
 */
public class VoxelShapeGenerator {

    /** Default maximum block height in pixels (full block height) */
    public static final int DEFAULT_BLOCK_HEIGHT = 16;

    /** Default maximum age/growth stage for generated shapes */
    public static final int DEFAULT_MAX_AGE = 7;

    /**
     * Generates an array of VoxelShapes for a block with multiple growth stages.
     * Creates shapes that progressively increase in height from age 1 to maxAge.
     * The height grows linearly from 0 to the specified block height.
     * Index 0 is left null/empty, with actual shapes starting at index 1.
     *
     * @param maxAge The maximum age
     * @param blockHeight The maximum height the block can reach (in pixels)
     * @return Array of VoxelShapes where index corresponds to growth stage
     *
     * @example
     * // Generate shapes for a 4-stage crop that grows to 8 pixels tall
     * VoxelShape[] cropShapes = VoxelShapeGenerator.generate(4, 8.0);
     */
    public static VoxelShape[] generate(int maxAge, double blockHeight) {
        VoxelShape[] shapes = new VoxelShape[maxAge + 1];
        for (int age = 1; age <= maxAge; age++) {
            double height = ((double) age / maxAge) * blockHeight;
            shapes[age] = Block.box(0.0D, 0.0D, 0.0D, 16.0D, height, 16.0D);
        }
        return shapes;
    }

    /**
     * Retrieves predefined VoxelShape arrays for common block types.
     *
     * Returns a cached template based on the provided template name.
     * If the requested template doesn't exist, returns the default template.
     *
     * @param template The name of the template to retrieve (case-sensitive)
     * @return Array of VoxelShapes for the specified template, or default if not found
     *
     * Available templates:
     * - "default": 4-stage growth to full block height
     * - "beetroot": 3-stage growth with 8px height
     * - "": Custom 4-stage template with specific heights (2, 4, 6, 8 pixels)
     *
     * @example
     * VoxelShape[] beetrootShapes = VoxelShapeGenerator.generate("beetroot");
     */
    public static VoxelShape[] generate(String template) {
        Map<String, VoxelShape[]> templates = getTemplates();
        if (templates.containsKey(template)) return templates.get(template);
        return templates.get("default");
    }

    /**
     * Creates and returns a map of predefined VoxelShape templates.
     * This method initializes all available templates on demand. Templates are
     * cached in a Hashtable for thread-safe access.
     * Templates included:
     * "default" -> Standard 16-stage linear growth to full block height
     * "beetroot" -> 3-stage growth optimized for beetroot crops (80% max height)
     * "" -> Custom template with 4 specific height stages
     * @return Map containing template names as keys and VoxelShape arrays as values
     */
    private static Map<String, VoxelShape[]> getTemplates() {
        Map<String, VoxelShape[]> templates = new Hashtable<>();
        templates.put("default", generate(DEFAULT_MAX_AGE, DEFAULT_BLOCK_HEIGHT));
        templates.put("beetroot", generate(3, 0.8));

        // Custom template with specific height stages
        // TODO: Add proper template
        templates.put("", new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        });

        return templates;
    }
}