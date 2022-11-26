package ev.jpapractice.service;

import ev.jpapractice.domain.OrderItem;
import ev.jpapractice.domain.item.Delivery;
import ev.jpapractice.domain.item.Item;
import ev.jpapractice.domain.item.Member;
import ev.jpapractice.domain.item.Order;
import ev.jpapractice.repository.ItemRepository;
import ev.jpapractice.repository.MemberRepository;
import ev.jpapractice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

//    @Transactional
//    public Long order(Long memberId, Long itemId, int count) {
//
//        final Member member = memberRepository.findOne(memberId);
//        final Item item = itemRepository.findOne(itemId);
//
//        final Delivery delivery = new Delivery();
//        delivery.setAddress(member.getAddress());
//
//        final OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
//
//        Order.
//
//    }
}
