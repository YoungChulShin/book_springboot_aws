package com.ycshin.book.springboot.config.auth;

import com.ycshin.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 비활성화
                .and()
                    .authorizeRequests()    // URL별 권환 관리를 시작하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // /api/v1/ 주소는 USER 권한에만 허용
                    .anyRequest().authenticated()   // 나머지는 인증된 사용자들에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃하면 "/" 경로로 이동
                .and()
                    .oauth2Login()  // OAuth 2로그인 기능에 대한 설정들의 진입점
                        .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 담당
                            .userService(customOAuth2UserService);  // 소셜 로그인 성공 이후 후속 조치를 취할 인터페이스의 구현체
                                                                    // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }
}
