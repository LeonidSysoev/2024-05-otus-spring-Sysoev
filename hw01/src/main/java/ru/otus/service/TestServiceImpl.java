package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.dao.CsvQuestionDao;
import ru.otus.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = csvQuestionDao.findAll();
        questions.forEach(question -> {
            System.out.println("Question" + question.text() + "Answer options");
            question.answers().forEach(System.out::println);
            System.out.println();
        });
        // Получить вопросы из дао и вывести их с вариантами ответов
    }
}
