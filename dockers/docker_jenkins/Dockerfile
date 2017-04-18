FROM jenkins:2.46.1

USER root
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN groupadd -g 999 docker && usermod -a -G docker jenkins