import application.CommentPreparer;
import application.CommentsReader;
import fileutils.CsvReader;
import application.ResultFileWriter;
import application.SchoolNameRepetitionCounter;
import application.SchoolNamesReader;
import fileutils.FileWriter;
import org.apache.commons.csv.CSVFormat;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CommentsReader commentsReader = new CommentsReader(
                new CsvReader(CSVFormat.INFORMIX_UNLOAD)
        );
        List<String> comments = commentsReader.read();

        SchoolNamesReader schoolNamesReader = new SchoolNamesReader(
                new CsvReader(CSVFormat.INFORMIX_UNLOAD)
        );
        List<String> schools = schoolNamesReader.read();

        SchoolNameRepetitionCounter counter
                = new SchoolNameRepetitionCounter(new CommentPreparer());

        ResultFileWriter resultFileWriter = new ResultFileWriter(new FileWriter());
        resultFileWriter.write(counter.countSchoolNameRepetitions(comments, schools));
    }
}
