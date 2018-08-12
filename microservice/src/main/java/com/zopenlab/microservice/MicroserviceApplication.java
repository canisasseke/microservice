package com.zopenlab.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zopenlab.microservice.dao.IProductDAO;
import com.zopenlab.microservice.model.Product;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MicroserviceApplication  implements CommandLineRunner{
	
	@Autowired
	IProductDAO productDAO;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		productDAO.save(new Product("Television", "Plasma", 8000, 5500));
		productDAO.save(new Product("Refrigerateur", "LG", 12000, 9500));
		productDAO.save(new Product("Ordinateur", "HP", 4500, 3200));
		productDAO.save(new Product("Modem", "xxx", 300, 250));
		
		productDAO.findAll().forEach(p ->{
			System.out.println(p);
		});
		
	}
}
