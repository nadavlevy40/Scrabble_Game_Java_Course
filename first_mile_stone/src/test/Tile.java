package test;


import java.util.Objects;
import java.util.Random;

public class Tile {
    final public char letter;
    final public int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag {

        private static Bag single_bag = null;
        int[] letterQty_array;
        Tile[] tile_array;
        int tile_count;

        private Bag() {
            this.letterQty_array = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            int[] letter_score = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            this.tile_array = new Tile[26];
            char tmp_letter = 'A';


            for (int i = 0; i < 26; i++) {
                this.tile_array[i] = new Tile(tmp_letter++, letter_score[i]);
            }

            this.tile_count = 98;
        }

        public static Bag getBag() {
            if (single_bag == null)
                single_bag = new Bag();

            return single_bag;
        }

        //gets random tile from the bag:
        public Tile getRand() {
            //get random tile from bag according to the probability of each letter:

            //if bag is empty:
            if (this.tile_count == 0)
                return null;

            Random rand = new Random();
            int rand_num = rand.nextInt(this.tile_count);
            int letter_index = 0;
            int letter_count = 0;
            while (letter_count <= rand_num) {
                letter_count += this.letterQty_array[letter_index++];
            }

            //update all the needed variables:
            letter_index--;
            this.letterQty_array[letter_index]--;
            this.tile_count--;

            return this.tile_array[letter_index];

        }

        public Tile getTile(char letter) {
            if (letter < 'A' || letter > 'Z')
                return null;


            int indexed_Letter = letter - 65; // 'A' = 0, 'B' = 1,...etc.
            if (letterQty_array[indexed_Letter] > 0) //do if there is ant tiles of this letter.
            {
                this.letterQty_array[indexed_Letter]--;
                this.tile_count--;
                return this.tile_array[indexed_Letter];
            }
            return null;

        }

        public void put(Tile t) {
            if (this.tile_count == 98)
                return;
            int letter_index = t.letter - 65;
            this.letterQty_array[letter_index]++;
            this.tile_count++;
        }

        public int size() {
            return this.tile_count;
        }

        public int[] getQuantities() {
            int[] copy = new int[26];
            System.arraycopy(this.letterQty_array, 0, copy, 0, 26);
            return copy;

        }


    }


}