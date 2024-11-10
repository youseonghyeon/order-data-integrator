# 주문 관리 시스템 과제 - 유성현

## 사용한 기술 스택
- **Java 21** & **Gradle**
- **Spring Boot 3.3.5**
- **Lombok**

## 목차
- [클래스 다이어그램](#클래스-다이어그램)
- [외부 연동 서비스에 대한 구조 및 설명](#외부-연동-서비스에-대한-구조-및-설명)
- [서비스 실행 방법](#서비스-실행-방법)

---

## 클래스 다이어그램
<img width="1167" alt="image" src="https://github.com/user-attachments/assets/3a4faf5e-db77-4483-ad6b-43c4bbc96369">

---

## 외부 연동 서비스에 대한 구조 및 설명

### 1. `OrderDataFetcher` 인터페이스
- 외부 시스템에서 주문 데이터를 가져오는 역할을 담당합니다.
- 다중 처리가 가능하며, 리턴 타입과 컨버터를 사용하여 다양한 데이터를 처리할 수 있습니다.

### 2. `HttpOrderDataFetcher` 구현체
- `OrderDataFetcher`를 구현하여 HTTP GET 요청을 통해 외부 시스템에서 데이터를 가져옵니다.
- `RestTemplate`을 통해 요청을 보내고, 데이터를 `ExternalFetchResponse`로 받아 `Order` 객체로 변환합니다.

### 3. `OrderDataSender` 인터페이스
- 외부 시스템으로 주문 데이터를 전송하는 역할을 합니다.
- `Order` 리스트를 받아 처리합니다.

### 4. `HttpOrderDataSender` 구현체
- `OrderDataSender` 인터페이스를 구현하여 HTTP POST 요청으로 외부 시스템에 데이터를 전송합니다.
- `Order` 리스트를 변환 후 전송하며, 응답을 `ExternalSenderResponse`로 받아 전송 성공 여부를 반환합니다.

### 5. `OrderRepository` 인터페이스 & `InMemoryOrderRepository` 구현체
- `Order` 객체를 메모리에 저장하고 관리하는 인터페이스입니다.
- `InMemoryOrderRepository`는 `OrderRepository`를 구현하며, 멀티 스레딩 환경을 고려하여 `ConcurrentHashMap`을 사용하여 동시성을 보장합니다.

### 6. `OrderService`
- 주문 데이터를 가져오고, 전송하는 주요 서비스를 제공합니다.
- `OrderDataFetcher`, `OrderDataSender`, `OrderRepository`와 함께 작동하여 필요한 기능을 수행합니다.

### 7. `AppConfig`
- 외부 시스템과의 연동 설정을 위한 구성 클래스입니다.
- `RestTemplate` 빈과 URL 설정을 포함하여, 외부 데이터 연동에 필요한 환경을 제공합니다.

---

## 서비스 실행 방법

서비스 실행은 테스트 코드로 확인할 수 있습니다.

### 1. `OrderRepositoryTest`
- **주요 테스트 항목:** `Order` 객체의 조회, 저장, 수정

### 2. `HttpOrderDataFetcherTest`
- **주요 테스트 항목:** 외부 시스템에서 데이터를 가져오거나 실패하는 경우
- **외부 시스템 연동:** `MockServiceServer`를 이용하여 테스트

### 3. `HttpOrderDataSenderTest`
- **주요 테스트 항목:** 외부 시스템으로 데이터를 전송하거나 실패하는 경우
- **외부 시스템 연동:** `MockServiceServer`를 이용하여 테스트

### 4. `OrderServiceTest`
- **주요 테스트 항목:**
   - 주문 데이터를 가져오고 전송하는 메서드
   - 주문 데이터를 저장하고 조회하는 메서드
- **단위 테스트 구성:** `TestComponent` 사용
   - `TestOrderDataFetcher`: `fetchOrderData` 호출 시 두 개의 데이터를 반환하도록 설정된 테스트 컴포넌트
   - `TestOrderDataSender`: `sendOrderData` 호출 시 파라미터에 데이터가 있으면 성공을 반환하도록 설정된 테스트 컴포넌트

---
