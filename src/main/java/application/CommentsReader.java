package application;

import fileutils.CsvReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

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
            return StreamSupport.stream(
                            csvReader.read(FILE_NAME)
                                    .spliterator(),
                            true
                    ).map(record -> record.get(0))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
