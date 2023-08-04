package com.project.concertView;

import com.project.concertView.domain.ConcertConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConcertConfig.class)	//Bean 으로 등록한 ConcertConfig 클래스 등록
public class ConcertViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcertViewApplication.class, args);
	}

}
