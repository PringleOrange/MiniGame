package com.github.micha4w;

public enum Bases {
    GREEN(-8, -8),
    YELLOW(7, -8),
    BLUE(-8, 7),
    RED(7, 7);

    int chunkX;
    int chunkZ;
    Bases opposite;

    Bases (int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public boolean sameAs (int chunkX, int chunkZ) {
        return this.chunkX == chunkX && this.chunkZ == chunkZ;
    }
}
