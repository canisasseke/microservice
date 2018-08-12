package com.zopenlab.microservice.rest;



import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zopenlab.microservice.dao.IProductDAO;
import com.zopenlab.microservice.exception.ProduitIntrouvableException;
import com.zopenlab.microservice.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="Gestion des produits")
@RestController
public class ProductRestController {
	
	@Autowired
	IProductDAO productDAO;
	
	@ApiOperation(value="Recupere la liste de tous les produits")
	@GetMapping(value="/produits")
	public List<Product> getAllProduct(){
		
		return productDAO.findAll();
	}
	
	
	@ApiOperation(value="Recupere un produit pas son identifiant")
	@GetMapping(value="/produits/{id}")
	public Product getProductById(@PathVariable Long id) {
		
		Optional<Product> product = productDAO.findById(id);
		if(!product.isPresent()) throw new ProduitIntrouvableException("le produit avec l 'id = "+ id+" n'existe pas");
		return product.get();
	}
	@ApiOperation(value="Recupere les produits dont le nom contient les caracteres saisies: %nom%")
	@GetMapping(value="/produits/search/{nom}")
	public List<Product> getProductByKeyword(@PathVariable String nom){
		return productDAO.getByKeywordName("%"+nom+"%");
	}
	
	@PostMapping(value="/produits")
	public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product){
		
		//if(product==null) return ResponseEntity.noContent().build();
		Product product1=productDAO.save(product);		
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(product1.getId())
					.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value="/produits/{id}")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable Long id){
		Optional<Product> p = productDAO.findById(id);
		if(!p.isPresent()) return ResponseEntity.notFound().build();
		product.setId(id);
		productDAO.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/produits/{id}")
	public void deleteProduct(@PathVariable Long id){
		Optional<Product> product = productDAO.findById(id);
		if(!product.isPresent()) throw new ProduitIntrouvableException("le produit avec l 'id = "+ id+" n'existe pas");
		productDAO.deleteById(id);
	}
	
	@GetMapping(value="/test/produits/{prixlimite}")
	public List<Product> getProductsWherePriceGreaterThan(@PathVariable float prixlimite){
		
		return productDAO.findByPrixGreaterThan(prixlimite);
	}

}
