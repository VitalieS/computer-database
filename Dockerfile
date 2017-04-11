# Pull base image.
FROM ubuntu:latest

MAINTAINER "vsova <vsova@excilys.com>"

# Update Ubuntu
RUN apt-get update && apt-get -y upgrade

# Add Oracle Java 8 repository
RUN apt-get -y install software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get -y update

# Accept the Oracle Java license
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections

# Install Oracle Java
RUN apt-get -y install oracle-java8-installer

apt-get clean

# Install Maven
RUN apt-get -y install maven

# SANDBOX
# /usr/bin/debconf-set-selections
# RUN mkdir -p /usr/src/app
# WORKDIR /usr/src/app
# ADD . /usr/src/app
