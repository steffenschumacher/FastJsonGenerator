/*
 *  
 *  TDC A/S CONFIDENTIAL
 *  __________________
 *  
 *   [2004] - [2013] TDC A/S, Operations department 
 *   All Rights Reserved.
 *  
 *  NOTICE:  All information contained herein is, and remains
 *  the property of TDC A/S and its suppliers, if any.
 *  The intellectual and technical concepts contained herein are
 *  proprietary to TDC A/S and its suppliers and may be covered
 *  by Danish and Foreign Patents, patents in process, and are 
 *  protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this 
 *  material is strictly forbidden unless prior written 
 *  permission is obtained from TDC A/S.
 *  
 */
package dk.schumacheren.json.test;

import dk.schumacheren.json.test.Objects.Router;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 */
public class Benchmark {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Router> routers = new ArrayList<>();
        for(int i = 0; i<50000; i++) {
            routers.add(new Router());
        }
        PrintWriter pw = new PrintWriter(new PrintStream(new File ("/dev/null")));
        
        long start = System.currentTimeMillis();
        JsonFunctions.serialize(pw, routers);
        long dur = System.currentTimeMillis()-start;
        System.out.println("StringBuilder took: " + dur + " ms");
        
        start = System.currentTimeMillis();
        FastJsonGenerator.serialize(pw, routers);
        dur = System.currentTimeMillis()-start;
        System.out.println("FastJsonGenerator took: " + dur + " ms");
        
        start = System.currentTimeMillis();
        FastJsonGenerator.serializeOracle(pw, routers);
        dur = System.currentTimeMillis()-start;
        System.out.println("JsonGenerator took: " + dur + " ms");
        
        start = System.currentTimeMillis();
        FastJsonGenerator.serializeObject(pw, routers);
        dur = System.currentTimeMillis()-start;
        System.out.println("JsonObject took: " + dur + " ms");
    }
    
}
