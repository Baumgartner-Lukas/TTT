package model;

public enum Stone {
        X("X"), O("O"), NONE(" ");

        private final String stone;

        Stone(String stone){
            this.stone = stone;
        }

    @Override
    public String toString() {
        return stone;
    }
}
