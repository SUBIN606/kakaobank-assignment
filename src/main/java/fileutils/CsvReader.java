package fileutils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/** resources 폴더에 위치한 csv 파일을 읽습니다. */
public class CsvReader {

    private CSVFormat csvFormat = CSVFormat.DEFAULT;

    public CsvReader() {}

    public CsvReader(final CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    /**
     * 주어진 파일명과 일치하는 csv 파일을 읽어 반환합니다.
     *
     * @param fileName csv 파일명
     * @return csv 파일 내용
     */
    public Iterable<CSVRecord> read(String fileName) throws IOException {
        Reader reader = new FileReader(
                getPath(fileName)
        );
        return csvFormat.parse(reader);
    }

    /** 파일의 path 반환 */
    private String getPath(final String fileName) {
        return Objects.requireNonNull(
                getClass().getClassLoader().getResource(fileName)
        ).getPath();
    }
}
