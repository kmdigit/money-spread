package com.kakao.pay.moneyspread.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor @Builder
@Getter @ToString @EqualsAndHashCode
@Entity
public class SpreadRoom {
    /**
     * 식별키 토큰
     */
    @Id @NonNull
    @Column(length = 3, nullable = false)
    private String token;

    /**
     * 방 만든 사람
     */
    @NonNull
    @Column(nullable = false)
    private Long userId;

    /**
     * 방 아이디
     */
    @NonNull
    @Column(nullable = false)
    private String roomId;


    /**
     * 뿌릴 금액
     */
    @NonNull
    @Column(nullable = false)
    private Long seedMoney;

    /**
     * 뿌릴 인원
     */
    @NonNull
    @Column(nullable = false)
    private Integer userCount;

    /**
     * 방 생성 시간
     */
    @CreatedDate
    private LocalDateTime createdTime;

    /**
     * 방에 참여한 사람들
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "spreadRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecvUser> recvUsers;

    public void addUser(RecvUser recvUser) {
        recvUsers.add(recvUser);
        recvUser.setSpreadRoom(this);
    }
}
