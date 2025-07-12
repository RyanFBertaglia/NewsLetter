package com.newsletter.service;

import com.newsletter.exception.AlreadyAnswered;
import com.newsletter.exception.UpdateFailedException;
import com.newsletter.model.Rate;
import com.newsletter.repository.CommentsRepository;
import com.newsletter.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    private final RateRepository rates;
    private final CommentsRepository comments;

    @Autowired
    public RateService(RateRepository rateRepository, CommentsRepository comments) {
        this.rates = rateRepository;
        this.comments = comments;
    }

    public void updateAverage(long version, double newGrade) {
        Rate rate = rates.findById(version)
                .orElseThrow(UpdateFailedException::new);

        rate.updateAverage(newGrade);

        rates.save(rate);
    }

    public void verifyCopy(long user, long version) {
        if (comments.findByVersionAndIdUser(version, user).isPresent()) {
            throw new AlreadyAnswered();
        }
    }

}
