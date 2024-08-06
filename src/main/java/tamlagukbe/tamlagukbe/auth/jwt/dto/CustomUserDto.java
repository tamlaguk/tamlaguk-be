package tamlagukbe.tamlagukbe.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tamlagukbe.tamlagukbe.member.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDto {

    private Long id;
    private String email;
    private String roleType;

    public static CustomUserDto fromEntity(Member member) {
        return CustomUserDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .roleType(member.getRoleType().getCode())
                .build();
    }

}
