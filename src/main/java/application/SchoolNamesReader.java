package application;

import fileutils.CsvReader;
import org.apache.commons.csv.CSVFormat;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            List<String> schools = new ArrayList<>();

            Logger.info("학교 이름 리스트 파일 읽기를 시작합니다.");
            csvReader.read(FILE_NAME)
                    .forEach(record ->
                            schools.add(record.get(0)));
            Logger.info("학교 이름 리스트 파일 읽기를 완료했습니다.");

            return schools;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
