package com.MCWorld.db;

public class DbConstants {

    public enum GameInfoDbTable {
        Download("download_list");
        
        private String name;

        private GameInfoDbTable(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
