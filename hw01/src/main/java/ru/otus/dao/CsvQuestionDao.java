package ru.otus.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.config.TestFileNameProvider;
import ru.otus.dao.dto.QuestionDto;
import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;


    @Override
    public List<Question> findAll() {

        List<Question> questions = new ArrayList<>();
        try (Reader reader = new InputStreamReader(Objects.requireNonNull
                (this.getClass().getResourceAsStream(fileNameProvider.getTestFileName()), "File not found"))) {
            List<QuestionDto> questionDtos = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            for (QuestionDto questionDto : questionDtos) {
                questions.add(questionDto.toDomainObject());
            }
            return questions;
        } catch (IOException e) {
            throw new QuestionReadException("Read error");
        }


        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

    }
}
