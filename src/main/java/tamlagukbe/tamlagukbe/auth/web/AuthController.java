package tamlagukbe.tamlagukbe.auth.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tamlagukbe.tamlagukbe.auth.dto.ReissueDto;
import tamlagukbe.tamlagukbe.auth.dto.SignInDto;
import tamlagukbe.tamlagukbe.auth.jwt.dto.TokenDto;
import tamlagukbe.tamlagukbe.auth.kakao.dto.KaKaoSignUpDto;
import tamlagukbe.tamlagukbe.auth.kakao.service.KakaoService;
import tamlagukbe.tamlagukbe.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping(path = "/reissue", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> reissue(@Valid @RequestBody ReissueDto request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
    @GetMapping("/redirected/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok(kakaoService.kakaoLogin(code));
    }
}
