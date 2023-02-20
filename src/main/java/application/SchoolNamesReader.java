package application;

import fileutils.CsvReader;

import java.util.List;
import java.util.stream.StreamSupport;

/** 학교 목록이 담긴 csv 파일을 읽습니다. */
public class SchoolNamesReader {

    private static final String FILE_NAME = "schools.csv";

    private final CsvReader csvReader;

    public SchoolNamesReader(final CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    /**
     * schools.csv 파일의 내용을 읽어 반환합니다.
     *
     * @return 학교 리스트
     */
    public List<String> read() {
        return StreamSupport.stream(
                csvReader.read(FILE_NAME)
                        .spliterator(),
                        true
                ).skip(1)
                .map(record -> record.get(0))
                .toList();
    }
}
