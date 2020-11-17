package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.SpreadSeed;
import com.kakao.pay.moneyspread.repositories.SpreadRoomRepository;
import com.kakao.pay.moneyspread.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MoneySpreadServiceImpl implements MoneySpreadService {
    private final SpreadRoomRepository spreadRoomRepository;

    @Override
    public String createRoom(String roomId, long userId, SpreadSeed spreadSeed) {
        String token = getToken();
        spreadRoomRepository.save(
                SpreadRoom.builder()
                        .token(token)
                        .roomId(roomId)
                        .userId(userId)
                        .seedMoney(spreadSeed.getSeedMoney())
                        .userCount(spreadSeed.getUserCount())
                        .build()
        );
        return token;
    }

    private String getToken() {
        String token = "";
        do {
            token = TokenGenerator.create(3);
        } while (spreadRoomRepository.findByToken(token).isEmpty());
        return token;
    }
}
