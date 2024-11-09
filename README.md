## 기본 패키지 설계
```📂 project-root
└── src
    ├── main
    │   ├── java
    │   │   └── com.orderdataintegrator
    │   │       ├── App.java                          # 메인 애플리케이션 실행 클래스
    │   │       ├── conf
    │   │       │   └── AppConfig.java               # 애플리케이션 설정 클래스
    │   │       ├── controller
    │   │       │   ├── ApiControllerAdvice.java     # 공통 예외 처리 컨트롤러
    │   │       │   └── OrderSearchingController.java # 주문 검색 API 컨트롤러
    │   │       ├── entity
    │   │       │   └── Order.java                   # 주문 엔티티 클래스
    │   │       ├── external
    │   │       │   └── OrderDataFetcher.java        # 외부 데이터 페처 인터페이스
    │   │       ├── repository
    │   │       │   ├── InMemoryOrderRepository.java # 인메모리 주문 저장소
    │   │       │   └── OrderRepository.java         # 주문 저장소 인터페이스
    │   │       └── service
    │   │           └── OrderService.java            # 주문 비즈니스 로직 서비스
    │   └── resources
    │       └── application.yml                      # 애플리케이션 설정 파일
    └── test
        └── java
            └── com.orderdataintegrator
                ├── AppTests.java                    # 애플리케이션 테스트 클래스
                └── repository
                    └── OrderRepositoryTest.java     # 주문 저장소 테스트 클래스
```
