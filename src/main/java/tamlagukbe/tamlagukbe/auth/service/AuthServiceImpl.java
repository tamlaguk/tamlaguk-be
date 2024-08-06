package tamlagukbe.tamlagukbe.auth.service;

import static tamlagukbe.tamlagukbe.global.exception.type.ErrorCode.INVALID_TOKEN;
import static tamlagukbe.tamlagukbe.global.exception.type.ErrorCode.USER_NOT_FOUND;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tamlagukbe.tamlagukbe.auth.dto.ReissueDto;
import tamlagukbe.tamlagukbe.auth.dto.SignInDto;
import tamlagukbe.tamlagukbe.auth.jwt.TokenProvider;
import tamlagukbe.tamlagukbe.auth.jwt.dto.TokenDto;
import tamlagukbe.tamlagukbe.global.exception.GlobalException;
import tamlagukbe.tamlagukbe.global.service.RedisService;
import tamlagukbe.tamlagukbe.member.entity.Member;
import tamlagukbe.tamlagukbe.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    private static final String REFRESH_TOKEN_PREFIX = "RT: ";

    @Transactional
    @Override
    public TokenDto signIn(SignInDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GlobalException(USER_NOT_FOUND));

        return generateToken(member.getEmail(), member.getRoleType().getCode());
    }

    @Transactional
    @Override
    public TokenDto reissue(ReissueDto reissueDto) {
        String data = redisService.getData(REFRESH_TOKEN_PREFIX + reissueDto.getAccessToken());

        if (!StringUtils.hasText(data) || !data.equals(reissueDto.getRefreshToken())) {
            throw new GlobalException(INVALID_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(reissueDto.getAccessToken());
        redisService.deleteData(REFRESH_TOKEN_PREFIX + reissueDto.getAccessToken());

        return generateToken(authentication.getName(), getAuthorities(authentication));
    }

    @Override
    public TokenDto generateToken(String email, String roleType) {
        TokenDto tokenDto = tokenProvider.generateToken(email, roleType);

        redisService.setDataExpire(REFRESH_TOKEN_PREFIX + tokenDto.getAccessToken(),
                tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpireTime());

        return tokenDto;
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
    }
}
