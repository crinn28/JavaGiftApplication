package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.example.dao.ShoppingCartDAORemote;
import com.example.dao.ShoppingCartProductDAORemote;
import com.example.dto.ProductDTO;
import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.dto.UserDTO;

@Named(value = "shoppingCartBean")
@SessionScoped
public class ShoppingCartBean implements Serializable {

	private static final long serialVersionUID = 1L;

	ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
	List<ShoppingCartDTO> shoppingCart = new ArrayList<>();
	List<ShoppingCartProductDTO> shoppingCartProducts = new ArrayList<>();
	private int numberOfProducts;
	private double shoppingCartTotal;

	@EJB
	ShoppingCartDAORemote shoppingCartDAORemote;

	@EJB
	ShoppingCartProductDAORemote shoppingCartProductDAORemote;

	UserDTO userDTO;

	public ShoppingCartDTO getShoppingCartDTO() {
		return shoppingCartDTO;
	}

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UserDTO user = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
		shoppingCartDTO = shoppingCartDAORemote.findValidShoppingcart(user.getId());
		facesContext.getExternalContext().getSessionMap().put("shoppingCartDTO", shoppingCartDTO);

		shoppingCart = shoppingCartDAORemote.findAll();
		initializareShoppingCartProducts();
	}

	private void initializareShoppingCartProducts() {
		shoppingCartProducts = new ArrayList<>();
		if (shoppingCartDTO != null) {
			shoppingCartProducts = shoppingCartProductDAORemote
					.findShoppingCartProductsByShoppingCartId(shoppingCartDTO);
		}
		numberOfProducts = 0;
		shoppingCartTotal = 0;
		for (ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProducts) {
			shoppingCartProductDTO.setTotalItemPrice(
					shoppingCartProductDTO.getProduct().getPrice() * shoppingCartProductDTO.getProductQuantity());
			numberOfProducts += shoppingCartProductDTO.getProductQuantity();
			shoppingCartTotal += shoppingCartProductDTO.getProduct().getPrice()
					* shoppingCartProductDTO.getProductQuantity();
		}
	}

	public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
		this.shoppingCartDTO = shoppingCartDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<ShoppingCartDTO> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<ShoppingCartDTO> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public List<ShoppingCartProductDTO> getShoppingCartProducts() {
		return shoppingCartProducts;
	}

	public void setShoppingCartProducts(List<ShoppingCartProductDTO> shoppingCartProducts) {
		this.shoppingCartProducts = shoppingCartProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	public double getShoppingCartTotal() {
		return shoppingCartTotal;
	}

	public void setShoppingCartTotal(double shoppingCartTotal) {
		this.shoppingCartTotal = shoppingCartTotal;
	}

	public String addProductToShoppingCart(ProductShoppingCartDTO product) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");

			ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getCategory(),
					product.getShop(), product.getPrice(), product.getQuantity());
			productDTO.setId(product.getId());

			if (shoppingCartDTO == null) {
				shoppingCartDTO = new ShoppingCartDTO();
				shoppingCartDTO.setUser(userDTO);
				shoppingCartDTO = shoppingCartDAORemote.create(shoppingCartDTO);
				init();
			}

			ShoppingCartProductDTO shoppingCartProductDTO = new ShoppingCartProductDTO(productDTO,
					product.getDesiredQuantity(), shoppingCartDTO);

			Optional<ShoppingCartProductDTO> existingProduct = shoppingCartProducts.stream()
					.filter(p -> p.getProduct().getId() == product.getId()).findFirst();

			if (existingProduct.isPresent()) {
				ShoppingCartProductDTO existingCartItem = existingProduct.get();
				existingCartItem
						.setProductQuantity(existingCartItem.getProductQuantity() + product.getDesiredQuantity());
				shoppingCartProductDTO = shoppingCartProductDAORemote.update(existingCartItem);
				initializareShoppingCartProducts();
			} else {
				shoppingCartProductDTO = shoppingCartProductDAORemote.create(shoppingCartProductDTO);
				initializareShoppingCartProducts();
			}

			return "/customer/shopProducts.xhtml?faces-redirect=true&shopId=" + product.getShop().getId();

		} catch (Exception e) {
			facesContext.addMessage("createShopForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return "/customer/customer.xhtml?faces-redirect=true";
		}
	}

	public String remove(ShoppingCartProductDTO product) {
		shoppingCartProductDAORemote.delete(product.getId());
		numberOfProducts -= product.getProductQuantity();
		shoppingCartTotal -= product.getProductQuantity() * product.getProduct().getPrice();
		initializareShoppingCartProducts();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String increment(ShoppingCartProductDTO product) {
		product.setProductQuantity(product.getProductQuantity() + 1);
		product.setTotalItemPrice(product.getTotalItemPrice() + product.getProduct().getPrice());
		product = shoppingCartProductDAORemote.update(product);
		numberOfProducts += 1;
		shoppingCartTotal += product.getProduct().getPrice();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String decrement(ShoppingCartProductDTO product) {
		if (product.getProductQuantity() - 1 == 0) {
			shoppingCartProductDAORemote.delete(product.getId());
			numberOfProducts -= 1;
			shoppingCartTotal -= product.getProduct().getPrice();
			product.setTotalItemPrice(product.getTotalItemPrice() - product.getProduct().getPrice());
			initializareShoppingCartProducts();
			return "/customer/shoppingCart.xhtml?faces-redirect=true";
		}
		if (product.getProductQuantity() > 0) {
			product.setProductQuantity(product.getProductQuantity() - 1);
			numberOfProducts -= 1;
			shoppingCartTotal -= product.getProduct().getPrice();
			product.setTotalItemPrice(product.getTotalItemPrice() - product.getProduct().getPrice());
			product = shoppingCartProductDAORemote.update(product);
			return "/customer/shoppingCart.xhtml?faces-redirect=true";
		}

		return null;
	}

	public String clear() {
		for (ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProducts) {
			shoppingCartProductDAORemote.delete(shoppingCartProductDTO.getId());
		}
		numberOfProducts = 0;
		shoppingCartTotal = 0;
		initializareShoppingCartProducts();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String placeOrder() {
		boolean allProductsAreInStock = true;
		for (ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProducts) {
			if (shoppingCartProductDTO.getProductQuantity() > shoppingCartProductDTO.getProduct().getQuantity()) {
				allProductsAreInStock = false;
				shoppingCartProductDTO.setProductIsInStock(false);
			} else {
				shoppingCartProductDTO.setProductIsInStock(true);
			}
		}
		if (allProductsAreInStock) {

			shoppingCartProducts = new ArrayList<>();
			numberOfProducts = 0;
			shoppingCartTotal = 0;
			shoppingCartDTO = null;
			return "/customer/receiver.xhtml?faces-redirect=true";
		}
		return null;
	}

	public String redirectToMainPage() {
		return "/customer/customer.xhtml?faces-redirect=true";
	}
}
