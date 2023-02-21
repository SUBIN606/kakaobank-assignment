package application;

import fileutils.FileWriter;

import java.io.IOException;
import java.util.Map;

/** 학교별 카툰트 결과를 txt 파일에 작성합니다. */
public class ResulFileWriter {

    private static final String FILE_NAME = "result.txt";
    private static final String FORMAT = "%s\t%d\n";

    private final FileWriter fileWriter;

    public ResulFileWriter(final FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    /** 주어진 결과를 txt 파일에 작성합니다. */
    public void write(Map<String, Long> results) {
        try {
            fileWriter.write(FILE_NAME, convertMapToString(results));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** 결과가 담긴 Map이 주어지면 결과 저장 형식에 맞는 문자열로 변환해 반환합니다. */
    private String convertMapToString(Map<String, Long> map) {
        StringBuffer stringBuffer = new StringBuffer();

        map.forEach((key, value) ->
            stringBuffer.append(
                    String.format(FORMAT, key, value)
            )
        );
        return stringBuffer.toString();
    }
}
