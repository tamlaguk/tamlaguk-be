package tamlagukbe.tamlagukbe.auth.service;

import tamlagukbe.tamlagukbe.auth.dto.ReissueDto;
import tamlagukbe.tamlagukbe.auth.dto.SignInDto;
import tamlagukbe.tamlagukbe.auth.jwt.dto.TokenDto;


public interface AuthService {

    TokenDto signIn(SignInDto request);

    TokenDto reissue(ReissueDto request);

    TokenDto generateToken(String email, String roleType);


}
