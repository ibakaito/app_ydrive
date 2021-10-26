package ydrive.app_ydrive.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class Sample3AuthConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * 認証処理に関する設定（誰がログインできるか）
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("p@ss")).roles("USER");

    // $ sshrun htpasswd -nbBC 10 admin adm1n
    // htpasswdでBCryptエンコードを行った後の文字列をパスワードとして指定している．
    auth.inMemoryAuthentication().withUser("admin")
        .password("$2y$10$3e7Hs2QZ/p91yJVgP5y/1OC7AC8jfc6YDYDzMGK1XieDlNR2nBGDe").roles("ADMIN");

    // $ sshrun htpasswd -nbBC 10 customer1 Cust0m
    auth.inMemoryAuthentication().withUser("customer1")
        .password("$2y$10$8IbzoKwqlCJf.z8/7YThKuSB1nGAQSr8rtHN7pQzm4mx9nrOhsN1C").roles("CUSTOMER");
    auth.inMemoryAuthentication().withUser("customer2")
        .password("$2y$10$8IbzoKwqlCJf.z8/7YThKuSB1nGAQSr8rtHN7pQzm4mx9nrOhsN1C").roles("CUSTOMER");
    auth.inMemoryAuthentication().withUser("seller")
        .password("$2y$10$8IbzoKwqlCJf.z8/7YThKuSB1nGAQSr8rtHN7pQzm4mx9nrOhsN1C").roles("SELLER");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.formLogin();
    http.authorizeRequests().antMatchers("/sample5/**").authenticated();
    http.logout().logoutSuccessUrl("/");
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}
