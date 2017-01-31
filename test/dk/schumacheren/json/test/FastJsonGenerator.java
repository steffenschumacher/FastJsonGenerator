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
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

/**
 * FastJsonGenerator - short description. Detailed description.
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 * @version 1.0
 */
public class FastJsonGenerator {

    public static void serialize(PrintWriter out, ArrayList<Router> routers) {
        for (Router r : routers) {
            dk.schumacheren.json.FastJsonGenerator fg = new dk.schumacheren.json.FastJsonGenerator(out);
            r.writeRouter(fg);
        }
    }

    public static void serializeOracle(PrintWriter out, ArrayList<Router> routers) {
        for (Router r : routers) {
            JsonGenerator fg = Json.createGenerator(out);
            r.writeRouter(fg);
        }
    }

    public static void serializeObject(PrintWriter out, ArrayList<Router> routers) {
        for (Router r : routers) {
            JsonWriter jsonWriter = Json.createWriter(out);
            jsonWriter.writeObject(r.toJson().build());
            jsonWriter.close();
        }
        
    }
}
