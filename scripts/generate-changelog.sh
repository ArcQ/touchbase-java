cd core_api
./gradlew generatedb
./gradlew diffChangeLog -PrunList=diffLocalDb -PchangeLogFile=src/main/resources/db/${1}.sql
cd -
