package Model.Data;


import java.util.ArrayList;
import java.util.Objects;

public class Board {

    static final public int BoardSize = 15;
    private static Board checkBoard = null;
    public CHECKING_BOARD[][] gameBoard = new CHECKING_BOARD[BoardSize][BoardSize];
    int wordOnBoard = 0;


    private Board() {
        String[][] bonusCells = {
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "TW", "NO", "NO", "NO", "DL", "NO", "NO", "TW"},
                {"NO", "DW", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "DW", "NO"},
                {"NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO"},
                {"DL", "NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO", "DL"},
                {"NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO"},
                {"NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO"},
                {"NO", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "NO"},
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "NO", "TW"},
                {"NO", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DL", "NO", "NO"},
                {"NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO"},
                {"NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO", "NO", "DW", "NO", "NO", "NO", "NO"},
                {"DL", "NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO", "DL"},
                {"NO", "NO", "DW", "NO", "NO", "NO", "DL", "NO", "DL", "NO", "NO", "NO", "DW", "NO", "NO"},
                {"NO", "DW", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "TL", "NO", "NO", "NO", "DW", "NO"},
                {"TW", "NO", "NO", "DL", "NO", "NO", "NO", "TW", "NO", "NO", "NO", "DL", "NO", "NO", "TW"}
        };
        //initialize the board
        for (int i = 0; i < BoardSize; i++) {
            for (int j = 0; j < BoardSize; j++) {
                gameBoard[i][j] = new CHECKING_BOARD(bonusCells[i][j]);
            }
        }


    }

    public static Board getBoard() {
        if (checkBoard == null) {
            checkBoard = new Board();
        }
        return checkBoard;
    }


    //getTiles() returns 2-D array of tiles according to the gameBoard tiles
    public Tile[][] getTiles() {
        Tile[][] tiles = new Tile[BoardSize][BoardSize];
        for (int row = 0; row < BoardSize; row++) {
            for (int col = 0; col < BoardSize; col++) {
                if (gameBoard[row][col].getTile() != null) {
                    tiles[row][col] = gameBoard[row][col].tile;
                }
            }

        }
        return tiles;
    }

    public boolean boardLegal(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();


        if (this.wordOnBoard == 0) {
            if (!relaysOnMiddle(word, row, col, vertical)) {
                return false;
            } else {
                return fitsBoard(word, row, col, vertical);
            }
        }


        return fitsBoard(word, row, col, vertical) && relaysOn(word, row, col, vertical) && noTileSwitch(word, row, col, vertical);
    }

    //check if the word fits on the board according to: row and col and tiles length
    private boolean fitsBoard(Word word, int row, int col, boolean vertical) {
        if (vertical) {
            return row >= 0 && row + word.getTiles().length <= BoardSize;
        } else {
            return col >= 0 && col + word.getTiles().length <= BoardSize;
        }

    }

    //return true if the given word relays on existing tiles on the board (adjacent or overlapping)
    private boolean relaysOn(Word word, int row, int col, boolean vertical) {
//if word was on the board, return true if it would be adjacent of overlapping to existing tiles
        if (wordOnBoard > 0) {
            if (vertical) {
                if (row > 0 && gameBoard[row - 1][col].getTile() != null) {
                    return true;
                }
                if (row + word.getTiles().length < BoardSize && gameBoard[row + word.getTiles().length][col].getTile() != null) {
                    return true;
                }
                for (int i = 0; i < word.getTiles().length; i++) {
                    if (col > 0 && gameBoard[row + i][col - 1].getTile() != null) {
                        return true;
                    }
                    if (col < BoardSize - 1 && gameBoard[row + i][col + 1].getTile() != null) {
                        return true;
                    }
                }
            } else {
                if (col > 0 && gameBoard[row][col - 1].getTile() != null) {
                    return true;
                }
                if (col + word.getTiles().length < BoardSize && gameBoard[row][col + word.getTiles().length].getTile() != null) {
                    return true;
                }
                for (int i = 0; i < word.getTiles().length; i++) {
                    if (row > 0 && gameBoard[row - 1][col + i].getTile() != null) {
                        return true;
                    }
                    if (row < BoardSize - 1 && gameBoard[row + 1][col + i].getTile() != null) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //return true if the given word doesn't switch any existing tiles on the board
    private boolean noTileSwitch(Word word, int row, int col, boolean vertical) {

        //PROBLEM HERE

        Tile[] tiles = new Tile[word.getTiles().length];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = word.getTiles()[i];
        }

        int length = tiles.length;


        if (vertical) {
            for (int i = 0; i < length; i++) {
                if (gameBoard[row + i][col].tile != null && tiles[i] != null && gameBoard[row + i][col].tile.letter != tiles[i].letter) {
                    return false;
                }
                if (gameBoard[row + i][col].tile == null && tiles[i] == null) {
                    return false;
                }

            }
        } else {
            for (int i = 0; i < length; i++) {
                if (gameBoard[row][col + i].tile != null && tiles[i] != null && gameBoard[row][col + i].tile.letter != tiles[i].letter) {
                    return false;
                }
                if (gameBoard[row][col + i].tile == null && tiles[i] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    //return true if the given word relays on the middle tile(first tile)
    private boolean relaysOnMiddle(Word word, int row, int col, boolean vertical) {
        int length = word.getTiles().length;
        //if the word pass true the middle tile (even partially)
        if (vertical) {
            return col == 7 && row <= 7 && row + length >= 7;
        } else {
            return row == 7 && col <= 7 && col + length >= 7;
        }

    }

    //dictionaryLegal() returns true if the given word is legal according to the dictionary
    public boolean dictionaryLegal(Word word) {
        return true;
    }


    //returns array of words that are formed by the given word if it was placed.
    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> words = new ArrayList<>();
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();


        if (vertical) {
            for (int i = 0; i < word.getTiles().length; i++) {

                if (getWordRightLeft(word.tiles[i], row + i, col) != null && word.tiles[i] != null) {
                    words.add(getWordRightLeft(word.tiles[i], row + i, col));

                    //if it's the first or last tile of the word, check up and down
                    if ((i == 0 || i == word.getTiles().length - 1) && getWordUpDown(word.tiles[i], col + i, col) != null) {
                        words.add(getWordUpDown(word.tiles[i], row, col + i));
                    }

                }


            }
        } else {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (getWordUpDown(word.tiles[i], row, col + i) != null && word.tiles[i] != null) {
                    words.add(getWordUpDown(word.tiles[i], row, col + i));

                    //if it's the first or last tile of the word, check left and right
                    if ((i == 0 || i == word.getTiles().length - 1) && getWordRightLeft(word.tiles[i], row, col + i) != null) {
                        words.add(getWordRightLeft(word.tiles[i], row, col + i));
                    }


                }

            }
        }

        //for each word in words, if word is already placed on board, remove it from words


        words.add(word);
        return words;
    }


    private Word getWordRightLeft(Tile tile, int row, int col) {
        int left = col;
        int right = col;
        while (left > 0 && gameBoard[row][left - 1].tile != null) {
            left--;
        }
        while (right < BoardSize - 1 && gameBoard[row][right + 1].tile != null) {
            right++;
        }
        Tile[] tiles = new Tile[right - left + 1];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = gameBoard[row][left + i].tile;

        }

        if (tiles.length == 1) {
            return null;
        }


        return new Word(tiles, row, left, false);
    }


    private Word getWordUpDown(Tile tile, int row, int col) {
        int up = row;
        int down = row;
        while (up > 0 && gameBoard[up - 1][col].tile != null) {
            up--;
        }
        while (down < BoardSize - 1 && gameBoard[down + 1][col].tile != null) {
            down++;
        }
        Tile[] tiles = new Tile[down - up + 1];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = gameBoard[up + i][col].tile;

            //            if (gameBoard[up + i][col].tile == null) {  // might not be needed
            //                tiles[i] = tile;
            //            }
        }

        if (tiles.length == 1) {
            return null;
        }


        return new Word(tiles, up, col, true);
    }


    //DL - 2 times the letter score
    //TL - 3 times the letter score
    //DW - 2 times the word score
    //TW - 3 times the word score
    public int getScore(Word word) {
        int score = 0;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        Tile[] tiles = new Tile[word.getTiles().length];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = word.getTiles()[i];
        }

        int length = tiles.length;

        int scoreMultiplier = 1;

        if (vertical) {
            for (int i = 0; i < length; i++) {
                //if tile relays on the middle tile, and it's not the first word give a score only for the letter
                if (col == 7 && row + i == 7 && this.wordOnBoard != 0) {
                    score += gameBoard[7][7].tile.score;
                } else {

                    if (Objects.equals(gameBoard[row + i][col].tileEffect, "DL"))
                        score += gameBoard[row + i][col].tile.score * 2;

                    else if (Objects.equals(gameBoard[row + i][col].tileEffect, "TL"))
                        score += gameBoard[row + i][col].tile.score * 3;

                    else if (Objects.equals(gameBoard[row + i][col].tileEffect, "NO"))
                        score += gameBoard[row + i][col].tile.score;


                    else if (Objects.equals(gameBoard[row + i][col].tileEffect, "DW")) {
                        score += gameBoard[row + i][col].tile.score;
                        scoreMultiplier *= 2;
                    } else if (Objects.equals(gameBoard[row + i][col].tileEffect, "TW")) {
                        score += gameBoard[row + i][col].tile.score;
                        scoreMultiplier *= 3;
                    }


                }
            }

        } else {
            for (int i = 0; i < length; i++) {
                //if tile relays on the middle tile, and it's not the first word give a score only for the letter
                if (row == 7 && col + i == 7 && this.wordOnBoard != 0) {
                    score += tiles[i].score;
                } else {

                    if (Objects.equals(gameBoard[row][col + i].tileEffect, "DL"))
                        score += gameBoard[row][col + i].tile.score * 2;

                    else if (Objects.equals(gameBoard[row][col + i].tileEffect, "TL"))
                        score += gameBoard[row][col + i].tile.score * 3;

                    else if (Objects.equals(gameBoard[row][col + i].tileEffect, "NO"))
                        score += gameBoard[row][col + i].tile.score;

                    else if (Objects.equals(gameBoard[row][col + i].tileEffect, "DW")) {
                        score += gameBoard[row][col + i].tile.score;
                        scoreMultiplier *= 2;
                    } else if (Objects.equals(gameBoard[row][col + i].tileEffect, "TW")) {
                        score += gameBoard[row][col + i].tile.score;
                        scoreMultiplier *= 3;
                    }

                }
            }
        }
        return scoreMultiplier * score;
    }


    //try to place the given word on the board and will return the score of the word if it was successful

    public int tryPlaceWord(Word word) {
        int score = 0;
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();

        if (!boardLegal(word))
            return 0;

        Tile[] tiles = new Tile[word.getTiles().length];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = word.getTiles()[i];
        }
        int length = tiles.length;

        ArrayList<Word> words = getWords(word);


        //if the word is boardLegal() and the words formed are legal,
        // place the word on the board and return the score of the word and the words formed
        for (Word w : words) {
            if (!dictionaryLegal(w)) {
                return 0;
            }
        }

        //if it's the first word
        if (this.wordOnBoard == 0) {
            if (vertical) {
                for (int i = 0; i < length; i++) {
                    gameBoard[row + i][col].setTile(tiles[i]);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    gameBoard[row][col + i].setTile(tiles[i]);
                }
            }

            score += getScore(word);
            this.wordOnBoard++;
            return score;
        }

        if (vertical) {
            //if one the word tile is missing, use the tile on the board
            for (int i = 0; i < length; i++) {
                if (word.tiles[i] != null) {
                    gameBoard[row + i][col].setTile(tiles[i]);
                }
            }

        } else {
            for (int i = 0; i < length; i++) {
                if (word.tiles[i] != null) {
                    gameBoard[row][col + i].setTile(tiles[i]);
                }
            }

        }

        //add the score of the word and the words formed
        for (Word w : words) {
            score += getScore(w);
        }

        return score;
    }


    public static class CHECKING_BOARD {

        public Tile tile;
        public String tileEffect;

        private CHECKING_BOARD (String tileEffect) {
            this.tileEffect = tileEffect;
            setTile(null);

        }

        public Tile getTile() {
            return tile;
        }

        public void setTile(Tile tile) {
            this.tile = tile;

        }

    }


}



