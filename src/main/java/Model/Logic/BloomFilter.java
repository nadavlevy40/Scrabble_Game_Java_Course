package Model.Logic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;


public class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final MessageDigest[] digests;

    //implementing the Bloom Filter, which is a probabilistic data structure
    // used to Test whether an element is a member of a set.
    // the C'tor initialized with a size, which is the number of bits in
    // the bit array that will be used to store the elements of the set.
    // it also takes an array of strings that specify the names of hash functions to use.
    // these hash functions will be used to map elements to positions in the bit array.
    public BloomFilter(int size, String... hashAlgs) {
        this.size = size;
        this.bitSet = new BitSet(size);
        this.digests = new MessageDigest[hashAlgs.length];
        for (int i = 0; i < hashAlgs.length; i++) {
            try {
                digests[i] = MessageDigest.getInstance(hashAlgs[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
//this method takes a string as input and uses the hash functions to map it to positions in the bit array,
// then sets the bits at those positions to 1.
    public void add(String word) {
        for (MessageDigest digest : digests) {
            byte[] bytes = digest.digest(word.getBytes());
            int hash = new BigInteger(bytes).intValue();
            bitSet.set(Math.abs(hash % size));
        }

    }
//this  method takes a string as input and uses the hash functions to map it to positions in the bit array ,
//then checks whether the bits at those positions are all set to 1. If they are, it returns true , otherwise false.
    public boolean contains(String word) {
        for (MessageDigest digest : digests) {
            byte[] bytes = digest.digest(word.getBytes());
            int hash = new BigInteger(bytes).intValue();
            if (!bitSet.get(Math.abs(hash % size))) {
                return false;
            }
        }
        return true;
    }
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            string.append(bitSet.get(i) ? "1" : "0");
        }
        return string.toString();
    }


}