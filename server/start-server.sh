#!/usr/bin/env bash
# start-server.sh
if [ -n "$DJANGO_SUPERUSER_USERNAME" ] && [ -n "$DJANGO_SUPERUSER_PASSWORD" ] ; then
    pipenv run python manage.py createsuperuser --no-input
fi
pipenv run gunicorn server.wsgi:application --bind 0.0.0.0:$PORT --workers 3
