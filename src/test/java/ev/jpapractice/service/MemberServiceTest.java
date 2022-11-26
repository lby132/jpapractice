package ev.jpapractice.service;

import ev.jpapractice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ev.jpapractice.domain.item.Member;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        final Member member = new Member();
        member.setName("lby");

        final Long savedId = memberService.join(member);

        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        final Member member1 = new Member();
        member1.setName("lby");

        final Member member2 = new Member();
        member2.setName("lby");

        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }).equals("이미 존재하는 회원입니다.");
    }
}