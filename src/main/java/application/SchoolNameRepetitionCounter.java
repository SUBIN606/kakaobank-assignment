package application;

import org.apache.commons.lang3.StringUtils;
import pojo.Comment;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 학교 이름의 반복 횟수를 카운트 합니다. */
public class SchoolNameRepetitionCounter {

    String[][] ELEMENT_SCHOOL_REPLACES = {
            {"초"},
            {"초등학교"}
    };
    String[][] MIDDLE_SCHOOL_REPLACES = {
            {"중", "여중", "체중", "예중"},
            {"중학교", "여자중", "체육중", "예술중"}
    };
    String[][] HIGH_SCHOOL_REPLACES = {
            {"고", "여고", "체고", "예고"},
            {"고등학교", "여자고", "체육고", "예술고"}
    };
    String[][] UNIV_SCHOOL_REPLACES = {
            {"[^대구]대", "여대", "체대", "예대"},
            {"대학교", "여자대", "체육대", "예술대"}
    };

    /**
     * 댓글 리스트와 학교 이름 리스트가 주어지면, 댓글의 유효한 학교 이름을 찾아내 카운트합니다.
     * 카운트 한 결과를 Map에 담아 반환합니다.
     *
     * @param comments 댓글 리스트
     * @param schools 학교 이름 리스트
     * @return 카운트 결과
     */
    public Map<String, Long> countSchoolNameRepetitions(
            List<Comment> comments,
            List<String> schools
    ) {
        return comments.parallelStream()
                .map((it) -> findSchoolName(schools, it))
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
    }

    /**
     * 학교 이름 리스트와 댓글이 주어지면 댓글에서 유효한 학교 이름을 찾아 반환합니다.
     * 만약 찾지 못할 경우 null을 반환합니다.
     *
     * @param schools 학교 이름 리스트
     * @param comment 댓글
     * @return 댓글에서 찾은 학교 이름. 찾지 못하면 null 반환
     */
    private String findSchoolName(List<String> schools, Comment comment) {
        String originalText = comment.getText();

        String replaceForElementsSchool = replaceEach(originalText, ELEMENT_SCHOOL_REPLACES);
        String replaceForMiddleSchool = replaceEach(originalText, MIDDLE_SCHOOL_REPLACES);
        String replaceForHighSchool = replaceEach(originalText, HIGH_SCHOOL_REPLACES);
        String replaceForUniversity = replaceEach(originalText, UNIV_SCHOOL_REPLACES);

        return schools.parallelStream()
                .filter(school ->
                        contains(replaceForElementsSchool, school)
                        || contains(replaceForMiddleSchool, school)
                        || contains(replaceForHighSchool, school)
                        || contains(replaceForUniversity, school)
                )
                .findFirst()
                .orElse(null);
    }

    /**
     * 대상 문자열에 검색하고자 하는 문자열의 포함 여부를 반환합니다.
     *
     * @param target 대상 문자열
     * @param searchText 대상 문자열에 포함 여부를 알고자하는 문자열
     * @return 포함되어 있으면 true, 아니면 false
     */
    private boolean contains(String target, String searchText) {
        return StringUtils.contains(target, searchText);
    }

    /**
     * 원본 문자열에서 정규식과 일치하는 부분을 대치 후 반환합니다.
     *
     * @param originalText 원본 문자열
     * @param replaces 정규식과 대치할 문자열이 담긴 2차원 배열
     */
    private String replaceEach(String originalText, String[][] replaces) {
        return StringUtils.replaceEach(
                originalText,
                replaces[0],
                replaces[1]
        );
    }
}
