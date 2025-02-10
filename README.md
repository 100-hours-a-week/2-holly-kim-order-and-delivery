# assignment2 by holly.kim  
thread를 사용하여 기존의 주문 서비스에서 요리와 서빙까지 추가 구현  
thread로 시간의 흐름도 구현


### 실행 예시  

<iframe width="560" height="315" src="https://www.youtube.com/embed/fsMpz8w6Huk" frameborder="0" allowfullscreen></iframe>

<iframe width="315" height="560" 
src="https://www.youtube.com/embed/QPOLrbKI5oQ" 
title="YouTube video player" frameborder="0" 
allow="accelerometer; autoplay; clipboard-write; encrypted-media;
gyroscope; picture-in-picture;
web-share"
allowfullscreen></iframe>


<iframe width="315" height="560" 
src="https://www.youtube.com/embed/QPOLrbKI5oQ" 
title="YouTube video player" frameborder="0" 
allow="accelerometer; autoplay; clipboard-write; encrypted-media;
gyroscope; picture-in-picture;
web-share"
allowfullscreen></iframe>


### 1주차 과제와 달라진 점
스레드 풀을 생성하여 시간 스레드, 조리 스레드, 서빙 스레드를 관리하도록 했습니다.  
조리 과정이 느리므로 요리 작업을 담당하는 스레드를 두 개 사용했습니다.  
주문 시 같은 메뉴를 여러 개 시켰을 때 상품 개수만큼 각각 orderList라는 큐에 추가되게 했습니다. 큐에 저장하기 위해 order객체를 만들었습니다.  
CountDownLatch인 latch를 사용해서 주문 큐에 들어온 모든 상품이 처리되도록 했습니다.  


큐를 내부적으로 동기화(락)를 제공하는 LinkedBlockingQueue로 구현했습니다. -> 자동 동기화 및 여러 개의 스레드가 동시에 접근해도 데이터 무결성 보장  
CookManager(요리사)가 완성된 요리를 넣고 ServeManager(서빙 담당)가 처리하는 경우 deliveryQueue가 자동으로 동기화를 보장해서 데이터 충돌 없이 안전하게 공유가 가능합니다.   
