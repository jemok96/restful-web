package com.example.restfulwebservice;

import com.example.restfulwebservice.domain.Post;
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
    public void init() {
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Usertable user1 = new Usertable("user1", new Date(), "test111", "9605");
            Usertable user2 = new Usertable("user2", new Date(), "test222", "9605");
            Usertable user3 = new Usertable("user3", new Date(), "test333", "9605");

            Post post1 = Post.builder().
                    description("First Post")
                    .user(user1).build();
            Post post2 = Post.builder().
                    description("Second Post")
                    .user(user1).build();
            Post post3 = Post.builder().
                    description("Third Post")
                    .user(user3).build();
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(post1);
            em.persist(post2);
            em.persist(post3);

        }
    }
}
