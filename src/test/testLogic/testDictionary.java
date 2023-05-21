package testGameLogic;

import Model.GameLogic.Dictionary;

public class testDictionary {
    public static void main(String[] args) {
        testDictionary t = new testDictionary();
        t.testDictionary();
        System.out.println("testDictionary-done");
    }


    public void testDictionary() {
        Dictionary d = new Dictionary("text1.txt", "text2.txt");
        if (!d.query("is")) //BloomFilter
            System.out.println("problem with dictionary in query ");
        if (!d.challenge("lazy")) //IOSearcher
            System.out.println("problem with dictionary in query");
    }
}
