import application.CommentPreparer;
import application.CommentsReader;
import fileutils.CsvReader;
import application.ResultFileWriter;
import application.SchoolNameRepetitionCounter;
import application.SchoolNamesReader;
import fileutils.FileWriter;
import org.apache.commons.csv.CSVFormat;
import org.tinylog.Logger;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader(CSVFormat.INFORMIX_UNLOAD);

        Logger.info("댓글리스트 파일 읽기를 시작합니다.");
        CommentsReader commentsReader = new CommentsReader(csvReader);
        List<String> comments = commentsReader.read();
        Logger.info("댓글리스트 파일 읽기를 완료했습니다.");

        Logger.info("학교 이름 리스트 파일 읽기를 시작합니다.");
        SchoolNamesReader schoolNamesReader = new SchoolNamesReader(csvReader);
        List<String> schools = schoolNamesReader.read();
        Logger.info("학교 이름 리스트 파일 읽기를 완료했습니다.");

        SchoolNameRepetitionCounter counter
                = new SchoolNameRepetitionCounter(new CommentPreparer());

        ResultFileWriter resultFileWriter = new ResultFileWriter(new FileWriter());
        resultFileWriter.write(counter.countSchoolNameRepetitions(comments, schools));
    }
}
