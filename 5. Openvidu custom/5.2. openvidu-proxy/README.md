### 참고
```
https://docs.openvidu.io/en/2.17.0/troubleshooting/#162-modify-openvidu-nginx-configuration
```
### 설명
```
동작중인 nginx의 설정파일 긁어오기
    images와 생성되는 컨테이너가 다르기 때문에 openvidu가 실행중인 상태에서 구성파일을 긁어옴
nginx 설정파일 수정
긁어온 구성파일을 기존 구성파일에 추가
openvidu재시작
```
### 사전준비
```
$ sudo su
$ cd /opt/openvidu
$ vi .env
.env파일이 수정된 상태여야함 : 
    DOMAIN_OR_PUBLIC_IP : DNS만들어 둔거 입력
    OPENVIDU_SECRET : MY_SECRET
    CRETIFICATE_TYPE : letsencrypt
    LETSENCRYPT_EMAIL : 이메일
openvidu 서버가 돌고있어야 함
$ ./openvidu start
```
### nginx설정파일 긁어오기
```
docker-compose exec nginx cat /etc/nginx/conf.d/default.conf > custom-nginx.conf
	  nginx의 구성 <- 이거 수정할 거임
docker-compose exec nginx cat /etc/nginx/nginx.conf > nginx.conf
	  nginx의 설정 파일
```
### custom-nginx.conf 수정
```
yourapp이라고 되있는 부분을 수정했음(/ 위치 가지기 위해)
location / {
  root /var/www/html/dist;
  try_files $uri $uri/ /index.html;
}
```
### 수정한 파일을 기본 구성파일에 포함시키기
```
$ vi docker-compose.yml
    nginx:
        ...
        volumes:
            ...
            - ./custom-nginx.conf:/custom-nginx/custom-nginx.conf
            - ./nginx.conf:/etc/nginx/nginx.conf
            - /var/www/html/dist:/var/www/html/dist
```
### openvidu 재시작
```
./openvidu restart
```