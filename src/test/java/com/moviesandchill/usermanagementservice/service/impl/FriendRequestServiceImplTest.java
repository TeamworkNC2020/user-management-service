package com.moviesandchill.usermanagementservice.service.impl;

import com.moviesandchill.usermanagementservice.dto.friendrequest.NewFriendRequestDto;
import com.moviesandchill.usermanagementservice.dto.user.NewUserDto;
import com.moviesandchill.usermanagementservice.dto.user.UserDto;
import com.moviesandchill.usermanagementservice.repository.FriendRequestRepository;
import com.moviesandchill.usermanagementservice.repository.UserRepository;
import com.moviesandchill.usermanagementservice.service.FriendRequestService;
import com.moviesandchill.usermanagementservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FriendRequestServiceImplTest {
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @BeforeEach
    public void beforeEach() {
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllFriendRequests() {
        var userDto1 = createAndAddUser("user1");
        var userDto2 = createAndAddUser("user2");
        var userDto3 = createAndAddUser("user3");
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto1.getUserId(), userDto2.getUserId()));
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto2.getUserId(), userDto1.getUserId()));
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto2.getUserId(), userDto3.getUserId()));

        var friendRequests = friendRequestService.getAllFriendRequests();

        assertThat(friendRequests).hasSize(3);
    }

    @Test
    void addFriendRequest() {
        var userDto1 = createAndAddUser("user1");
        var userDto2 = createAndAddUser("user2");

        var friendRequest = friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto1.getUserId(), userDto2.getUserId()));

        assertThat(friendRequest.getUserId()).isEqualTo(userDto1.getUserId());
        assertThat(friendRequest.getRecipientId()).isEqualTo(userDto2.getUserId());
    }

    @Test
    void deleteAllFriendRequests() {
        var userDto1 = createAndAddUser("user1");
        var userDto2 = createAndAddUser("user2");
        var userDto3 = createAndAddUser("user3");
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto1.getUserId(), userDto2.getUserId()));
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto2.getUserId(), userDto1.getUserId()));
        friendRequestService.addFriendRequest(new NewFriendRequestDto(userDto2.getUserId(), userDto3.getUserId()));

        friendRequestService.deleteAllFriendRequests();

        var friendRequests = friendRequestService.getAllFriendRequests();
        assertThat(friendRequests).hasSize(0);
    }

    private UserDto createAndAddUser(String name) {
        var newUserDto = NewUserDto.builder()
                .description(name + " description")
                .logoUrl(name + " logo url")
                .birthday(LocalDate.now())
                .email(name + " email")
                .login(name + " login")
                .password(name + " password")
                .build();
        return userService.addUser(newUserDto);
    }
}
