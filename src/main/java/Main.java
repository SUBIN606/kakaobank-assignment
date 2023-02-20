import application.CommentsReader;
import fileutils.CsvReader;
import application.ResulFileWriter;
import application.SchoolNameRepetitionCounter;
import application.SchoolNamesReader;
import fileutils.FileWriter;
import org.apache.commons.csv.CSVFormat;
import pojo.Comment;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader(CSVFormat.INFORMIX_UNLOAD);

        CommentsReader commentsReader = new CommentsReader(csvReader);
        List<Comment> comments = commentsReader.read();

        SchoolNamesReader schoolNamesReader = new SchoolNamesReader(csvReader);
        List<String> schools = schoolNamesReader.read();

        SchoolNameRepetitionCounter counter = new SchoolNameRepetitionCounter();

        ResulFileWriter resulFileWriter
                = new ResulFileWriter(new FileWriter());
        resulFileWriter.write(
                counter.countSchoolNameRepetitions(comments, schools)
        );
    }
}
