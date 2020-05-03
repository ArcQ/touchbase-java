# Dockerfile

# FROM directive instructing base image to build upon
FROM python:3.7-buster

RUN mkdir -p /opt/app
RUN mkdir -p /opt/app/touchbase
RUN mkdir -p /opt/app/.pip_cache
COPY . /opt/app/touchbase/
WORKDIR /opt/app/touchbase

FROM python:3.7
RUN pip install pipenv
RUN pipenv lock --requirements > requirements.txt
RUN pip install -r /tmp/requirements.txt --cache-dir=/opt/app/.pip_cache
RUN python manage.py migrate
COPY . /tmp/myapp
RUN chown -R www-data:www-data /opt/app

EXPOSE 8010
STOPSIGNAL SIGTERM
CMD ["/opt/app/touchbase/start-server.sh"]
