package com.example.util;

import com.example.dto.CategoryDTO;
import com.example.dto.CourierDTO;
import com.example.dto.OrderDTO;
import com.example.dto.ProductDTO;
import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ReceiverDTO;
import com.example.dto.RoleDTO;
import com.example.dto.ShopDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.dto.StatusDTO;
import com.example.dto.UserDTO;

import model.Category;
import model.Courier;
import model.Order;
import model.Product;
import model.Receiver;
import model.Role;
import model.Shop;
import model.Shoppingcart;
import model.Shoppingcartproduct;
import model.Status;
import model.User;

public class DtoToEntity {

	public Role convertRole(RoleDTO roleDTO) {
		Role role = new Role(roleDTO.getName());
		role.setId(roleDTO.getId());
		return role;
	}

	public User convertUser(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstname(), userDTO.getLastname(),
				convertRole(userDTO.getRole()));
		user.setApproved(userDTO.isApproved());
		user.setId(userDTO.getId());
		return user;
	}

	public Shop convertShop(ShopDTO shopDTO) {
		Shop shop = new Shop(shopDTO.getName(), shopDTO.getAddress(), shopDTO.getContact(),
				convertUser(shopDTO.getUser()));

		shop.getUser().setId(shopDTO.getUser().getId());

		return shop;
	}

	public Category convertCategory(CategoryDTO categoryDTO) {
		Category category = new Category(categoryDTO.getName());

		return category;
	}

	public Product convertProduct(ProductDTO productDTO) {
		Product product = new Product(productDTO.getName(), productDTO.getDescription(),
				convertCategory(productDTO.getCategory()), convertShop(productDTO.getShop()), productDTO.getPrice(),
				productDTO.getQuantity());

		product.setId(productDTO.getId());
		product.getCategory().setId(productDTO.getCategory().getId());
		product.getShop().setId(productDTO.getShop().getId());
		return product;
	}

	public Shoppingcart convertShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		Shoppingcart globalShop = new Shoppingcart(shoppingCartDTO.getIsValid(),
				convertUser(shoppingCartDTO.getUser()));

		globalShop.setId(shoppingCartDTO.getId());
		globalShop.setUser(convertUser(shoppingCartDTO.getUser()));
		return globalShop;
	}

	public Shoppingcartproduct convertShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO) {
		Shoppingcartproduct globalShop = new Shoppingcartproduct(convertProduct(shoppingCartProductDTO.getProduct()),
				shoppingCartProductDTO.getProductQuantity(),
				convertShoppingCart(shoppingCartProductDTO.getShoppingCartDTO()));

		globalShop.setId(shoppingCartProductDTO.getId());
		return globalShop;
	}

	public Shoppingcart convertShoppingCartWithProduct(ProductShoppingCartDTO productDTO,
			ShoppingCartDTO shoppingCartDTO) {
		Product product = new Product(productDTO.getName(), productDTO.getDescription(),
				convertCategory(productDTO.getCategory()), convertShop(productDTO.getShop()), productDTO.getPrice(),
				productDTO.getQuantity());

		product.setId(productDTO.getId());

		Shoppingcart globalShop = new Shoppingcart(shoppingCartDTO.getIsValid(),
				convertUser(shoppingCartDTO.getUser()));

		globalShop.setId(shoppingCartDTO.getId());
		globalShop.setUser(convertUser(shoppingCartDTO.getUser()));
		return globalShop;
	}

	public Receiver convertReceiver(ReceiverDTO receiverDTO) {
		Receiver receiver = new Receiver(receiverDTO.getAddress(), receiverDTO.getFirstname(),
				receiverDTO.getLastname());
		receiver.setId(receiverDTO.getId());
		return receiver;
	}

	public Status convertStatus(StatusDTO statusDTO) {
		Status status = new Status(statusDTO.getName());
		status.setId(statusDTO.getId());
		return status;
	}

	public Order convertOrder(OrderDTO orderDTO) {
		Order order = new Order(convertReceiver(orderDTO.getReceiver()),
				convertShoppingCart(orderDTO.getShoppingcart()), convertStatus(orderDTO.getStatus()),
				convertUser(orderDTO.getUser()));
		
		if(orderDTO.getCourier()!=null)
			order.setCourier(convertCourier(orderDTO.getCourier()));
		order.setId(orderDTO.getId());
		return order;
	}
	
	public Courier convertCourier(CourierDTO courierDTO) {
		Courier courier = new Courier(convertUser(courierDTO.getUser()));
		courier.setId(courierDTO.getId());
		return courier;
	}
}
