package com.kakao.pay.moneyspread.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiRequest {
    /**
     * 뿌리기 API 요청 RequestBody 클래스
     */
    @Getter @Setter
    public static class SpreadSeed {
        /**
         * 뿌릴 금액
         */
        private long seedMoney;
        /**
         * 뿌릴 인원
         */
        private int userCount;
    }

    @Getter
    public static class SpreadRetrieve {
        private final LocalDateTime createdTime;
        private final long spreadMoney;
        private final long receivedTotalMoney;
        @Setter
        private final List<Digger> diggers = new ArrayList<>();

        public SpreadRetrieve(LocalDateTime createdTime, long spreadMoney, long receivedTotalMoney) {
            this.createdTime = createdTime;
            this.spreadMoney = spreadMoney;
            this.receivedTotalMoney = receivedTotalMoney;
        }

        public void addDigger(final long userId, final long money) {
            diggers.add(new Digger(userId, money));
        }

        @Getter
        @RequiredArgsConstructor
        class Digger {
            private final long userId;
            private final long money;
        }
    }
}
