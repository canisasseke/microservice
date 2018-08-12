package com.zopenlab.microservice.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.zopenlab.microservice.dao.IProductDAO;
import com.zopenlab.microservice.model.Product;
import com.zopenlab.microservice.rest.ProductRestController;



@RunWith(SpringRunner.class)
@WebMvcTest(value=ProductRestController.class,secure=false)
public class ProductRestControllerTest {

	@Autowired
	private MockMvc  mockMvc;
	
	@MockBean
	private IProductDAO productDAO;
	
	Product produit=new Product(1L,"PC-PORTABLE", "HP-PROBOOK", 8000, 5000);
	Optional<Product> optprod= Optional.of(produit);
	String exampleProduct="{\"nom\":\"PC-PORTABLE\",\"description\":\"HP-PROBOOK\",\"prix\":\"8000\",\"prixAchat\":\"5000\"}";
	String exampleProduct1="{}";
	
	@Test
	public void getProductById() throws Exception {
		
		when(productDAO.findById(Mockito.anyLong())).thenReturn(optprod);
		
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/produits/1")
															.accept(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expectedstr="{nom:PC-PORTABLE,description:HP-PROBOOK,prix:8000}";
		JSONAssert.assertEquals(expectedstr, result.getResponse().getContentAsString(), false);	
		verify(productDAO).findById(Mockito.anyLong());
		
	}
	@Test
	public void getAllProduct() throws Exception{
		List<Product> produits=new ArrayList<>();
		produits.add(produit);
		when(productDAO.findAll()).thenReturn(produits);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/produits")
				.accept(MediaType.APPLICATION_JSON);
		//mockMvc.perform(requestBuilder).
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		
		verify(productDAO).findAll();
		
	}
	@Test
	public void saveproduct() throws Exception{
		
		when(productDAO.save(Mockito.any(Product.class))).thenReturn(produit);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/produits")
															.accept(MediaType.APPLICATION_JSON)
															.contentType(MediaType.APPLICATION_JSON)
															.content(exampleProduct);
															
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response=result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/produits/1", response.getHeader(HttpHeaders.LOCATION));	
		
	}

}
