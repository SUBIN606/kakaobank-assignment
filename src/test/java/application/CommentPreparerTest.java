package application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommentPreparerTest {

    CommentPreparer preparer = new CommentPreparer();

    @DisplayName("replaceForSearch 메서드")
    @Nested
    class Describe_replace_for_search {

        @DisplayName("학교명을 검색하기 위한 전처리를 완료한 문자열들을 반환한다")
        @Test
        void it_return_prepared_comments() {
            String originalText = "경기, 카뱅고입니다!";

            String[] replaceForSearch = preparer.replaceForSearch(originalText);

            assertThat(replaceForSearch[0]).isEqualTo(originalText);
            assertAll(() -> {
                for (int i = 1; i < replaceForSearch.length; i++) {
                    assertThat(replaceForSearch[i]).isNotEqualTo(originalText);
                }
            });
        }
    }
}
