package kr.co.chooz.user.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String nickname;

    @Column
    private String email;

    private String imageUrl;

    private String password;


    private String providerId;  // oauth2를 이용할 경우 아이디값

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;


    public User toDomainUser() {
        return new User(id, nickname, email, password, providerId, providerType);
    }


    public UserJpaEntity(User user) {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.providerId = user.getProviderId();
        this.providerType = user.getProviderType();
    }

}