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

package dk.schumacheren.json.test.Objects;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonGenerator;

/**
 * Router - short description.
 * Detailed description.
 * 
 * @author  Steffen Schumacher <steff@tdc.dk>
 * @version 1.0
 */
public class Router {
    public String name = "some.host.name.com";
    public String hwPlatform =  "mx960";
    public String swPlatform =  "JUNOS";
    public String swVersion =  "14.2R4-S2";
    public String netProcessor =  "N/A";
    public String neType =  "Router";
    public String country =  "us";
    public boolean noQoS =  false;
    public String dhcpMode =  "relay";
    public ArrayList<Intf> interfaces = new ArrayList<>();

    public Router() {
        for(int i = 0; i<100;i++) {
            interfaces.add(new Intf());
        }
    }
    
    public void writeRouter(JsonGenerator jg) {
        jg.writeStartObject();
        jg.write("name", name);
        jg.write("hwPlatform", hwPlatform);
        jg.write("swPlatform", swPlatform);
        jg.write("swVersion", swVersion);
        jg.write("netProcessor", netProcessor);
        jg.write("neType",neType);
        jg.write("country", country);
        jg.write("noQoS", noQoS);
        jg.write("dhcpMode", dhcpMode);
        if (interfaces != null) {
            jg.writeStartArray("interfaces");
            for(Intf intf : interfaces) {
                jg.writeStartObject();
                jg.write("name", intf.name);
                jg.write("hwType", intf.hwType);
                jg.write("sfp", intf.sfp);
                jg.write("isBackbone", intf.isBackbone);
                jg.write("hQoSDisabled", intf.hQoSDisabled);
                jg.writeEnd();
            }
            jg.writeEnd();
        }
        jg.writeEnd();
    }
    
    public JsonObjectBuilder toJson() {
        JsonObjectBuilder ob = Json.createObjectBuilder()
                .add("name", name)
                .add("hwPlatform", hwPlatform)
                .add("swPlatform", swPlatform)
                .add("swVersion", swVersion)
                .add("netProcessor", netProcessor)
                .add("neType", neType)
                .add("country", country)
                .add("noQoS", noQoS)
                .add("dhcpMode", dhcpMode);
        if (interfaces != null) {
            ob.add("interfaces", buildInterfaces());
        }
        return ob;
    }
    
    private JsonArrayBuilder buildInterfaces() {
        JsonArrayBuilder ab = Json.createArrayBuilder();
        for(Intf i : interfaces) {
            JsonObjectBuilder job = Json.createObjectBuilder().
                    add("name", i.name).
                    add("hwType", i.hwType).
                    add("sfp", i.sfp).
                    add("isBackbone", i.isBackbone).
                    add("hQoSDisabled", i.hQoSDisabled);
            ab.add(job);
        }
        return ab;
    }
}
