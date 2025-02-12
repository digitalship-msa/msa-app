# 1. OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# 2. JAR 파일 복사
WORKDIR /app
COPY build/libs/*.jar app.jar

# 3. 컨테이너가 실행될 때 JAR 실행
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]
