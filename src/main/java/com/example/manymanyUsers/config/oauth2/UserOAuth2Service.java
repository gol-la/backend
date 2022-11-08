package com.example.manymanyUsers.config.oauth2;

import com.example.manymanyUsers.user.domain.Role;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //여기서 accessToken을 이용해 서버로부터 사용자 정보를 받아옴
        //DefaultOAuth2UserService 클래스의 loadUser() 메서드에 이 기능이 구현되어있기 때문에 super.loadUser() 를 호출하기만하면 된다.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");

        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");
        String profile_img = (String)properties.get("profile_image");
        String kakao_id = (String)properties.get("id");

        System.out.println("properties = " + properties);


        if(userRepository.existsByEmail(email)){
            User user = User.oauth2Register().email(email).username(nickname).imageUrl(profile_img).provider("kakao").providerId(kakao_id).build();
        }else{
            System.out.println("이미 가입한 유저이므로 회원가입을 진행하지 않습니다.");
        }
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())), attributes, "id");
    }
}
