### 설명
```
이 문서는 인스턴스에서 http://{public_IP} 로 vue페이지를 띄우는 과정까지를 설명
```
### 인스턴스 연결
### 필요한거 설치
```
$ sudo apt-get update
$ sudo apt-get upgrade
    nginx 설치
$ sudo apt-get install nginx
    브라우저에 http://{public_IP} 쳐보고 welcome페이지 확인
    npm 설치
$ sudo apt install npm
```
### 깃 클론(openvidu-tutorials 사용)
```
$ git clone https://github.com/OpenVidu/openvidu-tutorials.git -b v2.20.0
```
### 서버 켜보고 빌드
```
$ cd openvidu-tutorials/openvidu-insecure-vue
$ npm install
$ npm run serve
  	크롬에 http://{public_IP}:8080 확인
$ npm run build
```
### dist폴더 이동시키고 nginx에 적용
```
    dist폴더 이동
$ sudo cp -r ./dist/ /var/www/html/
	  nginx설정 수정
$ cd /etc/nginx/site-availale
$ sudo vi default
  	server {
  		~~~
  		root /var/www/html/dist;			이것만 수정
```