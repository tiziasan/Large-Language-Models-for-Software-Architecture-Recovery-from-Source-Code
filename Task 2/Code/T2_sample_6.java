package com.example.shopping.config;
@Configuration
public class AppConfig {
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	public static String getFormatted(int price) {
		float val = new Float(price);
		String formatted = String.format(java.util.Locale.UK, "£%.2f", val/100f);
		return formatted;
	}
}package com.example.shopping.data.repo;



public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}package com.example.shopping.data.repo;



public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, Long> {

}package com.example.shopping.data.repo;



@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}package com.example.shopping.data.model;



@Entity
@Table(name="ordeer")
public class Order {

	@Id @GeneratedValue
	private long id;
	
	@Transient
	private Map<Product, Integer> productQuantities;

	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy="order")
	private List<OrderPromotion> orderPromotions;
	
	private int totalNetPayable;
	private Date checkoutTimestamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Map<Product, Integer> getProductQuantities() {
		if (productQuantities == null) {
			productQuantities = new LinkedHashMap<>();
		}
		return productQuantities;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderPromotion> getOrderPromotions() {
		return orderPromotions;
	}

	public void setOrderPromotions(List<OrderPromotion> orderPromotions) {
		this.orderPromotions = orderPromotions;
	}

	public int getTotalNetPayable() {
		return totalNetPayable;
	}

	public void setTotalNetPayable(int totalNetPayable) {
		this.totalNetPayable = totalNetPayable;
	}

	public Date getCheckoutTimestamp() {
		return checkoutTimestamp;
	}

	public void setCheckoutTimestamp(Date checkoutTimestamp) {
		this.checkoutTimestamp = checkoutTimestamp;
	}

	public String getTotalPreDiscount() {
		int total = 0;
		if (orderItems != null)
		for (OrderItem item : orderItems) {
			total += item.getTotalPrice();
		}
		return AppConfig.getFormatted(total);
	}
	
	public String getTotalDiscount() {
		int total = 0;
		if (orderPromotions != null)
		for (OrderPromotion promotion : orderPromotions) {
			total += promotion.getPromotionValue();
		}
		return AppConfig.getFormatted(total);
	}
	
}package com.example.shopping.data.model;



@Entity
public class OrderItem {
	@Id @GeneratedValue
	private long id;
	private String productId;
	private String productName;
	private int productPrice;

	private int quantity;
	
	@ManyToOne
	private Order order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Transient
	public int getTotalPrice() {
		return this.quantity * this.getProductPrice();
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}package com.example.shopping.data.model;



@Entity
public class OrderPromotion {

	@Id @GeneratedValue
	private long id;
	
	private String promotionId;
	private String promotionType;
	private int promotionValue;
	
	@ManyToOne
	private Order order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotion_id) {
		this.promotionId = promotion_id;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public int getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(int promotionValue) {
		this.promotionValue = promotionValue;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}package com.example.shopping.data.json;

public class Product {
	private String id;
	private String name;
	private int price;
	
	private Promotion[] promotions;
	
	public Product() {
		this(null, null, 0);
	}
	
	public Product(String id, String name, int price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public Promotion[] getPromotions() {
		return promotions;
	}

	public void setPromotions(Promotion[] promotions) {
		this.promotions = promotions;
	}
}package com.example.shopping.data.json;


public class Promotion {
	private String id;
	private PromotionType type;
	
	@JsonProperty("required_qty")
	private int requiredQty;
	
	private int price;	
	@JsonProperty("free_qty")
	private int freeQty;
	
	private int amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
	}

	public int getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(int requiredQty) {
		this.requiredQty = requiredQty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(int freeQty) {
		this.freeQty = freeQty;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}package com.example.shopping.data.json;



public enum PromotionType {
	BUY_X_GET_Y_FREE {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			int required = promo.getRequiredQty();
			int freeItems = promo.getFreeQty();
			if (quantity < required) {
				return 0;
			} else if (quantity > required) {
				return product.getPrice() * (quantity/required) * freeItems;
			} else {
				return product.getPrice() * freeItems;
			}
		}
	},
	
	QTY_BASED_PRICE_OVERRIDE {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			int requied = promo.getRequiredQty();
			if (quantity < requied) {
				return 0; 
			} else if (quantity > requied) {
				int noOfCoupens = quantity/requied;
				int totalCostForEligibleQuantity = product.getPrice() * requied * noOfCoupens;
				int offerCost = promo.getPrice() * noOfCoupens;
				return totalCostForEligibleQuantity - offerCost ;
			} else {
				return (product.getPrice() * requied) - promo.getPrice();
			}
		}
	},
	
	FLAT_PERCENT {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			BigDecimal dec = new BigDecimal(String.valueOf(promo.getAmount()));
			BigDecimal percent = dec.divide(new BigDecimal("100"));
			int total = product.getPrice() * quantity;
			return percent.multiply(new BigDecimal(total)).intValue();
		}
	};

	public OrderPromotion getOrderPromotion(Order order, Product product, Promotion promo) {
		if (promo == null) {
			return null;
		}
		int quantity = order.getProductQuantities().get(product);
		int discountedPrice = calculatePriceDiscount(product, quantity, promo);
		if (discountedPrice < 1) {
			return null;
		}
		return populateOrderPromotion(order, promo, discountedPrice);
	}

	protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
		return 0;
	}

	private OrderPromotion populateOrderPromotion(Order order, Promotion promo, int discountedPrice) {
		OrderPromotion op = new OrderPromotion();
		op.setOrder(order);
		op.setPromotionId(promo.getId());
		op.setPromotionType(promo.getType().name());
		op.setPromotionValue(discountedPrice);
		return op;
	}
}package com.example.shopping.service;


@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItem save(OrderItem item) {
		return orderItemRepository.save(item);
	}
}package com.example.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.data.model.OrderPromotion;
import com.example.shopping.data.repo.OrderPromotionRepository;

@Service
public class OrderPromotionService {
	
	@Autowired
	private OrderPromotionRepository orderPromotionRepository;
	
	public OrderPromotion save(OrderPromotion item) {
		return orderPromotionRepository.save(item);
	}
}package com.example.shopping.service;



@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository itemRepository;
	@Autowired
	private OrderPromotionRepository promotionRepository;
	
	@Transactional
	public Order save(Order order) {
		Order o = orderRepository.save(order);
		if (order.getOrderPromotions() != null && order.getOrderPromotions().size() > 0) {
			promotionRepository.saveAll(order.getOrderPromotions());
		}
		if (order.getOrderItems() != null && order.getOrderItems().size() > 0) {
			itemRepository.saveAll(order.getOrderItems());
		}
		return o;
	}
	
	@Transactional
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
	
	@Transactional
	public Order getOrder(long id) {
		return orderRepository.findById(id).get();
	}

	@Transactional
	public Order checkout(Order order) {
		List<OrderItem> orderItems = new ArrayList<>();
		List<OrderPromotion> orderPromotions = new ArrayList<>();
		int total = 0, promoDiscount = 0;
		
		for (Product	 product : order.getProductQuantities().keySet()) {
			OrderItem item = getOrderItem(order, product);
			total += item.getTotalPrice();
			
			orderItems.add(item);
			if (product.getPromotions() != null) { 
				for (Promotion promotion: product.getPromotions()) {
					OrderPromotion promo = promotion.getType().getOrderPromotion(order, product, promotion);
					if (promo == null) {
						continue;
					} else {
						orderPromotions.add(promo);
						promoDiscount += promo.getPromotionValue();
					}
				}
			}
//			promoDiscount += getPromotionalDiscount(order, orderPromotions, promoDiscount, product);
		}
		order.setOrderItems(orderItems);
		order.setOrderPromotions(orderPromotions);
		order.setTotalNetPayable(total - promoDiscount);
		order.setCheckoutTimestamp(new Date());
		save(order);
		return order;
	}

	private OrderItem getOrderItem(Order order, Product product) {
		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setProductId(product.getId());
		item.setProductName(product.getName());
		item.setProductPrice(product.getPrice());
		item.setQuantity(order.getProductQuantities().get(product));
		return item;
	}
}package com.example.shopping.service;



@Service
public class ProductService {
	
	@Value("${app.products.url}")
	private String productsURL;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Product> getProducts() {
		Product[] ps = restTemplate.getForEntity(productsURL, Product[].class).getBody();
		return Arrays.asList(ps);
	}

	public Product getProduct(String id) {
		ResponseEntity<Product> resp =  restTemplate.getForEntity(productsURL+"/"+id, Product.class);
		return resp.getBody();
	}

}
package com.example.shopping.web;


@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	@GetMapping("/orders")
	public String orders(Model model) {
		List<Order> orders = orderService.getOrders();
		model.addAttribute("orders", orders);
		return "orders/list";
	}

	@GetMapping("/orders/create-form")
	public String createOrder(Model model) {
		OrderDTO dto = getNewOrderDto();
		model.addAttribute("orderDto", dto);
		return "orders/create";
	}

	@GetMapping("/orders/{id}")
	public String viewOrder(@PathVariable Long id, Model model) {
		Order order = orderService.getOrder(id);
		model.addAttribute("order", order);
		return "orders/show";
	}

	@PostMapping("/orders")
	public String checkout(@ModelAttribute OrderDTO dto, Model model) {
		Order order = populateOrderFromDto(dto);

		Order checkoutOrder = orderService.checkout(order);
		model.addAttribute("order", checkoutOrder);
		return "orders/show";
	}
	
	private OrderDTO getNewOrderDto() {
		OrderDTO dto = new OrderDTO();
		List<Product> products = productService.getProducts();
		List<CheckoutItem> items = new ArrayList<>();
		for (Product product : products) {
			CheckoutItem item = new CheckoutItem();
			item.setProduct(product);
			item.setQuantity(0);
			items.add(item);
		}
		dto.setItems(items);
		return dto;
	}

	private Order populateOrderFromDto(OrderDTO dto) {
		Order order = new Order();
		for (int i = 0; i < dto.getItems().size(); i++) {
			int noOfItems = dto.getItems().get(i).getQuantity();
			if (noOfItems > 0) {
				Product product = dto.getItems().get(i).getProduct();
				product = productService.getProduct(product.getId());
				order.getProductQuantities().put(product, noOfItems);
			} else {
				continue;
			}
		}
		return order;
	}

}package com.example.shopping.web.dto;

import com.example.shopping.data.json.Product;

public class CheckoutItem {
	private Product product;
	private int quantity;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}package com.example.shopping.web.dto;

import java.util.List;

public class OrderDTO {
	private List<CheckoutItem> items;

	public List<CheckoutItem> getItems() {
		return items;
	}

	public void setItems(List<CheckoutItem> items) {
		this.items = items;
	}
}package com.example.shopping.web.dto;



@Component
public class ProductToStringConverter implements Converter<Product, String> {

	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String convert(Product source) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(source);
			return json;
		} catch (JsonProcessingException e) {
			return "{\"id\":\""+source.getId()+ "\",\"price\":\"" +source.getPrice()+"\"}";
		}
	}

}package com.example.shopping.web.dto;



@Component
public class StringToProductConverter implements Converter<String,Product> {

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Product convert(String source) {
		try {
			return mapper.readValue(source, Product.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}package com.example.shopping;


@SpringBootApplication
public class ShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}
}