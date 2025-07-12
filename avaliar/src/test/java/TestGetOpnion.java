import com.newsletter.model.MessageOpinion;
import com.newsletter.service.SentimentAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.Map;
import java.lang.String;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@SpringBootTest(classes = {SentimentAnalysis.class})
public class TestGetOpnion {

    //private static final Logger log = LoggerFactory.getLogger(TestGetOpnion.class);


    private final SentimentAnalysis analysis;


    @Autowired
    public TestGetOpnion(SentimentAnalysis analysis) {
        this.analysis = analysis;
    }


    MessageOpinion messageLiked = new MessageOpinion("Eu gostei muito do serviço, recomendo", 10L);
    MessageOpinion messageDoesNotLiked = new MessageOpinion("Eu não gostei, não usaria novamente", 210L);
    MessageOpinion messageDoesNotMatter = new MessageOpinion("A página do email é ali", 5438252L);


    Map<String, MessageOpinion> comments = Map.of(
            "Liked", messageLiked,
            "DN Liked", messageDoesNotLiked,
            "Not opined", messageDoesNotMatter
    );


    @Test
    public void userLiked() {
        double grade = analysis.analyzeSentiment(comments.get("Liked"));
        log.info("User that liked has the grade: {}", grade);
        assertTrue(grade > 8);
    }


    @Test
    public void userDoesNotLiked() {
        double grade = analysis.analyzeSentiment(comments.get("DN Liked"));
        log.info("User that doesn't liked has the grade: {}", grade);
        assertTrue(grade < 5);
    }


    @Test
    public void userIndiferent() {
        double grade = analysis.analyzeSentiment(comments.get("Not opined"));
        log.info("User that has not opined has the grade: {}", grade);
        assertTrue(grade > 4 && grade < 6);
    }
}





