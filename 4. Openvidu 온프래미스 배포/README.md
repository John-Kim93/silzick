### 설명
```
도커 : 개발환경 컨테이너 단위
도커 컴포즈 : 개발 환경 컨테이너들을 묶어놓은 단위
설치 : 공식문서보고 했음 무슨 의미인지는 모름
```
### 도커 설치 & 도커 컴포즈 설치
```
$ sudo apt-get update
$ sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
$ echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$ sudo apt-get update
$ sudo apt-get install docker-ce docker-ce-cli containerd.io
```
```
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
$ sudo chmod +x /usr/local/bin/docker-compose
```
### OPENVIDU On premises 설치
```
$ sudo su
$ cd /opt
$ curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash
```
### 설정파일 셋팅
```
$ cd openvidu
$ vi .env
    DOMAIN_OR_PUBLIC_IP : DNS만들어 둔거 입력
    OPENVIDU_SECRET : MY_SECRET
    CRETIFICATE_TYPE : letsencrypt
    LETSENCRYPT_EMAIL : 이메일
```
### Openvidu 실행
```
$ ./openvidu start
```