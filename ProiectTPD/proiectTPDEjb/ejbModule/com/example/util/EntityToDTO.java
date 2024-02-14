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

public class EntityToDTO {

	public RoleDTO convertRole(Role role) {
		RoleDTO globalRoleDTO = new RoleDTO(role.getName());

		globalRoleDTO.setId(role.getId());
		return globalRoleDTO;
	}

	public UserDTO convertUser(User user) {
		UserDTO globalUserDTO = new UserDTO(user.getEmail(), user.getPassword(), user.getFirstname(),
				user.getLastname(), convertRole(user.getRole()));

		globalUserDTO.setId(user.getId());
		globalUserDTO.setApproved(user.getApproved());
		return globalUserDTO;

	}

	public ShopDTO convertShop(Shop shop) {
		ShopDTO globalShopDTO = new ShopDTO(shop.getName(), shop.getAddress(), shop.getContact(),
				convertUser(shop.getUser()));

		globalShopDTO.setId(shop.getId());
		return globalShopDTO;
	}

	public ShoppingCartDTO convertShoppingCart(Shoppingcart shoppingCart) {
		ShoppingCartDTO globalShopDTO = new ShoppingCartDTO(shoppingCart.getIsValid(),
				convertUser(shoppingCart.getUser()));

		globalShopDTO.setId(shoppingCart.getId());
		return globalShopDTO;
	}

	public ShoppingCartProductDTO convertShoppingCartProduct(Shoppingcartproduct shoppingCartProduct) {
		ShoppingCartProductDTO globalShopDTO = new ShoppingCartProductDTO(
				convertProduct(shoppingCartProduct.getProduct()), shoppingCartProduct.getProductQuantity(),
				convertShoppingCart(shoppingCartProduct.getShoppingcart()));

		globalShopDTO.setId(shoppingCartProduct.getId());
		return globalShopDTO;
	}

	public CategoryDTO convertCategory(Category category) {
		CategoryDTO globalCategoryDTO = new CategoryDTO(category.getName());

		globalCategoryDTO.setId(category.getId());
		return globalCategoryDTO;
	}

	public ProductDTO convertProduct(Product product) {
		ProductDTO globalProductDTO = new ProductDTO(product.getName(), product.getDescription(),
				convertCategory(product.getCategory()), convertShop(product.getShop()), product.getPrice(),
				product.getQuantity());

		globalProductDTO.setId(product.getId());
		return globalProductDTO;
	}

	public ProductShoppingCartDTO convertProductToShoppingCart(Product product) {
		ProductShoppingCartDTO globalProductDTO = new ProductShoppingCartDTO(product.getName(),
				product.getDescription(), convertCategory(product.getCategory()), convertShop(product.getShop()),
				product.getPrice(), product.getQuantity());

		globalProductDTO.setId(product.getId());
		return globalProductDTO;
	}

	public ProductDTO convertProductShoppingCartToProduct(ProductShoppingCartDTO productShoppingCartDTO) {
		ProductDTO globalProductDTO = new ProductDTO(productShoppingCartDTO.getName(),
				productShoppingCartDTO.getDescription(), productShoppingCartDTO.getCategory(),
				productShoppingCartDTO.getShop(), productShoppingCartDTO.getPrice(),
				productShoppingCartDTO.getQuantity());

		globalProductDTO.setId(productShoppingCartDTO.getId());
		return globalProductDTO;
	}

	public ReceiverDTO convertReceiver(Receiver receiver) {
		ReceiverDTO receiverDTO = new ReceiverDTO(receiver.getAddress(), receiver.getFirstname(),
				receiver.getLastname());
		receiverDTO.setId(receiver.getId());
		return receiverDTO;
	}

	public StatusDTO convertStatus(Status status) {
		StatusDTO statusDTO = new StatusDTO(status.getName());
		statusDTO.setId(status.getId());
		return statusDTO;
	}

	public OrderDTO convertOrder(Order order) {
		OrderDTO orderDTO = new OrderDTO(convertReceiver(order.getReceiver()),
				convertShoppingCart(order.getShoppingcart()), convertStatus(order.getStatus()),
				convertUser(order.getUser()));
		
		if(order.getCourier()!=null)
			orderDTO.setCourier(convertCourier(order.getCourier()));
		
		orderDTO.setId(order.getId());
		return orderDTO;
	}
	
	public CourierDTO convertCourier(Courier courier) {
		CourierDTO globalCourierDTO = new CourierDTO(convertUser(courier.getUser()));

		globalCourierDTO.setId(courier.getId());
		return globalCourierDTO;

	}

}
