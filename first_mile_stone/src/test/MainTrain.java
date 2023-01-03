package test;

import java.io.FileWriter;
import java.io.PrintWriter;

public class MainTrain {

    public static void testLRU() {
        CacheReplacementPolicy lru=new LRU();
        lru.add("a");
        lru.add("b");
        lru.add("c");
        lru.add("a");

        if(!lru.remove().equals("b"))
            System.out.println("wrong implementation for LRU (-10)");
    }

    public static void testLFU() {
        CacheReplacementPolicy lfu=new LFU();
        lfu.add("a");
        lfu.add("b");
        lfu.add("b");
        lfu.add("c");
        lfu.add("a");

        if(!lfu.remove().equals("c"))
            System.out.println("wrong implementation for LFU (-10)");
    }

    public static void testCacheManager() {
        CacheManager exists=new CacheManager(3, new LRU());
        boolean b = exists.query("a");
        b|=exists.query("b");
        b|=exists.query("c");

        if(b)
            System.out.println("wrong result for CacheManager first queries (-5)");

        exists.add("a");
        exists.add("b");
        exists.add("c");

        b=exists.query("a");
        b&=exists.query("b");
        b&=exists.query("c");

        if(!b)
            System.out.println("wrong result for CacheManager second queries (-5)");

        boolean bf = exists.query("d"); // false, LRU is "a"
        exists.add("d");
        boolean bt = exists.query("d"); // true
        bf|= exists.query("a"); // false
        exists.add("a");
        bt &= exists.query("a"); // true, LRU is "b"

        if(bf || ! bt)
            System.out.println("wrong result for CacheManager last queries (-10)");

    }

    public static void testBloomFilter() {
        BloomFilter bf =new BloomFilter(256,"MD5","SHA1");
        String[] words = "the quick brown fox jumps over the lazy dog".split(" ");
        for(String w : words)
            bf.add(w);

        if(!bf.toString().equals("0010010000000000000000000000000000000000000100000000001000000000000000000000010000000001000000000000000100000010100000000010000000000000000000000000000000110000100000000000000000000000000010000000001000000000000000000000000000000000000000000000000000001"))
            System.out.println("problem in the bit vector of the bloom filter (-10)");

        boolean found=true;
        for(String w : words)
            found &= bf.contains(w);

        if(!found)
            System.out.println("problem finding words that should exist in the bloom filter (-15)");

        found=false;
        for(String w : words)
            found |= bf.contains(w+"!");

        if(found)
            System.out.println("problem finding words that should not exist in the bloom filter (-15)");
    }

    public static void testIOSearch() throws Exception{
        String words1 = "the quick brown fox \n jumps over the lazy dog";
        String words2 = "A Bloom filter is a space efficient probabilistic data structure, \n conceived by Burton Howard Bloom in 1970";
        PrintWriter out = new PrintWriter(new FileWriter("text1.txt"));
        out.println(words1);
        out.close();
        out = new PrintWriter(new FileWriter("text2.txt"));
        out.println(words2);
        out.close();

        if(!IOSearcher.search("is", "text1.txt","text2.txt"))
            System.out.println("your IOsearch did not found a word (-5)");
        if(IOSearcher.search("cat", "text1.txt","text2.txt"))
            System.out.println("your IOsearch found a word that does not exist (-5)");
    }

    public static void testDictionary() {
        Dictionary d = new Dictionary("text1.txt","text2.txt");
        if(!d.query("is"))
            System.out.println("problem with dictionarry in query (-5)");
        if(!d.challenge("lazy"))
            System.out.println("problem with dictionarry in query (-5)");
    }

    public static void main(String[] args) {
        testLRU();
        testLFU();
        testCacheManager();
        testBloomFilter();
        try {
            testIOSearch();
        } catch(Exception e) {
            System.out.println("you got some exception (-10)");
        }
        testDictionary();
        System.out.println("done");
    }
}


//package test;
//
//import test.Tile.Bag;
//
//public class MainTrain {
//
//    public static void testBag() {
//        Bag b = Tile.Bag.getBag();
//        Bag b1 = Tile.Bag.getBag();
//        if (b1 != b)
//            System.out.println("your Bag in not a Singleton (-5)");
//
//        int[] q0 = b.getQuantities();
//        q0[0] += 1;
//        int[] q1 = b.getQuantities();
//        if (q0[0] != q1[0] + 1)
//            System.out.println("getQuantities did not return a clone (-5)");
//
//        for (int k = 0; k < 9; k++) {
//            int[] qs = b.getQuantities();
//            Tile t = b.getRand();
//            int i = t.letter - 'A';
//            int[] qs1 = b.getQuantities();
//            if (qs1[i] != qs[i] - 1)
//                System.out.println("problem with getRand (-1)");
//
//            b.put(t);
//            b.put(t);
//            b.put(t);
//
//            if (b.getQuantities()[i] != qs[i])
//                System.out.println("problem with put (-1)");
//        }
//
//        if (b.getTile('a') != null || b.getTile('$') != null || b.getTile('A') == null)
//            System.out.println("your getTile is wrong (-2)");
//
//    }
//
//
//    private static Tile[] get(String s) {
//        Tile[] ts = new Tile[s.length()];
//        int i = 0;
//        for (char c : s.toCharArray()) {
//            ts[i] = Bag.getBag().getTile(c);
//            i++;
//        }
//        return ts;
//    }
//
//    public static void testBoard() {
//        Board b = Board.getBoard();
//        if (b != Board.getBoard())
//            System.out.println("board should be a Singleton (-5)");
//
//
//        Bag bag = Bag.getBag();
//        Tile[] ts = new Tile[10];
//        for (int i = 0; i < ts.length; i++)
//            ts[i] = bag.getRand();
//
//        Word w0 = new Word(ts, 0, 6, true);
//        Word w1 = new Word(ts, 7, 6, false);
//        Word w2 = new Word(ts, 6, 7, true);
//        Word w3 = new Word(ts, -1, 7, true);
//        Word w4 = new Word(ts, 7, -1, false);
//        Word w5 = new Word(ts, 0, 7, true);
//        Word w6 = new Word(ts, 7, 0, false);
//        Word w7 = new Word(ts, -2, 0, false);
//        Word w8 = new Word(ts, -2, -1, true);
//        Word w9 = new Word(ts, 1, 18, true);
//
//
//        if (b.boardLegal(w0) || b.boardLegal(w1) || b.boardLegal(w2) || b.boardLegal(w3) || b.boardLegal(w4) || !b.boardLegal(w5) || !b.boardLegal(w6))
//            System.out.println("your boardLegal function is wrong (-10)");
//
//        if (b.boardLegal(w7) || b.boardLegal(w8) || b.boardLegal(w9))
//            System.out.println("your boardLegal function is wrong2 (-10)");
//
//        for (Tile t : ts)
//            bag.put(t);
//
//        Word horn = new Word(get("HORN"), 7, 5, false);
//        if (b.tryPlaceWord(horn) != 14)
//            System.out.println("problem in placeWord for 1st word (-10)");
//
//        Word farm = new Word(get("FA_M"), 5, 7, true);
//        if (b.tryPlaceWord(farm) != 9)
//            System.out.println("problem in placeWord for 2ed word (-10)");
//
//        Word paste = new Word(get("PASTE"), 9, 5, false);
//        if (b.tryPlaceWord(paste) != 25)
//            System.out.println("problem in placeWord for 3ed word (-10)");
//
//        Word mob = new Word(get("_OB"), 8, 7, false);
//        int mobpoint = b.tryPlaceWord(mob);
//        if (mobpoint != 18)
//            System.out.println("mob point should be 18");
//
//        Word bit = new Word(get("BIT"), 10, 4, false);
//        int bitpoint = b.tryPlaceWord(bit);
//        if (bitpoint != 22)
//            System.out.println("bitpoint should be 22 (-15)");
//
//        Word bit2 = new Word(get("S_TA"), 9, 4, true);
//        if (b.tryPlaceWord(bit2) != 28)
//            System.out.println("SBTA should be 28 (-15)");
//
//        Word bit3 = new Word(get("A_ONE"), 11, 3, false);
//        if (b.tryPlaceWord(bit3) != 26)
//            System.out.println("ATONE should be 26 (-15)");
//
//        Word bit4 = new Word(get("CHECK"), 0, 12, true);
//        if (b.tryPlaceWord(bit4) != 0)
//            System.out.println("CHECK1 should be 0 (-15)");
//
////        Word bit5 = new Word(get("CHECK"), 0, 12, true);
////        if (b.tryPlaceWord(bit5) != 0)
////            System.out.println("CHECK2 should be 0 (-15)");
//
////        Word bit6 = new Word(get("CHECK"), 0, 0, false);
////        if (b.tryPlaceWord(bit6) != 0)
////            System.out.println("CHECK3 should be 0 (-15)");
//
////        Word bit7 = new Word(get("CHECK"), 0, 0, true);
////        if (b.tryPlaceWord(bit7) != 0)
////            System.out.println("CHECK4 should be 0 (-15)");
//
////        Word bit8 = new Word(get("CHECK"), 12, 0, true);
////        if (b.tryPlaceWord(bit8) != 0)
////            System.out.println("CHECK5 should be 0 (-15)");
//
////        Word bit9 = new Word(get("CHECK"), 1, 7, true);
////        if (b.tryPlaceWord(bit9) != 0)
////            System.out.println("CHECK6 should be 0 (-15)");
//
////        Word bit10 = new Word(get("CHECK"), 5, 3, false);
////        if (b.tryPlaceWord(bit10) != 0)
////            System.out.println("CHECK7 should be 0 (-15)");
//
////        Word bit11 = new Word(get("_HECK"), 5, 3, false);
////        if (b.tryPlaceWord(bit11) != 0)
////            System.out.println("CHECK8 should be 0 (-15)");
//
////        Word bit12 = new Word(get("_HECK"), 1, 7, true);
////        if (b.tryPlaceWord(bit12) != 0)
////            System.out.println("CHECK9 should be 0 (-15)");
//
////        Word bit13 = new Word(get("CHECK"), 8, 10, true);
////        if (b.tryPlaceWord(bit13) != 63)
////            System.out.println("CHECK10 should be 63 (-15)");
//
//
//
//    }
//
//
//    public static void main(String[] args) {
//        testBag(); // 30 points
//        testBoard(); // 70 points
//        System.out.println("done");
//    }
//
//}