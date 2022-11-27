package ev.jpapractice.service;

import ev.jpapractice.domain.OrderItem;
import ev.jpapractice.domain.item.Delivery;
import ev.jpapractice.domain.item.Item;
import ev.jpapractice.domain.item.Member;
import ev.jpapractice.domain.item.Order;
import ev.jpapractice.repository.ItemRepository;
import ev.jpapractice.repository.MemberRepository;
import ev.jpapractice.repository.OrderRepository;
import ev.jpapractice.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        final Member member = memberRepository.findOne(memberId);
        final Item item = itemRepository.findOne(itemId);

        final Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        final OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        final Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();

    }

    @Transactional
    public void cancelOrder(Long orderId) {
        final Order order = orderRepository.findOne(orderId);

        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
