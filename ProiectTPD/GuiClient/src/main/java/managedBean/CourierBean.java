package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.example.dao.CourierDAORemote;
import com.example.dao.OrderDAORemote;
import com.example.dao.ShoppingCartProductDAORemote;
import com.example.dao.StatusDAORemote;
import com.example.dto.CourierDTO;
import com.example.dto.OrderDTO;
import com.example.dto.ShoppingCartProductDTO;
import com.example.dto.StatusDTO;
import com.example.dto.UserDTO;

@Named(value = "courierBean")
@SessionScoped
public class CourierBean implements Serializable {

	private static final long serialVersionUID = 1L;

	List<OrderDTO> orderList = new ArrayList<>();
	UserDTO userDTO = new UserDTO();

	OrderDTO orderDTO;
	CourierDTO courierDTO;
	private int numberOfProducts;
	private double shoppingCartTotal;

	@EJB
	OrderDAORemote orderDAORemote;
	
	@EJB
	StatusDAORemote statusDAORemote;
	
	@EJB
	CourierDAORemote courierDAORemote;

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

	public CourierDTO getCourierDTO() {
		return courierDTO;
	}

	public void setCourierDTO(CourierDTO courierDTO) {
		this.courierDTO = courierDTO;
	}

	@PostConstruct
	public void initializeOrders() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");
		courierDTO = courierDAORemote.findCourierByUserId(userDTO.getId());
		
		orderList = orderDAORemote.getNewOrders();
		for (OrderDTO order : orderList) {
			order.setShoppingCartProducts(
					shoppingCartProductDAORemote.findShoppingCartProductsByShoppingCartId(order.getShoppingcart()));
		}
		Collections.reverse(orderList);
	}

	public String redirectToMyOrdersPage() {
		orderList = orderDAORemote.getMyOrders(courierDTO);
		for (OrderDTO order : orderList) {
			order.setShoppingCartProducts(
					shoppingCartProductDAORemote.findShoppingCartProductsByShoppingCartId(order.getShoppingcart()));
		}
		Collections.reverse(orderList);
		return "/courier/myOrders.xhtml?faces-redirect=true";
	}
	
	
	public String redirectToOrdersPage() {
		initializeOrders();
		return "/courier/courier.xhtml?faces-redirect=true";
	}
	
	public String changeOrderStatus(OrderDTO order, int id) {
		StatusDTO statusDTO = statusDAORemote.findById(id);
		
		courierDTO = courierDAORemote.findCourierByUserId(userDTO.getId());
		
		order.setCourier(courierDTO);
		order.setStatus(statusDTO);
		
		order = orderDAORemote.update(order);
		
		initializeOrders();
		
		if(id == 2) {
			initializeOrders();
			return "/courier/courier.xhtml?faces-redirect=true";
		}
		
		return redirectToMyOrdersPage();
	}
	
	public String viewOrder(OrderDTO order) {
		orderDTO = order;
		numberOfProducts = 0;
		shoppingCartTotal = 0;
		for (ShoppingCartProductDTO shoppingCartProductDTO : order.getShoppingCartProducts()) {
			shoppingCartProductDTO.setTotalItemPrice(
					shoppingCartProductDTO.getProduct().getPrice() * shoppingCartProductDTO.getProductQuantity());
			numberOfProducts += shoppingCartProductDTO.getProductQuantity();
			shoppingCartTotal += shoppingCartProductDTO.getProduct().getPrice()
					* shoppingCartProductDTO.getProductQuantity();
		}
		return "/courier/viewOrder.xhtml?faces-redirect=true";
	}

}
