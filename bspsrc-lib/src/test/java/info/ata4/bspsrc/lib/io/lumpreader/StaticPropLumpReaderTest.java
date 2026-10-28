package info.ata4.bspsrc.lib.io.lumpreader;

import info.ata4.bspsrc.lib.app.SourceAppId;
import info.ata4.bspsrc.lib.struct.DStaticPropVinScaling;
import info.ata4.bspsrc.lib.vector.Vector3f;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticPropLumpReaderTest {

    @Test
    void readsVindictusVersion7Scale() throws Exception {
        assertVindictusScaleRecovered(7, 64);
    }

    @Test
    void readsVindictusVersion8ScaleWithTrailingStructExtension() throws Exception {
        assertVindictusScaleRecovered(8, 72);
    }

    private void assertVindictusScaleRecovered(int version, int propSize) throws Exception {
        ByteBuffer lump = ByteBuffer.allocate(4 * 5 + 12 + propSize)
                .order(ByteOrder.LITTLE_ENDIAN);

        lump.putInt(0); // model dictionary count
        lump.putInt(0); // leaf count
        lump.putInt(1); // scaling entry count
        lump.putInt(0); // static prop index
        lump.putFloat(1.25f);
        lump.putFloat(2.5f);
        lump.putFloat(3.75f);
        lump.putInt(1); // static prop count
        lump.put(new byte[propSize]);
        lump.flip();

        StaticPropLumpReader.StaticPropData data =
                new StaticPropLumpReader(version, SourceAppId.VINDICTUS).read(lump);

        assertEquals(1, data.props.size());
        Vector3f scale = ((DStaticPropVinScaling) data.props.get(0)).getScaling();
        assertEquals(1.25f, scale.x(), 0.0f);
        assertEquals(2.5f, scale.y(), 0.0f);
        assertEquals(3.75f, scale.z(), 0.0f);
    }
}
