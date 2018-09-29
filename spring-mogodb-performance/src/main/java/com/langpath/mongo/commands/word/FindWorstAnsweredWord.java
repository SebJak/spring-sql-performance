package com.langpath.mongo.commands.word;

import com.langpath.mongo.model.User;
import com.langpath.mongo.model.Word;
import com.langpath.mongo.model.WordGroup;
import com.langpath.mongo.repository.UserRepository;
import com.langpath.mongo.repository.WordGroupRepository;
import com.langpath.mongo.comparator.FishCardWrongAnswerComparator;
import com.langpath.mongo.comparator.WordWorstAnsweredComparator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * Created by root on 14.10.16.
 */
@Service
class FindWorstAnsweredWord {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordGroupRepository groupRepository;

    Optional<Word> findWorstAnsweredGroup(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("Can not find user with id:"+userId));
        Map<ObjectId, WordGroup> wgIds = user.getWordGroups();
        WordGroup wg = groupRepository.findByIdInOrderByFishCardsMeaningsWrongAnswers(wgIds.keySet());
        Optional<FishCard> fishCard = wg.getFishCards().stream().sorted(new FishCardWrongAnswerComparator()).findFirst();
        Optional<Word> word = fishCard.get().getMeanings().stream().sorted(new WordWorstAnsweredComparator()).findFirst();

        return word;
    }
}
