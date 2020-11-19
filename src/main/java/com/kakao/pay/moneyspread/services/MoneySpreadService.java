package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.entities.SpreadRoom;
import com.kakao.pay.moneyspread.models.ApiRequest;

public interface MoneySpreadService {
    String createRoom(final String roomId, final long userId, final ApiRequest.SpreadSeed spreadSeed);
    long digUp(final String token, final long userId, final String roomId);
    SpreadRoom findRoom(final String token, final long userId);
}
