package com.invillia.acme;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.invillia.acme.domain.Address;
import com.invillia.acme.domain.Store;
import com.invillia.acme.repository.AddressRepository;
import com.invillia.acme.repository.StoreRepository;

@SpringBootApplication
public class InvilliaApplication implements CommandLineRunner {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(InvilliaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Address a1 = new Address("Rua São Jorge","Bahia","Paulo Afonso", "Centro", "Brazil","1A");
		Address a2 = new Address("Rua São Mateus","Pernambuco","Jatobá", "Itaparica", "Brazil","258");
		Address a3 = new Address("Rua São Paulo","Sergipe","Petrolândia", "Agrovila", "Brazil","368C");
		Address a4 = new Address("Rua São Pedro","Ceará","Fortaleza", "Prainha", "Brazil","S/N");
		
		addressRepository.saveAll(Arrays.asList(a1,a2,a3,a4));
		
		Store s1 = new Store("Info", a2);
		Store s2 = new Store("Microsoft", a4);
		Store s3 = new Store("Google", a1);
		Store s4 = new Store("Apple", a3);
		
		storeRepository.saveAll(Arrays.asList(s1, s2, s3, s4));
		
	}
}
