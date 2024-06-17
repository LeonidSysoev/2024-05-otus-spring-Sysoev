package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.config.TestFileNameProvider;
import ru.otus.dao.dto.QuestionDto;
import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionReadException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;


    @Override
    public List<Question> findAll() {

        List<Question> questions = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(fileNameProvider.getTestFileName()));
        } catch (QuestionReadException | FileNotFoundException e) {
            e.printStackTrace();
        }

        CsvToBean<QuestionDto> questionDtos = new CsvToBeanBuilder<QuestionDto>(reader)
                .withSkipLines(1)
                .build();
        List<QuestionDto> questionDto = questionDtos.parse();
        for (QuestionDto q : questionDto) {
            questions.add(q.toDomainObject());
        }
        return questions;
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

    }
}
