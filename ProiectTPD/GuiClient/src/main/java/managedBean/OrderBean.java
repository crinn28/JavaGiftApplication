package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.example.dao.OrderDAORemote;
import com.example.dao.ShoppingCartProductDAORemote;
import com.example.dto.OrderDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.dto.UserDTO;

@Named(value = "orderBean")
@RequestScoped
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	List<OrderDTO> orderList = new ArrayList<>();
	UserDTO userDTO = new UserDTO();

	OrderDTO orderDTO;
	private int numberOfProducts;
	private double shoppingCartTotal;

	@EJB
	OrderDAORemote orderDAORemote;

	@EJB
	ShoppingCartProductDAORemote shoppingCartProductDAORemote;

	public List<OrderDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderDTO> orderList) {
		this.orderList = orderList;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
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

	@PostConstruct
	public void initializeOrders() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
		orderDTO = (OrderDTO) facesContext.getExternalContext().getSessionMap().get("orderDTO");

		orderList = orderDAORemote.getOrdersByUserId(userDTO.getId());

		for (OrderDTO order : orderList) {
			order.setShoppingCartProducts(
					shoppingCartProductDAORemote.findShoppingCartProductsByShoppingCartId(order.getShoppingcart()));
		}
		if (orderDTO != null) {
			numberOfProducts = 0;
			shoppingCartTotal = 0;
			for (ShoppingCartProductDTO shoppingCartProductDTO : orderDTO.getShoppingCartProducts()) {
				shoppingCartProductDTO.setTotalItemPrice(
						shoppingCartProductDTO.getProduct().getPrice() * shoppingCartProductDTO.getProductQuantity());
				numberOfProducts += shoppingCartProductDTO.getProductQuantity();
				shoppingCartTotal += shoppingCartProductDTO.getProduct().getPrice()
						* shoppingCartProductDTO.getProductQuantity();
			}
		}
		Collections.reverse(orderList);
	}

	public String redirectToMainPage() {
		return "/customer/customer.xhtml?faces-redirect=true";
	}

	public String redirectToOrdersPage() {
		return "/customer/order.xhtml?faces-redirect=true";
	}

	public String viewOrder(OrderDTO order) {
		orderDTO = order;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getSessionMap().put("orderDTO", orderDTO);

		return "/customer/viewOrder.xhtml?faces-redirect=true";
	}

}
