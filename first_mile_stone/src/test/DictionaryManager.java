package test;

import java.util.HashMap;

public class DictionaryManager {
    HashMap<String , Dictionary> map = new HashMap<>();
    private static DictionaryManager dm = null;

    DictionaryManager(){
        map = new HashMap<>();
    }


    public boolean query(String ... args){
        for(int i=0; i<args.length-1; i++){
            if(!map.containsKey(args[i])){
                map.put(args[i], new Dictionary(args[i]));
            }
        }

        for(Dictionary d : map.values()){
            if(d.query(args[args.length-1])){
               return true;
            }
        }
        return false;

    }


    public boolean challenge(String ... args){
        for(Dictionary d : map.values()){
            if(d.challenge(args[args.length-1])){
                return  true;
            }
        }
return  false;    }


    public int getSize() {
        return map.size();
    }


    public static DictionaryManager get(){
        if(dm == null){
            dm = new DictionaryManager();
        }
        return dm;
    }

}