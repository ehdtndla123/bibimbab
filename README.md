# 프로젝트 목적

* 신조어에 대해 검색하고 퀴즈를 풀면서 알게 되고 세대간의 벽을 허물게 하기 위함입니다.



# 구현 내용

* 웹 크롤링한 데이터를 AWS RDS 이용하여 PostgreSQL 데이터베이스에 저장하였습니다.
* 데이터를 기반으로 만들어진 5가지 문제를 풀고 자신이 신조어를 얼마나 알고 있는지 테스트 할 수 있습니다.
* 야민정음을 해석하거나 만들 수 있습니다.
* 데이터베이스에 있는 신조어들을 조회하고 만들 수 있습니다.



#  구현 기술

* 세션을 이용해서 이름을 변경 시키고 점수를 출력하게 할 수 있었습니다.



![image-20220821204819210](/Users/changdongsoo/Library/Application Support/typora-user-images/image-20220821204819210.png)



* docker로 spring boot, nginx 을 이미지로 만들고 docker-compose 를 사용해 여러 개의 컨테이너로부터 이루어진 서비스를 구축 실행하는 순서를 자동으로 하여 관리를 간단히 하였습니다.

  

  

  









Dockerfile

```
FROM openjdk:18-jdk-alpine
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

docker-compose.yml

```
version: '3'
services:
  spring-app:
      container_name: spring-app
      image: docker/spring
      ports:
       - "8080:8080"
  nginx:
      container_name: nginx
      image: docker/nginx
      ports:
       - "80:80"
      depends_on:
       - spring-app
```

Nginx/Dockerfile

```
FROM nginx:latest
RUN rm -rf /etc/nginx/conf.d/default.conf

COPY nginx-app.conf  /etc/nginx/conf.d/app.conf
COPY nginx.conf  /etc/nginx/nginx.conf

VOLUME ["/data", "/etc/nginx", "/var/log/nginx"]

WORKDIR /etc/nginx

CMD ["nginx"]
```

nginx-app.conf

```
server {
    listen       80;
    listen      [::]:80;

    server_name  "";

    access_log off;

    location / {
            proxy_pass http://ec2-52-79-228-118.ap-northeast-2.compute.amazonaws.com:8080;
    	    proxy_set_header Host $host:$server_port;
            proxy_set_header X-Forwarded-Host $server_name;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }

}
```

nginx.conf

```
daemon off;
user  www-data;
worker_processes  2;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
    use epoll;
    accept_mutex off;
}

http {
    include       /etc/nginx/mime.types;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    client_max_body_size 300m;
    client_body_buffer_size 128k;

    gzip  on;
    gzip_http_version 1.0;
    gzip_comp_level 6;
    gzip_min_length 0;
    gzip_buffers 16 8k;
    gzip_proxied any;
    gzip_types text/plain text/css text/xml text/javascript application/xml application/xml+rss application/javascript application/json;
    gzip_disable "MSIE [1-6]\.";
    gzip_vary on;

    include /etc/nginx/conf.d/*.conf;
}
```

* **리버스 프록시**란? 클라이언트 요청을 대신 받아 **내부 서버로 전달**해주는 것을 리버스 프록시(Reverse Proxy) 라고 합니다.

  저도 사실 프록시라는 개념이 낯설었는데요, 일단 프록시라는 개념부터 확인해야 합니다.

  **프록시**란 대리라는 의미로, **정보를 대신 전달해주는 주체**라고 생각하면 되는데, 만약 이 프록시 없이 웹 서버를 운영한다고 가정합니다.

   

  localhost:3000

   

  라고 하는 웹서버를 열어서 운영했을 때, 사용자가 갑자기 많아지거나, 웹서버가 그대로 노출되어 있기 때문에 보안적으로 위험성이 있겠죠? nginx를 사용하면 로드 밸런싱으로 부하를 줄여줄 수 있고, 분산 처리 또한 가능하며 웹서버의 SSL 인증도 적용할 수 있습니다.

   

  따라서 아래와 같이 **사용자 -> nginx -> 웹서버**로 구성해서 사용자의 요청을 nginx가 대신 웹서버로 전달해주도록 구성합니다.

