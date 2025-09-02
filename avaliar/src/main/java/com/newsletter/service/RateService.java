package com.newsletter.service;

import com.newsletter.exception.AlreadyAnswered;
import com.newsletter.exception.UpdateFailedException;
import com.newsletter.model.Rate;
import com.newsletter.repository.CommentsRepository;
import com.newsletter.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RateService {

    private final RateRepository rates;
    private final CommentsRepository comments;

    @Autowired
    public RateService(RateRepository rateRepository, CommentsRepository comments) {
        this.rates = rateRepository;
        this.comments = comments;
    }

    @Transactional
    public void updateAverage(long version, double newGrade) {
        Optional<Rate> existingRate = rates.findByVersionForUpdate(version);

        Rate rate = existingRate.orElseGet(() -> {
            Rate newRate = new Rate();
            newRate.setVersion(version);
            newRate.setDate_rate(LocalDate.now());
            newRate.setQuantComments(0);
            newRate.setAverage(0.0);
            return rates.save(newRate);
        });

        rate.updateAverage(newGrade);
        rates.save(rate);
    }

    public void verifyCopy(long user, long version) {
        if (comments.findByVersionAndIdUser(version, user).isPresent()) {
            throw new AlreadyAnswered();
        }
    }

}
