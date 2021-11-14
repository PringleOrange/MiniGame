package com.github.micha4w;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunk) {

        // Excludes Chunks other than center 16x16
        if ( chunkX < -8 || chunkX > 7 || chunkZ < -8 || chunkZ > 7 ) {
            return;
        }

        // Set the Bedrock Floor
        chunk.setRegion(0, 0, 0, 16, 1, 16, Material.BEDROCK);

        // Set Colors for Bases
        if ( Bases.GREEN.sameAs(chunkX, chunkZ) ) {
            chunk.setRegion(1, 0, 0, 16, 2, 16, Material.GREEN_CONCRETE);
            chunk.setRegion(255, 0, 0, 16, 256, 16, Material.GREEN_STAINED_GLASS);

        } else if ( Bases.BLUE.sameAs(chunkX, chunkZ) ) {
            chunk.setRegion(1, 0, 0, 16, 2, 16, Material.BLUE_CONCRETE);
            chunk.setRegion(255, 0, 0, 16, 256, 16, Material.BLUE_STAINED_GLASS);

        } else if ( Bases.YELLOW.sameAs(chunkX, chunkZ) ) {
            chunk.setRegion(1, 0, 0, 16, 2, 16, Material.YELLOW_CONCRETE);
            chunk.setRegion(255, 0, 0, 16, 256, 16, Material.YELLOW_STAINED_GLASS);

        } else if ( Bases.RED.sameAs(chunkX, chunkZ) ) {
            chunk.setRegion(1, 0, 0, 16, 2, 16, Material.RED_CONCRETE);
            chunk.setRegion(255, 0, 0, 16, 256, 16, Material.RED_STAINED_GLASS);

        } else {
            // Set Everything to Dirt
            chunk.setRegion(0, 0, 0, 16, 256, 16, Material.DIRT);
        }
    }

    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
    }

//    @Override
//    public boolean canSpawn(World world, int x, int y) {
//        return false;
//    }


}
