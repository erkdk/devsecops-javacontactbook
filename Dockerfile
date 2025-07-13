# Use official Tomcat 10.1 image with JDK 21
FROM tomcat:10.1-jdk21

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into Tomcat's webapps directory
COPY target/contactbook.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 9090

# Start Tomcat
CMD ["catalina.sh", "run"]

