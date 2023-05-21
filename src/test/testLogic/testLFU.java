package testGameLogic;

import Model.GameLogic.CacheReplacementPolicy;
import Model.GameLogic.LFU;

public class testLFU {
    public static void main(String[] args) {
        testLFU t = new testLFU();
        t.testLFU();
        System.out.println("testLFU-done");
    }


    public void testLFU() {
        CacheReplacementPolicy lfu = new LFU();
        lfu.add("a");
        lfu.add("b");
        lfu.add("b");
        lfu.add("c");
        lfu.add("a");

        if (!lfu.remove().equals("c")) System.out.println("wrong implementation for LFU ");
    }
}