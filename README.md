# assignment2 by holly.kim  
thread를 사용하여 기존의 주문 서비스에서 요리와 서빙까지 추가 구현  


### 실행 예시  
<img width="635" alt="스크린샷 2025-02-08 오후 3 25 58" src="https://github.com/user-attachments/assets/1bde998e-e7e9-4d2f-a664-a0e0dbafada2" />
<img width="647" alt="스크린샷 2025-02-08 오후 3 26 24" src="https://github.com/user-attachments/assets/0c196dc9-619d-4fb3-95eb-25095b1ddf3f" />
<img width="640" alt="스크린샷 2025-02-08 오후 3 26 48" src="https://github.com/user-attachments/assets/001c4297-f7f2-4077-b17a-8583c1a7fb7c" />
<img width="642" alt="스크린샷 2025-02-08 오후 3 27 09" src="https://github.com/user-attachments/assets/917b87fc-4927-49d4-884e-2de9da4c970f" />
<img width="525" alt="스크린샷 2025-02-08 오후 3 27 24" src="https://github.com/user-attachments/assets/b1bc3775-fdaa-4e42-9785-939fa6c8da99" />
<img width="631" alt="스크린샷 2025-02-08 오후 3 27 37" src="https://github.com/user-attachments/assets/9912b8ee-9d8d-4fbf-af30-a7ceec7c8aad" />





### 해결해야 하는 것들  
서빙이 느리므로 서빙 스레드를 추가해보기  
서빙 스레드 추가 시 null 서빙 문제 해결하기  
주문 시 같은 메뉴를 여러 개 시켰을 때 메뉴 종류가 큐에 들어가는 것이 아니라 상품 개수만큼 각각 큐에 추가되게 해보기(공장이 아니라 실제 시뮬레이션처럼 하기 위해서)  
