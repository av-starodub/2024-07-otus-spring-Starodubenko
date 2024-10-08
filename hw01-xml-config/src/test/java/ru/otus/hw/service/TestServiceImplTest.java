package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestServiceImplTest")
public class TestServiceImplTest {

    private final List<Question> questions = List.of(
            new Question("What true?", List.of(new Answer("A", false), new Answer("B", true)))
    );

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    @DisplayName("Should get questions from QuestionDao and pass them to IOService")
    void executeTest() {
        var questionTemplate = TestServiceImpl.QUESTION_FORMAT_TEMPLATE;
        var answerTemplate = TestServiceImpl.ANSWER_FORMAT_TEMPLATE;

        when(questionDao.findAll()).thenReturn(questions);

        testService.executeTest();

        var expectedQuestionsArgs = new Object[]{1, "What true?"};
        var expectedAnswersArgs = List.of(new Object[]{1, "A"}, new Object[]{2, "B"});

        verify(ioService).printFormattedLine(questionTemplate, expectedQuestionsArgs);
        verify(ioService).printFormattedLine(answerTemplate, expectedAnswersArgs.get(0));
        verify(ioService).printFormattedLine(answerTemplate, expectedAnswersArgs.get(1));
    }
}
