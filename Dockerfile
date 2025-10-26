# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Gradle 설정 파일들 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# 의존성 다운로드
RUN gradle dependencies --no-daemon || true

# 소스 코드 복사
COPY src ./src

# 빌드 (테스트 제외)
RUN gradle clean build -x test --no-daemon

# 2단계: 실행 스테이지
FROM openjdk:21-jdk-slim

WORKDIR /app

# 빌드 스테이지에서 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 환경변수 기본값
ENV SPRING_PROFILE=dev
ENV SERVER_PORT=8301

# 포트 노출
EXPOSE ${SERVER_PORT}

# 실행
ENTRYPOINT ["java", "-jar", \
  "-Dspring.profiles.active=${SPRING_PROFILE}", \
  "-Dserver.port=${SERVER_PORT}", \
  "app.jar"]