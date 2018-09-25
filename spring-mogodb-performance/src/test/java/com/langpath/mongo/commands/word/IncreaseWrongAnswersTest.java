package com.langpath.mongo.commands.word;

import com.langpath.mongo.model.Word;
import com.langpath.mongo.model.WordGroup;
import com.langpath.mongo.repository.WordGroupRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by root on 11.10.16.
 */
public class IncreaseWrongAnswersTest extends AbstractTestMongo{

    @Autowired
    private IncreaseWrongAnswers command;

    @Autowired
    private WordGroupRepository repository;

    @Test
    public void updateWrongAnswers(){
        Page<WordGroup> wordGroups = repository.findAll(new PageRequest(1,300));
        wordGroups.forEach( test -> {
            Word word = test.getFishCards().stream().findFirst().get().getWord();
            String value = word.getValue();
            command.increaseWrongAnswers(test.getId(), value);
        });

    }
}