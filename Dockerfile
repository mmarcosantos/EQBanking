FROM bellsoft/liberica-openjdk-alpine:17

# Specify a volume for temporary files (optional)
VOLUME /tmp

# Set the JAR_FILE argument
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the image
COPY ${JAR_FILE} app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]