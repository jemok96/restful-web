package com.example.restfulwebservice.di;

/**
 * Dependency Injection(의존관계 주입)
 * 강한 결합 : A라는 클래스 내에서 B라는 클래스를 new 연산자를 사용해 직접 만들어 사용할 경우
 * B클래스말고 다른 C클래스로 수정할 때 A클래스도 수정해야하는 방식으로 강한 결합이라고 한다.
 *
 * 약할 결합 : 보통 인터페이스와 다형성을 통해 구현되는데, A클래스에서 어떤 클래스를 사용함에 있어
 * 외부에서 주입받아 사용할 때 약한 결합이라고 한다. 이렇게 하면 결합도를 낮출 수 있어 변화에 유리하다.
 *
 * 의존관계 주입에는 생성자주입, 수정자 주입, 필드 주입 방법이 있다.
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 수정자(Set)주입의 단점, 필드주입 단점
         * 컴파일단계에서 명확하게 의존관계를 확인할 수 없음
         * Controller만 생성해도 Controller내부에있는 Service를 사용할 수 있다. 하지만 nullPointerException이 발생함
         * 컴파일 단계에서 확인할 수 없어서 개발자 실수로 의존관계를 주입하지 않을 경우 예외가 발생하는 거임
         */
        /**
         * 생성자(Constructor)주입
         * 객체를 생성하는 시점(컴파일) 단계에서 의좐관계에 대한 주입(조립)이 이뤄지기 때문에
         * 의존관계를 주입하지 않을 경우 객체 생성조차 불가능하다. 컴파일 단계에서 오류를 잡아낼 수 있다
         * 추가적으로 final을 사용할 수 있게 된다. final변수는 선언과 동시에 초기화 되어야 하기 때문에 Setter주입시 final 사용불가
         * fina의 장점은 누군가 객체를 바꿔치기 할 수 없다는 점이다.
         *
         */
        DiController diController = new DiController(new DiServiceImpl());
//        diController.setService(()-> System.out.println("hello"));
        diController.controllerService();
    }
}
