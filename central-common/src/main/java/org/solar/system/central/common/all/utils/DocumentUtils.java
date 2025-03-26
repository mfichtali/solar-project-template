package org.solar.system.central.common.all.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@UtilityClass
public class DocumentUtils {

    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressDocument(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];
        while(!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp,0, size);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decompressDocument(byte[] data) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[BITE_SIZE];
        while (!inflater.finished()) {
            int count = inflater.inflate(tmp);
            outputStream.write(tmp, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }

    public static String getFileExtension(String filename) {
        if (filename != null && !filename.isEmpty()) {
            int lastDotIndex = filename.lastIndexOf(".");
            if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
                return filename.substring(lastDotIndex + 1);
            }
        }
        return StringUtils.EMPTY;
    }

    public static String getFileNameWithoutExtension(String filename) {

        if (StringUtils.isBlank(filename)) {
            return StringUtils.EMPTY;
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return filename.substring(0, lastDotIndex);
        }
        return filename;
    }

}