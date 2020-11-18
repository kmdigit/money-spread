package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.constants.ResponseCode;
import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.SpreadSeed;
import com.kakao.pay.moneyspread.repositories.SpreadRoomRepository;
import com.kakao.pay.moneyspread.utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
            // NotFoundException
            //ResponseCode.ERR_NOTFOUND;
        } else if (spreadRoom.getUserId() != userId) {
            // AccessDenied
//            ResponseCode.ERR_NOTUSER;
        } else if (spreadRoom.getCreatedTime().isBefore(expiredDate)) {
            // AccessDenied
//            ResponseCode.ERR_EXPIRED;
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
