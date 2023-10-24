package com.example.restfulwebservice;

import com.example.restfulwebservice.domain.Usertable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Usertable user1 =new Usertable("user1",new Date(),"test111","9605");
            Usertable user2 =new Usertable("user2",new Date(),"test222","9605");
            Usertable user3 =new Usertable("user3",new Date(),"test333","9605");
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

        }
    }
}
