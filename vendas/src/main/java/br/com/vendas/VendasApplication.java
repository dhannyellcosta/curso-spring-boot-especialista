package br.com.vendas;

import br.com.vendas.domain.entity.Cliente;
import br.com.vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ClienteRepository clienteRepository) {
		return args -> {
			for (int i = 1; i <= 10; i++) {
				Cliente cliente = new Cliente();
				cliente.setNome("Cliente " + i);
				clienteRepository.save(cliente);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
}
