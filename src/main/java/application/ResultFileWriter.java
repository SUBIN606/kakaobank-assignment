package application;

import fileutils.FileWriter;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Map;

/** 학교별 카운트 결과를 txt 파일에 작성합니다. */
public class ResultFileWriter {

    private static final String FILE_NAME = "result.txt";
    private static final String FORMAT = "%s\t%d\n";

    private final FileWriter fileWriter;

    public ResultFileWriter(final FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    /** 주어진 결과를 txt 파일에 작성합니다. */
    public void write(Map<String, Long> results) {
        try {
            Logger.info("결과를 {} 파일에 저장합니다.", FILE_NAME);
            fileWriter.write(FILE_NAME, convertMapToString(results));
            Logger.info("결과가 정상적으로 저장되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** 결과가 담긴 Map이 주어지면 결과 저장 형식에 맞는 문자열로 변환해 반환합니다. */
    private String convertMapToString(Map<String, Long> map) {
        Logger.info("찾은 학교 갯수: {}", map.size());
        StringBuffer stringBuffer = new StringBuffer();

        map.forEach((key, value) ->
            stringBuffer.append(
                    String.format(FORMAT, key, value)
            )
        );
        return stringBuffer.toString();
    }
}
