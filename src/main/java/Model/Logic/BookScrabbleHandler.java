package Model.Logic;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.io.PrintWriter;

public class BookScrabbleHandler implements ClientHandler{

    String line;
    String[] args;

    @Override
    public void handleClient(InputStream inFromclient , OutputStream outToClient) {
        Scanner in = new Scanner(inFromclient);
        PrintWriter out = new PrintWriter(outToClient);
        try {
            if(in.hasNextLine()){
                line = in.nextLine();
                args = line.split(",");
            }
        } catch (Exception e){e.printStackTrace();}
        DictionaryManager dm = new DictionaryManager();

        String []fileNames = new String[args.length-1];
        for(int i =0 , s=1; s<args.length; i++ , s++){
            fileNames[i] = args[s];
        }


        boolean res;
        if(args[0].equals("C")){
            res = dm.challenge(fileNames);
        }
        else {
            res = dm.query(fileNames);
        }

        if(res){
            out.println("true");
            out.flush();
        }
        else {
            out.println("false");
            out.flush();
        }
        out.close();
    }

    @Override
    public void close() {

    }
}