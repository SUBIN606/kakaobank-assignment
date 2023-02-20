package application;

import fileutils.FileWriter;

import java.util.Map;

/** 학교별 카툰트 결과를 txt 파일에 작성합니다. */
public class ResulFileWriter {

    private static final String FILE_NAME = "result.txt";

    private final FileWriter fileWriter;

    public ResulFileWriter(final FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    /** 주어진 결과를 txt 파일에 작성합니다. */
    public void write(Map<String, Long> results) {
        StringBuffer stringBuffer = new StringBuffer();

        results.forEach((key, value) ->
            stringBuffer.append(
                    String.format("%s\t%d\n", key, value)
            )
        );

        fileWriter.write(FILE_NAME, stringBuffer.toString());
    }
}
