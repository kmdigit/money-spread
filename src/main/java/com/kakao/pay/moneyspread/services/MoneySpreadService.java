package com.kakao.pay.moneyspread.services;

import com.kakao.pay.moneyspread.models.SpreadSeed;

public interface MoneySpreadService {
    String createRoom(final String roomId, final long userId, final SpreadSeed spreadSeed);
}
