package application;

import fileutils.CsvReader;
import pojo.Comment;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

/** 댓글 목록이 담긴 csv 파일을 읽습니다. */
public class CommentsReader {

    private static final String FILE_NAME = "comments.csv";
    private static final String REGEX = "[^가-힣a-zA-Z]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private final CsvReader csvReader;

    public CommentsReader(final CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    /**
     * comments.csv 파일의 내용을 읽어 반환합니다.
     *
     * @return 댓글 리스트
     */
    public List<Comment> read() {
        return StreamSupport.stream(
                        csvReader.read(FILE_NAME)
                                .spliterator(),
                        true
                ).skip(1)
                .map(record -> record.get(0))
                .map((it) -> new Comment(
                        it,
                        removeUnnecessaryCharacters(it)
                )).toList();
    }

    /**
     * 한글 혹은 영문자를 제외한 문자열을 제거 후 반환합니다.
     *
     * @param originalText 원본 텍스트
     * @return 한글 혹은 영문자를 제외한 모든 문자열이 제거된 문자열
     */
    private String removeUnnecessaryCharacters(String originalText) {
        return PATTERN.matcher(originalText).replaceAll("");
    }
}
