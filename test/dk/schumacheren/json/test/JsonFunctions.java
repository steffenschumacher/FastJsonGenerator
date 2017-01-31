package dk.schumacheren.json.test;

/*
 * $Id: JsonFunctions.java,v 1.9.2.2 2013/12/12 06:38:09 a72721 Exp $
 */

import dk.schumacheren.json.test.Objects.Intf;
import java.util.*;
import dk.schumacheren.json.test.Objects.Router;
import java.io.PrintWriter;

/**
 *
 * @author ssch
 */
public class JsonFunctions {
    public static void serialize(PrintWriter out, ArrayList<Router> routers) {
        for(Router r: routers) {
            out.println(JsonFunctions.getJsonHost(r));
        }
    }
    public static String getJsonHost(Router r) {
        StringBuilder retval = new StringBuilder();
        retval.append("{\n\t").append(getJsonParam("name", r.name)).append(",\n");
        retval.append("\t").append(getJsonParam("hwPlatform", r.hwPlatform)).append(",\n");
        retval.append("\t").append(getJsonParam("swPlatform", r.swPlatform)).append(",\n");
        retval.append("\t").append(getJsonParam("swVersion", r.swVersion)).append(",\n");
        retval.append("\t").append(getJsonParam("netProcessor", r.netProcessor)).append(",\n");
        retval.append("\t").append(getJsonParam("neType", r.neType)).append(",\n");
        retval.append("\t").append(getJsonParam("country", r.country)).append(",\n");
        retval.append("\t").append(getJsonParam("noQoS", (r.noQoS))).append(",\n");
        retval.append("\t").append(getJsonParam("dhcpMode", r.dhcpMode));
        if (!r.interfaces.isEmpty()) {
            retval.append(",\n\t\"interfaces\":\n\t[\n");
            Iterator<Intf> ifIter = r.interfaces.iterator();
            while (ifIter.hasNext()) {
                Intf intf = ifIter.next();
                retval.append(getJsonInterface(intf)).append(ifIter.hasNext() ? ",\n" : "\n");
            }
            retval.append("\t]");
        }
        retval.append("\n}");
        return retval.toString();

    }

    public static String getJsonInterface(Intf intf) {

        StringBuilder retval = new StringBuilder();
        retval.append("\t{\n\t\t").append(getJsonParam("name", intf.name)).append(",\n");
        retval.append("\t\t").append(getJsonParam("hwType", intf.hwType)).append(",\n");
        retval.append("\t\t").append(getJsonParam("sfp", intf.sfp)).append(",\n");
        retval.append("\t\t").append(getJsonParam("isBackbone", intf.isBackbone)).append(",\n");
        retval.append("\t\t").append(getJsonParam("hQoSDisabled", intf.hQoSDisabled)).append(",\n");
        retval.append("\n\t}");
        return retval.toString();
    }

    public static String getJsonParam(String name, String value) {
        return "\"" + name + "\": \"" + value + "\"";
    }

    public static String getJsonParam(String name, Boolean value) {
        return "\"" + name + "\": " + value;
    }

}
