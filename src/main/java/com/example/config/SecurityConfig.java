package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    // favicon, css, js, imagesは匿名アクセスOK
    web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/img/**");

    // ";"を含んだURLを処理できるようにする
    // web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // CSRF対策を無効化する
    http.csrf().disable();

    http
      .authorizeRequests()
    .antMatchers(HttpMethod.GET, "/api/init", "/api/login", "/api/authorize/**", "/api/callback/**").permitAll()
    .anyRequest().authenticated();

    http
      .oauth2Login()
      // ログインページ
      .loginPage("/api/login")
      // アクセストークン取得に成功・失敗した時のリダイレクトURL
      .defaultSuccessUrl("/boards/")
      .failureUrl("/login/?error")
      // リクエストトークンを要求するURLの設定
      // githubの場合、/api/authorize/github。
      .authorizationEndpoint()
      .baseUri("/api/authorize")
      .and()
      // アクセストークンを受け取るコールバックURLの設定
      // githubの場合、/api/callback/github?code=<token>。
      // spring.security.oauth2.client.registration.github.redirectUriTemplate と対応する
      .redirectionEndpoint()
      .baseUri("/api/callback/**");

    http
      .logout()
      // .logoutUrl("/api/logout") => /api/logout の呼び出しを POST にする必要がある
      // AntPathRequestMatcher => GETでもOK
      .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
      .logoutSuccessUrl("/")
      // ログアウト時に削除するクッキー名
      .deleteCookies("JSESSIONID")
      // ログアウト時のセッション破棄を有効化
      .invalidateHttpSession(true);

    // iOSのPWAとブラウザ間でクッキーを共有できないので、URLRewritingを使う
    // http.sessionManagement().enableSessionUrlRewriting(true);
  }

  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    DefaultHttpFirewall firewall = new DefaultHttpFirewall();
    firewall.setAllowUrlEncodedSlash(true);
    return firewall;
  }
}
