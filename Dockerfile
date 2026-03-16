# 1단계: 빌드 스테이지 (JDK 17 + Gradle)
FROM gradle:8.10-jdk17 AS build
WORKDIR /home/gradle/project

# 소스 코드를 컨테이너로 복사
COPY --chown=gradle:gradle . .

# gradlew 실행 권한 부여 및 war 파일 빌드
# --no-daemon을 사용하여 일회성 빌드에 최적화
RUN chmod +x ./gradlew
RUN ./gradlew clean war --no-daemon

# 2단계: 실행 스테이지 (Tomcat 9)
FROM tomcat:9.0-jdk17-openjdk-slim

# 빌드 스테이지에서 생성된 war 파일을 Tomcat의 webapps 폴더로 복사
# ROOT.war로 이름을 바꾸면 접속 시 경로 뒤에 프로젝트명을 붙이지 않아도 됩니다 (예: localhost:8082/)
COPY --from=build /home/gradle/project/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat 기본 포트 노출
EXPOSE 8080

# Tomcat 실행
CMD ["catalina.sh", "run"]