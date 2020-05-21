#based on tomcat9 version to continue my work
FROM tomcat:9.0.16-jre8

#Only the expose command can be used to access a specific port
#my project can only start on 8080 port
EXPOSE 8080

#ENV DB_URL=""

#reomve ROOT in tomcat docker container
RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY ./*.war /usr/local/tomcat/webapps/ROOT.war

COPY ./setenv.sh /usr/local/tomcat/bin/setenv.sh