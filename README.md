# 🎸 Band-Connect Web Service Modernization
**Servlet/JSP 기반의 레거시 시스템을 Spring Boot와 Thymeleaf로 현대화한 마이그레이션 프로젝트입니다.**

---

## 📌 Project Overview
본 프로젝트는 기존의 강결합된 Servlet/JSP 구조에서 발생하는 기술부채를 해결하기 위해, Spring Boot 환경으로 전면 리팩토링 및 마이그레이션을 진행했습니다.

## 🌿 Branch Strategy
과거의 레거시 코드와 현대화된 코드를 비교 분석할 수 있도록 브랜치를 분리하여 관리합니다.

- **`main` (Current):** Spring Boot + Data JPA + Thymeleaf 기반의 현대화된 버전
- **`legacy-jsp`:** 기존 HttpServlet + JDBC + JSP **Layered Architecture** 기반의 원본 버전

---

## 🛠️ Modernization Key Points (주요 개선 사항)

### 1. Architecture: Monolithic ➡️ Layered
- **As-Is:** Servlet 하나에 비즈니스 로직과 DB 접근 로직이 혼재된 구조.
- **To-Be:** `Controller - Service - Repository - Entity` 계층 분리를 통해 관심사 분리(SoC) 및 유지보수성 향상.

### 2. View Engine: JSP ➡️ Thymeleaf
- **As-Is:** 스파게티 코드가 발생하기 쉬운 Scriptlet 기반 JSP.
- **To-Be:** Natural Template 특성을 가진 **Thymeleaf** 도입으로 퍼블리싱 생산성 및 가독성 확보.

### 3. Data Access: JDBC ➡️ Spring Data JPA
- **As-Is:** 반복적인 SQL 작성 및 자원 반납(close) 노가다 발생.
- **To-Be:** **JPA** 도입으로 객체 지향적인 데이터 핸들링 및 `default_batch_fetch_size` 설정을 통한 N+1 문제 최적화.

---

## 💻 실행 방법 (How to Run)
1. `git checkout main`
2. `./gradlew bootRun`
3. 접속: `http://localhost:8080`
