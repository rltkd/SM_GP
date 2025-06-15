# Field Survivor  
**2019180014 백기상**

---

## 🎮 프로젝트 개요

**Field Survivor**는 로그라이크 슈팅 서바이벌 게임입니다.  
플레이어는 선택한 무기를 통해 몰려오는 몬스터들을 처치하며 버티는 것이 목표입니다.  

- 무기: **칼 / 총 (2종)**  
- 게임 방식: **자동 공격**, **강화 시스템**, **지속적 몬스터 웨이브**  
- 맵 크기: **3000 x 2000**  
- 화면 크기: **1600 x 900 고정**, **플레이어 중심 카메라 오프셋**
- 차별점 : **스킬은 없고 플레이어 스탯 업그레이드로만 진행**

### 🔧 플레이어 스탯 업그레이드 목록

- 데미지 증가  
- 공격 범위 증가  
- 공격 속도 증가  
- 방어력 증가  
- 체력 증가  
- 자연 치유 능력  
- 처치 시 치유 능력  

> 무기는 자동으로 일정 쿨다운마다 공격하며, 몬스터 처치 시 경험치를 획득하여 위의 항목 중 하나를 선택해 강화할 수 있습니다.

---

## ✅ 기능 구현 체크리스트

- ✅ 플레이어 이동 (조이스틱 기반)  
- ✅ 무기 선택 후 시작  
- ✅ 몬스터 생성 및 추적  
- ✅ 충돌 판정 (공격 / 피격)  
- ✅ 무기 자동 공격 시스템  
- ✅ 경험치 및 레벨업 시스템  
- ✅ UI (HP, XP, Timer)  
- ✅ 레벨업 강화 시스템  

---

## 🖼️ 커밋 로그

![게임 이미지](https://github.com/user-attachments/assets/d32b30a6-3c15-4b17-ba72-97ed5aae6443)

---
## 🛠 사용된 기술

- Android Canvas 기반 2D 게임 프레임워크 활용
  
- 커스텀 Scene 스택 관리 시스템

- 게임 내 카메라 시스템 구현
  
- 클래스 기반의 Joystick 조작 구현

- 플레이어 능력치(Stat) 적용을 위한 독립된 상태 관리 구조

- 자동 공격 무기 시스템 (근접/원거리)

- 게임 루프 (Choreographer)를 활용한 프레임 기반 업데이트 구조
  
- Object Pooling 기법을 이용한 Bullet, Enemy 객체 재활용 구조
---

## 📚 참고한 자료

- Vampire Survivors (게임 컨셉 )  
- Android Studio 기본 템플릿 및 View 시스템 구조
- 수업 중 소개된 프레임워크(a2dg) 구조 분석 및 활용

---

## 💡 수업 내용에서 차용한 것

- Scene 전환 구조  
- GameObject 업데이트 루프  
- Joystick 클래스 및 터치 처리 방식  
- 충돌 처리 로직  
- 객체 재활용  
- Frame Time 계산

---

## 🧩 직접 개발한 것

- 무기 자동 공격 시스템 (Sword / Gun)  
- Enemy Spawner 및 추적 로직  
- 플레이어 중심 카메라 오프셋 시스템  
- 강화 시스템 (랜덤성 능력치 부여 포함)

---

## 😢 아쉬운 점

- 밸런스 조절이 어려운 문제였음.
- 사운드 시스템이랑 무기별 스킬을 하고싶었으나 못했음. 
- 발매 시엔 UI 디테일 및 이미지 리소스 보완. 밸런스 문제 해결.
- `Enemy` 객체 재생성 시 비정상적인 속도나 한 방에 몬스터가 죽는 버그 발생
---

## 🎓 수업에 대한 소감

- 자바 기반으로 Custom View를 직접 다루며 모바일 게임을 만든 점이 좋은 경험이었음  
- 객체지향 중심 구조 설계 능력을 기를 수 있었음  
- 기본 프레임워크가 있어 개발 부담이 줄었음

- GitHub 커밋으로 코드 복습이 가능한 점은 매우 좋았음  
- 다만 수업 시간 내 집중도 유지가 어려운 구조였음  
  - 타이핑이나 실습형이 아닌 경우, 잠깐만 흐름 놓쳐도 이해 어려움  

---

## 🖼️ 시연
![Image](https://github.com/user-attachments/assets/03c7b0a9-9269-4d51-965a-e9dce9e357aa)
title

![Image](https://github.com/user-attachments/assets/1dab44bf-1a3a-4bdb-b384-1c58dbcae6a5)
selectweapon

![Image](https://github.com/user-attachments/assets/9e6a77d2-9f5b-47f6-a79e-29cc0a99dfad)
ingame
![Image](https://github.com/user-attachments/assets/7abc7a9e-ed3a-43fc-9b2e-47d7a1a33ef2)
upgrade

![Image](https://github.com/user-attachments/assets/8b640913-0d66-4f7c-80d2-2ed8af8e7afa)
pause
![Image](https://github.com/user-attachments/assets/ceb60cb2-9e2f-4099-954d-c6d7f86017cd)
stat
