package info.ata4.bspsrc.lib.struct;

import info.ata4.io.DataReader;

import java.io.IOException;

import static info.ata4.io.Seekable.Origin.CURRENT;

/**
 * Vindictus static prop structure version 8. It retains the version 7 fields
 * and has an additional 8-byte trailing extension (as reported for current
 * Vindictus BSPs).
 */
public class DStaticPropV8VIN extends DStaticPropV7VIN {

    private static final int EXTENSION_SIZE = 8;

    @Override
    public int getSize() {
        return super.getSize() + EXTENSION_SIZE;
    }

    @Override
    public void read(DataReader in) throws IOException {
        super.read(in);
        in.seek(EXTENSION_SIZE, CURRENT);
    }
}
