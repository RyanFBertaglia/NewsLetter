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


@Slf4j
@SpringBootTest(classes = {SentimentAnalysis.class})
public class TestGetOpnion {

    //private static final Logger log = LoggerFactory.getLogger(TestGetOpnion.class);


    private final SentimentAnalysis analysis;


    @Autowired
    public TestGetOpnion(SentimentAnalysis analysis) {
        this.analysis = analysis;
        log.info(comments.toString());
    }


    MessageOpinion messageLiked = new MessageOpinion("Eu não gostei", LocalDate.of(2025, 2, 10));
    MessageOpinion messageDoesNotLiked = new MessageOpinion("Eu gostei muito do serviço", LocalDate.of(2025, 2, 10));
    MessageOpinion messageDoesNotMatter = new MessageOpinion("A cor do email é amarela", LocalDate.of(2025, 2, 10));


    Map<String, MessageOpinion> comments = Map.of(
            "Liked", messageLiked,
            "DN Liked", messageDoesNotLiked,
            "Not opined", messageDoesNotMatter
    );


    @Test
    public void userLiked() {
        double grade = analysis.analyzeSentiment(comments.get("Liked"));
        log.info("User that liked has the grade: {}", grade);
    }


    @Test
    public void userDoesNotLiked() {
        double grade = analysis.analyzeSentiment(comments.get("DN Liked"));
        log.info("User that doesn't liked has the grade: {}", grade);
    }


    @Test
    public void userIndiferent() {
        double grade = analysis.analyzeSentiment(comments.get("Not opined"));
        log.info("User that has not opined has the grade: {}", grade);
    }
}





