package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.enums.CHOICE;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class VoteResult {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_RESULT_ID")
    private Long id;


    @OneToOne(mappedBy = "voteResult")
    private Vote vote;

    /**
     * User 와의 연관관계 주인
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User votedUser;

    @Column(nullable = false)
    private CHOICE choice;

}
