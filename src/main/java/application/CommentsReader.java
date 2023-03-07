package application;

import fileutils.CsvReader;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 댓글 목록이 담긴 csv 파일을 읽습니다. */
public class CommentsReader {

    private static final String FILE_NAME = "comments.csv";

    private final CsvReader csvReader;

    public CommentsReader(final CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    /**
     * comments.csv 파일의 내용을 읽어 반환합니다.
     *
     * @return 댓글 리스트
     */
    public List<String> read() {
        try {
            List<String> comments = new ArrayList<>();

            Logger.info("댓글리스트 파일 읽기를 시작합니다.");
            csvReader.read(FILE_NAME)
                    .forEach(record -> comments.add(record.get(0)));
            Logger.info("댓글리스트 파일 읽기를 완료했습니다.");

            return comments;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
