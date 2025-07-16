FROM tomcat:10.1-jdk21

# Remove default apps    --> this may be redundant step, why to increase the layers?
RUN rm -rf /usr/local/tomcat/webapps/*          

# Copy your WAR file into tomcat's webapps directory
COPY target/contactbook.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 9090

# Start tomcat
CMD ["catalina.sh", "run"]



# specify user, follow best practice.
