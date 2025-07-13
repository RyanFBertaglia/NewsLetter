package com.newsletter.service;

import com.newsletter.dto.RatingDatailsDTO;
import com.newsletter.dto.UserCommentDTO;
import com.newsletter.exception.MediaNotFound;
import com.newsletter.exception.VersionNotFound;
import com.newsletter.model.feedback.Rate;
import com.newsletter.dto.RateAverageDTO;
import com.newsletter.dto.UserShareDTO;
import com.newsletter.repository.CommentsRepository;
import com.newsletter.repository.RateRepository;
import com.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Statistics {

    private final RateRepository ratings;
    private final UserRepository users;
    private final CommentsRepository comments;

    @Autowired
    public Statistics(RateRepository ratings, UserRepository users, CommentsRepository comments) {
        this.ratings = ratings;
        this.users = users;
        this.comments = comments;
    }

    @Transactional(transactionManager = "feedbackTransactionManager")
    public double findAverage(Long version) {
        return Optional.ofNullable(ratings.findAverageMediaByDia(version))
                .orElseThrow(() -> new MediaNotFound("Nenhuma avaliação encontrada para essa versão"));
    }

    @Transactional(transactionManager = "feedbackTransactionManager")
    public List<RateAverageDTO> findAverageByDay(LocalDate date) {
        return Optional.ofNullable(ratings.findMessagesByDay(date))
                .orElseThrow(() -> new MediaNotFound("Nenhuma avaliação encontrada para essa data"));
    }

    @Transactional(transactionManager = "feedbackTransactionManager")
    public List<Rate> getAllRatings() {
        return Optional.of(ratings.findAll())
                .orElseThrow(() -> new MediaNotFound("Nenhuma avaliação encontrada"));
    }

    @Transactional(transactionManager = "usersTransactionManager")
    public List<UserShareDTO> getShares() {
        return users.findUsersShare();
    }

    @Transactional(transactionManager = "feedbackTransactionManager")
    public RatingDatailsDTO getRatingDatails(long version) {
        Rate rate = ratings.findById(version)
                .orElseThrow(VersionNotFound::new);
        List<UserCommentDTO> usersComments = comments.findAllByVersion(version);

        return new RatingDatailsDTO(rate.getVersion(),
                rate.getDate_rate(),
                rate.getAverage(),
                usersComments);
    }
}