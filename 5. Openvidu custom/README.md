### 설명
```
openvidu-server : 서버 수정 후 올리는 방법
openvidu-proxy : nginx설정파일 뽑아서 수정
custom back server : java-spring서버 
custom front server : openvidu-proxy에 포함
```
### 주의
```
front에서 openvidu기능활용 axios요청 보낼때, port번호 입력 안하는걸로
    nginx에서 각 서버들을 proxy로 연결하기 때문에(아마도 이게 원인이라고 생각함)내부에서 포트번호 없이 연결됨
```