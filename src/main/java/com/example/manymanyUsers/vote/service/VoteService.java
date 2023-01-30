package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.GetVoteListRequest;
import com.example.manymanyUsers.vote.dto.UpdateVoteRequest;
import com.example.manymanyUsers.vote.dto.VoteListData;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public Long createVote(@Valid CreateVoteRequest createVoteRequest, Long userId) throws UserNotFoundException{
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Vote vote = Vote.builder()
                .postedUser(findUser)
                .totalTitle(createVoteRequest.getTitle())
                .imageA(createVoteRequest.getImageA())
                .imageB(createVoteRequest.getImageB())
                .detail(createVoteRequest.getDetail())
                .filteredGender(createVoteRequest.getFilteredGender())
                .filteredAge(createVoteRequest.getFilteredAge())
                .category(createVoteRequest.getCategory())
                .filteredMbti(createVoteRequest.getFilteredMbti())
                .build();

        voteRepository.save(vote);

        return vote.getId();

    }


    public void doVote() {

    }

    public Slice<VoteListData> getVoteList(SortBy sortBy, Integer page, Integer size, Category category){

        Slice<Vote> voteSlice;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy.getValue()));

        if (category == null) {
            voteSlice = voteRepository.findSliceBy(pageRequest);
        }else{

            voteSlice = voteRepository.findByCategory(category,pageRequest);
        }

        Slice<VoteListData> voteListData = voteSlice.map(vote -> {
            vote.getPostedUser(); //프록시 처리된 user 엔티티 가져오기 위함
            return new VoteListData(vote);
        });
        return voteListData;
    }

    public void updateVote(@Valid UpdateVoteRequest updateVoteRequest, Long userId) throws UserNotFoundException, VoteNotFoundException {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Vote vote = voteRepository.findById(updateVoteRequest.getVoteId()).orElseThrow(VoteNotFoundException::new);

        vote.update(updateVoteRequest);

    }

    public void deleteVote(Long voteId, Long userId) throws UserNotFoundException {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        voteRepository.deleteById(voteId);

    }

}
