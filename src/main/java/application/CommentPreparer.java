package application;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

public class CommentPreparer {

    private static final String REGEX = "[^가-힣a-zA-Z]";
    private static final String REPLACE_EMPTY = "";
    private static final String[][] ELEMENT_SCHOOL_REPLACES = {
            {"초"},
            {"초등학교"}
    };
    private static final String[][] MIDDLE_SCHOOL_REPLACES = {
            {"중", "여중", "체중", "예중"},
            {"중학교", "여자중", "체육중", "예술중"}
    };
    private static final String[][] HIGH_SCHOOL_REPLACES = {
            {"고", "여고", "체고", "예고"},
            {"고등학교", "여자고", "체육고", "예술고"}
    };
    private static final String[][] UNIV_SCHOOL_REPLACES = {
            {"[^대구]대", "여대", "체대", "예대"},
            {"대학교", "여자대", "체육대", "예술대"}
    };

    /**
     * 원본 텍스트를 받아 검색을 위한 텍스트로 변경후 반환합니다.
     */
    public String[] replaceForSearch(String originalText) {
        String removedText = removeUnnecessaryCharacters(originalText);
        return new String[] {
                replaceEach(removedText, ELEMENT_SCHOOL_REPLACES),
                replaceEach(removedText, MIDDLE_SCHOOL_REPLACES),
                replaceEach(removedText, HIGH_SCHOOL_REPLACES),
                replaceEach(removedText, UNIV_SCHOOL_REPLACES)
        };
    }

    /**
     * 한글 혹은 영문자를 제외한 문자열을 제거 후 반환합니다.
     *
     * @param originalText 원본 텍스트
     * @return 한글 혹은 영문자를 제외한 모든 문자열이 제거된 문자열
     */
    private String removeUnnecessaryCharacters(String originalText) {
        return RegExUtils.replaceAll(originalText, REGEX, REPLACE_EMPTY);
    }

    /**
     * 원본 문자열에서 정규식과 일치하는 부분을 대치 후 반환합니다.
     *
     * @param originalText 원본 문자열
     * @param replaces     정규식과 대치할 문자열이 담긴 2차원 배열
     */
    private String replaceEach(String originalText, String[][] replaces) {
        return StringUtils.replaceEachRepeatedly(
                originalText,
                replaces[0],
                replaces[1]
        );
    }
}
