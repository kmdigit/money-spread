package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.entities.RecvUser;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.exceptions.*;
import com.kakao.pay.moneyspread.models.ApiDTO;
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
    public String createRoom(String roomId, long userId, ApiDTO.SpreadSeed spreadSeed) {
        String token = getToken();
        spreadRoomRepository.save(
                new SpreadRoom(token, userId, roomId, spreadSeed.getSeedMoney(),
                        spreadSeed.getSeedMoney(), spreadSeed.getUserCount())
        );
        return token;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public long digUp(String token, long userId, String roomId) {
        SpreadRoom spreadRoom = spreadRoomRepository.findByToken(token);
        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(10);

        if (spreadRoom == null) {
            throw new RoomNotFoundException();
        } else if (spreadRoom.getRoomId() != roomId) {
            throw new NotSameRoomException();
        } else if (spreadRoom.getCreatedTime().isBefore(expireTime)) {
            throw new ExpireTimeException();
        } else if(spreadRoom.getUserId() == userId) {
            throw new UserAccessDeniedException();
        } else if (spreadRoom.getRecvUsers().stream().filter(u -> u.getUserId() == userId).count() != 0) {
            throw new DuplicateUserException();
        } else if (spreadRoom.getLeftMoney() == 0 && spreadRoom.getUserCount() == spreadRoom.getRecvUsers().size()) {
            throw new NotSeedMoneyException();
        }

        RecvUser recvUser = new RecvUser(userId, getDigMoney(spreadRoom, userId));
        spreadRoom.addUser(recvUser);
        return recvUser.getRecvMoney();
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

    private int getDigMoney(final SpreadRoom spreadRoom, final long userId) {
        long digMoney = 0;
        if (spreadRoom.getUserCount() - spreadRoom.getRecvUsers().size() == 1) {
            digMoney = spreadRoom.getLeftMoney();
        } else {
            digMoney = (long)(spreadRoom.getLeftMoney() * Math.random());
        }
        spreadRoom.setLeftMoney(spreadRoom.getLeftMoney() - digMoney);
        return (int) digMoney;
    }
}