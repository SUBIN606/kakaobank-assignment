package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SchoolNameRepetitionCounter 클래스")
class SchoolNameRepetitionCounterTest {

    SchoolNameRepetitionCounter counter;

    @BeforeEach
    void setUp() {
        this.counter = new SchoolNameRepetitionCounter(new CommentPreparer());
    }

    @DisplayName("countSchoolNameRepetitions 메서드")
    @Nested
    class Describe_count_school_name_repetitions {
        @DisplayName("댓글 리스트와 학교 이름 리스트가 주어지면")
        @Nested
        class Context_with_comment_list_and_school_name_list {
            List<String> comments = List.of(
                    "서울, 카뱅고",
                    "카뱅고입니다",
                    "강원도, 카뱅여고",
                    "경기도 가나다초 가나다초 가나다초 가나다초 가나다초"
            );

            List<String> schools = List.of(
                    "카뱅고등학교", "가나다초등학교", "카뱅중학교", "카뱅초등학교", "카뱅여자고등학교"
            );

            @DisplayName("댓글 리스트에서 언급된 학교 이름을 카운트 후 결과를 반환한다")
            @Test
            void it_returns_count_result() {
                Map<String, Long> result = counter.countSchoolNameRepetitions(comments, schools);

                assertThat(result).isNotEmpty();
                assertThat(result.get("카뱅고등학교")).isEqualTo(2);
                assertThat(result.get("카뱅여자고등학교")).isEqualTo(1);
            }
        }

        @DisplayName("학교 이름 리스트에는 없지만 학교명을 정확히 입력한 경우는")
        @Nested
        class Context_with_postfix_matched_comments {
            List<String> comments = List.of(
                    "아무개중학교입니다.",
                    "카뱅고"
            );

            List<String> schools = List.of(
                    "카뱅고등학교"
            );

            @DisplayName("카운트 대상에서 제외 한다")
            @Test
            void it_returns_count_postfix_matched_word() {
                Map<String, Long> result = counter.countSchoolNameRepetitions(comments, schools);

                assertThat(result).isNotEmpty();
                assertThat(result.get("아무개중학교")).isEqualTo(null);
                assertThat(result.get("카뱅고등학교")).isEqualTo(1);
            }
        }
    }
}
