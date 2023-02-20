package fileutils;

import java.io.BufferedWriter;
import java.io.IOException;

/** 파일에 내용을 작성합니다. */
public class FileWriter {

    /**
     * 파일명과 파일에 작성할 내용이 주어지면 해당 파일에 내용을 덮어씁니다.
     */
    public void write(String fileName, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new java.io.FileWriter(fileName)
            );
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
