### 설명
```
openvidu에 기본으로 들어가 있는 서버들은 일반적인 방법으로 갈아 끼우기에는 맨탈이 나가 수 있다.
openvidu를 클론해와서 수정할 부분을 수정하고, 필요한 이미지를 자체생산하도록 하자
자체생산한 이미지를 docker-compose.yml파일에 갈아 끼우도록 한다.
```
### 깃 클론
```
$ git clone https://github.com/OpenVidu/openvidu.git
    수정하고 싶은 내용 수정
```
### 이미지 생성
```
$ cd openvidu/openvidu-server/docker/openvidu-server
    EC2는 bash를 사용하고 있고, bash에서는 create_image.sh가 안먹힘
$ vi create_image.sh
    맨 첫줄에 추가 : #!/bin/bash
$ cd openvidu/
$ sudo mvn package -DskipTests
$ cd openvidu-server/docker/openvidu-server
$ ./create_image.sh x.y.z
```
### 이미지 갈아 끼우기
```
$ sudo su
$ cd /opt/openvidu
$ vi docker-compose.yml
    openvidu-server
      image:만든 이미지로 갈아 끼우면 됨(아마 버전만 바꾸면 될거임)
$ ./openvidu restart
```