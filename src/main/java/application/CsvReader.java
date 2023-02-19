package application;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/** csv 확장자 파일을 읽습니다. */
public class CsvReader {

    /**
     * 주어진 파일명과 일치하는 csv 파일을 읽어 반환합니다.
     *
     * @param fileName csv 파일명
     * @return csv 파일 내용
     */
    public Iterable<CSVRecord> read(String fileName) {
        try {
            Reader reader = new FileReader(
                    getPath(fileName)
            );

            return CSVFormat.INFORMIX_UNLOAD
                    .parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /** 파일의 path 반환 */
    private String getPath(final String fileName) {
        return Objects.requireNonNull(
                getClass().getClassLoader().getResource(fileName)
        ).getPath();
    }
}
