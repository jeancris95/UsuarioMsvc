package org.jean.springcloud.msvc.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan(basePackages = {
		"org.jean.springcloud.msvc.usuarios.models.entity", // Paquete de Usuario
		"com.jean.springcloud.msvc.commons.entity" // Paquete de CursoUsuario
})
@EnableFeignClients
public class MsvcUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsuariosApplication.class, args);
	}

}
