package ev.jpapractice.service;

import ev.jpapractice.domain.OrderStatus;
import ev.jpapractice.domain.item.*;
import ev.jpapractice.exception.NotEnoughStockException;
import ev.jpapractice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        final Member member = createMember();

        final Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        final Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        final Member member = createMember();
        final Item item = createBook("JPA", 10000, 10);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    void 주문취소() throws Exception {
        final Member member = createMember();
        final Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        final Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        final Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, item.getStockQuantity());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        final Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        final Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}
