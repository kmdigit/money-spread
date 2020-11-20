package com.kakao.pay.moneyspread.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 수집한 사용자 정보
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter @Setter
@ToString @EqualsAndHashCode
@Entity
public class RecvUser {
    /**
     * 사용자 고유 아이디
     */
    @Id
    @Column(nullable = false) @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 사용자 아이디
     *
     * @note 사용자 아이디로 고유키를 할 경우 똑같은 사용자가 다른 방에 들어갈 수가 없다.
     *       그렇기 때문에 다른 아이디를 부여하고 사용자의 아이디는 일반 컬럼으로 정의한다.
     */
    @NonNull
    @Column(nullable = false)
    private Long userId;

    /**
     * 사용자가 받은 돈
     */
    @NonNull
    @Column(nullable = false)
    private Integer recvMoney;

    /**
     * 사용자가 받은 시간
     */
    @CreatedDate
    private LocalDateTime recvTime;

    /**
     * 사용자가 참여한 방
     */
    @ManyToOne
    @JoinColumn(name = "token")
    private SpreadRoom spreadRoom;
}
