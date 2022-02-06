### 설명
```
탄력적 IP : 
    AWS인스턴스는 DHCP이기 때문에 껏다 킬때마다 IP주소가 바뀜
    DNS와의 연결을 위해 고정된 IP를 가져야 함
FreeNom : 
    돈아까워서 무료버전의 DNS를 할당받는 사이트를 사용함
    실제 런칭할 서버스에는 사용하면 안됨
```
### 탄력적 IP 할당
```
위치 : AWS console - EC2 - 네트워크 및 보안 - 탄력적IP
우측 상단 탄력적 IP주소 할당 클릭 - 할당
```
### DHCP를 탄력적 IP로 전환
```
인스턴스 ID확인
탄력적 IP 클릭 - 우측 상단 작업 클릭 - 탄력적 IP 주소 연결
인스턴스 탭에 확인했던 인스턴스 ID넣고 연결
인스턴스 재부팅
```
### DNS주소 획득
```
위치 : freenom - service - Register a New Domain
입력창에 만들고 싶은 dns입력
-> 5개 중 하나 select하면 위쪽에 checkout버튼 생김, 클릭
12개월까지 free버전이므로 선택후 dns획득
```
### AWS에 DNS연결
```
위치 : aws console - route 53 - dns관리
우측 상단 호스팅 영역 생성 - 도메인 이름에 DNS넣고 호스팅 영역 생성
레코드 생성  - 레코드 이름 : 비워놓기
            - 값 : 탄력적 IP
            - 레코드 유형 : A
            - 레코드 생성
유형 NS인 레코드의 값/트래픽 라우팅 대상 4개 있음, 복사

위치 : freenom - service - my domain - manage domain - management tools - nameservers
Use custom nameservers선택 - 입력창 5개중 4개에다가 위에서 복사한것 붙여넣기
```