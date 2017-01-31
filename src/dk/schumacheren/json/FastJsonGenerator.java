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
package dk.schumacheren.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;

/**
 * FastJsonGenerator - short description. Detailed description.
 *
 * @author Steffen Schumacher <steff@tdc.dk>
 * @version 1.0
 */
public class FastJsonGenerator implements javax.json.stream.JsonGenerator {

    private final static int SIZE = 4096;
    private final static char[] CHARS = "[]{}:\",".toCharArray();
    private final static char C_SB_O = CHARS[0];
    private final static char C_SB_C = CHARS[1];
    private final static char C_CB_O = CHARS[2];
    private final static char C_CB_C = CHARS[3];
    private final static char C_COLON = CHARS[4];
    private final static char C_QUOTE = CHARS[5];
    private final static char C_COMMA = CHARS[6];

    private enum Mode {
        root, inArray, inObject
    }
    private final Stack<Mode> stage;
    private final char[] buf;
    private int pos;
    private boolean isFirstNode;
    private final PrintWriter out;

    public FastJsonGenerator(PrintWriter out) {
        stage = new Stack<>();
        stage.add(Mode.root);
        buf = new char[SIZE];
        pos = 0;
        isFirstNode = true;
        this.out = out;
    }

    @Override
    public JsonGenerator writeStartObject() {
        try {
            addCommaPfxIfNeeded();
            addSingleByte(C_CB_O);  //Start {
            isFirstNode = true; //Disables comma prefix
            stage.push(Mode.inObject);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartObject(String string) {
        try {
            writeQuotedString(string, true);
            addSingleByte(C_CB_O);  //Start {
            isFirstNode = true; //Disables comma prefix
            stage.push(Mode.inObject);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartArray() {
        try {
            addCommaPfxIfNeeded();
            addSingleByte(C_SB_O);  //Start [
            isFirstNode = true; //Disables comma prefix
            stage.push(Mode.inArray);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartArray(String string) {
        try {
            writeQuotedString(string, true);
            addSingleByte(C_SB_O);  //Start [
            isFirstNode = true; //Disables comma prefix
            stage.push(Mode.inArray);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, JsonValue jv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonGenerator write(String string, String string1) {
        try {
            writeQuotedString(string, true);
            isFirstNode = true; //Disables comma prefix
            writeQuotedString(string1, false);

        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, BigInteger bi) {
        try {
            writeQuotedString(string, true);
            writeBigInteger(bi);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, BigDecimal bd) {
        try {
            writeQuotedString(string, true);
            writeBigDecimal(bd);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, int i) {
        try {
            writeQuotedString(string, true);
            writeInteger(i);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, long l) {
        try {
            writeQuotedString(string, true);
            writeLong(l);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, double d) {
        try {
            writeQuotedString(string, true);
            writeDouble(d);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(String string, boolean bln) {
        try {
            writeQuotedString(string, true);
            writeBoolean(bln);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator writeNull(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonGenerator writeEnd() {
        try {
            if (pos == SIZE) {
                writeBuffer();
            }
            switch (stage.pop()) {
                case inArray:
                    buf[pos++] = C_SB_C;
                    break;
                case inObject:
                    buf[pos++] = C_CB_C;
                    break;
                default:
                    throw new IllegalArgumentException("Attempt to end stage which is already in root");
            }
        } catch (IOException e) {
        }
        isFirstNode = false;
        return this;
    }

    @Override
    public JsonGenerator write(JsonValue jv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonGenerator write(String string) {
        try {
            addCommaPfxIfNeeded();
            writeQuotedString(string, false);
        } catch (IOException ex) {

        }
        return this;
    }

    @Override
    public JsonGenerator write(BigDecimal bd) {
        try {
            addCommaPfxIfNeeded();
            writeBigDecimal(bd);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(BigInteger bi) {
        try {
            addCommaPfxIfNeeded();
            writeBigInteger(bi);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(int i) {
        try {
            addCommaPfxIfNeeded();
            writeInteger(i);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(long l) {
        try {
            addCommaPfxIfNeeded();
            writeLong(l);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(double d) {
        try {
            addCommaPfxIfNeeded();
            writeDouble(d);
        } catch (IOException e) {
        }
        return this;
    }

    @Override
    public JsonGenerator write(boolean bln) {
        try {
            addCommaPfxIfNeeded();
            writeBoolean(bln);
        } catch (IOException ex) {

        }
        return this;
    }

    @Override
    public JsonGenerator writeNull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        flush();
    }

    @Override
    public void flush() {
        try {
            writeBuffer();
            out.flush();
        } catch (IOException ex) {
        }

    }

    private void writeBigDecimal(BigDecimal bd) throws IOException {
        char[] val = bd.toString().toCharArray();
        if (!hasRoom(val.length)) {
            writeBuffer();
        }
        System.arraycopy(val, 0, buf, pos, val.length);
        pos += val.length;
    }

    private void writeBigInteger(BigInteger bi) throws IOException {
        char[] val = bi.toString().toCharArray();
        if (!hasRoom(val.length)) {
            writeBuffer();
        }

        System.arraycopy(val, 0, buf, pos, val.length);
        pos += val.length;
    }

    private void writeInteger(int i) throws IOException {
        char[] val = String.valueOf(i).toCharArray();
        if (!hasRoom(val.length)) {
            writeBuffer();
        }
        System.arraycopy(val, 0, buf, pos, val.length);
        pos += val.length;
    }

    private void writeLong(long l) throws IOException {
        char[] val = String.valueOf(l).toCharArray();
        if (!hasRoom(val.length)) {
            writeBuffer();
        }
        System.arraycopy(val, 0, buf, pos, val.length);
        pos += val.length;
    }

    private void writeDouble(double d) throws IOException {
        char[] val = String.valueOf(d).toCharArray();
        if (!hasRoom(val.length)) {
            writeBuffer();
        }
        System.arraycopy(val, 0, buf, pos, val.length);
        pos += val.length;
    }

    private void writeBoolean(boolean bln) throws IOException {
        if (!hasRoom(5)) {
            writeBuffer();
        }
        if (bln) {
            System.arraycopy("true".toCharArray(), 0, buf, pos, 4);
            pos += 4;
        } else {
            System.arraycopy("false".toCharArray(), 0, buf, pos, 5);
            pos += 5;
        }

    }

    private void addCommaPfxIfNeeded() throws IOException {
        if (!isFirstNode) { //add comma for ! subsequent nodes
            addSingleByte(C_COMMA);
        } else {
            isFirstNode = false;
        }
    }

    private void addSingleByte(char b) throws IOException {
        if (pos == SIZE) {
            writeBuffer();
        }
        buf[pos++] = b;
    }

    private void writeQuotedString(String str, boolean isName) throws IOException {
        char[] chars = str.toCharArray();
        addCommaPfxIfNeeded();
        int free = SIZE - pos;
        if (free > (2 + chars.length)) {
            //everything fits in one go
            buf[pos++] = C_QUOTE;
            System.arraycopy(chars, 0, buf, pos, chars.length);
            pos += chars.length;
            buf[pos++] = C_QUOTE;
        } else {
            addSingleByte(C_QUOTE);
            int strPos = 0;
            int remaining = chars.length;
            while (strPos < chars.length) {
                if (pos == SIZE) {
                    writeBuffer();
                }
                free = SIZE - pos;
                int copyLen = (free < remaining ? free : remaining);
                System.arraycopy(chars, strPos, buf, pos, copyLen);
                strPos += copyLen;
                remaining -= copyLen;
            }
            addSingleByte(C_QUOTE);
        }
        if (isName) {
            addSingleByte(C_COLON);
        }
    }

    private boolean hasRoom(int chunk) {
        return (pos + chunk) < SIZE;
    }

    private void writeBuffer() throws IOException {
        out.write(buf, 0, pos);
        pos = 0;

    }
}
