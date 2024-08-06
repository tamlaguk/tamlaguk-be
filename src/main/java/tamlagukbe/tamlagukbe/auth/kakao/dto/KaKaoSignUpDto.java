package tamlagukbe.tamlagukbe.auth.kakao.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tamlagukbe.tamlagukbe.member.entity.Member;
import tamlagukbe.tamlagukbe.member.entity.type.MemberRoleType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoSignUpDto {

      @Email
      private String email;

      public static Member toEntity(KaKaoSignUpDto request) {
            return Member.builder()
                    .email(request.getEmail())
                    .roleType(MemberRoleType.USER)
                    .build();
      }

}
