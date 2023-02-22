package application;

import org.apache.commons.lang3.StringUtils;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** 학교 이름의 반복 횟수를 카운트 합니다. */
public class SchoolNameRepetitionCounter {

    private final Pattern pattern = Pattern.compile("\\b[가-힣a-zA-Z]+( |)+학교");

    private final CommentPreparer preparer;

    public SchoolNameRepetitionCounter(final CommentPreparer preparer) {
        this.preparer = preparer;
    }

    /**
     * 댓글 리스트와 학교 이름 리스트가 주어지면, 댓글의 유효한 학교 이름을 찾아내 카운트합니다.
     * 카운트 한 결과를 Map에 담아 반환합니다.
     *
     * @param comments 댓글 리스트
     * @param schools 학교 이름 리스트
     * @return 카운트 결과
     */
    public Map<String, Long> countSchoolNameRepetitions(
            List<String> comments,
            List<String> schools
    ) {
        Logger.info("댓글 리스트에서 유효한 학교명을 카운트 합니다.");
        Logger.info("댓글 총 갯수: {}", comments.size());

        Map<Boolean, List<String>> result = comments.parallelStream()
                .map(preparer::replaceForSearch)
                .map((it) -> findSchoolName(schools, it))
                .collect(
                        Collectors.partitioningBy(Objects::nonNull)
                );

        List<String> validComments = result.get(true);
        Logger.info("유효한 학교명을 포함한 댓글 갯수: {}", validComments.size());
        List<String> invalidComments = result.get(false);
        Logger.info("유효한 학교명을 찾지 못한 댓글 갯수: {}", invalidComments.size());

        return validComments.parallelStream()
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
     * @param comments 댓글
     * @return 댓글에서 찾은 학교 이름. 찾지 못하면 null 반환
     */
    private String findSchoolName(List<String> schools, String[] comments) {
        return schools.parallelStream()
                .filter(school ->
                        containsAny(comments, school)
                )
                .findFirst()
                .orElseGet(() -> {
                    String match = matchSchoolPostfix(comments[0]);
                    Logger.warn("-".repeat(50));
                    Logger.warn("아래 댓글에서 유효한 학교명을 찾지 못했습니다.\n{}", comments[0]);
                    if (match != null) {
                        Logger.warn("학교 이름으로 추정되는 문자열이 있습니다. : {}", match);
                    }
                    return null;
                });
    }

    /**
     * 문자열 배열에서 찾고자하는 문자열을 포함 여부를 반환합니다.
     *
     * @param targets 대상 문자열이 담긴 배열
     * @param searchText 찾고자하는 문자열
     * @return 포함하고 있는 문자열을 하나라도 찾으면 true, 없으면 false
     */
    private boolean containsAny(String[] targets, String searchText) {
        return Arrays.stream(targets)
                .anyMatch(comment -> contains(comment, searchText));
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
     * 학교 이름 리스트에서 일치하는 학교명을 발견하지 못했으나,
     * 정확히 '학교'라고 입력한 경우를 정규식으로 찾아 반환합니다.
     *
     * @param target 정규식으로 검사할 대상 문자열
     * @return 정규식으로 찾은 학교가 포함된 부분
     *         찾지 못한 경우는 null을 반환합니다.
     */
    private String matchSchoolPostfix(String target) {
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()
                && StringUtils.deleteWhitespace(matcher.group()).length() > 4) {
            return matcher.group();
        }
        return null;
    }
}
