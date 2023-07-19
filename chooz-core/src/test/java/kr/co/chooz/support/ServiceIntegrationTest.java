package kr.co.chooz.support;

import kr.co.chooz.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource("classpath:application-core-test.yml")
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
@SpringBootTest
public @interface ServiceIntegrationTest {
}
