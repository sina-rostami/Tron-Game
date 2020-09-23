package ks.models;

import java.util.HashMap;
import java.util.Map;

public enum ECell {
    Empty((byte) 0),
    AreaWall((byte) 1),
    BlueWall((byte) 2),
    YellowWall((byte) 3),
    ;

    private static Map<Byte, ECell> reverseLookup;
    private final byte value;


    ECell(byte value) {
        this.value = value;
    }

    public static ECell of(byte value) {
        if (reverseLookup == null) {
            reverseLookup = new HashMap<>();
            for (ECell c : ECell.values())
                reverseLookup.put(c.getValue(), c);
        }
        return reverseLookup.get(value);
    }

    public byte getValue() {
        return value;
    }

}
