# assignment2 by holly.kim  
thread를 사용하여 기존의 주문 서비스에서 요리, 서빙, 시간의 흐름 구현


### 실행 예시  
(https://www.youtube.com/shorts/fsMpz8w6Huk)



### 기능 구현 설명 (파일명 클릭하면 링크로 갈 수도 있음)

| [HamburgerStoreSimulation.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/HamburgerStoreSimulation.java) | main() 함수가 여기에 위치, 전체적인 실행 흐름을 여기서 관리. (여기서 실행하시면 됩니다.) |
| --- | --- |
| [Item.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/Item.java) | 아이템 abstract class |
| [MainItem.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/MainItem.java) | 메인 메뉴 아이템 class (버거) |
| [AdditionalItem.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/AdditionalItem.java) | 추가 메뉴 아이템 class (감자튀김,음료) |
| [PromotionItem.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/PromotionItem.java) | 프로모션 메뉴 아이템 class |
| [Order.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/Order.java) | 주문을 저장하는 class (주문에 들어있는 상품 목록과 주문 수량, 금액을 인스턴스 변수로 가짐) |
| [OrderManager.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/OrderManager.java) | 주문을 관리 |
| [TimeManager.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/TimeManager.java) | Runnable을 구현한 class. 시간 스레드의 동작을 정의 |
| [CookManager.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/CookManager.java) | Runnable을 구현한 class. 요리 스레드의 동작을 정의 |
| [ServeManager.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/ServeManager.java) | Runnable을 구현한 class. 서빙 스레드의 동작을 정의 |
| [PaymentManager.java](https://github.com/100-hours-a-week/2-holly-kim-order-and-delivery/blob/main/Store/PaymentManager.java) | 계산을 관리 |

### 동시성 구현 설명

요리사가 2명이 요리를 하고 홀 직원 1명이 서빙을 한다.  

음식이 서빙될 때까지의 과정:   

준비 중(소요시간: 4초) → 준비완료, 서빙 대기→ 서빙 중(소요시간: 2초)→ 서빙완료 

음식을 준비하는 중에 서빙이 독립적으로 이뤄지므로 스레드로 비동기 프로그램을 구현했음을 알 수 있다.

이때 주문이 끝난 이후부터 소요시간이 함께 표시된다.

### 구현 특징

- 스레드 풀을 생성하여 시간 스레드, 음식준비 스레드, 서빙 스레드를 관리하도록 했다.

- 음식준비 과정이 느리므로 음식준비 작업을 담당하는 스레드를 두 개 사용했다.
  
- 주문 시 같은 메뉴를 여러 개 시켰을 때 상품 개수만큼 각각 orderList라는 큐에 추가되게 했습니다. 큐에 저장하기 위해 Order class를 새롭게 추가했다.
- CountDownLatch를 사용해서 주문 큐에 들어온 **모든 상품이 처리되도록** 했다. CountDownLatch는 **상태 변수(state variable)와 내부적으로 사용되는 lock 메커니즘**을 조합하여 동기화를 수행한다. 여기서는 orderList의 요소의 개수를 카운트의 초기값으로 설정했고 0이 되면 대기중인 모든 스레드가 해제되었다.
- deliveryQueue를 내부적으로 동기화(락)를 제공하는 LinkedBlockingQueue로 구현했다. -> 자동 동기화 및 여러 개의 스레드가 동시에 접근해도 데이터 무결성 보장
- CookManager(요리사)가 완성된 요리를 넣고 ServeManager(서빙 담당)가 처리하는 경우 deliveryQueue가 자동으로 동기화를 보장해서 데이터 충돌 없이 안전하게 공유가 가능하다.
- 스레드 풀도 마지막에 잘 닫아주었다.
- AtomicBoolean shutdownFlag = new AtomicBoolean(false)로 초기화했다. AtomicBoolean을 사용하면 타입을 boolean으로 할 때와 다르게 가시성 문제가 발생하지 않는다. 다른 쓰레드에서 `shutdownFlag.set(true);` 하면 이 변경 사항이 즉시 반영된다. (참고로 타입이`boolean` 이면 단순한 변수이므로 CPU 캐시에 머물러 최신 값이 반영되지 않는다.) volatile을 사용해도 가시성 문제가 발생하지 않을 것이다.

### 객체 지향-책임 분리

Order 내에서 계산을 하도록 할 수도 있으나 계산은 PaymentManager에서 처리하게 했다.
