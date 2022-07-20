package ru.yandex.practicum.filmorate.sortage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserFriendDbStorageTest {

    private final UserFriendDbStorage userFriendDbStorage;

    @Test
    public void addFriend() {
        userFriendDbStorage.addFriend(1, 2);
        Integer fiendsId = userFriendDbStorage.findAllFriends(1).get(0).getUserId();
        assertThat(fiendsId).isEqualTo(2);
    }

    @Test
    public void findAllFriends() {
        userFriendDbStorage.addFriend(1, 2);
        List<User> allFriends = userFriendDbStorage.findAllFriends(1);
        assertFalse(allFriends.isEmpty());
    }

    @Test
    public void deleteFriends() {
        userFriendDbStorage.deleteFriends(1, 2);
        Integer friend = userFriendDbStorage.findAllFriends(1).size();
        assertThat(friend).isEqualTo(0);
    }
}