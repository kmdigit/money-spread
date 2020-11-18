package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.exceptions.RoomNotFoundException;
import com.kakao.pay.moneyspread.exceptions.UserAccessDeniedException;
import com.kakao.pay.moneyspread.models.SpreadSeed;
import com.kakao.pay.moneyspread.repositories.SpreadRoomRepository;
import com.kakao.pay.moneyspread.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MoneySpreadServiceImpl implements MoneySpreadService {
    private final SpreadRoomRepository spreadRoomRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    @Override
    public String createRoom(String roomId, long userId, SpreadSeed spreadSeed) {
        String token = getToken();
        spreadRoomRepository.save(
                new SpreadRoom(token, userId, roomId, spreadSeed.getSeedMoney(), spreadSeed.getUserCount())
        );
        return token;
    }

    @Transactional(readOnly = true)
    @Override
    public SpreadRoom findRoom(String token, long userId) {
        SpreadRoom spreadRoom = spreadRoomRepository.findByToken(token);
        LocalDateTime expiredDate = LocalDateTime.now().minusDays(7);

        if (spreadRoom == null) {
            throw new RoomNotFoundException();
        } else if (spreadRoom.getUserId() != userId) {
            throw new UserAccessDeniedException();
        } else if (spreadRoom.getCreatedTime().isBefore(expiredDate)) {
            throw new UserAccessDeniedException();
        }
        return spreadRoom;
    }

    private String getToken() {
        String token = "";
        do {
            token = TokenGenerator.create(3);
        } while (spreadRoomRepository.findByToken(token) != null);
        return token;
    }
}
