package com.moviesandchill.usermanagementservice.service.impl;

import com.moviesandchill.usermanagementservice.dto.friendrequest.FriendRequestDto;
import com.moviesandchill.usermanagementservice.dto.friendrequest.NewFriendRequestDto;
import com.moviesandchill.usermanagementservice.mapper.FriendRequestMapper;
import com.moviesandchill.usermanagementservice.repository.FriendRequestRepository;
import com.moviesandchill.usermanagementservice.service.FriendRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FriendRequestServiceImpl implements FriendRequestService {

    private FriendRequestRepository friendRequestRepository;
    private FriendRequestMapper friendRequestMapper;

    @Override
    public List<FriendRequestDto> getAllFriendRequests() {
        var friendRequests = friendRequestRepository.findAll();
        return friendRequestMapper.mapToDto(friendRequests);
    }

    @Override
    public FriendRequestDto addFriendRequest(NewFriendRequestDto newFriendRequestDto) {
        var friendRequest = friendRequestMapper.mapToEntity(newFriendRequestDto);
        friendRequest = friendRequestRepository.save(friendRequest);
        return friendRequestMapper.mapToDto(friendRequest);
    }

    @Override
    public void deleteAllFriendRequests() {
        friendRequestRepository.deleteAll();
    }

    @Autowired
    public void setFriendRequestRepository(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    @Autowired
    public void setFriendRequestMapper(FriendRequestMapper friendRequestMapper) {
        this.friendRequestMapper = friendRequestMapper;
    }
}
