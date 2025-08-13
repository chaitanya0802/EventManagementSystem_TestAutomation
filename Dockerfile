# 1. Use an official Maven image with Java 17
FROM maven:3.9.8-eclipse-temurin-17 AS builder
 
# 2. Set working directory inside container
WORKDIR /app
 
# 3. Copy pom.xml and download dependencies first (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B
 
# 4. Copy source code
COPY src ./src
 
# 5. Package the framework (skip tests here to avoid premature run)
RUN mvn clean package -DskipTests
 
# -------------------------
# Runtime container
# -------------------------
FROM eclipse-temurin:17-jdk
 
WORKDIR /app
 
# Copy built project from builder
COPY --from=builder /app /app
 
# Install Google Chrome for Selenium tests
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
&& wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
&& echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" \
> /etc/apt/sources.list.d/google-chrome.list \
&& apt-get update && apt-get install -y google-chrome-stable \
&& rm -rf /var/lib/apt/lists/*
 
# Install ChromeDriver
RUN CHROME_VERSION=$(google-chrome --version | awk '{print $3}') && \
    DRIVER_VERSION=$(curl -sS https://chromedriver.storage.googleapis.com/LATEST_RELEASE) && \
    wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/${DRIVER_VERSION}/chromedriver_linux64.zip" && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin && \
    rm /tmp/chromedriver.zip
 
# Default command to run tests
CMD ["mvn", "test"]