package com.github.micha4w;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunk) {

        // Excludes Chunks other than center 16x16
        if ( chunkX < -7 || chunkX > 8 || chunkZ < -7 || chunkZ > 8 ) {
            return;
        }

        // Set the Bedrock Floor
        chunk.setRegion(0, 0, 0, 15, 0, 15, Material.BEDROCK);

        // Exclude 4 Border Chunks
        if ( (chunkX == -7 ?1:0) + (chunkX == 8 ?1:0) + (chunkZ == -7 ?1:0) + (chunkZ == 8 ?1:0) == 2 ) {
            return;
        }

        // Set Everything to Dirt
        chunk.setRegion(0, 0, 0, 15, 0, 15, Material.BEDROCK);
    }

    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
    }

//    @Override
//    public boolean canSpawn(World world, int x, int y) {
//        return false;
//    }


}
