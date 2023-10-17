package com.project.concertView;

import com.project.concertView.web.repository.LikeConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class ConcertViewApplicationTests {
	private final LikeConcertRepository repository;
	@Test
	@DisplayName("repository 관련 테스트")
	void contextLoads() {
		log.info("repository={}",repository.likeConcertList(3L).size());
	}

}
