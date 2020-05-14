# Dockerfile

# FROM directive instructing base image to build upon
FROM python:3.7
ADD . /server
WORKDIR /server
ENV PIPENV_VENV_IN_PROJECT 1
RUN pip install pipenv
RUN pipenv install --deploy

EXPOSE $PORT
STOPSIGNAL SIGTERM
CMD ["/server/start-server.sh"]
