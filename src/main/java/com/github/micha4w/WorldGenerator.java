package com.github.micha4w;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunk) {

        int minHeight = chunk.getMinHeight();
        int maxHeight = chunk.getMaxHeight();

        // Excludes Chunks other than center 16x16
        if ( chunkX < -8 || chunkX > 7 || chunkZ < -8 || chunkZ > 7 ) {
            return;
        }

        // Set the Bedrock Floor
        chunk.setRegion(0, minHeight, 0, 16, minHeight+1, 16, Material.BEDROCK);
        chunk.setRegion(0, maxHeight, 0, 16, maxHeight+1, 16, Material.BARRIER);

        // Set Colors for Bases
        for ( Teams team : Teams.values() ) {
            if ( team.isBaseChunk(chunkX, chunkZ) ) {
                chunk.setRegion(0, minHeight+1, 0, 16, minHeight+2, 16, team.baseBlock);
                return;
            }
        }

        // Set Everything to Dirt
        chunk.setRegion(0, minHeight+1, 0, 16, maxHeight, 16, Material.DIRT);
    }

    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {

    }

//    @Override
//    public boolean canSpawn(World world, int x, int y) {
//        return false;
//    }


}
