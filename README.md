# Donation-Project-Spring-chatServer







-------------------------------------------------------------------
-------------------------------------------------------------------





*개인프로젝트 사이트 : http://myhopebridge.duckdns.org/

## 프로젝트 개요
이 프로젝트는 
*기부 모금 대행 웹 서비스 
* 로, 사용자가 쉽게 기부를 하고,모금 내역 기부 및 기부처 전달 영상을 확인할 수 있도록 제공하는 웹사이트 개발 진행 중입니다. 
토스페이먼츠 API를 활용하여 결제 시스템을 연동하고(테스트 연결은 완료 실제 서비스는 진행중), 실시간 기부 모니터링과 웹소켓 기반 채팅 기능을 제공합니다.

 핵심 기능
⦁	로그인 세션 관리 (JWT + Redis 기반 세션 관리)
⦁	실시간 기부 내역 조회 (Redis 캐싱 활용)
⦁	실시간 채팅 시스템 (WebSocket 활용)
⦁	관리자 기능 (기부 내역)





서비스 아키텍쳐 설명
# 서비스 아키텍처
이 프로젝트는 **프론트엔드(React) - 백엔드(Spring Boot) - 데이터베이스(PostgreSQL) - 캐시(Redis)** 구조로 설계되었습니다.

📌 구성 요소
1. Frontend (React)
   - 사용자가 기부 및 결제, 기부 내역, 채팅, 영상 시청등  조회할 수 있는 UI 제공
2. Backend (Spring Boot)
   - REST API 및 WebSocket 서버
   - 사용자 인증 (JWT + Redis 세션)
   - 결제 처리 (토스페이먼츠 API 테스트 연동)  *개발중
3. Database (PostgreSQL)
   - 기부 내역, 사용자 정보, 결제 내역 저장
4. Redis
   - 로그인 세션 관리 (JWT + Redis)
   - 자주 조회되는 데이터(총 기부금 등) 캐싱
   - 채팅 메시지, 채팅방 등 실시간성 데이터 관리
5. WebSocket (Spring Boot 기반)
   - 실시간 채팅 기능 제공 (Redis 활용)


📌 서비스 아키텍처 다이어그램


<img width="552" height="355" alt="아키텍처" src="https://github.com/user-attachments/assets/7cf39463-7969-4547-96f1-4ac6572ea2e8" />



📌 설명

✅ User → Router → Nginx (Reverse Proxy)
⦁	사용자가 사이트에 접속
⦁	Nginx가 Reverse Proxy 역할을 수행하여 요청을 적절한 서비스로 분배

✅ Frontend (React) → Backend (Spring Boot)
⦁	프론트엔드는 API 요청을 백엔드로 전달
⦁	백엔드는 DB에서 데이터를 조회하거나, Redis에서 캐싱된 데이터를 가져옴

✅ Frontend (React) → ChatServer (Spring WebSocket)
⦁	WebSocket을 사용해 실시간 채팅 서버와 직접 연결
⦁	사용자의 메시지를 실시간으로 처리하여 다른 클라이언트에게 전달

✅ Backend ↔ Database (PostgreSQL)
⦁	백엔드는 PostgreSQL을 사용하여 데이터를 저장 & 조회

✅ Backend ↔ Redis (세션 & 캐싱)
⦁	로그인 세션 관리 및 자주 조회되는 데이터(예: 총 모금액) 캐싱

⦁	성능을 높이기 위해 Redis를 활용
✅ ChatServer ↔ Redis (실시간 메시지 큐)
⦁	실시간 채팅 서버에서는 Redis를 사용하여 메시지를 중계
⦁	사용자가 보낸 채팅 메시지를 Redis에 저장 후 다른 사용자에게 전달
⦁	실시간 성능 최적화를 위해 Redis를 메시지 브로커(Message Broker)로 활용



<img width="626" height="435" alt="image" src="https://github.com/user-attachments/assets/8c58a62e-4816-4ea2-a560-960d6693bf1f" />

*디테일한 erd  (https://www.erdcloud.com/d/3M798LiBGRR7ddbSF) 참고

⦁	사용자 (users)

⦁	기부 (donation), 결제 (payment), 채팅 (messages, chat_users), 세션 (user_sessions), 감사 메시지 (thank_you_messages)와 연관됨

⦁	기부 (donation)

⦁	bank(입금은행), payment(결제 정보), donation_history(기부 내역)와 연결됨

⦁	결제 (payment)

⦁	bank(결제 은행), transaction(결제 트랜잭션)과 연결됨

⦁	세션 (user_sessions)

⦁	로그인한 사용자의 기록 저장 (IP 주소, 로그인/로그아웃 시간)

⦁	채팅 (messages, chat_users)

⦁	사용자의 실시간 채팅 기록 저장


## 기술적 과제 및 해결 과정
이 프로젝트를 개발하며 **대규모 트래픽 대응, 성능 최적화, 보안 강화**를 위한 다양한 문제를 고려하였습니다.

 1. 트래픽 급증 대응
   - 기부 방송 중 순간적으로 많은 사용자가 접속하는 문제 발생시 
   - **해결 방법:** Redis 캐싱 적용 + WAS 증설 (클라우드 이관) + Nginx 로드 밸런싱 적용
	 2. 외부 비정상 접근자 대응
⦁	Nginx Rate Limiting (ddos 대응) 적용 (진행예정)
⦁	SSL/TLS 인증서를 적용 ( https 모든 트레픽 암호화) 적용 (진행예정)


## 마무리 

개인 프로젝트를 통해 경험한 기술을 다시 적용하며 되새기고, 새로운 기술들을 녹여 실험하는 과정을 진행 중입니다.
실제 운영 환경을 고려해 Redis 캐싱, JWT 인증, WebSocket 실시간 채팅, 결제 API 연동 등을 적용하고, 네트워크 설정 및 서버 구성까지 직접 다루며 깊이 있는 아키텍처 설계를 고민하고 있습니다.
이 과정에서 부족한 부분을 채우고, 배운 것을 공유하며 더 나은 방향을 찾고 싶다는 생각이 들었습니다. 앞으로 좋은 동료들과 함께 고민을 나누고, 부족한 점은 채우며, 내가 경험한 것들을 공유하는 개발자로 성장하고 싶습니다.

