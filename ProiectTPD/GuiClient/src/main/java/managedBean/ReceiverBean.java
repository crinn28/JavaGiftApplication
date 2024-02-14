package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.example.dao.OrderDAORemote;
import com.example.dao.ProductDAORemote;
import com.example.dao.ReceiverDAORemote;
import com.example.dao.ShoppingCartDAORemote;
import com.example.dao.ShoppingCartProductDAORemote;
import com.example.dto.OrderDTO;
import com.example.dto.ProductDTO;
import com.example.dto.ReceiverDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.dto.StatusDTO;
import com.example.dto.UserDTO;

@Named(value = "receiverBean")
@SessionScoped
public class ReceiverBean implements Serializable {

	private static final long serialVersionUID = 1L;

	ReceiverDTO receiverDTO = new ReceiverDTO();
	ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
	UserDTO userDTO = new UserDTO();
	List<ShoppingCartProductDTO> shoppingCartProducts = new ArrayList<>();
	private int numberOfProducts;
	private double shoppingCartTotal;

	@EJB
	ReceiverDAORemote receiverDAORemote;

	@EJB
	OrderDAORemote orderDAORemote;

	@EJB
	ShoppingCartDAORemote shoppingCartDAORemote;

	@EJB
	ShoppingCartProductDAORemote shoppingCartProductDAORemote;

	@EJB
	ProductDAORemote productDAORemote;

	public void init() {
		numberOfProducts = 0;
		shoppingCartTotal = 0;
		shoppingCartProducts = new ArrayList<>();
		shoppingCartProducts = shoppingCartProductDAORemote.findShoppingCartProductsByShoppingCartId(shoppingCartDTO);
		for (ShoppingCartProductDTO shoppingCartProductDTO : shoppingCartProducts) {
			shoppingCartProductDTO.setTotalItemPrice(
					shoppingCartProductDTO.getProduct().getPrice() * shoppingCartProductDTO.getProductQuantity());
			numberOfProducts += shoppingCartProductDTO.getProductQuantity();
			shoppingCartTotal += shoppingCartProductDTO.getProduct().getPrice()
					* shoppingCartProductDTO.getProductQuantity();
		}
	}

	public ReceiverDTO getReceiverDTO() {
		return receiverDTO;
	}

	public void setReceiverDTO(ReceiverDTO receiverDTO) {
		this.receiverDTO = receiverDTO;
	}

	public ShoppingCartDTO getShoppingCartDTO() {
		return shoppingCartDTO;
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

	public List<ShoppingCartProductDTO> getShoppingCartProducts() {
		return shoppingCartProducts;
	}

	public void setShoppingCartProducts(List<ShoppingCartProductDTO> shoppingCartProducts) {
		this.shoppingCartProducts = shoppingCartProducts;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
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

	public String createReceiver() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			receiverDTO = receiverDAORemote.create(receiverDTO);

			shoppingCartDTO = (ShoppingCartDTO) facesContext.getExternalContext().getSessionMap()
					.get("shoppingCartDTO");

			init();

			userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
			OrderDTO orderDTO = new OrderDTO(receiverDTO, shoppingCartDTO, new StatusDTO(), userDTO);
			orderDTO.setShoppingcart(shoppingCartDTO);

			orderDTO = orderDAORemote.create(orderDTO);

			shoppingCartDTO.setIsValid(false);
			shoppingCartDTO = shoppingCartDAORemote.update(shoppingCartDTO);

			for (ShoppingCartProductDTO shoppingCartProduct : shoppingCartProducts) {
				ProductDTO productDTO = shoppingCartProduct.getProduct();
				productDTO.setQuantity(productDTO.getQuantity() - shoppingCartProduct.getProductQuantity());
				productDTO = productDAORemote.update(productDTO);
			}

			return "/customer/thankYouPage.xhtml?faces-redirect=true";

		} catch (PersistenceException e) {
			facesContext.addMessage("createProductForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return null;
		}
	}

	public String redirectToMainPage() {
		shoppingCartProducts.clear();
		receiverDTO = new ReceiverDTO();
		return "/customer/customer.xhtml?faces-redirect=true";
	}
}
