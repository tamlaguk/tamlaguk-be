package tamlagukbe.tamlagukbe.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tamlagukbe.tamlagukbe.member.entity.type.MemberRoleType;
import tamlagukbe.tamlagukbe.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private MemberRoleType roleType;

    // 카카오 로그인에 사용될 사용자 정보를 설정하는 메서드
    public static Member createKakaoMember(String email, MemberRoleType roleType) {
        return Member.builder()
                .email(email)
                .roleType(roleType)
                .build();
    }
}
