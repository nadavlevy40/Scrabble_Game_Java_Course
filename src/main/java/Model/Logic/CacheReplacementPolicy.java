package Model.Logic;
//interface - each class that using this interface must implement its methods!
public interface CacheReplacementPolicy {
    void add(String word);

    String remove();
}