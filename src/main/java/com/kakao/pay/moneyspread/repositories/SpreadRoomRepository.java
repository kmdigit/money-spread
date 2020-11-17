package com.kakao.pay.moneyspread.repositories;

import com.kakao.pay.moneyspread.entities.SpreadRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpreadRoomRepository extends JpaRepository<SpreadRoom, String> {
    List<SpreadRoom> findByToken(String token);
}