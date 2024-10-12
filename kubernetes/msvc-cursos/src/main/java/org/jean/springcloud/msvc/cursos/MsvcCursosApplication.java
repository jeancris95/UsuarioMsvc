package org.jean.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EntityScan({"org.jean.springcloud.msvc.cursos.entity", "com.jean.springcloud.msvc.commons.entity"})
public class MsvcCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCursosApplication.class, args);
	}

}
